package com.zfgc.zfgbb.services.cms;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.zfgc.zfgbb.dataprovider.cms.WikiDataProvider;
import com.zfgc.zfgbb.model.cms.WikiConfig;
import com.zfgc.zfgbb.model.cms.WikiConfig.NavItem;
import com.zfgc.zfgbb.model.cms.WikiConfig.NavSection;
import com.zfgc.zfgbb.model.cms.WikiPage;
import com.zfgc.zfgbb.services.system.SystemConfigService;

@Service
@Transactional(readOnly = true)
public class WikiConfigService {

	private static final String SIDEBAR_SLUG = "MediaWiki:Sidebar";
	private static final String WIKI_SITE_NAME = "wiki_site_name";

	private static final Map<String, String> TARGET_MAGIC = Map.of(
			"mainpage", "/wiki/Main_Page",
			"portal-url", "/wiki/Project:Community_portal",
			"helppage", "/wiki/Help:Contents",
			"recentchanges-url", "/wiki/special/recentchanges",
			"randompage-url", "/wiki/special/random");

	private static final Map<String, String> LABEL_MAGIC = Map.of(
			"mainpage-description", "Main page",
			"portal", "Community portal",
			"help", "Help",
			"recentchanges", "Recent changes",
			"randompage", "Random page");

	private static final Map<String, String> LEGACY_URLS = new LinkedHashMap<>();
	static {
		LEGACY_URLS.put("http://zfgc.com/index.php/projects/", "/content/projects");
		LEGACY_URLS.put("http://zfgc.com/index.php/resources/", "/content/resources");
		LEGACY_URLS.put("http://zfgc.com/index.php/chat", "/chat");
		LEGACY_URLS.put("http://zfgc.com/forum/index.php", "/forum");
		LEGACY_URLS.put("http://zfgc.com/index.php/", "/");
	}

	private static final Pattern SECTION = Pattern.compile(
			"\\[li\\]([^\\[]+?)\\[list\\](.*?)\\[/list\\]\\s*\\[/li\\]", Pattern.DOTALL);
	private static final Pattern ITEM = Pattern.compile("\\[li\\](.*?)\\[/li\\]", Pattern.DOTALL);
	private static final Pattern URL_BBCODE = Pattern.compile("\\[url=([^\\]]+)\\].*?\\[/url\\]", Pattern.DOTALL);

	@Autowired
	private WikiDataProvider wikiDataProvider;

	@Autowired
	private SystemConfigService systemConfigService;

	public WikiConfig getConfig() {
		return new WikiConfig(resolveSiteName(), wikiDataProvider.getNamespaces(), parseNav());
	}

	private String resolveSiteName() {
		String wikiName = systemConfigService.get(WIKI_SITE_NAME);
		if (wikiName != null && !wikiName.isBlank()) {
			return wikiName;
		}
		String siteName = systemConfigService.get(SystemConfigService.Keys.SITE_NAME);
		return siteName != null && !siteName.isBlank() ? siteName : "Wiki";
	}

	private List<NavSection> parseNav() {
		String content = wikiDataProvider.getWikiPageQuietly(SIDEBAR_SLUG).map(WikiPage::getContent).orElse(null);
		if (content == null)
			return List.of();
		List<NavSection> sections = new ArrayList<>();
		Matcher sectionMatcher = SECTION.matcher(content);
		while (sectionMatcher.find()) {
			String title = sectionMatcher.group(1).trim();
			List<NavItem> items = new ArrayList<>();
			Matcher itemMatcher = ITEM.matcher(sectionMatcher.group(2));
			while (itemMatcher.find()) {
				NavItem item = parseItem(itemMatcher.group(1).trim());
				if (item != null) {
					items.add(item);
				}
			}
			if (!items.isEmpty()) {
				sections.add(new NavSection(title, items));
			}
		}
		return sections;
	}

	private NavItem parseItem(String raw) {
		if (raw.isEmpty()) {
			return null;
		}
		int pipe = raw.indexOf('|');
		String target = pipe >= 0 ? raw.substring(0, pipe).trim() : raw;
		String label = pipe >= 0 ? raw.substring(pipe + 1).trim() : null;

		String resolvedTarget = resolveTarget(target);
		if (resolvedTarget == null) {
			return null;
		}
		return new NavItem(resolveLabel(label, target), resolvedTarget);
	}

	private String resolveTarget(String target) {
		Matcher urlMatcher = URL_BBCODE.matcher(target);
		if (urlMatcher.find()) {
			return rewriteLegacyUrl(urlMatcher.group(1).trim());
		}
		if (target.startsWith("http://") || target.startsWith("https://")) {
			return rewriteLegacyUrl(target);
		}
		String magic = TARGET_MAGIC.get(target);
		if (magic != null) {
			return magic;
		}
		return "/wiki/" + target;
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

	private String rewriteLegacyUrl(String url) {
		for (Map.Entry<String, String> entry : LEGACY_URLS.entrySet()) {
			if (url.startsWith(entry.getKey())) {
				String rest = url.substring(entry.getKey().length());
				return rest.isBlank() ? entry.getValue() : entry.getValue() + "/" + rest;
			}
		}
		return url;
	}
}
