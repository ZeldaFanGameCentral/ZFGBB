package com.zfgc.zfgbb.migrator;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.params.provider.Arguments.arguments;
import static org.mockito.Mockito.mock;

import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.OffsetDateTime;
import java.util.List;
import java.util.Map;
import java.util.NavigableMap;
import java.util.Optional;
import java.util.Set;
import java.util.concurrent.ExecutorService;
import java.util.regex.Pattern;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.springframework.beans.BeansException;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.core.env.MapPropertySource;

import com.zfgc.zfgbb.mappers.BrBoardPermissionDboMapper;
import com.zfgc.zfgbb.mappers.BrUserPermissionDboMapper;
import com.zfgc.zfgbb.mappers.MessageHistoryDboMapper;
import com.zfgc.zfgbb.mappers.PermissionDboMapper;
import com.zfgc.zfgbb.migrator.converters.AbstractConverter;
import com.zfgc.zfgbb.migrator.converters.LegacyIdMaps;
import com.zfgc.zfgbb.migrator.converters.LegacyUrlRewriter;
import com.zfgc.zfgbb.migrator.converters.cms.CmsSupport;
import com.zfgc.zfgbb.migrator.jobs.JobContextHolder;
import com.zfgc.zfgbb.migrator.jobs.JobService;
import com.zfgc.zfgbb.migrator.jobs.JobType;
import com.zfgc.zfgbb.migrator.jobs.DistributedLeaseManager;
import com.zfgc.zfgbb.migrator.jobs.MigratorPermissionService;
import com.zfgc.zfgbb.migrator.jobs.QuoteStripPlanner;
import com.zfgc.zfgbb.migrator.jobs.QuoteStripService;
import com.zfgc.zfgbb.migrator.jobs.SourceReferenceOperations;
import com.zfgc.zfgbb.migrator.mappers.QuoteStripConversionMapper;
import com.zfgc.zfgbb.migrator.markup.MarkupConverter;

class ConversionTest {

	@Nested
	class WikitextMarkup {

		private static String fixture(String name) throws IOException {
			try (InputStream in = ConversionTest.class.getResourceAsStream("/wiki-fixtures/content/" + name)) {
				if (in == null) {
					throw new IllegalStateException("missing fixture " + name);
				}
				return new String(in.readAllBytes(), StandardCharsets.UTF_8);
			}
		}


		@Test
		void convertsWikilinksInsideTemplateParams() {
			String wt = "{{ItemInfobox\n|title=Bomb cannon\n|obtained=[[KOT:Goron Mines|Goron Mines]]\n}}";
			String bb = MarkupConverter.toBBCode(wt);
			assertTrue(bb.contains("[template=ItemInfobox]"), bb);
			assertTrue(bb.contains("obtained=[wiki=KOT:Goron_Mines]Goron Mines[/wiki]"), bb);
			assertFalse(bb.contains("[[KOT:Goron Mines"), "raw wikilink must not remain in a param: " + bb);
		}

		@Test
		void convertsGameTemplatePage() throws IOException {
			String wt = fixture("Project_Zelda_Engine.wiki");
			String bb = MarkupConverter.toBBCode(wt);
			assertTrue(bb.contains("[template=Game]"), "expected Game template");
			assertTrue(bb.contains("[h2]"), "expected a heading");
			assertFalse(bb.contains("@@ZTPL"), "sentinel leaked into output");
		}

		@Test
		void convertsNestedBulletsInTheSidebar() throws IOException {
			String wt = fixture("MediaWiki_Sidebar.wiki");
			String bb = MarkupConverter.toBBCode(wt);
			assertFalse(bb.contains("**"), "nested wikitext bullets must not survive: " + bb);
			assertTrue(bb.contains("[li]navigation [list]"),
					"a '*' heading owning '**' children becomes an [li] wrapping a nested [list]: " + bb);
			assertTrue(bb.contains("[li]mainpage|mainpage-description[/li]"),
					"magic-word entries stay intact for the nav parser: " + bb);
			assertTrue(bb.contains("[li]Category:Members|List of Members[/li]"),
					"the leading colon on :Category: links is dropped so the target resolves: " + bb);
			assertTrue(bb.contains("[url=http://zfgc.com/index.php/chat]"),
					"bare legacy urls autolink: " + bb);
			assertEquals(3, countNestedSidebarSections(bb),
					"navigation, Content and ZFGC each own exactly one nested list: " + bb);
		}

		private static int countNestedSidebarSections(String bb) {
			return (int) Pattern.compile("\\[li\\][^\\[]+?\\[list\\]")
					.matcher(bb).results().count();
		}

		@Test
		void convertsWikitablePage() throws IOException {
			String wt = fixture("Zelda_II.wiki");
			String bb = MarkupConverter.toBBCode(wt);
			assertTrue(bb.contains("[table"), "expected a table: " + bb);
			assertTrue(bb.contains("[tr]"), "expected table rows: " + bb);
		}

		@Test
		void templateCallsInsidePreBlocksBecomeLiteralSource() {
			String wt = "Documentation:\n {{Game\n |title=Skyward Sword\n |genre=Action\n }}\nDone.";
			String bb = MarkupConverter.toBBCode(wt);
			assertFalse(bb.contains("@@ZT"), "template sentinel must never leak: " + bb);
			assertTrue(bb.contains("{{Game"), "pre blocks should show the literal template source: " + bb);
			assertTrue(bb.contains("title=Skyward Sword"), bb);
		}

		@Test
		void templateCallsInsideLinkTargetsNeverLeakSentinels() {
			String wt = "This box: [[{{FULLPAGENAMEE}}|view]] and [http://example.com/w?title={{FULLPAGENAMEE}} edit].";
			String bb = MarkupConverter.toBBCode(wt);
			assertFalse(bb.contains("@@ZT"), "sentinel leaked into link targets: " + bb);
			assertFalse(bb.contains("%40%40ZT"), "url-encoded sentinel leaked: " + bb);
		}

		@Test
		void nestedTemplateCallsInsideParamsNeverLeakSentinels() {
			String wt = "[[{{TALKPAGENAME:{{FULLPAGENAME}}}}|talk]] and {{Outer|inner={{Inner|x=1}}}}";
			String bb = MarkupConverter.toBBCode(wt);
			assertFalse(bb.contains("@@ZT"), "nested sentinel leaked: " + bb);
		}

		@Test
		void layoutTablesKeepStructure() {
			String wt = "{|style=\"width:100%\"\n|-\n! Header One\n|-\n| left cell\n|}";
			String bb = MarkupConverter.toBBCode(wt);
			assertTrue(bb.contains("[table=full]"), "full-width tables should carry the layout flag: " + bb);
			assertTrue(bb.contains("[th]"), "header cells should emit [th]: " + bb);
		}

		@Test
		void nestedTablesDoNotDuplicateRows() {
			String wt = "{|\n| outer-a\n{|\n| inner-x\n|}\n| outer-b\n|}";
			String bb = MarkupConverter.toBBCode(wt);
			int count = bb.split("inner-x", -1).length - 1;
			assertTrue(count == 1, "nested rows must not be captured twice: " + bb);
		}

		@Test
		void articleCountMagicWordBecomesPageCountTemplate() {
			String bb = MarkupConverter.toBBCode("There are {{NUMBEROFARTICLES}} articles.");
			assertTrue(bb.contains("[template=PageCount][/template]"), bb);
		}

		@Test
		void inlineTemplateCallsStayInline() {
			String bb = MarkupConverter.toBBCode("* {{tl|stub}} - Add this to articles that are too short");
			assertTrue(bb.contains("[/template] - Add this"), "no blank lines may follow an inline template call: " + bb);
		}

		@Test
		void conditionalCategoryAssignmentsAreNotCategories() {
			String wt = "text\n[[Category:{{#switch:{{{demolevel|}}}|yes=Demo templates|Other}}]]\n[[Category:Real Category]]";
			var cats = MarkupConverter.categories(MarkupConverter.parse(wt));
			assertTrue(cats.contains("Real Category"), cats.toString());
			assertTrue(cats.stream().noneMatch(c -> c.contains("{")), "unresolved parser functions are not categories: " + cats);
		}

		@Test
		void categoryNamesFollowMediaWikiTitleRules() {
			String wt = "Use <nowiki>[[Category:name]]</nowiki> to categorize.\n[[Category:featured project]]";
			var cats = MarkupConverter.categories(MarkupConverter.parse(wt));
			assertTrue(cats.contains("Featured project"), "first letter capitalizes like MediaWiki: " + cats);
			assertFalse(cats.contains("name"), "nowiki examples are not categories: " + cats);
			assertFalse(cats.contains("Name"), "nowiki examples are not categories: " + cats);
		}

		@Test
		void urlParserFunctionsBecomeWikiLinks() {
			String bb = MarkupConverter.toBBCode(
					"See the [{{canonicalurl:Special:AllPages|namespace=100}} list of KOT articles] here.");
			assertTrue(bb.contains("[wiki=Special:AllPages]list of KOT articles[/wiki]"), bb);
			assertFalse(bb.contains("canonicalurl"), bb);
		}

		@Test
		void tocDirectivesSurviveConversion() {
			assertTrue(MarkupConverter.toBBCode("__NOTOC__\n= A =\ntext").contains("[notoc]"));
			assertTrue(MarkupConverter.toBBCode("__TOC__\n= A =\ntext").contains("[toc]"));
			assertFalse(MarkupConverter.toBBCode("= A =\ntext").contains("[notoc]"));
		}

		@Test
		void convertsLorePage() throws IOException {
			String wt = fixture("Raj_Naidu.wiki");
			String bb = MarkupConverter.toBBCode(wt);
			assertTrue(bb.contains("[b]"), "expected bold");
			assertTrue(bb.contains("[h1]") || bb.contains("[h2]"), "expected a heading");
		}
	}

	@Nested
	class LegacyUrlRewrite {

		private static final class MapsBuilder {
			private Map<Integer, Integer> threads = Map.of();
			private Map<Integer, Integer> messages = Map.of();
			private Map<Integer, Integer> boards = Map.of();
			private Map<Integer, Integer> users = Map.of();
			private Map<Integer, Integer> attachments = Map.of();
			private Map<Integer, Integer> gamesToProjects = Map.of();

			MapsBuilder thread(int legacyId, int zfgbbId) {
				threads = Map.of(legacyId, zfgbbId);
				return this;
			}

			MapsBuilder message(int legacyId, int zfgbbId) {
				messages = Map.of(legacyId, zfgbbId);
				return this;
			}

			MapsBuilder board(int legacyId, int zfgbbId) {
				boards = Map.of(legacyId, zfgbbId);
				return this;
			}

			MapsBuilder user(int legacyId, int zfgbbId) {
				users = Map.of(legacyId, zfgbbId);
				return this;
			}

			MapsBuilder attachment(int legacyId, int zfgbbId) {
				attachments = Map.of(legacyId, zfgbbId);
				return this;
			}

			MapsBuilder gameProject(int legacyGameId, int projectId) {
				gamesToProjects = Map.of(legacyGameId, projectId);
				return this;
			}

			LegacyIdMaps build() {
				return new LegacyIdMaps(threads, messages, boards, users, attachments, gamesToProjects);
			}
		}

		private static MapsBuilder maps() {
			return new MapsBuilder();
		}

		@ParameterizedTest
		@MethodSource("staticRewriteCases")
		void rewriteTransformsLegacyBodies(String caseName, String input, LegacyIdMaps idMaps, String expected) {
			assertEquals(expected, LegacyUrlRewriter.rewrite(input, idMaps));
		}

		static Stream<Arguments> staticRewriteCases() {
			return Stream.of(
					arguments("urlToThreadBasic",
							"see [url=http://www.zfgc.com/index.php?topic=42.0]here[/url] please",
							maps().thread(42, 42).build(),
							"see [thread=42]here[/thread] please"),
					arguments("urlToThreadWithMsg",
							"[url=http://www.zfgc.com/index.php?topic=42.msg128#msg128]here[/url]",
							maps().thread(42, 42).message(128, 128).build(),
							"[thread=42 msg=128]here[/thread]"),
					arguments("urlToBoard",
							"[url=http://zfgc.com/forum/index.php?board=15.0]click[/url]",
							maps().board(15, 15).build(),
							"[board=15]click[/board]"),
					arguments("urlRemapsThreadId",
							"see [url=http://www.zfgc.com/index.php?topic=42.0]here[/url] please",
							maps().thread(42, 1042).build(),
							"see [thread=1042]here[/thread] please"),
					arguments("urlRemapsBoardId",
							"[url=http://zfgc.com/forum/index.php?board=15.0]click[/url]",
							maps().board(15, 2015).build(),
							"[board=2015]click[/board]"),
					arguments("urlToMemberSemicolonForm",
							"[url=http://www.zfgc.com/index.php?action=profile;u=789]profile[/url]",
							maps().user(789, 789).build(),
							"[member=789]profile[/member]"),
					arguments("urlToMemberAmpersandForm",
							"[url=http://www.zfgc.com/index.php?action=profile&u=789]profile[/url]",
							maps().user(789, 789).build(),
							"[member=789]profile[/member]"),
					arguments("iurlHandledSameAsUrl",
							"[iurl=http://www.zfgc.com/index.php?topic=42.0]inline[/iurl]",
							maps().thread(42, 42).build(),
							"[thread=42]inline[/thread]"),
					arguments("unknownThreadIdLeavesUrlAlone",
							"see [url=http://www.zfgc.com/index.php?topic=42.0]here[/url] please",
							LegacyIdMaps.empty(),
							"see [url=http://www.zfgc.com/index.php?topic=42.0]here[/url] please"),
					arguments("nonZfgcUrlsLeftAlone",
							"[url=https://en.wikipedia.org/wiki/X]wiki[/url]",
							LegacyIdMaps.empty(),
							"[url=https://en.wikipedia.org/wiki/X]wiki[/url]"),
					arguments("quoteLinkRewrittenToThreadMsgPreservesAuthorDropsDate",
							"[quote author=Foo link=topic=42.msg128#msg128 date=1234567890]body[/quote]",
							maps().thread(42, 42).message(128, 128).build(),
							"[quote author=Foo thread=42 msg=128]body[/quote]"),
					arguments("quoteLinkRemapsIds",
							"[quote author=Foo link=topic=42.msg128#msg128 date=1234567890]body[/quote]",
							maps().thread(42, 1042).message(128, 2128).build(),
							"[quote author=Foo thread=1042 msg=2128]body[/quote]"),
					arguments("quoteAuthorWithSpacesSurvivesWhole",
							"[quote author=Hammer Bro. Mike link=topic=42.msg128#msg128 date=1234567890]body[/quote]",
							maps().thread(42, 42).message(128, 128).build(),
							"[quote author=Hammer Bro. Mike thread=42 msg=128]body[/quote]"),
					arguments("quoteAuthorBearingEqualsSignsSurvivesWhole",
							"[quote author=-=Limey=- link=topic=42.msg128#msg128 date=1234567890]body[/quote]",
							maps().thread(42, 42).message(128, 128).build(),
							"[quote author=-=Limey=- thread=42 msg=128]body[/quote]"),
					arguments("quoteAuthorBearingABracketIsStrippedSoTheRendererCanStillReadTheTag",
							"[quote author=-x-[Sir Lunatic link=topic=42.msg128#msg128 date=1234567890]body[/quote]",
							maps().thread(42, 42).message(128, 128).build(),
							"[quote author=-x-Sir Lunatic thread=42 msg=128]body[/quote]"),
					arguments("quoteAuthorFollowingTheLinkStopsAtTheDateAttribute",
							"[quote link=topic=42.msg128#msg128 author=Foo date=1234567890]body[/quote]",
							maps().thread(42, 42).message(128, 128).build(),
							"[quote author=Foo thread=42 msg=128]body[/quote]"),
					arguments("quoteWithoutAnAuthorEmitsThreadAndMsgOnly",
							"[quote link=topic=42.msg128#msg128 date=1234567890]body[/quote]",
							maps().thread(42, 42).message(128, 128).build(),
							"[quote thread=42 msg=128]body[/quote]"),
					arguments("quoteWithoutLinkLeftAlone",
							"[quote author=Foo date=123]body[/quote]",
							LegacyIdMaps.empty(),
							"[quote author=Foo date=123]body[/quote]"),
					arguments("bareZfgcUrlToThread",
							"see http://www.zfgc.com/index.php?topic=42.0 yo",
							maps().thread(42, 42).build(),
							"see [thread=42]http://www.zfgc.com/forum/thread/42/1[/thread] yo"),
					arguments("labelReplacedWhenLabelEqualsUrl",
							"[url=http://www.zfgc.com/index.php?board=2.0]http://www.zfgc.com/index.php?board=2.0[/url]",
							maps().board(2, 2).build(),
							"[board=2]http://www.zfgc.com/forum/board/2/1[/board]"),
					arguments("labelReplacedWhenLabelIsBlank",
							"[url=http://www.zfgc.com/index.php?board=2.0]   [/url]",
							maps().board(2, 2).build(),
							"[board=2]http://www.zfgc.com/forum/board/2/1[/board]"),
					arguments("meaningfulLabelPreserved",
							"[url=http://www.zfgc.com/index.php?board=2.0]check this out[/url]",
							maps().board(2, 2).build(),
							"[board=2]check this out[/board]"),
					arguments("attachRefsRewrittenViaMap",
							"image [attach=1]",
							maps().attachment(1, 99).build(),
							"image [attach=99]"),
					arguments("unknownAttachRefLeftAlone",
							"image [attach=42]",
							maps().attachment(1, 99).build(),
							"image [attach=42]"),
					arguments("attachRefNotDoubleMappedWhenAlreadyZfgbbId",
							"image [attach=99]",
							maps().attachment(1, 99).build(),
							"image [attach=99]"),
					arguments("multiplePatternsInOneBody",
							"hi [url=http://www.zfgc.com/index.php?action=profile;u=5]foo[/url] "
									+ "check [url=http://www.zfgc.com/index.php?topic=42.msg128#msg128]this[/url] [attach=1]",
							maps().thread(42, 42).message(128, 128).user(5, 5).attachment(1, 99).build(),
							"hi [member=5]foo[/member] check [thread=42 msg=128]this[/thread] [attach=99]"),
					arguments("urlToResource",
							"[url=http://www.zfgc.com/index.php#?action=resources&sa=view&id=42]Sword tileset[/url]",
							LegacyIdMaps.empty(),
							"[resource=42]Sword tileset[/resource]"),
					arguments("urlToAMigratedGameBecomesItsProject",
							"[url=http://www.zfgc.com/index.php#?action=games&sa=view&id=99]Triforce Saga[/url]",
							maps().gameProject(99, 7).build(),
							"[project=7]Triforce Saga[/project]"),
					arguments("bareGameUrlWithAFragmentQueryRewritesToTheProject",
							"see it here: http://www.zfgc.com/index.php#?action=games&sa=view&id=99",
							maps().gameProject(99, 7).build(),
							"see it here: [project=7]Project #7[/project]"),
					arguments("aLegacyUrlInsideAnHtmlAttributeIsNotTurnedIntoBBCode",
							"<a href=\"http://www.zfgc.com/index.php#?action=games&sa=view&id=99\">Triforce Saga</a>",
							maps().gameProject(99, 7).build(),
							"<a href=\"http://www.zfgc.com/index.php#?action=games&sa=view&id=99\">Triforce Saga</a>"),
					arguments("aLegacyUrlUsedAsAnchorTextIsLeftAlone",
							"<a href=\"https://example.com\">http://www.zfgc.com/index.php#?action=games&sa=view&id=99</a>",
							maps().gameProject(99, 7).build(),
							"<a href=\"https://example.com\">http://www.zfgc.com/index.php#?action=games&sa=view&id=99</a>"),
					arguments("aBareUrlDirectlyAfterAnHtmlBreakStillRewrites",
							"it should be:<br /><br />http://www.zfgc.com/index.php#?action=games&sa=view&id=99",
							maps().gameProject(99, 7).build(),
							"it should be:<br /><br />[project=7]Project #7[/project]"),
					arguments("urlToAGameThatMigratedToNoProjectStaysAnOrdinaryUrl",
							"[url=http://www.zfgc.com/index.php#?action=games&sa=view&id=99]Triforce Saga[/url]",
							LegacyIdMaps.empty(),
							"[url=http://www.zfgc.com/index.php#?action=games&sa=view&id=99]Triforce Saga[/url]"),
					arguments("templateParamUrlsRewriteToThreadLinks",
							"Thread=http://zfgc.com/forum/index.php?topic=7.0",
							maps().thread(7, 42).build(),
							"Thread=[thread=42]http://zfgc.com/forum/thread/42/1[/thread]"),
					arguments("unmappedUrlBBCodeTargetsStayIntact",
							"[url=http://zfgc.com/forum/index.php?topic=9999.0]dead[/url]",
							maps().thread(7, 42).build(),
							"[url=http://zfgc.com/forum/index.php?topic=9999.0]dead[/url]"),
					arguments("nullBodyReturnsNull",
							null,
							LegacyIdMaps.empty(),
							null),
					arguments("wikiUrlInUrlTag",
							"[url=http://wiki.zfgc.com/New_Beginnings]the project[/url]",
							LegacyIdMaps.empty(),
							"[wiki=New_Beginnings]the project[/wiki]"),
					arguments("bareWikiUrlBecomesWikiTag",
							"see http://wiki.zfgc.com/KOT:Game_Engine here",
							LegacyIdMaps.empty(),
							"see [wiki=KOT:Game_Engine]KOT:Game Engine[/wiki] here"),
					arguments("bareWikiUrlTrailingParenTrimmed",
							"(http://wiki.zfgc.com/KOT:Game_Engine)",
							LegacyIdMaps.empty(),
							"([wiki=KOT:Game_Engine]KOT:Game Engine[/wiki])"),
					arguments("wikiImageUrlBecomesFilePage",
							"http://wiki.zfgc.com/images/0/07/AlphaScreen1.png",
							LegacyIdMaps.empty(),
							"[wiki=File:AlphaScreen1.png]File:AlphaScreen1.png[/wiki]"),
					arguments("wikiIndexPhpTitleForm",
							"http://wiki.zfgc.com/index.php?title=Main_Page",
							LegacyIdMaps.empty(),
							"[wiki=Main_Page]Main Page[/wiki]"));
		}

		@ParameterizedTest
		@MethodSource("configuredRewriterCases")
		void configuredRewriterHonorsLegacyHostAndAppBaseUrl(String caseName, String legacyHost, String appBaseUrl,
				String input, LegacyIdMaps idMaps, String expected) {
			LegacyUrlRewriter rewriter = appBaseUrl == null
					? LegacyUrlRewriter.forLegacyHost(legacyHost)
					: LegacyUrlRewriter.forLegacyHost(legacyHost, appBaseUrl);

			assertEquals(expected, rewriter.rewriteBody(input, idMaps));
		}

		static Stream<Arguments> configuredRewriterCases() {
			return Stream.of(
					arguments("legacyHostUrlRewrittenWhenConfigured", "localhost:8090", null,
							"see [url=http://localhost:8090/index.php?board=2.0]click[/url] now",
							maps().board(2, 2).build(),
							"see [board=2]click[/board] now"),
					arguments("legacyHostBareUrlRewrittenWhenConfigured", "localhost:8090", null,
							"see http://localhost:8090/index.php?topic=42.0 yo",
							maps().thread(42, 42).build(),
							"see [thread=42]http://localhost:8090/forum/thread/42/1[/thread] yo"),
					arguments("legacyHostBlankFallsBackToZfgcOnly", "", null,
							"[url=http://localhost:8090/index.php?board=2.0]click[/url]",
							maps().board(2, 2).build(),
							"[url=http://localhost:8090/index.php?board=2.0]click[/url]"),
					arguments("zfgcUrlStillRewrittenWhenLegacyHostConfigured", "localhost:8090", null,
							"[url=http://www.zfgc.com/index.php?topic=42.0]here[/url]",
							maps().thread(42, 42).build(),
							"[thread=42]here[/thread]"),
					arguments("appBaseUrlOverridesMatchedOriginInCanonicalLabel", "localhost:8090", "http://localhost:5173",
							"[url=http://localhost:8090/index.php?board=2.0]http://localhost:8090/index.php?board=2.0[/url]",
							maps().board(2, 2).build(),
							"[board=2]http://localhost:5173/forum/board/2/1[/board]"),
					arguments("appBaseUrlTrailingSlashNormalized", null, "http://localhost:5173/",
							"[url=http://www.zfgc.com/index.php?topic=42.0]http://www.zfgc.com/index.php?topic=42.0[/url]",
							maps().thread(42, 42).build(),
							"[thread=42]http://localhost:5173/forum/thread/42/1[/thread]"));
		}

		@Test
		void rewritingIsIdempotentForUrlInsideAlreadyRewrittenBBCode() {
			LegacyUrlRewriter rewriter = LegacyUrlRewriter.forLegacyHost("localhost:8090");
			String body = "[url=http://localhost:8090/index.php?board=2.0]http://localhost:8090/index.php?board=2.0[/url]";
			LegacyIdMaps idMaps = maps().board(2, 2).build();
			String once = rewriter.rewriteBody(body, idMaps);
			String twice = rewriter.rewriteBody(once, idMaps);
			assertEquals(once, twice);
		}
	}

	@Nested
	class PathConfinement {

		private final Path root = Paths.get("cms-root");

		@Test
		void resolvesChildWithinRoot() {
			Path resolved = CmsSupport.confinedResolve(root, "resources", "hoffy_maps.zip");
			assertNotNull(resolved);
			assertTrue(resolved.normalize().startsWith(root.normalize()));
			assertTrue(resolved.toString().endsWith("hoffy_maps.zip"));
		}

		@Test
		void rejectsParentTraversal() {
			assertNull(CmsSupport.confinedResolve(root, "resources", "../../../../etc/passwd"));
			assertNull(CmsSupport.confinedResolve(root, "../evil"));
		}

		@Test
		void rejectsNullInputs() {
			assertNull(CmsSupport.confinedResolve(null, "x"));
			assertNull(CmsSupport.confinedResolve(root, (String) null));
		}

		@Test
		void wikiImagePathConfinesTraversalName() {
			assertNull(CmsSupport.wikiImagePath(root, "../../../../etc/passwd"));
			assertNotNull(CmsSupport.wikiImagePath(root, "Master Sword.png"));
		}
	}

	@Nested
	class JobWiring {

		@Test
		void rejectsWhenAJobTypeHasNoConverter() {
			List<AbstractConverter<?>> partial = Stream.of(JobType.values())
					.filter(t -> t != JobType.MIGRATE_SMF_INSTALLATION)
					.filter(t -> t != JobType.CATEGORIES)
					.map(StubConverter::new)
					.collect(Collectors.toList());

			IllegalStateException ex = assertThrows(IllegalStateException.class,
					() -> new JobService(partial, null, null, mock(ExecutorService.class),
							Optional.empty(), Optional.empty()));
			assertTrue(ex.getMessage().contains("CATEGORIES"),
					"missing-converter list should call out CATEGORIES; was: " + ex.getMessage());
		}

		@Test
		void acceptsFullSetOfConverters() {
			List<AbstractConverter<?>> full = Stream.of(JobType.values())
					.filter(t -> t != JobType.MIGRATE_SMF_INSTALLATION)
					.map(StubConverter::new)
					.collect(Collectors.toList());

			new JobService(full, null, null, mock(ExecutorService.class),
					Optional.empty(), Optional.empty());
		}

		@Test
		void pipelineTypesExpandAndLeafTypesStandAlone() {
			assertEquals(List.of(JobType.CATEGORIES), JobType.CATEGORIES.expand());
			assertEquals(JobType.USERS, JobType.SMF_INSTALLATION_PIPELINE.get(0),
					"users must be migrated first; everything else carries their ids");
			assertEquals(20, JobType.MIGRATE_SMF_INSTALLATION.expand().size());
			assertEquals(List.of(JobType.PROJECTS, JobType.RESOURCES, JobType.CMS_COMMENTS, JobType.WIKI_PAGES,
					JobType.BBCODE_REWRITE), JobType.MIGRATE_CMS_INSTALLATION.expand());
		}

		@Test
		void migrateEverythingRunsBothPipelinesEndToEndWithoutRepeatingAStep() {
			List<JobType> everything = JobType.MIGRATE_EVERYTHING.expand();

			assertEquals(everything.size(), everything.stream().distinct().count(),
					"no step may run twice in one pipeline: " + everything);
			assertTrue(everything.stream().noneMatch(JobType::isPipeline),
					"expansion must yield runnable steps only: " + everything);
			assertTrue(everything.containsAll(JobType.SMF_INSTALLATION_PIPELINE)
					&& everything.containsAll(JobType.CMS_INSTALLATION_PIPELINE),
					"both halves must be present: " + everything);
			assertTrue(everything.indexOf(JobType.USERS) < everything.indexOf(JobType.WIKI_PAGES),
					"the SMF half must run before the CMS half, which references migrated users");
		}

		@Test
		void everyRunnableJobTypeIsWiredIntoAPipeline() {
			List<JobType> everything = JobType.MIGRATE_EVERYTHING.expand();
			List<JobType> orphaned = Stream.of(JobType.values())
					.filter(type -> !type.isPipeline())
					.filter(type -> !everything.contains(type))
					.toList();

			assertTrue(orphaned.isEmpty(),
					"JobType(s) that no pipeline runs, so a full migration silently skips them: " + orphaned);
		}

		private static final class StubConverter extends AbstractConverter<Void> {
			private final JobType type;

			StubConverter(JobType type) {
				this.type = type;
			}

			@Override
			public JobType getType() {
				return type;
			}

			@Override
			public Void convertToZfgbb() {
				return null;
			}
		}
	}

	@Nested
	class GroupPermissionMapping {

		private final MigratorPermissionService permissions = new MigratorPermissionService(
				mock(PermissionDboMapper.class),
				mock(BrBoardPermissionDboMapper.class),
				mock(BrUserPermissionDboMapper.class));

		@AfterEach
		void clearJobContext() {
			JobContextHolder.clear();
		}

		@Test
		void reservedSmfGroupsMapToBuiltInCodes() {
			assertEquals(Set.of(MigratorPermissionService.CODE_GUEST), permissions.mapSmfGroupToCodes(-1));
			assertEquals(Set.of(MigratorPermissionService.CODE_USER), permissions.mapSmfGroupToCodes(0));
			assertEquals(Set.of(MigratorPermissionService.CODE_SITE_ADMIN), permissions.mapSmfGroupToCodes(1));
			assertEquals(Set.of(MigratorPermissionService.CODE_SITE_MODERATOR), permissions.mapSmfGroupToCodes(2));
			assertEquals(Set.of(MigratorPermissionService.CODE_SITE_MODERATOR), permissions.mapSmfGroupToCodes(3));
		}

		@Test
		void nullGroupIdNeverThrowsAndYieldsEmpty() {
			assertEquals(Set.of(), assertDoesNotThrow(() -> permissions.mapSmfGroupToCodes(null)));
			assertEquals(Set.of(), permissions.mapSmfGroupCsvToCodes(null));
			assertEquals(Set.of(), permissions.mapSmfGroupCsvToCodes(""));

			JobContextHolder.set(null, null, null, null, null, null, null, null, null, false, false,
					null, null, null, Map.of(9, List.of(MigratorPermissionService.CODE_SITE_ADMIN)));
			try {
				assertEquals(Set.of(), assertDoesNotThrow(() -> permissions.mapSmfGroupToCodes(null)));
			} finally {
				JobContextHolder.clear();
			}
		}

		@Test
		void unmappedCustomGroupsFailClosed() {
			Set<String> singleGroup = permissions.mapSmfGroupToCodes(12);
			assertTrue(singleGroup.isEmpty());
			assertFalse(singleGroup.contains(MigratorPermissionService.CODE_USER));
			assertFalse(singleGroup.contains(MigratorPermissionService.CODE_GUEST));

			assertTrue(permissions.mapSmfGroupCsvToCodes("12").isEmpty());

			Set<String> mixedCsv = permissions.mapSmfGroupCsvToCodes("-1,0,12");
			assertEquals(Set.of(MigratorPermissionService.CODE_GUEST, MigratorPermissionService.CODE_USER), mixedCsv);
		}

		@Test
		void operatorGroupPermissionMapIsHonored() {
			JobContextHolder.set(null, null, null, null, null, null, null, null, null, false, false,
					null, null, null, Map.of(12, List.of(MigratorPermissionService.CODE_SITE_ADMIN)));
			try {
				assertEquals(Set.of(MigratorPermissionService.CODE_SITE_ADMIN), permissions.mapSmfGroupToCodes(12));
			} finally {
				JobContextHolder.clear();
			}
		}
	}

	@Nested
	class QuoteStripping {

		private AnnotationConfigApplicationContext theMigratorHalfOfTheStrip() {
			AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext();
			context.getEnvironment().getPropertySources().addFirst(new MapPropertySource("theStripIsEnabled",
					Map.of("zfgbb.migrator.enabled", "true")));
			context.registerBean(QuoteStripConversionMapper.class, () -> mock(QuoteStripConversionMapper.class));
			context.registerBean(MessageHistoryDboMapper.class, () -> mock(MessageHistoryDboMapper.class));
			context.registerBean(DistributedLeaseManager.class, () -> mock(DistributedLeaseManager.class));
			context.registerBean(QuoteStripPlanner.class, () -> mock(QuoteStripPlanner.class));
			context.register(QuoteStripService.class);
			return context;
		}

		private String everyMessageOf(Throwable thrown) {
			StringBuilder messages = new StringBuilder();
			for (Throwable cause = thrown; cause != null; cause = cause.getCause())
				messages.append(cause.getMessage()).append('\n');
			return messages.toString();
		}

		@Test
		void theStripRefusesToStartWhenTheAppRegistersNoSourceReferencePort() {
			try (AnnotationConfigApplicationContext context = theMigratorHalfOfTheStrip()) {
				BeansException refused = assertThrows(BeansException.class, context::refresh);

				assertTrue(everyMessageOf(refused).contains(SourceReferenceOperations.class.getName()),
						"the strip walks parsed bodies through a port only the app can implement, so an app that "
								+ "stops registering the adapter must be told at startup and by name; a port held "
								+ "as an optional or a nullable field turns that into a NullPointerException "
								+ "somewhere inside a migration run: " + everyMessageOf(refused));
			}
		}

		@Test
		void theStripStartsOnceTheAppRegistersTheSourceReferencePort() {
			try (AnnotationConfigApplicationContext context = theMigratorHalfOfTheStrip()) {
				context.registerBean(SourceReferenceOperations.class, () -> mock(SourceReferenceOperations.class));
				context.refresh();

				assertNotNull(context.getBean(QuoteStripService.class),
						"the refusal above only says something while the very same context starts with the port "
								+ "present; otherwise it could be failing on any of the other collaborators");
			}
		}

		@ParameterizedTest(name = "{0}")
		@MethodSource("everyDecisionThePlannerMakesAboutAnEmbeddedBody")
		void thePlannerKeepsAnEmbeddedBodyUnlessItIsAFaithfulCopyOfItsSource(String caseName, String embeddedBody,
				String sourceBody, boolean theEmbeddedBodyNestsAQuote, boolean theSourceBodyNestsAQuote,
				String expectedOutcome) {
			RecordedDecision decision = new RecordedDecision(sourceBody);
			QuoteStripPlanner planner = new QuoteStripPlanner(
					new OneSourceReference(theEmbeddedBodyNestsAQuote, theSourceBodyNestsAQuote));

			assertEquals(expectedOutcome.equals(STRIPPED) ? "" : embeddedBody,
					planner.stripFaithfulMsgQuotes(embeddedBody, decision));
			assertEquals(expectedOutcome, decision.outcome, caseName);
		}

		static final String STRIPPED = "stripped";

		static Stream<Arguments> everyDecisionThePlannerMakesAboutAnEmbeddedBody() {
			return Stream.of(
					arguments("a faithful copy is stripped", "hello", "hello", false, false, STRIPPED),
					arguments("an embedded body that nests a quote is kept", "hello", "hello", true, false,
							QuoteStripPlanner.KEEP_NESTED_EMBEDDED),
					arguments("a blank embedded body is kept", "   ", "hello", false, false,
							QuoteStripPlanner.KEEP_BLANK_EMBEDDED),
					arguments("an unavailable source is kept", "hello", null, false, false,
							QuoteStripPlanner.KEEP_SOURCE_UNAVAILABLE),
					arguments("a blank source is kept", "hello", "  ", false, false,
							QuoteStripPlanner.KEEP_SOURCE_UNAVAILABLE),
					arguments("a source that nests a quote is kept", "hello", "hello", false, true,
							QuoteStripPlanner.KEEP_SOURCE_NESTED),
					arguments("a body the author edited is kept", "hello", "hello there", false, false,
							QuoteStripPlanner.KEEP_MODIFIED));
		}

		@Test
		void thePlannerLeavesANullBodyAlone() {
			assertNull(new QuoteStripPlanner(new OneSourceReference(false, false))
					.stripFaithfulMsgQuotes(null, new RecordedDecision("hello")));
		}

		private static final class RecordedDecision implements QuoteStripPlanner.StripContext {

			private final String sourceBody;

			private String outcome;

			private RecordedDecision(String sourceBody) {
				this.sourceBody = sourceBody;
			}

			@Override
			public String resolveFloorBody(Integer msgId) {
				return sourceBody;
			}

			@Override
			public String normalize(String text) {
				return QuoteStripService.normalize(text);
			}

			@Override
			public void recordStrip(Integer msgId) {
				outcome = STRIPPED;
			}

			@Override
			public void recordKeep(Integer msgId, String reason) {
				outcome = reason;
			}
		}

		private static final class OneSourceReference implements SourceReferenceOperations {

			private final boolean theEmbeddedBodyNestsAQuote;

			private final boolean theSourceBodyNestsAQuote;

			private boolean theEmbeddedBodyWasAskedAbout;

			private OneSourceReference(boolean theEmbeddedBodyNestsAQuote, boolean theSourceBodyNestsAQuote) {
				this.theEmbeddedBodyNestsAQuote = theEmbeddedBodyNestsAQuote;
				this.theSourceBodyNestsAQuote = theSourceBodyNestsAQuote;
			}

			@Override
			public Set<Integer> collectSourceReferenceIds(String body) {
				return Set.of(7);
			}

			@Override
			public boolean containsSourceReference(String body) {
				if (!theEmbeddedBodyWasAskedAbout) {
					theEmbeddedBodyWasAskedAbout = true;
					return theEmbeddedBodyNestsAQuote;
				}
				return theSourceBodyNestsAQuote;
			}

			@Override
			public String rewriteSourceReferenceBodies(String body, SourceBodyRewriter rewriter) {
				return rewriter.rewrite(7, body);
			}

			@Override
			public Map<Integer, NavigableMap<OffsetDateTime, String>> everyRevisionOfTheSourcesNamed(
					Set<Integer> sourceIds) {
				return Map.of();
			}
		}
	}
}
