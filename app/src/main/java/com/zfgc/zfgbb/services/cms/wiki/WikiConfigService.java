package com.zfgc.zfgbb.services.cms.wiki;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Stream;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.zfgc.zfgbb.dataprovider.cms.WikiDataProvider;
import com.zfgc.zfgbb.dataprovider.cms.WikiNamespaceDataProvider;
import com.zfgc.zfgbb.model.cms.WikiConfig;
import com.zfgc.zfgbb.model.cms.WikiConfig.NavItem;
import com.zfgc.zfgbb.model.cms.WikiConfig.NavSection;
import com.zfgc.zfgbb.model.cms.WikiPage;
import com.zfgc.zfgbb.model.forum.AttributeSemanticRole;
import com.zfgc.zfgbb.services.system.SystemConfigService;
import com.zfgc.zfgbb.content.renderer.ContentOutputSanitizer;
import com.zfgc.zfgbb.content.renderer.bbcode.BBCodeGrammarHolder;
import com.zfgc.zfgbb.content.renderer.bbcode.BBCodeNode;
import com.zfgc.zfgbb.content.renderer.bbcode.BBCodeParser;
import com.zfgc.zfgbb.content.renderer.bbcode.BBCodeTag;
import com.zfgc.zfgbb.content.renderer.bbcode.BBCodeText;
import com.zfgc.zfgbb.wiki.WikiNamespaceRole;

@Service
@Slf4j
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class WikiConfigService {

	private static final String WIKI_LEGACY_HOST = "wiki_legacy_host";

	private static final Map<String, String> TARGET_MAGIC = Map.of(
			"mainpage", "/wiki/Main_Page",
			"helppage", "/wiki/Help:Contents",
			"recentchanges-url", "/wiki/special/recentchanges",
			"randompage-url", "/wiki/special/random");

	private static final Map<String, String> LABEL_MAGIC = Map.of(
			"mainpage-description", "Main page",
			"portal", "Community portal",
			"help", "Help",
			"recentchanges", "Recent changes",
			"randompage", "Random page");


	private static final Map<String, String> LEGACY_PATH_ROUTES = Map.of(
			"", "/",
			"/", "/",
			"/index.php", "/",
			"/index.php/projects", "/content/projects",
			"/index.php/resources", "/content/resources",
			"/forum/index.php", "/forum");

	private static final String LIST_CODE = "list";

	private static final String LIST_ITEM_CODE = "li";

	private static final String URL_CODE = "url";

	private final WikiDataProvider wikiDataProvider;
	private final WikiNamespaceDataProvider namespaceData;
	private final SystemConfigService systemConfigService;
	private final BBCodeGrammarHolder bbCodeGrammarHolder;

	public WikiConfig getConfig() {
		return new WikiConfig(resolveSiteName(), wikiDataProvider.getNamespaces(), parseNav());
	}

	private String resolveSiteName() {
		return Optional.ofNullable(systemConfigService.get(SystemConfigService.Keys.SITE_NAME))
				.filter(siteName -> !siteName.isBlank())
				.orElse("Wiki");
	}

	private List<NavSection> parseNav() {
		Optional<String> sidebar = wikiDataProvider.getWikiPageQuietly(sidebarSlug()).map(WikiPage::getContent);
		if (sidebar.isEmpty())
			return List.of();
		List<NavSection> sections = new ArrayList<>();
		collectSections(BBCodeParser.parse(sidebar.get(), bbCodeGrammarHolder.current().configs()), sections);
		return sections;
	}

	private void collectSections(BBCodeNode node, List<NavSection> sections) {
		for (BBCodeNode child : node.children()) {
			Optional<BBCodeTag> entries = child instanceof BBCodeTag heading && hasCode(heading, LIST_ITEM_CODE)
					? childTagsWithCode(heading, LIST_CODE).findFirst()
					: Optional.empty();
			if (entries.isEmpty()) {
				collectSections(child, sections);
				continue;
			}
			List<NavItem> items = childTagsWithCode(entries.get(), LIST_ITEM_CODE)
					.map(this::parseItem).flatMap(Optional::stream).toList();
			if (!items.isEmpty())
				sections.add(new NavSection(headingBefore(child, entries.get()), items));
		}
	}

	private record NavEntry(String target, Optional<String> label, Optional<String> linkedUrl) {}

	private Optional<NavItem> parseItem(BBCodeTag item) {
		StringBuilder authored = new StringBuilder();
		Optional<String> linkedUrl = Optional.empty();
		for (BBCodeNode node : item.selfAndEveryDescendant()) {
			if (node instanceof BBCodeTag link && hasCode(link, URL_CODE)
					&& linkedUrl.isEmpty() && authored.indexOf("|") < 0)
				linkedUrl = link.valueWithRole(AttributeSemanticRole.DESTINATION);
			if (node instanceof BBCodeText text)
				authored.append(BBCodeText.lineBreakMarkupAsNewlines(text.sourceText()));
		}
		int pipe = authored.indexOf("|");
		NavEntry entry = new NavEntry(
				(pipe < 0 ? authored.toString() : authored.substring(0, pipe)).trim(),
				pipe < 0 ? Optional.empty() : Optional.of(authored.substring(pipe + 1).trim()),
				linkedUrl);
		if (entry.target().isEmpty() && entry.linkedUrl().isEmpty())
			return Optional.empty();
		return resolveTarget(entry).map(resolvedTarget -> new NavItem(resolveLabel(entry), resolvedTarget));
	}

	private Optional<String> resolveTarget(NavEntry entry) {
		return routeFor(entry).flatMap(ContentOutputSanitizer::safeHrefFor);
	}

	private Optional<String> routeFor(NavEntry entry) {
		if (entry.linkedUrl().isPresent())
			return rewriteLegacyUrl(entry.linkedUrl().get());
		String target = entry.target();
		if (target.startsWith("http://") || target.startsWith("https://")) {
			return rewriteLegacyUrl(target);
		}
		if ("portal-url".equals(target))
			return Optional.of("/wiki/" + metaNamespace() + ":Community_portal");
		return Optional.of(TARGET_MAGIC.getOrDefault(target,
				ContentOutputSanitizer.isSafeRelativeUrl(target) ? target : "/wiki/" + target));
	}

	private static boolean hasCode(BBCodeTag tag, String code) {
		return code.equalsIgnoreCase(tag.config().getCode());
	}

	private static Stream<BBCodeTag> childTagsWithCode(BBCodeTag parent, String code) {
		return parent.children().stream()
				.filter(BBCodeTag.class::isInstance).map(BBCodeTag.class::cast)
				.filter(child -> hasCode(child, code));
	}

	private static String headingBefore(BBCodeNode heading, BBCodeTag entries) {
		StringBuilder title = new StringBuilder();
		for (BBCodeNode child : heading.children()) {
			if (child == entries)
				break;
			for (BBCodeNode descendant : child.selfAndEveryDescendant())
				if (descendant instanceof BBCodeText text)
					title.append(BBCodeText.lineBreakMarkupAsNewlines(text.sourceText()));
		}
		return title.toString().trim();
	}

	private String sidebarSlug() {
		return namespaceData.nameForRole(WikiNamespaceRole.MEDIAWIKI).orElse("MediaWiki") + ":Sidebar";
	}

	private String metaNamespace() {
		return namespaceData.nameForRole(WikiNamespaceRole.META).orElse("Meta");
	}

	private Optional<String> rewriteLegacyUrl(String url) {
		Optional<String> legacyPath = legacyPathOf(url);
		if (legacyPath.isEmpty())
			return Optional.of(url);
		String path = legacyPath.get();
		while (path.length() > 1 && path.endsWith("/"))
			path = path.substring(0, path.length() - 1);
		Optional<String> rewritten = Optional.ofNullable(LEGACY_PATH_ROUTES.get(path));
		if (rewritten.isEmpty())
			log.debug("dropping wiki nav entry '{}': no app route maps '{}'", url, path);
		return rewritten;
	}

	private Optional<String> legacyPathOf(String url) {
		Optional<String> legacyHost = Optional.ofNullable(systemConfigService.get(WIKI_LEGACY_HOST))
				.map(String::trim)
				.filter(host -> !host.isEmpty());
		if (legacyHost.isEmpty())
			return Optional.empty();
		URI parsed;
		try {
			parsed = new URI(url);
		}
		catch (URISyntaxException notAUrl) {
			return Optional.empty();
		}
		if (!"http".equalsIgnoreCase(parsed.getScheme()) && !"https".equalsIgnoreCase(parsed.getScheme()))
			return Optional.empty();
		String host = parsed.getHost() == null ? "" : parsed.getHost();
		if (!host.equalsIgnoreCase(legacyHost.get()) && !host.equalsIgnoreCase("www." + legacyHost.get()))
			return Optional.empty();
		return Optional.of((parsed.getRawPath() == null ? "" : parsed.getRawPath())
				+ (parsed.getRawQuery() == null ? "" : "?" + parsed.getRawQuery())
				+ (parsed.getRawFragment() == null ? "" : "#" + parsed.getRawFragment()));
	}

	private static String resolveLabel(NavEntry entry) {
		String target = entry.target();
		String unprefixedTarget = target.contains(":") ? target.substring(target.indexOf(':') + 1) : target;
		return entry.label().filter(text -> !text.isEmpty())
				.map(text -> LABEL_MAGIC.getOrDefault(text, text))
				.orElseGet(() -> LABEL_MAGIC.getOrDefault(target, unprefixedTarget.replace('_', ' ').trim()));
	}
}
