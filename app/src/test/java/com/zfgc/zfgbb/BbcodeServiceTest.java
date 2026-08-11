package com.zfgc.zfgbb;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.params.provider.Arguments.arguments;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.util.List;
import java.util.Map;
import java.util.stream.Stream;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.springframework.beans.factory.annotation.Autowired;

import com.zfgc.zfgbb.content.ContentFormat;
import com.zfgc.zfgbb.content.ContentScope;
import com.zfgc.zfgbb.content.renderer.ContentOutputSanitizer;
import com.zfgc.zfgbb.content.renderer.ContentRenderingService;
import com.zfgc.zfgbb.content.renderer.ContentTagResolver;
import com.zfgc.zfgbb.content.renderer.RenderedTextEnricher;
import com.zfgc.zfgbb.content.renderer.SourceReferenceService;
import com.zfgc.zfgbb.content.renderer.bbcode.BBCodeGrammarHolder;
import com.zfgc.zfgbb.content.renderer.bbcode.BBCodeGrammarLoader;
import com.zfgc.zfgbb.content.renderer.bbcode.BBCodeParser;
import com.zfgc.zfgbb.content.renderer.bbcode.BBCodeRenderer;
import com.zfgc.zfgbb.content.renderer.markdown.MarkdownRenderer;
import com.zfgc.zfgbb.content.renderer.templates.TemplateExpander;
import com.zfgc.zfgbb.content.renderer.templates.TemplateExpansion;
import com.zfgc.zfgbb.dao.forum.SmileyDao;
import com.zfgc.zfgbb.dataprovider.forum.BBCodeDataProvider;
import com.zfgc.zfgbb.model.forum.BBCodeConfig;
import com.zfgc.zfgbb.services.forum.QuotedMessageSource;
import com.zfgc.zfgbb.testsupport.PostgresIntegrationTest;

class BbcodeServiceTest extends PostgresIntegrationTest {

	@Autowired
	private BBCodeDataProvider bbCodeDataProvider;

	private BBCodeRenderer bbCodeRenderer;
	private Renderer renderer;
	private BBCodeConfig bbCodeQuote;

	static final String TIME_ELEMENT_1494552503 = "<time class=\"bb-date-long\" datetime=\"2017-05-12T01:28:23Z\">May 12, 2017, 1:28:23 AM</time>";
	static final String TIME_ELEMENT_1494552504 = "<time class=\"bb-date-long\" datetime=\"2017-05-12T01:28:24Z\">May 12, 2017, 1:28:24 AM</time>";

	static final String QUOTE_CLOSE = "</div></div>";
	static final String QUOTE_WITHOUT_HEADER = "<div class=\"bb-code-quote\"><div class=\"bb-code-quote-body\">";
	static final String QUOTE_FROM_MGZERO = "<div class=\"bb-code-quote\"><div class=\"bb-code-quote-header\">"
			+ "Quote from MG-Zero,</div><div class=\"bb-code-quote-body\">";
	static final String QUOTE_FROM_MGZERO_DATED = "<div class=\"bb-code-quote\"><div class=\"bb-code-quote-header\">"
			+ "Quote from MG-Zero on " + TIME_ELEMENT_1494552503 + "</div><div class=\"bb-code-quote-body\">";

	@BeforeEach
	void buildTheRendererOverTheMigratedGrammar() {
		renderer = buildRenderer(bbCodeDataProvider, messageResolver());
		bbCodeRenderer = renderer.bbCodeRenderer();
		bbCodeQuote = bbCodeDataProvider.getBBCodeConfig().get("QUOTE");
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
						"<div class=\"bb-code-quote\"><div class=\"bb-code-quote-header\">Quote from test,</div>"
								+ "<div class=\"bb-code-quote-body\">test" + QUOTE_CLOSE),
				arguments("parseTextMode0Code",
						"[quote author=MG-Zero link=/thread/99 date=1494552503]test[/quote]",
						QUOTE_FROM_MGZERO_DATED + "test" + QUOTE_CLOSE),
				arguments("parseTextQuoteEmbeddedMode0",
						"[quote author=MG-Zero link=/thread/99 date=1494552503][quote author=MG-Zero link=/thread/99 date=1494552503]test[/quote][/quote]",
						QUOTE_FROM_MGZERO_DATED + QUOTE_FROM_MGZERO_DATED + "test" + QUOTE_CLOSE + QUOTE_CLOSE),
				arguments("parseTextQuoteNoParam",
						"[quote]test[/quote]",
						QUOTE_WITHOUT_HEADER + "test" + QUOTE_CLOSE),
				arguments("parseTextQuoteTwo",
						"[quote author=MG-Zero link=/thread/99 date=1494552503]test[/quote][quote author=MG-Zero link=/thread/99 date=1494552503]test[/quote]",
						QUOTE_FROM_MGZERO_DATED + "test" + QUOTE_CLOSE
								+ QUOTE_FROM_MGZERO_DATED + "test" + QUOTE_CLOSE),
				arguments("parseTextNamelessAttribute (seeded quote declares none, so =MGZero is discarded)",
						"[quote=MGZero]test[/quote]",
						QUOTE_WITHOUT_HEADER + "test" + QUOTE_CLOSE),
				arguments("parseTextUnmatchedAttributeModeRoundTrips",
						"[quote author=a link=b]test[/quote]",
						"[quote author=a link=b]test[/quote]"),
				arguments("parseTextQuoteAuthorBearingABracketCannotBeReadAsATag",
						"[quote author=-x-[Sir Lunatic link=/thread/99 date=1494552503]test[/quote]",
						"[quote author=-x-[Sir Lunatic link=/thread/99 date=1494552503]test[/quote]"),
				arguments("parseTextUnmatchedModeKeepsSurroundingStyling",
						"[b][quote link=b]Game Download[/b] after",
						"<span class=\"bb-code-b\">[quote link=b]Game Download</span> after"));
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
						"<pre class=\"bb-code-code\">test</pre>"),
				arguments("parseTextCodeEmbedded",
						"[code]test[code]test[quote][/code]",
						"<pre class=\"bb-code-code\">test[code]test[quote]</pre>"),
				arguments("parseTextCodeEscapesMarkup",
						"[code]<map><version>1.0</version></map>[/code]",
						"<pre class=\"bb-code-code\">&lt;map&gt;&lt;version&gt;1.0&lt;/version&gt;&lt;/map&gt;</pre>"),
				arguments("parseTextCodeConvertsBrToNewline",
						"[code]line1<br/>line2<br />line3[/code]",
						"<pre class=\"bb-code-code\">line1\nline2\nline3</pre>"));
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
						"<span class=\"bb-code-img\"><img src=\"http://img.photobucket.com/albums/v191/legofreak1988/avy-sig/corpse.jpg\"></span>"),
				arguments("parseTextUrlContent",
						"[url]http://zfgc.com[/url]",
						"<span class=\"bb-code-url\"><a href=\"http://zfgc.com\">http://zfgc.com</a></span>"),
				arguments("parseTextUrlContentEmbedded",
						"[url][b]http://zfgc.com[/b][/url]",
						"<span class=\"bb-code-url\"><a href=\"http://zfgc.com\">"
								+ "<span class=\"bb-code-b\">http://zfgc.com</span></a></span>"),
				arguments("parseTextUrlImgEmbedded",
						"[url=https://somelink.com][img]https://someimg.jpg[/img][/url]",
						"<span class=\"bb-code-url\"><a href=\"https://somelink.com\">"
								+ "<span class=\"bb-code-img\"><img src=\"https://someimg.jpg\"></span></a></span>"),
				arguments("parseTextImg",
						"[img]http://zfgc.com[/img]",
						"<span class=\"bb-code-img\"><img src=\"http://zfgc.com\"></span>"),
				arguments("parseTextImgStrayEmbedded",
						"[img][/b]http://zfgc.com[/img]",
						"<span class=\"bb-code-img\"><img></span>"),
				arguments("parseTextUrlContentEmbeddedStray",
						"[url][/b]http://zfgc.com[/url]",
						"<span class=\"bb-code-url\"><a>[/b]</a></span>"
								+ "<a href=\"http://zfgc.com[/url]\">http://zfgc.com[/url]</a>"));
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
						"This is my <pre class=\"bb-code-code\"> house</pre>"),
				arguments("parseTextStrayClosingEmbedded",
						"[quote author=MG-Zero]This is [/code] my house[/quote]",
						QUOTE_FROM_MGZERO + "This is [/code]" + QUOTE_CLOSE + " my house[/quote]"),
				arguments("parseTextStrayMismatched",
						"[b][i]This is my house[/b][/i]",
						"<span class=\"bb-code-b\"><span class=\"bb-code-i\">This is my house[/b]</span>[/i]</span>"),
				arguments("parseTextStrayClosingOutside",
						"[quote author=MG-Zero]This is my house[/quote][/code]",
						QUOTE_FROM_MGZERO + "This is my house" + QUOTE_CLOSE + "[/code]"),
				arguments("parseTextMattyBoyTestBadInput",
						"[b][code]test[/code][/b][b]hey[b]yo[b]wassup[b][i][u]bitch!!![/i][/u][/b][/b][/b][/b]  [i][u]yeah man[/i][/u] ",
						"<span class=\"bb-code-b\"><pre class=\"bb-code-code\">test</pre></span><span class=\"bb-code-b\">hey<span class=\"bb-code-b\">yo<span class=\"bb-code-b\">wassup<span class=\"bb-code-b\"><span class=\"bb-code-i\"><span class=\"bb-code-u\">bitch!!![/i]</span>[/u]</span></span></span></span></span>  <span class=\"bb-code-i\"><span class=\"bb-code-u\">yeah man[/i]</span>[/u]</span> "),
				arguments("parseTextMattyBoyTestGoodInput",
						"[b][code]test[/code][/b][b]hey[b]yo[b]wassup[b][i][u]bitch!!![/u][/i][/b][/b][/b][/b]  [i][u]yeah man[/u][/i] o",
						"<span class=\"bb-code-b\"><pre class=\"bb-code-code\">test</pre></span><span class=\"bb-code-b\">hey<span class=\"bb-code-b\">yo<span class=\"bb-code-b\">wassup<span class=\"bb-code-b\"><span class=\"bb-code-i\"><span class=\"bb-code-u\">bitch!!!</span></span></span></span></span></span>  <span class=\"bb-code-i\"><span class=\"bb-code-u\">yeah man</span></span> o"),
				arguments("parseTextInvalidTag",
						"i am [blink] a [blink] dumb [b]test[/b]",
						"i am [blink] a [blink] dumb <span class=\"bb-code-b\">test</span>"),
				arguments("parseTextDigitTag",
						"[h2]The Basics[/h2]",
						"<h2 class=\"bb-code-h2\">The Basics</h2>"),
				arguments("parseTextBareDigitsNotATag",
						"i am [42] years old [b]test[/b]",
						"i am [42] years old <span class=\"bb-code-b\">test</span>"));
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
						"author=MG-Zero link=/thread/99 date=1494552503",
						QUOTE_FROM_MGZERO_DATED),
				arguments("processAttributesAllValidMode1",
						"author=MG-Zero",
						QUOTE_FROM_MGZERO),
				arguments("processAttributesOneInvalid",
						" autor=test link=test date=1494552504",
						"[quote autor=test link=test date=1494552504]"),
				arguments("processAttributeskippedOne",
						" autor=test date=1494552504",
						"[quote autor=test date=1494552504]"),
				arguments("processAttributesOutOfOrder",
						"link=test author=test date=1494552504",
						"<div class=\"bb-code-quote\"><div class=\"bb-code-quote-header\">Quote from test on "
								+ TIME_ELEMENT_1494552504 + "</div><div class=\"bb-code-quote-body\">"),
				arguments("processAttributesNamelessExtra (seeded quote declares none, so =x is discarded)",
						"=x link=test author=test date=1494552504",
						"<div class=\"bb-code-quote\"><div class=\"bb-code-quote-header\">Quote from test on "
								+ TIME_ELEMENT_1494552504 + "</div><div class=\"bb-code-quote-body\">"));
	}

	record Renderer(BBCodeRenderer bbCodeRenderer, ContentRenderingService contentRenderingService) {
		String render(String source) {
			return contentRenderingService.render(source, ContentFormat.BBCODE, ContentScope.FORUM);
		}
	}

	static Renderer buildRenderer(BBCodeDataProvider provider, ContentTagResolver... resolvers) {
		BBCodeGrammarHolder grammarHolder = new BBCodeGrammarHolder();
		RenderedTextEnricher enricher = new RenderedTextEnricher(mock(SmileyDao.class));
		ContentOutputSanitizer sanitizer = new ContentOutputSanitizer(enricher, grammarHolder);
		SourceReferenceService handler = new SourceReferenceService(List.of(resolvers), grammarHolder);
		new BBCodeGrammarLoader(provider, handler, sanitizer, grammarHolder, enricher).loadBBCodeConfig();

		TemplateExpansion templateExpansion = templateExpansion(grammarHolder);
		BBCodeRenderer bbCodeRenderer = new BBCodeRenderer(grammarHolder, handler, templateExpansion);
		MarkdownRenderer markdownRenderer = new MarkdownRenderer(grammarHolder, enricher, handler, bbCodeRenderer,
				templateExpansion);
		return new Renderer(bbCodeRenderer,
				new ContentRenderingService(bbCodeRenderer, markdownRenderer, sanitizer, handler));
	}

	static TemplateExpansion templateExpansion(BBCodeGrammarHolder grammarHolder) {
		TemplateExpander expander = mock(TemplateExpander.class);
		when(expander.theFileReferencesResolvedIn(any(), any())).thenAnswer(call -> call.getArgument(0));
		return new TemplateExpansion(expander, grammarHolder);
	}

	static QuotedMessageSource messageResolver() {
		QuotedMessageSource lookup = mock(QuotedMessageSource.class);
		when(lookup.resolverCode()).thenReturn(QuotedMessageSource.RESOLVER_CODE);
		return lookup;
	}
}
