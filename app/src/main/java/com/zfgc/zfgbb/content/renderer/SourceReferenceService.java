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
import java.util.NavigableMap;
import java.util.Optional;
import java.util.Set;
import java.util.TreeMap;

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

	static final int MAX_SOURCE_EXPANSION_DEPTH = 1;

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

	private SourceBodyRenderer theRegisteredSourceBodyRenderer() {
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
			boolean itsBodyMayBeReplaced) {}

	private final ThreadLocal<ReferenceScope> referenceScope = new ThreadLocal<>();

	private final ThreadLocal<Map<Integer, ContentTagResolver.Resolved>> resolutionInFlight = new ThreadLocal<>();

	private final ThreadLocal<Integer> expansionDepth = ThreadLocal.withInitial(() -> 0);

	public BBCodeGrammar.PreparedSourceReferences theSourceReferencesPreparedFrom(Map<String, BBCodeConfig> candidateGrammar) {
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
				theSourceReferencingMarkupCompiledFrom(candidateGrammar));
	}

	private BBCodeGrammar.PreparedSourceReferences theSourceReferencesInForce() {
		return grammarHolder.current().sourceReferences();
	}

	public static final class ScopeRestore {

		private final ReferenceScope priorScope;

		private final int priorExpansionDepth;

		private ScopeRestore(ReferenceScope priorScope, int priorExpansionDepth) {
			this.priorScope = priorScope;
			this.priorExpansionDepth = priorExpansionDepth;
		}
	}

	public ScopeRestore openScope(Collection<QuotingPost> posts, Set<Integer> visibleBoardIds) {
		ScopeRestore restore = new ScopeRestore(referenceScope.get(), expansionDepth.get());
		Set<Integer> topLevelIds = new LinkedHashSet<>();
		if (posts != null)
			for (QuotingPost post : posts)
				topLevelIds.addAll(collectSourceReferenceIds(post.rawText()));
		Map<Integer, ContentTagResolver.Resolved> bySourceId =
				new HashMap<>(resolve(theIdsWithinTheScopeBudget(topLevelIds, MAX_SOURCE_REFERENCES_PER_SCOPE),
						visibleBoardIds));

		Map<RevisionKey, ContentTagResolver.SourceRevision> neededBodies = new LinkedHashMap<>();
		Set<Integer> innerIds = new LinkedHashSet<>();
		if (posts != null)
			for (QuotingPost post : posts)
				if (post.createdTs() != null)
					collectTheRevisionsThisPostQuotes(post, bySourceId, neededBodies, innerIds);

		innerIds.removeAll(bySourceId.keySet());
		if (!innerIds.isEmpty())
			resolve(theIdsWithinTheScopeBudget(innerIds,
					MAX_SOURCE_REFERENCES_PER_SCOPE - bySourceId.size()), visibleBoardIds)
					.forEach(bySourceId::putIfAbsent);

		Map<RevisionKey, String> renderedBodyHtml = new HashMap<>();
		referenceScope.set(new ReferenceScope(bySourceId, renderedBodyHtml));
		try {
			for (Map.Entry<RevisionKey, ContentTagResolver.SourceRevision> needed : neededBodies.entrySet())
				renderedBodyHtml.put(needed.getKey(),
						theSourceBodyRendered(needed.getValue(), needed.getKey().revisionCreatedTs()));
		} catch (RuntimeException | Error unrenderable) {
			closeScope(restore);
			throw unrenderable;
		}
		return restore;
	}

	private static Set<Integer> theIdsWithinTheScopeBudget(Set<Integer> ids, int budget) {
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
		LOGGER.warn("source references on this page exceed the per-scope budget: resolving {} of {}; the rest "
				+ "render as unresolved", budget, ids.size());
		return withinBudget;
	}

	private void collectTheRevisionsThisPostQuotes(QuotingPost post,
			Map<Integer, ContentTagResolver.Resolved> bySourceId,
			Map<RevisionKey, ContentTagResolver.SourceRevision> neededBodies, Set<Integer> innerIds) {
		for (Integer sourceId : collectSourceReferenceIds(post.rawText())) {
			Map.Entry<OffsetDateTime, ContentTagResolver.SourceRevision> floor =
					theRevisionInForceAt(bySourceId.get(sourceId), post.createdTs());
			if (floor == null || floor.getValue() == null || floor.getValue().body() == null
					|| floor.getValue().body().isBlank())
				continue;
			RevisionKey key = new RevisionKey(sourceId, floor.getKey());
			if (neededBodies.containsKey(key))
				continue;
			neededBodies.put(key, floor.getValue());
			innerIds.addAll(collectSourceReferenceIds(floor.getValue().body()));
		}
	}

	public void closeScope(ScopeRestore restore) {
		if (restore == null || restore.priorScope == null)
			referenceScope.remove();
		else
			referenceScope.set(restore.priorScope);
		if (restore == null)
			expansionDepth.remove();
		else
			expansionDepth.set(restore.priorExpansionDepth);
	}

	public void resolveEverySourceReferenceIn(BBCodeNode root, OffsetDateTime quotingCreatedTs) {
		List<ReferencingTag> referencing = new ArrayList<>();
		collectEverySourceReferenceIn(root, false, referencing);
		if (referencing.isEmpty())
			return;
		ReferenceScope scope = referenceScope.get();
		boolean nestedInsideASourceBody = expansionDepth.get() >= MAX_SOURCE_EXPANSION_DEPTH;
		boolean weResolveTheIdsOurselves = scope == null && !nestedInsideASourceBody;
		Map<Integer, ContentTagResolver.Resolved> resolved = scope != null
				? scope.bySourceId()
				: weResolveTheIdsOurselves
						? resolve(everyIdIn(referencing), null)
						: Optional.ofNullable(resolutionInFlight.get()).orElseGet(Map::of);
		if (weResolveTheIdsOurselves)
			resolutionInFlight.set(resolved);
		try {
			for (ReferencingTag each : referencing)
				each.siblings().set(each.index(), theTagWithItsSourceResolved(each, resolved, scope,
						quotingCreatedTs, nestedInsideASourceBody));
		} finally {
			if (weResolveTheIdsOurselves)
				resolutionInFlight.remove();
		}
	}

	private BBCodeTag theTagWithItsSourceResolved(ReferencingTag each,
			Map<Integer, ContentTagResolver.Resolved> resolved, ReferenceScope scope,
			OffsetDateTime quotingCreatedTs, boolean nestedInsideASourceBody) {
		ContentTagResolver.Resolved source = resolved.get(each.sourceId());
		String header = itsAttributionHeader(each.tag(), each.sourceId(), source);
		if (!each.itsBodyMayBeReplaced() || nestedInsideASourceBody)
			return each.tag().itsMarkupAndBodyReplacedBy(header, each.tag().children());
		Optional<String> body = scope != null
				? theBodyTheScopeSelects(each, source, scope, quotingCreatedTs)
				: theBodyTheCurrentRevisionSupplies(each, source);
		return body
				.map(html -> each.tag().itsMarkupAndBodyReplacedBy(header,
						new ArrayList<>(List.of(BBCodeText.passThroughText(html)))))
				.orElseGet(() -> each.tag().itsMarkupAndBodyReplacedBy(header, each.tag().children()));
	}

	private Optional<String> theBodyTheScopeSelects(ReferencingTag each, ContentTagResolver.Resolved source,
			ReferenceScope scope, OffsetDateTime quotingCreatedTs) {
		if (quotingCreatedTs == null)
			return Optional.empty();
		Map.Entry<OffsetDateTime, ContentTagResolver.SourceRevision> floor =
				theRevisionInForceAt(source, quotingCreatedTs);
		if (floor == null)
			return itsAuthoredBodyIsBlank(each.tag())
					? Optional.of(SOURCE_UNAVAILABLE_PLACEHOLDER)
					: Optional.empty();
		if (floor.getValue() == null || floor.getValue().body() == null || floor.getValue().body().isBlank())
			return Optional.of(SOURCE_UNAVAILABLE_PLACEHOLDER);
		return Optional.of(Optional
				.ofNullable(scope.renderedBodyHtml().get(new RevisionKey(each.sourceId(), floor.getKey())))
				.orElseGet(() -> SOURCE_UNAVAILABLE_PLACEHOLDER));
	}

	private Optional<String> theBodyTheCurrentRevisionSupplies(ReferencingTag each,
			ContentTagResolver.Resolved source) {
		if (!itsAuthoredBodyIsBlank(each.tag()))
			return Optional.empty();
		Map.Entry<OffsetDateTime, ContentTagResolver.SourceRevision> current =
				theRevisionInForceAt(source, OffsetDateTime.now(ZoneOffset.UTC));
		if (current == null || current.getValue() == null || current.getValue().body() == null
				|| current.getValue().body().isBlank())
			return Optional.of(SOURCE_UNAVAILABLE_PLACEHOLDER);
		return Optional.of(theSourceBodyRendered(current.getValue(), current.getKey()));
	}

	private boolean itsAuthoredBodyIsBlank(BBCodeTag tag) {
		for (BBCodeNode child : tag.children())
			if (!(child instanceof BBCodeText text)
					|| !BBCodeText.everyCharacterIsBlankOnceItsLineBreaksAreRead(text.sourceText()))
				return false;
		return true;
	}

	private String theSourceBodyRendered(ContentTagResolver.SourceRevision revision,
			OffsetDateTime revisionCreatedTs) {
		expansionDepth.set(MAX_SOURCE_EXPANSION_DEPTH);
		try {
			return theRegisteredSourceBodyRenderer().render(revision.body(), revision.contentFormat(),
					revisionCreatedTs);
		} finally {
			expansionDepth.set(0);
		}
	}

	private Map.Entry<OffsetDateTime, ContentTagResolver.SourceRevision> theRevisionInForceAt(
			ContentTagResolver.Resolved source, OffsetDateTime when) {
		NavigableMap<OffsetDateTime, ContentTagResolver.SourceRevision> revisions =
				source != null && source.permitted() ? source.revisionsByCreatedTs() : null;
		return revisions == null ? null : revisions.floorEntry(when);
	}

	private String itsAttributionHeader(BBCodeTag tag, Integer sourceId,
			ContentTagResolver.Resolved source) {
		if (tag.attributeMode().isEmpty())
			return tag.openMarkup();
		BBCodeAttributeMode mode = tag.attributeMode().get();
		Template declaredMarkup =
				theSourceReferencesInForce().theMarkupEverySourceReferencingModeDeclares().get(mode.getOpenTag());
		if (declaredMarkup == null)
			return tag.openMarkup();
		return BBCodeParser.theMarkupWithEveryAttributeSlotFilled(
				declaredMarkup.execute(theContextTheDeclaredMarkupReads(tag, sourceId, source)), mode,
				tag.parsedAttributes());
	}

	private Object theContextTheDeclaredMarkupReads(BBCodeTag tag, Integer sourceId,
			ContentTagResolver.Resolved resolved) {
		String slotName = tag.config().theNameItsSourceReferenceSlotsUse();
		SourceReferenceScalars scalars = theScalarsTheSourceSupplies(tag, sourceId, resolved);
		return (Mustache.CustomContext) name -> {
			if (slotName.equals(name))
				return scalars;
			return name.indexOf('.') < 0 ? "{{" + name + "}}" : null;
		};
	}

	private SourceReferenceScalars theScalarsTheSourceSupplies(BBCodeTag tag, Integer sourceId,
			ContentTagResolver.Resolved resolved) {
		Optional<ContentTagResolver.Resolved> source =
				Optional.ofNullable(resolved).filter(ContentTagResolver.Resolved::permitted);
		Optional<BBCodeDateElement> date = source.map(ContentTagResolver.Resolved::createdTs)
				.map(BBCodeDateElement::of);
		return new SourceReferenceScalars(
				source.map(ContentTagResolver.Resolved::authorDisplayName)
						.or(() -> tag.theValueOfTheDeclaredAttribute(AUTHOR_ATTRIBUTE_NAME))
						.map(String::trim)
						.filter(name -> !name.isEmpty())
						.orElse(null),
				source.map(ContentTagResolver.Resolved::authorUserId).orElse(null),
				date.map(BBCodeDateElement::isoTimestamp).orElse(null),
				date.flatMap(BBCodeDateElement::itsLongFormText).orElse(null),
				source.map(ContentTagResolver.Resolved::threadId).orElse(null),
				source.map(ContentTagResolver.Resolved::page).orElse(null),
				sourceId, source.isPresent());
	}

	private static Map<String, Template> theSourceReferencingMarkupCompiledFrom(
			Map<String, BBCodeConfig> candidateGrammar) {
		Map<String, Template> compiled = new HashMap<>();
		for (BBCodeConfig config : candidateGrammar.values()) {
			if (!config.referencesSourceContent())
				continue;
			String slotOpener = "{{" + config.theNameItsSourceReferenceSlotsUse() + ".";
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
			PendingChild each = pending.pop();
			BBCodeNode child = each.siblings().get(each.index());
			boolean itsBodyMayBeReplaced = false;
			if (child instanceof BBCodeTag tag) {
				Optional<Integer> sourceId = theSourceIdReferencedBy(tag);
				itsBodyMayBeReplaced = sourceId.isPresent() && !each.insideAReplaceableBody()
						&& tag.authoredSource().itsAuthorWroteACloser();
				if (sourceId.isPresent())
					found.add(new ReferencingTag(each.siblings(), each.index(), tag, sourceId.get(),
							itsBodyMayBeReplaced));
			}
			pushEveryChildInDocumentOrder(pending, child.children(),
					each.insideAReplaceableBody() || itsBodyMayBeReplaced);
		}
	}

	private void pushEveryChildInDocumentOrder(Deque<PendingChild> pending, List<BBCodeNode> children,
			boolean insideAReplaceableBody) {
		for (int index = children.size() - 1; index >= 0; index--)
			pending.push(new PendingChild(children, index, insideAReplaceableBody));
	}

	public Optional<Integer> theSourceIdReferencedBy(BBCodeTag tag) {
		if (!tag.config().referencesSourceContent())
			return Optional.empty();
		return tag.theValueOfTheDeclaredAttribute(tag.config().getSourceReferenceAttribute())
				.map(String::trim)
				.flatMap(this::theIntegerWrittenIn);
	}

	private Optional<Integer> theIntegerWrittenIn(String value) {
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
		for (ReferencingTag each : everySourceReferenceIn(input))
			ids.add(each.sourceId());
		return ids;
	}

	public boolean containsSourceReference(String input) {
		return input != null
				&& anySourceReferencingTagIn(BBCodeParser.parse(input, theSourceReferencesInForce().grammar()));
	}

	private boolean anySourceReferencingTagIn(BBCodeNode root) {
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
		for (ReferencingTag each : everySourceReferenceIn(input)) {
			if (!each.itsBodyMayBeReplaced())
				continue;
			AuthoredSource authored = each.tag().authoredSource();
			rewritten.append(input, copiedUpTo, authored.bodyStartIndex());
			rewritten.append(rewriter.rewrite(each.sourceId(),
					input.substring(authored.bodyStartIndex(), authored.bodyEndIndex())));
			rewritten.append(input, authored.bodyEndIndex(), authored.endIndex());
			copiedUpTo = authored.endIndex();
		}
		rewritten.append(input, copiedUpTo, input.length());
		return rewritten.toString();
	}

	private List<ReferencingTag> everySourceReferenceIn(String input) {
		List<ReferencingTag> found = new ArrayList<>();
		collectEverySourceReferenceIn(BBCodeParser.parse(input, theSourceReferencesInForce().grammar()), false, found);
		return found;
	}

	private Set<Integer> everyIdIn(List<ReferencingTag> referencing) {
		Set<Integer> ids = new HashSet<>();
		for (ReferencingTag each : referencing)
			ids.add(each.sourceId());
		return ids;
	}

	public Map<Integer, ContentTagResolver.Resolved> resolve(Set<Integer> sourceIds, Set<Integer> visibleBoardIds) {
		if (sourceIds.isEmpty())
			return Map.of();
		Map<Integer, ContentTagResolver.Resolved> resolved = new HashMap<>();
		for (Map.Entry<String, Set<Integer>> batch : theIdsGroupedByTheirResolver(sourceIds).entrySet())
			theRegisteredResolverNamed(batch.getKey())
					.ifPresent(resolver -> resolved.putAll(resolver.resolve(batch.getValue(), visibleBoardIds)));
		return resolved;
	}

	private Map<String, Set<Integer>> theIdsGroupedByTheirResolver(Set<Integer> sourceIds) {
		Map<String, Set<Integer>> byResolverCode = new TreeMap<>();
		for (BBCodeConfig config : theSourceReferencesInForce().grammar().values())
			if (config.referencesSourceContent())
				byResolverCode.computeIfAbsent(config.getSourceReferenceResolver(), code -> new HashSet<>())
						.addAll(sourceIds);
		return byResolverCode;
	}

	private Optional<ContentTagResolver> theRegisteredResolverNamed(String resolverCode) {
		for (ContentTagResolver resolver : contentTagResolvers)
			if (resolver.resolverCode().equals(resolverCode))
				return Optional.of(resolver);
		return Optional.empty();
	}
}
