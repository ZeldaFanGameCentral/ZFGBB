package com.zfgc.zfgbb;

import com.zfgc.zfgbb.content.renderer.ContentOutputSanitizer;
import com.zfgc.zfgbb.content.renderer.ContentRenderingService;
import com.zfgc.zfgbb.content.renderer.ContentTagResolver;
import com.zfgc.zfgbb.content.renderer.RenderedTextEnricher;
import com.zfgc.zfgbb.content.renderer.SourceReferenceService;
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
import org.springframework.core.io.ByteArrayResource;
import org.springframework.core.io.DefaultResourceLoader;
import org.springframework.core.io.Resource;
import tools.jackson.core.type.TypeReference;
import tools.jackson.databind.ObjectMapper;
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
import com.zfgc.zfgbb.dao.forum.BBCodeConfigDao;
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
import com.zfgc.zfgbb.dao.forum.AttributeDataTypeDao;
import com.zfgc.zfgbb.dao.forum.AttributeValueMappingDao;
import com.zfgc.zfgbb.dao.forum.ListStyleTypeDao;
import com.zfgc.zfgbb.dao.forum.BBCodeAttributeDao;
import com.zfgc.zfgbb.dao.forum.BBCodeAttributeModeDao;
import com.zfgc.zfgbb.dao.forum.SmileyDao;
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
import com.zfgc.zfgbb.content.renderer.LinkPolicy;

class BbcodeServiceTest {

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
		return new RenderedTextEnricher(mock(SmileyDao.class));
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
