package com.zfgc.zfgbb.services.cms.wiki;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

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
import com.zfgc.zfgbb.services.system.SystemConfigService;
import com.zfgc.zfgbb.content.renderer.LinkPolicy;
import com.zfgc.zfgbb.wiki.WikiNamespaceRole;

@Service
@Slf4j
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class WikiConfigService {

	private final Set<String> unmappedLegacyPaths = ConcurrentHashMap.newKeySet();

	private volatile LegacyHostCache legacyHostCache;

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
			"/index.php", "/",
			"/index.php/projects", "/content/projects",
			"/index.php/resources", "/content/resources",
			"/forum/index.php", "/forum");

	private static final Pattern SECTION = Pattern.compile(
			"\\[li\\]([^\\[]+?)\\[list\\](.*?)\\[/list\\]\\s*\\[/li\\]", Pattern.DOTALL);
	private static final Pattern ITEM = Pattern.compile("\\[li\\](.*?)\\[/li\\]", Pattern.DOTALL);
	private static final Pattern URL_BBCODE = Pattern.compile("\\[url=([^\\]]+)\\].*?\\[/url\\]", Pattern.DOTALL);

	private final WikiDataProvider wikiDataProvider;
	private final WikiNamespaceDataProvider namespaceData;
	private final SystemConfigService systemConfigService;

	public WikiConfig getConfig() {
		return new WikiConfig(resolveSiteName(), wikiDataProvider.getNamespaces(), parseNav());
	}

	private String resolveSiteName() {
		String siteName = systemConfigService.get(SystemConfigService.Keys.SITE_NAME);
		return siteName != null && !siteName.isBlank() ? siteName : "Wiki";
	}

	private List<NavSection> parseNav() {
		String content = wikiDataProvider.getWikiPageQuietly(sidebarSlug()).map(WikiPage::getContent).orElse(null);
		if (content == null)
			return List.of();
		List<NavSection> sections = new ArrayList<>();
		Matcher sectionMatcher = SECTION.matcher(content);
		while (sectionMatcher.find()) {
			String title = sectionMatcher.group(1).trim();
			List<NavItem> items = new ArrayList<>();
			Matcher itemMatcher = ITEM.matcher(sectionMatcher.group(2));
			while (itemMatcher.find()) {
				parseItem(itemMatcher.group(1).trim()).ifPresent(items::add);
			}
			if (!items.isEmpty()) {
				sections.add(new NavSection(title, items));
			}
		}
		return sections;
	}

	private Optional<NavItem> parseItem(String raw) {
		if (raw.isEmpty()) {
			return Optional.empty();
		}
		int pipe = raw.indexOf('|');
		String target = pipe >= 0 ? raw.substring(0, pipe).trim() : raw;
		String label = pipe >= 0 ? raw.substring(pipe + 1).trim() : null;

		return resolveTarget(target)
				.map(resolvedTarget -> new NavItem(resolveLabel(label, target), resolvedTarget));
	}

	private Optional<String> resolveTarget(String target) {
		Matcher urlMatcher = URL_BBCODE.matcher(target);
		if (urlMatcher.find()) {
			return rewriteLegacyUrl(urlMatcher.group(1).trim());
		}
		if (target.startsWith("http://") || target.startsWith("https://")) {
			return rewriteLegacyUrl(target);
		}
		if ("portal-url".equals(target))
			return Optional.of("/wiki/" + metaNamespace() + ":Community_portal");
		String magic = TARGET_MAGIC.get(target);
		if (magic != null) {
			return Optional.of(magic);
		}
		if (LinkPolicy.isSafeRelativeUrl(target)) {
			return Optional.of(target);
		}
		return Optional.of("/wiki/" + target);
	}

	private String sidebarSlug() {
		return namespaceData.nameForRole(WikiNamespaceRole.MEDIAWIKI).orElse("MediaWiki") + ":Sidebar";
	}

	private String metaNamespace() {
		return namespaceData.nameForRole(WikiNamespaceRole.META).orElse("Meta");
	}

	private record LegacyHostCache(String host, Pattern pattern) {
	}

	private Optional<Pattern> legacyHostPattern() {
		String configuredHost = systemConfigService.get(WIKI_LEGACY_HOST);
		String host = configuredHost == null ? "" : configuredHost.trim();
		if (host.isEmpty())
			return Optional.empty();
		LegacyHostCache cached = legacyHostCache;
		if (cached != null && cached.host().equals(host))
			return Optional.of(cached.pattern());
		LegacyHostCache compiled = new LegacyHostCache(host,
				Pattern.compile("^https?://(?:www\\.)?" + Pattern.quote(host) + "(/.*)?$",
						Pattern.CASE_INSENSITIVE));
		legacyHostCache = compiled;
		return Optional.of(compiled.pattern());
	}

	private Optional<String> rewriteLegacyUrl(String url) {
		Pattern legacyHost = legacyHostPattern().orElse(null);
		if (legacyHost == null)
			return Optional.of(url);
		Matcher hostMatcher = legacyHost.matcher(url);
		if (!hostMatcher.matches())
			return Optional.of(url);
		String path = hostMatcher.group(1) == null ? "" : hostMatcher.group(1);
		while (path.length() > 1 && path.endsWith("/"))
			path = path.substring(0, path.length() - 1);
		String rewritten = LEGACY_PATH_ROUTES.get("/".equals(path) ? "" : path);
		if (rewritten != null)
			return Optional.of(rewritten);
		if (unmappedLegacyPaths.add(path))
			log.warn("dropping wiki nav entry '{}': no app route maps '{}'", url, path);
		return Optional.empty();
	}

	private String resolveLabel(String label, String target) {
		if (label != null && !label.isEmpty()) {
			String magic = LABEL_MAGIC.get(label);
			return magic != null ? magic : label;
		}
		String targetMagic = LABEL_MAGIC.get(target);
		if (targetMagic != null) {
			return targetMagic;
		}
		String name = target.contains(":") ? target.substring(target.indexOf(':') + 1) : target;
		return name.replace('_', ' ').trim();
	}
}
