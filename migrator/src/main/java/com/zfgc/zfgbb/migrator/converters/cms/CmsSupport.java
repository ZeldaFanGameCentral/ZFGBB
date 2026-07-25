package com.zfgc.zfgbb.migrator.converters.cms;

import java.nio.charset.StandardCharsets;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDateTime;
import java.time.OffsetDateTime;
import java.time.ZoneOffset;
import java.time.format.DateTimeFormatter;
import java.util.Map;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.springframework.web.util.HtmlUtils;

import com.zfgc.zfgbb.wiki.WikiNamespaceRole;
import com.zfgc.zfgbb.dbo.IpAddressDbo;
import com.zfgc.zfgbb.dbo.IpAddressDboExample;
import com.zfgc.zfgbb.dbo.UserDbo;
import com.zfgc.zfgbb.mappers.ContentResourceDboMapper;
import com.zfgc.zfgbb.mappers.IpAddressDboMapper;
import com.zfgc.zfgbb.mappers.UserDboMapper;
import com.zfgc.zfgbb.migrator.SmfTimes;
import com.zfgc.zfgbb.migrator.converters.MigrationHasher;
import com.zfgc.zfgbb.migrator.jobs.JobContextHolder;

public final class CmsSupport {

	private static final DateTimeFormatter MW_TS = DateTimeFormatter.ofPattern("yyyyMMddHHmmss");
	private static final Pattern REDIRECT = Pattern.compile("#REDIRECT\\s*\\[\\[([^\\]|]+)", Pattern.CASE_INSENSITIVE);

	private CmsSupport() {}

	public static String unescape(String value) {
		if (value == null) {
			return null;
		}
		return HtmlUtils.htmlUnescape(value)
				.replace("\\'", "'").replace("\\\"", "\"").replace("\\\\", "\\")
				.trim();
	}

	public static String normalizeTitle(String title) {
		String unescaped = unescape(title);
		return unescaped == null ? "" : unescaped.toLowerCase().replaceAll("\\s+", " ").trim();
	}

	private static String slug(String title) {
		String unescaped = unescape(title);
		String slug = unescaped == null ? "" : unescaped.toLowerCase().replaceAll("[^a-z0-9]+", "-").replaceAll("(^-+|-+$)", "");
		return slug.isBlank() ? "page" : slug;
	}

	public static String uniqueSlug(String title, Set<String> used) {
		String base = slug(title);
		String candidate = base;
		int suffix = 1;
		while (!used.add(candidate)) {
			candidate = base + "-" + (++suffix);
		}
		return candidate;
	}

	private static final Map<Integer, String> MEDIAWIKI_NAMESPACE_EDIT_POLICY = Map.of(
			4, "ZFGC_WIKI_MODERATOR",
			8, "ZFGC_SITE_ADMIN",
			10, "ZFGC_WIKI_MODERATOR");

	public static String defaultEditPermissionCode(Integer sourceNamespaceId) {
		return sourceNamespaceId == null ? null : MEDIAWIKI_NAMESPACE_EDIT_POLICY.get(sourceNamespaceId);
	}

	public static String engineRoleName(Integer sourceNamespaceId) {
		WikiNamespaceRole role = WikiNamespaceRole.ofMediaWikiNamespaceId(sourceNamespaceId);
		return role == null ? null : role.name();
	}

	public static String wikiNamespace(Integer namespace) {
		int id = namespace == null ? 0 : namespace;
		Map<Integer, String> configured = JobContextHolder.getWikiNamespaceIds();
		String name = configured == null ? null : configured.get(id);
		if (name != null && !name.isBlank()) {
			return name.trim();
		}
		return id == 0 ? "MAIN" : "NS" + id;
	}

	public static String wikiSlug(String namespace, String pageTitle) {
		return "MAIN".equals(namespace) ? pageTitle : namespace + ":" + pageTitle;
	}

	public static String wikiTitleDisplay(String pageTitle) {
		return pageTitle == null ? "" : pageTitle.replace('_', ' ');
	}

	public static String redirectTarget(String wikitext) {
		if (wikitext == null) {
			return null;
		}
		Matcher matcher = REDIRECT.matcher(wikitext);
		return matcher.find() ? matcher.group(1).trim().replace(' ', '_') : null;
	}

	public static OffsetDateTime parseMwTimestamp(String value) {
		if (value == null || value.isBlank()) {
			return null;
		}
		try {
			return LocalDateTime.parse(value.trim(), MW_TS).atOffset(ZoneOffset.UTC);
		} catch (RuntimeException e) {
			return null;
		}
	}

	public static OffsetDateTime epoch(Integer seconds) {
		return SmfTimes.fromEpochSeconds(seconds);
	}

	public static String ciProjectStatus(Integer status) {
		if (status == null) {
			return "WIP";
		}
		return switch (status) {
			case 1 -> "SCRAPPED";
			case 2 -> "CONCEPT";
			case 4 -> "COMPLETE";
			default -> "WIP";
		};
	}

	public static String gameStatus(Integer status) {
		return status != null && status == 4 ? "COMPLETE" : "WIP";
	}

	public static String resourceType(Integer type) {
		if (type == null) {
			return "OTHER";
		}
		return switch (type) {
			case 1 -> "AUDIO";
			case 2 -> "GRAPHICS";
			case 3 -> "CODE";
			default -> "OTHER";
		};
	}

	public static Short toShort(Integer value) {
		return value == null ? 0 : value.shortValue();
	}

	public static String displayName(UserDboMapper userMapper, Integer zfgbbUserId) {
		if (zfgbbUserId == null) {
			return null;
		}
		UserDbo user = userMapper.selectByPrimaryKey(zfgbbUserId);
		return user == null ? null : user.getDisplayName();
	}

	public record AssetSource(CmsAssetStore store, Path root) {}

	public static AssetSource assetSource(ContentResourceDboMapper contentMapper, String storageDir) {
		String cmsFiles = JobContextHolder.getCmsFilesSourcePath();
		if (cmsFiles == null) {
			return null;
		}
		String targetPath = JobContextHolder.getAttachmentsTargetPath();
		if (targetPath == null || targetPath.isBlank()) {
			throw new IllegalStateException("attachmentsTargetPath must be provided when cmsFilesSourcePath is set");
		}
		return new AssetSource(new CmsAssetStore(contentMapper, targetPath, storageDir), Paths.get(cmsFiles));
	}

	public static Path wikiImagePath(Path imagesRoot, String name) {
		String file = name.trim().replace(' ', '_');
		if (file.isEmpty()) {
			return null;
		}
		file = Character.toUpperCase(file.charAt(0)) + file.substring(1);
		String hash = CmsAssetStore.md5Hex(file.getBytes(StandardCharsets.UTF_8));
		return confinedResolve(imagesRoot, hash.substring(0, 1), hash.substring(0, 2), file);
	}

	public static Integer ensureIpAddress(IpAddressDboMapper ipAddressMapper, String ipString) {
		String address = ipString == null || ipString.isBlank() ? "127.0.0.1" : ipString.trim();
		boolean ipv6 = address.contains(":");
		String migrationHash = MigrationHasher.hash(address + ipv6 + "false");
		IpAddressDboExample lookup = new IpAddressDboExample();
		lookup.createCriteria().andIpEqualTo(address).andMigrationHashEqualTo(migrationHash);
		IpAddressDbo existing = ipAddressMapper.selectByExample(lookup).stream().findFirst().orElse(null);
		if (existing != null) {
			return existing.getIpAddressId();
		}
		IpAddressDbo ipAddress = new IpAddressDbo();
		ipAddress.setIp(address);
		ipAddress.setIpV6Flag(ipv6);
		ipAddress.setIsSpammerFlag(false);
		ipAddress.setMigrationHash(migrationHash);
		ipAddressMapper.insert(ipAddress);
		return ipAddress.getIpAddressId();
	}

		public static Path confinedResolve(Path root, String... segments) {
		if (root == null) {
			return null;
		}
		Path resolved = root;
		for (String segment : segments) {
			if (segment == null) {
				return null;
			}
			resolved = resolved.resolve(segment);
		}
		resolved = resolved.normalize();
		return resolved.startsWith(root.normalize()) ? resolved : null;
	}

	private static final Pattern TEMPLATE_BLOCK = Pattern.compile(
			"\\[template=[^\\]\\n]+\\](?:.*?\\[/template\\])?",
			Pattern.CASE_INSENSITIVE | Pattern.DOTALL);

	private static final Pattern MW_TEMPLATE_PARAM = Pattern.compile(
			"\\{\\{\\{([A-Za-z0-9_ ]+)(?:\\|[^{}]*)?\\}\\}\\}");

	public static String mustacheBody(String content) {
		if (content == null) {
			return "";
		}
		Matcher paramMatcher = MW_TEMPLATE_PARAM.matcher(content);
		StringBuilder out = new StringBuilder();
		while (paramMatcher.find()) {
			String name = paramMatcher.group(1).trim();
			String key = name.matches("\\d+") ? "_" + name : name;
			paramMatcher.appendReplacement(out, Matcher.quoteReplacement("{{" + key + "}}"));
		}
		paramMatcher.appendTail(out);
		return out.toString();
	}

	private static final Pattern HEADING = Pattern.compile("\\[h[1-6]\\]");

	private static final int LEAD_MAX = 300;

	public static String leadSummary(String content) {
		if (content == null) {
			return null;
		}
		String prose = TEMPLATE_BLOCK.matcher(content).replaceAll("");
		Matcher heading = HEADING.matcher(prose);
		String lead = heading.find() ? prose.substring(0, heading.start()) : prose;
		String plain = lead.replaceAll("\\[\\[[^\\]]*\\]\\]", " ")
				.replaceAll("\\[[^\\]]*\\]", " ")
				.replaceAll("\\s+", " ")
				.strip();
		if (plain.isEmpty() || plain.length() <= LEAD_MAX) {
			return plain;
		}
		int cut = plain.lastIndexOf(' ', LEAD_MAX);
		return plain.substring(0, cut > LEAD_MAX / 2 ? cut : LEAD_MAX) + "…";
	}
}
