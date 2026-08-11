package com.zfgc.zfgbb.content.renderer.templates;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.params.provider.Arguments.arguments;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.HashMap;
import com.zfgc.zfgbb.model.forum.BBCodeConfig;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Stream;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.springframework.beans.factory.ObjectProvider;
import org.springframework.transaction.PlatformTransactionManager;

import com.zfgc.zfgbb.content.ContentFormat;
import com.zfgc.zfgbb.content.renderer.RenderedTextEnricher;
import com.zfgc.zfgbb.content.renderer.SourceReferenceService;
import com.zfgc.zfgbb.content.renderer.bbcode.BBCodeRenderer;
import com.zfgc.zfgbb.content.renderer.markdown.MarkdownRenderer;
import com.zfgc.zfgbb.dao.forum.SmileyDao;
import com.zfgc.zfgbb.content.renderer.bbcode.BBCodeGrammar;
import com.zfgc.zfgbb.content.renderer.bbcode.BBCodeGrammarHolder;
import com.zfgc.zfgbb.model.forum.AttributeDataType;
import com.zfgc.zfgbb.model.forum.AttributeValuePolicy;
import com.zfgc.zfgbb.model.forum.BBCodeAttribute;
import com.zfgc.zfgbb.model.forum.BBCodeAttributeMode;
import com.zfgc.zfgbb.content.ContentScope;
import com.zfgc.zfgbb.dataprovider.cms.WikiDataProvider;
import com.zfgc.zfgbb.dao.cms.ContentResourceDao;
import com.zfgc.zfgbb.model.users.User;
import com.zfgc.zfgbb.model.cms.WikiPage;
import com.zfgc.zfgbb.services.cms.wiki.WikiModerationService.TemplateSourceDirective;

import tools.jackson.databind.ObjectMapper;

class TemplateTest {

	@Nested
	class DataFetching {

		public static class FakeService implements TemplateDataService {

			@TemplateSource("/widgets/card")
			public Map<String, Object> card(String slug, Integer limit) {
				return Map.of("got", slug + ":" + limit);
			}

			@TemplateSource("/widgets/{widgetId}")
			public Map<String, Object> byId(Integer widgetId, User user) {
				return Map.of("id", widgetId, "viewer", user.getDisplayName());
			}

			public Map<String, Object> unexposed() {
				return Map.of("secret", true);
			}
		}

		private TemplateDataFetcher fetcher;

		@BeforeEach
		@SuppressWarnings("unchecked")
		void setUp() {
			ObjectProvider<TemplateDataService> services = mock(ObjectProvider.class);
			when(services.stream()).thenAnswer(invocation -> List.<TemplateDataService>of(new FakeService()).stream());
			fetcher = new TemplateDataFetcher(services, new ObjectMapper(),
					mock(PlatformTransactionManager.class), 4);
		}

		@Test
		@SuppressWarnings("unchecked")
		void bindsQueryParamsByName() {
			Map<String, Object> result = (Map<String, Object>) fetcher.fetch("/widgets/card?slug=zelda&limit=3");

			assertEquals("zelda:3", result.get("got"));
		}

		@Test
		@SuppressWarnings("unchecked")
		void bindsTrailingVariableAndInjectsGuest() {
			Map<String, Object> result = (Map<String, Object>) fetcher.fetch("/widgets/17");

			assertEquals(17, result.get("id"));
			assertEquals("Friend", result.get("viewer"));
		}

		@Test
		@SuppressWarnings("unchecked")
		void decodesEncodedValues() {
			Map<String, Object> result = (Map<String, Object>) fetcher.fetch("/widgets/card?slug=a%2Fb&limit=1");

			assertEquals("a/b:1", result.get("got"));
		}

		@Test
		void unknownPathsReturnNull() {
			assertNull(fetcher.fetch("/widgets/card/extra"));
			assertNull(fetcher.fetch("/unexposed"));
			assertNull(fetcher.fetch("/widgets/"));
			assertNull(fetcher.fetch("widgets/card"));
			assertNull(fetcher.fetch(null));
		}

		@Test
		void canResolveAcceptsRegisteredExactPaths() {
			assertTrue(fetcher.canResolve("/widgets/card"));
			assertTrue(fetcher.canResolve("/widgets/card?slug={slug}&limit={limit}"));
		}

		@Test
		void canResolveAcceptsRegisteredTrailingVariablePaths() {
			assertTrue(fetcher.canResolve("/widgets/{widgetId}"));
			assertTrue(fetcher.canResolve("/widgets/17?extra=1"));
		}

		@Test
		void canResolveRejectsUnknownPaths() {
			assertFalse(fetcher.canResolve("/nowhere"));
			assertFalse(fetcher.canResolve("/widgets/card/extra"));
			assertFalse(fetcher.canResolve("/unexposed"));
			assertFalse(fetcher.canResolve("widgets/card"));
			assertFalse(fetcher.canResolve(null));
		}

		@Test
		void badParamTypesDegradeToNull() {
			assertNull(fetcher.fetch("/widgets/notanumber"));
		}

		@Test
		void malformedEncodingDegradesToNull() {
			assertNull(fetcher.fetch("/widgets/card?slug=5%&limit=1"));
		}

		@Test
		void duplicatePathsFailRegistration() {
			TemplateDataFetcher broken = fetcherOver(new FakeService(), new DuplicateService());

			assertThrows(IllegalStateException.class, broken::initializeSources);
			assertNull(broken.fetch("/widgets/card?slug=zelda&limit=1"));
		}

		@Test
		void unsupportedParamTypesFailRegistration() {
			TemplateDataFetcher broken = fetcherOver(new UnsupportedTypeService());

			assertThrows(IllegalStateException.class, broken::initializeSources);
		}

		@Test
		void nestedFetchesAreRefused() {
			ReentrantService service = new ReentrantService();
			TemplateDataFetcher reentrant = fetcherOver(service);
			service.fetcher = reentrant;

			@SuppressWarnings("unchecked")
			Map<String, Object> result = (Map<String, Object>) reentrant.fetch("/reentrant");

			assertEquals("null", result.get("inner"));
		}

		@Test
		@SuppressWarnings("unchecked")
		void writeOnlyFieldsNeverReachTemplates() {
			Map<String, Object> result = (Map<String, Object>) fetcherOver(new DistilledService()).fetch("/distilled");

			assertEquals("safe", result.get("rendered"));
			assertFalse(result.containsKey("raw"), String.valueOf(result));
		}

		@SuppressWarnings("unchecked")
		private static TemplateDataFetcher fetcherOver(TemplateDataService... beans) {
			ObjectProvider<TemplateDataService> services = mock(ObjectProvider.class);
			when(services.stream()).thenAnswer(invocation -> List.of(beans).stream());
			return new TemplateDataFetcher(services, new ObjectMapper(), mock(PlatformTransactionManager.class), 4);
		}

		public static class DuplicateService implements TemplateDataService {

			@TemplateSource("/widgets/card")
			public Map<String, Object> shadow(String slug) {
				return Map.of();
			}
		}

		public static class UnsupportedTypeService implements TemplateDataService {

			@TemplateSource("/flagged")
			public Map<String, Object> flagged(Boolean flag) {
				return Map.of();
			}
		}

		public static class ReentrantService implements TemplateDataService {

			TemplateDataFetcher fetcher;

			@TemplateSource("/reentrant")
			public Map<String, Object> reenter() {
				return Map.of("inner", String.valueOf(fetcher.fetch("/reentrant")));
			}
		}

		public static class Distilled {

			@com.fasterxml.jackson.annotation.JsonProperty(access = com.fasterxml.jackson.annotation.JsonProperty.Access.WRITE_ONLY)
			public String raw = "SECRET";
			public String rendered = "safe";
		}

		public static class DistilledService implements TemplateDataService {

			@TemplateSource("/distilled")
			public Distilled distilled() {
				return new Distilled();
			}
		}
	}

	@Nested
	class Expansion {

		private ContentTemplateCatalog templates;
		private TemplateDataFetcher fetcher;
		private WikiDataProvider wikiPages;
		private BBCodeGrammarHolder holder;
		private TemplateExpander expander;
		private MarkdownRenderer markdownLane;
		private BBCodeRenderer bbCodeLane;

		@BeforeEach
		void setUp() {
			templates = mock(ContentTemplateCatalog.class);
			fetcher = mock(TemplateDataFetcher.class);
			wikiPages = mock(WikiDataProvider.class);
			holder = new BBCodeGrammarHolder();
			holder.publish(grammarDeclaring(templateConfig()));
			expander = new TemplateExpander(mock(ContentResourceDao.class), templates, fetcher, wikiPages,
					holder);
			SourceReferenceService sourceReferences = new SourceReferenceService(List.of(), holder);
			BBCodeRenderer bbCodeRenderer =
					new BBCodeRenderer(holder, sourceReferences, new TemplateExpansion(expander, holder));
			markdownLane = new MarkdownRenderer(holder, new RenderedTextEnricher(mock(SmileyDao.class)),
					sourceReferences, bbCodeRenderer, new TemplateExpansion(expander, holder));
			bbCodeLane = bbCodeRenderer;
		}

		private static BBCodeGrammar grammarDeclaringVerbatimCode() {
			return grammarDeclaring(codeConfig(), templateConfig());
		}

		private static BBCodeGrammar grammarDeclaring(BBCodeConfig... configs) {
			Map<String, BBCodeConfig> byCode = new HashMap<>();
			for (BBCodeConfig config : configs)
				byCode.put(config.getCode().toUpperCase(), config);
			return new BBCodeGrammar(byCode, Map.of(), AttributeValuePolicy.rejectingEveryValue(""), List.of(),
					BBCodeGrammar.NO_SOURCE_REFERENCE_IS_DECLARED, BBCodeGrammar.NO_CUSTOM_PROPERTY_IS_DECLARED);
		}

		@Test
		void aDocumentNamingMoreInvocationsThanTheBudgetLeavesTheRestAsAuthored() {
			defineMarkupTemplate("zfgc", "EXPANDED");
			int invocations = TemplateExpansion.MAX_INVOCATIONS_PER_DOCUMENT + 50;
			String source = "[template=zfgc][/template]".repeat(invocations);

			String result = bbCodeLane.render(source, null, ContentScope.ALL, Map.of());

			assertEquals(TemplateExpansion.MAX_INVOCATIONS_PER_DOCUMENT,
					result.split("EXPANDED", -1).length - 1,
					"every invocation costs a catalog lookup and possibly a data fetch, so one stored document "
							+ "cannot buy unbounded work: " + result.length());
			assertTrue(result.endsWith("[template=zfgc][/template]"),
					"invocations past the budget stay exactly as the author wrote them: " + result.substring(
							Math.max(0, result.length() - 60)));
		}

		@Test
		void theFenceGuardReadsMarkdownTheWayTheLaneItselfDoes() {
			defineMarkupTemplate("zfgc", "EXPANDED");
			holder.publish(grammarDeclaring(quoteConfig(), codeConfig(),
					templateConfig()));

			String result = markdownLane.render("[quote]\n    [template=zfgc][/template]\n[/quote]",
					null, ContentScope.ALL, Map.of());

			assertFalse(result.contains("EXPANDED"),
					"the lane's own parser opens a bbcode block container here, which makes the indented line "
							+ "an indented code block; a second stock parser sees only a lazy paragraph and would "
							+ "expand the template into what the reader is shown as literal code: " + result);
		}

		@Test
		void aTemplateInsideAMarkdownCodeSpanIsNeverExpanded() {
			defineMarkupTemplate("zfgc", "EXPANDED");

			String result = markdownLane.render("Write `[template=zfgc][/template]` to transclude.",
					null, ContentScope.ALL, Map.of());

			assertTrue(result.contains("<code>[template=zfgc][/template]</code>"),
					"the markdown lane renders an inline code span literally, and RenderingTest pins that a "
							+ "code span protects bbcode exactly as a fence does; expansion must honour the same "
							+ "invariant or a wiki page documenting template syntax rewrites itself: " + result);
		}

		@Test
		void aFileReferenceInsideAMarkdownCodeSpanStaysLiteral() {
			String result = markdownLane.render("Write `[[File:Shield.png]]` to embed.",
					null, ContentScope.ALL, Map.of());

			assertTrue(result.contains("<code>[[File:Shield.png]]</code>"),
					"a file reference inside backticks is documentation; with no matching resource row it "
							+ "would otherwise degrade to the bare filename: " + result);
		}

		@Test
		void aTemplateInsideAMarkdownFenceIsNeverExpanded() {
			defineMarkupTemplate("zfgc", "EXPANDED");

			String result = markdownLane.render("```\n[template=zfgc][/template]\n```\n\n[template=zfgc][/template]",
					null, ContentScope.ALL, Map.of());

			assertTrue(result.contains("[template=zfgc][/template]"),
					"expansion runs before commonmark parses, so a fenced block documenting template syntax "
							+ "used to be expanded anyway; the fence must protect it: " + result);
			assertTrue(result.contains("<p>EXPANDED</p>"),
					"the invocation outside the fence still expands: " + result);
		}

		@Test
		void aFileReferenceInsideAMarkdownFenceStaysLiteral() {
			String result = markdownLane.render("```\n[[File:Shield.png]]\n```\n\n[[File:Shield.png]]",
					null, ContentScope.ALL, Map.of());

			assertTrue(result.contains("[[File:Shield.png]]"),
					"a file reference inside a fence is documentation, not a reference: " + result);
			assertTrue(result.contains("<p>Shield.png</p>"),
					"the reference outside the fence still resolves: " + result);
		}

		@Test
		void aTemplateInvocationInsideAVerbatimBodyIsNeverExpanded() {
			defineMarkupTemplate("zfgc", "EXPANDED");
			holder.publish(grammarDeclaringVerbatimCode());

			String result = bbCodeLane.render(
					"[code][template=zfgc][/template][/code] [template=zfgc][/template]",
					null, ContentScope.ALL, Map.of());

			assertTrue(result.contains("[template=zfgc][/template]"),
					"a template invocation pasted inside a verbatim body is the author documenting the "
							+ "syntax, not invoking it: " + result);
			assertTrue(result.contains("EXPANDED"),
					"the invocation outside the verbatim body still expands: " + result);
		}


		@Test
		void aFileReferenceInsideAVerbatimBodyStaysLiteral() {
			holder.publish(grammarDeclaringVerbatimCode());

			String result = bbCodeLane.render("[code][[File:Shield.png]][/code] [[File:Shield.png]]",
					null, ContentScope.ALL, Map.of());

			assertTrue(result.contains("[[File:Shield.png]]"),
					"a file reference pasted inside a verbatim body stays what the author typed: " + result);
			assertTrue(result.contains(" Shield.png"),
					"the reference outside the verbatim body still resolves: " + result);
		}

		private void defineTemplate(String name, String source, String body) {
			ContentTemplateCatalog.Template template = new ContentTemplateCatalog.Template(source, body, ContentScope.ALL);
			when(templates.lookup(eq(name), any(), any()))
					.thenReturn(new ContentTemplateCatalog.Lookup(template, true));
			when(templates.find(eq(name), any(), any())).thenReturn(template);
		}

		private void defineMarkupTemplate(String name, String body) {
			defineTemplate(name, null, body);
		}

		@Test
		void interpolatedValuesCannotInvokeTemplates() {
			defineTemplate("outer", "/x", "{{note}}");
			defineMarkupTemplate("zfgc", "GOTCHA");
			when(fetcher.fetch("/x")).thenReturn(Map.of("note", "hi [template=zfgc][/template] there"));

			String result = bbCodeLane.render("[template=outer][/template]", null, ContentScope.ALL, Map.of());

			assertTrue(result.contains("hi [template=zfgc][/template] there"), result);
			assertFalse(result.contains("GOTCHA"), result);
		}

		@Test
		void nestedInvocationCarriesInterpolatedParams() {
			defineTemplate("outer", "/x", "{{#template}}child\nx={{v}}\n{{/template}}");
			defineMarkupTemplate("child", "child:{{x}}");
			when(fetcher.fetch("/x")).thenReturn(Map.of("v", "5"));

			String result = bbCodeLane.render("[template=outer][/template]", null, ContentScope.ALL, Map.of());

			assertEquals("child:5", result);
		}

		@Test
		void nestedInvocationDepthIsBounded() {
			defineTemplate("self", "/x", "x{{#template}}self{{/template}}");

			String result = bbCodeLane.render("[template=self][/template]", null, ContentScope.ALL, Map.of());

			assertEquals("xxx", result);
		}

		@Test
		void markupTemplatesExpandNestedBlocksThenInterpolateParams() {
			defineMarkupTemplate("tasks", "help out on [template=zfgc][/template], [b]{{name}}[/b]!");
			defineMarkupTemplate("zfgc", "ZFGC-LINK");

			String result = bbCodeLane.render("[template=tasks]\nname=Zelda\n[/template]", null, ContentScope.WIKI, Map.of());

			assertEquals("help out on ZFGC-LINK, [b]Zelda[/b]!", result);
		}

		@Test
		void fetchedValuesSplicedIntoMarkupTemplatesCannotInvokeTemplates() {
			defineMarkupTemplate("box", "x [template=inner][/template] y");
			defineTemplate("inner", "/x", "{{note}}");
			defineMarkupTemplate("zfgc", "GOTCHA");
			when(fetcher.fetch("/x")).thenReturn(Map.of("note", "{{#template}}zfgc\n{{/template}}"));

			String result = bbCodeLane.render("[template=box][/template]", null, ContentScope.WIKI, Map.of());

			assertFalse(result.contains("GOTCHA"), result);
		}

		@Test
		void transcludedWikiPagesExpandStructurally() {
			when(templates.lookup(eq("Navbox"), any(), any()))
					.thenReturn(new ContentTemplateCatalog.Lookup(null, false));
			defineMarkupTemplate("pagecount", "42");
			WikiPage page = new WikiPage();
			page.setContent("nav [template=pagecount][/template]");
			when(wikiPages.getWikiPageQuietly("Navbox")).thenReturn(Optional.of(page));

			String result = bbCodeLane.render("[template=Navbox][/template]", null, ContentScope.WIKI, Map.of());

			assertEquals("nav 42", result);
		}

		@Test
		void formatDateEmitsMachineReadableTime() {
			defineMarkupTemplate("stamp", "{{#formatDate}}{{ts}}{{/formatDate}}");

			String result = bbCodeLane.render("[template=stamp]\nts=2022-07-03T15:21:56Z\n[/template]",
					null, ContentScope.ALL, Map.of());

			assertTrue(result.contains("<time datetime=\"2022-07-03T15:21:56Z\">"), result);
			assertTrue(result.contains("July 3, 2022"), result);
		}
	}

	@Nested
	class SourceDirectives {

		@ParameterizedTest
		@MethodSource("directiveParseCases")
		void parseSeparatesDirectiveFromBody(String caseName, String content, boolean expectedPresent,
				String expectedSource, String expectedBody) {
			TemplateSourceDirective directive = TemplateSourceDirective.parse(content);

			assertEquals(expectedPresent, directive.directivePresent());
			assertEquals(expectedSource, directive.source());
			assertEquals(expectedBody, directive.body());
		}

		static Stream<Arguments> directiveParseCases() {
			return Stream.of(
					arguments("directiveWithValueStripsTheLineAndPreservesTheMultilineBody",
							"[source=/board/recent-activity?boardId={boardId}&limit={limit}]\n"
									+ "[b]Recent[/b]\n{{#data}}{{threadName}}\n{{/data}}",
							true, "/board/recent-activity?boardId={boardId}&limit={limit}",
							"[b]Recent[/b]\n{{#data}}{{threadName}}\n{{/data}}"),
					arguments("emptyDirectiveClearsTheSourceAndStripsTheLine",
							"[source=]\nplain body", true, null, "plain body"),
					arguments("absentDirectiveLeavesContentUntouched",
							"plain body\nwith more lines", false, null, "plain body\nwith more lines"),
					arguments("malformedDirectivesAreOrdinaryContent unterminatedBracket",
							"[source=/x", false, null, "[source=/x"),
					arguments("malformedDirectivesAreOrdinaryContent leadingWhitespace",
							" [source=/x]\nbody", false, null, " [source=/x]\nbody"),
					arguments("malformedDirectivesAreOrdinaryContent trailingJunkOnDirectiveLine",
							"[source=/x]trailing junk\nbody", false, null, "[source=/x]trailing junk\nbody"),
					arguments("malformedDirectivesAreOrdinaryContent directiveNotOnFirstLine",
							"body first\n[source=/x]\nmore", false, null, "body first\n[source=/x]\nmore"),
					arguments("directiveOnlyContentYieldsAnEmptyBody",
							"[source=/wiki/meta/statistics]", true, "/wiki/meta/statistics", ""),
					arguments("windowsLineEndingsAreStrippedWithTheDirective",
							"[source=/projects/news]\r\nbody", true, "/projects/news", "body"),
					arguments("nullContentParsesAsAnAbsentDirectiveOverAnEmptyBody",
							null, false, null, ""));
		}
	}

	// R__03_bbcodes.sql:139 -- create_attribute_data_type('TEXT', 'Plain text', 2, null, '', true, false, null, null)
	private static final AttributeValuePolicy PLAIN_TEXT =
			new AttributeValuePolicy(Optional.empty(), "", true, false, Optional.empty(), Set.of(), Map.of());

	// mirrors R__03_bbcodes.sql:380 (attr mode 78); the other two only need to be declared, not faithful
	private static BBCodeConfig templateConfig() {
		BBCodeAttribute name = new BBCodeAttribute();
		name.setName(BBCodeConfig.NAMELESS_ATTRIBUTE_NAME);
		name.setAttributeIndex("{{0}}");
		name.setDataType(AttributeDataType.TEXT);
		name.setValuePolicy(PLAIN_TEXT);

		BBCodeAttributeMode invocation = new BBCodeAttributeMode();
		invocation.setOpenTag("<div class=\"bb-code-template\" data-resource=\"template\" "
				+ "data-template-name=\"{{0}}\">");
		invocation.setCloseTag("</div>");
		invocation.setAttributes(new ArrayList<>(List.of(name)));

		BBCodeConfig template = new BBCodeConfig();
		template.setCode("template");
		template.setProcessContentFlag(false);
		template.setEndTag("</div>");
		template.setAllAttributeNamesAsString(BBCodeConfig.NAMELESS_ATTRIBUTE_NAME);
		template.getAttributeConfig().put(BBCodeConfig.NAMELESS_ATTRIBUTE_NAME, invocation);
		template.setValuePolicyByAttributeName(Map.of(BBCodeConfig.NAMELESS_ATTRIBUTE_NAME, PLAIN_TEXT));
		return template;
	}

	private static BBCodeConfig codeConfig() {
		return singleModeTag("code", "<code>", "</code>", false);
	}

	private static BBCodeConfig quoteConfig() {
		return singleModeTag("quote", "<div class=\"bb-q\"><div class=\"bb-qb\">", "</div></div>", true);
	}

	private static BBCodeConfig singleModeTag(String code, String openTag, String closeTag, boolean processContent) {
		BBCodeAttributeMode only = new BBCodeAttributeMode();
		only.setOpenTag(openTag);
		only.setCloseTag(closeTag);

		BBCodeConfig config = new BBCodeConfig();
		config.setCode(code);
		config.setProcessContentFlag(processContent);
		config.setEndTag(closeTag);
		config.setAllAttributeNamesAsString("");
		config.getAttributeConfig().put("", only);
		return config;
	}
}
