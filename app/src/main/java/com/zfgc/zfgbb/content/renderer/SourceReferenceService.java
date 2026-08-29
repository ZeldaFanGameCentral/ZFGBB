package com.zfgc.zfgbb.content.renderer;

import java.time.OffsetDateTime;
import java.time.ZoneOffset;
import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Deque;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedHashMap;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.jsoup.nodes.Entities;
import org.springframework.stereotype.Component;

import com.samskivert.mustache.Mustache;
import com.zfgc.zfgbb.content.ContentFormat;
import com.samskivert.mustache.Template;
import com.zfgc.zfgbb.content.renderer.bbcode.AuthoredSource;
import com.zfgc.zfgbb.content.renderer.bbcode.BBCodeParser;
import com.zfgc.zfgbb.content.renderer.bbcode.BBCodeNode;
import com.zfgc.zfgbb.content.renderer.bbcode.BBCodeGrammar;
import com.zfgc.zfgbb.content.renderer.bbcode.BBCodeGrammarHolder;
import com.zfgc.zfgbb.content.renderer.bbcode.BBCodeTag;
import com.zfgc.zfgbb.content.renderer.bbcode.BBCodeText;
import com.zfgc.zfgbb.content.renderer.ContentRenderingService.QuotingPost;
import com.zfgc.zfgbb.exception.InvalidBBCodeGrammarException;
import com.zfgc.zfgbb.model.forum.BBCodeAttributeMode;
import com.zfgc.zfgbb.model.forum.BBCodeConfig;
import com.zfgc.zfgbb.model.forum.BBCodeDateElement;

import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class SourceReferenceService {

	public static final String SOURCE_UNAVAILABLE_PLACEHOLDER = "(quoted message unavailable)";


	static final int MAX_SOURCE_REFERENCES_PER_SCOPE = 500;

	private static final Logger LOGGER = LogManager.getLogger(SourceReferenceService.class);

	private static final String AUTHOR_ATTRIBUTE_NAME = "author=";

	private static final Mustache.Compiler MARKUP_COMPILER = Mustache.compiler()
			.defaultValue("")
			.emptyStringIsFalse(true)
			.withEscaper(Entities::escape);

	private final List<ContentTagResolver> contentTagResolvers;

	private final BBCodeGrammarHolder grammarHolder;

	@FunctionalInterface
	public interface SourceBodyRenderer {
		String render(String rawBody, ContentFormat contentFormat, OffsetDateTime quotingCreatedTs);
	}

	private volatile SourceBodyRenderer sourceBodyRenderer;

	public void registerSourceBodyRenderer(SourceBodyRenderer renderer) {
		sourceBodyRenderer = renderer;
	}

	private SourceBodyRenderer registeredSourceBodyRenderer() {
		SourceBodyRenderer registered = sourceBodyRenderer;
		if (registered == null)
			throw new IllegalStateException("no source body renderer registered");
		return registered;
	}


	public record SourceReferenceScalars(String author, Integer authorUserId, String dateIso, String dateText,
			Integer threadId, Integer page, Integer sourceId, boolean permitted) {}

	private record RevisionKey(Integer sourceId, OffsetDateTime revisionCreatedTs) {}

	private record ReferenceScope(Map<Integer, ContentTagResolver.Resolved> bySourceId,
			Map<RevisionKey, String> renderedBodyHtml) {}

	private record ReferencingTag(List<BBCodeNode> siblings, int index, BBCodeTag tag, Integer sourceId,
			boolean bodyMayBeReplaced) {}

	private final ThreadLocal<ReferenceScope> referenceScope = new ThreadLocal<>();

	private final ThreadLocal<Map<Integer, ContentTagResolver.Resolved>> resolutionInFlight = new ThreadLocal<>();

	private final ThreadLocal<Boolean> insideSourceBody = ThreadLocal.withInitial(() -> Boolean.FALSE);

	public BBCodeGrammar.PreparedSourceReferences sourceReferencesPreparedFrom(Map<String, BBCodeConfig> candidateGrammar) {
		Set<String> registered = new HashSet<>();
		for (ContentTagResolver resolver : contentTagResolvers)
			registered.add(resolver.resolverCode());
		Set<String> declared = new HashSet<>();
		for (BBCodeConfig config : candidateGrammar.values())
			if (config.referencesSourceContent()) {
				if (!registered.contains(config.getSourceReferenceResolver()))
					throw InvalidBBCodeGrammarException.unknownSourceReferenceResolver(config, registered);
				declared.add(config.getSourceReferenceResolver());
			}
		if (declared.size() > 1)
			throw InvalidBBCodeGrammarException.moreThanOneSourceReferenceResolver(declared);
		return new BBCodeGrammar.PreparedSourceReferences(candidateGrammar,
				sourceReferencingMarkupCompiledFrom(candidateGrammar));
	}

	private BBCodeGrammar.PreparedSourceReferences sourceReferencesInForce() {
		return grammarHolder.current().sourceReferences();
	}

	public static final class ScopeRestore {

		private final Optional<ReferenceScope> priorScope;

		private final boolean priorInsideSourceBody;

		private ScopeRestore(Optional<ReferenceScope> priorScope, boolean priorInsideSourceBody) {
			this.priorScope = priorScope;
			this.priorInsideSourceBody = priorInsideSourceBody;
		}
	}

	public ScopeRestore openScope(Collection<QuotingPost> posts, Set<Integer> visibleBoardIds) {
		ScopeRestore restore =
				new ScopeRestore(Optional.ofNullable(referenceScope.get()), insideSourceBody.get());
		Set<Integer> topLevelIds = new LinkedHashSet<>();
		for (QuotingPost post : posts)
			topLevelIds.addAll(collectSourceReferenceIds(post.rawText()));
		Map<Integer, ContentTagResolver.Resolved> bySourceId =
				new HashMap<>(resolve(idsWithinTheScopeBudget(topLevelIds, MAX_SOURCE_REFERENCES_PER_SCOPE),
						visibleBoardIds));

		Map<RevisionKey, ContentTagResolver.SourceRevision> neededBodies = new LinkedHashMap<>();
		Set<Integer> innerIds = new LinkedHashSet<>();
		for (QuotingPost post : posts)
			if (post.createdTs() != null)
				collectTheRevisionsThisPostQuotes(post, bySourceId, neededBodies, innerIds);

		innerIds.removeAll(bySourceId.keySet());
		if (!innerIds.isEmpty())
			resolve(idsWithinTheScopeBudget(innerIds,
					MAX_SOURCE_REFERENCES_PER_SCOPE - bySourceId.size()), visibleBoardIds)
					.forEach(bySourceId::putIfAbsent);

		Map<RevisionKey, String> renderedBodyHtml = new HashMap<>();
		referenceScope.set(new ReferenceScope(bySourceId, renderedBodyHtml));
		try {
			for (Map.Entry<RevisionKey, ContentTagResolver.SourceRevision> needed : neededBodies.entrySet())
				renderedBodyHtml.put(needed.getKey(),
						sourceBodyRendered(needed.getValue(), needed.getKey().revisionCreatedTs()));
		} catch (RuntimeException | Error unrenderable) {
			closeScope(restore);
			throw unrenderable;
		}
		return restore;
	}

	private static Set<Integer> idsWithinTheScopeBudget(Set<Integer> ids, int budget) {
		if (budget <= 0)
			return Set.of();
		if (ids.size() <= budget)
			return ids;
		Set<Integer> withinBudget = new LinkedHashSet<>();
		for (Integer id : ids) {
			if (withinBudget.size() == budget)
				break;
			withinBudget.add(id);
		}
		LOGGER.warn("source references exceed the per-scope budget: resolving {} of {}", budget, ids.size());
		return withinBudget;
	}

	private void collectTheRevisionsThisPostQuotes(QuotingPost post,
			Map<Integer, ContentTagResolver.Resolved> bySourceId,
			Map<RevisionKey, ContentTagResolver.SourceRevision> neededBodies, Set<Integer> innerIds) {
		for (Integer sourceId : collectSourceReferenceIds(post.rawText())) {
			Optional<Map.Entry<OffsetDateTime, ContentTagResolver.SourceRevision>> floor =
					revisionInForceAt(Optional.ofNullable(bySourceId.get(sourceId)), post.createdTs())
							.filter(SourceReferenceService::carriesABody);
			if (floor.isEmpty())
				continue;
			RevisionKey key = new RevisionKey(sourceId, floor.get().getKey());
			if (neededBodies.containsKey(key))
				continue;
			neededBodies.put(key, floor.get().getValue());
			innerIds.addAll(collectSourceReferenceIds(floor.get().getValue().body()));
		}
	}

	public void closeScope(ScopeRestore restore) {
		restore.priorScope.ifPresentOrElse(referenceScope::set, referenceScope::remove);
		insideSourceBody.set(restore.priorInsideSourceBody);
	}

	public void resolveEverySourceReferenceIn(BBCodeNode root, OffsetDateTime quotingCreatedTs) {
		List<ReferencingTag> referencing = new ArrayList<>();
		collectEverySourceReferenceIn(root, false, referencing);
		if (referencing.isEmpty())
			return;
		Optional<ReferenceScope> scope = Optional.ofNullable(referenceScope.get());
		boolean nestedInsideASourceBody = insideSourceBody.get();
		boolean weResolveTheIdsOurselves = scope.isEmpty() && !nestedInsideASourceBody;
		Map<Integer, ContentTagResolver.Resolved> resolved;
		if (scope.isPresent())
			resolved = scope.get().bySourceId();
		else if (weResolveTheIdsOurselves)
			resolved = resolve(idsIn(referencing), null);
		else
			resolved = Optional.ofNullable(resolutionInFlight.get()).orElseGet(Map::of);
		if (weResolveTheIdsOurselves)
			resolutionInFlight.set(resolved);
		try {
			for (ReferencingTag reference : referencing)
				reference.siblings().set(reference.index(), tagWithItsSourceResolved(reference, resolved, scope,
						quotingCreatedTs, nestedInsideASourceBody));
		} finally {
			if (weResolveTheIdsOurselves)
				resolutionInFlight.remove();
		}
	}

	private BBCodeTag tagWithItsSourceResolved(ReferencingTag reference,
			Map<Integer, ContentTagResolver.Resolved> resolved, Optional<ReferenceScope> scope,
			OffsetDateTime quotingCreatedTs, boolean nestedInsideASourceBody) {
		Optional<ContentTagResolver.Resolved> source =
				Optional.ofNullable(resolved.get(reference.sourceId()));
		String header = attributionHeaderFor(reference.tag(), reference.sourceId(), source);
		if (!reference.bodyMayBeReplaced() || nestedInsideASourceBody)
			return reference.tag().withMarkupAndBodyReplacedBy(header, reference.tag().children());
		Optional<String> body = scope.isPresent()
				? bodyTheScopeSelects(reference, source, scope.get(), quotingCreatedTs)
				: bodyFromTheCurrentRevision(reference, source);
		return body
				.map(html -> reference.tag().withMarkupAndBodyReplacedBy(header,
						new ArrayList<>(List.of(BBCodeText.passThroughText(html)))))
				.orElseGet(() -> reference.tag().withMarkupAndBodyReplacedBy(header, reference.tag().children()));
	}

	private Optional<String> bodyTheScopeSelects(ReferencingTag reference,
			Optional<ContentTagResolver.Resolved> source, ReferenceScope scope, OffsetDateTime quotingCreatedTs) {
		if (quotingCreatedTs == null)
			return Optional.empty();
		Optional<Map.Entry<OffsetDateTime, ContentTagResolver.SourceRevision>> floor =
				revisionInForceAt(source, quotingCreatedTs);
		if (floor.isEmpty())
			return hasBlankAuthoredBody(reference.tag())
					? Optional.of(SOURCE_UNAVAILABLE_PLACEHOLDER)
					: Optional.empty();
		if (!carriesABody(floor.get()))
			return Optional.of(SOURCE_UNAVAILABLE_PLACEHOLDER);
		return Optional.of(Optional
				.ofNullable(scope.renderedBodyHtml().get(new RevisionKey(reference.sourceId(), floor.get().getKey())))
				.orElse(SOURCE_UNAVAILABLE_PLACEHOLDER));
	}

	private Optional<String> bodyFromTheCurrentRevision(ReferencingTag reference,
			Optional<ContentTagResolver.Resolved> source) {
		if (!hasBlankAuthoredBody(reference.tag()))
			return Optional.empty();
		Optional<Map.Entry<OffsetDateTime, ContentTagResolver.SourceRevision>> current =
				revisionInForceAt(source, OffsetDateTime.now(ZoneOffset.UTC))
						.filter(SourceReferenceService::carriesABody);
		if (current.isEmpty())
			return Optional.of(SOURCE_UNAVAILABLE_PLACEHOLDER);
		return Optional.of(sourceBodyRendered(current.get().getValue(), current.get().getKey()));
	}

	private static boolean hasBlankAuthoredBody(BBCodeTag tag) {
		for (BBCodeNode child : tag.children())
			if (!(child instanceof BBCodeText text)
					|| !BBCodeText.isBlankOnceLineBreaksAreRead(text.sourceText()))
				return false;
		return true;
	}

	private String sourceBodyRendered(ContentTagResolver.SourceRevision revision,
			OffsetDateTime revisionCreatedTs) {
		insideSourceBody.set(Boolean.TRUE);
		try {
			return registeredSourceBodyRenderer().render(revision.body(), revision.contentFormat(),
					revisionCreatedTs);
		} finally {
			insideSourceBody.set(Boolean.FALSE);
		}
	}

	private static Optional<Map.Entry<OffsetDateTime, ContentTagResolver.SourceRevision>> revisionInForceAt(
			Optional<ContentTagResolver.Resolved> source, OffsetDateTime when) {
		return source.filter(ContentTagResolver.Resolved::permitted)
				.map(ContentTagResolver.Resolved::revisionsByCreatedTs)
				.map(revisions -> revisions.floorEntry(when));
	}

	private static boolean carriesABody(Map.Entry<OffsetDateTime, ContentTagResolver.SourceRevision> revision) {
		return revision.getValue() != null && revision.getValue().body() != null
				&& !revision.getValue().body().isBlank();
	}

	private String attributionHeaderFor(BBCodeTag tag, Integer sourceId,
			Optional<ContentTagResolver.Resolved> source) {
		if (tag.attributeMode().isEmpty())
			return tag.openMarkup();
		BBCodeAttributeMode mode = tag.attributeMode().get();
		Template declaredMarkup =
				sourceReferencesInForce().markupEverySourceReferencingModeDeclares().get(mode.getOpenTag());
		if (declaredMarkup == null)
			return tag.openMarkup();
		return BBCodeParser.markupWithEveryAttributeSlotFilled(
				declaredMarkup.execute(contextTheDeclaredMarkupReads(tag, sourceId, source)), mode,
				tag.parsedAttributes());
	}

	private static Object contextTheDeclaredMarkupReads(BBCodeTag tag, Integer sourceId,
			Optional<ContentTagResolver.Resolved> resolved) {
		String slotName = tag.config().sourceReferenceSlotName();
		SourceReferenceScalars scalars = scalarsTheSourceSupplies(tag, sourceId, resolved);
		return (Mustache.CustomContext) name -> {
			if (slotName.equals(name))
				return scalars;
			return name.indexOf('.') < 0 ? "{{" + name + "}}" : null;
		};
	}

	private static SourceReferenceScalars scalarsTheSourceSupplies(BBCodeTag tag, Integer sourceId,
			Optional<ContentTagResolver.Resolved> resolved) {
		Optional<ContentTagResolver.Resolved> source = resolved.filter(ContentTagResolver.Resolved::permitted);
		Optional<BBCodeDateElement> date = source.map(ContentTagResolver.Resolved::createdTs)
				.map(BBCodeDateElement::of);
		return new SourceReferenceScalars(
				source.map(ContentTagResolver.Resolved::authorDisplayName)
						.or(() -> tag.declaredAttributeValue(AUTHOR_ATTRIBUTE_NAME))
						.map(String::trim)
						.filter(name -> !name.isEmpty())
						.orElse(null),
				source.map(ContentTagResolver.Resolved::authorUserId).orElse(null),
				date.map(BBCodeDateElement::isoTimestamp).orElse(null),
				date.flatMap(BBCodeDateElement::longFormText).orElse(null),
				source.map(ContentTagResolver.Resolved::threadId).orElse(null),
				source.map(ContentTagResolver.Resolved::page).orElse(null),
				sourceId, source.isPresent());
	}

	private static Map<String, Template> sourceReferencingMarkupCompiledFrom(
			Map<String, BBCodeConfig> candidateGrammar) {
		Map<String, Template> compiled = new HashMap<>();
		for (BBCodeConfig config : candidateGrammar.values()) {
			if (!config.referencesSourceContent())
				continue;
			String slotOpener = "{{" + config.sourceReferenceSlotName() + ".";
			for (BBCodeAttributeMode mode : config.getAttributeConfig().values())
				if (mode.getOpenTag().contains(slotOpener))
					compiled.put(mode.getOpenTag(), MARKUP_COMPILER.compile(mode.getOpenTag()));
		}
		return Map.copyOf(compiled);
	}

	private record PendingChild(List<BBCodeNode> siblings, int index, boolean insideAReplaceableBody) {}

	private void collectEverySourceReferenceIn(BBCodeNode root, boolean insideAReplaceableBody,
			List<ReferencingTag> found) {
		Deque<PendingChild> pending = new ArrayDeque<>();
		pushEveryChildInDocumentOrder(pending, root.children(), insideAReplaceableBody);
		while (!pending.isEmpty()) {
			PendingChild pendingChild = pending.pop();
			BBCodeNode child = pendingChild.siblings().get(pendingChild.index());
			boolean bodyMayBeReplaced = false;
			if (child instanceof BBCodeTag tag) {
				Optional<Integer> sourceId = sourceIdReferencedBy(tag);
				bodyMayBeReplaced = sourceId.isPresent() && !pendingChild.insideAReplaceableBody()
						&& tag.authoredSource().hasCloser();
				if (sourceId.isPresent())
					found.add(new ReferencingTag(pendingChild.siblings(), pendingChild.index(), tag, sourceId.get(),
							bodyMayBeReplaced));
			}
			pushEveryChildInDocumentOrder(pending, child.children(),
					pendingChild.insideAReplaceableBody() || bodyMayBeReplaced);
		}
	}

	private static void pushEveryChildInDocumentOrder(Deque<PendingChild> pending, List<BBCodeNode> children,
			boolean insideAReplaceableBody) {
		for (int index = children.size() - 1; index >= 0; index--)
			pending.push(new PendingChild(children, index, insideAReplaceableBody));
	}

	public Optional<Integer> sourceIdReferencedBy(BBCodeTag tag) {
		if (!tag.config().referencesSourceContent())
			return Optional.empty();
		return tag.declaredAttributeValue(tag.config().getSourceReferenceAttribute())
				.map(String::trim)
				.flatMap(SourceReferenceService::integerWrittenIn);
	}

	private static Optional<Integer> integerWrittenIn(String value) {
		try {
			return Optional.of(Integer.valueOf(value));
		} catch (NumberFormatException notAnId) {
			return Optional.empty();
		}
	}


	public Set<Integer> collectSourceReferenceIds(String input) {
		Set<Integer> ids = new LinkedHashSet<>();
		if (input == null)
			return ids;
		for (ReferencingTag reference : sourceReferencesIn(input))
			ids.add(reference.sourceId());
		return ids;
	}

	public boolean containsSourceReference(String input) {
		return input != null
				&& anySourceReferencingTagIn(BBCodeParser.parse(input, sourceReferencesInForce().grammar()));
	}

	private static boolean anySourceReferencingTagIn(BBCodeNode root) {
		for (BBCodeNode node : root.selfAndEveryDescendant())
			if (node instanceof BBCodeTag tag && tag.config().referencesSourceContent())
				return true;
		return false;
	}

	@FunctionalInterface
	public interface SourceBodyRewriter {
		String rewrite(Integer sourceId, String body);
	}

	public String rewriteSourceReferenceBodies(String input, SourceBodyRewriter rewriter) {
		StringBuilder rewritten = new StringBuilder(input.length());
		int copiedUpTo = 0;
		for (ReferencingTag reference : sourceReferencesIn(input)) {
			if (!reference.bodyMayBeReplaced())
				continue;
			AuthoredSource authored = reference.tag().authoredSource();
			rewritten.append(input, copiedUpTo, authored.bodyStartIndex());
			rewritten.append(rewriter.rewrite(reference.sourceId(),
					input.substring(authored.bodyStartIndex(), authored.bodyEndIndex())));
			rewritten.append(input, authored.bodyEndIndex(), authored.endIndex());
			copiedUpTo = authored.endIndex();
		}
		rewritten.append(input, copiedUpTo, input.length());
		return rewritten.toString();
	}

	private List<ReferencingTag> sourceReferencesIn(String input) {
		List<ReferencingTag> found = new ArrayList<>();
		collectEverySourceReferenceIn(BBCodeParser.parse(input, sourceReferencesInForce().grammar()), false, found);
		return found;
	}

	private static Set<Integer> idsIn(List<ReferencingTag> referencing) {
		Set<Integer> ids = new HashSet<>();
		for (ReferencingTag reference : referencing)
			ids.add(reference.sourceId());
		return ids;
	}

	public Map<Integer, ContentTagResolver.Resolved> resolve(Set<Integer> sourceIds, Set<Integer> visibleBoardIds) {
		if (sourceIds.isEmpty())
			return Map.of();
		return declaredResolverCode()
				.flatMap(this::registeredResolverNamed)
				.map(resolver -> resolver.resolve(sourceIds, visibleBoardIds))
				.orElseGet(Map::of);
	}

	private Optional<String> declaredResolverCode() {
		for (BBCodeConfig config : sourceReferencesInForce().grammar().values())
			if (config.referencesSourceContent())
				return Optional.of(config.getSourceReferenceResolver());
		return Optional.empty();
	}

	private Optional<ContentTagResolver> registeredResolverNamed(String resolverCode) {
		for (ContentTagResolver resolver : contentTagResolvers)
			if (resolver.resolverCode().equals(resolverCode))
				return Optional.of(resolver);
		return Optional.empty();
	}
}
