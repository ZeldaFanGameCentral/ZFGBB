package com.zfgc.zfgbb.wiki;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Stream;

import org.junit.jupiter.api.Test;

class WikiNamespaceRolePolicyTest {

	private static final List<String> ROLE_BEARING_NAMESPACES = List.of(
			"Template", "Category", "File", "Special", "MediaWiki", "Help");

	private static final List<Path> PRODUCTION_SOURCE_ROOTS = List.of(
			Paths.get("src/main/java/com/zfgc/zfgbb/services/cms"),
			Paths.get("src/main/java/com/zfgc/zfgbb/content/renderer"),
			Paths.get("src/main/java/com/zfgc/zfgbb/dataprovider/cms"),
			Paths.get("../migrator/src/main/java/com/zfgc/zfgbb/migrator/converters/cms"));

	private static final Path ROLE_AWARE_ALLOWLIST = Paths.get(
			"src/main/java/com/zfgc/zfgbb/dataprovider/cms/WikiNamespaceDataProvider.java");

	@Test
	void namespaceDispatchGoesThroughEngineRolesNotHardcodedDisplayNames() throws IOException {
		String names = String.join("|", ROLE_BEARING_NAMESPACES);
		Pattern comparison = Pattern.compile(
				// "Template".equals(x) / "Template:".startsWith(...) — literal on the left
				"\"(" + names + ")(:?)\"\\s*\\.\\s*\\w+\\s*\\("
						// x.equals("Template") / x.startsWith("Template:") — literal on the right
						+ "|\\.\\s*(equals|equalsIgnoreCase|startsWith|endsWith|contains|equalTo"
						+ "|andNamespaceEqualTo|setNamespace)\\s*\\(\\s*\"(" + names + ")(:?)\""
						// case "Template" ->  /  Set.of("Template", …)  /  Map.of("Template", …)
						+ "|case\\s+\"(" + names + ")(:?)\""
						+ "|(Set|List|Map)\\s*\\.\\s*of\\s*\\(\\s*\"(" + names + ")(:?)\""
						// any string literal that hardcodes a prefixed slug, e.g. "MediaWiki:Sidebar"
						+ "|\"(" + names + "):[^\"]*\"");
		assertTrue(Files.isRegularFile(ROLE_AWARE_ALLOWLIST),
				"the allowlisted role-aware source resolves relatively, so a moved or renamed class leaves an "
						+ "exemption that matches nothing while reading as if it still exempts something: "
						+ ROLE_AWARE_ALLOWLIST.toAbsolutePath());
		List<String> offenders = new ArrayList<>();
		int scanned = 0;
		for (Path root : PRODUCTION_SOURCE_ROOTS) {
			assertTrue(Files.isDirectory(root),
					"this guard resolves its source roots relatively, so a moved or renamed package makes it "
							+ "scan nothing and pass while enforcing nothing: " + root.toAbsolutePath());
			try (Stream<Path> sources = Files.walk(root)) {
				for (Path source : sources.filter(path -> path.toString().endsWith(".java")).toList()) {
					scanned++;
					if (source.normalize().equals(ROLE_AWARE_ALLOWLIST.normalize()))
						continue;
					String body = Files.readString(source, StandardCharsets.UTF_8);
					Matcher matcher = comparison.matcher(body);
					while (matcher.find())
						offenders.add(source.getFileName() + ": " + matcher.group().trim());
				}
			}
		}
		assertTrue(scanned > 0, "the guard scanned no source files at all");
		assertTrue(offenders.isEmpty(),
				"namespace display names are operator-configurable, so dispatch must use WikiNamespaceRole "
						+ "(registry.hasRole/roleOf/nameForRole) instead of comparing the name: " + offenders);
	}

	@Test
	void everyMediaWikiCanonicalNamespaceIdMapsToADistinctRole() {
		List<WikiNamespaceRole> mapped = new ArrayList<>();
		for (int sourceNamespaceId = -1; sourceNamespaceId <= 15; sourceNamespaceId++) {
			WikiNamespaceRole role = WikiNamespaceRole.ofMediaWikiNamespaceId(sourceNamespaceId);
			assertTrue(role != null || sourceNamespaceId == -2,
					"MediaWiki namespace id " + sourceNamespaceId + " must map to an engine role");
			if (role != null)
				mapped.add(role);
		}
		assertEquals(mapped.size(), mapped.stream().distinct().count(),
				"two MediaWiki namespace ids must never claim the same engine role: " + mapped);
	}

	@Test
	void talkPairingIsDerivedFromMediaWikiNumberingNotFromNameSpelling() {
		assertEquals(null, WikiNamespaceRole.subjectNamespaceId(0), "subject namespaces have no subject");
		assertEquals(null, WikiNamespaceRole.subjectNamespaceId(4));
		assertEquals(null, WikiNamespaceRole.subjectNamespaceId(-1), "Special is never a talk namespace");
		assertEquals(null, WikiNamespaceRole.subjectNamespaceId(null));

		for (int subject = 0; subject <= 14; subject += 2) {
			assertTrue(!WikiNamespaceRole.isTalkNamespaceId(subject), subject + " is a subject namespace");
			assertTrue(WikiNamespaceRole.isTalkNamespaceId(subject + 1), (subject + 1) + " is a talk namespace");
			assertEquals(subject, WikiNamespaceRole.subjectNamespaceId(subject + 1));
		}
	}

	@Test
	void ofNeverRewritesAnAuthoritativeNamespaceName() {
		assertEquals("User talk", WikiTitle.of("User talk", "Foo", WikiTitle.CaseMode.FIRST_LETTER).namespace(),
				"a name that came from the database or operator config must survive verbatim");
		assertEquals("Image", WikiTitle.of("Image", "Foo", WikiTitle.CaseMode.FIRST_LETTER).namespace(),
				"the compiled bootstrap aliases must never override the namespace registry");
		assertEquals("MAIN", WikiTitle.of(null, "Foo", WikiTitle.CaseMode.FIRST_LETTER).namespace(),
				"a blank namespace still normalizes to MAIN");
		assertEquals("MAIN", WikiTitle.of("  ", "Foo", WikiTitle.CaseMode.FIRST_LETTER).namespace());

		assertEquals("User_talk", WikiTitle.parse("user talk:Foo").namespace(),
				"parse() keeps the bootstrap spellings for the no-registry path");
	}

	@Test
	void unknownRoleNamesParseToNullRatherThanThrowing() {
		assertEquals(null, WikiNamespaceRole.parse("NOT_A_ROLE"));
		assertEquals(null, WikiNamespaceRole.parse(null));
		assertEquals(null, WikiNamespaceRole.parse("  "));
		assertEquals(WikiNamespaceRole.TEMPLATE, WikiNamespaceRole.parse(" TEMPLATE "));
		assertEquals(null, WikiNamespaceRole.parse("template".toUpperCase(Locale.ROOT) + "S"));
	}
}
