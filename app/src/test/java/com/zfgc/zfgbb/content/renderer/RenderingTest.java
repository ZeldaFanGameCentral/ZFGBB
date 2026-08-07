package com.zfgc.zfgbb.content.renderer;

import static com.zfgc.zfgbb.testsupport.BBCodeTestFixtures.QUOTE_HEADER;
import static com.zfgc.zfgbb.testsupport.BBCodeTestFixtures.seededAttributeValuePolicies;
import static com.zfgc.zfgbb.testsupport.BBCodeTestFixtures.seededListStyleTypes;
import static com.zfgc.zfgbb.testsupport.BBCodeTestFixtures.attr;
import static com.zfgc.zfgbb.testsupport.BBCodeTestFixtures.mode;
import static com.zfgc.zfgbb.testsupport.BBCodeTestFixtures.codeConfig;
import static com.zfgc.zfgbb.testsupport.BBCodeTestFixtures.indexTheValuePoliciesOfEveryDeclaredAttribute;
import static com.zfgc.zfgbb.testsupport.BBCodeTestFixtures.messageResolver;
import static com.zfgc.zfgbb.testsupport.BBCodeTestFixtures.quoteConfig;
import static com.zfgc.zfgbb.testsupport.BBCodeTestFixtures.resolved;
import static com.zfgc.zfgbb.testsupport.BBCodeTestFixtures.seededBBCodeGrammar;
import static com.zfgc.zfgbb.testsupport.BBCodeTestFixtures.seededBBCodeTemplateBodies;
import static com.zfgc.zfgbb.testsupport.BBCodeTestFixtures.seededSmilies;
import static com.zfgc.zfgbb.testsupport.BBCodeTestFixtures.simpleTag;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertSame;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.params.provider.Arguments.arguments;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.spy;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.regex.Pattern;
import java.time.OffsetDateTime;
import java.time.ZoneOffset;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Base64;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.NavigableMap;
import java.util.Optional;
import java.util.Set;
import java.util.TimeZone;
import java.util.TreeMap;
import java.util.TreeSet;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import java.util.stream.Stream;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Element;
import org.jsoup.nodes.Entities;
import org.mockito.ArgumentCaptor;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.condition.EnabledIfSystemProperty;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import com.zfgc.zfgbb.content.ContentFormat;
import com.zfgc.zfgbb.content.ContentScope;
import com.zfgc.zfgbb.content.renderer.RenderedTextEnricher.SmileyToken;
import com.zfgc.zfgbb.content.renderer.bbcode.AttributeTokenizer;
import com.zfgc.zfgbb.content.renderer.bbcode.BBCodeParser;
import com.zfgc.zfgbb.content.renderer.bbcode.BBCodeGrammarLoader;
import com.zfgc.zfgbb.content.renderer.bbcode.BBCodeDocument;
import com.zfgc.zfgbb.content.renderer.bbcode.BBCodeGrammar;
import com.zfgc.zfgbb.content.renderer.bbcode.BBCodeGrammarHolder;
import com.zfgc.zfgbb.content.renderer.bbcode.RetiredBBCodeRewriter;
import com.zfgc.zfgbb.content.renderer.bbcode.BBCodeRenderer;
import com.zfgc.zfgbb.content.renderer.bbcode.BBCodeNode;
import com.zfgc.zfgbb.content.renderer.bbcode.BBCodeTag;
import com.zfgc.zfgbb.content.renderer.bbcode.ContentLevel;
import com.zfgc.zfgbb.content.renderer.markdown.MarkdownRenderer;
import com.zfgc.zfgbb.model.cms.WikiPage;
import com.zfgc.zfgbb.services.cms.CmsPageRenderer;
import com.zfgc.zfgbb.testsupport.BBCodeFuzzGenerator;
import com.zfgc.zfgbb.testsupport.RenderedOutputComparison;
import com.zfgc.zfgbb.testsupport.RenderedOutputComparison.DivergenceFamily;
import com.zfgc.zfgbb.dao.bbcode.BBCodeConfigDao;
import com.zfgc.zfgbb.content.renderer.templates.TemplateExpander;
import com.zfgc.zfgbb.content.renderer.templates.TemplateExpansion;
import com.zfgc.zfgbb.dataprovider.forum.BBCodeDataProvider;
import com.zfgc.zfgbb.dataprovider.forum.QuotedMessageDataProvider;
import com.zfgc.zfgbb.services.forum.QuotedMessageSource;
import com.zfgc.zfgbb.dbo.AttributeDataTypeDboExample;
import com.zfgc.zfgbb.dbo.AttributeValueMappingDboExample;
import com.zfgc.zfgbb.dbo.ListStyleTypeDboExample;
import com.zfgc.zfgbb.dbo.BBCodeAttributeDbo;
import com.zfgc.zfgbb.dbo.BBCodeAttributeDboExample;
import com.zfgc.zfgbb.dbo.BBCodeAttributeModeDbo;
import com.zfgc.zfgbb.dbo.BBCodeAttributeModeDboExample;
import com.zfgc.zfgbb.dbo.BBCodeConfigDbo;
import com.zfgc.zfgbb.dbo.BBCodeConfigDboExample;
import com.zfgc.zfgbb.dbo.MessageDbo;
import com.zfgc.zfgbb.dbo.MessageDboExample;
import com.zfgc.zfgbb.dbo.MessageHistoryDbo;
import com.zfgc.zfgbb.dbo.MessageHistoryDboExample;
import com.zfgc.zfgbb.dbo.UserDbo;
import com.zfgc.zfgbb.dbo.UserDboExample;
import com.zfgc.zfgbb.mappers.AttributeDataTypeDboMapper;
import com.zfgc.zfgbb.mappers.AttributeValueMappingDboMapper;
import com.zfgc.zfgbb.mappers.ListStyleTypeDboMapper;
import com.zfgc.zfgbb.mappers.BBCodeAttributeDboMapper;
import com.zfgc.zfgbb.mappers.BBCodeAttributeModeDboMapper;
import com.zfgc.zfgbb.mappers.MessageDboMapper;
import com.zfgc.zfgbb.mappers.MessageHistoryDboMapper;
import com.zfgc.zfgbb.mappers.SmileyDboMapper;
import com.zfgc.zfgbb.mappers.UserDboMapper;
import com.zfgc.zfgbb.mapstruct.forum.BBCodeAttributeMap;
import com.zfgc.zfgbb.mapstruct.forum.BBCodeAttributeModeMap;
import com.zfgc.zfgbb.mapstruct.forum.BBCodeConfigMap;
import com.zfgc.zfgbb.model.forum.AttributeDataType;
import com.zfgc.zfgbb.model.forum.AttributeSemanticRole;
import com.zfgc.zfgbb.model.forum.AttributeValuePolicy;
import com.zfgc.zfgbb.model.forum.BBCodeAttribute;
import com.zfgc.zfgbb.model.forum.BBCodeAttributeMode;
import com.zfgc.zfgbb.model.forum.BBCodeConfig;
import com.zfgc.zfgbb.exception.InvalidBBCodeGrammarException;
import com.zfgc.zfgbb.model.forum.BBCodeDateElement;
import com.zfgc.zfgbb.model.forum.MarkdownEquivalent;
import com.zfgc.zfgbb.security.LinkPolicy;

class RenderingTest {

	@Nested
	class BBCode {
		static BBCodeRenderer bbCodeRenderer;
		static Renderer renderer;
		static SourceReferenceService handler;
		static BBCodeConfig bbCodeQuote = null;

		static final String TIME_ELEMENT_1494552503 =
				"<time class=\"bb-date-long\" datetime=\"2017-05-12T01:28:23Z\">May 12, 2017, 1:28:23 AM</time>";
		static final String TIME_ELEMENT_1494552504 =
				"<time class=\"bb-date-long\" datetime=\"2017-05-12T01:28:24Z\">May 12, 2017, 1:28:24 AM</time>";

		@BeforeAll
		public static void initialize() {
			renderer = buildRenderer(mock(BBCodeDataProvider.class), messageResolver());
			bbCodeRenderer = renderer.bbCodeRenderer();
			handler = renderer.handler();

			Map<String, BBCodeConfig> grammar = new HashMap<>();
			initQuote(grammar);
			initUrl(grammar);
			initImg(grammar);
			grammar.put("CODE",
					simpleTag("code", "<span class=\"bb-code-header\">Code</span><span class=\"bb-code-block\">",
							"</span>", false));
			grammar.put("B", simpleTag("b", "<span class=\"bb-b\">", "</span>", true));
			grammar.put("I", simpleTag("i", "<span class=\"bb-i\">", "</span>", true));
			grammar.put("U", simpleTag("u", "<span class=\"bb-u\">", "</span>", true));
			grammar.put("H2", simpleTag("h2", "<h2 class=\"bb-h2\">", "</h2>", true));
			renderer.useGrammar(grammar);
		}

		private static void initQuote(Map<String, BBCodeConfig> grammar) {
			bbCodeQuote = new BBCodeConfig();
			bbCodeQuote.setAllAttributeNamesAsString("author=,link=,time=,=");

			BBCodeAttributeMode mode0 = new BBCodeAttributeMode();
			mode0.setOpenTag(
					"<span class=\"bb-quote-header\"><a href=\"{{1}}\">Authored by {{0}} at {{2}}</a></span><span class=\"bb-quote-block\">");
			mode0.setCloseTag("</span>");

			List<BBCodeAttribute> mode0Att = new ArrayList<>();
			BBCodeAttribute author = new BBCodeAttribute();
			author.setAttributeIndex("{{0}}");
			author.setDataType(AttributeDataType.TEXT);
			author.setName("author=");

			BBCodeAttribute link = new BBCodeAttribute();
			link.setAttributeIndex("{{1}}");
			link.setDataType(AttributeDataType.TEXT);
			link.setName("link=");

			BBCodeAttribute time = new BBCodeAttribute();
			time.setAttributeIndex("{{2}}");
			time.setDataType(AttributeDataType.TIMESTAMP);
			time.setName("time=");

			mode0Att.add(author);
			mode0Att.add(link);
			mode0Att.add(time);

			mode0.setAttributes(mode0Att);
			bbCodeQuote.setAttributeConfig(new HashMap<>());
			bbCodeQuote.getAttributeConfig().put("author=link=time=", mode0);
			bbCodeQuote.setCode("quote");
			bbCodeQuote.setProcessContentFlag(true);
			bbCodeQuote.setEndTag("</span>");

			BBCodeAttributeMode mode1 = new BBCodeAttributeMode();
			mode1.setOpenTag("<span class=\"bb-quote-header\">Authored by {{0}}</span><span class=\"bb-quote-block\">");
			mode1.setCloseTag("</span>");

			List<BBCodeAttribute> mode1Att = new ArrayList<>();
			BBCodeAttribute author1 = new BBCodeAttribute();
			author1.setAttributeIndex("{{0}}");
			author1.setDataType(AttributeDataType.TEXT);
			author1.setName("author=");
			mode1Att.add(author1);

			mode1.setAttributes(mode1Att);
			bbCodeQuote.getAttributeConfig().put("author=", mode1);

			BBCodeAttributeMode modeNameless = new BBCodeAttributeMode();
			modeNameless.setOpenTag(
					"<span class=\"bb-quote-header\">Authored by {{0}}</span><span class=\"bb-quote-block\">");
			modeNameless.setCloseTag("</span>");

			List<BBCodeAttribute> modeNamelessAtt = new ArrayList<>();
			BBCodeAttribute nameless = new BBCodeAttribute();
			nameless.setAttributeIndex("{{0}}");
			nameless.setDataType(AttributeDataType.TEXT);
			nameless.setName("=");
			modeNamelessAtt.add(nameless);

			modeNameless.setAttributes(modeNamelessAtt);
			bbCodeQuote.getAttributeConfig().put("=", modeNameless);

			BBCodeAttributeMode empty = new BBCodeAttributeMode();
			empty.setOpenTag("<span class=\"bb-quote-header\">Quote</span><span class=\"bb-quote-block\">");
			empty.setCloseTag("</span>");
			bbCodeQuote.getAttributeConfig().put("", empty);

			grammar.put("QUOTE", bbCodeQuote);
		}

		private static void initUrl(Map<String, BBCodeConfig> grammar) {
			BBCodeConfig bbCodeUrl = new BBCodeConfig();
			bbCodeUrl.setAllAttributeNamesAsString("=");
			bbCodeUrl.setCode("url");
			bbCodeUrl.setProcessContentFlag(true);
			bbCodeUrl.setEndTag("</a>");

			BBCodeAttributeMode modeNameless = new BBCodeAttributeMode();
			modeNameless.setOpenTag("<a href=\"{{0}}\">");
			modeNameless.setCloseTag("</a>");
			BBCodeAttribute nameless = new BBCodeAttribute();
			nameless.setAttributeIndex("{{0}}");
			nameless.setDataType(AttributeDataType.TEXT);
			nameless.setName("=");
			modeNameless.setAttributes(Arrays.asList(nameless));

			bbCodeUrl.getAttributeConfig().put("=", modeNameless);

			BBCodeAttributeMode empty = new BBCodeAttributeMode();
			empty.setOpenTag("<a href=\"{{c}}\">");
			empty.setCloseTag("</a>");
			empty.setContentIsAttributeFlag(true);
			bbCodeUrl.getAttributeConfig().put("", empty);

			grammar.put("URL", bbCodeUrl);
		}

		private static void initImg(Map<String, BBCodeConfig> grammar) {
			BBCodeConfig bbCodeImg = new BBCodeConfig();
			bbCodeImg.setAllAttributeNamesAsString("");
			bbCodeImg.setCode("img");
			bbCodeImg.setProcessContentFlag(false);
			bbCodeImg.setEndTag("</span>");

			BBCodeAttributeMode none = new BBCodeAttributeMode();
			none.setOpenTag("<span class=\"bb-img\"><img src=\"{{c}}\">");
			none.setCloseTag("</span>");
			none.setContentIsAttributeFlag(true);
			none.setOutputContentFlag(false);
			bbCodeImg.getAttributeConfig().put("", none);

			grammar.put("IMG", bbCodeImg);
		}

		@Test
		void theBBCodeParserEmitsUnsanitizedHtmlSoOnlyTheChokepointCleansIt() {
			String source = "[b]<script>alert(1)</script>[/b]";

			assertTrue(bbCodeRenderer.render(source, null, ContentScope.FORUM, Map.of()).contains("<script"),
					"the parser appends author text verbatim; if it also sanitized there would be two sanitize "
							+ "points and no single place that every render path provably crosses");
			assertFalse(renderer.render(source).contains("<script"),
					"ContentRenderingService is that single place, so nothing reaches a caller uncleaned");
		}

		@ParameterizedTest
		@MethodSource("quoteRenderingCases")
		void parseTextRendersQuoteModes(String caseName, String input, String expected) {
			assertEquals(expected, renderer.render(input));
		}

		static Stream<Arguments> quoteRenderingCases() {
			return Stream.of(
					arguments("parseTextMode1Code",
							"[quote author=test]test[/quote]",
							"<span class=\"bb-quote-header\">Authored by test</span><span class=\"bb-quote-block\">test</span>"),
					arguments("parseTextMode0Code",
							"[quote author=MG-Zero link=/thread/99 time=1494552503]test[/quote]",
							"<span class=\"bb-quote-header\"><a href=\"/thread/99\">Authored by MG-Zero at " + TIME_ELEMENT_1494552503 + "</a></span><span class=\"bb-quote-block\">test</span>"),
					arguments("parseTextQuoteEmbeddedMode0",
							"[quote author=MG-Zero link=/thread/99 time=1494552503][quote author=MG-Zero link=/thread/99 time=1494552503]test[/quote][/quote]",
							"<span class=\"bb-quote-header\"><a href=\"/thread/99\">Authored by MG-Zero at " + TIME_ELEMENT_1494552503 + "</a></span><span class=\"bb-quote-block\"><span class=\"bb-quote-header\"><a href=\"/thread/99\">Authored by MG-Zero at " + TIME_ELEMENT_1494552503 + "</a></span><span class=\"bb-quote-block\">test</span></span>"),
					arguments("parseTextQuoteNoParam",
							"[quote]test[/quote]",
							"<span class=\"bb-quote-header\">Quote</span><span class=\"bb-quote-block\">test</span>"),
					arguments("parseTextQuoteTwo",
							"[quote author=MG-Zero link=/thread/99 time=1494552503]test[/quote][quote author=MG-Zero link=/thread/99 time=1494552503]test[/quote]",
							"<span class=\"bb-quote-header\"><a href=\"/thread/99\">Authored by MG-Zero at " + TIME_ELEMENT_1494552503 + "</a></span><span class=\"bb-quote-block\">test</span><span class=\"bb-quote-header\"><a href=\"/thread/99\">Authored by MG-Zero at " + TIME_ELEMENT_1494552503 + "</a></span><span class=\"bb-quote-block\">test</span>"),
					arguments("parseTextNamelessAttribute",
							"[quote=MGZero]test[/quote]",
							"<span class=\"bb-quote-header\">Authored by MGZero</span><span class=\"bb-quote-block\">test</span>"),
					arguments("parseTextUnmatchedAttributeModeRoundTrips",
							"[quote author=a link=b]test[/quote]",
							"[quote author=a link=b]test[/quote]"),
					arguments("parseTextQuoteAuthorBearingABracketCannotBeReadAsATag",
							"[quote author=-x-[Sir Lunatic link=/thread/99 time=1494552503]test[/quote]",
							"[quote author=-x-[Sir Lunatic link=/thread/99 time=1494552503]test[/quote]"),
					arguments("parseTextUnmatchedModeKeepsSurroundingStyling",
							"[b][quote link=b]Game Download[/b] after",
							"<span class=\"bb-b\">[quote link=b]Game Download</span> after"));
		}

		@ParameterizedTest
		@MethodSource("codeRenderingCases")
		void parseTextRendersCodeBlocksLiterally(String caseName, String input, String expected) {
			assertEquals(expected, renderer.render(input));
		}

		static Stream<Arguments> codeRenderingCases() {
			return Stream.of(
					arguments("parseTextCode",
							"[code]test[/code]",
							"<span class=\"bb-code-header\">Code</span><span class=\"bb-code-block\">test</span>"),
					arguments("parseTextCodeEmbedded",
							"[code]test[code]test[quote][/code]",
							"<span class=\"bb-code-header\">Code</span><span class=\"bb-code-block\">test[code]test[quote]</span>"),
					arguments("parseTextCodeEscapesMarkup",
							"[code]<map><version>1.0</version></map>[/code]",
							"<span class=\"bb-code-header\">Code</span><span class=\"bb-code-block\">"
									+ "&lt;map&gt;&lt;version&gt;1.0&lt;/version&gt;&lt;/map&gt;</span>"),
					arguments("parseTextCodeConvertsBrToNewline",
							"[code]line1<br/>line2<br />line3[/code]",
							"<span class=\"bb-code-header\">Code</span><span class=\"bb-code-block\">"
									+ "line1\nline2\nline3</span>"));
		}

		@ParameterizedTest
		@MethodSource("urlAndImgRenderingCases")
		void parseTextRendersUrlAndImgMarkup(String caseName, String input, String expected) {
			assertEquals(expected, renderer.render(input));
		}

		static Stream<Arguments> urlAndImgRenderingCases() {
			return Stream.of(
					arguments("caseMismatchTest",
							"[IMG]http://img.photobucket.com/albums/v191/legofreak1988/avy-sig/corpse.jpg[/img]",
							"<span class=\"bb-img\"><img src=\"http://img.photobucket.com/albums/v191/legofreak1988/avy-sig/corpse.jpg\"></span>"),
					arguments("parseTextUrlContent",
							"[url]http://zfgc.com[/url]",
							"<a href=\"http://zfgc.com\">http://zfgc.com</a>"),
					arguments("parseTextUrlContentEmbedded",
							"[url][b]http://zfgc.com[/b][/url]",
							"<a href=\"http://zfgc.com\"><span class=\"bb-b\">http://zfgc.com</span></a>"),
					arguments("parseTextUrlImgEmbedded",
							"[url=https://somelink.com][img]https://someimg.jpg[/img][/url]",
							"<a href=\"https://somelink.com\"><span class=\"bb-img\"><img src=\"https://someimg.jpg\"></span></a>"),
					arguments("parseTextImg",
							"[img]http://zfgc.com[/img]",
							"<span class=\"bb-img\"><img src=\"http://zfgc.com\"></span>"),
					arguments("parseTextImgStrayEmbedded",
							"[img][/b]http://zfgc.com[/img]",
							"<span class=\"bb-img\"><img></span>"));
		}

		@ParameterizedTest
		@MethodSource("strayAndNonTagCases")
		void parseTextHandlesStrayMalformedAndNonTagInput(String caseName, String input, String expected) {
			assertEquals(expected, renderer.render(input));
		}

		static Stream<Arguments> strayAndNonTagCases() {
			return Stream.of(
					arguments("parseTextUnterminatedTagRendersLiterally trailingOpenTag", "hello [b", "hello [b"),
					arguments("parseTextUnterminatedTagRendersLiterally lonelyBracket", "[", "["),
					arguments("parseTextUnterminatedTagRendersLiterally trailingBracket", "a[", "a["),
					arguments("parseTextUnterminatedTagRendersLiterally trailingCloserStart", "x[/", "x[/"),
					arguments("parseTextUnterminatedTagRendersLiterally unclosedTagName", "[url", "[url"),
					arguments("parseTextStrayClosing",
							"This is my [/quote] house",
							"This is my [/quote] house"),
					arguments("parseTextStrayOpening",
							"This is my [code] house",
							"This is my <span class=\"bb-code-header\">Code</span><span class=\"bb-code-block\"> house</span>"),
					arguments("parseTextStrayClosingEmbedded",
							"[quote author=MG-Zero]This is [/code] my house[/quote]",
							"<span class=\"bb-quote-header\">Authored by MG-Zero</span><span class=\"bb-quote-block\">This is [/code]</span> my house[/quote]"),
					arguments("parseTextStrayMismatched",
							"[b][i]This is my house[/b][/i]",
							"<span class=\"bb-b\"><span class=\"bb-i\">This is my house[/b]</span>[/i]</span>"),
					arguments("parseTextStrayClosingOutside",
							"[quote author=MG-Zero]This is my house[/quote][/code]",
							"<span class=\"bb-quote-header\">Authored by MG-Zero</span><span class=\"bb-quote-block\">This is my house</span>[/code]"),
					arguments("parseTextMattyBoyTestBadInput",
							"[b][code]test[/code][/b][b]hey[b]yo[b]wassup[b][i][u]bitch!!![/i][/u][/b][/b][/b][/b]  [i][u]yeah man[/i][/u] ",
							"<span class=\"bb-b\"><span class=\"bb-code-header\">Code</span><span class=\"bb-code-block\">test</span></span><span class=\"bb-b\">hey<span class=\"bb-b\">yo<span class=\"bb-b\">wassup<span class=\"bb-b\"><span class=\"bb-i\"><span class=\"bb-u\">bitch!!![/i]</span>[/u]</span></span></span></span></span>  <span class=\"bb-i\"><span class=\"bb-u\">yeah man[/i]</span>[/u]</span> "),
					arguments("parseTextMattyBoyTestGoodInput",
							"[b][code]test[/code][/b][b]hey[b]yo[b]wassup[b][i][u]bitch!!![/u][/i][/b][/b][/b][/b]  [i][u]yeah man[/u][/i] o",
							"<span class=\"bb-b\"><span class=\"bb-code-header\">Code</span><span class=\"bb-code-block\">test</span></span><span class=\"bb-b\">hey<span class=\"bb-b\">yo<span class=\"bb-b\">wassup<span class=\"bb-b\"><span class=\"bb-i\"><span class=\"bb-u\">bitch!!!</span></span></span></span></span></span>  <span class=\"bb-i\"><span class=\"bb-u\">yeah man</span></span> o"),
					arguments("parseTextInvalidTag",
							"i am [hr] a [hr] dumb [b]test[/b]",
							"i am [hr] a [hr] dumb <span class=\"bb-b\">test</span>"),
					arguments("parseTextDigitTag",
							"[h2]The Basics[/h2]",
							"<h2 class=\"bb-h2\">The Basics</h2>"),
					arguments("parseTextBareDigitsNotATag",
							"i am [42] years old [b]test[/b]",
							"i am [42] years old <span class=\"bb-b\">test</span>"));
		}

		@ParameterizedTest
		@MethodSource("processAttributesCases")
		void processAttributesResolvesModeOrRoundTrips(String caseName, String attributes, String expected) {
			assertEquals(expected, BBCodeParser.theTagAnOpeningCodeExpandsTo(bbCodeQuote, attributes)
					.map(BBCodeParser.ExpandedOpener::openMarkup)
					.orElseGet(() -> "[" + bbCodeQuote.getCode() + attributes + "]"),
					"the expansion is the parser's own now, so a shape no mode accepts has to come back as an "
							+ "absent tag and the caller writes the author's markup back itself");
		}

		static Stream<Arguments> processAttributesCases() {
			return Stream.of(
					arguments("processAttributesAllValidMode0",
							"author=MG-Zero link=/thread/99 time=1494552503",
							"<span class=\"bb-quote-header\"><a href=\"/thread/99\">Authored by MG-Zero at " + TIME_ELEMENT_1494552503 + "</a></span><span class=\"bb-quote-block\">"),
					arguments("processAttributesAllValidMode1",
							"author=MG-Zero",
							"<span class=\"bb-quote-header\">Authored by MG-Zero</span><span class=\"bb-quote-block\">"),
					arguments("processAttributesOneInvalid",
							" autor=test link=test time=1494552504",
							"[quote autor=test link=test time=1494552504]"),
					arguments("processAttributeskippedOne",
							" autor=test time=1494552504",
							"[quote autor=test time=1494552504]"),
					arguments("processAttributesOutOfOrder",
							"link=test author=test time=1494552504",
							"<span class=\"bb-quote-header\"><a href=\"test\">Authored by test at " + TIME_ELEMENT_1494552504 + "</a></span><span class=\"bb-quote-block\">"),
					arguments("processAttributesNamelessExtra",
							"=x link=test author=test time=1494552504",
							"[quote=x link=test author=test time=1494552504]"));
		}

	// @Test
	public void parseTextUrlContentEmbeddedStray() {
			String result = renderer.render("[url][/b]http://zfgc.com[/url]");
			// current output: "<a href=\"{{c}}\">[/b]</span>http://zfgc.com[/url]"
			// FIXME: handle stray closing tags, seems to be something with the way the
			// cursor flushes text?
			assertEquals("<a href=\"[/b]http://zfgc.com\">[/b]http://zfgc.com</a>", result);
	}

	}

	@Nested
	class ImplicitItemMarker {

		static Map<String, BBCodeConfig> grammar;
		static Renderer renderer;

		@BeforeAll
		static void loadTheRealSeededEngine() {
			grammar = seededBBCodeGrammar();
			renderer = buildRenderer(mock(BBCodeDataProvider.class), messageResolver());
			renderer.useGrammar(grammar);
		}

		@Test
		void theMarkerAndItsItemCodeComeFromTheGrammarRowRatherThanFromJava() {
			BBCodeConfig list = grammar.get("LIST");

			assertEquals(Optional.of("[*]"), list.declaredImplicitItemMarker(),
					"the marker is bb_code_config.implicit_item_marker now; a seed line that loses it must not "
							+ "leave the expansion working off a Java constant");
			assertEquals(Optional.of("li"), list.declaredImplicitItemCode());
			assertEquals(List.of(new BBCodeGrammar.ImplicitItemExpansion("list", "[*]", "li")),
					renderer.grammarHolder().current().implicitItemExpansions());
		}

		@Test
		void aGrammarDeclaringNoMarkerLeavesTheExpansionMachineryCompletelyInert() {
			Map<String, BBCodeConfig> withoutTheMarker = new HashMap<>(grammar);
			BBCodeConfig listDeclaringNothing = seededBBCodeGrammar().get("LIST");
			listDeclaringNothing.setImplicitItemMarker(null);
			listDeclaringNothing.setImplicitItemCode(null);
			withoutTheMarker.put("LIST", listDeclaringNothing);
			renderer.useGrammar(withoutTheMarker);
			try {
				assertEquals(List.of(), renderer.grammarHolder().current().implicitItemExpansions());
				assertEquals("<ul>[*]x</ul>", renderer.render("[list][*]x[/list]"),
						"with nothing in the grammar declaring an implicit item marker there is no [*] handling "
								+ "left at all, which is the proof the binding really is data");
			} finally {
				renderer.useGrammar(grammar);
			}
		}

		@Test
		void theExpansionNeverReachesIntoABodyTheGrammarKeepsLiteral() {
			assertEquals("<pre class=\"bb-code-code\">[list][*]x[/list]\n</pre>",
					renderer.render("[code]\n[list][*]x[/list]\n[/code]"),
					"the pre-pass used to run over the whole raw source, so a pasted sample inside a "
							+ "process_content_flag=false body had its markers rewritten into [li] and the sample "
							+ "stopped being the bytes the author pasted");
			assertEquals("<ul><li>x</li></ul>", renderer.render("[list][*]x[/list]"),
					"and a marker outside literal content still expands, or the fix broke lists instead");
		}

		static final String STRADDLE_DEFECT =
				"the pre-pass split the source into runs at every literal span and ran the container regex per "
						+ "run, so a [list] holding a process_content_flag=false code could never match the "
						+ "container pattern and not one [*] in it expanded; the markers rendered as raw text "
						+ "inside the <ul>";

		@Test
		void aListWhoseItemHoldsAnImageStillExpandsEveryMarker() {
			String rendered = renderer.render("[list][*]see [img]http://x/y.png[/img][*]next[/list]");

			assertEquals(renderer.render("[list][li]see [img]http://x/y.png[/img][/li][li]next[/li][/list]"),
					rendered, STRADDLE_DEFECT);
			assertTrue(rendered.contains("<li>see "), rendered);
			assertTrue(rendered.contains("<li>next</li>"), rendered);
			assertFalse(rendered.contains("[*]"), "no marker may survive into the output: " + rendered);
			assertTrue(rendered.contains("src=\"http://x/y.png\""),
					"and the literal body the straddle used to protect must still arrive untouched: " + rendered);
		}

		@Test
		void aListWhoseItemHoldsAYoutubeEmbedStillExpandsEveryMarker() {
			String rendered = renderer.render("[list][*]watch [youtube]dQw4w9WgXcQ[/youtube][*]next[/list]");

			assertEquals(renderer.render(
					"[list][li]watch [youtube]dQw4w9WgXcQ[/youtube][/li][li]next[/li][/list]"),
					rendered, STRADDLE_DEFECT);
			assertTrue(rendered.contains("<li>watch "), rendered);
			assertTrue(rendered.contains("<li>next</li>"), rendered);
			assertFalse(rendered.contains("[*]"), "no marker may survive into the output: " + rendered);
			assertTrue(rendered.contains("src=\"https://www.youtube.com/embed/dQw4w9WgXcQ\""),
					"and the embed the straddle used to protect must still arrive untouched: " + rendered);
		}

		@Test
		void aListWhoseItemHoldsACodeSampleStillExpandsEveryMarkerOutsideTheSample() {
			String rendered = renderer.render("[list][*]run [code]a[*]b[/code][*]next[/list]");

			assertEquals(renderer.render("[list][li]run [code]a[*]b[/code][/li][li]next[/li][/list]"),
					rendered, STRADDLE_DEFECT);
			assertTrue(rendered.contains("<li>run "), rendered);
			assertTrue(rendered.contains("<li>next</li>"), rendered);
			assertTrue(rendered.contains("a[*]b"),
					"the marker the author pasted inside the sample is the one marker that must NOT expand, or "
							+ "the fix traded a missing list for a rewritten code sample: " + rendered);
			assertEquals(1, rendered.split(Pattern.quote("[*]"), -1).length - 1,
					"exactly the sample's own marker survives: " + rendered);
		}

		@Test
		void aContainerCloserWrittenInsideALiteralBodyDoesNotCloseTheContainer() {
			String rendered = renderer.render("[list][*]a [code]x[/list]y[/code][*]b[/list]");

			assertTrue(rendered.contains("<li>b</li>"),
					"the [/list] the author pasted inside the code sample must not end the list, or every "
							+ "marker after the sample stays a raw [*] in the output: " + rendered);
			assertTrue(rendered.contains("x[/list]y"),
					"and the pasted closer itself stays literal text inside the sample: " + rendered);
			assertFalse(rendered.replace("x[/list]y", "").contains("[*]"),
					"both markers outside the sample must have expanded: " + rendered);
		}

		@Test
		void theMarkdownLaneExpandsTheSameStraddleTheBBCodeLaneDoes() {
			String rendered = renderer.renderMarkdown("[list][*]see [img]http://x/y.png[/img][*]next[/list]");

			assertTrue(rendered.contains("<li>"),
					"the markdown lane runs the same expansion over each adjacent-text run, so it inherited the "
							+ "same straddle defect and must inherit the fix: " + rendered);
			assertFalse(rendered.contains("[*]"), rendered);
			assertTrue(rendered.contains("src=\"http://x/y.png\""), rendered);
		}

		@Test
		void aMarkerDeclaredWithNoItemCodeIsRefusedAtLoadRatherThanExpandingIntoNothing() {
			Map<String, BBCodeConfig> halfDeclared = new HashMap<>(grammar);
			BBCodeConfig listWithoutAnItemCode = seededBBCodeGrammar().get("LIST");
			listWithoutAnItemCode.setImplicitItemCode(null);
			halfDeclared.put("LIST", listWithoutAnItemCode);
			BBCodeDataProvider provider = mock(BBCodeDataProvider.class);
			doReturn(halfDeclared).when(provider).getBBCodeConfig();
			BBCodeGrammarLoader loading = buildRenderer(provider, messageResolver()).service();

			InvalidBBCodeGrammarException refused =
					assertThrows(InvalidBBCodeGrammarException.class, loading::loadBBCodeConfig);

			assertTrue(refused.getMessage().contains("implicit_item_code"), refused.getMessage());
		}

		@Test
		void aMarkerTheMarkdownLaneCannotHonourIsRefusedAtLoadRatherThanExpandingInOnlyOneLane() {
			Map<String, BBCodeConfig> markerTheLanesDisagreeOn = new HashMap<>(grammar);
			BBCodeConfig listWithALongMarker = seededBBCodeGrammar().get("LIST");
			listWithALongMarker.setImplicitItemMarker("[**]");
			markerTheLanesDisagreeOn.put("LIST", listWithALongMarker);
			BBCodeDataProvider provider = mock(BBCodeDataProvider.class);
			doReturn(markerTheLanesDisagreeOn).when(provider).getBBCodeConfig();
			BBCodeGrammarLoader loading = buildRenderer(provider, messageResolver()).service();

			InvalidBBCodeGrammarException refused =
					assertThrows(InvalidBBCodeGrammarException.class, loading::loadBBCodeConfig);

			assertTrue(refused.getMessage().contains("[**]"), refused.getMessage());
			assertTrue(refused.getMessage().contains("implicit_item_marker"), refused.getMessage());
			assertTrue(refused.getMessage().contains(
					String.valueOf(
							BBCodeGrammar.ImplicitItemExpansion.A_MARKER_IS_AN_OPENER_AN_INNER_CHARACTER_AND_A_CLOSER)),
					"ImplicitItemMarkerFactory used to drop any marker whose length was not three and say nothing, "
							+ "while the bbcode lane went on expanding it, so a hand-configured marker rendered as "
							+ "a list in one lane and as raw text in the other. The refusal has to name the length "
							+ "a marker carries so the operator can fix the row: " + refused.getMessage());
		}
	}

	@Nested
	class AttributeValueWhitespace {

		static Map<String, BBCodeConfig> grammar;
		static Renderer renderer;

		@BeforeAll
		static void loadTheRealSeededEngine() {
			grammar = seededBBCodeGrammar();
			renderer = buildRenderer(mock(BBCodeDataProvider.class), messageResolver());
			renderer.useGrammar(grammar);
		}

		@Test
		void theSeededRowsDeclareExactlyTheTypesWhoseValuesMayCarryASpace() {
			Set<AttributeDataType> admitting = new TreeSet<>();
			seededAttributeValuePolicies().forEach((type, policy) -> {
				if (policy.valueAdmitsWhitespace())
					admitting.add(type);
			});

			assertEquals(new TreeSet<>(Set.of(AttributeDataType.TEXT, AttributeDataType.FONT_NAME,
					AttributeDataType.COLOR, AttributeDataType.URL)), admitting,
					"this used to be a hardcoded per-type method reached through three indirections; the row has "
							+ "to declare exactly the same set or attribute parsing changed silently");
		}

		@Test
		void theScannerReadsTheRowRatherThanGreppingEveryModeOfTheCode() {
			assertEquals(Optional.of(true),
					grammar.get("QUOTE").valuePolicyOfTheAttributeNamed("author=")
							.map(AttributeValuePolicy::valueAdmitsWhitespace),
					"the scanner reaches the per-type answer with one lookup on the attribute name now");
			assertTrue(renderer.render("[quote author=Bob Smith]hi[/quote]").contains("Quote from Bob Smith,"),
					"TEXT's row admits whitespace, so the value runs to the next declared attribute name instead "
							+ "of stopping at the first space");
		}

		@Test
		void aTypeWhoseRowRefusesWhitespaceStopsItsValueAtTheFirstSpace() {
			assertEquals(Optional.of(false),
					grammar.get("LIST").valuePolicyOfTheAttributeNamed("type=")
							.map(AttributeValuePolicy::valueAdmitsWhitespace),
					"LIST_TYPE never admitted a space in its value and must not start now");
			assertTrue(renderer.render("[list type=lower-roman junk][*]x[/list]")
							.contains("class=\"bb-list-lower-roman\""),
					"the value ends at the space, so the trailing word never reaches the allowed-value set and "
							+ "the list keeps the style the author named");
		}
	}

	@Nested
	class MarkdownBinding {

		static Map<String, BBCodeConfig> grammar;

		@BeforeAll
		static void loadTheRealSeededGrammar() {
			grammar = seededBBCodeGrammar();
		}

		private Renderer loading(Map<String, BBCodeConfig> candidate) {
			BBCodeDataProvider provider = mock(BBCodeDataProvider.class);
			doReturn(candidate).when(provider).getBBCodeConfig();
			return buildRenderer(provider, messageResolver());
		}

		@Test
		void anEquivalentWithBindingsButNoCanonicalCodeIsRefusedAtLoad() {
			Map<String, BBCodeConfig> uncrowned = new HashMap<>(grammar);
			BBCodeConfig bold = seededBBCodeGrammar().get("B");
			bold.setMarkdownCanonicalFlag(false);
			uncrowned.put("B", bold);

			InvalidBBCodeGrammarException refused = assertThrows(InvalidBBCodeGrammarException.class,
					loading(uncrowned).service()::loadBBCodeConfig);

			assertTrue(refused.getMessage().contains("STRONG_EMPHASIS"), refused.getMessage());
			assertTrue(refused.getMessage().contains("markdown_canonical_flag"), refused.getMessage());
		}

		@Test
		void twoCanonicalCodesForOneEquivalentAreRefusedAtLoadBecauseTheWriterWouldPickArbitrarily() {
			Map<String, BBCodeConfig> twoCrowns = new HashMap<>(grammar);
			BBCodeConfig strong = seededBBCodeGrammar().get("U");
			strong.setMarkdownEquivalent(MarkdownEquivalent.STRONG_EMPHASIS.name());
			strong.setMarkdownCanonicalFlag(true);
			twoCrowns.put("U", strong);

			InvalidBBCodeGrammarException refused = assertThrows(InvalidBBCodeGrammarException.class,
					loading(twoCrowns).service()::loadBBCodeConfig);

			assertTrue(refused.getMessage().contains("STRONG_EMPHASIS"), refused.getMessage());
			assertTrue(refused.getMessage().contains("[b, u]") || refused.getMessage().contains("[u, b]"),
					refused.getMessage());
		}

		@Test
		void aSecondCodeBoundToAnEquivalentIsFineWhileOnlyOneOfThemIsCanonical() {
			Map<String, BBCodeConfig> aliased = new HashMap<>(grammar);
			BBCodeConfig alias = seededBBCodeGrammar().get("U");
			alias.setMarkdownEquivalent(MarkdownEquivalent.STRONG_EMPHASIS.name());
			alias.setMarkdownCanonicalFlag(false);
			aliased.put("U", alias);

			assertDoesNotThrow(loading(aliased).service()::loadBBCodeConfig,
					"aliasing is the feature the canonical flag exists to make safe, so it must load");
		}

		@Test
		void aBlockCodeBoundToAnInlineConstructIsRefusedAtLoadWithItsCodeNamed() {
			Map<String, BBCodeConfig> mismatched = new HashMap<>(grammar);
			BBCodeConfig blockLevel = seededBBCodeGrammar().get("ALIGN");
			blockLevel.setMarkdownEquivalent(MarkdownEquivalent.EMPHASIS.name());
			blockLevel.setMarkdownCanonicalFlag(true);
			mismatched.put("ALIGN", blockLevel);
			BBCodeConfig italic = seededBBCodeGrammar().get("I");
			italic.setMarkdownCanonicalFlag(false);
			mismatched.put("I", italic);

			InvalidBBCodeGrammarException refused = assertThrows(InvalidBBCodeGrammarException.class,
					loading(mismatched).service()::loadBBCodeConfig);

			assertTrue(refused.getMessage().contains("align"), refused.getMessage());
			assertTrue(refused.getMessage().contains("EMPHASIS"), refused.getMessage());
		}

		@Test
		void anInlineCodeBoundToABlockConstructIsRefusedAtLoadToo() {
			Map<String, BBCodeConfig> mismatched = new HashMap<>(grammar);
			BBCodeConfig inline = seededBBCodeGrammar().get("SPOILER");
			inline.setMarkdownEquivalent(MarkdownEquivalent.BLOCK_QUOTE.name());
			inline.setMarkdownCanonicalFlag(false);
			mismatched.put("SPOILER", inline);

			InvalidBBCodeGrammarException refused = assertThrows(InvalidBBCodeGrammarException.class,
					loading(mismatched).service()::loadBBCodeConfig);

			assertTrue(refused.getMessage().contains("spoiler"), refused.getMessage());
		}

		@Test
		void everyHeadingLevelResolvesFromTheHeadingElementInItsMarkup() {
			Renderer loaded = loading(grammar);
			loaded.service().loadBBCodeConfig();

			for (int level = 1; level <= 6; level++)
				assertEquals("h" + level,
						loaded.grammarHolder().current().theCanonicalHeadingCodeForLevel(level)
								.map(BBCodeConfig::getCode).orElse(null),
						"the heading level is read off the <h" + level + "> element in the seeded markup, so h"
								+ level + " has to be what level " + level + " resolves to");
		}

		@Test
		void thePlatformShipsNoBBCodeForAnEntityItDoesNotHave() throws IOException {
			assertFalse(grammar.containsKey("GAME"),
					"[game] was never a ZFGC concept -- legacy game rows migrate to CMS projects -- so a seed "
							+ "line reintroducing it would ship markup pointing at an entity that does not exist");
			assertFalse(sanitizer().sanitize("<a data-game-id=\"1\" href=\"/a\">x</a>").contains("data-game-id"),
					"the safelist carried the attribute the dead code emitted, so leaving it there keeps hand "
							+ "written html able to mint links to an entity the platform does not have");
			assertTrue(Files.readString(Path.of("src/main/resources/db/migration/tables/forum/"
							+ "V20260805.2__drop-game-bbcode.sql")).contains("delete from zfgbb.bb_code_config"),
					"R__03 upserts and never deletes, so removing the seed line alone leaves the code alive in "
							+ "every database that already ran it");
		}
	}

	@Nested
	class AttributeScanning {

		static Map<String, BBCodeConfig> grammar;
		static Renderer renderer;
		@BeforeAll
		static void loadTheRealSeededEngine() {
			grammar = seededBBCodeGrammar();
			BBCodeGrammarHolder grammarHolder = grammarHolder();
			RenderedTextEnricher enricher = enricher();
			ContentOutputSanitizer sanitizer = sanitizer(grammarHolder, enricher);
			enricher.registerSmilies(seededSmilies());
			renderer = buildRenderer(mock(BBCodeDataProvider.class), grammarHolder, enricher, sanitizer,
					messageResolver());
			renderer.useGrammar(grammar);
		}

		static BBCodeConfig.ParsedAttributes parse(String code, String attributeText) {
			return new AttributeTokenizer(grammar.get(code), attributeText).parseAttributeValues();
		}

		@Test
		void aValueThatRunsPastItsOwnTypeDoesNotSwallowTheAttributeWrittenAfterIt() {
			BBCodeConfig.ParsedAttributes parsed = parse("IMG", " width=10 and height=20");

			assertEquals("10", parsed.attributeValues().get("width="),
					"width is declared INTEGER, so its value cannot contain a space; reading to the next attribute "
							+ "name instead handed the transform '10 and', which validates to nothing and renders an "
							+ "image with no width at all");
			assertEquals("20", parsed.attributeValues().get("height="));
			assertEquals("width=height=", parsed.attFormat(),
					"the value extent may not move which mode the shape selects");
			assertTrue(renderer.render("[img width=10 and height=20]a.png[/img]").contains("width=\"10\""),
					"the whole point is the rendered image: " + renderer.render("[img width=10 and height=20]a.png[/img]"));
		}

		@Test
		void anAttributeNameTheAuthorTypedInsideAValueStaysPartOfThatValue() {
			BBCodeConfig.ParsedAttributes parsed =
					parse("QUOTE", " author=see the thread=x here thread=3 msg=14");

			assertEquals("see the thread=x here", parsed.attributeValues().get("author="),
					"an attribute name can only open a value once, so the earlier 'thread=' is text the author "
							+ "wrote inside the attribution; matching it as the attribute cut the author's name in "
							+ "half and left the real thread id inside the discarded remainder");
			assertEquals("3", parsed.attributeValues().get("thread="),
					"the real thread id is the last one written, and it is what the jump link needs");
			assertEquals("14", parsed.attributeValues().get("msg="));
			assertEquals("author=msg=thread=", parsed.attFormat());
			assertTrue(renderer.render("[quote author=see the thread=x here thread=3 msg=14]b[/quote]")
					.contains("data-thread-id=\"3\""),
					"a shredded thread id renders a jump link that goes nowhere");
		}

		@Test
		void aNameOnlyOpensAValueAtATokenBoundary() {
			BBCodeConfig.ParsedAttributes withANameInsideItsDestination =
					parse("QUOTE", " author=Bob link=http://zfgc.com/t?msg=5 date=1494552503");

			assertEquals("http://zfgc.com/t?msg=5", withANameInsideItsDestination.attributeValues().get("link="),
					"'msg=' inside a query string is not preceded by whitespace, so it is not a name; a scanner "
							+ "that matched names anywhere would cut this destination at the query parameter");
			assertEquals("author=link=date=", withANameInsideItsDestination.attFormat(),
					"and it would select the wrong mode as well, because the query parameter would count as a "
							+ "declared attribute the author never wrote");
			assertEquals("=msg=", parse("THREAD", "=3 msg=14").attFormat(),
					"a nameless value followed by a named one is a real seeded shape ([thread=3 msg=14])");
			assertEquals("3", parse("THREAD", "=3 msg=14").attributeValues()
					.get(BBCodeConfig.NAMELESS_ATTRIBUTE_NAME));
		}

		@Test
		void anUndeclaredNameNeverOpensAValue() {
			BBCodeConfig.ParsedAttributes parsed = parse("QUOTE", " autor=test link=test date=1494552504");

			assertEquals("link=date=", parsed.attFormat(),
					"'autor=' is not a declared name, so it opens nothing and the shape selects no quote mode, "
							+ "which is what makes the tag round-trip as the author's own text");
			assertFalse(grammar.get("QUOTE").getAttributeConfig().containsKey(parsed.attFormat()));
		}

		@ParameterizedTest(name = "{0}")
		@MethodSource("namelessValuesThatCarryTheirOwnCommas")
		void aFunctionNotationValueSurvivesTheNamelessSplit(String caseName, String code, String attributeText,
				List<String> expectedValues) {
			assertEquals(expectedValues, parse(code, attributeText).namelessValues(),
					"a comma inside rgb(...) separates the function's own arguments, not the tag's attributes; "
							+ "splitting on every comma hands the colour transform 'rgb(0' and it validates to "
							+ "nothing, so the shadow or glow renders with no colour at all");
		}

		static Stream<Arguments> namelessValuesThatCarryTheirOwnCommas() {
			return Stream.of(
					arguments("a shadow colour in function notation", "SHADOW", "=rgb(0,0,0)",
							List.of("rgb(0,0,0)")),
					arguments("a glow colour in function notation with a size after it", "GLOW", "=rgb(0,0,0),3",
							List.of("rgb(0,0,0)", "3")),
					arguments("a shadow colour and its direction", "SHADOW", "=red,left", List.of("red", "left")),
					arguments("a glow with a third value no mode declares", "GLOW", "=red,2,300",
							List.of("red", "2", "300")),
					arguments("an alpha channel inside the function", "SHADOW", "=rgba(0,0,0,0.5)",
							List.of("rgba(0,0,0,0.5)")));
		}

		@ParameterizedTest(name = "{0}")
		@MethodSource("namelessValuesRenderedThroughTheirModes")
		void aNamelessValueRendersThroughEverySlotItsModeDeclares(String caseName, String source,
				String expectedFragment) {
			assertTrue(renderer.render(source).contains(expectedFragment),
					"the split feeds the mode's slots in order, so a mis-split shows up as a missing style value: "
							+ renderer.render(source));
		}

		static Stream<Arguments> namelessValuesRenderedThroughTheirModes() {
			return Stream.of(
					arguments("shadow keeps a function-notation colour", "[shadow=rgb(0,0,0)]x[/shadow]",
							"--bb-shadow-color:rgb(0,0,0)"),
					arguments("glow keeps a function-notation colour and its size",
							"[glow=rgb(0,0,0),3]x[/glow]", "--bb-glow-color:rgb(0,0,0);--bb-glow-radius:3px"),
					arguments("shadow keeps a named colour", "[shadow=red,left]x[/shadow]",
							"--bb-shadow-color:red"),
					arguments("glow keeps a named colour", "[glow=red,2,300]x[/glow]",
							"--bb-glow-color:red;--bb-glow-radius:2px"));
		}

		@Test
		void aModeWithOneNamelessSlotKeepsTheCommasTheAuthorWrote() {
			assertEquals("Arial, Helvetica", parse("FONT", "=Arial, Helvetica").attributeValues()
					.get(BBCodeConfig.NAMELESS_ATTRIBUTE_NAME),
					"font declares a single nameless attribute, so its commas are the author's font stack and "
							+ "never a separator between two attribute values");
			assertTrue(renderer.render("[font=Arial, Helvetica]x[/font]").contains("--bb-font:Arial, Helvetica"),
					renderer.render("[font=Arial, Helvetica]x[/font]"));
		}

		@ParameterizedTest(name = "{0}")
		@MethodSource("theRolesTheConvertersReadsDependOn")
		void aSeededAttributeCarriesTheSemanticRoleItsConsumerAsksFor(String caseName, String code, String modeKey,
				String attributeName, AttributeSemanticRole expectedRole) {
			BBCodeAttributeMode mode = grammar.get(code).getAttributeConfig().get(modeKey);

			assertNotNull(mode, "the grammar no longer declares mode '" + modeKey + "' for " + code);
			assertEquals(Optional.of(expectedRole), mode.getAttributes().stream()
					.filter(attribute -> attributeName.equals(attribute.getName()))
					.findFirst()
					.orElseThrow(() -> new AssertionError(
							code + " mode '" + modeKey + "' declares no attribute named " + attributeName))
					.declaredSemanticRole(),
					"the converter asks the grammar what an attribute means instead of parsing the code's name in "
							+ "Java, so a seed row that loses its role silently turns the conversion off");
		}

		static Stream<Arguments> theRolesTheConvertersReadsDependOn() {
			return Stream.of(
					arguments("a link destination", "URL", "=", "=", AttributeSemanticRole.DESTINATION),
					arguments("an image width", "IMG", "width=height=", "width=", AttributeSemanticRole.WIDTH),
					arguments("an image height", "IMG", "width=height=", "height=", AttributeSemanticRole.HEIGHT),
					arguments("a named list style", "LIST", "type=", "type=", AttributeSemanticRole.LIST_STYLE),
					arguments("a nameless list style", "LIST", "=", "=", AttributeSemanticRole.LIST_STYLE));
		}

		@ParameterizedTest(name = "{0}")
		@MethodSource("theModesThatCarryTheirMeaningInTheirBody")
		void aModeWithNoAttributeRowsStillDeclaresWhatItsBodyMeans(String caseName, String code, String modeKey) {
			BBCodeAttributeMode mode = grammar.get(code).getAttributeConfig().get(modeKey);

			assertNotNull(mode, "the grammar no longer declares mode '" + modeKey + "' for " + code);
			assertEquals(Optional.of(AttributeSemanticRole.DESTINATION), mode.declaredContentSemanticRole(),
					"[url]dest[/url] and [img]src[/img] have no attribute rows at all, so a role that only ever "
							+ "lived on an attribute could never describe them and the converter would be back to "
							+ "knowing the codes by name");
		}

		static Stream<Arguments> theModesThatCarryTheirMeaningInTheirBody() {
			return Stream.of(
					arguments("a url whose body is its destination", "URL", ""),
					arguments("an image whose body is its source", "IMG", ""),
					arguments("a sized image whose body is its source", "IMG", "width=height="));
		}

		@Test
		void aSelfClosingTagCarriesNoParsedAttributesRatherThanNull() {
			BBCodeTag selfClosing = (BBCodeTag) BBCodeParser.parse("[hr]", grammar).children().get(0);

			assertEquals(Map.of(), selfClosing.parsedAttributes().attributeValues(),
					"a self-closing tag never reaches the expander, so it has no parse to carry; the caller has to "
							+ "get an empty parse rather than a null it would have to guard");
			assertEquals(Optional.empty(), selfClosing.valueWithRole(AttributeSemanticRole.DESTINATION));
			assertFalse(selfClosing.itsBodyCarriesTheRole(AttributeSemanticRole.DESTINATION));
		}

		@Test
		void aShapeNoModeAcceptsNeverBecomesATagAtAll() {
			List<BBCodeNode> children =
					BBCodeParser.parse("[quote author=a link=b]x[/quote]", grammar).children();

			assertTrue(children.stream().noneMatch(node -> node instanceof BBCodeTag),
					"a shape no mode accepts is the author's own text, so there is no tag to carry a parse and "
							+ "nothing downstream may assume one exists: " + children);
		}

		@ParameterizedTest(name = "{0}")
		@MethodSource("shapesTheSeededGrammarAlreadyParses")
		void aShapeTheCorpusAlreadyWritesKeepsItsValues(String caseName, String code, String attributeText,
				String expectedAttFormat, Map<String, String> expectedValues) {
			BBCodeConfig.ParsedAttributes parsed = parse(code, attributeText);

			assertEquals(expectedAttFormat, parsed.attFormat(),
					"the tokenizer feeds the existing mode keying, so a shape the corpus already writes has to "
							+ "select the same mode it always did");
			assertEquals(expectedValues, parsed.attributeValues());
		}

		static Stream<Arguments> shapesTheSeededGrammarAlreadyParses() {
			return Stream.of(
					arguments("an smf quote attribution", "QUOTE",
							" author=Bob link=http://zfgc.com/a date=1494552503", "author=link=date=",
							Map.of("author=", "Bob", "link=", "http://zfgc.com/a", "date=", "1494552503")),
					arguments("an author name with a space in it", "QUOTE",
							" author=Simple Machines thread=1 msg=1", "author=msg=thread=",
							Map.of("author=", "Simple Machines", "thread=", "1", "msg=", "1")),
					arguments("the numbered list the wiki migrator emits", "LIST", "=1", "=",
							Map.of("=", "1")),
					arguments("a styled list", "LIST", " type=decimal", "type=",
							Map.of("type=", "decimal")),
					arguments("a url destination", "URL", "=x", "=", Map.of("=", "x")),
					arguments("a size in points", "SIZE", "=36pt", "=", Map.of("=", "36pt")),
					arguments("a wiki slug carrying punctuation", "WIKI", "=Help:Rules#Wiki_Rules", "=",
							Map.of("=", "Help:Rules#Wiki_Rules")),
					arguments("a font stack", "FONT", "=comic sans ms", "=", Map.of("=", "comic sans ms")),
					arguments("no attributes at all", "IMG", "", "", Map.of()));
		}
	}

	@Nested
	class QuoteSourceReference {

		private static final OffsetDateTime REV_TS = OffsetDateTime.of(2020, 6, 1, 0, 0, 0, 0, ZoneOffset.UTC);
		private static final OffsetDateTime QUOTING_TS = OffsetDateTime.of(2020, 6, 2, 0, 0, 0, 0, ZoneOffset.UTC);
		private static final Set<Integer> VISIBLE_BOARDS = Set.of(3);
		private static final String PLACEHOLDER = "(quoted message unavailable)";

		private BBCodeRenderer bbCodeRenderer;
		private Renderer renderer;
		private SourceReferenceService handler;
		private QuotedMessageSource lookup;

		@BeforeEach
		void setup() {
			lookup = messageResolver();
			renderer = buildRenderer(mock(BBCodeDataProvider.class), lookup);
			bbCodeRenderer = renderer.bbCodeRenderer();
			handler = renderer.handler();
			renderer.useGrammar(buildConfig());
		}

		private static Map<String, BBCodeConfig> buildConfig() {
			Map<String, BBCodeConfig> config = new HashMap<>();
			config.put("QUOTE", quoteConfig());
			config.put("B", simpleTag("b", "<span class=\"bb-code-b\">", "</span>", true));
			config.put("CODE", codeConfig());
			return config;
		}

		private void stubResolve(Map<Integer, ContentTagResolver.Resolved> master) {
			when(lookup.resolve(any(), any())).thenAnswer(invocation -> {
				Set<Integer> ids = invocation.getArgument(0);
				Map<Integer, ContentTagResolver.Resolved> out = new HashMap<>();
				for (Integer id : ids) {
					if (master.containsKey(id)) {
						out.put(id, master.get(id));
					}
				}
				return out;
			});
		}

		private String renderInScope(String post, Map<Integer, ContentTagResolver.Resolved> master) {
			stubResolve(master);
			return renderInTheScopeOf(post);
		}

		private String renderInTheScopeOf(String post) {
			SourceReferenceService.ScopeRestore restore = handler.openScope(List.of(new ContentRenderingService.QuotingPost(post, QUOTING_TS)), VISIBLE_BOARDS);
			try {
				return renderer.render(post, QUOTING_TS);
			} finally {
				handler.closeScope(restore);
			}
		}

		@Test
		void aQuoteOfAnUnreadableSourceDegradesInsteadOfFailingTheRender() {
			when(lookup.resolve(any(), any())).thenReturn(Map.of());

			String post = "[quote author=Hammer Bro. Mike thread=3 msg=5]stale copy[/quote] tail text";
			SourceReferenceService.ScopeRestore restore = handler.openScope(
					List.of(new ContentRenderingService.QuotingPost(post, QUOTING_TS)), VISIBLE_BOARDS);
			String rendered;
			try {
				rendered = renderer.render(post, QUOTING_TS);
			} finally {
				handler.closeScope(restore);
			}

			assertTrue(rendered.contains("Hammer Bro. Mike"),
					"a source the viewer cannot read must fall back to the author the quoting post typed: "
							+ rendered);
			assertTrue(rendered.contains("tail text"),
					"an unreadable source degrades in place; it must never abort the surrounding render, or "
							+ "one quote of a since-hidden post takes down the whole thread page: " + rendered);
			assertFalse(rendered.contains("/user/profile/"),
					"an unreadable source must not link its author: " + rendered);
		}

		@Test
		void everySourceOnThePageIsResolvedInOneCall() {
			stubResolve(Map.of(5, resolved(true, REV_TS, "body five"),
					6, resolved(true, REV_TS, "body six"),
					7, resolved(true, REV_TS, "body seven")));
			List<ContentRenderingService.QuotingPost> posts = List.of(
					new ContentRenderingService.QuotingPost("[quote msg=5][/quote][quote msg=6][/quote]", QUOTING_TS),
					new ContentRenderingService.QuotingPost("[quote msg=7][/quote]", QUOTING_TS));

			SourceReferenceService.ScopeRestore restore = handler.openScope(posts, VISIBLE_BOARDS);
			handler.closeScope(restore);

			ArgumentCaptor<Set<Integer>> resolvedIds = ArgumentCaptor.forClass(Set.class);
			verify(lookup, times(1)).resolve(resolvedIds.capture(), any());
			assertEquals(Set.of(5, 6, 7), resolvedIds.getValue(),
					"a scope resolves every source the page names in ONE batch; a per-tag lookup is a query "
							+ "per quote per post on an anonymous thread view");
		}

		@Test
		void aPageNamingMoreSourcesThanTheBudgetResolvesOnlyTheBudget() {
			stubResolve(Map.of());
			int namedSources = SourceReferenceService.MAX_SOURCE_REFERENCES_PER_SCOPE + 250;
			StringBuilder post = new StringBuilder();
			for (int sourceId = 1; sourceId <= namedSources; sourceId++)
				post.append("[quote msg=").append(sourceId).append("][/quote]");

			SourceReferenceService.ScopeRestore restore = handler.openScope(
					List.of(new ContentRenderingService.QuotingPost(post.toString(), QUOTING_TS)), VISIBLE_BOARDS);
			handler.closeScope(restore);

			ArgumentCaptor<Set<Integer>> resolvedIds = ArgumentCaptor.forClass(Set.class);
			verify(lookup).resolve(resolvedIds.capture(), any());
			assertEquals(SourceReferenceService.MAX_SOURCE_REFERENCES_PER_SCOPE, resolvedIds.getValue().size(),
					"one stored post can name thousands of sources, and every one costs a full-text history "
							+ "row plus a render on an anonymous thread view, so a scope resolves at most its "
							+ "budget and the rest render unresolved");
		}

		@Test
		void aScopeOpenedInsideAnotherRestoresTheOuterOneWhenItCloses() {
			when(lookup.resolve(any(), any())).thenAnswer(invocation -> {
				Set<Integer> visibleBoardIds = invocation.getArgument(1);
				return Map.of(5, visibleBoardIds == null
						? resolved(false, REV_TS, "outer viewer source body")
						: resolved(true, REV_TS, "outer viewer source body"));
			});
			String post = "[quote msg=5][/quote]";

			SourceReferenceService.ScopeRestore outer = handler.openScope(
					List.of(new ContentRenderingService.QuotingPost(post, QUOTING_TS)), VISIBLE_BOARDS);
			String rendered;
			try {
				SourceReferenceService.ScopeRestore inner = handler.openScope(List.of(), VISIBLE_BOARDS);
				handler.closeScope(inner);
				rendered = renderer.render(post, QUOTING_TS);
			} finally {
				handler.closeScope(outer);
			}

			assertTrue(rendered.contains("outer viewer source body"),
					"a template source that renders a thread opens and closes its own quote scope inside the "
							+ "caller's; if closing it removed the thread local instead of restoring the outer "
							+ "scope, the outer render would re-resolve with no visible boards and every quote "
							+ "would degrade to the unavailable placeholder: " + rendered);
			assertFalse(rendered.contains(PLACEHOLDER), rendered);
		}

		@Test
		void aFailedOpenQuoteScopeLeavesNoScopeStrandedOnTheThread() {
			stubResolve(Map.of(5, resolved(true, REV_TS, "viewer-one source body")));

			handler.registerSourceBodyRenderer((rawBody, contentFormat, quotingCreatedTs) -> {
				throw new IllegalStateException("render blew up");
			});
			try {
				assertThrows(IllegalStateException.class, () -> handler.openScope(
						List.of(new ContentRenderingService.QuotingPost("[quote msg=5][/quote]", QUOTING_TS)),
						VISIBLE_BOARDS));
			} finally {
				registerTheLaneDispatcherOf(renderer);
			}

			doReturn(Map.of()).when(lookup).resolve(any(), any());
			String nextRequestOnThisThread = renderer.render("[quote msg=5][/quote]", null);

			assertFalse(nextRequestOnThisThread.contains("Alice"),
					"a throw while opening the scope must not strand one viewer's permission-filtered "
							+ "quote resolution on a pooled request thread, or the next request "
							+ "renders the previous viewer's quote header: " + nextRequestOnThisThread);
			assertTrue(nextRequestOnThisThread.contains(PLACEHOLDER),
					"the scope-less render must resolve nothing of its own: " + nextRequestOnThisThread);
		}

		@Test
		void theSourceIdReadOffATagIsStrictAboutReferenceModes() {
			assertEquals(Set.of(42), handler.collectSourceReferenceIds("[quote msg=42]x[/quote]"));
			assertEquals(Set.of(42), handler.collectSourceReferenceIds("[quote thread=3 msg=42]x[/quote]"));
			assertEquals(Set.of(42),
					handler.collectSourceReferenceIds("[quote author=Bob thread=3 msg=42]x[/quote]"));
			assertEquals(Set.of(), handler.collectSourceReferenceIds("[quote author=Some msg=42 guy]x[/quote]"));
			assertEquals(Set.of(), handler.collectSourceReferenceIds("[quote author=Bob]x[/quote]"));
			assertEquals(Set.of(), handler.collectSourceReferenceIds("[quote=Bob]x[/quote]"));
		}

		@Test
		void gateNoOpIsByteIdenticalForNonMsgQuote() {
			String input = "[quote author=Bob]legacy body[/quote]";
			String withoutScope = renderer.render(input);

			stubResolve(Map.of(5, resolved(true, REV_TS, "SOURCE")));
			SourceReferenceService.ScopeRestore restore = handler.openScope(
					List.of(new ContentRenderingService.QuotingPost("other [quote msg=5][/quote]", QUOTING_TS)), VISIBLE_BOARDS);
			String withScope;
			try {
				withScope = renderer.render(input, QUOTING_TS);
			} finally {
				handler.closeScope(restore);
			}

			assertEquals(withoutScope, withScope);
			assertTrue(withoutScope.contains("legacy body"));
			assertFalse(withoutScope.contains("\uE000"));
		}

		@Test
		void pullHappyPathSplicesRenderedSourceBody() {
			String result = renderInScope("[quote msg=5][/quote]",
					Map.of(5, resolved(true, REV_TS, "original body [b]bold[/b]")));

			assertTrue(result.contains("Alice"));
			assertTrue(result.contains("/user/profile/7"));
			assertTrue(result.contains("original body"));
			assertTrue(result.contains("<span class=\"bb-code-b\">bold</span>"));
			assertFalse(result.contains("\uE000"));
			assertFalse(result.contains("[quote"));
		}

		@Test
		void multiRevisionSelectsOlderBodyByQuotingTime() {
			OffsetDateTime olderTs = OffsetDateTime.of(2020, 6, 1, 0, 0, 0, 0, ZoneOffset.UTC);
			OffsetDateTime newerTs = OffsetDateTime.of(2020, 6, 3, 0, 0, 0, 0, ZoneOffset.UTC);
			NavigableMap<OffsetDateTime, ContentTagResolver.SourceRevision> revisions = new TreeMap<>();
			revisions.put(olderTs, new ContentTagResolver.SourceRevision("OLDER-BODY", ContentFormat.BBCODE));
			revisions.put(newerTs, new ContentTagResolver.SourceRevision("NEWER-BODY", ContentFormat.BBCODE));
			ContentTagResolver.Resolved multi =
					new ContentTagResolver.Resolved("Alice", 7, olderTs, 42, 1, 3, true, revisions);

			String result = renderInScope("[quote msg=5][/quote]", Map.of(5, multi));

			assertTrue(result.contains("OLDER-BODY"));
			assertFalse(result.contains("NEWER-BODY"));
		}

		@Test
		void aQuotedMarkdownSourceRendersThroughTheMarkdownLane() {
			NavigableMap<OffsetDateTime, ContentTagResolver.SourceRevision> revisions = new TreeMap<>();
			revisions.put(REV_TS, new ContentTagResolver.SourceRevision("**markdown source**", ContentFormat.MARKDOWN));

			String result = renderInScope("[quote msg=5][/quote]", Map.of(5,
					new ContentTagResolver.Resolved("Alice", 7, REV_TS, 42, 1, 3, true, revisions)));

			assertTrue(result.contains("<strong>markdown source</strong>"),
					"the quoted revision declares MARKDOWN, so its body must render through the markdown lane "
							+ "instead of the quoting post's: " + result);
		}

		@Test
		void legacyEmbeddedBodyReplacedWhenSourceResolves() {
			String result = renderInScope("[quote msg=5]stale embedded copy[/quote]",
					Map.of(5, resolved(true, REV_TS, "live source body")));

			assertTrue(result.contains("live source body"));
			assertFalse(result.contains("stale embedded copy"));
		}

		@Test
		void revisionSelectionCacheHitRendersSourceOnce() {
			stubResolve(Map.of(5, resolved(true, REV_TS, "SOURCEMARKER")));
			String duplicateQuotingPosts = "[quote msg=5][/quote]";
			List<String> sourceBodiesHandedToTheRenderer = new ArrayList<>();
			handler.registerSourceBodyRenderer((rawBody, contentFormat, quotingCreatedTs) -> {
				sourceBodiesHandedToTheRenderer.add(rawBody);
				return bbCodeRenderer.render(rawBody, quotingCreatedTs, ContentScope.FORUM, Map.of());
			});
			SourceReferenceService.ScopeRestore restore = handler.openScope(List.of(
					new ContentRenderingService.QuotingPost(duplicateQuotingPosts, QUOTING_TS),
					new ContentRenderingService.QuotingPost(duplicateQuotingPosts, QUOTING_TS)), VISIBLE_BOARDS);
			try {
				renderer.render(duplicateQuotingPosts, QUOTING_TS);
				renderer.render(duplicateQuotingPosts, QUOTING_TS);
			} finally {
				handler.closeScope(restore);
				registerTheLaneDispatcherOf(renderer);
			}

			assertEquals(1,
					sourceBodiesHandedToTheRenderer.stream().filter(body -> body.contains("SOURCEMARKER")).count(),
					"two posts quoting the same revision must share one rendered body, or a thread page renders "
							+ "the same source once per quoting post: " + sourceBodiesHandedToTheRenderer);
		}

		@Test
		void unresolvedSourceRendersPlaceholder() {
			String result = renderInScope("[quote msg=5][/quote]", Map.of());

			assertTrue(result.contains(PLACEHOLDER));
			assertTrue(result.contains("(unavailable)"));
			assertFalse(result.contains("/user/profile/"));
		}

		@Test
		void notPermittedRendersPlaceholderAndNeutralHeader() {
			String result = renderInScope("[quote msg=5][/quote]",
					Map.of(5, resolved(false, REV_TS, "secret body")));

			assertTrue(result.contains(PLACEHOLDER));
			assertTrue(result.contains("(unavailable)"));
			assertFalse(result.contains("secret body"));
			assertFalse(result.contains("/user/profile/"));
		}

		@Test
		void floorNullRendersPlaceholder() {
			OffsetDateTime revisionAfterQuoting = QUOTING_TS.plusDays(5);
			String result = renderInScope("[quote msg=5][/quote]",
					Map.of(5, resolved(true, revisionAfterQuoting, "future body")));

			assertTrue(result.contains(PLACEHOLDER));
			assertFalse(result.contains("future body"));
		}

		@Test
		void emptySourceRendersPlaceholder() {
			String result = renderInScope("[quote msg=5][/quote]",
					Map.of(5, resolved(true, REV_TS, "   ")));

			assertTrue(result.contains(PLACEHOLDER));
		}

		@Test
		void headerUsesTagAuthorFallbackWhenNotPermitted() {
			String result = renderInScope("[quote author=Ghost thread=3 msg=5][/quote]",
					Map.of(5, resolved(false, REV_TS, "secret")));

			assertTrue(result.contains("Ghost"));
			assertTrue(result.contains(PLACEHOLDER));
			assertFalse(result.contains("/user/profile/"));
		}

		private static ContentTagResolver.Resolved ownerlessResolved(OffsetDateTime createdTs, String body) {
			NavigableMap<OffsetDateTime, ContentTagResolver.SourceRevision> revisions = new TreeMap<>();
			revisions.put(REV_TS, new ContentTagResolver.SourceRevision(body, ContentFormat.BBCODE));
			return new ContentTagResolver.Resolved(null, null, createdTs, 42, 1, 3, true, revisions);
		}

		@Test
		void headerUsesTagAuthorFallbackWhenTheResolvedMessageHasNoOwner() {
			String result = renderInScope("[quote author=Hammer Bro. Mike thread=3 msg=5][/quote]",
					Map.of(5, ownerlessResolved(REV_TS, "guest body")));

			assertTrue(result.contains("Hammer Bro. Mike"),
					"a permitted quote whose source message has no owner must fall back to the "
							+ "author the tag carries instead of rendering (unknown): " + result);
			assertFalse(result.contains("(unknown)"), result);
			assertTrue(result.contains("guest body"), result);
			assertFalse(result.contains("/user/profile/"), result);
		}

		@Test
		void headerStillReadsUnknownWhenNeitherOwnerNorTagCarriesAName() {
			String result = renderInScope("[quote thread=3 msg=5][/quote]",
					Map.of(5, ownerlessResolved(REV_TS, "guest body")));

			assertTrue(result.contains("(unknown)"), result);
		}

		private String renderQuoteOfMessageFive() {
			String post = "[quote msg=5][/quote]";
			SourceReferenceService.ScopeRestore restore = handler.openScope(List.of(new ContentRenderingService.QuotingPost(post, QUOTING_TS)), VISIBLE_BOARDS);
			try {
				return renderer.render(post, QUOTING_TS);
			} finally {
				handler.closeScope(restore);
			}
		}

		@Test
		void headerDateIsATimeElementIndependentOfTheServerTimeZone() {
			OffsetDateTime postedNow = OffsetDateTime.now(ZoneOffset.UTC);
			NavigableMap<OffsetDateTime, ContentTagResolver.SourceRevision> revisions = new TreeMap<>();
			revisions.put(REV_TS, new ContentTagResolver.SourceRevision("source body", ContentFormat.BBCODE));
			stubResolve(Map.of(5, new ContentTagResolver.Resolved(
					"Alice", 7, postedNow, 42, 1, 3, true, revisions)));

			TimeZone serverZone = TimeZone.getDefault();
			String renderedFarWest;
			String renderedFarEast;
			try {
				TimeZone.setDefault(TimeZone.getTimeZone("Pacific/Niue"));
				renderedFarWest = renderQuoteOfMessageFive();
				TimeZone.setDefault(TimeZone.getTimeZone("Pacific/Kiritimati"));
				renderedFarEast = renderQuoteOfMessageFive();
			} finally {
				TimeZone.setDefault(serverZone);
			}

			assertEquals(renderedFarWest, renderedFarEast,
					"the quote header date must carry the message's own instant, not a "
							+ "Today/Yesterday phrase computed in the server's default time zone");
			assertTrue(renderedFarWest.contains("datetime=\"" + postedNow + "\""),
					"the quote header must emit a machine-readable time element: " + renderedFarWest);
			assertTrue(renderedFarWest.contains("class=\"" + BBCodeDateElement.LONG_FORM_CLASS + "\""),
					"the quote header time element must ask the client for long form: " + renderedFarWest);
		}

		@Test
		void literalCodeBlockIsNotSpliced() {
			String result = renderInScope("[code][quote msg=5][/quote][/code]",
					Map.of(5, resolved(true, REV_TS, "SHOULD NOT APPEAR")));

			assertTrue(result.contains("[quote msg=5][/quote]"));
			assertFalse(result.contains("SHOULD NOT APPEAR"));
			assertFalse(result.contains("Quote from"));
		}

		@Test
		void unbalancedQuoteLeavesTailIntact() {
			String result = renderInScope("[quote msg=5]tail without close",
					Map.of(5, resolved(true, REV_TS, "pulled body")));

			assertTrue(result.contains("tail without close"));
			assertFalse(result.contains("pulled body"));
			assertFalse(result.contains("\uE000"));
		}

		@Test
		void falsePositiveAttributeDoesNotGate() {
			String result = renderInScope("[quote author=Some msg=42 guy]body[/quote]",
					Map.of(42, resolved(true, REV_TS, "SHOULD NOT PULL")));

			assertFalse(result.contains("SHOULD NOT PULL"));
			assertFalse(result.contains("Quote from"));
			assertTrue(result.contains("msg=42"));
		}

		@Test
		void sentinelSurvivesSanitize() {
			String sentinel = "\uE000" + "0" + "\uE001";
			String out = sanitizer().sanitize("before" + sentinel + "after");

			assertTrue(out.contains(sentinel));
		}

		@Test
		void batchResolveIsConstantNotPerPost() {
			stubResolve(Map.of(5, resolved(true, REV_TS, "SOURCE")));
			List<ContentRenderingService.QuotingPost> posts = IntStream.range(0, 8)
					.mapToObj(i -> new ContentRenderingService.QuotingPost("[quote msg=5][/quote]", QUOTING_TS))
					.collect(Collectors.toList());

			SourceReferenceService.ScopeRestore restore = handler.openScope(posts, VISIBLE_BOARDS);
			handler.closeScope(restore);

			verify(lookup, times(1)).resolve(any(), any());
		}

		@Test
		void nonScopedStrippedQuoteRendersCurrentSourceNotEmpty() {
			stubResolve(Map.of(5, resolved(true, REV_TS, "revived source body [b]bold[/b]")));

			String result = renderer.render("[quote msg=5][/quote]");

			assertTrue(result.contains("revived source body"));
			assertTrue(result.contains("<span class=\"bb-code-b\">bold</span>"));
			assertTrue(result.contains("Alice"));
			assertFalse(result.contains(PLACEHOLDER));
			assertFalse(result.contains("\uE000"));
		}

		@Test
		void nonScopedStrippedQuoteWithoutPermissionRendersPlaceholderNotEmpty() {
			stubResolve(Map.of(5, resolved(false, REV_TS, "secret source body")));

			String result = renderer.render("[quote msg=5][/quote]");

			assertTrue(result.contains(PLACEHOLDER));
			assertFalse(result.contains("secret source body"));
			assertFalse(result.contains("/user/profile/"));
		}

		@Test
		void anAuthorTypedPrivateUseCharacterIsNeverReplacedByAQuotedBody() {
			String post = "\uE000" + "0" + "\uE001 forged [quote msg=5][/quote]";
			String result = renderInScope(post, Map.of(5, resolved(true, REV_TS, "PULLED-SOURCE-BODY")));

			assertEquals(1, result.split(Pattern.quote("PULLED-SOURCE-BODY"), -1).length - 1,
					"the retired splice indexed its pulled bodies with private-use characters and substituted "
							+ "them back after rendering, so an author who typed the same characters had a quoted "
							+ "body written into their own text; resolution happens on the tree now and there is "
							+ "no index for an author to forge: " + result);
			assertTrue(result.contains("\uE000" + "0" + "\uE001"),
					"and the characters the author typed are still their own text: " + result);
		}

		@Test
		void aQuoteWrittenInsideAMarkdownFenceIsNeverResolved() {
			stubResolve(Map.of(5, resolved(true, REV_TS, "PULLED-SOURCE-BODY")));
			String post = "```\n[quote msg=5][/quote]\n```";
			SourceReferenceService.ScopeRestore restore = handler.openScope(List.of(new ContentRenderingService.QuotingPost(post, QUOTING_TS)), VISIBLE_BOARDS);
			String result;
			try {
				result = renderer.contentRenderingService().render(post, ContentFormat.MARKDOWN, ContentScope.FORUM,
						QUOTING_TS);
			} finally {
				handler.closeScope(restore);
			}

			assertFalse(result.contains("PULLED-SOURCE-BODY"),
					"the retired splice rewrote the raw source before either lane parsed it, so a fenced code "
							+ "block documenting a quote had the source post's body pulled into the sample; a "
							+ "fence produces no bbcode tag, so tree resolution cannot reach into one: " + result);
			assertTrue(result.contains("[quote msg=5][/quote]"),
					"and the sample stays the bytes the author pasted: " + result);
		}

		@Test
		void aSourceReferenceWithNoRegisteredResolverIsRefusedAtLoadRatherThanAtRenderTime() {
			Renderer withNoResolverRegistered = buildRenderer(mock(BBCodeDataProvider.class));

			InvalidBBCodeGrammarException refused = assertThrows(InvalidBBCodeGrammarException.class,
					() -> withNoResolverRegistered.useGrammar(buildConfig()));

			assertTrue(refused.getMessage().contains("registered resolvers are []"),
					"a code declaring source_reference_resolver with nothing registered to answer it must name "
							+ "the empty registry at load; resolving it lazily would leave every quote on the "
							+ "site rendering an unexplained placeholder: " + refused.getMessage());
		}

		@Test
		void aResolverThatAnswersWithNothingLeavesTheHeaderNeutralInsteadOfFailing() {
			stubResolve(Map.of());

			String result = renderer.render("[quote msg=5]author body[/quote]");

			assertTrue(result.contains("(unavailable)"), result);
			assertTrue(result.contains("author body"),
					"an id the registered resolver knows nothing about leaves the author's own body: " + result);
			assertTrue(result.contains("href=\"#\""), result);
		}

		@Test
		void anImplicitItemMarkerInsideALiteralBodyOpensNoItemEvenInsideItsOwnContainer() {
			renderer.useGrammar(theListGrammar());

			assertEquals("<ul><li>run <code>a[*]b</code></li><li>next</li></ul>",
					renderer.render("[list][*]run [code]a[*]b[/code][*]next[/list]"),
					"the marker is read inside the one parse now, so the guard is the open-tag stack: a code "
							+ "whose grammar row keeps its content literal is on top of that stack and no marker "
							+ "inside it may open an item");
		}

		private static Map<String, BBCodeConfig> theListGrammar() {
			Map<String, BBCodeConfig> grammar = new HashMap<>(buildConfig());
			BBCodeConfig list = simpleTag("list", "<ul>", "</ul>", true);
			list.setImplicitItemMarker("[*]");
			list.setImplicitItemCode("li");
			grammar.put("LIST", list);
			grammar.put("LI", simpleTag("li", "<li>", "</li>", true));
			return grammar;
		}

		private static final String A_HEADER_A_MARKDOWN_BLOCK_COULD_HOST =
				"<div class=\"bb-q\">Quote from {{msg.author}} on {{msg.dateText}}<div class=\"bb-qb\">";

		private static Map<String, BBCodeConfig> theGrammarWhoseQuoteHeaderAMarkdownBlockCouldHost() {
			Map<String, BBCodeConfig> grammar = new HashMap<>(buildConfig());
			BBCodeConfig quote = quoteConfig();
			quote.getAttributeConfig().put("msg=",
					mode(A_HEADER_A_MARKDOWN_BLOCK_COULD_HOST, attr("msg=", "{{0}}")));
			grammar.put("QUOTE", quote);
			return grammar;
		}

		@Test
		void aQuoteNamingItsSourceResolvesThroughTheTreeEvenWhereAMarkdownBlockCouldHaveHostedIt() {
			renderer.useGrammar(theGrammarWhoseQuoteHeaderAMarkdownBlockCouldHost());
			stubResolve(Map.of(5, resolved(true, REV_TS, "PULLED-SOURCE-BODY")));
			String post = "[quote msg=5]\nthe author's own body\n[/quote]";
			SourceReferenceService.ScopeRestore restore = handler.openScope(List.of(new ContentRenderingService.QuotingPost(post, QUOTING_TS)), VISIBLE_BOARDS);
			String result;
			try {
				result = renderer.contentRenderingService().render(post, ContentFormat.MARKDOWN, ContentScope.FORUM,
						QUOTING_TS);
			} finally {
				handler.closeScope(restore);
			}

			assertFalse(result.contains("{{msg."),
					"the block factory expands the opener but resolves nothing, so a source-naming quote that "
							+ "opens a commonmark block puts the engine's own slot text on the page: " + result);
			assertTrue(result.contains("PULLED-SOURCE-BODY"),
					"and its body is the source post's, which only the tree pass can pull: " + result);
			assertTrue(result.contains("Alice"), result);
			assertFalse(result.contains("the author's own body"),
					"the stale embedded copy is replaced in the markdown lane exactly as it is in the bbcode "
							+ "lane: " + result);
		}

		@Test
		void aQuoteNamingNoSourceStillOpensItsMarkdownBlockSoItsBodyStaysMarkdown() {
			renderer.useGrammar(theGrammarWhoseQuoteHeaderAMarkdownBlockCouldHost());

			String result = renderer.renderMarkdown("[quote]\n# heading\n[/quote]");

			assertTrue(result.contains("<div class=\"bb-qb\">\n<h1>heading</h1>"),
					"refusing the block for every quote would take markdown out of every ordinary quote body; "
							+ "only a quote that actually names a source may fall through to the tree: " + result);
			assertFalse(result.contains("<p>[quote]</p>"),
					"and the opener must not read as a paragraph of its own text: " + result);
		}

		private static final String A_HEADER_THAT_WRITES_THE_TAGS_OWN_AUTHOR_INTO_ITSELF =
				"<div class=\"bb-q\"><div class=\"bb-qh\">Quote from {{msg.author}}, who wrote it as {{0}}"
						+ "</div><div class=\"bb-qb\">";

		private static Map<String, BBCodeConfig> theGrammarWhoseHeaderWritesTheTagsOwnAuthorIntoItself() {
			Map<String, BBCodeConfig> grammar = new HashMap<>(buildConfig());
			BBCodeConfig quote = quoteConfig();
			quote.getAttributeConfig().put("author=msg=", mode(A_HEADER_THAT_WRITES_THE_TAGS_OWN_AUTHOR_INTO_ITSELF,
					attr("author=", "{{0}}"), attr("msg=", "{{1}}")));
			grammar.put("QUOTE", quote);
			return grammar;
		}

		@Test
		void anAuthorWhoWritesTheEnginesOwnSlotSyntaxIsReadAsTextRatherThanAsTemplate() {
			renderer.useGrammar(theGrammarWhoseHeaderWritesTheTagsOwnAuthorIntoItself());
			String forgedAuthor = "{{msg.author}}{{#msg.permitted}}forged{{/msg.permitted}}";

			String result = renderInScope("[quote author=" + forgedAuthor + " msg=5][/quote]",
					Map.of(5, resolved(true, REV_TS, "source body")));

			assertTrue(result.contains("Quote from Alice, who wrote it as " + forgedAuthor),
					"the attribute slots are filled with author-controlled values before this pass runs, so a "
							+ "pass that hands that filled string to a template engine lets an author's own text "
							+ "name the engine's variables and open its sections; the declared markup is compiled "
							+ "at grammar load, where no author value can reach it: " + result);
			assertFalse(result.contains("{{0}}"),
					"and the attribute slot the pass carried through the render is still filled afterwards: "
							+ result);
		}

		private static Map<String, BBCodeConfig> theGrammarWhoseSourceReferenceIsNamedPost() {
			BBCodeConfig quote = new BBCodeConfig();
			quote.setCode("quote");
			quote.setProcessContentFlag(true);
			quote.setEndTag("</div></div>");
			quote.setAllAttributeNamesAsString("post=");
			quote.setSourceReferenceAttribute("post=");
			quote.setSourceReferenceResolver(QuotedMessageSource.RESOLVER_CODE);
			quote.setAttributeConfig(new HashMap<>());
			quote.getAttributeConfig().put("post=",
					mode(QUOTE_HEADER.replace("msg.", "post."), attr("post=", "{{0}}")));
			indexTheValuePoliciesOfEveryDeclaredAttribute(quote);
			Map<String, BBCodeConfig> grammar = new HashMap<>(buildConfig());
			grammar.put("QUOTE", quote);
			return grammar;
		}

		@Test
		void aCodeThatNamesItsSourceReferenceAttributeSomethingElseWritesItsSlotsUnderThatName() {
			stubResolve(Map.of(5, resolved(true, REV_TS, "source body")));
			String underTheMsgName = renderInTheScopeOf("[quote msg=5][/quote]");

			renderer.useGrammar(theGrammarWhoseSourceReferenceIsNamedPost());
			String underThePostName = renderInTheScopeOf("[quote post=5][/quote]");

			assertEquals(underTheMsgName, underThePostName,
					"bb_code_config.source_reference_attribute declares the name, so a code declaring 'post' has "
							+ "to read {{post.*}} with no Java change; a hardcoded '{{msg.' prefix leaves the "
							+ "declared slots on the page: " + underThePostName);
			assertTrue(underThePostName.contains("/user/profile/7"), underThePostName);
		}

		@Test
		void aPermittedSourceMissingItsTimestampAndItsThreadDegradesInTheDeclaredMarkup() {
			NavigableMap<OffsetDateTime, ContentTagResolver.SourceRevision> revisions = new TreeMap<>();
			revisions.put(REV_TS, new ContentTagResolver.SourceRevision("source body", ContentFormat.BBCODE));

			String result = renderInScope("[quote msg=5][/quote]", Map.of(5,
					new ContentTagResolver.Resolved("Alice", 7, null, null, null, 3, true, revisions)));

			assertTrue(result.contains("Quote from <a class=\"bb-resource-link\" href=\"/user/profile/7\" "
					+ "data-resource=\"member\" data-user-id=\"7\">Alice</a> on  "
					+ "(<a class=\"bb-resource-link\" href=\"#\" data-resource=\"thread\">jump</a>)"),
					"a source with no created_ts writes no time element and one with no thread writes no jump "
							+ "destination; both degradations are conditionals in the declared markup now: "
							+ result);
			assertTrue(result.contains("source body"), result);
		}

		@Test
		void nestingCapDegradesInnerQuoteToEmbedded() {
			Map<Integer, ContentTagResolver.Resolved> master = new HashMap<>();
			master.put(5, resolved(true, REV_TS, "outer [quote msg=7]inner embedded[/quote]"));
			master.put(7, resolved(true, REV_TS, "SEVEN SOURCE"));

			String result = renderInScope("[quote msg=5][/quote]", master);

			assertTrue(result.contains("inner embedded"));
			assertFalse(result.contains("SEVEN SOURCE"));
		}
	}

	@Nested
	class SharedQuoteBodyWalk {

		private Renderer renderer;
		private SourceReferenceService handler;

		@BeforeEach
		void setup() {
			renderer = buildRenderer(mock(BBCodeDataProvider.class), messageResolver());
			handler = renderer.handler();
			renderer.useGrammar(QuoteSourceReference.buildConfig());
		}

		private String rewriteBodiesTo(String input, String replacement) {
			return handler.rewriteSourceReferenceBodies(input, (msgId, body) -> replacement);
		}

		@Test
		void everyMsgQuoteBodyIsHandedToTheRewriterAndReplacedInPlace() {
			assertEquals("[quote msg=7]REWRITTEN[/quote]",
					rewriteBodiesTo("[quote msg=7]original[/quote]", "REWRITTEN"));
			assertEquals("before [quote msg=7]REWRITTEN[/quote] after",
					rewriteBodiesTo("before [quote msg=7]original[/quote] after", "REWRITTEN"));
		}

		@Test
		void quoteMarkupTheWalkCannotResolveIsLeftExactlyAsWritten() {
			assertEquals("[quote]no msg id[/quote]", rewriteBodiesTo("[quote]no msg id[/quote]", "REWRITTEN"));
			assertEquals("[quote msg=7]never closed", rewriteBodiesTo("[quote msg=7]never closed", "REWRITTEN"));
			assertEquals("[/quote] with no opener", rewriteBodiesTo("[/quote] with no opener", "REWRITTEN"));
			assertEquals("[code][quote msg=7]literal[/quote][/code]",
					rewriteBodiesTo("[code][quote msg=7]literal[/quote][/code]", "REWRITTEN"),
					"a quote inside a literal region is source text, not markup the rewriter may touch");
			assertEquals("[code][quote msg=7]unterminated literal",
					rewriteBodiesTo("[code][quote msg=7]unterminated literal", "REWRITTEN"),
					"an unterminated literal region must swallow the rest of the input verbatim");
		}

		@Test
		void theWalkNeverFindsASourceReferenceTheRendererLeavesAsLiteralText() {
			for (String escaped : List.of(
					"[[quote msg=5]body[/quote]",
					"prefix [[quote msg=5]body[/quote] suffix",
					"[[/quote] [[quote msg=5]body[/quote]")) {
				String rendered = renderer.render(escaped);

				assertTrue(rendered.contains("[quote msg=5]"),
						"the renderer's char loop swallows the second bracket into the tag code, so [[ is the "
								+ "escape and this text is literal: " + rendered);
				assertTrue(handler.collectSourceReferenceIds(escaped).isEmpty(),
						"the quote-strip job walks with readTag and rescans from the next character, so it reads "
								+ "a real source reference where the renderer printed literal text; that job writes "
								+ "to message_history, so the two recognizers must agree: " + escaped);
				assertFalse(handler.containsSourceReference(escaped),
						"the same divergence decides whether a body is treated as containing a nested quote: "
								+ escaped);
				assertEquals(escaped, rewriteBodiesTo(escaped, "REWRITTEN"),
						"a body the renderer prints literally must survive the rewriter untouched: " + escaped);
			}
		}

		@Test
		void theWalkStillFindsTheUnescapedSourceReferenceTheRendererDoesRender() {
			String unescaped = "[quote msg=5]body[/quote]";

			assertEquals(Set.of(5), handler.collectSourceReferenceIds(unescaped),
					"aligning the recognizers must not make the walk blind to ordinary quotes");
			assertTrue(handler.containsSourceReference(unescaped));
			assertEquals("[quote msg=5]REWRITTEN[/quote]", rewriteBodiesTo(unescaped, "REWRITTEN"));
		}

		@Test
		void anIdentityRewriteIsAFaithfulRoundTripForEveryShapeTheWalkUnderstands() {
			for (String input : List.of(
					"plain text with no markup",
					"[b]bold[/b]",
					"[quote msg=7]body[/quote]",
					"[quote msg=7]outer [quote msg=8]inner[/quote] tail[/quote]",
					"[quote msg=7]a[/quote] between [quote msg=9]b[/quote]",
					"[code]literal [quote msg=7]x[/quote][/code]",
					"unmatched [ bracket",
					"[quote msg=7]never closed"))
				assertEquals(input, handler.rewriteSourceReferenceBodies(input, (msgId, body) -> body),
						"an identity rewrite must return the input unchanged: " + input);
		}
	}

	@Nested
	class Sanitizer {

		private final ContentOutputSanitizer sanitizer = sanitizer();

		@Test
		void keepsAllowlistedInlineStyle() {
			String result = sanitizer.sanitize(
					"<span style=\"--bb-glow-color:red;--bb-glow-radius:2px\">hi</span>");
			assertTrue(result.contains("--bb-glow-color:red"), result);
			assertTrue(result.contains("--bb-glow-radius:2px"), result);
		}

		@Test
		void stripsDisallowedStyleProperty() {
			String result = sanitizer.sanitize("<span style=\"position:fixed;--bb-color:red\">hi</span>");
			assertFalse(result.contains("position"), result);
			assertTrue(result.contains("--bb-color:red"), result);
		}

		@Test
		void stripsDangerousStyleValue() {
			String result = sanitizer.sanitize("<span style=\"background-color:url(javascript:alert(1))\">hi</span>");
			assertFalse(result.toLowerCase().contains("javascript"), result);
			assertFalse(result.contains("url("), result);
		}

		@Test
		void stripsStyleOnNonSpanElements() {
			String result = sanitizer.sanitize("<div style=\"color:red\">hi</div>");
			assertFalse(result.contains("style"), result);
		}

		@Test
		void stripsBackslashRelativeUrl() {
			String result = sanitizer.sanitize("<a href=\"/\\evil.com\">x</a>");
			assertFalse(result.contains("evil.com"), result);
		}

		@Test
		void keepsPlainRelativeUrl() {
			String result = sanitizer.sanitize("<a href=\"/wiki/Foo\">x</a>");
			assertTrue(result.contains("/wiki/Foo"), result);
		}

		@Test
		void stripsJavascriptUrlAndScript() {
			assertFalse(sanitizer.sanitize("<a href=\"javascript:alert(1)\">x</a>").toLowerCase().contains("javascript"));
			assertFalse(sanitizer.sanitize("<script>alert(1)</script>").toLowerCase().contains("<script"));
			assertFalse(sanitizer.sanitize("<img src=x onerror=alert(1)>").toLowerCase().contains("onerror"));
		}

		@Test
		void keepsYoutubeEmbedIframe() {
			String result = sanitizer.sanitize(
					"<div class=\"bb-code-youtube\"><iframe width=\"640\" height=\"480\" src=\"https://www.youtube.com/embed/dQw4w9WgXcQ\"></iframe></div>");
			assertTrue(result.contains("<iframe"), result);
			assertTrue(result.contains("youtube.com/embed/dQw4w9WgXcQ"), result);
		}

		@Test
		void stripsNonYoutubeIframe() {
			String result = sanitizer.sanitize("<iframe src=\"https://evil.example.com/frame\"></iframe>");
			assertFalse(result.contains("<iframe"), result);
			assertFalse(result.contains("evil.example.com"), result);
		}

		@Test
		void keepsWidgetDivAttribute() {
			String result = sanitizer.sanitize(
					"<div class=\"bb-code-widget\" data-widget-title=\"Featured Project\">x</div>");
			assertTrue(result.contains("data-widget-title=\"Featured Project\""), result);
		}

		@Test
		void stripsIslandInjectionAttributes() {
			String result = sanitizer.sanitize(
					"<div class=\"bb-code-island\" data-island=\"X\" data-props=\"{}\">x</div>");
			assertFalse(result.contains("data-island"), result);
			assertFalse(result.contains("data-props"), result);
		}

		@ParameterizedTest
		@MethodSource("youtubeEmbedNormalizationCases")
		void normalizeYoutubeEmbedCanonicalizesOrRejects(String caseName, String embedUrl, String expected) {
			assertEquals(expected, ContentOutputSanitizer.normalizeYoutubeEmbed(embedUrl, ContentOutputSanitizer.DEFAULT_YOUTUBE_EMBED_PREFIX));
		}

		static Stream<Arguments> youtubeEmbedNormalizationCases() {
			return Stream.of(
					arguments("normalizesLegacyYoutubeUrlEmbeds legacyVForm",
							"https://www.youtube.com/embed/http://www.youtube.com/v/E5qOEhp-cNQ&hl=en&fs=1",
							"https://www.youtube.com/embed/E5qOEhp-cNQ"),
					arguments("normalizesLegacyYoutubeUrlEmbeds legacyWatchForm",
							"https://www.youtube.com/embed/http://www.youtube.com/watch?v=dQw4w9WgXcQ",
							"https://www.youtube.com/embed/dQw4w9WgXcQ"),
					arguments("normalizesLegacyYoutubeUrlEmbeds alreadyCanonical",
							"https://www.youtube.com/embed/dQw4w9WgXcQ",
							"https://www.youtube.com/embed/dQw4w9WgXcQ"),
					arguments("rejectsUnextractableYoutubeEmbeds foreignUrlInsideEmbedPath",
							"https://www.youtube.com/embed/http://evil.example.com/page", null),
					arguments("rejectsUnextractableYoutubeEmbeds foreignHost",
							"https://evil.example.com/embed/abc123def45", null));
		}

		@Test
		void skipsAutolinkInsideTheTeletypeWrapperTheTtBBCodeActuallyEmits() {
			assertFalse(sanitizer.sanitize(
					"<span class=\"bb-code-tt\">visit http://example.com/page</span>").contains("<a "),
					"AUTOLINK_SKIP_TAGS names the tt element, but R__03_bbcodes.sql attribute mode 66 makes [tt] "
							+ "emit <span class=\"bb-code-tt\">, so a skip keyed on the element name alone never "
							+ "fires for the only tag in the engine that is named tt");
			assertFalse(sanitizer.sanitize("<tt>visit http://example.com/page</tt>").contains("<a "),
					"the tt element is on the safelist and still reachable from author markdown, so removing its "
							+ "element-name entry would trade one hole for another");
		}

		@Test
		void unextractableYoutubeEmbedIframeIsStripped() {
			String result = sanitizer.sanitize(
					"<iframe src=\"https://www.youtube.com/embed/http://evil.example.com/page\"></iframe>");
			assertFalse(result.contains("<iframe"), result);
		}

		@Test
		void stripsAnAuthorSuppliedIdSoPageAssemblyAlwaysOwnsTheHeadingAnchor() {
			String result = sanitizer.sanitize("<h2 id=\"author-anchor\" class=\"bb-code-h2\">Title</h2>");

			assertFalse(result.contains("id="),
					"CmsPageRenderer assigns every heading its own deduped anchor unconditionally, which is only "
							+ "safe because no id reaches it; safelisting id here would silently make it clobber "
							+ "author anchors and would need the assignment guarded again: " + result);
			assertTrue(result.contains("class=\"bb-code-h2\""),
					"a class the renderer emits is kept for every element, so this test must be failing on id "
							+ "alone");
		}

		static final String ONE_POST_MAY_NOT_BE_ABLE_TO_KILL_ITS_THREAD =
				"ForumService renders every message on the page, and a StackOverflowError is an Error rather than "
						+ "an exception, so it escapes the handlers as a 500. One saved post carrying a value that "
						+ "overflows a nested-quantifier property pattern therefore breaks that thread page on "
						+ "every request, for every reader, permanently. No style value may be matched by a "
						+ "pattern whose cost grows with the length of the value.";

		static String aTextShadowOf(int segments) {
			return IntStream.range(0, segments)
					.mapToObj(segment -> "0.15rem 0.15rem 0.25rem #aabbcc")
					.collect(Collectors.joining(", "));
		}

		@Test
		void aTextShadowLongEnoughToOverflowTheStackCannotReachAPatternThatRecurses() {
			String payload = "<span style=\"text-shadow:" + aTextShadowOf(192) + "\">glow</span>";

			String result = assertDoesNotThrow(() -> sanitizer.sanitize(payload),
					ONE_POST_MAY_NOT_BE_ABLE_TO_KILL_ITS_THREAD);

			assertFalse(result.contains("style="),
					"text-shadow is composed by the stylesheet now, so the server has no reason to admit one: "
							+ result);
			assertTrue(result.contains("glow"), result);
		}

		@ParameterizedTest(name = "{0}")
		@MethodSource("stylePropertiesTheServerNoLongerComposes")
		void aStylePropertyThatIsNotABbCustomPropertyIsDropped(String caseName, String declaration) {
			assertFalse(sanitizer.sanitize("<span style=\"" + declaration + "\">x</span>").contains("style="),
					"the server emits classes plus --bb-* scalars and the stylesheet composes them, so any other "
							+ "property in a style attribute came from raw author html: " + declaration);
		}

		static Stream<Arguments> stylePropertiesTheServerNoLongerComposes() {
			return Stream.of(
					arguments("composed colour", "color:red"),
					arguments("composed size", "font-size:24pt"),
					arguments("composed font", "font-family:comic sans ms"),
					arguments("composed shadow", "text-shadow:0 0 2px red"),
					arguments("composed list marker", "list-style-type:lower-roman"),
					arguments("composed alignment", "text-align:center"),
					arguments("composed background", "background-color:red"),
					arguments("a custom property nobody declared", "--tw-ring-color:red"),
					arguments("a bb prefixed property nobody declared", "--bb-position:fixed"),
					arguments("case folded so it reads as one of ours", "--BB-COLOR:red"));
		}

		static final String NO_CLASS_MAY_PAINT_OVER_THE_PAGE =
				"class was safelisted for every element and Jsoup cannot pattern match an attribute value, so an "
						+ "author could name any utility the site's own stylesheet ships. The markdown lane hands "
						+ "raw html straight to this sanitiser, so a post could paint a full viewport overlay and "
						+ "ask the reader to sign in again.";

		@Test
		void aFullViewportOverlayClassCannotSurviveAPost() {
			String result = sanitizer.sanitize(
					"<div class=\"fixed inset-0 z-50 bg-black/80\">"
							+ "<form class=\"absolute top-1/2 left-1/2\">Session expired. Sign in again.</form>"
							+ "</div>");

			for (String painted : List.of("fixed", "inset-0", "z-50", "bg-black/80", "absolute", "top-1/2",
					"left-1/2"))
				assertFalse(result.contains(painted), NO_CLASS_MAY_PAINT_OVER_THE_PAGE + " Got: " + result);
			assertTrue(result.contains("Session expired. Sign in again."),
					"only the paint is refused; the words the author wrote stay visible: " + result);
		}

		@Test
		void theOverlayIsRefusedOnTheMarkdownLaneWhereRawHtmlReachesTheSanitiser() {
			BBCodeGrammarHolder grammarHolder = grammarHolder();
			RenderedTextEnricher enricher = enricher();
			ContentOutputSanitizer lanesSanitizer = sanitizer(grammarHolder, enricher);
			Renderer renderer = buildRenderer(mock(BBCodeDataProvider.class), grammarHolder, enricher,
					lanesSanitizer, messageResolver());
			renderer.useGrammar(seededBBCodeGrammar());

			String result = renderer.renderMarkdown("<div class=\"fixed inset-0 z-50 bg-black/80\">boo</div>");

			assertFalse(result.contains("fixed"), NO_CLASS_MAY_PAINT_OVER_THE_PAGE + " Got: " + result);
			assertFalse(result.contains("z-50"), result);
		}

		@ParameterizedTest(name = "{0}")
		@MethodSource("classesEveryLaneStillEmits")
		void aClassTheEngineItselfEmitsIsNotStripped(String caseName, String element) {
			assertTrue(sanitizer.sanitize(element).contains("class="),
					"the allowlist is measured against what the seeded grammar, the smiley pass, the date element "
							+ "and the seeded content templates actually write: " + element);
		}

		static Stream<Arguments> classesEveryLaneStillEmits() {
			return Stream.of(
					arguments("a seeded code class", "<span class=\"bb-code-b\">x</span>"),
					arguments("a smiley wrapper", "<span class=\"bb-smiley bb-smiley-cool\">8)</span>"),
					arguments("a long date", "<time class=\"bb-date-long\">x</time>"),
					arguments("a resource link", "<a class=\"bb-resource-link\" href=\"/wiki/A\">x</a>"),
					arguments("a grid width", "<div class=\"bb-code-grid bb-grid-2\">x</div>"),
					arguments("a table variant", "<table class=\"bb-code-table bb-table-infobox\"><tr><td>x"
							+ "</td></tr></table>"),
					arguments("a toc marker", "<span class=\"bb-toc\"></span>"),
					arguments("a colour span", "<span class=\"bb-color\" style=\"--bb-color:black\">x</span>"),
					arguments("a list style", "<ul class=\"bb-list-lower-roman\"><li>x</li></ul>"),
					arguments("a seeded template panel", "<div class=\"bb-template-panel bb-template-stacked\">"
							+ "x</div>"),
					arguments("a seeded template lead", "<h1 class=\"bb-template-lead-title\">x</h1>"));
		}

		static final String THE_SEEDED_TEMPLATES_SPEAK_THE_SAME_VOCABULARY =
				"the seeded content templates used to carry Tailwind utilities, which forced the allowlist to "
						+ "carry a hardcoded copy of whatever those rows happened to say. They speak bb-* now, so "
						+ "the allowlist is the pattern alone and a template row that reaches for a utility class "
						+ "loses it rather than widening the rule.";

		@ParameterizedTest(name = "{0}")
		@MethodSource("utilityClassesTheSeededTemplatesGaveUp")
		void aTemplateRowReachingForAUtilityClassLosesIt(String caseName, String utility) {
			assertFalse(sanitizer.sanitize("<div class=\"" + utility + "\">x</div>").contains("class="),
					THE_SEEDED_TEMPLATES_SPEAK_THE_SAME_VOCABULARY + " Kept: " + utility);
		}

		static Stream<Arguments> utilityClassesTheSeededTemplatesGaveUp() {
			return Stream.of(
					arguments("spacing", "p-4"), arguments("stack spacing", "space-y-2"),
					arguments("margin", "mt-4"), arguments("padding", "pt-3"),
					arguments("border width", "border-t"), arguments("border colour", "border-default"),
					arguments("type scale", "text-4xl"), arguments("body scale", "text-base"),
					arguments("small scale", "text-sm"), arguments("emphasis", "italic"),
					arguments("highlight token", "text-highlighted"), arguments("dim token", "text-dimmed"),
					arguments("muted token", "text-muted"), arguments("stack spacing tight", "space-y-1"));
		}

		static String theValueSplicedInto(String markup, String value) {
			return BBCodeParser.theMarkupWithEveryAttributeSlotFilled(markup, mode(markup, attr("text=", "{{0}}")),
					new BBCodeConfig.ParsedAttributes("text=", Map.of("text=", value), List.of()));
		}

		@ParameterizedTest(name = "{0}")
		@MethodSource("hostileValuesTheEscaperHasToCarryWordForWord")
		void anEscapedValueComesBackWordForWordFromBothTheTextAndTheQuotedAttributeContext(String caseName,
				String hostile) {
			String inText = theValueSplicedInto("<div>{{0}}</div>", hostile);
			String inAttribute = theValueSplicedInto("<a title=\"{{0}}\">x</a>", hostile);

			assertEquals(hostile, Jsoup.parseBodyFragment(inText).body().selectFirst("div").wholeText(),
					"escaping is the whole of what stands between an authored value and the markup it is "
							+ "spliced into, and nothing else pins that the escaper is adequate: " + inText);
			assertEquals(hostile, Jsoup.parseBodyFragment(inAttribute).body().selectFirst("a").attr("title"),
					"the smiley pass writes escaped text inside title=\"...\" and the date element inside "
							+ "datetime=\"...\", so the quoted attribute context carries the same property: "
							+ inAttribute);
		}

		static Stream<Arguments> hostileValuesTheEscaperHasToCarryWordForWord() {
			return Stream.of(
					arguments("an attribute break out", "\"><img src=x onerror=alert(1)>"),
					arguments("a single quoted event handler", "' onmouseover='x"),
					arguments("a raw text element break out", "</title><svg onload=alert(1)>"),
					arguments("a cdata close", "]]>"),
					arguments("a comment close and a style close", "--></style>"),
					arguments("an entity the author typed", "&amp;already"),
					arguments("non ascii text", "café ☃ 𝟘"),
					arguments("a no break space", "\u00a0"),
					arguments("a supplementary codepoint whose low half reads as a surrogate",
							new String(Character.toChars(0x1D800))),
					arguments("a backtick", "`"),
					arguments("an equals sign", "="));
		}

		@Test
		void aNullCharacterIsTheOneValueTheTextContextCannotGiveBack() {
			String escaped = Entities.escape("\u0000");

			assertEquals("&#x0;", escaped,
					"the escaper does write it, so whatever happens next is not the escaper's doing");
			assertEquals("", Jsoup.parseBodyFragment("<div>" + escaped + "</div>").body()
					.selectFirst("div").wholeText(),
					"U+0000 sits outside the round trip property because the HTML parser drops it per spec, "
							+ "not because the escaper mishandles it; the property is not weakened to admit it");
			assertEquals("\u0000", Jsoup.parseBodyFragment("<a title=\"" + escaped + "\">x</a>").body()
					.selectFirst("a").attr("title"),
					"and the quoted attribute context does give it back, which is what makes the exclusion a "
							+ "property of the parser rather than a hole in the escaper");
		}
	}

	@Nested
	class UrlAndStylePolicy {

		private final ContentOutputSanitizer sanitizer = sanitizer();

		@ParameterizedTest(name = "{0}")
		@MethodSource("urlsTheOnePolicyDecides")
		void oneUrlPolicyDecidesEveryPlaceAHrefCanCome(String caseName, String written, String expected) {
			assertEquals(Optional.ofNullable(expected), LinkPolicy.theSafeHrefFor(written),
					"the attribute arm, the sanitiser and the autolinker all ask this one method now, so a "
							+ "disagreement between them is no longer expressible: " + written);
		}

		static Stream<Arguments> urlsTheOnePolicyDecides() {
			return Stream.of(
					arguments("absolute http", "http://zfgc.com/a", "http://zfgc.com/a"),
					arguments("absolute https", "https://zfgc.com/a", "https://zfgc.com/a"),
					arguments("ftp", "ftp://zfgc.com/a", "ftp://zfgc.com/a"),
					arguments("mailto", "mailto:a@zfgc.com", "mailto:a@zfgc.com"),
					arguments("site relative", "/wiki/Home", "/wiki/Home"),
					arguments("fragment", "#section", "#section"),
					arguments("schemeless domain is promoted to https", "zfgc.com/a", "https://zfgc.com/a"),
					arguments("schemeless www is promoted to https", "www.zfgc.com/a", "https://www.zfgc.com/a"),
					arguments("javascript dies", "javascript:alert(1)", null),
					arguments("javascript with padding dies", "  javascript:alert(1)".trim(), null),
					arguments("data uri dies", "data:text/html;base64,PHN2Zz4=", null),
					arguments("protocol relative is not a safe relative url", "//evil.test/a", null),
					arguments("backslash relative dies", "/a\\b", null),
					arguments("tab relative dies protocol relative", "/\t/evil.test/a", null),
					arguments("newline relative dies protocol relative", "/\n/evil.test/a", null),
					arguments("carriage return relative dies protocol relative", "/\r/evil.test/a", null),
					arguments("tab relative to another host dies protocol relative", "/\t/evil.example/x", null),
					arguments("tab inside a schemeless domain resolves without it", "zfgc.com/\ta",
							"https://zfgc.com/a"),
					arguments("tab inside an absolute url resolves without it", "https://zfgc.com/\ta",
							"https://zfgc.com/a"),
					arguments("tab inside a fragment resolves without it", "#a\tb", "#ab"),
					arguments("a trailing newline is trimmed off a relative url", "/wiki/Foo\n", "/wiki/Foo"),
					arguments("a leading c0 control is trimmed off a relative url", "\u0001/wiki/Foo", "/wiki/Foo"),
					arguments("a mid string null dies", "/wiki/a\u0000b", null),
					arguments("a mid string c0 control the browser keeps dies", "/wiki/a\u0001b", null),
					arguments("a delete character the browser keeps dies", "/wiki/a\u007fb", null),
					arguments("a unicode line separator is not a character the browser strips",
							"/wiki/Foo\u2028", "/wiki/Foo\u2028"),
					arguments("empty dies", "", null),
					arguments("nothing but characters the browser strips dies", " \t\n\r ", null));
		}

		static final String THE_BROWSER_RESOLVES_WHAT_IS_LEFT_AFTER_ITS_OWN_STRIPS =
				"a browser removes every U+0009, U+000A and U+000D from a url attribute and trims leading and "
						+ "trailing C0-or-space off it, then resolves what is left. Refusing the whole value "
						+ "instead threw away a link the reader would have followed; validating the written value "
						+ "and emitting it unchanged would have validated a string the browser never resolves. "
						+ "The policy normalises exactly those characters, validates the result and returns the "
						+ "result, so every consumer writes back the bytes the browser will act on.";

		@Test
		void aHrefCarryingOnlyCharactersTheBrowserStripsSurvivesAsTheValueTheBrowserResolves() {
			assertTrue(sanitizer.sanitize("<a href=\"/wiki/Foo\n\">x</a>").contains("href=\"/wiki/Foo\""),
					THE_BROWSER_RESOLVES_WHAT_IS_LEFT_AFTER_ITS_OWN_STRIPS + " Got: "
							+ sanitizer.sanitize("<a href=\"/wiki/Foo\n\">x</a>"));
			assertFalse(sanitizer.sanitize("<a href=\"/\t/evil.example/x\">x</a>").contains("evil.example"),
					"and the same normalisation turns a tab-hidden protocol relative url into the //host form "
							+ "the policy already refuses, rather than admitting it as a site path");
			assertFalse(sanitizer.sanitize("<a href=\"/wiki/a\u0001b\">x</a>").contains("href="),
					"a control character the browser does not strip is still a value nothing may emit");
			assertEquals("/wiki/a\ufffdb", Jsoup.parseBodyFragment("<a href=\"/wiki/a\u0000b\">x</a>")
					.body().selectFirst("a").attr("href"),
					"U+0000 never reaches the policy through markup because the HTML parser substitutes it per "
							+ "spec, which is why the null row is pinned on LinkPolicy directly rather than here");
		}

		@Test
		void aTabHiddenProtocolRelativeUrlDiesAsTheProtocolRelativeUrlItBecomes() {
			assertEquals(Optional.of("/wiki/Foo"), LinkPolicy.theSafeHrefFor("/\twiki/Foo"),
					"the browser removes U+0009 wherever it sits and resolves /wiki/Foo, so /wiki/Foo is the "
							+ "string the policy has to judge and the string it has to hand back");
			assertEquals(Optional.empty(), LinkPolicy.theSafeHrefFor("/\t/evil.example/x"),
					"the same removal turns this one into //evil.example/x, so it dies for being protocol "
							+ "relative rather than for carrying a control character. Removing less than the "
							+ "browser removes would still refuse it, but for a reason the browser does not share, "
							+ "and would refuse /\twiki/Foo with it");
			assertEquals(Optional.empty(), LinkPolicy.theSafeHrefFor("//evil.example/x"),
					"which is the refusal the written protocol relative form already gets");
		}

		@Test
		void aRelativeUrlCarryingACharacterTheBrowserStripsIsNotARelativeUrlAtAll() {
			assertFalse(LinkPolicy.isSafeRelativeUrl("/\t/evil.test/a"),
					"a browser drops U+0009, U+000A and U+000D out of a url attribute before it parses it, so "
							+ "this is stored as an internal link and resolves protocol relative to evil.test");
			assertFalse(sanitizer.sanitize("<a href=\"/\t/evil.test/a\">looks internal</a>").contains("evil.test"),
					"the same value reaches the reader through stripIfNotAllowed, where a phishing destination "
							+ "that reads as a site path is the whole of the attack");
			assertFalse(sanitizer.sanitize("<img src=\"/\t/evil.test/pixel.png\">").contains("evil.test"),
					"and through src, where a remote pixel logs the address and user agent of every reader of "
							+ "the thread");
		}

		@Test
		void theSchemelessPromotionIsHttpsEverywhereItCanHappen() {
			assertEquals(LinkPolicy.SCHEME_A_SCHEMELESS_DOMAIN_IS_PROMOTED_TO, "https://",
					"promoting a schemeless link to plaintext http downgrades every reader who follows it");
			assertTrue(sanitizer.sanitize("<a href=\"zfgc.com/a\">x</a>").contains("href=\"https://zfgc.com/a\""),
					"an authored href gets the same promotion the autolinker gives a bare one");
			assertTrue(sanitizer.sanitize("visit www.zfgc.com/a now").contains("href=\"https://www.zfgc.com/a\""));
		}

		@Test
		void aBareUrlThePolicyRefusesIsLeftAsTextRatherThanLinkedToNowhere() {
			assertEquals("visit www.q now", sanitizer.sanitize("visit www.q now"),
					"the autolinker used to prefix a scheme onto anything shaped like www., including shapes the "
							+ "url policy would never accept as a destination");
		}

		static final String ONE_POLICY_NOW_PROMOTES_EVERY_URL_ATTRIBUTE =
				"the old sanitiser promoted a schemeless value only when the attribute was href and dropped src "
						+ "and cite outright. A markdown ![](i.imgur.com/a.png) writes an <img> with no scheme, so "
						+ "the image reached the reader as a broken empty box. One url policy for every attribute "
						+ "keeps the value and promotes it, which is the benign direction; this row pins that as a "
						+ "decision rather than as an accident of unification.";

		@Test
		void aSchemelessImageSourceIsPromotedTheWayAHrefIs() {
			String sanitized = sanitizer.sanitize("<img src=\"i.imgur.com/a.png\">");

			assertTrue(sanitized.contains("src=\"https://i.imgur.com/a.png\""),
					ONE_POLICY_NOW_PROMOTES_EVERY_URL_ATTRIBUTE + " Got: " + sanitized);
		}

		@Test
		void aSchemelessBlockquoteCitationIsPromotedTheWayAHrefIs() {
			String sanitized = sanitizer.sanitize("<blockquote cite=\"zfgc.com/thread/1\">x</blockquote>");

			assertTrue(sanitized.contains("cite=\"https://zfgc.com/thread/1\""),
					ONE_POLICY_NOW_PROMOTES_EVERY_URL_ATTRIBUTE + " Got: " + sanitized);
		}

		@Test
		void aScriptUrlStillDiesInEveryAttributeTheOnePolicyReaches() {
			assertFalse(sanitizer.sanitize("<img src=\"javascript:alert(1)\">").contains("javascript"),
					"promoting a schemeless source may not be read as accepting any source");
			assertFalse(sanitizer.sanitize("<blockquote cite=\"javascript:alert(1)\">x</blockquote>")
					.contains("javascript"));
		}

		@Test
		void aJavascriptHrefStillDiesInTheSanitiser() {
			assertFalse(sanitizer.sanitize("<a href=\"javascript:alert(1)\">x</a>").contains("javascript"),
					"the one url policy has to keep refusing script urls or unifying it traded safety for tidiness");
		}

		@ParameterizedTest(name = "{0}")
		@MethodSource("styleDeclarationsTheAllowlistKeeps")
		void aStyleValueMatchingItsPropertysShapeSurvives(String caseName, String declaration) {
			assertTrue(sanitizer.sanitize("<span style=\"" + declaration + "\">x</span>").contains("style="),
					"every value the seeded modes and the corpus actually emit has to pass the allowlist: "
							+ declaration);
		}

		static Stream<Arguments> styleDeclarationsTheAllowlistKeeps() {
			return Stream.of(
					arguments("named colour", "--bb-color:red"),
					arguments("hex colour", "--bb-color:#aabbcc"),
					arguments("rgba colour", "--bb-color:rgba(1,2,3,0.5)"),
					arguments("legacy size level output", "--bb-size:12pt"),
					arguments("pixel size", "--bb-size:12px"),
					arguments("font name list", "--bb-font:comic sans ms, cursive"),
					arguments("glow colour", "--bb-glow-color:red"),
					arguments("glow radius", "--bb-glow-radius:2px"),
					arguments("shadow colour", "--bb-shadow-color:#aabbcc"));
		}

		@ParameterizedTest(name = "{0}")
		@MethodSource("styleDeclarationsTheAllowlistDrops")
		void aStyleValueThatIsNotTheShapeItsPropertyAdmitsIsDropped(String caseName, String declaration) {
			assertFalse(sanitizer.sanitize("<span style=\"" + declaration + "\">x</span>").contains("style="),
					"a substring denylist loses to encodings it did not enumerate; the allowlist has to refuse "
							+ "anything that is not the shape the property admits: " + declaration);
		}

		static Stream<Arguments> styleDeclarationsTheAllowlistDrops() {
			return Stream.of(
					arguments("url function", "--bb-color:url(http://evil.test/a)"),
					arguments("css escaped url function", "--bb-color:\\75 rl(http://evil.test/a)"),
					arguments("entity carrying url function", "--bb-color:&#117;rl(http://evil.test/a)"),
					arguments("expression", "--bb-color:expression(alert(1))"),
					arguments("script url", "--bb-color:javascript:alert(1)"),
					arguments("comment smuggling", "--bb-color:re/*x*/d"),
					arguments("angle brackets", "--bb-color:<script>"),
					arguments("position property", "position:fixed"),
					arguments("behavior property", "behavior:url(#default#time2)"),
					arguments("size with a function", "--bb-size:calc(100px)"),
					arguments("a keyword size no attribute_data_type row admits", "--bb-size:large"),
					arguments("a length unit no attribute_data_type row admits", "--bb-glow-radius:5vh"),
					arguments("a signed length no attribute_data_type row admits", "--bb-glow-radius:-2px"),
					arguments("font carrying a url", "--bb-font:url(http://evil.test/a)"),
					arguments("font carrying angle brackets", "--bb-font:<script>"),
					arguments("radius carrying a url", "--bb-glow-radius:url(x)"),
					arguments("a scalar written into the wrong variable", "--bb-glow-radius:red"),
					arguments("empty value", "--bb-color:"));
		}

		static BBCodeAttribute attributeOfType(String name, AttributeDataType dataType) {
			BBCodeAttribute attribute = new BBCodeAttribute();
			attribute.setName(name);
			attribute.setAttributeIndex("{{0}}");
			attribute.setDataType(dataType);
			attribute.setValuePolicy(seededAttributeValuePolicies().get(dataType));
			return attribute;
		}

		static Map<String, BBCodeConfig> theGrammarFillingOneCustomPropertyFrom(AttributeDataType oneMode,
				AttributeDataType anotherMode) {
			BBCodeConfig lane = new BBCodeConfig();
			lane.setCode("lane");
			lane.setProcessContentFlag(true);
			lane.setEndTag("</span>");
			lane.setAllAttributeNamesAsString("one=,two=");
			lane.setAttributeConfig(new HashMap<>());
			lane.getAttributeConfig().put("one=",
					mode("<span style=\"--bb-lane: {{0}}\">", attributeOfType("one=", oneMode)));
			lane.getAttributeConfig().put("two=",
					mode("<span style=\"--bb-lane: {{0}}\">", attributeOfType("two=", anotherMode)));
			indexTheValuePoliciesOfEveryDeclaredAttribute(lane);
			Map<String, BBCodeConfig> grammar = new HashMap<>();
			grammar.put("LANE", lane);
			return grammar;
		}

		@Test
		void aCustomPropertyTwoModesFillFromOneDataTypeIsOneBinding() {
			BBCodeGrammarHolder grammarHolder = grammarHolder();
			ContentOutputSanitizer bound = new ContentOutputSanitizer(enricher(), grammarHolder);

			assertDoesNotThrow(() -> grammarHolder.publish(theGrammarCarryingOnly(
					bound.theCustomPropertiesPreparedFrom(theGrammarFillingOneCustomPropertyFrom(
							AttributeDataType.COLOR, AttributeDataType.COLOR)))),
					"two modes writing one variable is how a code offers the same value in more than one "
							+ "attribute shape; the binding is only ambiguous when the shapes disagree");
			assertTrue(bound.sanitize("<span style=\"--bb-lane:red\">x</span>").contains("--bb-lane:red"),
					"and the one binding still decides which values that variable admits");
		}

		@Test
		void aCustomPropertyFilledFromTwoDataTypesIsRefusedNamingTheVariable() {
			ContentOutputSanitizer bound = new ContentOutputSanitizer(enricher(), grammarHolder());

			InvalidBBCodeGrammarException refused = assertThrows(InvalidBBCodeGrammarException.class,
					() -> bound.theCustomPropertiesPreparedFrom(theGrammarFillingOneCustomPropertyFrom(
							AttributeDataType.COLOR, AttributeDataType.SIZE)));

			assertTrue(refused.getMessage().contains("--bb-lane"),
					"the administrator who wrote the row has to be told which variable is ambiguous: "
							+ refused.getMessage());
			assertTrue(refused.getMessage().contains("COLOR") && refused.getMessage().contains("SIZE"),
					"and which two data types disagree over it: " + refused.getMessage());
		}

		@Test
		void theScalarPatternsAreWhatDropsTheseValuesRatherThanThePropertyNameAlone() {
			assertTrue(sanitizer.sanitize("<span style=\"--bb-color:red\">x</span>").contains("--bb-color:red"),
					"--bb-color is a declared custom property, so the url-function and expression cases above are "
							+ "refused by the scalar shape, not by the property allowlist");
		}
	}

	@Nested
	class SpecialLinks {

		static Renderer renderer;

		@BeforeAll
		static void loadTheRealSeededEngine() {
			BBCodeGrammarHolder grammarHolder = grammarHolder();
			RenderedTextEnricher enricher = enricher();
			ContentOutputSanitizer sanitizer = sanitizer(grammarHolder, enricher);
			enricher.registerSmilies(seededSmilies());
			renderer = buildRenderer(mock(BBCodeDataProvider.class), grammarHolder, enricher, sanitizer,
					messageResolver());
			renderer.useGrammar(seededBBCodeGrammar());
		}

		static Stream<Arguments> legacySpecialHrefCases() {
			return Stream.of(
					arguments("plain page name",
							"[wiki=Special:Random]Random page[/wiki]", "/wiki/special/random"),
					arguments("mixed case page name",
							"[wiki=Special:RecentChanges]Recent changes[/wiki]", "/wiki/special/recentchanges"),
					arguments("subpage keeps its case",
							"[wiki=Special:WhatLinksHere/Ocarina]What links here[/wiki]",
							"/wiki/special/whatlinkshere/Ocarina"),
					arguments("query string keeps its case",
							"[url=/wiki/Special:AllPages?ns=Items]All pages[/url]",
							"/wiki/special/allpages?ns=Items"),
					arguments("fragment keeps its case",
							"[url=/wiki/Special:Statistics#pages]Statistics[/url]",
							"/wiki/special/statistics#pages"),
					arguments("page name with no app route reaches the catch-all",
							"[wiki=Special:Preferences]Preferences[/wiki]", "/wiki/special/preferences"));
		}

		@ParameterizedTest(name = "{0}")
		@MethodSource("legacySpecialHrefCases")
		void onlyThePageNameSegmentOfALegacySpecialHrefIsLowercased(String caseName, String source,
				String expectedHref) {
			String rendered = renderer.render(source);

			assertTrue(rendered.contains("href=\"" + expectedHref + "\""),
					"migrated wiki content is the only source of /wiki/Special: hrefs and the React routes are all "
							+ "lowercase, so the page name folds and everything after it -- subpath, query, fragment -- "
							+ "is the caller's data and must survive byte for byte: " + rendered);
		}

		@Test
		void aLegacySpecialPathTheAuthorWroteAsProseIsLeftAsProse() {
			String rendered = renderer.render("the legacy path was /wiki/Special:Random and it is gone");

			assertTrue(rendered.contains("/wiki/Special:Random"),
					"the deleted regex rewrote every occurrence in the serialised document, so a sentence about the "
							+ "legacy wiki silently became a false statement about it while staying just as "
							+ "unclickable; only an href decides where a reader lands: " + rendered);
		}

		@Test
		void aBareLegacySpecialPathIsNeverAutolinkedSoTheProseCaseIsReachable() {
			ContentOutputSanitizer sanitizer = sanitizer();

			String sanitized = sanitizer.sanitize("the legacy path was /wiki/Special:Random and it is gone");

			assertFalse(sanitized.contains("<a "),
					"autolink runs inside sanitize and only fires on http://, ftp:// and www., so a bare "
							+ "site-relative path is never anything but a text node; widen the autolink scheme gate and the previous "
							+ "assertion would pass because the path became an href -- one autolink creates after "
							+ "the rewrite has already run, so it would point at the dead legacy route -- not "
							+ "because prose was left alone: " + sanitized);
		}

		@Test
		void aLegacySpecialPathInAnAttributeThatIsNotAnHrefIsLeftAlone() {
			String rendered = renderer.render("<span title=\"see /wiki/Special:Random\">tooltip</span>");

			assertTrue(rendered.contains("title=\"see /wiki/Special:Random\""),
					"a title is prose the reader hovers, not a destination; the deleted regex could not tell the "
							+ "two apart because it matched the serialised text: " + rendered);
		}

		@Test
		void aSpecialPrefixThatDoesNotMatchTheMigratorsCasingIsNotARoute() {
			String rendered = renderer.render(
					"[wiki=Special:Random]canonical[/wiki] [wiki=special:Random]lower[/wiki]");

			assertTrue(rendered.contains("href=\"/wiki/special/random\""),
					"the canonical link is what carries this document past the short-circuit and into the rewrite; "
							+ "without it the next assertion would hold for a reason that has nothing to do with "
							+ "how hrefs are matched: " + rendered);
			assertTrue(rendered.contains("href=\"/wiki/special:Random\""),
					"jsoup attribute-value selectors fold case, so a[href^=/wiki/Special:] would silently widen the "
							+ "rule to slugs the migrator never emits; the prefix test belongs in Java: " + rendered);
		}

		@Test
		void aSpecialSlugWhoseNameStartsWithNoLetterStaysAnOrdinaryWikiSlug() {
			String rendered = renderer.render("[wiki=Special:404]digits[/wiki]");

			assertTrue(rendered.contains("href=\"/wiki/Special:404\""),
					"Special is a real migrated namespace, so a page whose name is not a MediaWiki special-page name "
							+ "must keep resolving as the wiki page it is: " + rendered);
		}

		@Test
		void anAbsoluteLegacyWikiUrlIsNotBentIntoAnAppRoute() {
			String rendered = renderer.render("[url=http://zfgc.com/wiki/Special:Random]absolute[/url]");

			assertTrue(rendered.contains("href=\"http://zfgc.com/wiki/Special:Random\""),
					"the deleted regex matched the path anywhere in the serialised document, so it rewrote the "
							+ "path of an off-site absolute url into an app route the dead host never served; a "
							+ "route only exists for an href that is site-relative: " + rendered);
		}

		@Test
		void aTableKeepsTheSameShapeWhetherOrNotTheDocumentAlsoMentionsTheLegacyWiki() {
			String withoutTheLegacyLink = renderer.render("[table]:huh:[/table]");
			String withTheLegacyLink = renderer.render("[table]:huh:[/table][wiki=Special:Random]r[/wiki]");

			assertTrue(withoutTheLegacyLink.contains("<table class=\"bb-code-table\"><span class=\"bb-smiley"),
					"the smiley pass injects spans into the tree after it was parsed, so the sanitiser really does "
							+ "emit inline content directly inside a table that its own parser would never have "
							+ "accepted; that defect predates this stage and is not what this test is about: "
							+ withoutTheLegacyLink);
			assertTrue(withTheLegacyLink.startsWith(withoutTheLegacyLink),
					"adding a legacy link may only append. While the rewrite lived in ContentRenderingService it ran as a "
							+ "second Jsoup.parseBodyFragment over the bytes the sanitiser had just serialised, and "
							+ "that parse foster-parented the smiley back out of the table -- so two posts with "
							+ "identical table markup rendered differently purely because one of them also mentioned "
							+ "a legacy wiki link. Measured at 44 of 40000 fuzz outputs (0.11%), and the frontend "
							+ "parses with html-react-parser, which does not foster-parent, so the difference "
							+ "reached the DOM. Folded into the sanitiser's element loop the rewrite costs no extra "
							+ "parse and repairs nothing by accident:\n  without=" + withoutTheLegacyLink
							+ "\n  with   =" + withTheLegacyLink);
		}

		@Test
		void contentRendererHandsBackTheSanitisersOwnStringWithNothingDoneToIt() {
			String sanitized = new String("<table class=\"bb-code-table\"><span class=\"bb-smiley\">:huh:</span></table>");
			ContentOutputSanitizer sanitizer = mock(ContentOutputSanitizer.class);
			when(sanitizer.sanitize(any())).thenReturn(sanitized);
			Renderer guarded =
					buildRenderer(mock(BBCodeDataProvider.class), grammarHolder(), enricher(), sanitizer,
							messageResolver());
			guarded.useGrammar(seededBBCodeGrammar());

			assertSame(sanitized, guarded.render("an ordinary post"),
					"the sanitiser is the single serialisation chokepoint, and anything ContentRenderingService bolts on "
							+ "top of it has to re-parse the bytes that were just serialised. A re-parse is not "
							+ "free and it is not neutral: it silently repairs shapes the sanitiser genuinely "
							+ "emits, which is how the presence of a legacy Special link came to decide whether a "
							+ "table kept its inline content");
		}

		@Test
		void everyHrefAutolinkCreatesCarriesASchemeSoTheRewriteNeverHasToSeeOne() {
			ContentOutputSanitizer sanitizer = sanitizer();

			String sanitized = sanitizer.sanitize("www.zfgc.com/wiki/Special:Random"
					+ " http://zfgc.com/wiki/Special:Random"
					+ " ftp://zfgc.com/wiki/Special:Random"
					+ " /wiki/Special:Random");

			assertEquals("<a href=\"https://www.zfgc.com/wiki/Special:Random\">www.zfgc.com/wiki/Special:Random</a>"
					+ " <a href=\"http://zfgc.com/wiki/Special:Random\">http://zfgc.com/wiki/Special:Random</a>"
					+ " <a href=\"ftp://zfgc.com/wiki/Special:Random\">ftp://zfgc.com/wiki/Special:Random</a>"
					+ " /wiki/Special:Random", sanitized,
					"autolink runs after the element loop the rewrite now lives in, so an href autolink creates is "
							+ "never offered to the rewrite -- nor to the schemeless-domain repair in "
							+ "stripIfNotAllowed, which is in that same loop. That ordering is only harmless while "
							+ "every autolinked span emits an absolute href: drop the www type's "
							+ "http:// prefix and the sanitiser starts shipping a schemeless href nothing repairs, "
							+ "and widen autolinking to site-relative paths and legacy Special links start reaching "
							+ "the DOM unrewritten");
		}
	}

	@Nested
	class Smilies {

		private ContentOutputSanitizer smileySanitizer() {
			RenderedTextEnricher enricher = enricher();
			ContentOutputSanitizer s = sanitizer(grammarHolder(), enricher);
			enricher.registerSmilies(List.of(
					new SmileyToken(":)", "smiley", "Smiley"),
					new SmileyToken(":D", "cheesy", "Cheesy"),
					new SmileyToken(">:D", "evil", "Evil"),
					new SmileyToken("XD", "grin", "Grin"),
					new SmileyToken("8)", "cool", "Cool")));
			return s;
		}

		@Test
		void rendersSmileyAsSpanKeepingCodeAsText() {
			String result = smileySanitizer().sanitize("hello :D world");
			assertTrue(result.contains("<span class=\"bb-smiley bb-smiley-cheesy\" title=\"Cheesy\">:D</span>"), result);
			assertTrue(result.contains("hello "), result);
			assertTrue(result.contains(" world"), result);
		}

		@Test
		void matchesLongestSmileyFirst() {
			String result = smileySanitizer().sanitize("muahaha >:D");
			assertTrue(result.contains("bb-smiley-evil"), result);
			assertFalse(result.contains("bb-smiley-cheesy"), result);
		}

		@Test
		void skipsSmileyAttachedToWord() {
			String result = smileySanitizer().sanitize("scoreboard:Dx and word:) here");
			assertFalse(result.contains("bb-smiley-cheesy"), result);
			assertFalse(result.contains("bb-smiley-smiley"), result);
		}

		@Test
		void requiresBoundaryForAlphanumericCodes() {
			ContentOutputSanitizer s = smileySanitizer();
			assertFalse(s.sanitize("fileXD.zip").contains("bb-smiley"), "XD inside a word must not match");
			assertFalse(s.sanitize("call 18) now").contains("bb-smiley"), "8) after a digit must not match");
			assertTrue(s.sanitize("haha XD good").contains("bb-smiley-grin"));
		}

		@Test
		void skipsSmiliesInsideCodeBlocksAndLinks() {
			ContentOutputSanitizer s = smileySanitizer();
			assertFalse(s.sanitize("<pre>keep :D literal</pre>").contains("bb-smiley"), "pre must stay literal");
			String linked = s.sanitize("<a href=\"http://example.com/page\">text :D inside</a>");
			assertFalse(linked.contains("bb-smiley"), linked);
		}

		@Test
		void smiliesComposeWithAutolink() {
			String result = smileySanitizer().sanitize("look http://example.com/cool :)");
			assertTrue(result.contains("<a href=\"http://example.com/cool\""), result);
			assertTrue(result.contains("bb-smiley-smiley"), result);
		}

		@Test
		void skipsSmiliesInsideTheTeletypeWrapperTheTtBBCodeActuallyEmits() {
			assertFalse(smileySanitizer().sanitize("<span class=\"bb-code-tt\">keep :D literal</span>")
					.contains("bb-smiley"),
					"teletype content is meant to read as typed, and [tt] emits a class, not a tt element");
		}

		@Test
		void doesNotSmilifyItsOwnSmileyWrapperASecondTime() {
			ContentOutputSanitizer sanitizer = smileySanitizer();
			String once = sanitizer.sanitize("hello :D");
			String twice = sanitizer.sanitize(once);

			assertEquals(once, twice,
					"the smiley wrapper is not an element name, so a skip set keyed only on tag names lets "
							+ "collectTextNodes re-visit the sanitizer's own output and nest the wrapper one level "
							+ "deeper on every pass, unbounded: " + twice);
			assertEquals(1, twice.split("bb-smiley-cheesy", -1).length - 1,
					"one authored smiley must stay exactly one rendered smiley: " + twice);
		}

		static final List<SmileyToken> NAMES_AND_LABELS_A_SEED_ROW_COULD_CARRY = List.of(
				new SmileyToken(":evil:", "x\" onmouseover=\"alert(1)", "lab"),
				new SmileyToken(":spaced:", "Cheesy Face", "Cheesy Face"),
				new SmileyToken(":quoted:", "ok", "a \" b & c"));

		private RenderedTextEnricher enricherCarryingHostileSmileyRows() {
			RenderedTextEnricher enricher = enricher();
			enricher.registerSmilies(NAMES_AND_LABELS_A_SEED_ROW_COULD_CARRY);
			return enricher;
		}

		private ContentOutputSanitizer sanitizerCarryingHostileSmileyRows() {
			return sanitizer(grammarHolder(), enricherCarryingHostileSmileyRows());
		}

		@Test
		void aSmileyNameCannotOpenAnAttributeOnTheWrapperTheSanitizerWritesAfterTheCleaner() {
			String sanitized = sanitizerCarryingHostileSmileyRows().sanitize("hello :evil: world");

			assertFalse(sanitized.toLowerCase(Locale.ROOT).contains("onmouseover"),
					"the smiley pass runs after the Cleaner and after the class allowlist, so a name spliced "
							+ "unescaped into class=\"...\" closes the attribute and adds a live event handler no "
							+ "later stage ever inspects: " + sanitized);
		}

		@Test
		void everyClassTheSmileyWrapperCarriesIsOneTheClassAllowlistWouldHaveKept() {
			ContentOutputSanitizer hostile = sanitizerCarryingHostileSmileyRows();

			for (SmileyToken registered : NAMES_AND_LABELS_A_SEED_ROW_COULD_CARRY) {
				String sanitized = hostile.sanitize("hello " + registered.code() + " world");
				for (Element wrapper : Jsoup.parseBodyFragment(sanitized).select("span"))
					for (String className : wrapper.classNames())
						assertTrue(className.matches("bb-[a-z0-9-]+"),
								"the wrapper builds its own class token out of a database field and splices it in "
										+ "after stripEveryClassTheRendererCannotEmit has already run, so the token "
										+ "has to be held to that same allowlist where it is built: " + sanitized);
			}
		}

		static final String THE_SOURCE_EVERY_ENRICHMENT_PASS_HAS_TO_FIRE_ON =
				"hello :evil: :spaced: :quoted: and http://zfgc.com/a now";

		@Test
		void nothingThePassesAfterTheCleanerInsertCanOutliveASecondClean() {
			ContentOutputSanitizer hostile = sanitizerCarryingHostileSmileyRows();
			String once = hostile.sanitize(THE_SOURCE_EVERY_ENRICHMENT_PASS_HAS_TO_FIRE_ON);

			assertEquals(once, hostile.sanitize(once),
					"the smiley and autolink passes run after the Cleaner, so whatever they splice in is the one "
							+ "markup in the output that no allowlist ever saw. A second clean removing anything is "
							+ "the general seam rather than one bad field: " + once);
		}

		static final String THE_SEAM_IS_ENUMERATED_SO_A_NEW_PASS_CANNOT_ARRIVE_UNMEASURED =
				"ContentOutputSanitizer.sanitize applies the enrichment passes to the body the Cleaner and the "
						+ "per-element rules have already finished with, so every byte a pass splices in is markup "
						+ "no allowlist has ever inspected -- which is exactly how an unescaped smiley name once "
						+ "reached the reader as a live onmouseover handler. A pass only reaches the body through "
						+ "RenderedTextEnricher.Pass, and this test walks Pass.values(), so a pass added to the "
						+ "seam is covered here the moment it exists rather than whenever someone remembers.";

		@Test
		void everyEnrichmentPassLeavesOnlyMarkupASecondCleanKeepsWordForWord() {
			ContentOutputSanitizer hostile = sanitizerCarryingHostileSmileyRows();
			RenderedTextEnricher enricher = enricherCarryingHostileSmileyRows();

			for (RenderedTextEnricher.Pass pass : RenderedTextEnricher.Pass.values()) {
				Element body =
						hostile.theCleanedBodyOf(THE_SOURCE_EVERY_ENRICHMENT_PASS_HAS_TO_FIRE_ON);
				String beforeThePass = body.html();
				pass.applyTo(enricher, body);
				String afterThePass = body.html();

				assertNotEquals(beforeThePass, afterThePass,
						pass + " changed nothing on THE_SOURCE_EVERY_ENRICHMENT_PASS_HAS_TO_FIRE_ON, so the "
								+ "assertion below certifies an empty pass. Extend that source until this pass "
								+ "fires on it. " + THE_SEAM_IS_ENUMERATED_SO_A_NEW_PASS_CANNOT_ARRIVE_UNMEASURED);
				assertEquals(afterThePass, hostile.theCleanedBodyOf(afterThePass).html(),
						pass + " spliced markup a second clean does not give back word for word, which means it "
								+ "wrote something the safety gate would have refused had the gate run last: "
								+ afterThePass + "\n  "
								+ THE_SEAM_IS_ENUMERATED_SO_A_NEW_PASS_CANNOT_ARRIVE_UNMEASURED);
			}
		}

		static final String MASK_OPEN = "\uE002";

		static final String MASK_CLOSE = "\uE003";

		private Renderer rendererWith(List<SmileyToken> registered) {
			BBCodeGrammarHolder grammarHolder = grammarHolder();
			RenderedTextEnricher enricher = enricher();
			ContentOutputSanitizer sanitizer = sanitizer(grammarHolder, enricher);
			enricher.registerSmilies(registered);
			Renderer built = buildRenderer(mock(BBCodeDataProvider.class), grammarHolder, enricher, sanitizer,
					messageResolver());
			built.useGrammar(seededBBCodeGrammar());
			return built;
		}

		private Renderer seededRenderer() {
			return rendererWith(seededSmilies());
		}

		@Test
		void theCodesCommonmarkReadsAsBlockMarkersAreDerivedFromTheSeededSmilies() {
			RenderedTextEnricher enricher = enricher();
			enricher.registerSmilies(seededSmilies());

			assertEquals(List.of(">:(", ">:D", ">:)", "8)"),
					enricher.smileyCodesCommonmarkReadsAsBlockMarkers(),
					"R__04_lookups.sql is the only place the colliding set may come from, and it is ordered "
							+ "longest-first so a longer code always wins the match; a list spelled out in Java "
							+ "stops tracking the seed the moment a smiley is added: " + seededSmilies());
		}

		@Test
		void aCodeIsJudgedCollidingByItsOwnShapeNotByBeingOnAKnownList() {
			for (String colliding : List.of(">:(", ">:D", ">:)", ">", ">x", "8)", "9.", "12.", "007)"))
				assertTrue(RenderedTextEnricher.readsAsAMarkdownBlockMarker(colliding),
						colliding + " opens a commonmark block marker, so commonmark eats it before the smiley "
								+ "pass ever runs");
			for (String safe : List.of(":)", ":D", "XD", ":(", ":o", ":huh:", "::)", ":P", ":-[", ":-X", ":-\\",
					":-*", ":'(", "8", "8x", "a)", ".8", "x>:("))
				assertFalse(RenderedTextEnricher.readsAsAMarkdownBlockMarker(safe),
						safe + " is ordinary inline text to commonmark, so masking it would move bytes for no "
								+ "reason");
		}

		@Test
		void everySeededSmileyThatOpensALineSurvivesTheMarkdownBlockParser() {
			Renderer renderer = seededRenderer();

			assertEquals("<p><span class=\"bb-smiley bb-smiley-cool\" title=\"Cool\">8)</span> that one</p>\n",
					renderer.renderMarkdown("8) that one"),
					"commonmark reads 8) as an ordered list marker starting at eight, so without the mask the "
							+ "smiley is gone before the smiley pass runs");
			assertEquals("<p><span class=\"bb-smiley bb-smiley-angry\" title=\"Grrr\">&gt;:(</span> grumpy</p>\n",
					renderer.renderMarkdown(">:( grumpy"));
			assertEquals("<p><span class=\"bb-smiley bb-smiley-evil\" title=\"Evil\">&gt;:D</span> evil</p>\n",
					renderer.renderMarkdown(">:D evil"));
			assertEquals("<p><span class=\"bb-smiley bb-smiley-evil\" title=\"Evil\">&gt;:)</span> evil</p>\n",
					renderer.renderMarkdown(">:) evil"));
		}

		@Test
		void aLineStartSmileyRendersTheSameBytesAsTheSameSmileyMidLine() {
			Renderer renderer = seededRenderer();

			for (String code : List.of("8)", ">:(", ">:D", ">:)")) {
				String openingTheLine = renderer.renderMarkdown(code + " tail");
				String midLine = renderer.renderMarkdown("head " + code + " tail");

				assertEquals(openingTheLine.replace("<p>", "<p>head "), midLine,
						code + " must render one smiley wherever it sits; a mask that restores something other "
								+ "than the authored code makes the two positions disagree");
			}
		}

		@Test
		void aSmileyThatOnlyExistsInTheRegistryIsMaskedToo() {
			List<SmileyToken> registered = new ArrayList<>(seededSmilies());
			registered.add(new SmileyToken("42.", "answer", "The Answer"));

			assertEquals("<p><span class=\"bb-smiley bb-smiley-answer\" title=\"The Answer\">42.</span> of them</p>\n",
					rendererWith(registered).renderMarkdown("42. of them"),
					"no seeded smiley uses the digits-then-dot shape, so this is the only thing that can tell a "
							+ "derived colliding set apart from four strings typed into the renderer");
		}

		@Test
		void aRestoredCodeIsEscapedBeforeItIsSplicedBackIntoTheHtml() {
			List<SmileyToken> registered = new ArrayList<>(seededSmilies());
			registered.add(new SmileyToken(">:<b>", "hostile", "Hostile"));

			assertEquals(
					"<p><span class=\"bb-smiley bb-smiley-hostile\" title=\"Hostile\">&gt;:&lt;b&gt;</span> hi</p>\n",
					rendererWith(registered).renderMarkdown(">:<b> hi"),
					"a smiley code is a row in the smiley table, not a literal in this class; restore splices it "
							+ "back into a rendered HTML string, so splicing it raw lets that row open an element "
							+ "commonmark never wrote");
		}

		@Test
		void aRealOrderedListAndARealBlockQuoteStillParse() {
			Renderer renderer = seededRenderer();

			assertEquals("<ol start=\"8\">\n<li>first</li>\n<li>second</li>\n</ol>\n",
					renderer.renderMarkdown("8. first\n9. second"),
					"the smiley wins at line start, so an author who wants a list numbered from eight writes 8.");
			assertEquals("<blockquote>\n<p>quoted\nmore</p>\n</blockquote>\n",
					renderer.renderMarkdown("> quoted\n> more"));
		}

		@Test
		void aSmileyBehindTheThreeSpacesCommonmarkStillReadsAsABlockMarkerIsMaskedToo() {
			Renderer renderer = seededRenderer();

			assertEquals("<p><span class=\"bb-smiley bb-smiley-cool\" title=\"Cool\">8)</span> in</p>\n",
					renderer.renderMarkdown("   8) in"),
					"commonmark looks for a block marker at the first non-space column, not at column zero, so a "
							+ "mask anchored to column zero leaves three spaces of indent as a bypass");
			assertEquals("<pre><code>8) in\n</code></pre>\n", renderer.renderMarkdown("    8) in"),
					"four spaces is an indented code block, where the code was never a marker; the mask must "
							+ "round-trip to the authored bytes there");
		}

		@Test
		void anAuthorCannotForgeTheMaskMarker() {
			Renderer renderer = seededRenderer();
			String forged = MASK_OPEN + "0" + MASK_CLOSE + " forged\n"
					+ MASK_OPEN + "99" + MASK_CLOSE + " out of range";

			String rendered = assertDoesNotThrow(() -> renderer.renderMarkdown(forged),
					"an author-typed index outside the colliding set would blow up the restore pass if the mask "
							+ "characters were not stripped from the source first");

			assertEquals("<p>0 forged\n99 out of range</p>\n", rendered,
					"the mask characters are stripped from the source before masking, so the only markers left "
							+ "for the restore pass are the ones this renderer wrote: " + rendered);
			assertFalse(rendered.contains(MASK_OPEN) || rendered.contains(MASK_CLOSE),
					"a mask character reaching the browser is the defect this strip exists to prevent");
			assertFalse(rendered.contains("bb-smiley"),
					"a forged marker must not be redeemed for a smiley the author never typed");
		}

		@Test
		void aForgedMarkerCannotDisplaceARealOneOnTheSameLine() {
			Renderer renderer = seededRenderer();

			assertEquals("<p>0 <span class=\"bb-smiley bb-smiley-cool\" title=\"Cool\">8)</span> real</p>\n",
					renderer.renderMarkdown(MASK_OPEN + "0" + MASK_CLOSE + " 8) real"),
					"stripping runs before masking, so a forged marker cannot shift the indices this renderer "
							+ "hands to its own restore pass");
		}

		@Test
		void theMaskIsScopedToTheMarkdownLane() {
			Renderer renderer = seededRenderer();
			String authored = MASK_OPEN + "0" + MASK_CLOSE + " forged";

			assertEquals(authored, renderer.render(authored),
					"the bbcode lane never masks, so it must not gain the markdown lane's strip either; moving "
							+ "the mask into the shared sanitize chokepoint would move bbcode bytes");
			assertEquals("<span class=\"bb-smiley bb-smiley-cool\" title=\"Cool\">8)</span> that one",
					renderer.render("8) that one"),
					"the bbcode lane has no block parser to defend against, so this stage must leave it alone");
		}

		@Test
		void aVerbatimBlockKeepsItsAuthoredBytesThroughTheMaskAndBack() {
			Renderer renderer = seededRenderer();

			assertEquals("<pre><code>8) fenced\n&gt;:( fenced\n</code></pre>\n",
					renderer.renderMarkdown("```\n8) fenced\n>:( fenced\n```"),
					"a fence is verbatim, so the mask must round-trip to the authored bytes rather than to a "
							+ "smiley span");
			assertEquals("<pre class=\"bb-code-code\">8) verbatim\n&gt;:( verbatim</pre>\n",
					renderer.renderMarkdown("[code]\n8) verbatim\n>:( verbatim\n[/code]"));
		}
	}

	@Nested
	class QuoteLookup {

		private static final OffsetDateTime ORIGINAL_TS = OffsetDateTime.of(2020, 1, 1, 0, 0, 0, 0, ZoneOffset.UTC);
		private static final OffsetDateTime EDITED_TS = OffsetDateTime.of(2021, 1, 1, 0, 0, 0, 0, ZoneOffset.UTC);

		private QuotedMessageDataProvider messageDataProvider;
		private QuotedMessageSource lookup;

		@BeforeEach
		void setup() {
			messageDataProvider = mock(QuotedMessageDataProvider.class);
			lookup = new QuotedMessageSource(messageDataProvider);
			when(messageDataProvider.getQuotableSources(any(), any()))
					.thenAnswer(invocation -> Map.of(5, quotableMessageFive(7, "Alice", null)));
		}

		private static QuotedMessageDataProvider.QuotedSource quotableMessageFive(Integer ownerId, String displayName,
				String guestAuthorName) {
			NavigableMap<OffsetDateTime, QuotedMessageDataProvider.QuotedRevision> revisions = new TreeMap<>();
			revisions.put(ORIGINAL_TS, new QuotedMessageDataProvider.QuotedRevision("original", "BBCODE"));
			revisions.put(EDITED_TS, new QuotedMessageDataProvider.QuotedRevision("edited", "BBCODE"));
			return new QuotedMessageDataProvider.QuotedSource(5,
					displayName == null ? guestAuthorName : displayName, ownerId,
					ORIGINAL_TS, 42, 1, revisions);
		}

		@Test
		void aReadableSourceCarriesItsRevisionsAndTheirDeclaredFormat() {
			ContentTagResolver.Resolved entry = lookup.resolve(Set.of(5), Set.of(3)).get(5);

			assertTrue(entry.permitted());
			assertEquals("edited", entry.revisionsByCreatedTs().floorEntry(EDITED_TS).getValue().body());
			assertEquals("original", entry.revisionsByCreatedTs().floorEntry(ORIGINAL_TS).getValue().body());
			assertEquals(ContentFormat.BBCODE,
					entry.revisionsByCreatedTs().floorEntry(EDITED_TS).getValue().contentFormat());
		}

		@Test
		void aSourceTheProviderWithheldIsSimplyAbsent() {
			when(messageDataProvider.getQuotableSources(any(), any())).thenReturn(Map.of());

			assertTrue(lookup.resolve(Set.of(5), Set.of(99)).isEmpty(),
					"board visibility is filtered in the provider query now, so an unreadable source never "
							+ "reaches the renderer at all; every downstream use of Resolved is already gated "
							+ "behind permitted, so absent and not-permitted render identically");
		}

		@Test
		void ownerlessMessageResolvesTheStoredGuestAuthorName() {
			when(messageDataProvider.getQuotableSources(any(), any()))
					.thenAnswer(invocation -> Map.of(5, quotableMessageFive(null, null, "Simple Machines")));

			ContentTagResolver.Resolved entry = lookup.resolve(Set.of(5), Set.of(3)).get(5);

			assertEquals("Simple Machines", entry.authorDisplayName(),
					"a migrated guest post must resolve the SMF poster name it was backfilled with");
			assertNull(entry.authorUserId(),
					"a guest author must not be linked to a member profile");
		}
	}

	@Nested
	class Markdown {

		private final Renderer renderer =
				buildRenderer(mock(BBCodeDataProvider.class), messageResolver());

		@Test
		void rendersBasicMarkdown() {
			String html = renderer.renderMarkdown("# Title\n\nSome **bold** text and a [link](https://zfgc.com).");
			assertTrue(html.contains("<h1>Title</h1>"), html);
			assertTrue(html.contains("<strong>bold</strong>"), html);
			assertTrue(html.contains("href=\"https://zfgc.com\""), html);
		}

		@Test
		void sanitizesDangerousHtml() {
			String html = renderer.renderMarkdown("Hello\n\n<script>alert(1)</script>");
			assertFalse(html.contains("<script"), html);
			assertTrue(html.contains("Hello"), html);
		}

		@Test
		void blankInputYieldsEmpty() {
			assertTrue(renderer.renderMarkdown("   ").isEmpty());
			assertTrue(renderer.renderMarkdown(null).isEmpty());
		}

		@Test
		void theMarkdownRendererItselfEmitsUnsanitizedHtmlSoOnlyTheChokepointCleansIt() {
			assertTrue(renderer.markdownRenderer().render("Hello\n\n<script>alert(1)</script>", null,
					ContentScope.FORUM, Map.of()).contains("<script"),
					"MarkdownRenderer must hand raw commonmark output to ContentRenderingService; if it sanitizes on its "
							+ "own the engine has two sanitize points again and the chokepoint stops being one");
		}
	}

	@Nested
	class MarkdownWithBBCode {

		private final Renderer renderer =
				buildRenderer(mock(BBCodeDataProvider.class), messageResolver());

		@BeforeEach
		void loadTheRealSeededGrammar() {
			renderer.useGrammar(seededBBCodeGrammar());
		}

		@Test
		void aBlockLevelCodeAloneOnItsLineOpensARealBlockSoItsBodyIsParsedAsMarkdown() {
			String html = renderer.renderMarkdown("[quote]\n# heading\n\n- one\n- two\n\n**bold**\n[/quote]");

			assertEquals("<div class=\"bb-code-quote\"><div class=\"bb-code-quote-body\">\n<h1>heading</h1>\n"
					+ "<ul>\n<li>one</li>\n<li>two</li>\n</ul>\n<p><strong>bold</strong></p>\n</div></div>\n", html,
					"a block parser factory is the only place commonmark hands over a container, which is what "
							+ "lets the quote body be a markdown document instead of a flat string");
		}

		@Test
		void aBlockLevelCodeInheritsBlockQuotePrefixesAndListContextFromCommonmark() {
			assertTrue(renderer.renderMarkdown("> [quote]\n> body\n> [/quote]")
					.contains("<blockquote>\n<div class=\"bb-code-quote\">"),
					"the '> ' prefix on every line is stripped by commonmark before the bbcode block sees it; a "
							+ "hand-rolled line planner has to re-derive that and gets it wrong");
			assertTrue(renderer.renderMarkdown("- [quote]body[/quote]").contains("<li>"),
					"a list item is a container, so the expansion belongs inside it");
		}

		@Test
		void anInlineCodeExpandsInsideAMarkdownParagraphAlongsideMarkdownInlines() {
			assertEquals("<p>some <span class=\"bb-code-b\">bold</span> and <strong>markdown</strong></p>\n",
					renderer.renderMarkdown("some [b]bold[/b] and **markdown**"));
		}

		@Test
		void theMarkdownLaneEscapesItsOwnTextWhileTheBBCodeLaneHandsItToTheSanitizer() {
			assertEquals("<p>a &lt; b <span class=\"bb-code-b\">x</span></p>\n",
					renderer.renderMarkdown("a < b [b]x[/b]"),
					"the inline expansion hands plain text back to commonmark as Text nodes, so commonmark's own "
							+ "escaping still owns it; rendering the run to HTML and splicing it would lose that");
		}

		@Test
		void theLineBreakPrePassIsScopedToTheBBCodeLaneAndNeverRunsOnMarkdown() {
			String source = "[b]line one\nline two[/b]";

			assertEquals("<p><span class=\"bb-code-b\">line one\nline two</span></p>\n",
					renderer.renderMarkdown(source),
					"commonmark owns line breaks in markdown: a soft break is a newline, not a <br/>");
			assertEquals("<span class=\"bb-code-b\">line one<br>line two</span>", renderer.render(source),
					"the same source in the bbcode lane must still get the pre-pass, or the rule is not scoped, "
							+ "it is deleted");
		}

		@Test
		void aBlockLevelCodeInsideMarkdownEmphasisIsLeftAsAuthored() {
			String source = "A **bold with [quote]x[/quote] inside** word.";

			assertEquals("<p>A <strong>bold with [quote]x[/quote] inside</strong> word.</p>\n",
					renderer.renderMarkdown(source),
					"a run whose markdown parent is an inline node cannot hold a <div>; because the bbcode tree is "
							+ "not fused into the commonmark one this is silent invalid nesting rather than the "
							+ "ClassCastException a fused tree would raise, so nothing but this assertion catches it");
		}

		@Test
		void aBlockLevelCodeInsideAMarkdownHeadingIsLeftAsAuthored() {
			String source = "# Heading with [quote]x[/quote] here";

			assertEquals("<h1>Heading with [quote]x[/quote] here</h1>\n", renderer.renderMarkdown(source),
					"a Heading IS a Block, so a guard that stops at the first Block ancestor happily puts a <div> "
							+ "inside an <h1>; the raw-tag leak detector calls that clean because no bracket "
							+ "survives");
		}

		@Test
		void aParagraphIsNeverSplitAroundARunThatCouldNotBeExpanded() {
			String source = "[table][tr][td]**md** and [quote]inner[/quote][/td][/tr][/table]";

			assertEquals("<p>[table][tr][td]<strong>md</strong> and [quote]inner[/quote][/td][/tr][/table]</p>\n",
					renderer.renderMarkdown(source),
					"the markdown inline split this paragraph into two runs; expanding and lifting the second "
							+ "while the first stays literal splits the paragraph around raw bbcode, which is new "
							+ "and worse than leaving the whole thing as authored");
			assertEquals("<table class=\"bb-code-table\"><tbody><tr><td>plain and "
					+ "<div class=\"bb-code-quote\"><div class=\"bb-code-quote-body\">inner</div></div>"
					+ "</td></tr></tbody></table>\n",
					renderer.renderMarkdown("[table][tr][td]plain and [quote]inner[/quote][/td][/tr][/table]"),
					"the same paragraph with no markdown inline in it is one run, so it expands whole");
		}

		@Test
		void aBlockLevelCodeNeverInterruptsAnOpenMarkdownParagraph() {
			String source = "A **bold with\n[quote]\nx\n[/quote]\ninside** word.";

			assertEquals("<p>A <strong>bold with\n[quote]\nx\n[/quote]\ninside</strong> word.</p>\n",
					renderer.renderMarkdown(source),
					"commonmark resolves emphasis only once a paragraph closes, so a block start that interrupts "
							+ "one destroys the emphasis around it and leaks the '**' as text");
		}

		@Test
		void theCustomBlockNodeIsRegisteredWithTheRendererSoItsBodyCannotVanish() {
			String html = renderer.renderMarkdown("[quote]\nbody\n[/quote]");

			assertTrue(html.contains("body"),
					"NodeRendererMap is an exact-class lookup that returns null and renders nothing for a node "
							+ "type nobody registered, so an unregistered custom block deletes its whole subtree "
							+ "with no error anywhere: " + html);
			assertTrue(html.contains("bb-code-quote"), html);
		}

		@Test
		void aMarkdownCodeSpanAndFenceStillProtectBBCodeFromExpansion() {
			assertEquals("<p><code>[b]literal[/b]</code></p>\n", renderer.renderMarkdown("`[b]literal[/b]`"));
			assertEquals("<pre><code>[quote]\nx\n[/quote]\n</code></pre>\n",
					renderer.renderMarkdown("```\n[quote]\nx\n[/quote]\n```"),
					"a closer line inside a fence must not close a bbcode block that is not even open");
		}

		@Test
		void aVerbatimCodeKeepsItsBodyLiteralInsideMarkdown() {
			assertEquals("<pre class=\"bb-code-code\">**not bold** and [b]x[/b]</pre>\n",
					renderer.renderMarkdown("[code]\n**not bold** and [b]x[/b]\n[/code]"),
					"a code whose process-content flag is false is not a container, so commonmark must not parse "
							+ "its body at all");
		}

		@Test
		void aCodeStillAwaitingItsAuthorsCloserIsLeftAsAuthored() {
			assertEquals("<p>[b]unclosed bold and more text</p>\n",
					renderer.renderMarkdown("[b]unclosed bold and more text"),
					"the bbcode lane closes a dangling tag at end of input; doing that to a markdown paragraph "
							+ "restyles text the author never opened a tag around");
			assertEquals("<p>orphan [/b] close</p>\n", renderer.renderMarkdown("orphan [/b] close"));
			assertEquals("<p>[b]a[i]b[/b]c[/i]</p>\n", renderer.renderMarkdown("[b]a[i]b[/b]c[/i]"),
					"crossed tags close each other on behalf, which is not the author's closer");
		}

		@Test
		void aListItemMarkerIsNoLongerEatenByMarkdownEmphasisAndBothLanesEmitTheSameItems() {
			String singleLine = "[list][*]one[*]two[/list]";

			assertEquals("<ul><li>one</li><li>two</li></ul>\n", renderer.renderMarkdown(singleLine),
					"markdown used to pair the two asterisks into emphasis before any bbcode parser ran, so this "
							+ "input rendered [<em>]one[</em>]two and carried no list item at all");
			assertEquals("<ul><li>one</li><li>two</li></ul>", renderer.render(singleLine),
					"the marker is expanded by the same function in both lanes, so a markdown lane that grew its "
							+ "own rule would drift from the bbcode lane one input at a time");
			assertEquals("<ul>\n<li>one</li><li>two</li></ul>\n",
					renderer.renderMarkdown("[list]\n[*]one\n[*]two\n[/list]"),
					"commonmark owns the newline after the opener, so the markdown lane keeps it as a newline "
							+ "where the bbcode lane's pre-pass turns it into a <br>");
			assertEquals("<ul><li>one</li></ul>\n", renderer.renderMarkdown("[list][*]one[/list]"),
					"a lone asterisk never formed emphasis, so this input already reached the bbcode parser and "
							+ "was refused by the deferral this rule replaces");
		}

		@Test
		void aShadowKeepsItsColorWhenTheAuthorAlsoNamesADirection() {
			assertTrue(renderer.render("[shadow=red,left]Red Shadow[/shadow]").contains("red"),
					"SMF writes [shadow=colour,direction]; a grammar that declares only the colour hands the "
							+ "whole 'red,left' string to the colour sanitiser, which rejects it and leaves "
							+ "text-shadow with no colour at all -- CSS then paints the shadow in currentColor, "
							+ "so the reader sees a white shadow instead of a red one");
			assertTrue(renderer.render("[shadow=red]Red Shadow[/shadow]").contains("red"),
					"the direction is optional, so naming only the colour must keep working");
		}

		@Test
		void aListNumberedThroughTheNamelessAttributeStillNumbersItsItems() {
			assertEquals("<ul class=\"bb-list-decimal\"><li>one</li><li>two</li></ul>",
					renderer.render("[list=1][li]one[/li][li]two[/li][/list]"),
					"the wiki migrator emits [list=1] for every ordered list it converts, so a grammar with no "
							+ "nameless mode silently drops the numbering of migrated content");
			assertEquals("<ul class=\"bb-list-lower-roman\"><li>one</li></ul>",
					renderer.render("[list=lower-roman][li]one[/li][/list]"),
					"a named style the grammar knows must survive the nameless form too");
			assertEquals("<ul class=\"bb-list-disc\"><li>one</li></ul>",
					renderer.render("[list=disc][li]one[/li][/list]"),
					"an unnumbered style must stay unnumbered rather than falling through to decimal");
			assertEquals("<ul><li>one</li></ul>", renderer.render("[list][li]one[/li][/list]"),
					"a list with no attribute at all keeps the plain bullet mode it has always had");
		}

		@Test
		void onlyTheAsteriskInsideTheListItemMarkerStopsBeingAMarkdownDelimiter() {
			assertEquals("<p>a <em>b</em> c and <strong>d</strong> e</p>\n",
					renderer.renderMarkdown("a *b* c and **d** e"),
					"claiming every asterisk would delete emphasis from the whole markdown lane");
			assertEquals("<p><em>emph with [*] inside</em></p>\n",
					renderer.renderMarkdown("*emph with [*] inside*"),
					"the marker's asterisk used to pair with the emphasis opener around it and split the word; "
							+ "taking it out of the delimiter run is what lets the author's emphasis close");
			assertEquals("<p>[*x] and <em>y</em></p>\n", renderer.renderMarkdown("[*x] and *y*"),
					"an asterisk after '[' that no ']' immediately closes is not a marker, so it stays an "
							+ "ordinary delimiter and pairs with the next one");
			assertEquals("<p>[<em>a</em>]</p>\n", renderer.renderMarkdown("[*a*]"),
					"an asterisk before ']' is only a marker when '[' is what sits behind it; keying on the ']' "
							+ "alone claims the closing delimiter of bracketed emphasis and deletes the emphasis");
		}

		@Test
		void aMarkdownVerbatimConstructStillShieldsTheListItemMarkerFromTheRewrite() {
			assertEquals("<pre><code>[list][*]x[/list]\n</code></pre>\n",
					renderer.renderMarkdown("```\n[list][*]x[/list]\n```"));
			assertEquals("<p><code>[list][*]x[/list]</code></p>\n",
					renderer.renderMarkdown("`[list][*]x[/list]`"));
			assertEquals("<pre class=\"bb-code-code\">[list][*]x[/list]</pre>\n",
					renderer.renderMarkdown("[code]\n[list][*]x[/list]\n[/code]"),
					"the bbcode lane rewrites the whole source before it parses, so it corrupts this pasted "
							+ "sample into [li]; rewriting one text run at a time is what keeps markdown's own "
							+ "verbatim constructs, and this lane's verbatim blocks, literal");
		}

		@Test
		void aListNestedDeeperThanOneContainerNowExpandsEveryMarkerInBothLanes() {
			assertEquals("<ul>\n<li>a\n<ul>\n<li>b</li></ul></li></ul>\n",
					renderer.renderMarkdown("[list]\n[*]a\n[list]\n[*]b\n[/list]\n[/list]"),
					"the marker opens its item inside the one parse now, so the container the outer marker "
							+ "belongs to is the one on the open-tag stack; the pre-pass could only ever reach the "
							+ "innermost [list] body and left the outer marker as text");
			assertEquals("<ul><li>a</li></ul>\n<p> tail</p>\n",
					renderer.renderMarkdown("[list][*]a[/list] tail"),
					"the closer is read by the bbcode parser inside the run, not by a line-oriented block "
							+ "parser, so it closes wherever the author put it");
		}

		@Test
		void aBlockClosesWhereItsAuthorWroteTheCloserEvenWithTextAfterItOnTheSameLine() {
			assertEquals("<div class=\"bb-code-quote\"><div class=\"bb-code-quote-body\">\n<p>body </p>\n"
					+ "</div></div>\n<p> tail</p>\n",
					renderer.renderMarkdown("[quote]\nbody [/quote] tail"),
					"BlockContinue.finished() drops the rest of the line, so a block parser that closes on the "
							+ "closer alone eats the author's tail; the closer is read out of the text run instead, "
							+ "which is where the bbcode parser can see it wherever the author put it");
			assertEquals("<div class=\"bb-code-quote\"><div class=\"bb-code-quote-body\">\n<p>body\n</p>\n"
					+ "</div></div>\n<p> tail</p>\n",
					renderer.renderMarkdown("[quote]\nbody\n[/quote] tail"),
					"a closer that opens its line but does not own it is the same shape");
			assertEquals("<div class=\"bb-code-align bb-align-center\">\n<p>centered </p>\n</div>\n<p> tail</p>\n",
					renderer.renderMarkdown("[align=center]\ncentered [/align] tail"),
					"nothing about this is specific to quote; every block-hosting code closes where its author "
							+ "wrote the closer");
			assertEquals("<div class=\"bb-code-quote\"><div class=\"bb-code-quote-body\">\n<p>body </p>\n"
					+ "</div></div>\n<p> <strong>tail</strong></p>\n",
					renderer.renderMarkdown("[quote]\nbody [/quote] **tail**"),
					"the tail keeps the inline parse commonmark already gave it, so markdown after the closer is "
							+ "still markdown");
		}

		@Test
		void aBlockThatClosesMidLineDeletesNoCharacterTheAuthorTyped() {
			for (String source : List.of("[quote]\nbody [/quote] tail", "[quote]\nbody\n[/quote] tail",
					"lead [quote]\nbody [/quote] tail", "[quote]\nouter\n[quote]\ninner [/quote] mid\n[/quote]",
					"[align=center]\ncentered [/align] tail")) {
				String visible = org.jsoup.Jsoup.parse(renderer.renderMarkdown(source)).wholeText();
				for (String authored : source.replace("[quote]", " ").replace("[/quote]", " ")
						.replace("[align=center]", " ").replace("[/align]", " ").split("\\s+"))
					if (!authored.isEmpty())
						assertTrue(visible.contains(authored),
								"the rejected fix closed the block by eating the rest of the line, which deleted "
										+ "the author's text; '" + authored + "' must survive " + source + ": "
										+ visible);
			}
		}

		@Test
		void aNestedBlockAndItsParentEachCloseOnTheirOwnMidLineCloser() {
			assertEquals("<div class=\"bb-code-quote\"><div class=\"bb-code-quote-body\">\n<p>outer\n</p>\n"
					+ "<div class=\"bb-code-quote\"><div class=\"bb-code-quote-body\">\ninner </div></div>\n"
					+ "<p> mid\n</p>\n</div></div>\n",
					renderer.renderMarkdown("[quote]\nouter\n[quote]\ninner [/quote] mid\n[/quote]"),
					"the outer block's own closer is the one the run leaves unmatched; matching the first closer "
							+ "in the run would steal the inner quote's closer and unbalance the tree");
		}

		@Test
		void anInlineCodeTheAuthorLeftOpenDoesNotStopTheBlockClosingAtItsCloser() {
			for (String paragraph : List.of("[/align]", "text [/align] tail", "[b]bold[/b] [/align] tail",
					"[b]bold [/align] tail")) {
				String html = renderer.renderMarkdown("[align=center]\n" + paragraph + "\n\noutside the block");

				assertFalse(Jsoup.parseBodyFragment(html).selectFirst("div.bb-code-align").wholeText()
								.contains("outside the block"),
						"a closer is recorded as unmatched only when the whole tag stack is empty, so one inline "
								+ "code the author never closed swallows the rest of the document into the block: '"
								+ paragraph + "' -> " + html);
			}
		}

		@Test
		void anAttributedQuoteHostsMarkdownBlocksExactlyAsAnUnattributedOneDoes() {
			String unattributed = renderer.renderMarkdown("[quote]\n# heading\n\n- one\n[/quote]");
			String attributed = renderer.renderMarkdown("[quote author=Bob]\n# heading\n\n- one\n[/quote]");

			assertTrue(attributed.contains("<h1>heading</h1>") && attributed.contains("<li>one</li>"),
					"the two modes differ only in a header written above the same body, so an element scanner "
							+ "that reads </div> as an unnamed element lets an attribute decide whether the body is "
							+ "parsed as markdown at all: " + attributed);
			assertEquals(
					Jsoup.parseBodyFragment(unattributed).selectFirst("div.bb-code-quote-body").html(),
					Jsoup.parseBodyFragment(attributed).selectFirst("div.bb-code-quote-body").html(),
					"and the body the two modes host has to be the same body, not merely both parsed: "
							+ attributed);
		}

		@Test
		void theQuoteModesThatNameASourceStillCarryElementsNoMarkdownBlockMayHost() {
			int modesTheSourceHeaderKeepsOutOfTheMarkdownLane = 0;
			for (Map.Entry<String, BBCodeAttributeMode> mode
					: seededBBCodeGrammar().get("QUOTE").getAttributeConfig().entrySet()) {
				if (!mode.getKey().contains("msg="))
					continue;
				modesTheSourceHeaderKeepsOutOfTheMarkdownLane++;
				List<String> named = ContentLevel.everyElementNamedIn(mode.getValue().getOpenTag());

				assertTrue(named.contains("a") && named.contains("time"),
						"the source-bearing header links to the quoted message and dates it, and neither element "
								+ "may hold parsed markdown blocks, so these modes stay refused for a reason the "
								+ "scanner fix does not touch: " + mode.getKey() + " -> " + named);
			}

			assertEquals(4, modesTheSourceHeaderKeepsOutOfTheMarkdownLane,
					"a mode key that stops naming its source would take its markup out of this pin without "
							+ "taking it out of the refusal it is meant to explain");
		}

		@Test
		void anotherCodesOrphanCloserInsideTheBodyIsNotTheBlocksOwnCloser() {
			assertEquals("<div class=\"bb-code-quote\"><div class=\"bb-code-quote-body\">\n"
					+ "<p>body [/b] more </p>\n</div></div>\n<p> tail</p>\n",
					renderer.renderMarkdown("[quote]\nbody [/b] more [/quote] tail"),
					"an orphan closer belonging to some other code is ordinary text the bbcode lane prints as it "
							+ "is; a search for the first unmatched closer of any code closes the quote on [/b] and "
							+ "leaks the real closer straight back out");
			assertEquals("<div class=\"bb-code-quote\"><div class=\"bb-code-quote-body\"><br>body [/b]</div>"
					+ "</div> more [/quote] tail",
					renderer.render("[quote]\nbody [/b] more [/quote] tail"),
					"the bbcode lane closes the quote on behalf of the crossed [/b] instead, which is the one "
							+ "shape where the markdown lane is now the more faithful of the two; pinning it here "
							+ "keeps that a stated divergence rather than an unnoticed one");
		}

		@Test
		void aCloserWithTrailingTextStaysLiteralWhereverMarkdownOrBBCodeHoldsItsBodyVerbatim() {
			assertEquals("<pre><code>[quote]\nbody [/quote] tail\n</code></pre>\n",
					renderer.renderMarkdown("```\n[quote]\nbody [/quote] tail\n```"),
					"a fenced code block owns its lines, so no block ever opened and nothing may close");
			assertEquals("<pre class=\"bb-code-code\">body [/code] tail</pre>\n",
					renderer.renderMarkdown("[code]\nbody [/code] tail\n[/code]"),
					"a verbatim bbcode block holds its body as authored text, which is the only way [code] can "
							+ "carry a sample containing its own closer");
			assertEquals("<div class=\"bb-code-quote\"><div class=\"bb-code-quote-body\">\n"
					+ "<p>body <code>[/quote] not a closer</code> still inside\n</p>\n</div></div>\n<p> tail</p>\n",
					renderer.renderMarkdown("[quote]\nbody `[/quote] not a closer` still inside\n[/quote] tail"),
					"a code span is its own inline node, so it never joins the text run the closer is read out "
							+ "of; a line-oriented scan cannot tell the two apart");
		}

		@Test
		void aListItemMarkerInOrdinaryProseNeverSuppressesTheCodesAroundIt() {
			String source = "footnote [*] marker with [b]bold[/b]";

			assertEquals("<p>footnote [*] marker with <span class=\"bb-code-b\">bold</span></p>\n",
					renderer.renderMarkdown(source),
					"[*] only means anything inside a list, so keying the refusal on the raw run text made an "
							+ "ordinary footnote marker silently disable every code beside it");
			assertEquals("footnote [*] marker with <span class=\"bb-code-b\">bold</span>", renderer.render(source),
					"the bbcode lane renders it, so a markdown lane that did not would be the odd one out");
		}

		@Test
		void aCodeWhoseMarkupCannotHoldParsedMarkdownNeverOpensAContainer() {
			assertEquals("<ul>\n<li>one</li>\n</ul>\n",
					renderer.renderMarkdown("[list]\n[li]one[/li]\n[/list]"),
					"a <ul> may only contain <li>, so commonmark blocks must never be parsed into one; the whole "
							+ "run expands through the bbcode renderer instead");
			assertEquals("<p>[h1]</p>\n<h1>H</h1>\n<p>[/h1]</p>\n", renderer.renderMarkdown("[h1]\n# H\n[/h1]"),
					"a heading holds phrasing content only");
		}

		@Test
		void aRequiredChildElementIsNeverOpenedAsAnOrphanAroundAMarkdownBody() {
			assertEquals("<p>[li]</p>\n<h1>H</h1>\n<p>[/li]</p>\n", renderer.renderMarkdown("[li]\n# H\n[/li]"),
					"the same allowlist that refuses containers refuses their required children, because an <li> "
							+ "with no list around it is an orphan the sanitizer happily keeps and the reader gets "
							+ "a bare list item in the middle of the page");
			assertEquals("<p>[td]</p>\n<h1>H</h1>\n<p>para\n[/td]</p>\n",
					renderer.renderMarkdown("[td]\n# H\n\npara\n[/td]"),
					"an orphan <td> is worse than an orphan <li>: the sanitizer deletes the tag silently and the "
							+ "cell content reflows into the document as if it had never been in a table");
		}

		@Test
		void aVerbatimBlocksBodyIsEscapedBeforeItReachesThePage() {
			assertEquals("<pre class=\"bb-code-code\">&lt;script&gt;alert(1)&lt;/script&gt;</pre>\n",
					renderer.renderMarkdown("[code]\n<script>alert(1)</script>\n[/code]"),
					"the collected verbatim lines are written raw into the output, so this escape is the only "
							+ "thing between a pasted code sample and script execution");
		}

		@Test
		void aFencedCodeBlockInsideABBCodeBlockKeepsItsCloserAsText() {
			assertEquals("<div class=\"bb-code-quote\"><div class=\"bb-code-quote-body\">\n"
					+ "<pre><code>[/quote]\n</code></pre>\n</div></div>\n",
					renderer.renderMarkdown("[quote]\n```\n[/quote]\n```\n[/quote]"),
					"the outer block's tryContinue runs before the fence's, so without asking whether a verbatim "
							+ "markdown block owns the line the fence's own text closes the quote early");
		}

		@Test
		void anIndentedCodeBlockNeverOpensABBCodeBlock() {
			assertEquals("<pre><code>[quote]\nbody\n[/quote]\n</code></pre>\n",
					renderer.renderMarkdown("    [quote]\n    body\n    [/quote]"),
					"custom block factories are consulted before every core factory, so without the indent check "
							+ "a bbcode block wins over markdown's own indented code block");
		}

		@Test
		void anInlineCodeNeverOpensABlockEvenAloneOnItsLine() {
			assertEquals("<p><span class=\"bb-code-b\">\ntext\n</span></p>\n",
					renderer.renderMarkdown("[b]\ntext\n[/b]"),
					"an inline code's <span> is not on the allowlist either, so the one allowlist covers inline "
							+ "codes, orphans and illegal containers alike rather than three separate refusals");
		}

		@Test
		void aBlockLevelExpansionKeepsTheTextAroundItInsideAParagraph() {
			assertEquals("<hr>\n<p>after</p>\n", renderer.renderMarkdown("[hr]after"),
					"the text beside a lifted block belongs in a paragraph of its own, not loose in the body");
		}

		@Test
		void aTreeFarDeeperThanAnyPageCanStillBeRenderedInTheMarkdownLane() {
			int depth = 20000;
			String source = "[b]".repeat(depth) + "x" + "[/b]".repeat(depth);

			String rendered = assertDoesNotThrow(() -> renderer.renderMarkdown(source),
					"the bbcode lane walks the tree iteratively so a hostile page cannot overflow the stack; the "
							+ "markdown lane walks the same tree and must not reintroduce the limit");

			assertEquals(depth, rendered.split("<span class=\"bb-code-b\">", -1).length - 1);
		}

		@Test
		void aRunSplitAcrossAMarkdownBlockBoundaryLeavesBothTagsVisible() {
			assertEquals("<p>para\n[quote]</p>\n<h1>H</h1>\n<ul>\n<li>a\n[/quote]</li>\n</ul>\n",
					renderer.renderMarkdown("para\n[quote]\n# H\n\n- a\n[/quote]"),
					"the heading ends the paragraph the opener landed in, so no single run ever holds both tags "
							+ "and the reader sees them; re-uniting them would mean re-deriving markdown's own "
							+ "block structure, which is the thing this lane exists not to do");
			assertEquals("<p>[b]</p>\n<h1>H</h1>\n<p>[/b]</p>\n", renderer.renderMarkdown("[b]\n# H\n[/b]"),
					"the same class for an inline code, which no block factory can rescue");
		}

		@Test
		void aBlockOpensAMarkdownContainerOnlyAtABlockBoundaryAndIsLiftedOtherwise() {
			assertEquals("<div class=\"bb-code-quote\"><div class=\"bb-code-quote-body\">\n<p>outer</p>\n"
					+ "<div class=\"bb-code-quote\"><div class=\"bb-code-quote-body\">\n<p>inner</p>\n"
					+ "</div></div>\n</div></div>\n",
					renderer.renderMarkdown("[quote]\nouter\n\n[quote]\ninner\n[/quote]\n[/quote]"),
					"a blank line closes the paragraph, so the inner opener sits at a block boundary and opens a "
							+ "container whose body is markdown");
			assertEquals("<div class=\"bb-code-quote\"><div class=\"bb-code-quote-body\">\n<p>outer\n</p>\n"
					+ "<div class=\"bb-code-quote\"><div class=\"bb-code-quote-body\">\ninner\n</div></div>\n"
					+ "</div></div>\n",
					renderer.renderMarkdown("[quote]\nouter\n[quote]\ninner\n[/quote]\n[/quote]"),
					"without the blank line the inner opener is inside an open paragraph, so the lift renders it "
							+ "through the bbcode renderer and its body is never markdown-parsed");
		}
	}

	@Nested
	class ConfigLoading {

		private BBCodeDataProvider dataProvider;
		private String declaredDataTypeCode;
		private BBCodeConfigMap configMap;

		@BeforeEach
		void setup() {
			BBCodeConfigDao configDao = mock(BBCodeConfigDao.class);
			BBCodeAttributeModeDboMapper modeMapper = mock(BBCodeAttributeModeDboMapper.class);
			BBCodeAttributeDboMapper attributeMapper = mock(BBCodeAttributeDboMapper.class);
			configMap = mock(BBCodeConfigMap.class);
			BBCodeAttributeModeMap modeMap = mock(BBCodeAttributeModeMap.class);
			BBCodeAttributeMap attributeMap = mock(BBCodeAttributeMap.class);
			AttributeDataTypeDboMapper dataTypeMapper = mock(AttributeDataTypeDboMapper.class);
			when(dataTypeMapper.selectByExample(any(AttributeDataTypeDboExample.class))).thenReturn(List.of());
			AttributeValueMappingDboMapper valueMappingMapper = mock(AttributeValueMappingDboMapper.class);
			when(valueMappingMapper.selectByExample(any(AttributeValueMappingDboExample.class)))
					.thenReturn(List.of());
			ListStyleTypeDboMapper listStyleTypeMapper = mock(ListStyleTypeDboMapper.class);
			when(listStyleTypeMapper.selectByExample(any(ListStyleTypeDboExample.class))).thenReturn(List.of());
			dataProvider = new BBCodeDataProvider(configDao, modeMapper, attributeMapper, dataTypeMapper,
					valueMappingMapper, listStyleTypeMapper, configMap, modeMap, attributeMap);

			when(configDao.get(any(BBCodeConfigDboExample.class)))
					.thenReturn(List.of(new BBCodeConfigDbo()));
			when(configMap.toModel(any(BBCodeConfigDbo.class))).thenAnswer(invocation -> {
				BBCodeConfig quote = new BBCodeConfig();
				quote.setCode("quote");
				quote.setBbCodeConfigId(18);
				return quote;
			});

			when(modeMapper.selectByExample(any(BBCodeAttributeModeDboExample.class)))
					.thenReturn(List.of(new BBCodeAttributeModeDbo()));
			when(modeMap.toModel(any(BBCodeAttributeModeDbo.class))).thenAnswer(invocation -> {
				BBCodeAttributeMode mode = new BBCodeAttributeMode();
				mode.setBbCodeAttributeModeId(19);
				return mode;
			});

			when(attributeMapper.selectByExample(any(BBCodeAttributeDboExample.class)))
					.thenReturn(List.of(new BBCodeAttributeDbo()));
			when(attributeMap.toModel(any(BBCodeAttributeDbo.class))).thenAnswer(invocation -> {
				BBCodeAttribute attribute = new BBCodeAttribute();
				attribute.setBbCodeAttributeId(6);
				attribute.setBbCodeAttributeModeId(19);
				attribute.setAttributeIndex("2");
				attribute.setName("date");
				attribute.setAttributeDataType(declaredDataTypeCode);
				return attribute;
			});
		}

		private BBCodeAttribute loadDeclaring(String dataTypeCode) {
			declaredDataTypeCode = dataTypeCode;
			return dataProvider.getBBCodeConfig().get("QUOTE")
					.getAttributeConfig().get("date=").getAttributes().get(0);
		}

		private BBCodeConfig loadConfigDeclaring(String referenceAttribute, String resolverCode) {
			declaredDataTypeCode = AttributeDataType.TEXT.name();
			when(configMap.toModel(any(BBCodeConfigDbo.class))).thenAnswer(invocation -> {
				BBCodeConfig quote = new BBCodeConfig();
				quote.setCode("quote");
				quote.setBbCodeConfigId(18);
				quote.setSourceReferenceAttribute(referenceAttribute);
				quote.setSourceReferenceResolver(resolverCode);
				return quote;
			});
			return dataProvider.getBBCodeConfig().get("QUOTE");
		}

		@Test
		void aDeclaredSourceReferenceAttributeGainsTheSameEqualsSuffixAsEveryOtherAttributeName() {
			BBCodeConfig loaded = loadConfigDeclaring("msg", QuotedMessageSource.RESOLVER_CODE);

			assertEquals("msg=", loaded.getSourceReferenceAttribute(),
					"attribute names are stored bare and gain '=' on load; if the source reference skips that "
							+ "normalization it can never match a parsed attribute key");
			assertTrue(loaded.referencesSourceContent());
		}

		@Test
		void aConfigDeclaringOnlyOneHalfOfASourceReferenceIsRejectedAtLoad() {
			InvalidBBCodeGrammarException unloadable = assertThrows(InvalidBBCodeGrammarException.class,
					() -> loadConfigDeclaring(null, QuotedMessageSource.RESOLVER_CODE));

			assertTrue(unloadable.getMessage().contains("needs both"), unloadable.getMessage());
		}

		@Test
		void aDeclaredDataTypeCodeIsResolvedByNameNotByEnumOrdinal() {
			assertEquals(AttributeDataType.TIMESTAMP, loadDeclaring("TIMESTAMP").getDataType(),
					"a TIMESTAMP-declaring row must load as TIMESTAMP");
			assertEquals("<time class=\"bb-date-long\" datetime=\"2017-05-12T01:28:23Z\">"
							+ "May 12, 2017, 1:28:23 AM</time>",
					BBCodeDateElement.theEpochSecondsAsADateElement("1494552503"),
					"the date element is written where attribute values are applied, not by the model");
		}

		@Test
		void everyDataTypeCodeTheSeedCanWriteIsLoadable() {
			for (AttributeDataType declared : AttributeDataType.values())
				assertDoesNotThrow(() -> loadDeclaring(declared.name()),
						"the loader must accept every code the bbcode seed can write: " + declared);
		}

		@Test
		void aGrammarWhoseCustomPropertyBindingsAreRefusedIsNeverPublished() {
			BBCodeDataProvider provider = mock(BBCodeDataProvider.class);
			BBCodeGrammarHolder grammarHolder = grammarHolder();
			RenderedTextEnricher enricher = enricher();
			ContentOutputSanitizer sanitizer = sanitizer(grammarHolder, enricher);
			Renderer renderer = buildRenderer(provider, grammarHolder, enricher, sanitizer, messageResolver());
			Map<String, BBCodeConfig> published = new HashMap<>(QuoteSourceReference.buildConfig());
			published.putAll(UrlAndStylePolicy.theGrammarFillingOneCustomPropertyFrom(
					AttributeDataType.COLOR, AttributeDataType.COLOR));
			renderer.useGrammar(published);
			String renderedBeforeTheRefusal = renderer.render("[b]x[/b]");
			when(provider.theDeclaredListStyleTypes()).thenReturn(Map.of());

			InvalidBBCodeGrammarException refused = assertThrows(InvalidBBCodeGrammarException.class,
					() -> renderer.useGrammar(UrlAndStylePolicy.theGrammarFillingOneCustomPropertyFrom(
							AttributeDataType.COLOR, AttributeDataType.SIZE)));

			assertTrue(refused.getMessage().contains("--bb-lane"), refused.getMessage());
			assertEquals(renderedBeforeTheRefusal, renderer.render("[b]x[/b]"),
					"the binding scan ran after the grammar was already live, so a refused candidate rolled the "
							+ "database back while memory kept the candidate; every invariant is checked before "
							+ "anything is published now");
			assertTrue(renderer.grammarHolder().current().listStyleTypeNumbersItsItems("decimal"),
					"the list style policy is published in the same swap and must not have moved either");
			assertTrue(renderer.handler().containsSourceReference("[quote msg=5]x[/quote]"),
					"nor may the source reference service, which answers from the published grammar");
			assertTrue(sanitizer.sanitize("<span style=\"--bb-lane:red\">x</span>").contains("--bb-lane:red"),
					"and the sanitizer keeps the bindings of the grammar that is actually live");
		}

		@Test
		void anUnknownDataTypeCodeFailsWithAMessageNamingTheOffendingRow() {
			declaredDataTypeCode = "NOT_A_DATA_TYPE";

			InvalidBBCodeGrammarException unloadable =
					assertThrows(InvalidBBCodeGrammarException.class, () -> dataProvider.getBBCodeConfig());

			assertTrue(unloadable.getMessage().contains("bb_code_attribute 6"), unloadable.getMessage());
			assertTrue(unloadable.getMessage().contains("date"), unloadable.getMessage());
			assertTrue(unloadable.getMessage().contains("NOT_A_DATA_TYPE"), unloadable.getMessage());
			assertTrue(unloadable.getMessage().contains("TIMESTAMP"), unloadable.getMessage());
		}
	}

	@Nested
	class ValuePolicy {

		static Map<AttributeDataType, AttributeValuePolicy> seeded() {
			return seededAttributeValuePolicies();
		}

		static final String HEX_COLOR_THIS_BUILD_ALWAYS_ACCEPTED =
				"#(?:[0-9a-fA-F]{3,4}|[0-9a-fA-F]{6}|[0-9a-fA-F]{8})";

		static final String RGB_COLOR_THIS_BUILD_ALWAYS_ACCEPTED =
				"rgba?\\(\\s*\\d{1,3}\\s*,\\s*\\d{1,3}\\s*,\\s*\\d{1,3}\\s*(?:,\\s*(?:0|1|0?\\.\\d+)\\s*)?\\)";

		static final String NAMED_COLOR_THIS_BUILD_ALWAYS_ACCEPTED = "[a-zA-Z]+";

		static final Map<AttributeDataType, String> THE_PATTERN_EACH_TYPE_SHIPPED_BEFORE_THE_ROWS = Map.of(
				AttributeDataType.INTEGER, "^\\d+$",
				AttributeDataType.DIMENSION, "^\\d+(?:\\.\\d+)?(?:px|pt|em|rem|%)?$",
				AttributeDataType.SIZE, "^\\d+(?:\\.\\d+)?(?:px|pt|em|rem|%)?$",
				AttributeDataType.IDENTIFIER, "^[A-Za-z0-9_-]+$",
				AttributeDataType.FONT_NAME, "^[A-Za-z0-9 ,'\"_-]+$",
				AttributeDataType.COLOR, "^(?:" + HEX_COLOR_THIS_BUILD_ALWAYS_ACCEPTED + "|"
						+ RGB_COLOR_THIS_BUILD_ALWAYS_ACCEPTED + "|"
						+ NAMED_COLOR_THIS_BUILD_ALWAYS_ACCEPTED + ")$");

		static final Set<AttributeDataType> THE_TYPES_THE_RENDERER_STILL_ANSWERS_ITSELF = Set.of(
				AttributeDataType.TIMESTAMP, AttributeDataType.TEXT, AttributeDataType.URL);

		@Test
		void theSeedDeclaresARowForEveryDataTypeAndEveryPatternCompiles() {
			Map<AttributeDataType, AttributeValuePolicy> seeded = seeded();

			assertEquals(Set.of(AttributeDataType.values()), seeded.keySet(),
					"a data type with no row has no value policy at all now that the Java fallbacks are gone, so "
							+ "every value of it would be rejected on a live board with no test failing first");
			for (AttributeDataType type : AttributeDataType.values()) {
				if (THE_TYPES_THE_RENDERER_STILL_ANSWERS_ITSELF.contains(type))
					continue;
				AttributeValuePolicy policy = seeded.get(type);
				assertTrue(policy.validationPattern().isPresent() || !policy.allowedValues().isEmpty(),
						"a type the renderer routes to the row-backed policy has to declare either a pattern or a "
								+ "set of allowed values, or it silently rejects everything: " + type);
			}
		}

		@ParameterizedTest(name = "{0}")
		@MethodSource("theTypesWhoseShippedPatternMovedIntoASeedRow")
		void theSeededPatternIsTheOneThisBuildShippedCharacterForCharacter(AttributeDataType type) {
			assertEquals(THE_PATTERN_EACH_TYPE_SHIPPED_BEFORE_THE_ROWS.get(type),
					seeded().get(type).validationPattern().orElseThrow().pattern(),
					"moving a pattern into data may not change one character of what it accepts: " + type);
		}

		static Stream<Arguments> theTypesWhoseShippedPatternMovedIntoASeedRow() {
			return THE_PATTERN_EACH_TYPE_SHIPPED_BEFORE_THE_ROWS.keySet().stream().map(Arguments::of);
		}

		private BBCodeAttribute attributeDeclaring(AttributeDataType type, AttributeValuePolicy policy) {
			BBCodeAttribute attribute = new BBCodeAttribute();
			attribute.setName("=");
			attribute.setAttributeIndex("{{0}}");
			attribute.setDataType(type);
			attribute.setValuePolicy(policy);
			return attribute;
		}

		private String transformedBySeededRowFor(AttributeDataType type, String value) {
			return attributeDeclaring(type, seeded().get(type)).transformValue(value);
		}

		@ParameterizedTest(name = "{0}")
		@MethodSource("valuesTheSeededRowsDecide")
		void theSeededRowDecidesWhatEachTypeAccepts(String caseName, AttributeDataType type, String written,
				String expected) {
			assertEquals(expected, transformedBySeededRowFor(type, written),
					"the row decides this, or the column is decoration: " + type + " '" + written + "'");
		}

		static Stream<Arguments> valuesTheSeededRowsDecide() {
			return Stream.of(
					arguments("whole number accepted", AttributeDataType.INTEGER, "42", "42"),
					arguments("whole number rejected", AttributeDataType.INTEGER, "four", ""),
					arguments("identifier accepted", AttributeDataType.IDENTIFIER, "left-1", "left-1"),
					arguments("identifier rejected", AttributeDataType.IDENTIFIER, "left 1", ""),
					arguments("font name accepted", AttributeDataType.FONT_NAME, "Comic Sans MS", "Comic Sans MS"),
					arguments("font name rejected", AttributeDataType.FONT_NAME, "Comic<Sans", ""),
					arguments("hex colour lowercased", AttributeDataType.COLOR, "#AABBCC", "#aabbcc"),
					arguments("named colour lowercased", AttributeDataType.COLOR, "Red", "red"),
					arguments("rgb colour accepted", AttributeDataType.COLOR, "rgb(1,2,3)", "rgb(1,2,3)"),
					arguments("colour rejected", AttributeDataType.COLOR, "#zz", ""),
					arguments("bare dimension gains the row's unit", AttributeDataType.DIMENSION, "12", "12px"),
					arguments("dimension with a unit keeps it", AttributeDataType.DIMENSION, "12em", "12em"),
					arguments("fractional dimension gains no unit", AttributeDataType.DIMENSION, "12.5", "12.5"),
					arguments("dimension rejected", AttributeDataType.DIMENSION, "wide", ""),
					arguments("legacy size level 1", AttributeDataType.SIZE, "1", "8pt"),
					arguments("legacy size level 7", AttributeDataType.SIZE, "7", "36pt"),
					arguments("size above the levels gains the unit", AttributeDataType.SIZE, "8", "8px"),
					arguments("size zero gains the unit", AttributeDataType.SIZE, "0", "0px"),
					arguments("size with a unit keeps it", AttributeDataType.SIZE, "2em", "2em"),
					arguments("size rejected", AttributeDataType.SIZE, "huge", ""),
					arguments("list style accepted", AttributeDataType.LIST_TYPE, "Upper-Roman", "upper-roman"),
					arguments("list style falls back", AttributeDataType.LIST_TYPE, "spiral", "decimal"));
		}

		@Test
		void aLegacyLevelWrittenWithLeadingZerosStillReachesItsMapping() {
			assertEquals("8pt", transformedBySeededRowFor(AttributeDataType.SIZE, "01"),
					"the old sanitizeSize read the level with Integer.parseInt, so [size=01] was 8pt; an "
							+ "exact-string mapping lookup reads '01' as unmapped and renders 01px, which is a "
							+ "different font size on every legacy post that padded its level");
			assertEquals("36pt", transformedBySeededRowFor(AttributeDataType.SIZE, "0007"),
					"the same divergence at the top of the level family: [size=0007] was 36pt");
			assertEquals("8pt", transformedBySeededRowFor(AttributeDataType.SIZE, "1"),
					"and the unpadded level has to keep mapping the way it always did");
		}

		@Test
		void aPaddedValueOutsideTheLevelFamilyKeepsTheDigitsTheAuthorWrote() {
			assertEquals("010px", transformedBySeededRowFor(AttributeDataType.SIZE, "010"),
					"the old code normalised only to find the level; what it emitted for a value outside the "
							+ "family was the author's own string plus the unit, so normalising the emitted value "
							+ "too would be a second divergence rather than a fix");
			assertEquals("00px", transformedBySeededRowFor(AttributeDataType.SIZE, "00"),
					"and a padded zero is outside the family the same way");
		}

		@Test
		void aSizeTooLargeForAnIntegerRendersAValueInsteadOfThrowing() {
			String rendered = assertDoesNotThrow(
					() -> transformedBySeededRowFor(AttributeDataType.SIZE, "99999999999"));

			assertEquals("99999999999px", rendered,
					"the old sanitizeSize called Integer.parseInt on any all-digit value and let the "
							+ "NumberFormatException escape, so [size=99999999999] was a render-time crash on every "
							+ "view of the post; the row-backed policy never parses, and this pins that improvement "
							+ "rather than the crash");
		}

		@Test
		void aMappingRowBeatsThePatternThatWouldOtherwiseHaveAcceptedTheValue() {
			assertEquals("12pt", transformedBySeededRowFor(AttributeDataType.SIZE, "3"),
					"3 matches SIZE's validation pattern on its own, so a mapped value only wins if the mapping "
							+ "table is consulted before the pattern");
			assertEquals("3px", transformedBySeededRowFor(AttributeDataType.DIMENSION, "3"),
					"the same 3 with no mapping row for its type takes the pattern-and-unit path instead");
		}

		@Test
		void theLowercasingFlagIsWhatLetsAnUpperCaseValueMatchAtAll() {
			AttributeValuePolicy withoutLowercasing = new AttributeValuePolicy(
					seeded().get(AttributeDataType.LIST_TYPE).validationPattern(), "decimal", false, false,
					Optional.empty(), seeded().get(AttributeDataType.LIST_TYPE).allowedValues(), Map.of());

			assertEquals("decimal", attributeDeclaring(AttributeDataType.LIST_TYPE, withoutLowercasing)
					.transformValue("Upper-Roman"),
					"with lowercases_value off the allowed-value set never matches a capitalised value");
			assertEquals("upper-roman", transformedBySeededRowFor(AttributeDataType.LIST_TYPE, "Upper-Roman"),
					"and with the seeded row's flag on it does");
		}

		@Test
		void theBareIntegerUnitIsWhatSuffixesAnAcceptedWholeNumber() {
			AttributeValuePolicy withoutTheUnit = new AttributeValuePolicy(
					seeded().get(AttributeDataType.DIMENSION).validationPattern(), "", false, false,
					Optional.empty(), Set.of(), Map.of());

			assertEquals("12", attributeDeclaring(AttributeDataType.DIMENSION, withoutTheUnit).transformValue("12"),
					"with bare_integer_unit unset the accepted value is written through unchanged");
			assertEquals("12px", transformedBySeededRowFor(AttributeDataType.DIMENSION, "12"),
					"and with the seeded row's unit it is suffixed");
		}

		@Test
		void aTypeWhosePatternWillNotCompileRejectsEveryValueWithNoJavaFallbackLeft() {
			AttributeValuePolicy uncompilable = AttributeValuePolicy.rejectingEveryValue("");

			assertEquals("", attributeDeclaring(AttributeDataType.INTEGER, uncompilable).transformValue("42"),
					"a row the loader could not compile leaves the type with nothing to validate against, and the "
							+ "only safe reading of nothing is to accept nothing");
		}

		@ParameterizedTest(name = "{0}")
		@MethodSource("theTypesWhoseShippedPatternMovedIntoASeedRow")
		void aValueLongerThanTheCapIsRejectedWithoutRunningThePatternOverIt(AttributeDataType type) {
			String longerThanTheCap =
					"1".repeat(AttributeValuePolicy.LONGEST_VALUE_A_VALIDATION_PATTERN_IS_APPLIED_TO + 1);

			assertTrue(AttributeValuePolicy.LONGEST_VALUE_A_VALIDATION_PATTERN_IS_APPLIED_TO > 0
					&& AttributeValuePolicy.LONGEST_VALUE_A_VALIDATION_PATTERN_IS_APPLIED_TO <= 65536,
					"a pattern that arrives as data can backtrack, so the input it runs over has to be bounded, "
							+ "and a bound nobody can state is not a bound");
			assertEquals("", transformedBySeededRowFor(type, longerThanTheCap),
					"an unbounded value handed to an administrator-supplied pattern is a denial of service the "
							+ "renderer would run on every view of the post");
		}

		@Test
		void escapingStaysInJavaBecauseItIsNotADecisionARowCanExpress() {
			assertEquals("&lt;b&gt;", transformedBySeededRowFor(AttributeDataType.TEXT, "<b>"),
					"TEXT can never become a row: the attribute value is escaped before it is spliced into the "
							+ "open tag, and a value that closes its own attribute would inject a safelisted "
							+ "element that the output sanitizer -- which parses the assembled string -- then "
							+ "reads as the author's own markup");
		}

		@Test
		void everyListStyleTheSeedAllowsAlsoDeclaresWhetherItNumbersItsItems() {
			assertEquals(seededListStyleTypes().keySet(),
					seeded().get(AttributeDataType.LIST_TYPE).allowedValues(),
					"LIST_TYPE's allowed values are derived from list_style_type in the seed, so a style that is "
							+ "allowed but declares no numbering -- or numbers but is not allowed -- means the "
							+ "derivation stopped being a derivation");
		}
	}


	@Nested
	class EngineNeutrality {

		private Map<String, BBCodeConfig> citeAsTheOnlySourceReferencingTag() {
			BBCodeConfig cite = quoteConfig();
			cite.setCode("cite");
			cite.setSourceReferenceAttribute("ref=");
			cite.setAllAttributeNamesAsString("author=,thread=,ref=");
			cite.setAttributeConfig(new HashMap<>(Map.of(
					"ref=", mode(QUOTE_HEADER, attr("ref=", "{{0}}")))));
			BBCodeConfig quote = quoteConfig();
			quote.setSourceReferenceAttribute(null);
			quote.setSourceReferenceResolver(null);
			return new HashMap<>(Map.of("CITE", cite, "QUOTE", quote));
		}

		private Renderer rendererLoading(Map<String, BBCodeConfig> grammar) {
			Renderer renderer = buildRenderer(mock(BBCodeDataProvider.class), messageResolver());
			renderer.useGrammar(grammar);
			return renderer;
		}

		private Renderer serviceWithCiteAsTheOnlySourceReferencingTag() {
			return rendererLoading(citeAsTheOnlySourceReferencingTag());
		}

		@Test
		void aTagOtherThanQuoteResolvesItsSourceWhenTheConfigurationSaysSo() {
			SourceReferenceService handler = serviceWithCiteAsTheOnlySourceReferencingTag().handler();

			assertEquals(Set.of(5), handler.collectSourceReferenceIds("[cite ref=5]body[/cite]"),
					"the engine must collect ids for whatever tag declares a source reference, not for 'quote'");
			assertTrue(handler.containsSourceReference("[cite ref=5]body[/cite]"));
		}

		@Test
		void theQuoteTagStopsResolvingWhenItNoLongerDeclaresASourceReference() {
			SourceReferenceService handler = serviceWithCiteAsTheOnlySourceReferencingTag().handler();

			assertEquals(Set.of(), handler.collectSourceReferenceIds("[quote msg=5]body[/quote]"),
					"a tag with no source reference configured must not be resolved; if this passes only "
							+ "because 'quote' is hardcoded somewhere, the engine is not data-driven");
			assertFalse(handler.containsSourceReference("[quote msg=5]body[/quote]"));
		}

		@Test
		void aSelfClosingTagBeforeASourceReferenceDoesNotAbortTheScan() {
			BBCodeConfig horizontalRule = simpleTag("hr", "<hr/>", "", false);
			horizontalRule.setSelfClosingFlag(true);
			Map<String, BBCodeConfig> grammar = citeAsTheOnlySourceReferencingTag();
			grammar.put("HR", horizontalRule);
			Renderer renderer = rendererLoading(grammar);
			SourceReferenceService handler = renderer.handler();

			assertEquals(Set.of(5), handler.collectSourceReferenceIds("[hr][cite ref=5]body[/cite]"),
					"a self-closing tag has no closing tag to find, so treating it as a literal block aborts "
							+ "the walk and silently drops every source reference after it");
			assertEquals(Set.of(9, 10), handler.collectSourceReferenceIds(
					"a [hr] b [cite ref=9]x[/cite] c [cite ref=10]y[/cite]"));
			assertTrue(renderer.grammarHolder().current().configs().containsKey("HR")
							&& !renderer.grammarHolder().current().configs().get("HR").referencesSourceContent(),
					"the assertions above only prove anything while HR is a known self-closing tag; if it is "
							+ "missing from the grammar they pass for the wrong reason");
		}

		@Test
		void aLiteralBlockStillHidesTheSourceReferencesInsideIt() {
			Map<String, BBCodeConfig> grammar = citeAsTheOnlySourceReferencingTag();
			grammar.put("CODE", codeConfig());
			SourceReferenceService handler = rendererLoading(grammar).handler();

			assertEquals(Set.of(), handler.collectSourceReferenceIds("[code][cite ref=5]x[/cite][/code]"),
					"ids inside a literal block are never rendered, so resolving them costs a query and "
							+ "throws the result away");
			assertEquals(Set.of(6), handler.collectSourceReferenceIds(
					"[code][cite ref=5]x[/cite][/code][cite ref=6]y[/cite]"));
		}

		@Test
		void aConfigNamingAnUnregisteredResolverIsRejectedAtLoadRatherThanOnFirstRender() {
			BBCodeDataProvider provider = mock(BBCodeDataProvider.class);
			BBCodeConfig unknownResolver = quoteConfig();
			unknownResolver.setBbCodeConfigId(18);
			unknownResolver.setSourceReferenceResolver("NO_SUCH_RESOLVER");
			when(provider.getBBCodeConfig()).thenReturn(new HashMap<>(Map.of("QUOTE", unknownResolver)));
			BBCodeGrammarLoader service = buildRenderer(provider, messageResolver()).service();

			InvalidBBCodeGrammarException unloadable =
					assertThrows(InvalidBBCodeGrammarException.class, service::loadBBCodeConfig);

			assertTrue(unloadable.getMessage().contains("NO_SUCH_RESOLVER"), unloadable.getMessage());
		}

		@Test
		void aGrammarThatFailsValidationIsNeverPublishedToTheRenderer() {
			BBCodeDataProvider provider = mock(BBCodeDataProvider.class);
			BBCodeConfig unknownResolver = quoteConfig();
			unknownResolver.setBbCodeConfigId(18);
			unknownResolver.setSourceReferenceResolver("NO_SUCH_RESOLVER");
			when(provider.getBBCodeConfig()).thenReturn(new HashMap<>(Map.of("QUOTE", unknownResolver)));
			Renderer renderer = buildRenderer(provider, messageResolver());
			BBCodeGrammar published = renderer.grammarHolder().current();

			assertThrows(InvalidBBCodeGrammarException.class, renderer.service()::loadBBCodeConfig);

			assertSame(published, renderer.grammarHolder().current(),
					"a grammar that fails validation must not go live; publishing before validating leaves the "
							+ "renderer on the rejected grammar and the handler on the old one until restart");
		}

		@Test
		void twoDeclaredResolversAreRejectedAtLoadBecauseSourceIdsArePooled() {
			BBCodeDataProvider provider = mock(BBCodeDataProvider.class);
			BBCodeConfig quote = quoteConfig();
			quote.setBbCodeConfigId(18);
			BBCodeConfig cite = quoteConfig();
			cite.setBbCodeConfigId(19);
			cite.setCode("cite");
			cite.setSourceReferenceResolver("WIKI_PAGE");
			when(provider.getBBCodeConfig()).thenReturn(new HashMap<>(Map.of("QUOTE", quote, "CITE", cite)));
			ContentTagResolver wikiResolver = mock(ContentTagResolver.class);
			when(wikiResolver.resolverCode()).thenReturn("WIKI_PAGE");
			BBCodeGrammarLoader service =
					buildRenderer(provider, messageResolver(), wikiResolver).service();

			InvalidBBCodeGrammarException unloadable =
					assertThrows(InvalidBBCodeGrammarException.class, service::loadBBCodeConfig);

			assertTrue(unloadable.getMessage().contains("source_reference_resolver"), unloadable.getMessage());
		}

		@Test
		void theSeededQuoteTagDeclaresItsSourceReferenceSoTheEngineCanFindIt() throws IOException {
			String seed = Files.readString(
					Path.of("src/main/resources/db/migration/functions/R__03_bbcodes.sql"));
			assertTrue(seed.contains("'quote', '</div></div>', true, false, true, 'msg', 'MESSAGE'"),
					"if the quote tag stops declaring its source reference the engine silently stops resolving "
							+ "quoted messages, and every header falls back to the unavailable placeholder");
		}
	}

	@Nested
	class SyntaxTree {

		static Map<String, BBCodeConfig> grammar;
		static BBCodeRenderer bbCodeRenderer;


		@BeforeAll
		static void loadTheRealSeededEngine() {
			grammar = seededBBCodeGrammar();
			Renderer built = buildRenderer(mock(BBCodeDataProvider.class), messageResolver());
			bbCodeRenderer = built.bbCodeRenderer();
			built.useGrammar(grammar);
		}

		private String parse(String source) {
			return bbCodeRenderer.render(source, null, ContentScope.FORUM, Map.of());
		}

		@ParameterizedTest(name = "{0}")
		@MethodSource("charLoopRecognitionCases")
		void theCharLoopRecognitionRulesSurviveTheRewriteToAParser(String caseName, String input, String expected) {
			assertEquals(expected, parse(input),
					"the parser replaced a hand-rolled char loop whose recognition rules are load bearing: the "
							+ "measured alternative deletes raw HTML that sits next to bbcode in shipped templates, "
							+ "so recognition is preserved exactly rather than cleaned up");
		}

		static Stream<Arguments> charLoopRecognitionCases() {
			return Stream.of(
					arguments("aFailedCandidateSwallowsTheBracketThatEndedIt",
							"[xy[b]z[/b]", "[xy[b]z[/b]"),
					arguments("doubledBracketsAreTheDeFactoEscape",
							"[[b]bold[/b]", "[[b]bold[/b]"),
					arguments("aBracketInsideARecognizedTagsAttributesResumesAtThatBracket",
							"[b foo[i]x[/i]", "[b foo<span class=\"bb-code-i\">x</span>"),
					arguments("aRecognizedCodeWithNoClosingBracketStopsTheScan",
							"[b foo bar", "[b foo bar"),
					arguments("digitsAloneAreNotACode", "i am [42] years old", "i am [42] years old"),
					arguments("aTrailingBracketIsText", "a[", "a["),
					arguments("aTrailingCloserStartIsText", "x[/", "x[/"));
		}

		@ParameterizedTest(name = "{0}")
		@MethodSource("verbatimBodyCases")
		void aVerbatimBodyClosesOnTheSameScanThatFoundItsOpener(String caseName, String input, String expected) {
			assertEquals(expected, parse(input),
					"a process_content_flag=false body is closed by continuing the one scan, exactly as the char "
							+ "loop did; closing it with the rescanning tag reader instead deletes author content");
		}

		static Stream<Arguments> verbatimBodyCases() {
			return Stream.of(
					arguments("anEscapedCloserInsideAPreBodyIsBodyText",
							"[pre]text [[/pre]after", "<pre>text [[/pre]after</pre>"),
					arguments("aSmileyEndingInABracketDoesNotCloseACodeBody",
							"[code]:-[[/code]after", "<pre class=\"bb-code-code\">:-[[/code]after</pre>"),
					arguments("anEscapedCloserInsideATemplateBodyIsBodyText",
							"[template=x]body[[/template]tail",
							"<div class=\"bb-code-template\" data-resource=\"template\" "
									+ "data-template-name=\"x\">body[[/template]tail</div>"),
					arguments("anAttributedCloserStillClosesAVerbatimBody",
							"[pre]a[/pre foo]b", "<pre>a</pre>b"),
					arguments("theFirstMatchingCloserWinsWithNoDepthCounting",
							"[code]x[[/code]y[/code]", "<pre class=\"bb-code-code\">x[[/code]y</pre>"),
					arguments("nestedTagsInsideAVerbatimBodyStayText",
							"[code]a[b]b[/b]c[/code]", "<pre class=\"bb-code-code\">a[b]b[/b]c</pre>"),
					arguments("aSelfClosingCodeIsInertInsideAVerbatimBody",
							"[pre][hr]after[/pre]", "<pre>[hr]after</pre>"),
					arguments("markupInsideAVerbatimBodyIsEscaped",
							"[pre]<b>x</b>[/pre]", "<pre>&lt;b&gt;x&lt;/b&gt;</pre>"));
		}

		@ParameterizedTest(name = "{0}")
		@MethodSource("lineBreakRuleCases")
		void theLineBreakRuleRunsBeforeRecognitionAndIsUndoneInsideVerbatimBodies(String caseName, String input,
				String expected) {
			assertEquals(expected, parse(input),
					"the BBCODE line break rule is not a post-pass over text nodes: it changes which characters the "
							+ "scanner sees, and a verbatim body then rewrites break markup back to a newline per "
							+ "flushed chunk. Moving either half after parsing changes bytes");
		}

		static Stream<Arguments> lineBreakRuleCases() {
			return Stream.of(
					arguments("anAuthoredNewlineBecomesBreakMarkup", "one\ntwo", "one<br/>two"),
					arguments("anAuthoredNewlineInsideAnInlineTagBecomesBreakMarkup",
							"[b]a\nb[/b]", "<span class=\"bb-code-b\">a<br/>b</span>"),
					arguments("anAuthoredNewlineInsideAVerbatimBodyComesBackAsANewline",
							"[pre]a\nb[/pre]", "<pre>a\nb</pre>"),
					arguments("authorTypedBreakMarkupInsideAVerbatimBodyBecomesANewlineToo",
							"[pre]a<br/>b[/pre]", "<pre>a\nb</pre>"),
					arguments("aNewlineOpeningAFailedCandidateIsSplitAcrossTwoFlushedChunks",
							"[pre]a[\nb[/pre]", "<pre>a[&lt;br/&gt;b</pre>"),
					arguments("aNewlineInsideAuthorTypedBreakMarkupIsConsumedByTheBreakPattern",
							"[pre]<br\n>[/pre]", "<pre>&lt;br\n&gt;</pre>"));
		}

		@ParameterizedTest(name = "{0}")
		@MethodSource("contentPlaceholderCases")
		void theContentPlaceholderIsAnIndexCarriedOnTheTagThatOwnsIt(String caseName, String input, String expected) {
			assertEquals(expected, parse(input),
					"content-as-attribute is the mechanism that decides where a tag body goes; the index of "
							+ RenderedOutputComparison.CONTENT_PLACEHOLDER + " is recorded when the tag is expanded "
							+ "and carried on that tag, never searched for again in the accumulated output");
		}

		static Stream<Arguments> contentPlaceholderCases() {
			return Stream.of(
					arguments("anAuthorTypedPlaceholderIsNotClobberedByALaterTagsBody",
							"{{c}} and [img]body[/img]",
							"{{c}} and <span class=\"bb-code-img\"><img src=\"body\"/></span>"),
					arguments("anAuthorTypedPlaceholderSurvivesInsideAnEarlierTagToo",
							"[b]{{c}} inside[/b] [img]x[/img]",
							"<span class=\"bb-code-b\">{{c}} inside</span> "
									+ "<span class=\"bb-code-img\"><img src=\"x\"/></span>"),
					arguments("theSlotIsGlobalSoAnInnerTagsCloseFillsTheOuterTagsPlaceholder",
							"[url]http://x[b]y[/b][/url]",
							"<span class=\"bb-code-url\"><a href=\"y\">http://x"
									+ "<span class=\"bb-code-b\">y</span></a></span>"),
					arguments("anInnerContentAttributeTagTakesTheSlotAndLeavesTheOuterPlaceholderLiteral",
							"[url][img]x[/img][/url]",
							"<span class=\"bb-code-url\"><a href=\"{{c}}\">"
									+ "<span class=\"bb-code-img\"><img src=\"x\"/></span></a></span>"),
					arguments("anUnclosedContentAttributeTagLeavesItsPlaceholderLiteral",
							"[img]unclosed body",
							"<span class=\"bb-code-img\"><img src=\"{{c}}\"/>unclosed body</span>"),
					arguments("aBracketInsideAContentAttributeBodyLeaksThePartBeforeItAsVisibleText",
							"[img]my [photo] here[/img]",
							"<span class=\"bb-code-img\"><img src=\"] here\"/>my [photo</span>"));
		}

		@ParameterizedTest(name = "{0}")
		@MethodSource("closerCases")
		void everyCloserPathTheCharLoopHadIsReproduced(String caseName, String input, String expected) {
			assertEquals(expected, parse(input),
					"the char loop had five distinct closer paths and they disagree about whether the closer text "
							+ "is emitted, whether a stack level is popped and whose close markup is written");
		}

		static Stream<Arguments> closerCases() {
			return Stream.of(
					arguments("aCloserWithNoTagEverOpenedIsLeftPending", "[/b]stray", "[/b]stray"),
					arguments("aMismatchedCloserIsEmittedRawAndClosesTheInnermostTag",
							"[b]x[/i]", "<span class=\"bb-code-b\">x[/i]</span>"),
					arguments("aCloserAfterEverythingClosedIsEmittedRaw",
							"[b]x[/b][/b]", "<span class=\"bb-code-b\">x</span>[/b]"),
					arguments("aNonMatchingCloserInsideAVerbatimBodyStaysInTheBody",
							"[pre]a[/b]c[/pre]", "<pre>a[/b]c</pre>"),
					arguments("aMismatchedCloserDecrementsTheInstanceCountOfTheTagItActuallyPopped",
							"[b]x[/pre][pre]body[/pre]",
							"<span class=\"bb-code-b\">x[/pre]</span><pre>body</pre>"),
					arguments("everyTagLeftOpenIsClosedInnermostFirst",
							"[b][i]unclosed both",
							"<span class=\"bb-code-b\"><span class=\"bb-code-i\">unclosed both</span></span>"));
		}

		@ParameterizedTest(name = "{0}")
		@MethodSource("openerCases")
		void everyOpenerPathTheCharLoopHadIsReproduced(String caseName, String input, String expected) {
			assertEquals(expected, parse(input),
					"an opener either expands, or round trips as literal text without opening a level, or is a "
							+ "self-closing code that emits only its end markup");
		}

		static Stream<Arguments> openerCases() {
			return Stream.of(
					arguments("anUnmatchedAttributeModeRoundTripsAsLiteralTextAndOpensNothing",
							"[quote author=a link=b]t[/quote]", "[quote author=a link=b]t[/quote]"),
					arguments("aSelfClosingCodeEmitsOnlyItsEndMarkup", "[hr]after", "<hr/>after"),
					arguments("anOpenerInsideAVerbatimBodyIsInert",
							"[pre]a[b]c[/pre]", "<pre>a[b]c</pre>"),
					arguments("theImplicitItemMarkerOpensItsItemInsideTheOneParse",
							"[list][*]one[*]two[/list]", "<ul><li>one</li><li>two</li></ul>"));
		}

		@Test
		void aStrayCloserDoesNotTurnALaterVerbatimBodyIntoLiveMarkup() {
			String rendered = parse("[b][/code][/b]\n[code]<a href=\"https://evil.example\">click</a>[/code]");

			assertTrue(rendered.contains("&lt;a href="),
					"a verbatim body is escaped literal text; the stray closer decremented the open instance "
							+ "count of the code it named rather than of the tag it actually popped, so the count "
							+ "went negative and the later body took the pass-through branch: " + rendered);
			assertFalse(rendered.contains("<a href=\"https://evil.example\">"),
					"every later code block in the post becomes a live HTML region: clickable links, remote "
							+ "images and iframes, from one stray closer anywhere above them: " + rendered);
			assertFalse(rendered.contains("[/code]</pre>"),
					"and the closer itself leaks into the body it was supposed to end: " + rendered);
		}

		@Test
		void theSharedElementScannerReadsAClosingTagAsTheElementItCloses() {
			assertEquals(List.of("div", "div", "div", "div"),
					ContentLevel.everyElementNamedIn("<div class=\"bb-code-quote\">"
							+ "<div class=\"bb-code-quote-header\">Quote from x,</div>"
							+ "<div class=\"bb-code-quote-body\">"),
					"a scanner that does not step over the slash of a closing tag reads an empty element name, "
							+ "and every caller that asks whether a markup only names elements from a set then "
							+ "answers no for markup that closes anything");
		}

		@Test
		void theParserProducesANestedTreeAndNotAFlatRunOfMarkup() {
			BBCodeDocument document = BBCodeParser.parse("x[b]a[i]b[/i]c[/b]y", grammar);

			assertEquals(3, document.children().size(), "text, tag, text");
			BBCodeTag bold = (BBCodeTag) document.children().get(1);
			assertEquals(3, bold.children().size(), "the italic tag is a child of the bold tag, not a sibling");
			assertEquals(BBCodeTag.class, bold.children().get(1).getClass());
			assertEquals("b", bold.config().getCode());
			assertEquals("i", ((BBCodeTag) bold.children().get(1)).config().getCode(),
					"nesting has to be in the tree or the later markdown stage cannot ask a node for its ancestors");
		}

		@Test
		void everyTagNodeCarriesTheContentLevelOfTheMarkupItActuallyEmitted() {
			BBCodeDocument document = BBCodeParser.parse("[b]x[/b][quote]y[/quote]", grammar);

			assertEquals(ContentLevel.INLINE,
					((BBCodeTag) document.children().get(0)).contentLevel());
			assertEquals(ContentLevel.BLOCK,
					((BBCodeTag) document.children().get(1)).contentLevel(),
					"a node that does not know whether it is block or inline cannot be placed in a markdown "
							+ "document without guessing, and a wrong guess throws at render time");
		}

		@Test
		void everySeededCodeDeclaresOneContentLevelAcrossAllOfItsAttributeModes() {
			Map<ContentLevel, Integer> counts = new TreeMap<>();
			Map<String, Set<ContentLevel>> mixed = new TreeMap<>();
			for (Map.Entry<String, BBCodeConfig> entry : grammar.entrySet()) {
				Set<ContentLevel> declared =
						ContentLevel.everyContentLevelDeclaredBy(entry.getValue());
				if (declared.size() > 1)
					mixed.put(entry.getKey(), declared);
				else
					counts.merge(declared.iterator().next(), 1, Integer::sum);
			}

			assertEquals(Map.of(), mixed, "a mixing code has no single place in a document tree");
			assertEquals(21, counts.get(ContentLevel.BLOCK));
			assertEquals(28, counts.get(ContentLevel.INLINE));
			assertEquals(49, grammar.size());
		}

		@Test
		void aCodeWhoseOpenMarkupBuriesItsBlockElementBehindAnInlineOneIsStillBlock() {
			assertEquals(ContentLevel.BLOCK,
					ContentLevel.theContentLevelOfMarkup("<span><ul>", "</ul></span>"),
					"probing only the first '<' reads this shape as INLINE while it emits a <ul>; the across-mode "
							+ "guard cannot see a mismatch that sits inside one mode, so the misread reaches every "
							+ "reader of contentLevel(). No seeded code carries the shape any more - "
							+ "theSeededListCodeEmitsItsUnorderedListWithNoSpanAroundIt is what keeps it that way - so "
							+ "this literal is the only place the probe is still measured");
			assertEquals(ContentLevel.BLOCK,
					ContentLevel.theContentLevelOfMarkup("<span>", "</ul></span>"),
					"the close markup names the element the code actually opened, so it decides the level too");
			assertEquals(ContentLevel.INLINE,
					ContentLevel.theContentLevelOfMarkup("<span><em>", "</em></span>"));
		}

		@Test
		void theSeededListCodeEmitsItsUnorderedListWithNoSpanAroundIt() {
			BBCodeConfig list = grammar.get("LIST");
			List<String> wrappedMarkup = new ArrayList<>();
			if (list.getEndTag().contains("span"))
				wrappedMarkup.add("end tag " + list.getEndTag());
			for (Map.Entry<String, BBCodeAttributeMode> mode : list.getAttributeConfig().entrySet())
				if (mode.getValue().getOpenTag().contains("span") || mode.getValue().getCloseTag().contains("span"))
					wrappedMarkup.add("mode " + mode.getKey() + " "
							+ mode.getValue().getOpenTag() + mode.getValue().getCloseTag());

			assertEquals(List.of(), wrappedMarkup,
					"a <ul> is not phrasing content, so a <span> around it is not a legal content model. The "
							+ "config end tag is unreachable from the renderer - every seeded list mode carries "
							+ "its own close markup - so no rendered byte moves if only that one is reverted and "
							+ "this is the only guard that would notice: " + wrappedMarkup);
			assertEquals(Set.of(ContentLevel.BLOCK),
					ContentLevel.everyContentLevelDeclaredBy(list),
					"the markdown lane refuses to open a container for a code that emits block markup its "
							+ "expansion cannot legally hold, and it asks this classification to find out");
		}

		@Test
		void aGrammarWhoseCodeMixesBlockAndInlineIsRefusedAtLoadRatherThanDegradingSilently() {
			BBCodeConfig mixing = simpleTag("mix", "<span>", "</span>", true);
			BBCodeAttributeMode blockMode = new BBCodeAttributeMode();
			blockMode.setOpenTag("<div>");
			blockMode.setCloseTag("</div>");
			blockMode.setAttributes(new ArrayList<>());
			mixing.getAttributeConfig().put("=", blockMode);
			BBCodeDataProvider provider = mock(BBCodeDataProvider.class);
			doReturn(Map.of("MIX", mixing)).when(provider).getBBCodeConfig();
			BBCodeGrammarLoader loading = buildRenderer(provider, messageResolver()).service();

			InvalidBBCodeGrammarException refused =
					assertThrows(InvalidBBCodeGrammarException.class, loading::loadBBCodeConfig);

			assertTrue(refused.getMessage().contains("MIX"), refused.getMessage());
			assertTrue(refused.getMessage().contains("BLOCK") && refused.getMessage().contains("INLINE"),
					refused.getMessage());
		}

		@Test
		void aDocumentNestedFarDeeperThanAnyPostCanStillBeRendered() {
			int depth = 20000;
			String source = "[b]".repeat(depth) + "x" + "[/b]".repeat(depth);

			String rendered = assertDoesNotThrow(() -> parse(source),
					"the char loop was flat, so a hostile post could not overflow the stack; a tree walk that "
							+ "recurses per level reintroduces that as a 500");

			assertEquals(depth, rendered.split("<span class=\"bb-code-b\">", -1).length - 1);
		}

		@Test
		void theRendererIsAPureFunctionOfTheTreeSoTheSameTreeCanBeRenderedTwice() {
			BBCodeDocument document = BBCodeParser.parse("[b]a[/b][img]x[/img]", grammar);

			assertEquals(bbCodeRenderer.BBCodeToHtml(document), bbCodeRenderer.BBCodeToHtml(document),
					"a renderer that mutates the tree while walking it cannot be reused by a later stage that "
							+ "needs to render a subtree on its own");
		}
	}

	@Nested
	class Parity {

		static final Path GOLDEN_CORPUS = Path.of("src/test/resources/content/renderer/parity-corpus.tsv");

		static final String SWEEP_PROPERTY = "zfgbb.render.parity.sweep";

		static final String REGENERATE_PROPERTY = "zfgbb.regenerate.parity.corpus";

		static final String TEMPLATE_CASE_PREFIX = "r05.";

		static final int SWEEP_INPUTS_PER_SEED = 20000;

		static final int SMOKE_INPUTS_PER_SEED = 250;

		static final int SMOKE_SEEDS = 2;

		static final int LENS_CALIBRATION_INPUTS = 2000;

		static final Map<DivergenceFamily, Integer> SMOKE_WIDE_FAMILIES = Map.of(
				DivergenceFamily.CONTENT_ATTRIBUTE_BODY_BRACKET_LEAK, 17,
				DivergenceFamily.UNATTRIBUTED_VISIBLE_TEXT_CHANGE, 30,
				DivergenceFamily.ELEMENT_STRUCTURE_ONLY, 1,
				DivergenceFamily.ESCAPING_OR_ORDERING_ONLY, 1);

		static final Map<DivergenceFamily, Integer> SMOKE_HOSTILE_FAMILIES = Map.of(
				DivergenceFamily.CONTENT_ATTRIBUTE_BODY_BRACKET_LEAK, 11,
				DivergenceFamily.UNATTRIBUTED_VISIBLE_TEXT_CHANGE, 52,
				DivergenceFamily.ESCAPING_OR_ORDERING_ONLY, 1);

		static final Map<DivergenceFamily, Integer> SWEEP_WIDE_FAMILIES = Map.of(
				DivergenceFamily.CONTENT_ATTRIBUTE_BODY_BRACKET_LEAK, 4142,
				DivergenceFamily.LINE_BREAK_ONLY, 9,
				DivergenceFamily.UNATTRIBUTED_VISIBLE_TEXT_CHANGE, 11478,
				DivergenceFamily.ATTRIBUTE_VALUE_ONLY, 305,
				DivergenceFamily.ELEMENT_STRUCTURE_ONLY, 111,
				DivergenceFamily.ESCAPING_OR_ORDERING_ONLY, 215);

		static final Map<DivergenceFamily, Integer> SWEEP_HOSTILE_FAMILIES = Map.of(
				DivergenceFamily.LITERAL_CONTENT_PLACEHOLDER_HIJACK, 1,
				DivergenceFamily.CONTENT_ATTRIBUTE_BODY_BRACKET_LEAK, 3951,
				DivergenceFamily.LINE_BREAK_ONLY, 9,
				DivergenceFamily.UNATTRIBUTED_VISIBLE_TEXT_CHANGE, 17405,
				DivergenceFamily.ATTRIBUTE_VALUE_ONLY, 254,
				DivergenceFamily.ELEMENT_STRUCTURE_ONLY, 166,
				DivergenceFamily.ESCAPING_OR_ORDERING_ONLY, 173);

		static Map<String, BBCodeConfig> grammar;
		static List<SmileyToken> smilies;
		static Renderer renderer;

		@BeforeAll
		static void loadTheRealSeededEngine() {
			grammar = seededBBCodeGrammar();
			smilies = seededSmilies();
			BBCodeGrammarHolder grammarHolder = grammarHolder();
			RenderedTextEnricher enricher = enricher();
			ContentOutputSanitizer sanitizer = sanitizer(grammarHolder, enricher);
			enricher.registerSmilies(smilies);
			renderer = buildRenderer(mock(BBCodeDataProvider.class), grammarHolder, enricher, sanitizer,
					messageResolver());
			renderer.useGrammar(grammar);
		}

		static final String RAW_TEMPLATE_BODY_SHAPE = "shape:raw-template-body";

		static final String LIST_CONTENT_MODEL_DEFECT = "defect:list-content-model";

		static final String HOISTED_BREAK_DEFECT = "defect:hoisted-break-before-table";

		static final String QUOTE_SENTINEL_DEFECT = "defect:quote-sentinel-passthrough";

		static final String PRIVATE_USE_PASSTHROUGH_SHAPE = "shape:private-use-character-passthrough";

		static final String THE_PRIVATE_USE_CHARACTERS_THE_RETIRED_SPLICE_INDEXED_WITH = "\uE000\uE001";

		static final String CONTENT_PLACEHOLDER_DEFECT = "defect:content-placeholder-hijack";

		static final String NESTED_CONTAINER_MARKER_DEFECT = "defect:nested-container-item-marker";

		static final String BLOCK_HOSTS_ITS_OWN_CLOSER_DEFECT = "defect:markdown-block-hosts-its-own-closer";

		static final Set<String> KNOWN_LABELS = Set.of(RAW_TEMPLATE_BODY_SHAPE, LIST_CONTENT_MODEL_DEFECT,
				HOISTED_BREAK_DEFECT, QUOTE_SENTINEL_DEFECT, CONTENT_PLACEHOLDER_DEFECT,
				NESTED_CONTAINER_MARKER_DEFECT, PRIVATE_USE_PASSTHROUGH_SHAPE,
				BLOCK_HOSTS_ITS_OWN_CLOSER_DEFECT);

		static final Set<String> LABELS_OF_DEFECTS_ALREADY_FIXED = Set.of(CONTENT_PLACEHOLDER_DEFECT,
				LIST_CONTENT_MODEL_DEFECT, NESTED_CONTAINER_MARKER_DEFECT, QUOTE_SENTINEL_DEFECT,
				BLOCK_HOSTS_ITS_OWN_CLOSER_DEFECT);

		static final Pattern HOISTED_BREAK_BEFORE_TABLE = Pattern.compile("(?:<br\\s*/?>\\s*)+<table");

		record GoldenCase(String id, String input, String expected, List<String> labels, ContentFormat lane) {}

		record GoldenInput(ContentFormat lane, String source) {}

		static List<GoldenCase> readGoldenCorpus() throws IOException {
			List<GoldenCase> cases = new ArrayList<>();
			for (String line : Files.readAllLines(GOLDEN_CORPUS)) {
				if (line.isEmpty())
					continue;
				String[] fields = line.split("\t", -1);
				cases.add(new GoldenCase(fields[0], decode(fields[1]), decode(fields[2]),
						fields[3].isEmpty() ? List.of() : List.of(fields[3].split(",")),
						ContentFormat.valueOf(fields[4])));
			}
			return cases;
		}

		static String renderInLane(ContentFormat lane, String source) {
			return lane == ContentFormat.MARKDOWN ? renderer.renderMarkdown(source) : renderer.render(source);
		}

		static List<String> labelsFor(String id, String input, String expected) {
			List<String> labels = new ArrayList<>();
			if (id.startsWith(TEMPLATE_CASE_PREFIX))
				labels.add(RAW_TEMPLATE_BODY_SHAPE);
			if (rendersAListOutsideItsContentModel(expected))
				labels.add(LIST_CONTENT_MODEL_DEFECT);
			if (HOISTED_BREAK_BEFORE_TABLE.matcher(expected).find())
				labels.add(HOISTED_BREAK_DEFECT);
			if (theOutputCarriesAPrivateUseCharacterTheInputDidNot(input, expected))
				labels.add(QUOTE_SENTINEL_DEFECT);
			if (theOutputCarriesAPrivateUseCharacterTheAuthorTyped(input, expected))
				labels.add(PRIVATE_USE_PASSTHROUGH_SHAPE);
			if (input.contains(RenderedOutputComparison.CONTENT_PLACEHOLDER)
					&& !expected.contains(RenderedOutputComparison.CONTENT_PLACEHOLDER))
				labels.add(CONTENT_PLACEHOLDER_DEFECT);
			if (aRenderedContainerStillShowsAnItemMarker(expected))
				labels.add(NESTED_CONTAINER_MARKER_DEFECT);
			if (id.startsWith(MARKDOWN_CASE_PREFIX) && aRenderedBlockStillShowsItsOwnCloser(expected))
				labels.add(BLOCK_HOSTS_ITS_OWN_CLOSER_DEFECT);
			return labels;
		}

		static final Set<String> ELEMENTS_THAT_HOLD_THEIR_BODY_VERBATIM = Set.of("pre", "code");

		static boolean aRenderedBlockStillShowsItsOwnCloser(String expected) {
			for (Element rendered
					: Jsoup.parseBodyFragment(expected).select("[class*=bb-code-]")) {
				if (ELEMENTS_THAT_HOLD_THEIR_BODY_VERBATIM.contains(rendered.tagName()))
					continue;
				Optional<String> code = theBBCodeWhoseExpansionCarriesTheClassOf(rendered);
				if (code.isEmpty())
					continue;
				Element outsideItsVerbatimContent = rendered.clone();
				outsideItsVerbatimContent.select("pre, code").remove();
				if (outsideItsVerbatimContent.wholeText().toLowerCase(Locale.ROOT)
						.contains("[/" + code.get() + "]"))
					return true;
			}
			return false;
		}

		static Optional<String> theBBCodeWhoseExpansionCarriesTheClassOf(Element rendered) {
			for (String className : rendered.classNames()) {
				if (!className.startsWith("bb-code-"))
					continue;
				String named = className.substring("bb-code-".length());
				while (!named.isEmpty()) {
					if (grammar.containsKey(named.toUpperCase(Locale.ROOT)))
						return Optional.of(named.toLowerCase(Locale.ROOT));
					int lastSegment = named.lastIndexOf('-');
					named = lastSegment < 0 ? "" : named.substring(0, lastSegment);
				}
			}
			return Optional.empty();
		}

		static boolean aRenderedContainerStillShowsAnItemMarker(String expected) {
			for (Element container
					: Jsoup.parseBodyFragment(expected).select("ul, ol")) {
				Element outsideItsVerbatimContent = container.clone();
				outsideItsVerbatimContent.select("pre, code").remove();
				for (BBCodeGrammar.ImplicitItemExpansion declared
						: renderer.grammarHolder().current().implicitItemExpansions())
					if (outsideItsVerbatimContent.wholeText().contains(declared.marker()))
						return true;
			}
			return false;
		}

		static int occurrencesOfThePrivateUseCharactersIn(String text) {
			int occurrences = 0;
			for (int index = 0; index < text.length(); index++)
				if (THE_PRIVATE_USE_CHARACTERS_THE_RETIRED_SPLICE_INDEXED_WITH.indexOf(text.charAt(index)) >= 0)
					occurrences++;
			return occurrences;
		}

		static boolean theOutputCarriesAPrivateUseCharacterTheInputDidNot(String input, String expected) {
			return occurrencesOfThePrivateUseCharactersIn(expected)
					> occurrencesOfThePrivateUseCharactersIn(input);
		}

		static boolean theOutputCarriesAPrivateUseCharacterTheAuthorTyped(String input, String expected) {
			return occurrencesOfThePrivateUseCharactersIn(input) > 0
					&& occurrencesOfThePrivateUseCharactersIn(expected) > 0;
		}

		static boolean rendersAListOutsideItsContentModel(String expected) {
			for (Element list : Jsoup.parseBodyFragment(expected).select("ul"))
				if (list.parent() != null && "span".equals(list.parent().tagName()))
					return true;
			return false;
		}

		static String decode(String encoded) {
			return new String(Base64.getDecoder().decode(encoded), StandardCharsets.UTF_8);
		}

		static String encode(String plain) {
			return Base64.getEncoder().encodeToString(plain.getBytes(StandardCharsets.UTF_8));
		}

		static final String DEFECT_CASE_PREFIX = "d8.";

		static Map<String, String> contentPlaceholderCases() {
			Map<String, String> cases = new LinkedHashMap<>();
			cases.put(DEFECT_CASE_PREFIX + "literal-placeholder-then-img", "{{c}} and [img]body[/img]");
			cases.put(DEFECT_CASE_PREFIX + "literal-placeholder-then-youtube",
					"{{c}} then [youtube]dQw4w9WgXcQ[/youtube]");
			cases.put(DEFECT_CASE_PREFIX + "literal-placeholder-then-url", "{{c}} then [url]http://zfgc.com[/url]");
			cases.put(DEFECT_CASE_PREFIX + "literal-placeholder-then-bold", "{{c}} then [b]bold body[/b]");
			cases.put(DEFECT_CASE_PREFIX + "literal-placeholder-then-color",
					"{{c}} then [color=red]tinted body[/color]");
			cases.put(DEFECT_CASE_PREFIX + "literal-placeholder-inside-bold", "[b]{{c}} inside[/b] [img]x[/img]");
			cases.put(DEFECT_CASE_PREFIX + "literal-placeholder-alone", "{{c}} with no later tag");
			return cases;
		}

		static final String PAGE_ASSEMBLY_CASE_PREFIX = "d9.";

		static Map<String, String> pageAssemblyInputCases() {
			Map<String, String> cases = new LinkedHashMap<>();
			cases.put(PAGE_ASSEMBLY_CASE_PREFIX + "heading-with-a-greater-than-in-a-nested-attribute",
					"[h2]a <span title=\"x > y\">b</span>[/h2]");
			cases.put(PAGE_ASSEMBLY_CASE_PREFIX + "heading-class-that-reads-as-an-id",
					"<h2 class=\"grid=2\">Title</h2>");
			cases.put(PAGE_ASSEMBLY_CASE_PREFIX + "toc-marker-named-in-prose",
					"[h2]Only heading[/h2]\nprose mentioning bb-toc in passing");
			cases.put(PAGE_ASSEMBLY_CASE_PREFIX + "suppression-marker-named-in-prose",
					"[h1]A[/h1][h1]B[/h1][h1]C[/h1][h1]D[/h1]\nprose mentioning bb-notoc");
			return cases;
		}

		static final String SPECIAL_LINK_CASE_PREFIX = "d10.";

		static Map<String, String> legacySpecialLinkInputCases() {
			Map<String, String> cases = new LinkedHashMap<>();
			cases.put(SPECIAL_LINK_CASE_PREFIX + "special-href-plain", "[wiki=Special:Random]Random page[/wiki]");
			cases.put(SPECIAL_LINK_CASE_PREFIX + "special-href-mixed-case",
					"[wiki=Special:RecentChanges]Recent changes[/wiki]");
			cases.put(SPECIAL_LINK_CASE_PREFIX + "special-href-subpage",
					"[wiki=Special:WhatLinksHere/Ocarina]What links here[/wiki]");
			cases.put(SPECIAL_LINK_CASE_PREFIX + "special-href-query-string",
					"[url=/wiki/Special:AllPages?ns=Items]All pages[/url]");
			cases.put(SPECIAL_LINK_CASE_PREFIX + "special-href-fragment",
					"[url=/wiki/Special:Statistics#pages]Statistics[/url]");
			cases.put(SPECIAL_LINK_CASE_PREFIX + "special-href-page-with-no-app-route",
					"[wiki=Special:Preferences]Preferences[/wiki]");
			cases.put(SPECIAL_LINK_CASE_PREFIX + "special-path-in-body-text",
					"[list][*]the legacy path was  /wiki/Special:Random  and it is gone[/list]");
			cases.put(SPECIAL_LINK_CASE_PREFIX + "special-path-in-a-code-block-beside-a-rewritten-link",
					"[code]see /wiki/Special:Random for the old route[/code]"
							+ "[wiki=Special:Random]the new route[/wiki]");
			cases.put(SPECIAL_LINK_CASE_PREFIX + "special-path-in-an-attribute-that-is-not-an-href",
					"<span title=\"see /wiki/Special:Random\">tooltip</span>");
			cases.put(SPECIAL_LINK_CASE_PREFIX + "special-href-beside-a-foster-parented-smiley",
					"[table]:huh:[/table][wiki=Special:Random]r[/wiki]");
			return cases;
		}

		static final String IMPLICIT_ITEM_STRADDLE_CASE_PREFIX = "d11.";

		static Map<String, String> implicitItemStraddleInputCases() {
			Map<String, String> cases = new LinkedHashMap<>();
			cases.put(IMPLICIT_ITEM_STRADDLE_CASE_PREFIX + "list-item-holding-an-image",
					"[list][*]see [img]http://x/y.png[/img][*]next[/list]");
			cases.put(IMPLICIT_ITEM_STRADDLE_CASE_PREFIX + "list-item-holding-a-youtube-embed",
					"[list][*]watch [youtube]dQw4w9WgXcQ[/youtube][*]next[/list]");
			cases.put(IMPLICIT_ITEM_STRADDLE_CASE_PREFIX + "list-item-holding-a-code-sample",
					"[list][*]run [code]a[*]b[/code][*]next[/list]");
			cases.put(IMPLICIT_ITEM_STRADDLE_CASE_PREFIX + "list-item-holding-an-email-address",
					"[list][*]write [email]a@zfgc.com[/email][*]next[/list]");
			cases.put(IMPLICIT_ITEM_STRADDLE_CASE_PREFIX + "list-opened-after-a-code-sample",
					"[code]x[/code][list][*]one[*]two[/list]");
			cases.put(IMPLICIT_ITEM_STRADDLE_CASE_PREFIX + "list-closed-before-a-code-sample",
					"[list][*]one[*]two[/list][code]x[/code]");
			return cases;
		}

		static final String MARKDOWN_CASE_PREFIX = "md.";

		static Map<String, String> markdownLaneCases() {
			Map<String, String> cases = new LinkedHashMap<>();
			cases.put(MARKDOWN_CASE_PREFIX + "blk.quote.owns-its-line", "[quote]\nbody\n[/quote]");
			cases.put(MARKDOWN_CASE_PREFIX + "blk.quote.markdown-inside",
					"[quote]\n# heading\n\n- one\n- two\n\n**bold**\n[/quote]");
			cases.put(MARKDOWN_CASE_PREFIX + "blk.quote.single-line", "[quote]x[/quote]");
			cases.put(MARKDOWN_CASE_PREFIX + "blk.quote.soft-break-inside", "[quote]a\nb[/quote]");
			cases.put(MARKDOWN_CASE_PREFIX + "blk.quote.nested", "[quote]\nouter\n[quote]\ninner\n[/quote]\n[/quote]");
			cases.put(MARKDOWN_CASE_PREFIX + "blk.quote.in-blockquote", "> [quote]\n> body\n> [/quote]");
			cases.put(MARKDOWN_CASE_PREFIX + "blk.quote.in-list-item", "- [quote]body[/quote]");
			cases.put(MARKDOWN_CASE_PREFIX + "blk.quote.closer-with-trailing-text", "[quote]\nbody [/quote] tail");
			cases.put(MARKDOWN_CASE_PREFIX + "blk.quote.closer-owns-its-line-with-trailing-text",
					"[quote]\nbody\n[/quote] tail");
			cases.put(MARKDOWN_CASE_PREFIX + "blk.quote.text-before-the-opener-and-after-the-closer",
					"lead [quote]\nbody [/quote] tail");
			cases.put(MARKDOWN_CASE_PREFIX + "blk.quote.nested-closer-with-trailing-text",
					"[quote]\nouter\n[quote]\ninner [/quote] mid\n[/quote]");
			cases.put(MARKDOWN_CASE_PREFIX + "blk.quote.closer-with-trailing-markdown-after-it",
					"[quote]\nbody [/quote] **tail**");
			cases.put(MARKDOWN_CASE_PREFIX + "guard.closer-with-trailing-text-in-a-fence",
					"```\n[quote]\nbody [/quote] tail\n```");
			cases.put(MARKDOWN_CASE_PREFIX + "guard.closer-with-trailing-text-in-a-verbatim-block",
					"[code]\nbody [/code] tail\n[/code]");
			cases.put(MARKDOWN_CASE_PREFIX + "guard.closer-with-trailing-text-in-a-code-span",
					"[quote]\nbody `[/quote] not a closer` still inside\n[/quote] tail");
			cases.put(MARKDOWN_CASE_PREFIX + "blk.center.closer-with-trailing-text",
					"[align=center]\ncentered [/align] tail");
			cases.put(MARKDOWN_CASE_PREFIX + "blk.quote.never-closed", "[quote]\nbody with no closer");
			cases.put(MARKDOWN_CASE_PREFIX + "blk.center.markdown-inside", "[align=center]\ncentered **markdown**\n[/align]");
			cases.put(MARKDOWN_CASE_PREFIX + "blk.code.verbatim", "[code]\n**not bold** and [b]x[/b]\n[/code]");
			cases.put(MARKDOWN_CASE_PREFIX + "blk.table.single-line",
					"[table][tr][td]plain and [quote]inner[/quote][/td][/tr][/table]");
			cases.put(MARKDOWN_CASE_PREFIX + "blk.hr.keeps-trailing-text-in-a-paragraph", "[hr]after");
			cases.put(MARKDOWN_CASE_PREFIX + "blk.paragraph-before-block", "para\n[quote]\nbody\n[/quote]");
			cases.put(MARKDOWN_CASE_PREFIX + "inline.alongside-markdown", "some [b]bold[/b] and **markdown**");
			cases.put(MARKDOWN_CASE_PREFIX + "inline.soft-break-is-not-a-break", "[b]line one\nline two[/b]");
			cases.put(MARKDOWN_CASE_PREFIX + "inline.text-is-escaped-by-commonmark", "a < b [b]x[/b]");
			cases.put(MARKDOWN_CASE_PREFIX + "inline.img", "[img]http://zfgc.com/a.png[/img]");
			cases.put(MARKDOWN_CASE_PREFIX + "inline.smiley", "hello :P");
			cases.put(MARKDOWN_CASE_PREFIX + "guard.block-under-emphasis",
					"A **bold with [quote]x[/quote] inside** word.");
			cases.put(MARKDOWN_CASE_PREFIX + "guard.block-under-heading", "# Heading with [quote]x[/quote] here");
			cases.put(MARKDOWN_CASE_PREFIX + "guard.paragraph-split-around-a-literal-run",
					"[table][tr][td]**md** and [quote]inner[/quote][/td][/tr][/table]");
			cases.put(MARKDOWN_CASE_PREFIX + "guard.no-paragraph-interruption",
					"A **bold with\n[quote]\nx\n[/quote]\ninside** word.");
			cases.put(MARKDOWN_CASE_PREFIX + "guard.unclosed-inline-code", "[b]unclosed bold and more text");
			cases.put(MARKDOWN_CASE_PREFIX + "guard.orphan-closer", "orphan [/b] close");
			cases.put(MARKDOWN_CASE_PREFIX + "guard.crossed-codes", "[b]a[i]b[/b]c[/i]");
			cases.put(MARKDOWN_CASE_PREFIX + "guard.code-span", "`[b]literal[/b]`");
			cases.put(MARKDOWN_CASE_PREFIX + "guard.fence", "```\n[quote]\nx\n[/quote]\n```");
			cases.put(MARKDOWN_CASE_PREFIX + "guard.indented-code", "    [b]literal[/b]\n");
			cases.put(MARKDOWN_CASE_PREFIX + "list.single-line", "[list][*]one[*]two[/list]");
			cases.put(MARKDOWN_CASE_PREFIX + "list.multi-line", "[list]\n[*]one\n[*]two\n[/list]");
			cases.put(MARKDOWN_CASE_PREFIX + "list.explicit-items", "[list]\n[li]one[/li]\n[/list]");
			cases.put(MARKDOWN_CASE_PREFIX + "list.unpaired-marker", "[list][*]one[/list]");
			cases.put(MARKDOWN_CASE_PREFIX + "list.styled", "[list type=decimal]\n[*]a\n[*]b\n[/list]");
			cases.put(MARKDOWN_CASE_PREFIX + "list.nested", "[list]\n[*]a\n[list]\n[*]b\n[/list]\n[/list]");
			cases.put(MARKDOWN_CASE_PREFIX + "list.closer-mid-line", "[list][*]a[/list] tail");
			cases.put(MARKDOWN_CASE_PREFIX + "list.bbcode-inside-an-item", "[list]\n[*][b]bold[/b]\n[/list]");
			cases.put(MARKDOWN_CASE_PREFIX + "list.item-holding-an-image",
					"[list][*]see [img]http://x/y.png[/img][*]next[/list]");
			cases.put(MARKDOWN_CASE_PREFIX + "list.item-holding-a-code-sample",
					"[list][*]run [code]a[*]b[/code][*]next[/list]");
			cases.put(MARKDOWN_CASE_PREFIX + "list.markdown-inside-an-item", "[list]\n[*]**bold**\n[/list]");
			cases.put(MARKDOWN_CASE_PREFIX + "inline.list-item-marker-in-prose",
					"footnote [*] marker with [b]bold[/b]");
			cases.put(MARKDOWN_CASE_PREFIX + "guard.list-item-marker-in-a-fence", "```\n[list][*]x[/list]\n```");
			cases.put(MARKDOWN_CASE_PREFIX + "guard.list-item-marker-in-a-code-span", "`[list][*]x[/list]`");
			cases.put(MARKDOWN_CASE_PREFIX + "guard.list-item-marker-in-a-verbatim-block",
					"[code]\n[list][*]x[/list]\n[/code]");
			cases.put(MARKDOWN_CASE_PREFIX + "guard.emphasis-around-a-list-item-marker", "*emph with [*] inside*");
			cases.put(MARKDOWN_CASE_PREFIX + "blk.grid.markdown-inside", "[grid=2]\n# H\n[/grid]");
			cases.put(MARKDOWN_CASE_PREFIX + "blk.code.escapes-its-body",
					"[code]\n<script>alert(1)</script>\n[/code]");
			cases.put(MARKDOWN_CASE_PREFIX + "blk.quote.nested-at-a-block-boundary",
					"[quote]\nouter\n\n[quote]\ninner\n[/quote]\n[/quote]");
			cases.put(MARKDOWN_CASE_PREFIX + "guard.orphan-list-item-refused", "[li]\n# H\n[/li]");
			cases.put(MARKDOWN_CASE_PREFIX + "guard.orphan-table-cell-refused", "[td]\n# H\n\npara\n[/td]");
			cases.put(MARKDOWN_CASE_PREFIX + "guard.heading-container-refused", "[h1]\n# H\n[/h1]");
			cases.put(MARKDOWN_CASE_PREFIX + "guard.fence-inside-a-block", "[quote]\n```\n[/quote]\n```\n[/quote]");
			cases.put(MARKDOWN_CASE_PREFIX + "guard.indented-code-opens-no-block",
					"    [quote]\n    body\n    [/quote]");
			cases.put(MARKDOWN_CASE_PREFIX + "guard.inline-code-opens-no-block", "[b]\ntext\n[/b]");
			cases.put(MARKDOWN_CASE_PREFIX + "leak.run-split-across-a-block-boundary",
					"para\n[quote]\n# H\n\n- a\n[/quote]");
			cases.put(MARKDOWN_CASE_PREFIX + "leak.inline-code-split-by-a-heading", "[b]\n# H\n[/b]");
			cases.put(MARKDOWN_CASE_PREFIX + "leak.indented-body-hides-the-closer",
					"[quote]\n    four-space indent\n[/quote]");
			cases.put(MARKDOWN_CASE_PREFIX + "leak.hard-line-break-before-a-block", "a  \n[quote]x[/quote]");
			cases.put(MARKDOWN_CASE_PREFIX + "md.link.is-untouched", "See [the docs](/wiki/Docs) for more.");
			cases.put(MARKDOWN_CASE_PREFIX + "md.heading-and-emphasis", "# Title\n\nSome **bold** text.");
			cases.put(MARKDOWN_CASE_PREFIX + "smiley.cool-opens-the-line", "8) that one");
			cases.put(MARKDOWN_CASE_PREFIX + "smiley.angry-opens-the-line", ">:( grumpy");
			cases.put(MARKDOWN_CASE_PREFIX + "smiley.evil-cheesy-opens-the-line", ">:D evil");
			cases.put(MARKDOWN_CASE_PREFIX + "smiley.evil-smiley-opens-the-line", ">:) evil");
			cases.put(MARKDOWN_CASE_PREFIX + "smiley.cool-mid-line", "that is 8) cool");
			cases.put(MARKDOWN_CASE_PREFIX + "smiley.angry-mid-line", "he was >:( today");
			cases.put(MARKDOWN_CASE_PREFIX + "smiley.evil-cheesy-mid-line", "he was >:D today");
			cases.put(MARKDOWN_CASE_PREFIX + "smiley.evil-smiley-mid-line", "he was >:) today");
			cases.put(MARKDOWN_CASE_PREFIX + "smiley.ordered-list-starting-at-eight", "8. first\n9. second");
			cases.put(MARKDOWN_CASE_PREFIX + "smiley.block-quote-still-opens-on-its-marker", "> quoted\n> more");
			cases.put(MARKDOWN_CASE_PREFIX + "smiley.opens-a-paragraph-continuation-line", "para\n>:( grumpy");
			cases.put(MARKDOWN_CASE_PREFIX + "smiley.opens-an-indented-line",
					"  8) two spaces in\n\n    8) four spaces in");
			cases.put(MARKDOWN_CASE_PREFIX + "smiley.opens-a-line-with-a-word-attached", ">:Days is not a smiley");
			cases.put(MARKDOWN_CASE_PREFIX + "smiley.inside-a-fence-stays-verbatim",
					"```\n8) fenced\n>:( fenced\n```");
			cases.put(MARKDOWN_CASE_PREFIX + "smiley.inside-a-verbatim-bbcode-block",
					"[code]\n8) verbatim\n>:( verbatim\n[/code]");
			cases.put(MARKDOWN_CASE_PREFIX + "smiley.behind-a-container-prefix-is-a-known-limit",
					"> 8) quoted\n\n- 8) item");
			cases.put(MARKDOWN_CASE_PREFIX + "smiley.forged-mask-marker",
					"\uE0020\uE003 forged\n\uE00299\uE003 out of range\n8) real");
			cases.put(MARKDOWN_CASE_PREFIX + "blk.quote.attributed-owns-its-line",
					"[quote author=Bob]\nbody\n[/quote]");
			cases.put(MARKDOWN_CASE_PREFIX + "blk.quote.attributed-markdown-inside",
					"[quote author=Bob]\n# heading\n\n- one\n- two\n\n**bold**\n[/quote]");
			cases.put(MARKDOWN_CASE_PREFIX + "blk.center.closer-after-an-inline-code-left-open",
					"[align=center]\n[b]bold [/align] tail\n\nafter the block");
			return cases;
		}

		static Map<String, String> templateCasesFromSeed() {
			Map<String, String> cases = new LinkedHashMap<>();
			for (Map.Entry<String, String> body : seededBBCodeTemplateBodies().entrySet()) {
				cases.put(TEMPLATE_CASE_PREFIX + body.getKey() + ".as-seeded", body.getValue());
				cases.put(TEMPLATE_CASE_PREFIX + body.getKey() + ".authored-multi-line",
						body.getValue().replace("[/tr]", "[/tr]\n").replace("[/table]", "\n[/table]"));
			}
			return cases;
		}

		static Stream<Arguments> goldenCorpusCases() throws IOException {
			return readGoldenCorpus().stream()
					.map(row -> arguments(row.id(), row.input(), row.expected(), row.labels(), row.lane()));
		}

		@ParameterizedTest(name = "{0}")
		@MethodSource("goldenCorpusCases")
		void everyGoldenCaseStillRendersItsCheckedInBytes(String id, String input, String expected,
				List<String> labels, ContentFormat lane) {
			String rendered = renderInLane(lane, input);

			RenderedOutputComparison comparison =
					RenderedOutputComparison.of(grammar, input, expected, rendered);
			assertTrue(comparison.byteIdentical(),
					"the checked-in golden bytes for '" + id + "' are the contract this harness exists to hold; "
							+ "regenerate them only in a reviewed commit (-D" + REGENERATE_PROPERTY + "=true).\n"
							+ describeLabels(labels) + comparison.describe() + "\n  first divergence: "
							+ comparison.firstDivergingByteOffset().orElse("none"));
		}

		static String describeLabels(List<String> labels) {
			if (labels.isEmpty())
				return "  this row pins ordinary expected output; a change here is a plain regression.\n";
			StringBuilder described = new StringBuilder();
			for (String label : labels)
				described.append("  ").append(label).append(": ").append(switch (label) {
					case RAW_TEMPLATE_BODY_SHAPE -> "the input is a seeded template body fed to parseText RAW. "
							+ "ContentRenderingService.renderWithTemplates expands jmustache FIRST, so production never "
							+ "renders this shape; the row characterises the body, it does not cover the template.";
					case LIST_CONTENT_MODEL_DEFECT -> "the expected value pins <span><ul> from "
							+ "R__03_bbcodes.sql attribute modes 22 and 68, which is not a legal HTML content "
							+ "model. This stage dropped the <span> from the seed, so no row may exhibit it "
							+ "again; a row that does means the seed was reverted.";
					case HOISTED_BREAK_DEFECT -> "the expected value pins <br> elements the HTML parser hoisted "
							+ "out of a <table>, because the authored newline landed between table rows.";
					case QUOTE_SENTINEL_DEFECT -> "the expected value pins a private-use character the engine "
							+ "itself emitted, which is the signature of the retired splice: it indexed pulled "
							+ "bodies with private-use characters and substituted them back after rendering. "
							+ "Resolution happens on the tree now, so no row may exhibit it again.";
					case PRIVATE_USE_PASSTHROUGH_SHAPE -> "the input carries a private-use character the author "
							+ "typed and the expected value carries it back. That is now ordinary text handling: "
							+ "the retired splice made these characters forgeable, and the row pins that the "
							+ "engine neither acts on them nor eats them.";
					case NESTED_CONTAINER_MARKER_DEFECT -> "the expected value pins an implicit item marker "
							+ "rendered as text inside the container element it belongs to, because the pre-pass "
							+ "only ever rewrote the innermost container body. The marker opens its item inside "
							+ "the one parse now, so no row may exhibit it again.";
					case BLOCK_HOSTS_ITS_OWN_CLOSER_DEFECT -> "the expected value pins the markdown lane hosting a "
							+ "block whose author wrote its closer with text after it on the same line: "
							+ "BBCodeBlockParser.tryContinue only recognised a closer that owned its whole line, so "
							+ "the closer and everything after it stayed inside the block as text. The block closes "
							+ "at the closer now, so no row may exhibit it again.";
					case CONTENT_PLACEHOLDER_DEFECT -> "the expected value pins D8/D19: "
							+ "BBCodeGrammarLoader.java:295 scans the whole accumulated buffer for {{c}}, so a later "
							+ "tag's body clobbers an author-typed literal placeholder.";
					default -> "unknown label";
				}).append('\n');
			described.append("  a later stage owning that defect must regenerate this row deliberately.\n");
			return described.toString();
		}

		@Test
		void bothRenderingLanesAreRepresentedInTheGoldenCorpus() throws IOException {
			Map<ContentFormat, Integer> rowsPerLane = new TreeMap<>();
			Set<String> missing = new LinkedHashSet<>(markdownLaneCases().keySet());
			for (GoldenCase row : readGoldenCorpus()) {
				rowsPerLane.merge(row.lane(), 1, Integer::sum);
				missing.remove(row.id());
			}

			assertTrue(missing.isEmpty(),
					"the markdown lane renders bbcode through a different mechanism than the bbcode lane, so a "
							+ "corpus that only pins one of them certifies half the engine: " + missing);
			for (ContentFormat lane : ContentFormat.values())
				assertTrue(rowsPerLane.getOrDefault(lane, 0) > 0, lane + " has no golden row: " + rowsPerLane);
		}

		@Test
		void everySeededTemplateBodyIsCoveredByTheGoldenCorpus() throws IOException {
			Set<String> goldenIds = new LinkedHashSet<>();
			for (GoldenCase row : readGoldenCorpus())
				goldenIds.add(row.id());

			Set<String> missing = new LinkedHashSet<>(templateCasesFromSeed().keySet());
			missing.removeAll(goldenIds);

			assertTrue(missing.isEmpty(),
					"R__05_content_templates.sql seeds BBCODE bodies the golden corpus has never rendered, so a "
							+ "change to the renderer could break a shipped template with nothing going red: " + missing);
		}

		@Test
		void everyPageAssemblyDefectInputKeepsItsGoldenRow() throws IOException {
			Set<String> goldenIds = new LinkedHashSet<>();
			for (GoldenCase row : readGoldenCorpus())
				goldenIds.add(row.id());

			Set<String> missing = new LinkedHashSet<>(pageAssemblyInputCases().keySet());
			missing.removeAll(goldenIds);

			assertTrue(missing.isEmpty(),
					"CmsPageRenderer reads the sanitiser's serialised bytes, so the shapes that used to defeat "
							+ "its regex scanners are only pinned while these rows exist; a regeneration that "
							+ "drops them turns nothing red: " + missing);
		}

		@Test
		void everyLegacySpecialLinkInputKeepsItsGoldenRow() throws IOException {
			Set<String> goldenIds = new LinkedHashSet<>();
			for (GoldenCase row : readGoldenCorpus())
				goldenIds.add(row.id());

			Set<String> missing = new LinkedHashSet<>(legacySpecialLinkInputCases().keySet());
			missing.removeAll(goldenIds);

			assertTrue(missing.isEmpty(),
					"legacy Special: link normalisation is one step of the sanitiser's per-element pass, so its "
							+ "href-only reach -- prose, code, a title attribute and an off-site absolute url all "
							+ "left alone -- is only pinned while these rows exist; a regeneration that drops them "
							+ "turns nothing red: " + missing);
		}

		@Test
		void everyImplicitItemStraddleInputKeepsItsGoldenRow() throws IOException {
			Set<String> goldenIds = new LinkedHashSet<>();
			for (GoldenCase row : readGoldenCorpus())
				goldenIds.add(row.id());

			Set<String> missing = new LinkedHashSet<>(implicitItemStraddleInputCases().keySet());
			missing.removeAll(goldenIds);

			assertTrue(missing.isEmpty(),
					"implicit [*] expansion runs over the raw source before the tree is built, so the one shape "
							+ "that used to defeat it -- a container whose body straddles a literal span -- is only "
							+ "pinned while these rows exist; a regeneration that drops them turns nothing red: "
							+ missing);
		}

		@Test
		void everySeededBBCodeIsReachableFromBothFuzzAlphabets() {
			for (BBCodeFuzzGenerator.Alphabet alphabet : BBCodeFuzzGenerator.Alphabet.values()) {
				String[] tokens = BBCodeFuzzGenerator.tokensFor(alphabet, grammar, smilies);
				Set<String> uncovered = new LinkedHashSet<>(grammar.keySet());
				uncovered.removeAll(BBCodeFuzzGenerator.codesCoveredBy(tokens, grammar));

				assertTrue(uncovered.isEmpty(),
						"a fuzz alphabet that cannot emit a code can never find a defect in it; " + alphabet
								+ " misses " + uncovered);
			}
		}

		static Set<String> codesThatArePrefixesOfLongerCodes() {
			Set<String> prefixes = new LinkedHashSet<>();
			for (String code : new TreeMap<>(grammar).keySet())
				for (String longer : grammar.keySet())
					if (!code.equals(longer) && longer.startsWith(code))
						prefixes.add(code);
			return prefixes;
		}

		@Test
		void theCoverageGuardStopsReportingACodeWhoseOwnTokensAreRemoved() {
			String[] tokens = BBCodeFuzzGenerator.tokensFor(
					BBCodeFuzzGenerator.Alphabet.SEEDED_GRAMMAR_WIDE, grammar, smilies);
			Set<String> atRiskCodes = codesThatArePrefixesOfLongerCodes();

			assertFalse(atRiskCodes.isEmpty(),
					"this test only proves something while some seeded code is a prefix of a longer one");

			for (String shortCode : atRiskCodes) {
				Pattern opensThisCode = Pattern.compile(
						"\\[" + shortCode.toLowerCase(Locale.ROOT) + "[\\]=\\s]", Pattern.DOTALL);
				String[] withoutThisCode = Arrays.stream(tokens)
						.filter(token -> !opensThisCode.matcher(token.toLowerCase(Locale.ROOT)).find())
						.toArray(String[]::new);

				assertTrue(withoutThisCode.length < tokens.length,
						"the filter removed nothing, so this proves nothing about " + shortCode);
				assertFalse(BBCodeFuzzGenerator.codesCoveredBy(withoutThisCode, grammar).contains(shortCode),
						shortCode + " is a prefix of longer seeded codes, so a coverage guard that matches by "
								+ "substring reports it covered by [img]/[board=]/[url]/[size=3]; the guard must "
								+ "match the open tag exactly or 57/57 is a number that cannot go down");
			}
		}

		@Test
		void everyCheckedInLabelStillMatchesWhatTheRenderedBytesShow() throws IOException {
			List<String> drifted = new ArrayList<>();
			Set<String> unknown = new LinkedHashSet<>();
			for (GoldenCase row : readGoldenCorpus()) {
				List<String> observed = labelsFor(row.id(), row.input(), row.expected());
				if (!observed.equals(row.labels()))
					drifted.add(row.id() + " checked in " + row.labels() + " but shows " + observed);
				unknown.addAll(row.labels());
			}
			unknown.removeAll(KNOWN_LABELS);

			assertTrue(unknown.isEmpty(), "the corpus carries labels outside the known vocabulary: " + unknown);
			assertTrue(drifted.isEmpty(),
					"a labelled row stopped exhibiting the thing its label names, or an unlabelled row started "
							+ "exhibiting one; either way the corpus no longer says what it pins: " + drifted);
		}

		@Test
		void theKnownDefectsAreActuallyPresentInTheCorpus() throws IOException {
			Map<String, Integer> labelCounts = new TreeMap<>();
			for (GoldenCase row : readGoldenCorpus())
				for (String label : row.labels())
					labelCounts.merge(label, 1, Integer::sum);

			for (String label : KNOWN_LABELS) {
				if (LABELS_OF_DEFECTS_ALREADY_FIXED.contains(label))
					continue;
				assertTrue(labelCounts.getOrDefault(label, 0) > 0,
						"the corpus claims to characterise " + label + " but carries no row that exhibits it, "
								+ "so a stage that changes the behaviour would turn nothing red: " + labelCounts);
			}
		}

		@Test
		void aDefectThisStageFixedCarriesNoCorpusRowButKeepsItsDetector() throws IOException {
			Map<String, Integer> labelCounts = new TreeMap<>();
			for (GoldenCase row : readGoldenCorpus())
				for (String label : row.labels())
					labelCounts.merge(label, 1, Integer::sum);

			for (String label : LABELS_OF_DEFECTS_ALREADY_FIXED) {
				assertTrue(KNOWN_LABELS.contains(label),
						"a fixed defect keeps its place in the vocabulary so a re-appearance reads as drift "
								+ "rather than as an unknown label: " + label);
				assertEquals(0, labelCounts.getOrDefault(label, 0),
						label + " names a defect this stage fixed, so no row may still exhibit it; the detector "
								+ "stays live in labelsFor, which means a regression turns "
								+ "everyCheckedInLabelStillMatchesWhatTheRenderedBytesShow red: " + labelCounts);
			}
		}

		@Test
		void theSeededGrammarLoadsEveryEnabledCodeThroughTheRealDataProvider() {
			assertEquals(49, grammar.size(),
					"the harness measures the real seeded grammar; if R__03_bbcodes.sql gains or loses a code the "
							+ "fuzz coverage claim in CONTRIBUTING.md is stale: " + new TreeMap<>(grammar).keySet());
			assertEquals(18, smilies.size(),
					"smiley rewriting runs on every sanitize, so the golden bytes depend on the seeded smiley set");
		}

		private String sortedCharacters(String text) {
			char[] characters = text.toCharArray();
			Arrays.sort(characters);
			return new String(characters);
		}

		private String mutateByDroppingOneVisibleCharacter(String html) {
			for (int index = 1; index < html.length(); index++)
				if (Character.isLetter(html.charAt(index)) && html.charAt(index - 1) == '>')
					return html.substring(0, index) + html.substring(index + 1);
			return html;
		}

		private String mutateByDuplicatingLinkTextIntoTheDocument(String html) {
			return html.replaceFirst("(<a [^>]*href=\"([^\"]+)\"[^>]*>)", "$1$2");
		}

		private String mutateByChangingOneAttributeValue(String html) {
			return html.replaceFirst("(<[a-zA-Z][^>]*?=\")([^\"]*)(\")", "$1$2-mutated$3");
		}

		private String mutateByDroppingOneLineBreakElement(String html) {
			return html.replaceFirst("<br\\s*/?>", "");
		}

		private String mutateByReplacingATextNewlineWithASpace(String html) {
			return html.replaceFirst("\n", " ");
		}

		@Test
		void theByteLensIsTheOnlyOneThatFiresOnAPureEscapingChange() {
			String expected = "<span>a&amp;b</span>";
			String actual = "<span>a&#38;b</span>";

			RenderedOutputComparison comparison = RenderedOutputComparison.of(grammar, "a&b", expected, actual);

			assertFalse(comparison.byteIdentical(), "the bytes differ, so the byte lens must fire");
			assertTrue(comparison.visibleTextIdentical(), "both entity forms decode to the same visible text");
			assertTrue(comparison.attributeSignaturesMatch(), "neither side carries an attribute");
			assertTrue(comparison.whitespaceLayoutMatches(), "neither side carries a line break");
			assertEquals(DivergenceFamily.ESCAPING_OR_ORDERING_ONLY, comparison.family(),
					"a divergence no structural lens can see must be named as the instrument's blind spot, "
							+ "not silently dropped");
		}

		@Test
		void aRendererThatDropsOneCharacterIsCaughtByTheVisibleTextLens() {
			String expected = renderer.render("[b]visible[/b]");
			String actual = mutateByDroppingOneVisibleCharacter(expected);

			RenderedOutputComparison comparison =
					RenderedOutputComparison.of(grammar, "[b]visible[/b]", expected, actual);

			assertNotEquals(expected, actual, "the mutation must actually change the output or it proves nothing");
			assertEquals("v", comparison.visibleTextLost(), "the visible-text lens must name the lost character");
			assertEquals("", comparison.visibleTextGained());
			assertTrue(comparison.attributeSignaturesMatch(),
					"dropping a text character must not be reported as an attribute change");
			assertTrue(comparison.whitespaceLayoutMatches(),
					"dropping a non-whitespace character must not be reported as a line-break change");
		}

		@Test
		void aRendererThatAddsCharactersIsCaughtByTheVisibleTextGainLens() {
			String source = "[url]http://zfgc.com[/url]";
			String expected = renderer.render(source).replaceFirst(">http://zfgc.com<", "><");
			String actual = mutateByDuplicatingLinkTextIntoTheDocument(expected);

			RenderedOutputComparison comparison = RenderedOutputComparison.of(grammar, source, expected, actual);

			assertNotEquals(expected, actual, "the mutation must actually change the output or it proves nothing");
			assertEquals("", comparison.visibleTextLost());
			assertFalse(comparison.visibleTextGained().isEmpty(),
					"measuring loss alone misses a renderer that duplicates a href into the document body");
			assertEquals(DivergenceFamily.VISIBLE_TEXT_DUPLICATED_INTO_ATTRIBUTE, comparison.family(),
					"text that also appears in an attribute value is a duplication, not an unexplained gain");
		}

		@Test
		void aRendererThatChangesAnAttributeValueIsCaughtOnlyByTheAttributeLens() {
			String source = "[url=http://zfgc.com]link[/url]";
			String expected = renderer.render(source);
			String actual = mutateByChangingOneAttributeValue(expected);

			RenderedOutputComparison comparison = RenderedOutputComparison.of(grammar, source, expected, actual);

			assertNotEquals(expected, actual, "the mutation must actually change the output or it proves nothing");
			assertTrue(comparison.visibleTextIdentical(),
					"an attribute value is invisible to text(), which is exactly why the attribute lens exists");
			assertTrue(comparison.whitespaceLayoutMatches());
			assertFalse(comparison.attributeSignaturesMatch(),
					"href-present versus href-mutated is clickable versus wrong-destination");
			assertEquals(DivergenceFamily.ATTRIBUTE_VALUE_ONLY, comparison.family());
		}

		@Test
		void aRendererThatDropsALineBreakIsCaughtInsteadOfDiscardedAsBlank() {
			String expected = "<span>one<br/>two</span>";
			String actual = mutateByDroppingOneLineBreakElement(expected);

			RenderedOutputComparison comparison = RenderedOutputComparison.of(grammar, "one\ntwo", expected, actual);

			assertNotEquals(expected, actual, "the mutation must actually change the output or it proves nothing");
			assertEquals("\n", comparison.visibleTextLost(),
					"the whole residual is blank, so gating classification on isBlank() scores this loss as zero");
			assertTrue(comparison.attributeSignaturesMatch(),
					"a dropped line break must not be reported as an attribute change");
			assertFalse(comparison.whitespaceLayoutMatches(),
					"every BBHtml render site sets whitespace-pre-wrap, so losing a line is user-visible: "
							+ comparison.whitespaceLayoutDifference());
			assertEquals(DivergenceFamily.LINE_BREAK_ONLY, comparison.family());
		}

		@Test
		void aRendererThatMovesWhitespaceIsCaughtOnlyByTheWhitespaceLayoutLens() {
			String expected = "<span>ab\ncd ef</span>";
			String actual = "<span>ab cd\nef</span>";

			RenderedOutputComparison comparison =
					RenderedOutputComparison.of(grammar, "ab\ncd ef", expected, actual);

			assertTrue(comparison.visibleTextIdentical(),
					"a character multiset cannot see whitespace move, which is the whole reason this lens exists");
			assertTrue(comparison.attributeSignaturesMatch());
			assertTrue(comparison.elementStructuresMatch());
			assertFalse(comparison.whitespaceLayoutMatches(),
					"under whitespace-pre-wrap these render as different lines: "
							+ comparison.whitespaceLayoutDifference());
			assertEquals(DivergenceFamily.WHITESPACE_POSITION_ONLY, comparison.family());
		}

		@Test
		void aRendererThatReparentsAnElementIsCaughtByTheStructureLens() {
			String expected = "<b><i>x</i></b>";
			String actual = "<b></b><i>x</i>";

			RenderedOutputComparison comparison = RenderedOutputComparison.of(grammar, "[b][i]x[/i][/b]",
					expected, actual);

			assertTrue(comparison.visibleTextIdentical(), "the same characters are visible either way");
			assertTrue(comparison.attributeSignaturesMatch(), "neither side carries an attribute");
			assertTrue(comparison.whitespaceLayoutMatches(), "neither side breaks a line");
			assertFalse(comparison.elementStructuresMatch(),
					"a flat document-order tag list reads both of these as b/i, so nesting has to be encoded or "
							+ "the lens certifies a reparenting as identical: "
							+ RenderedOutputComparison.elementStructure(expected) + " vs "
							+ RenderedOutputComparison.elementStructure(actual));
			assertEquals(DivergenceFamily.ELEMENT_STRUCTURE_ONLY, comparison.family());
		}

		@Test
		void aRendererThatChangesOnlyWhitespaceIsNotDiscardedAsBlank() {
			String expected = "<span>one\ntwo</span>";
			String actual = mutateByReplacingATextNewlineWithASpace(expected);

			RenderedOutputComparison comparison = RenderedOutputComparison.of(grammar, "one\ntwo", expected, actual);

			assertEquals("\n", comparison.visibleTextLost(),
					"a whitespace-only residual is blank, so gating classification on isBlank() throws it away");
			assertEquals(" ", comparison.visibleTextGained());
			assertEquals(DivergenceFamily.LINE_BREAK_ONLY, comparison.family(),
					"a newline collapsed to a space changes the rendered line count under whitespace-pre-wrap");
		}

		@Test
		void theContentPlaceholderFamilyIsDecidedByTheResidualNotByTheInput() {
			String hijackingSource = "{{c}} and [img]body[/img]";
			RenderedOutputComparison hijack = RenderedOutputComparison.of(grammar, hijackingSource,
					"<span>{{c}} and </span>", "<span>body and </span>");

			assertEquals(DivergenceFamily.LITERAL_CONTENT_PLACEHOLDER_HIJACK, hijack.family(),
					"one side keeping the author's literal {{c}} while the other substitutes a later tag's body "
							+ "is the observable signature of the whole-buffer indexOf at BBCodeGrammarLoader.java:295");

			RenderedOutputComparison unrelated = RenderedOutputComparison.of(grammar,
					"{{c}} and some text", "<span>{{c}} and some</span>", "<span>{{c}} and</span>");

			assertNotEquals(DivergenceFamily.LITERAL_CONTENT_PLACEHOLDER_HIJACK, unrelated.family(),
					"classifying on input.contains(\"{{c}}\") stamps every residual in a {{c}}-bearing input as "
							+ "the placeholder family, inflating it and hiding every other mechanism");
		}

		@Test
		void aVerbatimLineBreakRewriteIsNotReportedAsContentLoss() {
			RenderedOutputComparison comparison = RenderedOutputComparison.of(grammar,
					"[pre]a<br/>b[/pre]", "<pre>a\nb</pre>", "<pre>a&lt;br/&gt;b</pre>");

			assertEquals("\n", comparison.visibleTextLost(),
					"one side turned the authored break tag into a real newline");
			assertEquals(sortedCharacters("<br/>"), comparison.visibleTextGained(),
					"the other side kept the authored break tag as literal text");
			assertEquals(DivergenceFamily.VERBATIM_LINE_BREAK_REWRITE, comparison.family(),
					"break-tag characters traded against a newline is a rewrite, not unexplained content loss");
			assertFalse(comparison.whitespaceLayoutMatches(),
					"an escaped break tag reads as the literal text '<br/>' under whitespace-pre-wrap, so the "
							+ "two sides are not visually equivalent and the design's 'AST is correct here' "
							+ "framing is a judgement call, not a measurement");
		}

		@Test
		void anIdenticalRenderIsClassifiedAsIdenticalByEveryLens() {
			String source = "[b]same[/b] [url=http://zfgc.com]link[/url]\nnext line";
			String rendered = renderer.render(source);

			RenderedOutputComparison comparison =
					RenderedOutputComparison.of(grammar, source, rendered, rendered);

			assertTrue(comparison.byteIdentical());
			assertTrue(comparison.visibleTextIdentical());
			assertTrue(comparison.attributeSignaturesMatch());
			assertTrue(comparison.whitespaceLayoutMatches());
			assertEquals(DivergenceFamily.BYTE_IDENTICAL, comparison.family());
		}

		@Test
		void anAuthorTypedContentPlaceholderSurvivesEveryLaterTagOnTheRealEngine() {
			for (Map.Entry<String, String> defectCase : contentPlaceholderCases().entrySet()) {
				String rendered = renderer.render(defectCase.getValue());

				assertTrue(rendered.contains(RenderedOutputComparison.CONTENT_PLACEHOLDER),
						"the author typed a literal " + RenderedOutputComparison.CONTENT_PLACEHOLDER
								+ " and a later tag's body must not be substituted into it; scanning the whole "
								+ "accumulated buffer instead of the tag's own expansion deletes five characters "
								+ "of the author's text (" + defectCase.getKey() + "): " + rendered);
			}
		}

		@Test
		void aLaterTagStillFillsItsOwnContentAttributeAfterAnAuthorTypedPlaceholder() {
			String rendered = renderer.render("{{c}} then [youtube]dQw4w9WgXcQ[/youtube]");

			assertTrue(rendered.contains("src=\"https://www.youtube.com/embed/dQw4w9WgXcQ\""),
					"the whole-buffer scan did not only corrupt the author's text, it also robbed the tag of its "
							+ "own content: the embed lost its src and the sanitizer then dropped the iframe "
							+ "outright, so the video silently stopped rendering: " + rendered);
		}

		@Test
		void smileyRewritingIsIdempotentOnEveryRealSeededSmiley() {
			List<String> nonIdempotent = new ArrayList<>();
			for (SmileyToken smiley : smilies) {
				String once = renderer.render(smiley.code());
				if (!once.equals(renderer.render(once)))
					nonIdempotent.add(smiley.code() + " -> " + once + " -> " + renderer.render(once));
			}

			assertTrue(nonIdempotent.isEmpty(),
					"the smiley wrapper is not in the skip set, so collectTextNodes re-visits the renderer's own "
							+ "output and nests the wrapper one level deeper per pass, unbounded: " + nonIdempotent);
		}

		@Test
		void aRenderedSmileyKeepsItsLabelWhenTheOutputIsRenderedAgain() {
			String once = renderer.render(":P");

			assertTrue(once.contains("title=\""),
					"the smiley wrapper carries its label as a title attribute: " + once);
			assertEquals(once, renderer.render(once),
					"a span title is not on the relaxed safelist, so a second clean strips the smiley's label "
							+ "even once the wrapper is skipped: " + renderer.render(once));
		}

		record SweepOutcome(Map<DivergenceFamily, Integer> familyCounts,
				Map<DivergenceFamily, String> firstExamplePerFamily, int inputs, int divergences, int throwers) {}

		private SweepOutcome sweep(BBCodeFuzzGenerator.Alphabet alphabet, long[] seeds, int inputsPerSeed) {
			Map<DivergenceFamily, Integer> familyCounts = new TreeMap<>();
			Map<DivergenceFamily, String> firstExamplePerFamily = new TreeMap<>();
			int inputs = 0;
			int divergences = 0;
			int throwers = 0;
			for (long seed : seeds) {
				BBCodeFuzzGenerator generator = new BBCodeFuzzGenerator(alphabet, grammar, smilies, seed);
				for (int iteration = 0; iteration < inputsPerSeed; iteration++) {
					String input = generator.nextInput();
					inputs++;
					String rendered;
					try {
						rendered = renderer.render(input);
					} catch (RuntimeException thrown) {
						throwers++;
						continue;
					}
					assertEquals(rendered, renderer.render(input),
							"the renderer must be a pure function of its input, or no golden file can hold: "
									+ RenderedOutputComparison.visible(input));
					String reRendered = renderer.render(rendered);
					if (reRendered.equals(rendered))
						continue;
					divergences++;
					RenderedOutputComparison comparison =
							RenderedOutputComparison.of(grammar, input, rendered, reRendered);
					familyCounts.merge(comparison.family(), 1, Integer::sum);
					firstExamplePerFamily.putIfAbsent(comparison.family(),
							"input: " + RenderedOutputComparison.visible(input) + "\n" + comparison.describe());
				}
			}
			return new SweepOutcome(familyCounts, firstExamplePerFamily, inputs, divergences, throwers);
		}

		private void reportSweep(String label, SweepOutcome outcome) {
			StringBuilder report = new StringBuilder();
			report.append(label).append(": ").append(outcome.inputs()).append(" inputs, ")
					.append(outcome.divergences()).append(" re-render divergences, ")
					.append(outcome.throwers()).append(" throws");
			for (Map.Entry<DivergenceFamily, Integer> entry : outcome.familyCounts().entrySet())
				report.append("\n    ").append(String.format("%7d", entry.getValue())).append("  ")
						.append(entry.getKey()).append('\n')
						.append(outcome.firstExamplePerFamily().get(entry.getKey()).indent(8));
			System.out.println(report);
		}

		static final String SWEEP_HISTOGRAM_CONTRACT =
				"this histogram is the instrument's reading on an unchanged engine. It reproduces byte-identically "
						+ "run to run, so an unexplained move means either the renderer changed or the classifier "
						+ "did. Both are reviewable events; neither may pass silently. A move under a Turkish or "
						+ "Lithuanian default locale is the renderer's own case folding, not the harness.";

		private void assertBothAlphabetsRead(String label, long[] seeds, int inputsPerSeed,
				Map<DivergenceFamily, Integer> expectedWide, Map<DivergenceFamily, Integer> expectedHostile) {
			SweepOutcome wide =
					sweep(BBCodeFuzzGenerator.Alphabet.SEEDED_GRAMMAR_WIDE, seeds, inputsPerSeed);
			SweepOutcome hostile =
					sweep(BBCodeFuzzGenerator.Alphabet.HOSTILE_LITERALS, seeds, inputsPerSeed);
			reportSweep(label + " SEEDED_GRAMMAR_WIDE", wide);
			reportSweep(label + " HOSTILE_LITERALS", hostile);

			assertEquals(0, wide.throwers() + hostile.throwers(),
					"the renderer must survive every shape the seeded grammar can produce");
			assertEquals(seeds.length * inputsPerSeed, wide.inputs());
			assertEquals(seeds.length * inputsPerSeed, hostile.inputs());
			assertEquals(expectedWide, wide.familyCounts(),
					label + " SEEDED_GRAMMAR_WIDE moved. " + SWEEP_HISTOGRAM_CONTRACT);
			assertEquals(expectedHostile, hostile.familyCounts(),
					label + " HOSTILE_LITERALS moved. " + SWEEP_HISTOGRAM_CONTRACT);
		}

		@Test
		void aDeterministicSmokeSweepKeepsTheInstrumentHonestOnEveryBuild() {
			assertBothAlphabetsRead("smoke", Arrays.copyOf(BBCodeFuzzGenerator.REPRODUCIBLE_SEEDS, SMOKE_SEEDS),
					SMOKE_INPUTS_PER_SEED, SMOKE_WIDE_FAMILIES, SMOKE_HOSTILE_FAMILIES);
		}

		@Test
		@EnabledIfSystemProperty(named = SWEEP_PROPERTY, matches = "true")
		void theFullFuzzSweepMeasuresEveryCodeAcrossEveryReproducibleSeed() {
			assertBothAlphabetsRead("sweep", BBCodeFuzzGenerator.REPRODUCIBLE_SEEDS, SWEEP_INPUTS_PER_SEED,
					SWEEP_WIDE_FAMILIES, SWEEP_HOSTILE_FAMILIES);
		}

		@Test
		@EnabledIfSystemProperty(named = SWEEP_PROPERTY, matches = "true")
		void everyLensStaysSensitiveAcrossTheWholeFuzzCorpus() {
			Map<String, Integer> mutationsProven = new TreeMap<>();
			for (BBCodeFuzzGenerator.Alphabet alphabet : BBCodeFuzzGenerator.Alphabet.values()) {
				BBCodeFuzzGenerator generator =
						new BBCodeFuzzGenerator(alphabet, grammar, smilies, BBCodeFuzzGenerator.REPRODUCIBLE_SEEDS[0]);
				for (int iteration = 0; iteration < LENS_CALIBRATION_INPUTS; iteration++) {
					String input = generator.nextInput();
					String rendered = renderer.render(input);
					proveLensSensitivity(mutationsProven, input, rendered,
							"visible text", mutateByDroppingOneVisibleCharacter(rendered),
							comparison -> !comparison.visibleTextLost().isEmpty());
					proveLensSensitivity(mutationsProven, input, rendered,
							"attribute", mutateByChangingOneAttributeValue(rendered),
							comparison -> !comparison.attributeSignaturesMatch());
					proveLensSensitivity(mutationsProven, input, rendered,
							"whitespace layout", mutateByDroppingOneLineBreakElement(rendered),
							comparison -> !comparison.whitespaceLayoutMatches());
				}
			}
			System.out.println("lens sensitivity proven on real fuzz output: " + mutationsProven);

			for (String lens : List.of("visible text", "attribute", "whitespace layout"))
				assertTrue(mutationsProven.getOrDefault(lens, 0) > 0,
						"a lens that never fires across "
								+ BBCodeFuzzGenerator.Alphabet.values().length * LENS_CALIBRATION_INPUTS
								+ " mutated fuzz outputs is not measuring anything: " + lens);
		}

		private void proveLensSensitivity(Map<String, Integer> proven, String input, String rendered,
				String lens, String mutated, java.util.function.Predicate<RenderedOutputComparison> lensFires) {
			if (mutated.equals(rendered))
				return;
			RenderedOutputComparison comparison =
					RenderedOutputComparison.of(grammar, input, rendered, mutated);
			assertTrue(lensFires.test(comparison),
					"the " + lens + " lens missed a mutation it claims to catch:\n" + comparison.describe());
			proven.merge(lens, 1, Integer::sum);
		}

		@Test
		@EnabledIfSystemProperty(named = REGENERATE_PROPERTY, matches = "true")
		void regenerateTheGoldenCorpus() throws IOException {
			Map<String, GoldenInput> inputsById = new LinkedHashMap<>();
			for (GoldenCase row : readGoldenCorpus())
				if (!row.id().startsWith(TEMPLATE_CASE_PREFIX) && !row.id().startsWith(DEFECT_CASE_PREFIX)
						&& !row.id().startsWith(PAGE_ASSEMBLY_CASE_PREFIX)
						&& !row.id().startsWith(SPECIAL_LINK_CASE_PREFIX)
						&& !row.id().startsWith(IMPLICIT_ITEM_STRADDLE_CASE_PREFIX)
						&& !row.id().startsWith(MARKDOWN_CASE_PREFIX))
					inputsById.put(row.id(), new GoldenInput(row.lane(), row.input()));
			for (Map.Entry<String, String> defectCase : contentPlaceholderCases().entrySet())
				inputsById.put(defectCase.getKey(), new GoldenInput(ContentFormat.BBCODE, defectCase.getValue()));
			for (Map.Entry<String, String> assemblyCase : pageAssemblyInputCases().entrySet())
				inputsById.put(assemblyCase.getKey(), new GoldenInput(ContentFormat.BBCODE, assemblyCase.getValue()));
			for (Map.Entry<String, String> specialLinkCase : legacySpecialLinkInputCases().entrySet())
				inputsById.put(specialLinkCase.getKey(),
						new GoldenInput(ContentFormat.BBCODE, specialLinkCase.getValue()));
			for (Map.Entry<String, String> straddleCase : implicitItemStraddleInputCases().entrySet())
				inputsById.put(straddleCase.getKey(), new GoldenInput(ContentFormat.BBCODE, straddleCase.getValue()));
			for (Map.Entry<String, String> templateCase : templateCasesFromSeed().entrySet())
				inputsById.put(templateCase.getKey(), new GoldenInput(ContentFormat.BBCODE, templateCase.getValue()));
			for (Map.Entry<String, String> markdownCase : markdownLaneCases().entrySet())
				inputsById.put(markdownCase.getKey(),
						new GoldenInput(ContentFormat.MARKDOWN, markdownCase.getValue()));

			List<String> lines = new ArrayList<>();
			for (Map.Entry<String, GoldenInput> entry : inputsById.entrySet()) {
				GoldenInput input = entry.getValue();
				String rendered = renderInLane(input.lane(), input.source());
				lines.add(entry.getKey() + "\t" + encode(input.source()) + "\t" + encode(rendered) + "\t"
						+ String.join(",", labelsFor(entry.getKey(), input.source(), rendered)) + "\t"
						+ input.lane().name());
			}
			Files.write(GOLDEN_CORPUS, lines);

			System.out.println("regenerated " + GOLDEN_CORPUS + " with " + lines.size() + " cases");
		}
	}

	@Nested
	class SourceConversion {

		static Map<String, BBCodeConfig> grammar;
		static Renderer renderer;
		static ContentFormatConverter converter;

		@BeforeAll
		static void loadTheRealSeededEngine() {
			grammar = seededBBCodeGrammar();
			BBCodeGrammarHolder grammarHolder = grammarHolder();
			RenderedTextEnricher enricher = enricher();
			ContentOutputSanitizer sanitizer = sanitizer(grammarHolder, enricher);
			enricher.registerSmilies(seededSmilies());
			renderer = buildRenderer(mock(BBCodeDataProvider.class), grammarHolder, enricher, sanitizer,
					messageResolver());
			renderer.useGrammar(grammar);
			converter = new ContentFormatConverter(renderer.grammarHolder(), renderer.contentRenderingService(),
					renderer.markdownRenderer());
		}

		static final String A_MIGRATED_POST_THE_MARKDOWN_LANE_READS_DIFFERENTLY =
				"[quote author=mgzero thread=3 msg=14]<br /> ???<br /><br />Obviously Stove, and that's not even "
						+ "an option!<br />[/quote]<br />Yes, but we're all Steve.";

		static String toMarkdown(String bbCode) {
			return converter.convert(bbCode, ContentFormat.BBCODE, ContentFormat.MARKDOWN, ContentScope.FORUM).content();
		}

		static ContentFormatConverter.ConvertedContent toBBCode(String markdown) {
			return converter.convert(markdown, ContentFormat.MARKDOWN, ContentFormat.BBCODE, ContentScope.FORUM);
		}

		@ParameterizedTest(name = "{0}")
		@MethodSource("bbCodeConstructsWithAMarkdownEquivalent")
		void aBBCodeConstructWithAMarkdownEquivalentIsRewrittenIntoIt(String caseName, String bbCode,
				String expectedMarkdown) {
			assertEquals(expectedMarkdown, toMarkdown(bbCode));
		}

		static Stream<Arguments> bbCodeConstructsWithAMarkdownEquivalent() {
			return Stream.of(
					arguments("bold", "[b]bold[/b]", "**bold**"),
					arguments("italic", "[i]italic[/i]", "*italic*"),
					arguments("nested emphasis", "[b]bold [i]and italic[/i][/b]", "**bold *and italic***"),
					arguments("heading", "[h2]Title[/h2]", "## Title\n"),
					arguments("deepest heading", "[h6]Title[/h6]", "###### Title\n"),
					arguments("thematic break", "[hr]", "---\n"),
					arguments("link with a destination attribute", "[url=/wiki/Docs]the docs[/url]",
							"[the docs](/wiki/Docs)"),
					arguments("link whose body is its destination", "[url]http://zfgc.com[/url]",
							"[http://zfgc.com](http://zfgc.com)"),
					arguments("image", "[img]/content/1.png[/img]", "![](/content/1.png)"),
					arguments("quote", "[quote]\nquoted body\n[/quote]", "> quoted body\n"),
					arguments("code", "[code]\nraw **text**\n[/code]", "```\nraw **text**\n```\n"),
					arguments("list", "[list]\n[*]one\n[*]two\n[/list]", "- one\n- two\n"),
					arguments("decimal list", "[list type=decimal]\n[*]one\n[*]two\n[/list]", "1. one\n1. two\n"),
					arguments("numbered list written the way the wiki migrator emits it",
							"[list=1]\n[*]one\n[*]two\n[/list]", "1. one\n1. two\n"),
					arguments("bulleted list named through the nameless attribute",
							"[list=disc]\n[*]one\n[/list]", "- one\n"),
					arguments("explicit list items", "[list]\n[li]one[/li]\n[/list]", "- one\n"),
					arguments("teletype", "[tt]typed[/tt]", "`typed`"));
		}

		@ParameterizedTest(name = "{0}")
		@MethodSource("bbCodeConstructsWithNoMarkdownEquivalent")
		void aBBCodeConstructWithNoMarkdownEquivalentSurvivesVerbatim(String caseName, String bbCode) {
			assertEquals(bbCode, toMarkdown(bbCode),
					"markdown cannot express this code, and the markdown lane renders bbcode anyway, so leaving "
							+ "the author's own bytes alone is both free and lossless");
		}

		static Stream<Arguments> bbCodeConstructsWithNoMarkdownEquivalent() {
			return Stream.of(
					arguments("underline", "[u]underlined[/u]"),
					arguments("strikethrough", "[s]struck[/s]"),
					arguments("colour", "[color=red]red[/color]"),
					arguments("size", "[size=3]big[/size]"),
					arguments("spoiler", "[spoiler]hidden[/spoiler]"),
					arguments("table", "[table][tr][td]cell[/td][/tr][/table]"),
					arguments("youtube", "[youtube]dQw4w9WgXcQ[/youtube]"),
					arguments("youtube the author kept writing after",
							"[youtube]dQw4w9WgXcQ[/youtube] and more"),
					arguments("wiki link", "[wiki=Ocarina]Ocarina[/wiki]"),
					arguments("preformatted", "[pre]raw\n  text[/pre]"),
					arguments("sized image", "[img width=10 height=20]/content/1.png[/img]"),
					arguments("authored quote", "[quote author=Bob]hi[/quote]"),
					arguments("template", "[template=stub][/template]"));
		}

		@Test
		void anInlineCodeSpanSurvivesBothDirectionsNowThatTeletypeIsBoundToIt() {
			assertEquals("[tt]x[/tt]", toBBCode("`x`").content(),
					"markdown's inline code has to reach the code bb_code_config binds to INLINE_CODE");
			assertEquals("`x`", toMarkdown("[tt]x[/tt]"),
					"and it has to come back, which is exactly what the missing bb-to-md half used to break");
			assertEquals("[tt]a`b[/tt]", toBBCode(toMarkdown("[tt]a`b[/tt]")).content(),
					"a body carrying a backtick needs a longer fence, or the round trip stops at the first tick");
		}

		@Test
		void aSourceReferencingQuoteStaysBBCodeBecauseMarkdownCannotCarryItsAttribution() {
			assertEquals("[quote msg=1234]\nquoted body\n[/quote]",
					toMarkdown("[quote msg=1234]\nquoted body\n[/quote]"),
					"the msg attribute is what pulls the source post's body and header at render time; a markdown "
							+ "blockquote carries neither, so converting one would silently strip attribution");
			assertEquals("[quote author=Bob thread=3 msg=1234]stale copy[/quote]",
					toMarkdown("[quote author=Bob thread=3 msg=1234]stale copy[/quote]"));
		}

		@Test
		void aBlockConstructTheAuthorDidNotPutOnItsOwnLineStaysBBCode() {
			assertEquals("line one[hr]line two", toMarkdown("line one[hr]line two"),
					"markdown block syntax only means anything at the head of a line, so a code the author wrote "
							+ "mid-sentence has to stay bbcode");
			assertEquals("Look at this [quote]q[/quote] inline.", toMarkdown("Look at this [quote]q[/quote] inline."));
			assertEquals("prefix [h2]Title[/h2]", toMarkdown("prefix [h2]Title[/h2]"));
		}

		@ParameterizedTest(name = "{0}")
		@MethodSource("emphasisMarkdownsDelimiterRulesWouldNotRead")
		void emphasisStaysBBCodeWhereMarkdownWouldNotReadItsDelimiters(String caseName, String bbCode,
				String expectedMarkdown) {
			assertEquals(expectedMarkdown, toMarkdown(bbCode));
			assertEquals(RenderedOutputComparison.wholeVisibleText(renderer.render(bbCode)),
					RenderedOutputComparison.wholeVisibleText(
							renderer.renderMarkdown(expectedMarkdown)).stripTrailing(),
					"a delimiter run that cannot close leaves the asterisks on the page as text, so where "
							+ "markdown's flanking rules refuse the emphasis the bbcode has to stay");
		}

		static Stream<Arguments> emphasisMarkdownsDelimiterRulesWouldNotRead() {
			return Stream.of(
					arguments("punctuation before a word character", "[b]bold.[/b]next", "[b]bold.[/b]next"),
					arguments("a word character before punctuation", "next[b].bold[/b]", "next[b].bold[/b]"),
					arguments("punctuation before whitespace still converts", "[b]bold.[/b] next",
							"**bold.** next"),
					arguments("a word character before a word character still converts", "next[b]bold[/b]",
							"next**bold**"));
		}

		@Test
		void aParagraphThatStillCarriesBlockLevelBBCodeConvertsNothingOfItsOwn() {
			assertEquals("[table][tr][td][i]cell[/i][/td][/tr][/table]",
					toMarkdown("[table][tr][td][i]cell[/i][/td][/tr][/table]"),
					"the markdown lane only expands bbcode from a whole text run, and an emphasis node splits the "
							+ "run in two; converting the [i] here would leave the table rendering as literal text");
			assertEquals("{{#empty}}[i]none[/i]{{/empty}}[table][tr][td]x[/td][/tr][/table]",
					toMarkdown("{{#empty}}[i]none[/i]{{/empty}}[table][tr][td]x[/td][/tr][/table]"));
			assertEquals("**bold**\n\n[table][tr][td]x[/td][/tr][/table]",
					toMarkdown("[b]bold[/b]\n\n[table][tr][td]x[/td][/tr][/table]"),
					"the suppression is per paragraph, so a paragraph of its own still converts");
		}

		@Test
		void aParagraphTheMarkdownLaneReadsAsRawHtmlConvertsNothingOfItsOwn() {
			assertEquals("<div class=\"p-4\">[i]cell[/i]</div>",
					toMarkdown("<div class=\"p-4\">[i]cell[/i]</div>"),
					"commonmark hands a raw html block through without inline parsing, so markdown emphasis "
							+ "written inside one renders as literal asterisks");
		}

		@ParameterizedTest(name = "{0}")
		@MethodSource("textThatMarkdownWouldOtherwiseReadAsMarkup")
		void plainTextIsEscapedSoTheMarkdownLaneStillReadsItAsText(String caseName, String bbCode,
				String expectedMarkdown) {
			assertEquals(expectedMarkdown, toMarkdown(bbCode));
			assertEquals(RenderedOutputComparison.wholeVisibleText(renderer.render(bbCode)),
					RenderedOutputComparison.wholeVisibleText(
							renderer.renderMarkdown(expectedMarkdown)).stripTrailing(),
					"an unescaped run would silently become emphasis, a heading, a list or a quote in a post the "
							+ "author only ever typed as text");
		}

		static Stream<Arguments> textThatMarkdownWouldOtherwiseReadAsMarkup() {
			return Stream.of(
					arguments("asterisks in arithmetic", "2 * 3 * 4", "2 \\* 3 \\* 4"),
					arguments("emphasis the author typed literally", "a **bold** word", "a \\*\\*bold\\*\\* word"),
					arguments("underscores", "snake_case_name", "snake\\_case\\_name"),
					arguments("a hash opening a line", "# not a heading", "\\# not a heading"),
					arguments("a hyphen opening a line", "- not a list", "\\- not a list"),
					arguments("a plus opening a line", "+ not a list", "\\+ not a list"),
					arguments("a quote marker opening a line", "> not a quote", "\\> not a quote"),
					arguments("a setext underline", "Title\n=====", "Title\n\\====="),
					arguments("an ordered marker opening a line", "1. not a list", "1\\. not a list"),
					arguments("a paren ordered marker", "12) not a list", "12\\) not a list"),
					arguments("a tilde fence", "~~~ not a fence", "\\~~~ not a fence"),
					arguments("backticks", "`not code`", "\\`not code\\`"),
					arguments("brackets", "[not a link](/nowhere)", "\\[not a link\\](/nowhere)"),
					arguments("an image marker", "![not an image](/nowhere)", "!\\[not an image\\](/nowhere)"),
					arguments("a backslash", "a \\ b", "a \\\\ b"),
					arguments("four leading spaces", "    not indented code", "&#32;   not indented code"),
					arguments("a leading tab", "\tnot indented code", "&#9;not indented code"),
					arguments("two trailing spaces", "line one  \nline two", "line one &#32;\nline two"));
		}

		@ParameterizedTest(name = "{0}")
		@MethodSource("smileyShapesThatCollideWithMarkdownBlockMarkers")
		void aSmileyOpeningALineSurvivesTheEscaping(String caseName, String bbCode, String expectedSmileyClass) {
			String throughMarkdown = renderer.renderMarkdown(toMarkdown(bbCode));

			assertTrue(throughMarkdown.contains(expectedSmileyClass),
					"the smiley codes that read as markdown block markers are exactly the ones escaping can "
							+ "break, and a broken one turns a post's smiley into a numbered list or a "
							+ "blockquote: " + throughMarkdown);
			assertEquals(RenderedOutputComparison.wholeVisibleText(renderer.render(bbCode)),
					RenderedOutputComparison.wholeVisibleText(throughMarkdown).stripTrailing());
		}

		static Stream<Arguments> smileyShapesThatCollideWithMarkdownBlockMarkers() {
			return Stream.of(
					arguments("cool opens the line", "8) that one", "bb-smiley-cool"),
					arguments("angry opens the line", ">:( grumpy", "bb-smiley-angry"),
					arguments("evil opens the line", ">:D evil", "bb-smiley-evil"),
					arguments("cool mid line", "that is 8) cool", "bb-smiley-cool"),
					arguments("embarrassed carries a bracket", ":-[ oops", "bb-smiley-embarrassed"));
		}

		@Test
		void everyMarkdownConstructIsBoundToExactlyOneCanonicalSeededCode() {
			Map<String, List<String>> canonicalCodesByConstruct = new LinkedHashMap<>();
			for (BBCodeConfig config : grammar.values())
				config.declaredMarkdownEquivalent().ifPresent(equivalent -> {
					if (config.isTheCanonicalCodeForItsMarkdownEquivalent())
						canonicalCodesByConstruct.computeIfAbsent(
								equivalent == MarkdownEquivalent.HEADING
										? equivalent.name() + heading(config)
										: equivalent.name(),
								key -> new ArrayList<>()).add(config.getCode());
				});

			assertEquals(Set.of("STRONG_EMPHASIS", "EMPHASIS", "THEMATIC_BREAK", "LINK", "IMAGE", "BLOCK_QUOTE",
					"FENCED_CODE", "LIST", "INLINE_CODE", "HEADING1", "HEADING2", "HEADING3", "HEADING4",
					"HEADING5", "HEADING6"),
					canonicalCodesByConstruct.keySet(),
					"the binding lives in bb_code_config.markdown_equivalent now, so a seed line that loses its "
							+ "binding silently converts nothing");
			canonicalCodesByConstruct.forEach((construct, codes) -> assertEquals(1, codes.size(),
					"two canonical codes for one construct would make the md-to-bb direction pick arbitrarily: "
							+ construct + " -> " + codes));
		}

		private static String heading(BBCodeConfig config) {
			return String.valueOf(BBCodeGrammar.headingLevelDeclaredByTheMarkup(config));
		}

		@Test
		void aCodeTheAdministratorDisabledIsNotConvertedBecauseItIsNoLongerMarkup() {
			Map<String, BBCodeConfig> withoutBold = new HashMap<>(grammar);
			withoutBold.remove("B");
			renderer.useGrammar(withoutBold);
			try {
				assertEquals("\\[b\\]bold\\[/b\\]", toMarkdown("[b]bold[/b]"),
						"a disabled code is text, not markup, and text is escaped");
			} finally {
				renderer.useGrammar(grammar);
			}
		}

		@ParameterizedTest(name = "{0}")
		@MethodSource("markdownConstructsWithABBCodeEquivalent")
		void aMarkdownConstructWithABBCodeEquivalentIsRewrittenIntoIt(String caseName, String markdown,
				String expectedBBCode) {
			assertEquals(expectedBBCode, toBBCode(markdown).content());
		}

		static Stream<Arguments> markdownConstructsWithABBCodeEquivalent() {
			return Stream.of(
					arguments("strong emphasis", "**bold**", "[b]bold[/b]"),
					arguments("emphasis", "*italic*", "[i]italic[/i]"),
					arguments("underscore emphasis", "_italic_", "[i]italic[/i]"),
					arguments("heading", "## Title", "[h2]Title[/h2]"),
					arguments("setext heading", "Title\n=====", "[h1]Title[/h1]"),
					arguments("thematic break", "---", "[hr]"),
					arguments("link", "[the docs](/wiki/Docs)", "[url=/wiki/Docs]the docs[/url]"),
					arguments("autolink", "<https://zfgc.com>",
							"[url=https://zfgc.com]https://zfgc.com[/url]"),
					arguments("reference link", "[the docs][ref]\n\n[ref]: /wiki/Docs",
							"[url=/wiki/Docs]the docs[/url]"),
					arguments("image", "![alt text](/content/1.png)", "[img]/content/1.png[/img]"),
					arguments("block quote", "> quoted", "[quote]\nquoted\n[/quote]"),
					arguments("fenced code", "```\nraw [b]text[/b]\n```", "[code]\nraw [b]text[/b]\n[/code]"),
					arguments("indented code", "    raw text", "[code]\nraw text\n[/code]"),
					arguments("inline code", "an `example` span", "an [tt]example[/tt] span"),
					arguments("bullet list", "- one\n- two", "[list]\n[li]one[/li]\n[li]two[/li]\n[/list]"),
					arguments("ordered list", "1. one\n2. two",
							"[list type=decimal]\n[li]one[/li]\n[li]two[/li]\n[/list]"),
					arguments("hard line break", "line one  \nline two", "line one\nline two"),
					arguments("two paragraphs", "one\n\ntwo", "one\n\ntwo"),
					arguments("raw html", "<div class=\"p-4\">kept</div>", "<div class=\"p-4\">kept</div>"));
		}

		@Test
		void bbCodeAlreadyInTheMarkdownSourceStaysBBCode() {
			assertEquals("[quote msg=1234]body[/quote]", toBBCode("[quote msg=1234]body[/quote]").content());
			assertEquals("[color=red]red[/color] and [b]bold[/b]",
					toBBCode("[color=red]red[/color] and [b]bold[/b]").content());
			assertEquals("[list]\n[*]one\n[*]two\n[/list]", toBBCode("[list]\n[*]one\n[*]two\n[/list]").content());
		}

		@ParameterizedTest(name = "{0}")
		@MethodSource("markdownEscapesThatMustNotSurviveTheConversion")
		void aMarkdownEscapeIsResolvedBecauseBBCodeHasNoBackslashEscape(String caseName, String markdown,
				String expectedBBCode) {
			assertEquals(expectedBBCode, toBBCode(markdown).content(),
					"a backslash means nothing to the bbcode lane, so an escape carried over would render as a "
							+ "literal backslash the author never typed");
		}

		static Stream<Arguments> markdownEscapesThatMustNotSurviveTheConversion() {
			return Stream.of(
					arguments("escaped emphasis", "2 \\* 3 \\* 4", "2 * 3 * 4"),
					arguments("escaped brackets", "\\[not a link\\](/nowhere)", "[not a link](/nowhere)"),
					arguments("escaped hash", "\\# not a heading", "# not a heading"),
					arguments("escaped backslash", "a \\\\ b", "a \\ b"),
					arguments("a space entity", "&#32;   indented", "    indented"));
		}

		@Test
		void aConversionThatChangesNothingIsStillReportedWhenTheOtherLaneReadsItDifferently() {
			ContentFormatConverter.ConvertedContent converted = converter.convert(
					A_MIGRATED_POST_THE_MARKDOWN_LANE_READS_DIFFERENTLY, ContentFormat.BBCODE, ContentFormat.MARKDOWN,
					ContentScope.FORUM);

			assertEquals(A_MIGRATED_POST_THE_MARKDOWN_LANE_READS_DIFFERENTLY, converted.content(),
					"this post is one the converter deliberately leaves alone: its quote carries attributes markdown "
							+ "cannot express, and a paragraph still holding block-level bbcode converts nothing");
			assertEquals(List.of(ContentFormatConverter.aCodeTheOtherFormatDoesNotCarry("quote",
					ContentFormat.MARKDOWN)), converted.notes(),
					"leaving the source alone is not the same as the post surviving: the markdown lane prints this "
							+ "quote's opening tag as text, and an author who is told nothing finds out by "
							+ "publishing it");
			assertNotEquals(
					visibleTextWithoutWhitespace(renderer.render(A_MIGRATED_POST_THE_MARKDOWN_LANE_READS_DIFFERENTLY)),
					visibleTextWithoutWhitespace(renderer.renderMarkdown(converted.content())),
					"the note is only worth anything while the two lanes really do disagree about this post");
		}

		@ParameterizedTest(name = "{0}")
		@MethodSource("blocksWhoseAuthorWroteTheCloserMidLine")
		void aBlockClosedMidLineReadsTheSameInBothLanesSoTheFlipReportsNothing(String caseName, String bbCode) {
			ContentFormatConverter.ConvertedContent converted =
					converter.convert(bbCode, ContentFormat.BBCODE, ContentFormat.MARKDOWN, ContentScope.FORUM);

			assertEquals(visibleTextWithoutWhitespace(renderer.render(bbCode)),
					visibleTextWithoutWhitespace(renderer.renderMarkdown(converted.content())),
					"while the markdown lane hosted the closer as text the two lanes showed different words, so "
							+ "the converter had to warn about a post that was only ever mis-rendered: " + caseName);
			assertEquals(List.of(), converted.notes(),
					"a note that fires because the target lane leaks markup teaches the author to work around a "
							+ "renderer defect instead of the defect being fixed: " + caseName);
		}

		static Stream<Arguments> blocksWhoseAuthorWroteTheCloserMidLine() {
			return Stream.of(
					arguments("a quote closed mid line", "[quote]\nbody [/quote] tail"),
					arguments("a quote whose closer opens but does not own its line", "[quote]\nbody\n[/quote] tail"),
					arguments("text before the opener and after the closer", "lead [quote]\nbody [/quote] tail"),
					arguments("a nested quote closed mid line",
							"[quote]\nouter\n[quote]\ninner [/quote] mid\n[/quote]"),
					arguments("a centred block closed mid line", "[align=center]\ncentered [/align] tail"));
		}

		@Test
		void aConversionThatSurvivesTheFlipIsReportedAsNothingAtAll() {
			for (Arguments row : bbCodeConstructsWithNoMarkdownEquivalent().toList()) {
				String bbCode = (String) row.get()[1];
				assertEquals(visibleTextWithoutWhitespace(renderer.render(bbCode)),
						visibleTextWithoutWhitespace(renderer.renderMarkdown(bbCode)),
						"this row only says something about notes while both lanes agree about it: " + bbCode);
				assertEquals(List.of(),
						converter.convert(bbCode, ContentFormat.BBCODE, ContentFormat.MARKDOWN, ContentScope.FORUM).notes(),
						"a code markdown cannot express is left as bbcode, and the markdown lane renders bbcode, so "
								+ "there is nothing to warn the author about: " + bbCode);
			}
		}

		static String visibleTextWithoutWhitespace(String html) {
			return charactersWithoutWhitespace(RenderedOutputComparison.wholeVisibleText(html));
		}

		static final String A_HEADING_MARKDOWN_WOULD_READ_AS_A_CLOSING_SEQUENCE = "[h2]Title #[/h2]";

		static final String TWO_EMPHASES_MARKDOWN_CANNOT_READ_SIDE_BY_SIDE = "[i]a[/i][i]b[/i]";

		@Test
		void aConstructTheFlipWouldCorruptIsPutBackAsBBCodeInsteadOfOnlyBeingReportedOn() {
			ContentFormatConverter.ConvertedContent converted =
					converter.convert(A_HEADING_MARKDOWN_WOULD_READ_AS_A_CLOSING_SEQUENCE, ContentFormat.BBCODE,
							ContentFormat.MARKDOWN, ContentScope.FORUM);

			assertEquals(A_HEADING_MARKDOWN_WOULD_READ_AS_A_CLOSING_SEQUENCE, converted.content(),
					"'## Title #' ends in an ATX closing sequence, so markdown eats the author's trailing hash. "
							+ "BBCode is a lossless fallback in the markdown lane, so a conversion that damages the "
							+ "post must put the author's own bytes back rather than publish the damage with a note");
			assertEquals(visibleTextWithoutWhitespace(
					renderer.render(A_HEADING_MARKDOWN_WOULD_READ_AS_A_CLOSING_SEQUENCE)),
					visibleTextWithoutWhitespace(renderer.renderMarkdown(converted.content())),
					"the whole point of putting it back is that the two lanes now agree");
			assertEquals(List.of(ContentFormatConverter.theCodesTheFlipCouldNotCarry(
					Set.of(onlyTagOf("[h2]Title #[/h2]")), ContentFormat.MARKDOWN)), converted.notes(),
					"the note has to name what was put back and why, or the author cannot tell a lossless "
							+ "fallback from a converter that did nothing");
		}

		static BBCodeTag onlyTagOf(String bbCode) {
			return (BBCodeTag) BBCodeParser.parse(bbCode, grammar).children().get(0);
		}

		@Test
		void onlyTheTagsThatActuallyCorruptThePostArePutBack() {
			assertEquals("## Title\n\n[h2]Second #[/h2]",
					toMarkdown("[h2]Title[/h2]\n[h2]Second #[/h2]"),
					"reverting the whole document on any divergence would throw away every conversion the post "
							+ "did survive, which is the same as not converting at all");
			assertEquals("*a*[i]b[/i]", toMarkdown(TWO_EMPHASES_MARKDOWN_CANNOT_READ_SIDE_BY_SIDE),
					"'*a**b*' reads as one emphasis and a stray asterisk, so exactly one of the two has to go "
							+ "back; putting both back would lose a conversion that was fine");
		}

		@Test
		void aTagThatMeasuresCleanOnItsOwnIsStillPutBackWhenItsNeighbourBreaksIt() {
			assertEquals("*b*", toMarkdown("[i]b[/i]"),
					"measured on its own this tag converts cleanly, which is exactly why per-tag measurement is "
							+ "unsound: it would clear this tag and publish the corrupted document");
			assertEquals("*a*[i]b[/i]", toMarkdown(TWO_EMPHASES_MARKDOWN_CANNOT_READ_SIDE_BY_SIDE),
					"the same tag has to go back once its neighbour is beside it, so every candidate set has to "
							+ "be re-verified in the document it will actually be published in");
		}

		@Test
		void aTagInsideAConvertedBlockIsPutBackWithoutPuttingTheBlockBack() {
			assertEquals("> [h2]Title #[/h2]\n", toMarkdown("[quote]\n[h2]Title #[/h2]\n[/quote]"),
					"the quote converts to a markdown blockquote and survives; only the heading inside it has to "
							+ "keep its bbcode, so the search has to reach nested tags one at a time");
		}

		@Test
		void aPostNoReversionCanRescueKeepsTheNoteItAlwaysHad() {
			ContentFormatConverter.ConvertedContent converted = converter.convert(
					A_MIGRATED_POST_THE_MARKDOWN_LANE_READS_DIFFERENTLY, ContentFormat.BBCODE, ContentFormat.MARKDOWN,
					ContentScope.FORUM);

			assertEquals(List.of(ContentFormatConverter.aCodeTheOtherFormatDoesNotCarry("quote",
					ContentFormat.MARKDOWN)), converted.notes(),
					"this post converts nothing, so there is nothing to put back and the author still has to be "
							+ "told the markdown lane prints the quote's opening tag as text");
		}

		static final String A_LIST_ITEM_HOLDING_TWO_EMPHASES_MARKDOWN_CANNOT_READ_SIDE_BY_SIDE =
				"[list]\n[*]" + TWO_EMPHASES_MARKDOWN_CANNOT_READ_SIDE_BY_SIDE + "\n[/list]";

		static Map<String, BBCodeConfig> theGrammarWithListItemsAnAdministratorMadeInline() {
			Map<String, BBCodeConfig> inlineItems = seededBBCodeGrammar();
			BBCodeConfig item = inlineItems.get("LI");
			item.setEndTag("</span>");
			for (BBCodeAttributeMode mode : item.getAttributeConfig().values()) {
				mode.setOpenTag("<span class=\"bb-code-li\">");
				mode.setCloseTag("</span>");
			}
			return inlineItems;
		}

		@Test
		void aTagInsideAListItemIsPutBackOnItsOwnRatherThanCostingThePostItsWholeList() {
			renderer.useGrammar(theGrammarWithListItemsAnAdministratorMadeInline());
			try {
				ContentFormatConverter.ConvertedContent converted =
						converter.convert(A_LIST_ITEM_HOLDING_TWO_EMPHASES_MARKDOWN_CANNOT_READ_SIDE_BY_SIDE,
								ContentFormat.BBCODE, ContentFormat.MARKDOWN, ContentScope.FORUM);

				assertEquals("- *a*[i]b[/i]\n", converted.content(),
						"the list writer used to be handed its own empty set of tags written as markdown, so the "
								+ "[i] inside the item never became a candidate; the search then had only the list "
								+ "itself to put back and threw away a conversion the item mostly survived");
				assertEquals(List.of(ContentFormatConverter.theCodesTheFlipCouldNotCarry(
						Set.of(onlyTagOf(TWO_EMPHASES_MARKDOWN_CANNOT_READ_SIDE_BY_SIDE)), ContentFormat.MARKDOWN)),
						converted.notes(),
						"and the note named [list], which tells the author nothing about the emphasis that is "
								+ "actually what markdown could not read");
			} finally {
				renderer.useGrammar(grammar);
			}
		}

		@Test
		void aListItemOfTheSeededGrammarStillConvertsNothingOfItsOwn() {
			assertEquals("- " + TWO_EMPHASES_MARKDOWN_CANNOT_READ_SIDE_BY_SIDE + "\n",
					toMarkdown(A_LIST_ITEM_HOLDING_TWO_EMPHASES_MARKDOWN_CANNOT_READ_SIDE_BY_SIDE),
					"the seeded li emits <li>, so every tag in a list item sits in a paragraph that keeps its "
							+ "block-level bbcode and none of them convert. This is the gate that hides the set "
							+ "the writer was dropping, and it is why the row above has to move li inline to "
							+ "measure anything at all");
		}

		@Test
		void aMarkdownSourceIsNeverPutBackBecauseTheBBCodeLaneCannotRenderMarkdown() {
			ContentFormatConverter.ConvertedContent converted = toBBCode("an `[b]example[/b]` span");

			assertEquals("an `[b]example[/b]` span", converted.content(),
					"the markdown lane renders bbcode, so bbcode is a lossless fallback there; the bbcode lane "
							+ "renders no markdown, so this direction has no fallback to reach for and keeps the note");
			assertTrue(converted.notes().contains(
					ContentFormatConverter.contentTheOtherFormatDoesNotCarry(ContentFormat.BBCODE)));
		}

		@Test
		void theSourceIsRenderedOnceHoweverManyCandidatesTheSearchTries() {
			ContentRenderingService countingRenderer = spy(renderer.contentRenderingService());
			ContentFormatConverter countingConverter =
					new ContentFormatConverter(renderer.grammarHolder(), countingRenderer,
							renderer.markdownRenderer());
			String threeCandidatesOneOfWhichBreaks = "[b]x[/b] [i]a[/i][i]b[/i]";

			countingConverter.convert(threeCandidatesOneOfWhichBreaks, ContentFormat.BBCODE, ContentFormat.MARKDOWN,
					ContentScope.FORUM);

			verify(countingRenderer, times(1)).renderWithTemplates(eq(threeCandidatesOneOfWhichBreaks),
					eq(ContentFormat.BBCODE), any(), any());
			verify(countingRenderer, times(5)).renderWithTemplates(any(), eq(ContentFormat.MARKDOWN), any(), any());
		}

		static final int CODES_IN_A_POST_LONGER_THAN_THE_SEARCH_CAN_MEASURE = 4000;

		static String aPostLongerThanTheSearchCanMeasure() {
			return (TWO_EMPHASES_MARKDOWN_CANNOT_READ_SIDE_BY_SIDE + " ")
					.repeat(CODES_IN_A_POST_LONGER_THAN_THE_SEARCH_CAN_MEASURE / 2);
		}

		@Test
		void theSearchRendersThePostOncePerCodeItMeasuresAndStopsWhenItsBudgetIsSpent() {
			ContentRenderingService countingRenderer = spy(renderer.contentRenderingService());
			ContentFormatConverter countingConverter =
					new ContentFormatConverter(renderer.grammarHolder(), countingRenderer,
							renderer.markdownRenderer());
			String post = aPostLongerThanTheSearchCanMeasure();
			int codesTheSearchCanAfford = ContentFormatConverter.candidatesTheSearchCanAfford(post);

			ContentFormatConverter.ConvertedContent converted = countingConverter.convert(post,
					ContentFormat.BBCODE, ContentFormat.MARKDOWN, ContentScope.FORUM);

			assertTrue(codesTheSearchCanAfford < CODES_IN_A_POST_LONGER_THAN_THE_SEARCH_CAN_MEASURE,
					"this row only measures a bound while the post really does hold more codes than the search "
							+ "can afford: " + codesTheSearchCanAfford);
			verify(countingRenderer, times(codesTheSearchCanAfford + 2))
					.renderWithTemplates(any(), eq(ContentFormat.MARKDOWN), any(), any());
			assertEquals(ContentFormatConverter.theSearchStoppedBeforeItTriedEveryCode(codesTheSearchCanAfford,
					CODES_IN_A_POST_LONGER_THAN_THE_SEARCH_CAN_MEASURE, ContentFormat.MARKDOWN),
					converted.notes().get(1),
					"a search that stopped early leaves codes as bb code nobody measured, and a note that only "
							+ "says they did not carry would be describing a measurement that never happened");
		}

		@Test
		void theSearchCannotRenderThePostOncePerCodeHoweverManyCodesTheCapAllows() {
			assertEquals(20, ContentFormatConverter.candidatesTheSearchCanAfford("x".repeat(100_000)),
					"one render per code at the /content/convert cap is 6250 full renders of a 100 KB post, which "
							+ "holds a request thread and its database connection for minutes; the budget is what "
							+ "keeps the worst post the endpoint accepts inside a couple of seconds");
			assertEquals(ContentFormatConverter.CHARACTERS_THE_SEARCH_MAY_RENDER,
					ContentFormatConverter.candidatesTheSearchCanAfford("x".repeat(1000)) * 1000,
					"the budget is a character count, not a code count, so a short post is still searched to "
							+ "exhaustion and only a long one is cut short");
		}

		@Test
		void aPostNestedFarPastWhatTheJavaStackHoldsConvertsInBothDirections() {
			String aCodeMarkdownCannotExpress = "[u]".repeat(5000) + "x" + "[/u]".repeat(5000);
			assertEquals(aCodeMarkdownCannotExpress, toMarkdown(aCodeMarkdownCannotExpress),
					"the writer walked its own tree with the Java call stack, so a post nested past about three "
							+ "thousand levels left a StackOverflowError mid-StringBuilder on a pooled request "
							+ "thread and came back to the author as a 500");

			String aLinkChainNestedPastTheStack = "[url=/a]".repeat(3000) + "x" + "[/url]".repeat(3000);
			assertEquals(visibleTextWithoutWhitespace(renderer.render(aLinkChainNestedPastTheStack)),
					visibleTextWithoutWhitespace(renderer.renderMarkdown(toMarkdown(aLinkChainNestedPastTheStack))),
					"the lane that converts a tag from the markdown its children became went five frames deep "
							+ "per level, so it broke first: at barely a thousand");

			assertEquals("*".repeat(3000) + "x" + "*".repeat(3000),
					toMarkdown("[b]".repeat(1500) + "x" + "[/b]".repeat(1500)),
					"this is the shape the defect was reported on. It stops well short of five thousand because "
							+ "commonmark renders nested emphasis recursively and the markdown lane runs out of "
							+ "stack around two thousand on its own, converter or no converter");

			assertEquals("[quote]\n".repeat(2000) + "x" + "\n[/quote]".repeat(2000),
					toBBCode("> ".repeat(2000) + "x").content(),
					"the markdown-to-bbcode writer recursed through writeChildren the same way, and a quoted "
							+ "reply chain is the one post shape that really does nest without limit");
		}

		@ParameterizedTest(name = "{0}")
		@MethodSource("constructsWhoseLineBreakBookkeepingChangesInTheOtherLane")
		void lineBreakBookkeepingIsNotWorthANote(String caseName, String bbCode) {
			ContentFormatConverter.ConvertedContent converted =
					converter.convert(bbCode, ContentFormat.BBCODE, ContentFormat.MARKDOWN, ContentScope.FORUM);

			assertNotEquals(RenderedOutputComparison.wholeVisibleText(renderer.render(bbCode)),
					RenderedOutputComparison.wholeVisibleText(renderer.renderMarkdown(converted.content())),
					"this row only proves something while the two lanes really do lay this construct out "
							+ "differently: " + caseName);
			assertEquals(List.of(), converted.notes(),
					"commonmark writes a newline between the block elements it serialises where the bbcode "
							+ "expansion writes none, so a check that counted whitespace would warn about every "
							+ "list, quote and code block a post contains, and an author who is warned about "
							+ "everything reads none of it");
		}

		static Stream<Arguments> constructsWhoseLineBreakBookkeepingChangesInTheOtherLane() {
			return Stream.of(
					arguments("a list", "[list]\n[*]one\n[*]two\n[/list]"),
					arguments("a quote", "[quote]\nquoted body\n[/quote]"),
					arguments("a code block", "[code]\nraw text\n[/code]"));
		}

		@ParameterizedTest(name = "{0}")
		@MethodSource("markdownConstructsBBCodeCannotExpress")
		void aMarkdownConstructBBCodeCannotExpressIsReported(String caseName, String markdown,
				List<String> expectedNotes, String expectedBBCode) {
			ContentFormatConverter.ConvertedContent converted = toBBCode(markdown);

			assertEquals(expectedNotes, converted.notes());
			assertEquals(expectedBBCode, converted.content());
		}

		static Stream<Arguments> markdownConstructsBBCodeCannotExpress() {
			return Stream.of(
					arguments("a code span carrying bbcode", "an `[b]example[/b]` span",
							List.of(ContentFormatConverter.INLINE_CODE_SPAN_CARRYING_BB_CODE,
									ContentFormatConverter.contentTheOtherFormatDoesNotCarry(ContentFormat.BBCODE)),
							"an `[b]example[/b]` span"),
					arguments("a code block carrying the code closer", "```\n[/code]\n```",
							List.of(ContentFormatConverter.CODE_BLOCK_CARRYING_THE_BB_CODE_CODE_CLOSER,
									ContentFormatConverter.contentTheOtherFormatDoesNotCarry(ContentFormat.BBCODE)),
							"```\n[/code]\n```"),
					arguments("an ordered list that does not start at one", "5. five\n6. six",
							List.of(ContentFormatConverter.ORDERED_LIST_NOT_STARTING_AT_ONE),
							"[list type=decimal]\n[li]five[/li]\n[li]six[/li]\n[/list]"),
					arguments("a link title", "[the docs](/wiki/Docs \"Docs\")",
							List.of(ContentFormatConverter.LINK_TITLE_TEXT),
							"[url=/wiki/Docs]the docs[/url]"));
		}

		@Test
		void aPredictedNoteAndAMeasuredOneBothStandBecauseTheyAnswerDifferentQuestions() {
			List<String> notes = toBBCode("an `[b]example[/b]` span").notes();

			assertEquals(2, notes.size(),
					"the first note is the writer predicting a construct it could not express; the second is the "
							+ "converter rendering its own output and finding the post really did change. Dropping "
							+ "the measured one whenever a predicted one fired would hide an unrelated divergence "
							+ "behind an unrelated prediction: " + notes);
		}

		@Test
		void askingForNoConversionHandsTheSourceBack() {
			assertEquals("[b]bold[/b]",
					converter.convert("[b]bold[/b]", ContentFormat.BBCODE, ContentFormat.BBCODE,
							ContentScope.FORUM).content());
			assertEquals("", converter.convert(null, ContentFormat.BBCODE, ContentFormat.MARKDOWN,
					ContentScope.FORUM).content());
		}

		static final Set<String> ROWS_THE_CONVERSION_ITSELF_RESHAPES = Set.of(
				"base.emit.list", "attackB.B11.list.bbcode", "d10.special-path-in-body-text",
				"shape.S5.cool.in.quote", "shape.S7.cool.in.bbcode.code",
				"d11.list-item-holding-an-image", "d11.list-item-holding-a-youtube-embed",
				"d11.list-item-holding-a-code-sample", "d11.list-item-holding-an-email-address");

		static final int GOLDEN_ROWS_THE_MARKDOWN_LANE_CANNOT_RENDER_LIKE_THE_BBCODE_LANE = 123;

		static final int GOLDEN_ROWS_THE_CONVERSION_CARRIES_ACROSS_THE_LANES = 92;

		static String visibleTextThroughTheMarkdownLane(String markdown) {
			return RenderedOutputComparison.wholeVisibleText(renderer.renderMarkdown(markdown)).stripTrailing();
		}

		static Stream<Arguments> goldenCorpusCases() throws IOException {
			return Parity.goldenCorpusCases();
		}

		@ParameterizedTest(name = "{0}")
		@MethodSource("goldenCorpusCases")
		void aConvertedGoldenCaseReadsTheSameThroughTheMarkdownLane(String id, String input, String expected,
				List<String> labels, ContentFormat lane) {
			if (lane != ContentFormat.BBCODE)
				return;
			String throughTheBBCodeLane = RenderedOutputComparison.wholeVisibleText(expected);
			String converted = toMarkdown(input);
			String throughTheMarkdownLane = visibleTextThroughTheMarkdownLane(converted);
			if (throughTheBBCodeLane.equals(throughTheMarkdownLane))
				return;

			String describedDivergence = " ('" + id + "'):\n  bbcode lane:   "
					+ RenderedOutputComparison.visible(throughTheBBCodeLane) + "\n  converted:     "
					+ RenderedOutputComparison.visible(converted) + "\n  markdown lane: "
					+ RenderedOutputComparison.visible(throughTheMarkdownLane);
			if (ROWS_THE_CONVERSION_ITSELF_RESHAPES.contains(id)) {
				assertEquals(charactersWithoutWhitespace(throughTheBBCodeLane),
						charactersWithoutWhitespace(throughTheMarkdownLane),
						"the only reshaping this carve-out allows is line-break bookkeeping: commonmark writes a "
								+ "newline between the block elements it serialises -- between two list items, "
								+ "around a blockquote, after a code block's literal -- where the bbcode expansion "
								+ "of the same construct writes none. Nothing an author typed may move"
								+ describedDivergence);
				return;
			}
			assertNotEquals(throughTheBBCodeLane, visibleTextThroughTheMarkdownLane(input),
					"converting a post and rendering it in the other lane must show the reader the same text. "
							+ "This row is carved out only because the markdown lane cannot render its source the "
							+ "way the bbcode lane does even before conversion -- but it can, so the divergence is "
							+ "the converter's and there is nothing to exempt it" + describedDivergence);
			assertTrue(charactersTheLanesDoNotShare(throughTheBBCodeLane, throughTheMarkdownLane)
							<= charactersTheLanesDoNotShare(throughTheBBCodeLane,
									visibleTextThroughTheMarkdownLane(input)),
					"a row the markdown lane already renders differently is exempt from matching, but not from "
							+ "getting worse: converting it must not move it further from what the bbcode lane "
							+ "shows than the untouched source already was" + describedDivergence);
		}

		static int charactersTheLanesDoNotShare(String oneLane, String theOther) {
			return RenderedOutputComparison.charactersMissingFrom(oneLane, theOther).length()
					+ RenderedOutputComparison.charactersMissingFrom(theOther, oneLane).length();
		}

		static String charactersWithoutWhitespace(String text) {
			StringBuilder kept = new StringBuilder();
			for (char character : text.toCharArray())
				if (!Character.isWhitespace(character))
					kept.append(character);
			return kept.toString();
		}

		@Test
		void theCarveOutsAreCountedSoNeitherCanGrowInSilence() throws IOException {
			Set<String> carvedOutRowsTheCorpusNoLongerHas = new LinkedHashSet<>(ROWS_THE_CONVERSION_ITSELF_RESHAPES);
			int carriedAcross = 0;
			int theMarkdownLaneCannotRender = 0;
			for (Parity.GoldenCase row : Parity.readGoldenCorpus()) {
				carvedOutRowsTheCorpusNoLongerHas.remove(row.id());
				if (row.lane() != ContentFormat.BBCODE || ROWS_THE_CONVERSION_ITSELF_RESHAPES.contains(row.id()))
					continue;
				String throughTheBBCodeLane = RenderedOutputComparison.wholeVisibleText(row.expected());
				if (visibleTextThroughTheMarkdownLane(toMarkdown(row.input())).equals(throughTheBBCodeLane))
					carriedAcross++;
				else
					theMarkdownLaneCannotRender++;
			}

			assertTrue(carvedOutRowsTheCorpusNoLongerHas.isEmpty(),
					"a carve-out naming a row the corpus no longer contains exempts nothing and reads as though "
							+ "the divergence is still covered: " + carvedOutRowsTheCorpusNoLongerHas);
			assertEquals(GOLDEN_ROWS_THE_CONVERSION_CARRIES_ACROSS_THE_LANES, carriedAcross,
					"this is how many corpus rows the property actually holds for; a change that shrinks it has "
							+ "moved rows into the carve-out, which is where a regression would hide");
			assertEquals(GOLDEN_ROWS_THE_MARKDOWN_LANE_CANNOT_RENDER_LIKE_THE_BBCODE_LANE, theMarkdownLaneCannotRender,
					"and this is how many rows the markdown lane cannot render like the bbcode lane whatever the "
							+ "converter does; it is a property of the two lanes, so it may only change when a "
							+ "lane does");
		}
	}

	@Nested
	class PageAssembly {

		static final String HEADING_SELECTOR = "h1,h2,h3,h4,h5,h6";

		static CmsPageRenderer pageRenderer;

		@BeforeAll
		static void loadTheRealSeededEngine() {
			Map<String, BBCodeConfig> grammar = seededBBCodeGrammar();
			BBCodeGrammarHolder grammarHolder = grammarHolder();
			RenderedTextEnricher enricher = enricher();
			ContentOutputSanitizer sanitizer = sanitizer(grammarHolder, enricher);
			enricher.registerSmilies(seededSmilies());
			SourceReferenceService handler = new SourceReferenceService(List.of(messageResolver()), grammarHolder);
			BBCodeDataProvider bbCodeDataProvider = mock(BBCodeDataProvider.class);
			when(bbCodeDataProvider.getBBCodeConfig()).thenReturn(grammar);
			BBCodeGrammarLoader service = new BBCodeGrammarLoader(bbCodeDataProvider, handler, sanitizer, grammarHolder, enricher);
			service.loadBBCodeConfig();
			BBCodeRenderer bbCodeRenderer = new BBCodeRenderer(grammarHolder, handler, templateExpansion(grammarHolder));
			MarkdownRenderer markdownRenderer =
					new MarkdownRenderer(grammarHolder, enricher, handler, bbCodeRenderer, templateExpansion(grammarHolder));
			pageRenderer = new CmsPageRenderer(new ContentRenderingService(bbCodeRenderer, markdownRenderer, sanitizer, handler));
		}


		private WikiPage assemble(String source) {
			return pageRenderer.previewPage("Wiki", "Title", "Wiki:Title", source, "BBCODE", ContentScope.WIKI);
		}

		private WikiPage assembleMarkdown(String source) {
			return pageRenderer.previewPage("Wiki", "Title", "Wiki:Title", source, "MARKDOWN", ContentScope.WIKI);
		}

		private List<String> headingIds(WikiPage page) {
			return page.getHeadings().stream().map(WikiPage.Heading::id).toList();
		}

		private List<String> headingTexts(WikiPage page) {
			return page.getHeadings().stream().map(WikiPage.Heading::text).toList();
		}

		@Test
		void aHeadingWhoseMarkupCarriesAnEqualsSignStillGetsItsAnchor() {
			WikiPage page = assemble("<h2><span title=\"grid=2\">Title</span></h2>");

			assertEquals(List.of("title"), headingIds(page));
			assertEquals("<h2 id=\"title\"><span title=\"grid=2\">Title</span></h2>", page.getContentParsed(),
					"the deleted scanner asked whether the raw attribute text contained the substring \"id=\", so "
							+ "any attribute value carrying an equals sign -- grid=2, type=decimal -- read as an "
							+ "already-anchored heading and the outline entry linked to nothing. The class "
							+ "allowlist now refuses a class value shaped like that, so the hazard is carried by "
							+ "an attribute that still survives");
		}

		@Test
		void aTableOfContentsMarkerMentionedInProseNoLongerSwitchesTheOutlineOn() {
			WikiPage page = assemble("[h2]Only heading[/h2]\nprose mentioning bb-toc in passing");

			assertFalse(page.isToc(),
					"the deleted rule was html.contains(\"bb-toc\"), which matched the author's body text and "
							+ "forced a table of contents onto a one-heading page: " + page.getContentParsed());
		}

		@Test
		void theTableOfContentsMarkerElementStillSwitchesTheOutlineOn() {
			WikiPage page = assemble("[toc][h2]Only heading[/h2]");

			assertTrue(page.isToc(),
					"a class selector that never matches the real marker would make the previous assertion pass "
							+ "for the wrong reason: " + page.getContentParsed());
		}

		@Test
		void aSuppressionMarkerMentionedInProseNoLongerSwitchesTheOutlineOff() {
			WikiPage page = assemble("[h1]A[/h1][h1]B[/h1][h1]C[/h1][h1]D[/h1]\nprose mentioning bb-notoc");

			assertEquals(4, page.getHeadings().size());
			assertTrue(page.isToc(),
					"the deleted rule was !html.contains(\"bb-notoc\"), so writing about the suppression marker "
							+ "silently removed the outline from a page that had earned one: "
							+ page.getContentParsed());
		}

		@Test
		void theSuppressionMarkerElementStillSwitchesTheOutlineOff() {
			WikiPage page = assemble("[notoc][h1]A[/h1][h1]B[/h1][h1]C[/h1][h1]D[/h1]");

			assertFalse(page.isToc(),
					"a class selector that never matches the real suppression marker would make the previous "
							+ "assertion pass for the wrong reason: " + page.getContentParsed());
		}

		@Test
		void theUnmarkedOutlineThresholdIsFourHeadings() {
			assertFalse(assemble("[h1]A[/h1][h1]B[/h1][h1]C[/h1]").isToc(),
					"three headings do not earn an unmarked table of contents");
			assertTrue(assemble("[h1]A[/h1][h1]B[/h1][h1]C[/h1][h1]D[/h1]").isToc(),
					"four headings do");
		}

		@Test
		void repeatedHeadingTextGetsDistinctAnchorsInDocumentOrder() {
			WikiPage page = assemble("[h2]Items[/h2][h3]Items[/h3][h2]Items[/h2]");

			assertEquals(List.of("items", "items-2", "items-3"), headingIds(page),
					"the anchor suffix counts occurrences of the slug, not of the level, so two headings that "
							+ "differ only in level must still not collide");
			assertEquals(List.of(2, 3, 2), page.getHeadings().stream().map(WikiPage.Heading::level).toList(),
					"the level comes from the tag name; reading it from anywhere else silently flattens the "
							+ "outline");
		}

		@Test
		void everyHeadingLevelIsReadFromItsOwnTagName() {
			WikiPage page = assemble("[h1]A[/h1][h2]B[/h2][h3]C[/h3][h4]D[/h4][h5]E[/h5][h6]F[/h6]");

			assertEquals(List.of(1, 2, 3, 4, 5, 6),
					page.getHeadings().stream().map(WikiPage.Heading::level).toList());
		}

		@Test
		void theAnchorSlugKeepsItsPunctuationAndCaseFolding() {
			WikiPage page = assemble("[h2]  Hello, World! -- 42 --  [/h2]");

			assertEquals(List.of("hello-world-42"), headingIds(page),
					"the slug lowercases, collapses every run of non-alphanumerics to a single dash and trims "
							+ "the dashes off both ends; this is the anchor URL readers bookmark");
		}

		@Test
		void headingTextIsTakenFromTheParsedTreeSoNestedMarkupSurvives() {
			WikiPage page = assemble("[h2]a [i]b[/i] <span title=\"x > y\">c</span>[/h2]");

			assertEquals(List.of("a b c"), headingTexts(page),
					"reading only the heading's own text nodes drops every nested element's words from the "
							+ "outline, and rebuilding the text by stripping <[^>]+> from the serialised markup "
							+ "breaks on the first attribute value that contains a greater-than sign");
			assertEquals(List.of("a-b-c"), headingIds(page));
		}

		@Test
		void anEscapedCharacterInTheHeadingIsDecodedForTheOutlineButLeftEncodedInTheMarkup() {
			WikiPage page = assemble("[h2]Tools & Toys[/h2]");

			assertEquals(List.of("Tools & Toys"), headingTexts(page),
					"the outline is a display string, so it carries the decoded character");
			assertEquals(List.of("tools-toys"), headingIds(page));
			assertTrue(page.getContentParsed().contains("Tools &amp; Toys"),
					"decoding for the outline must not leak back into the markup: " + page.getContentParsed());
		}

		@Test
		void aLineBreakInsideAHeadingSeparatesWordsInsteadOfWeldingThem() {
			WikiPage page = assemble("[h2]first\nsecond[/h2]");

			assertTrue(page.getContentParsed().contains("first<br>second"),
					"the bbcode lane turns the authored newline into a break element: " + page.getContentParsed());
			assertEquals(List.of("first second"), headingTexts(page),
					"stripping the break element as if it were zero-width produced the outline entry "
							+ "'firstsecond' and the anchor '#firstsecond'");
			assertEquals(List.of("first-second"), headingIds(page));
		}

		@Test
		void repeatedWhitespaceInsideAHeadingCollapsesInTheOutlineEntry() {
			WikiPage page = assemble("[h2]spaced    out[/h2]");

			assertEquals(List.of("spaced out"), headingTexts(page),
					"the outline entry is a label rendered outside a whitespace-preserving context, so it must "
							+ "read the way the heading itself renders");
			assertEquals(List.of("spaced-out"), headingIds(page));
			assertTrue(page.getContentParsed().contains(">spaced    out<"),
					"collapsing for the outline must not rewrite the heading the reader sees: "
							+ page.getContentParsed());
		}

		@Test
		void aPageWithNoHeadingsCarriesNoOutlineAndNoTableOfContents() {
			WikiPage page = assemble("just prose");

			assertEquals(List.of(), page.getHeadings());
			assertFalse(page.isToc());
			assertEquals("just prose", page.getContentParsed());
		}

		@Test
		void theMarkdownLaneIsAssembledThroughTheSameOutlineRules() {
			WikiPage page = assembleMarkdown("# One\n\n## Two\n");

			assertEquals(List.of("one", "two"), headingIds(page));
			assertTrue(page.getContentParsed().contains("<h1 id=\"one\">One</h1>"), page.getContentParsed());
			assertTrue(page.getContentParsed().contains("<h2 id=\"two\">Two</h2>"), page.getContentParsed());
		}

		static Stream<Arguments> goldenCorpusCases() throws IOException {
			return Parity.goldenCorpusCases();
		}

		static final Set<String> ROWS_THE_ASSEMBLY_REPARSE_RESHAPES =
				Set.of("d10.special-href-beside-a-foster-parented-smiley");

		private String withoutHeadingAnchors(String html) {
			org.jsoup.nodes.Document document = Jsoup.parseBodyFragment(html);
			document.outputSettings(new org.jsoup.nodes.Document.OutputSettings().prettyPrint(false));
			for (Element heading : document.body().select(HEADING_SELECTOR))
				heading.removeAttr("id");
			return document.body().html();
		}

		private String asTheAssemblyReparseRebuildsIt(String html) {
			org.jsoup.nodes.Document document = Jsoup.parseBodyFragment(html);
			document.outputSettings(new org.jsoup.nodes.Document.OutputSettings().prettyPrint(false));
			return document.body().html();
		}

		@ParameterizedTest(name = "{0}")
		@MethodSource("goldenCorpusCases")
		void assemblingAGoldenCaseChangesNothingButTheHeadingAnchors(String id, String input, String expected,
				List<String> labels, ContentFormat lane) {
			WikiPage page = pageRenderer.previewPage("Wiki", "Title", "Wiki:Title", input,
					lane.name(), ContentScope.WIKI);

			if (ROWS_THE_ASSEMBLY_REPARSE_RESHAPES.contains(id)) {
				assertNotEquals(expected, page.getContentParsed(),
						"this row is only worth carving out while assembly really does reshape it; if the "
								+ "sanitiser stops emitting inline content inside a table, drop it from "
								+ "ROWS_THE_ASSEMBLY_REPARSE_RESHAPES rather than leave a carve-out that covers "
								+ "nothing ('" + id + "')");
				assertEquals(asTheAssemblyReparseRebuildsIt(expected), page.getContentParsed(),
						"and the only reshaping allowed is the one a plain re-parse performs. The smiley pass "
								+ "replaces a text node inside <table> with a <span> after the tree was built, and "
								+ "jsoup keeps text inside a table but foster-parents elements out of one, so "
								+ "decorateHeadings' unconditional re-parse hoists the smiley back out. The forum "
								+ "lane no longer re-parses at all, so this shape is where the two lanes differ -- "
								+ "which is at least a property of the lane now, instead of a property of whether "
								+ "the post happened to also mention a legacy wiki link ('" + id + "')");
				return;
			}
			if (page.getHeadings().isEmpty()) {
				assertEquals(expected, page.getContentParsed(),
						"page assembly parses the sanitiser's own output and serialises it again, so its output "
								+ "settings must match the sanitiser's byte for byte; pretty-printing or emitting "
								+ "the whole document instead of the body would rewrite every wiki, project and "
								+ "resource page in the site ('" + id + "')");
				return;
			}
			assertEquals(expected, withoutHeadingAnchors(page.getContentParsed()),
					"a decorated page must differ from the sanitiser's bytes by nothing but the heading anchors "
							+ "('" + id + "'):\n  golden:    " + expected + "\n  assembled: "
							+ page.getContentParsed());
		}

		@Test
		void theGoldenCorpusReachesBothTheDecoratedAndTheUntouchedAssemblyPaths() throws IOException {
			int withHeadings = 0;
			int withoutHeadings = 0;
			Set<String> carvedOutRowsTheCorpusNoLongerHas =
					new LinkedHashSet<>(ROWS_THE_ASSEMBLY_REPARSE_RESHAPES);
			for (Parity.GoldenCase row : Parity.readGoldenCorpus()) {
				carvedOutRowsTheCorpusNoLongerHas.remove(row.id());
				WikiPage page = pageRenderer.previewPage("Wiki", "Title", "Wiki:Title", row.input(),
						row.lane().name(), ContentScope.WIKI);
				if (page.getHeadings().isEmpty())
					withoutHeadings++;
				else
					withHeadings++;
			}

			assertTrue(carvedOutRowsTheCorpusNoLongerHas.isEmpty(),
					"a carve-out naming a row the corpus no longer contains exempts nothing and reads as though "
							+ "the divergence is still covered: " + carvedOutRowsTheCorpusNoLongerHas);
			assertTrue(withoutHeadings > 200,
					"assemblingAGoldenCaseChangesNothingButTheHeadingAnchors only proves the serialisation "
							+ "boundary while most rows pass through it untouched: " + withoutHeadings);
			assertTrue(withHeadings > 0,
					"and it only proves the anchor-stripping inverse while some row actually grows an anchor: "
							+ withHeadings);
		}
	}

	record Renderer(BBCodeDataProvider bbCodeDataProvider, BBCodeGrammarHolder grammarHolder, BBCodeGrammarLoader service,
			SourceReferenceService handler, BBCodeRenderer bbCodeRenderer, MarkdownRenderer markdownRenderer,
			ContentRenderingService contentRenderingService) {
		void useGrammar(Map<String, BBCodeConfig> grammar) {
			when(bbCodeDataProvider.getBBCodeConfig()).thenReturn(grammar);
			service.loadBBCodeConfig();
		}

		String render(String source) {
			return contentRenderingService.render(source, ContentFormat.BBCODE, ContentScope.FORUM);
		}

		String render(String source, OffsetDateTime quotingCreatedTs) {
			return contentRenderingService.render(source, ContentFormat.BBCODE, ContentScope.FORUM,
					quotingCreatedTs);
		}

		String renderMarkdown(String source) {
			return contentRenderingService.render(source, ContentFormat.MARKDOWN, ContentScope.FORUM);
		}
	}

	static Renderer buildRenderer(BBCodeDataProvider provider, ContentTagResolver... resolvers) {
		BBCodeGrammarHolder grammarHolder = new BBCodeGrammarHolder();
		RenderedTextEnricher enricher = enricher();
		return buildRenderer(provider, grammarHolder, enricher, sanitizer(grammarHolder, enricher), resolvers);
	}

	static Renderer buildRenderer(BBCodeDataProvider provider, BBCodeGrammarHolder grammarHolder,
			RenderedTextEnricher enricher, ContentOutputSanitizer sanitizer, ContentTagResolver... resolvers) {
		SourceReferenceService handler = new SourceReferenceService(List.of(resolvers), grammarHolder);
		when(provider.theDeclaredListStyleTypes()).thenReturn(seededListStyleTypes());
		when(provider.compileTheDeclaredValuePolicies()).thenReturn(seededAttributeValuePolicies());
		BBCodeGrammarLoader service = new BBCodeGrammarLoader(provider, handler, sanitizer, grammarHolder, enricher);
		TemplateExpansion templateExpansion = templateExpansion(grammarHolder);
		BBCodeRenderer bbCodeRenderer = new BBCodeRenderer(grammarHolder, handler, templateExpansion);
		MarkdownRenderer markdownRenderer =
				new MarkdownRenderer(grammarHolder, enricher, handler, bbCodeRenderer, templateExpansion);
		return new Renderer(provider, grammarHolder, service, handler, bbCodeRenderer, markdownRenderer,
				new ContentRenderingService(bbCodeRenderer, markdownRenderer, sanitizer, handler));
	}

	@Nested
	class RetiredCodeRewriting {

		private RetiredBBCodeRewriter rewriter;

		@BeforeEach
		void loadTheRealSeededGrammar() {
			BBCodeGrammarHolder grammarHolder = grammarHolder();
			Renderer built = buildRenderer(mock(BBCodeDataProvider.class), grammarHolder, enricher(),
					sanitizer(grammarHolder, enricher()), messageResolver());
			built.useGrammar(seededBBCodeGrammar());
			rewriter = new RetiredBBCodeRewriter(grammarHolder);
		}

		@Test
		void aRetiredCodeTheAuthorMeantAsMarkupIsRewrittenToItsModernForm() {
			assertEquals("[align=center]centered[/align]", rewriter.rewriteRetiredCodes("[center]centered[/center]"));
		}

		@Test
		void aRetiredLinkKeepsTheDestinationItsAuthorTyped() {
			assertEquals("[url=/wiki/Steve]steve[/url]",
					rewriter.rewriteRetiredCodes("[iurl=/wiki/Steve]steve[/iurl]"));
		}

		@Test
		void aRetiredCodeInsideAVerbatimBlockIsLeftExactlyAsItsAuthorTypedIt() {
			String documenting = "[code][center]centered[/center][/code]";

			assertEquals(documenting, rewriter.rewriteRetiredCodes(documenting),
					"[code] declares process_content=false, so the renderer shows that tag as literal text rather "
							+ "than parsing it; rewriting it would silently edit words the author typed, which is "
							+ "the whole reason this runs through the parser instead of a regex");
		}
	}

	static void registerTheLaneDispatcherOf(Renderer built) {
		built.handler().registerSourceBodyRenderer((rawBody, contentFormat, quotingCreatedTs) ->
				contentFormat == ContentFormat.MARKDOWN
						? built.markdownRenderer().render(rawBody, quotingCreatedTs, ContentScope.FORUM, Map.of())
						: built.bbCodeRenderer().render(rawBody, quotingCreatedTs, ContentScope.FORUM, Map.of()));
	}

	static TemplateExpansion templateExpansion(BBCodeGrammarHolder grammarHolder) {
		TemplateExpander expander = mock(TemplateExpander.class);
		when(expander.theFileReferencesResolvedIn(any(), any())).thenAnswer(call -> call.getArgument(0));
		return new TemplateExpansion(expander, grammarHolder);
	}

	static BBCodeGrammarHolder grammarHolder() {
		return new BBCodeGrammarHolder();
	}

	private static ContentOutputSanitizer sanitizer() {
		return sanitizer(grammarHolder(), enricher());
	}

	static RenderedTextEnricher enricher() {
		return new RenderedTextEnricher(mock(SmileyDboMapper.class));
	}

	static ContentOutputSanitizer sanitizer(BBCodeGrammarHolder grammarHolder, RenderedTextEnricher enricher) {
		ContentOutputSanitizer sanitizer = new ContentOutputSanitizer(enricher, grammarHolder);
		grammarHolder.publish(
				theGrammarCarryingOnly(sanitizer.theCustomPropertiesPreparedFrom(seededBBCodeGrammar())));
		return sanitizer;
	}

	static BBCodeGrammar theGrammarCarryingOnly(BBCodeGrammar.PreparedCustomProperties customProperties) {
		BBCodeGrammar nothingIsPublishedYet = BBCodeGrammar.theGrammarThatDeclaresNothing();
		return new BBCodeGrammar(nothingIsPublishedYet.configs(),
				nothingIsPublishedYet.listStyleTypesThatNumberTheirItems(),
				nothingIsPublishedYet.listStyleValuePolicy(), nothingIsPublishedYet.implicitItemExpansions(),
				nothingIsPublishedYet.sourceReferences(), customProperties);
	}
}
