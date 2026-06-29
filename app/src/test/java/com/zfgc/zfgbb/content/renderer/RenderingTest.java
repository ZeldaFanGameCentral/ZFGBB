package com.zfgc.zfgbb.content.renderer;

import static com.zfgc.zfgbb.testsupport.BBCodeTestFixtures.codeConfig;
import static com.zfgc.zfgbb.testsupport.BBCodeTestFixtures.quoteConfig;
import static com.zfgc.zfgbb.testsupport.BBCodeTestFixtures.resolved;
import static com.zfgc.zfgbb.testsupport.BBCodeTestFixtures.simpleTag;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.params.provider.Arguments.arguments;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.contains;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.spy;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.lang.reflect.Field;
import java.time.OffsetDateTime;
import java.time.ZoneOffset;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.NavigableMap;
import java.util.Set;
import java.util.TreeMap;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import java.util.stream.Stream;

import org.apache.commons.lang3.mutable.MutableInt;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.springframework.test.util.ReflectionTestUtils;
import org.springframework.util.ReflectionUtils;

import com.zfgc.zfgbb.content.renderer.BBCodeOutputSanitizer.SmileyToken;
import com.zfgc.zfgbb.dbo.MessageDbo;
import com.zfgc.zfgbb.dbo.MessageDboExample;
import com.zfgc.zfgbb.dbo.MessageHistoryDbo;
import com.zfgc.zfgbb.dbo.MessageHistoryDboExample;
import com.zfgc.zfgbb.dbo.UserDbo;
import com.zfgc.zfgbb.dbo.UserDboExample;
import com.zfgc.zfgbb.mappers.MessageDboMapper;
import com.zfgc.zfgbb.mappers.MessageHistoryDboMapper;
import com.zfgc.zfgbb.mappers.UserDboMapper;
import com.zfgc.zfgbb.model.forum.AttributeDataType;
import com.zfgc.zfgbb.model.forum.BBCodeAttribute;
import com.zfgc.zfgbb.model.forum.BBCodeAttributeMode;
import com.zfgc.zfgbb.model.forum.BBCodeConfig;

class RenderingTest {

	@Nested
	class Bbcode {
		static BBCodeService service = new BBCodeService();
		static BBCodeConfig bbCodeQuote = null;

		@BeforeAll
		public static void initialize() {
			service.outputSanitizer = new BBCodeOutputSanitizer();

			initQuote();
			initUrl();
			initImg();
			service.validBbCodes.put("CODE",
					simpleTag("code", "<span class=\"bbcode-code-header\">Code</span><span class=\"bbcode-code-block\">",
							"</span>", false));
			service.validBbCodes.put("B", simpleTag("b", "<span class=\"bbcode-b\">", "</span>", true));
			service.validBbCodes.put("I", simpleTag("i", "<span class=\"bbcode-i\">", "</span>", true));
			service.validBbCodes.put("U", simpleTag("u", "<span class=\"bbcode-u\">", "</span>", true));
			service.validBbCodes.put("H2", simpleTag("h2", "<h2 class=\"bbcode-h2\">", "</h2>", true));
		}

		private static void initQuote() {
			bbCodeQuote = new BBCodeConfig();
			bbCodeQuote.setAllAttributeNamesAsString("author=,link=,time=,=");

			BBCodeAttributeMode mode0 = new BBCodeAttributeMode();
			mode0.setOpenTag(
					"<span class=\"bbcode-quote-header\"><a href=\"{{1}}\">Authored by {{0}} at {{2}}</a></span><span class=\"bbcode-quote-block\">");
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

			BBCodeAttribute time = mock(BBCodeAttribute.class);
			when(time.getAttributeIndex()).thenReturn("{{2}}");
			when(time.getAttributeDataType()).thenReturn(AttributeDataType.TIMESTAMP.ordinal());
			when(time.getName()).thenReturn("time=");
			when(time.createDate(any(String.class))).thenReturn("05/12/2017 01:28:23");
			when(time.transformValue(any(String.class))).thenCallRealMethod();
			Field dataType = ReflectionUtils.findField(BBCodeAttribute.class, "dataType");
			dataType.setAccessible(true);
			ReflectionUtils.setField(dataType, time, AttributeDataType.TIMESTAMP);

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
			mode1.setOpenTag("<span class=\"bbcode-quote-header\">Authored by {{0}}</span><span class=\"bbcode-quote-block\">");
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
					"<span class=\"bbcode-quote-header\">Authored by {{0}}</span><span class=\"bbcode-quote-block\">");
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
			empty.setOpenTag("<span class=\"bbcode-quote-header\">Quote</span><span class=\"bbcode-quote-block\">");
			empty.setCloseTag("</span>");
			bbCodeQuote.getAttributeConfig().put("", empty);

			service.validBbCodes.put("QUOTE", bbCodeQuote);
		}

		private static void initUrl() {
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

			service.validBbCodes.put("URL", bbCodeUrl);
		}

		private static void initImg() {
			BBCodeConfig bbCodeImg = new BBCodeConfig();
			bbCodeImg.setAllAttributeNamesAsString("");
			bbCodeImg.setCode("img");
			bbCodeImg.setProcessContentFlag(false);
			bbCodeImg.setEndTag("</span>");

			BBCodeAttributeMode none = new BBCodeAttributeMode();
			none.setOpenTag("<span class=\"bbcode-img\"><img src=\"{{c}}\">");
			none.setCloseTag("</span>");
			none.setContentIsAttributeFlag(true);
			none.setOutputContentFlag(false);
			bbCodeImg.getAttributeConfig().put("", none);

			service.validBbCodes.put("IMG", bbCodeImg);
		}

		@ParameterizedTest
		@MethodSource("quoteRenderingCases")
		void parseTextRendersQuoteModes(String caseName, String input, String expected) {
			assertEquals(expected, service.parseText(input));
		}

		static Stream<Arguments> quoteRenderingCases() {
			return Stream.of(
					arguments("parseTextMode1Code",
							"[quote author=test]test[/quote]",
							"<span class=\"bbcode-quote-header\">Authored by test</span><span class=\"bbcode-quote-block\">test</span>"),
					arguments("parseTextMode0Code",
							"[quote author=MG-Zero link=/thread/99 time=1494552503000]test[/quote]",
							"<span class=\"bbcode-quote-header\"><a href=\"/thread/99\">Authored by MG-Zero at 05/12/2017 01:28:23</a></span><span class=\"bbcode-quote-block\">test</span>"),
					arguments("parseTextQuoteEmbeddedMode0",
							"[quote author=MG-Zero link=/thread/99 time=1494552503000][quote author=MG-Zero link=/thread/99 time=1494552503000]test[/quote][/quote]",
							"<span class=\"bbcode-quote-header\"><a href=\"/thread/99\">Authored by MG-Zero at 05/12/2017 01:28:23</a></span><span class=\"bbcode-quote-block\"><span class=\"bbcode-quote-header\"><a href=\"/thread/99\">Authored by MG-Zero at 05/12/2017 01:28:23</a></span><span class=\"bbcode-quote-block\">test</span></span>"),
					arguments("parseTextQuoteNoParam",
							"[quote]test[/quote]",
							"<span class=\"bbcode-quote-header\">Quote</span><span class=\"bbcode-quote-block\">test</span>"),
					arguments("parseTextQuoteTwo",
							"[quote author=MG-Zero link=/thread/99 time=1494552503000]test[/quote][quote author=MG-Zero link=/thread/99 time=1494552503000]test[/quote]",
							"<span class=\"bbcode-quote-header\"><a href=\"/thread/99\">Authored by MG-Zero at 05/12/2017 01:28:23</a></span><span class=\"bbcode-quote-block\">test</span><span class=\"bbcode-quote-header\"><a href=\"/thread/99\">Authored by MG-Zero at 05/12/2017 01:28:23</a></span><span class=\"bbcode-quote-block\">test</span>"),
					arguments("parseTextNamelessAttribute",
							"[quote=MGZero]test[/quote]",
							"<span class=\"bbcode-quote-header\">Authored by MGZero</span><span class=\"bbcode-quote-block\">test</span>"),
					arguments("parseTextUnmatchedAttributeModeRoundTrips",
							"[quote author=a link=b]test[/quote]",
							"[quote author=a link=b]test[/quote]"),
					arguments("parseTextUnmatchedModeKeepsSurroundingStyling",
							"[b][quote link=b]Game Download[/b] after",
							"<span class=\"bbcode-b\">[quote link=b]Game Download</span> after"));
		}

		@ParameterizedTest
		@MethodSource("codeRenderingCases")
		void parseTextRendersCodeBlocksLiterally(String caseName, String input, String expected) {
			assertEquals(expected, service.parseText(input));
		}

		static Stream<Arguments> codeRenderingCases() {
			return Stream.of(
					arguments("parseTextCode",
							"[code]test[/code]",
							"<span class=\"bbcode-code-header\">Code</span><span class=\"bbcode-code-block\">test</span>"),
					arguments("parseTextCodeEmbedded",
							"[code]test[code]test[quote][/code]",
							"<span class=\"bbcode-code-header\">Code</span><span class=\"bbcode-code-block\">test[code]test[quote]</span>"),
					arguments("parseTextCodeEscapesMarkup",
							"[code]<map><version>1.0</version></map>[/code]",
							"<span class=\"bbcode-code-header\">Code</span><span class=\"bbcode-code-block\">"
									+ "&lt;map&gt;&lt;version&gt;1.0&lt;/version&gt;&lt;/map&gt;</span>"),
					arguments("parseTextCodeConvertsBrToNewline",
							"[code]line1<br/>line2<br />line3[/code]",
							"<span class=\"bbcode-code-header\">Code</span><span class=\"bbcode-code-block\">"
									+ "line1\nline2\nline3</span>"));
		}

		@ParameterizedTest
		@MethodSource("urlAndImgRenderingCases")
		void parseTextRendersUrlAndImgMarkup(String caseName, String input, String expected) {
			assertEquals(expected, service.parseText(input));
		}

		static Stream<Arguments> urlAndImgRenderingCases() {
			return Stream.of(
					arguments("caseMismatchTest",
							"[IMG]http://img.photobucket.com/albums/v191/legofreak1988/avy-sig/corpse.jpg[/img]",
							"<span class=\"bbcode-img\"><img src=\"http://img.photobucket.com/albums/v191/legofreak1988/avy-sig/corpse.jpg\"></span>"),
					arguments("parseTextUrlContent",
							"[url]http://zfgc.com[/url]",
							"<a href=\"http://zfgc.com\">http://zfgc.com</a>"),
					arguments("parseTextUrlContentEmbedded",
							"[url][b]http://zfgc.com[/b][/url]",
							"<a href=\"http://zfgc.com\"><span class=\"bbcode-b\">http://zfgc.com</span></a>"),
					arguments("parseTextUrlImgEmbedded",
							"[url=https://somelink.com][img]https://someimg.jpg[/img][/url]",
							"<a href=\"https://somelink.com\"><span class=\"bbcode-img\"><img src=\"https://someimg.jpg\"></span></a>"),
					arguments("parseTextImg",
							"[img]http://zfgc.com[/img]",
							"<span class=\"bbcode-img\"><img src=\"http://zfgc.com\"></span>"),
					arguments("parseTextImgStrayEmbedded",
							"[img][/b]http://zfgc.com[/img]",
							"<span class=\"bbcode-img\"><img></span>"));
		}

		@ParameterizedTest
		@MethodSource("strayAndNonTagCases")
		void parseTextHandlesStrayMalformedAndNonTagInput(String caseName, String input, String expected) {
			assertEquals(expected, service.parseText(input));
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
							"This is my <span class=\"bbcode-code-header\">Code</span><span class=\"bbcode-code-block\"> house</span>"),
					arguments("parseTextStrayClosingEmbedded",
							"[quote author=MG-Zero]This is [/code] my house[/quote]",
							"<span class=\"bbcode-quote-header\">Authored by MG-Zero</span><span class=\"bbcode-quote-block\">This is [/code]</span> my house[/quote]"),
					arguments("parseTextStrayMismatched",
							"[b][i]This is my house[/b][/i]",
							"<span class=\"bbcode-b\"><span class=\"bbcode-i\">This is my house[/b]</span>[/i]</span>"),
					arguments("parseTextStrayClosingOutside",
							"[quote author=MG-Zero]This is my house[/quote][/code]",
							"<span class=\"bbcode-quote-header\">Authored by MG-Zero</span><span class=\"bbcode-quote-block\">This is my house</span>[/code]"),
					arguments("parseTextMattyBoyTestBadInput",
							"[b][code]test[/code][/b][b]hey[b]yo[b]wassup[b][i][u]bitch!!![/i][/u][/b][/b][/b][/b]  [i][u]yeah man[/i][/u] ",
							"<span class=\"bbcode-b\"><span class=\"bbcode-code-header\">Code</span><span class=\"bbcode-code-block\">test</span></span><span class=\"bbcode-b\">hey<span class=\"bbcode-b\">yo<span class=\"bbcode-b\">wassup<span class=\"bbcode-b\"><span class=\"bbcode-i\"><span class=\"bbcode-u\">bitch!!![/i]</span>[/u]</span></span></span></span></span>  <span class=\"bbcode-i\"><span class=\"bbcode-u\">yeah man[/i]</span>[/u]</span> "),
					arguments("parseTextMattyBoyTestGoodInput",
							"[b][code]test[/code][/b][b]hey[b]yo[b]wassup[b][i][u]bitch!!![/u][/i][/b][/b][/b][/b]  [i][u]yeah man[/u][/i] o",
							"<span class=\"bbcode-b\"><span class=\"bbcode-code-header\">Code</span><span class=\"bbcode-code-block\">test</span></span><span class=\"bbcode-b\">hey<span class=\"bbcode-b\">yo<span class=\"bbcode-b\">wassup<span class=\"bbcode-b\"><span class=\"bbcode-i\"><span class=\"bbcode-u\">bitch!!!</span></span></span></span></span></span>  <span class=\"bbcode-i\"><span class=\"bbcode-u\">yeah man</span></span> o"),
					arguments("parseTextInvalidTag",
							"i am [hr] a [hr] dumb [b]test[/b]",
							"i am [hr] a [hr] dumb <span class=\"bbcode-b\">test</span>"),
					arguments("parseTextDigitTag",
							"[h2]The Basics[/h2]",
							"<h2 class=\"bbcode-h2\">The Basics</h2>"),
					arguments("parseTextBareDigitsNotATag",
							"i am [42] years old [b]test[/b]",
							"i am [42] years old <span class=\"bbcode-b\">test</span>"));
		}

		@ParameterizedTest
		@MethodSource("processAttributesCases")
		void processAttributesResolvesModeOrRoundTrips(String caseName, String attributes, String expected) {
			assertEquals(expected, service.processAttributes(bbCodeQuote, attributes.toCharArray(), new MutableInt()));
		}

		static Stream<Arguments> processAttributesCases() {
			return Stream.of(
					arguments("processAttributesAllValidMode0",
							"author=MG-Zero link=/thread/99 time=1494552503000",
							"<span class=\"bbcode-quote-header\"><a href=\"/thread/99\">Authored by MG-Zero at 05/12/2017 01:28:23</a></span><span class=\"bbcode-quote-block\">"),
					arguments("processAttributesAllValidMode1",
							"author=MG-Zero",
							"<span class=\"bbcode-quote-header\">Authored by MG-Zero</span><span class=\"bbcode-quote-block\">"),
					arguments("processAttributesOneInvalid",
							" autor=test link=test time=1494552504",
							"[quote autor=test link=test time=1494552504]"),
					arguments("processAttributeskippedOne",
							" autor=test time=1494552504",
							"[quote autor=test time=1494552504]"),
					arguments("processAttributesOutOfOrder",
							"link=test author=test time=1494552504",
							"<span class=\"bbcode-quote-header\"><a href=\"test\">Authored by test at 05/12/2017 01:28:23</a></span><span class=\"bbcode-quote-block\">"),
					arguments("processAttributesNamelessExtra",
							"=x link=test author=test time=1494552504",
							"[quote=x link=test author=test time=1494552504]"));
		}

	// @Test
	public void parseTextUrlContentEmbeddedStray() {
			String result = service.parseText("[url][/b]http://zfgc.com[/url]");
			// current output: "<a href=\"{{c}}\">[/b]</span>http://zfgc.com[/url]"
			// FIXME: handle stray closing tags, seems to be something with the way the
			// cursor flushes text?
			assertEquals("<a href=\"[/b]http://zfgc.com\">[/b]http://zfgc.com</a>", result);
	}

	}

	@Nested
	class QuoteSourceReference {

		private static final OffsetDateTime REV_TS = OffsetDateTime.of(2020, 6, 1, 0, 0, 0, 0, ZoneOffset.UTC);
		private static final OffsetDateTime QUOTING_TS = OffsetDateTime.of(2020, 6, 2, 0, 0, 0, 0, ZoneOffset.UTC);
		private static final Set<Integer> VISIBLE_BOARDS = Set.of(3);
		private static final String PLACEHOLDER = "(quoted message unavailable)";

		private BBCodeService service;
		private BBCodeOutputSanitizer sanitizer;
		private QuotedMessageLookup lookup;

		@BeforeEach
		void setup() {
			service = new BBCodeService();
			service.validBbCodes = buildConfig();
			sanitizer = spy(new BBCodeOutputSanitizer());
			service.outputSanitizer = sanitizer;
			lookup = mock(QuotedMessageLookup.class);
			service.quotedMessageLookup = lookup;
		}

		private static Map<String, BBCodeConfig> buildConfig() {
			Map<String, BBCodeConfig> config = new HashMap<>();
			config.put("QUOTE", quoteConfig());
			config.put("B", simpleTag("b", "<span class=\"bb-code-b\">", "</span>", true));
			config.put("CODE", codeConfig());
			return config;
		}

		private void stubResolve(Map<Integer, QuotedMessageLookup.Resolved> master) {
			when(lookup.resolve(any(), any())).thenAnswer(invocation -> {
				Set<Integer> ids = invocation.getArgument(0);
				Map<Integer, QuotedMessageLookup.Resolved> out = new HashMap<>();
				for (Integer id : ids) {
					if (master.containsKey(id)) {
						out.put(id, master.get(id));
					}
				}
				return out;
			});
		}

		private String renderInScope(String post, Map<Integer, QuotedMessageLookup.Resolved> master) {
			stubResolve(master);
			service.openQuoteScope(List.of(new BBCodeService.QuotingPost(post, QUOTING_TS)), VISIBLE_BOARDS);
			try {
				return service.parseText(post, QUOTING_TS);
			} finally {
				service.closeQuoteScope();
			}
		}

		@Test
		void extractQuoteMsgIdIsStrictAboutMsgIdModes() {
			assertEquals(Integer.valueOf(42), service.extractQuoteMsgId(" msg=42"));
			assertEquals(Integer.valueOf(42), service.extractQuoteMsgId(" thread=3 msg=42"));
			assertEquals(Integer.valueOf(42), service.extractQuoteMsgId(" author=Bob thread=3 msg=42"));
			assertNull(service.extractQuoteMsgId(" author=Some msg=42 guy"));
			assertNull(service.extractQuoteMsgId(" author=Bob"));
			assertNull(service.extractQuoteMsgId("=Bob"));
		}

		@Test
		void gateNoOpIsByteIdenticalForNonMsgQuote() {
			String input = "[quote author=Bob]legacy body[/quote]";
			String withoutScope = service.parseText(input);

			stubResolve(Map.of(5, resolved(true, REV_TS, "SOURCE")));
			service.openQuoteScope(
					List.of(new BBCodeService.QuotingPost("other [quote msg=5][/quote]", QUOTING_TS)), VISIBLE_BOARDS);
			String withScope;
			try {
				withScope = service.parseText(input, QUOTING_TS);
			} finally {
				service.closeQuoteScope();
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
			NavigableMap<OffsetDateTime, String> revisions = new TreeMap<>();
			revisions.put(olderTs, "OLDER-BODY");
			revisions.put(newerTs, "NEWER-BODY");
			QuotedMessageLookup.Resolved multi =
					new QuotedMessageLookup.Resolved("Alice", 7, olderTs, 42, 1, 3, true, revisions);

			String result = renderInScope("[quote msg=5][/quote]", Map.of(5, multi));

			assertTrue(result.contains("OLDER-BODY"));
			assertFalse(result.contains("NEWER-BODY"));
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
			service.openQuoteScope(List.of(
					new BBCodeService.QuotingPost(duplicateQuotingPosts, QUOTING_TS),
					new BBCodeService.QuotingPost(duplicateQuotingPosts, QUOTING_TS)), VISIBLE_BOARDS);
			try {
				service.parseText(duplicateQuotingPosts, QUOTING_TS);
				service.parseText(duplicateQuotingPosts, QUOTING_TS);
			} finally {
				service.closeQuoteScope();
			}

			verify(sanitizer, times(1)).sanitize(contains("SOURCEMARKER"));
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
			String out = new BBCodeOutputSanitizer().sanitize("before" + sentinel + "after");

			assertTrue(out.contains(sentinel));
		}

		@Test
		void batchResolveIsConstantNotPerPost() {
			stubResolve(Map.of(5, resolved(true, REV_TS, "SOURCE")));
			List<BBCodeService.QuotingPost> posts = IntStream.range(0, 8)
					.mapToObj(i -> new BBCodeService.QuotingPost("[quote msg=5][/quote]", QUOTING_TS))
					.collect(Collectors.toList());

			service.openQuoteScope(posts, VISIBLE_BOARDS);
			service.closeQuoteScope();

			verify(lookup, times(1)).resolve(any(), any());
		}

		@Test
		void nonScopedStrippedQuoteRendersCurrentSourceNotEmpty() {
			stubResolve(Map.of(5, resolved(true, REV_TS, "revived source body [b]bold[/b]")));

			String result = service.parseText("[quote msg=5][/quote]");

			assertTrue(result.contains("revived source body"));
			assertTrue(result.contains("<span class=\"bb-code-b\">bold</span>"));
			assertTrue(result.contains("Alice"));
			assertFalse(result.contains(PLACEHOLDER));
			assertFalse(result.contains(""));
		}

		@Test
		void nonScopedStrippedQuoteWithoutPermissionRendersPlaceholderNotEmpty() {
			stubResolve(Map.of(5, resolved(false, REV_TS, "secret source body")));

			String result = service.parseText("[quote msg=5][/quote]");

			assertTrue(result.contains(PLACEHOLDER));
			assertFalse(result.contains("secret source body"));
			assertFalse(result.contains("/user/profile/"));
		}

		@Test
		void nestingCapDegradesInnerQuoteToEmbedded() {
			Map<Integer, QuotedMessageLookup.Resolved> master = new HashMap<>();
			master.put(5, resolved(true, REV_TS, "outer [quote msg=7]inner embedded[/quote]"));
			master.put(7, resolved(true, REV_TS, "SEVEN SOURCE"));

			String result = renderInScope("[quote msg=5][/quote]", master);

			assertTrue(result.contains("inner embedded"));
			assertFalse(result.contains("SEVEN SOURCE"));
		}
	}

	@Nested
	class Sanitizer {

		private final BBCodeOutputSanitizer sanitizer = new BBCodeOutputSanitizer();

		@Test
		void keepsAllowlistedInlineStyle() {
			String result = sanitizer.sanitize("<span style=\"color:red;font-size:24pt\">hi</span>");
			assertTrue(result.contains("color:red"), result);
			assertTrue(result.contains("font-size:24pt"), result);
		}

		@Test
		void stripsDisallowedStyleProperty() {
			String result = sanitizer.sanitize("<span style=\"position:fixed;color:red\">hi</span>");
			assertFalse(result.contains("position"), result);
			assertTrue(result.contains("color:red"), result);
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
			assertEquals(expected, BBCodeOutputSanitizer.normalizeYoutubeEmbed(embedUrl));
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
		void unextractableYoutubeEmbedIframeIsStripped() {
			String result = sanitizer.sanitize(
					"<iframe src=\"https://www.youtube.com/embed/http://evil.example.com/page\"></iframe>");
			assertFalse(result.contains("<iframe"), result);
		}
	}

	@Nested
	class Smilies {

		private BBCodeOutputSanitizer smileySanitizer() {
			BBCodeOutputSanitizer s = new BBCodeOutputSanitizer();
			s.registerSmilies(List.of(
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
			BBCodeOutputSanitizer s = smileySanitizer();
			assertFalse(s.sanitize("fileXD.zip").contains("bb-smiley"), "XD inside a word must not match");
			assertFalse(s.sanitize("call 18) now").contains("bb-smiley"), "8) after a digit must not match");
			assertTrue(s.sanitize("haha XD good").contains("bb-smiley-grin"));
		}

		@Test
		void skipsSmiliesInsideCodeBlocksAndLinks() {
			BBCodeOutputSanitizer s = smileySanitizer();
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
	}

	@Nested
	class QuoteLookup {

		private static final OffsetDateTime ORIGINAL_TS = OffsetDateTime.of(2020, 1, 1, 0, 0, 0, 0, ZoneOffset.UTC);
		private static final OffsetDateTime EDITED_TS = OffsetDateTime.of(2021, 1, 1, 0, 0, 0, 0, ZoneOffset.UTC);

		private MessageDboMapper messageMapper;
		private MessageHistoryDboMapper messageHistoryMapper;
		private UserDboMapper userMapper;
		private QuotedMessageLookup lookup;

		@BeforeEach
		void setup() {
			messageMapper = mock(MessageDboMapper.class);
			messageHistoryMapper = mock(MessageHistoryDboMapper.class);
			userMapper = mock(UserDboMapper.class);
			lookup = new QuotedMessageLookup();
			ReflectionTestUtils.setField(lookup, "messageMapper", messageMapper);
			ReflectionTestUtils.setField(lookup, "messageHistoryMapper", messageHistoryMapper);
			ReflectionTestUtils.setField(lookup, "userMapper", userMapper);

			MessageDbo message = new MessageDbo();
			message.setMessageId(5);
			message.setOwnerId(7);
			message.setThreadId(42);
			message.setBoardId(3);
			message.setPostInThread(1);
			message.setCreatedTs(ORIGINAL_TS);
			when(messageMapper.selectByExample(any(MessageDboExample.class))).thenReturn(List.of(message));

			UserDbo user = new UserDbo();
			user.setUserId(7);
			user.setDisplayName("Alice");
			when(userMapper.selectByExample(any(UserDboExample.class))).thenReturn(List.of(user));

			when(messageHistoryMapper.selectByExample(any(MessageHistoryDboExample.class)))
					.thenReturn(List.of(historyRow(ORIGINAL_TS, "original"), historyRow(EDITED_TS, "edited")));
		}

		private static MessageHistoryDbo historyRow(OffsetDateTime createdTs, String body) {
			MessageHistoryDbo row = new MessageHistoryDbo();
			row.setMessageId(5);
			row.setCreatedTs(createdTs);
			row.setMessageText(body);
			return row;
		}

		@Test
		void nullVisibleBoardsDeniesAndSkipsRevisionQuery() {
			Map<Integer, QuotedMessageLookup.Resolved> resolved = lookup.resolve(Set.of(5));

			QuotedMessageLookup.Resolved entry = resolved.get(5);
			assertFalse(entry.permitted());
			assertNull(entry.revisionsByCreatedTs());
			assertEquals("Alice", entry.authorDisplayName());
			assertEquals(Integer.valueOf(42), entry.threadId());
			assertEquals(Integer.valueOf(3), entry.sourceBoardId());
			verify(messageHistoryMapper, never()).selectByExample(any());
		}


		@Test
		void visibleBoardMembershipPermitsAndLoadsRevisions() {
			Map<Integer, QuotedMessageLookup.Resolved> resolved = lookup.resolve(Set.of(5), Set.of(3));

			QuotedMessageLookup.Resolved entry = resolved.get(5);
			assertTrue(entry.permitted());
			assertEquals("edited", entry.revisionsByCreatedTs().floorEntry(EDITED_TS).getValue());
			assertEquals("original", entry.revisionsByCreatedTs().floorEntry(ORIGINAL_TS).getValue());
			verify(messageHistoryMapper).selectByExample(any());
		}

		@Test
		void invisibleBoardDeniesPermissionAndDropsRevisions() {
			Map<Integer, QuotedMessageLookup.Resolved> resolved = lookup.resolve(Set.of(5), Set.of(99));

			QuotedMessageLookup.Resolved entry = resolved.get(5);
			assertFalse(entry.permitted());
			assertNull(entry.revisionsByCreatedTs());
		}
	}

	@Nested
	class Markdown {

		private final MarkdownRenderer renderer = new MarkdownRenderer(new BBCodeOutputSanitizer());

		@Test
		void rendersBasicMarkdown() {
			String html = renderer.render("# Title\n\nSome **bold** text and a [link](https://zfgc.com).");
			assertTrue(html.contains("<h1>Title</h1>"), html);
			assertTrue(html.contains("<strong>bold</strong>"), html);
			assertTrue(html.contains("href=\"https://zfgc.com\""), html);
		}

		@Test
		void sanitizesDangerousHtml() {
			String html = renderer.render("Hello\n\n<script>alert(1)</script>");
			assertFalse(html.contains("<script"), html);
			assertTrue(html.contains("Hello"), html);
		}

		@Test
		void blankInputYieldsEmpty() {
			assertTrue(renderer.render("   ").isEmpty());
			assertTrue(renderer.render(null).isEmpty());
		}
	}
}
