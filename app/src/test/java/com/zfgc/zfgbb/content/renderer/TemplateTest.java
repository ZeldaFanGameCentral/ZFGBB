package com.zfgc.zfgbb.content.renderer;

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

import java.util.List;
import java.util.Map;
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
import com.zfgc.zfgbb.content.ContentScope;
import com.zfgc.zfgbb.dataprovider.cms.WikiDataProvider;
import com.zfgc.zfgbb.mappers.ContentResourceDboMapper;
import com.zfgc.zfgbb.model.User;
import com.zfgc.zfgbb.model.cms.WikiPage;
import com.zfgc.zfgbb.services.cms.WikiModerationService.TemplateSourceDirective;

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
					mock(PlatformTransactionManager.class));
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
		void productionSourcesRegisterWithSeededParamNames() {
			TemplateDataFetcher production = fetcherOver(
					org.mockito.Mockito.mock(com.zfgc.zfgbb.services.cms.ProjectService.class),
					org.mockito.Mockito.mock(com.zfgc.zfgbb.services.cms.WikiService.class),
					org.mockito.Mockito.mock(com.zfgc.zfgbb.services.forum.ForumService.class),
					org.mockito.Mockito.mock(com.zfgc.zfgbb.services.core.UserService.class));

			Map<String, List<String>> sources = production.describeSources();

			assertEquals(Set.of("/projects/card", "/projects/news", "/projects/{slug}", "/board/recent-activity",
					"/thread/{threadId}", "/wiki/meta/statistics", "/wiki/meta/category", "/user-profile/{userId}"),
					sources.keySet());
			assertEquals(List.of("slug"), sources.get("/projects/card"));
			assertEquals(List.of("slug", "limit"), sources.get("/projects/news"));
			assertEquals(List.of("slug"), sources.get("/projects/{slug}"));
			assertEquals(List.of("boardId", "limit"), sources.get("/board/recent-activity"));
			assertEquals(List.of("threadId", "page", "pageSize"), sources.get("/thread/{threadId}"));
			assertEquals(List.of(), sources.get("/wiki/meta/statistics"));
			assertEquals(List.of("name"), sources.get("/wiki/meta/category"));
			assertEquals(List.of("userId"), sources.get("/user-profile/{userId}"));
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
			return new TemplateDataFetcher(services, new ObjectMapper(), mock(PlatformTransactionManager.class));
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

		private ContentTemplateService templates;
		private TemplateDataFetcher fetcher;
		private WikiDataProvider wikiPages;
		private TemplateExpander expander;

		@BeforeEach
		void setUp() {
			templates = mock(ContentTemplateService.class);
			fetcher = mock(TemplateDataFetcher.class);
			wikiPages = mock(WikiDataProvider.class);
			expander = new TemplateExpander(mock(ContentResourceDboMapper.class), templates, fetcher, wikiPages);
		}

		private void defineTemplate(String name, String source, String body) {
			ContentTemplateService.Template template = new ContentTemplateService.Template(source, body, ContentScope.ALL);
			when(templates.lookup(eq(name), any(), any()))
					.thenReturn(new ContentTemplateService.Lookup(template, true));
			when(templates.find(eq(name), any(), any())).thenReturn(template);
			when(templates.isDefined(name)).thenReturn(true);
		}

		private void defineMarkupTemplate(String name, String body) {
			defineTemplate(name, null, body);
		}

		@Test
		void interpolatedValuesCannotInvokeTemplates() {
			defineTemplate("outer", "/x", "{{note}}");
			defineMarkupTemplate("zfgc", "GOTCHA");
			when(fetcher.fetch("/x")).thenReturn(Map.of("note", "hi [template=zfgc][/template] there"));

			String result = expander.expand("[template=outer][/template]", ContentFormat.BBCODE, ContentScope.ALL);

			assertTrue(result.contains("hi [template=zfgc][/template] there"), result);
			assertFalse(result.contains("GOTCHA"), result);
		}

		@Test
		void nestedInvocationCarriesInterpolatedParams() {
			defineTemplate("outer", "/x", "{{#template}}child\nx={{v}}\n{{/template}}");
			defineMarkupTemplate("child", "child:{{x}}");
			when(fetcher.fetch("/x")).thenReturn(Map.of("v", "5"));

			String result = expander.expand("[template=outer][/template]", ContentFormat.BBCODE, ContentScope.ALL);

			assertEquals("child:5", result);
		}

		@Test
		void nestedInvocationDepthIsBounded() {
			defineTemplate("self", "/x", "x{{#template}}self{{/template}}");

			String result = expander.expand("[template=self][/template]", ContentFormat.BBCODE, ContentScope.ALL);

			assertEquals("xxx", result);
		}

		@Test
		void markupTemplatesExpandNestedBlocksThenInterpolateParams() {
			defineMarkupTemplate("tasks", "help out on [template=zfgc][/template], [b]{{name}}[/b]!");
			defineMarkupTemplate("zfgc", "ZFGC-LINK");

			String result = expander.expand("[template=tasks]\nname=Zelda\n[/template]",
					ContentFormat.BBCODE, ContentScope.WIKI);

			assertEquals("help out on ZFGC-LINK, [b]Zelda[/b]!", result);
		}

		@Test
		void fetchedValuesSplicedIntoMarkupTemplatesCannotInvokeTemplates() {
			defineMarkupTemplate("box", "x [template=inner][/template] y");
			defineTemplate("inner", "/x", "{{note}}");
			defineMarkupTemplate("zfgc", "GOTCHA");
			when(fetcher.fetch("/x")).thenReturn(Map.of("note", "{{#template}}zfgc\n{{/template}}"));

			String result = expander.expand("[template=box][/template]", ContentFormat.BBCODE, ContentScope.WIKI);

			assertFalse(result.contains("GOTCHA"), result);
		}

		@Test
		void transcludedWikiPagesExpandStructurally() {
			when(templates.lookup(eq("Navbox"), any(), any()))
					.thenReturn(new ContentTemplateService.Lookup(null, false));
			defineMarkupTemplate("pagecount", "42");
			WikiPage page = new WikiPage();
			page.setContent("nav [template=pagecount][/template]");
			when(wikiPages.getWikiPageQuietly("Navbox")).thenReturn(java.util.Optional.of(page));

			String result = expander.expand("[template=Navbox][/template]", ContentFormat.BBCODE, ContentScope.WIKI);

			assertEquals("nav 42", result);
		}

		@Test
		void formatDateEmitsMachineReadableTime() {
			defineMarkupTemplate("stamp", "{{#formatDate}}{{ts}}{{/formatDate}}");

			String result = expander.expand("[template=stamp]\nts=2022-07-03T15:21:56Z\n[/template]",
					ContentFormat.BBCODE, ContentScope.ALL);

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
}
