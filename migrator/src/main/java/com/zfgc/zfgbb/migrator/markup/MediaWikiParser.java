package com.zfgc.zfgbb.migrator.markup;

import java.io.IOException;
import java.net.URLDecoder;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Element;
import org.jsoup.nodes.TextNode;

import info.bliki.wiki.filter.HTMLConverter;
import info.bliki.wiki.model.WikiModel;

public final class MediaWikiParser {

    private static final String LINK_BASE = "/zfgcwiki/";
    private static final String IMG_BASE = "/zfgcimg/";

    private static final Pattern CATEGORY =
            Pattern.compile("\\[\\[Category:([^\\]|]+)(?:\\|[^\\]]*)?\\]\\]", Pattern.CASE_INSENSITIVE);
    private static final Pattern NOWIKI =
            Pattern.compile("<nowiki>.*?</nowiki>", Pattern.DOTALL | Pattern.CASE_INSENSITIVE);
    private static final Pattern URL_FUNCTION_LINK = Pattern.compile(
            "\\[\\{\\{\\s*(?:canonicalurl|fullurl|localurl)e?:\\s*([^}|]+?)\\s*(?:\\|[^}]*)?\\}\\}\\s+([^\\]]+)\\]",
            Pattern.CASE_INSENSITIVE);
    private static final Pattern URL_FUNCTION_BARE = Pattern.compile(
            "\\{\\{\\s*(?:canonicalurl|fullurl|localurl)e?:\\s*([^}|]+?)\\s*(?:\\|[^}]*)?\\}\\}",
            Pattern.CASE_INSENSITIVE);
    private static final Pattern TPL_TOKEN = Pattern.compile("@@ZT(\\d+)T@@");
    private static final Pattern MAGIC_WORD = Pattern.compile(
            "__(?:NOTOC|FORCETOC|TOC|NOEDITSECTION|NOGALLERY|HIDDENCAT|INDEX|NOINDEX|NEWSECTIONLINK|NONEWSECTIONLINK)__");
    private static final Set<String> MAGIC_VARIABLES = Set.of(
            "NUMBEROFARTICLES", "NUMBEROFPAGES", "NUMBEROFUSERS", "NUMBEROFFILES", "NUMBEROFEDITS",
            "PAGENAME", "FULLPAGENAME", "BASEPAGENAME", "SITENAME", "SERVER", "SERVERNAME",
            "CURRENTYEAR", "CURRENTMONTH", "CURRENTMONTHNAME", "CURRENTDAY", "CURRENTDAYNAME",
            "CURRENTTIME", "CURRENTTIMESTAMP");

    private final List<Node.Template> capturedTemplates = new ArrayList<>();

    private MediaWikiParser() {}

    public static Node.Document parse(String wikitext) {
        return new MediaWikiParser().run(wikitext == null ? "" : wikitext);
    }

    private Node.Document run(String wikitext) {
        wikitext = URL_FUNCTION_LINK.matcher(wikitext).replaceAll("[[$1|$2]]");
        wikitext = URL_FUNCTION_BARE.matcher(wikitext).replaceAll("[[$1]]");
        Boolean tocDirective = null;
        if (wikitext.contains("__NOTOC__")) {
            tocDirective = false;
        } else if (wikitext.contains("__FORCETOC__") || wikitext.contains("__TOC__")) {
            tocDirective = true;
        }
        wikitext = MAGIC_WORD.matcher(wikitext).replaceAll("");
        List<Node> categories = new ArrayList<>();
        Matcher categoryMatcher = CATEGORY.matcher(NOWIKI.matcher(wikitext).replaceAll(""));
        while (categoryMatcher.find()) {
            String name = categoryMatcher.group(1).trim();
            if (name.isEmpty() || name.contains("{") || name.contains("}")) {
                continue;
            }
            categories.add(new Node.Category(Character.toUpperCase(name.charAt(0)) + name.substring(1)));
        }

        String html;
        try {
            html = new IrWikiModel().render(new HTMLConverter(), wikitext);
        } catch (IOException e) {
            throw new MarkupException("Failed to render MediaWiki content via bliki", e);
        }

        org.jsoup.nodes.Document doc = Jsoup.parseBodyFragment(html);
        doc.outputSettings().prettyPrint(false);
        doc.select("table#toc, div#toc, div.toc").remove();

        List<Node> top = new ArrayList<>();
        if (tocDirective != null) {
            top.add(new Node.TocDirective(tocDirective));
        }
        for (org.jsoup.nodes.Node child : doc.body().childNodes()) {
            convert(child, top);
        }
        top.addAll(categories);
        return new Node.Document(top);
    }

    private final class IrWikiModel extends WikiModel {
        IrWikiModel() {
            super(IMG_BASE + "${image}", LINK_BASE + "${title}");
        }

        @Override
        public void substituteTemplateCall(String templateName, Map<String, String> parameterMap,
                Appendable writer) throws IOException {
            if (templateName == null || templateName.startsWith("#")) {
                return;
            }
            String upper = templateName.trim().toUpperCase();
            if (upper.equals("NUMBEROFARTICLES") || upper.equals("NUMBEROFPAGES")) {
                templateName = "PageCount";
            } else if (MAGIC_VARIABLES.contains(upper)) {
                return;
            }
            List<Node.Template.Param> params = new ArrayList<>();
            for (Map.Entry<String, String> entry : parameterMap.entrySet()) {
                String key = entry.getKey() == null ? "" : entry.getKey().trim();
                String value = MediaWikiParser.this.literalizeTokens(entry.getValue() == null ? "" : entry.getValue().trim());
                params.add(new Node.Template.Param(isPositional(key) ? null : key, value));
            }
            int index = MediaWikiParser.this.capturedTemplates.size();
            MediaWikiParser.this.capturedTemplates.add(new Node.Template(
                    MediaWikiParser.this.literalizeTokens(templateName == null ? "" : templateName.trim()), params));
            writer.append("@@ZT").append(Integer.toString(index)).append("T@@");
        }
    }

    private void convert(org.jsoup.nodes.Node node, List<Node> out) {
        if (node instanceof TextNode tn) {
            appendText(tn.getWholeText(), out);
            return;
        }
        if (!(node instanceof Element el)) {
            return;
        }
        switch (el.normalName()) {
            case "h1", "h2", "h3", "h4", "h5", "h6" ->
                    out.add(new Node.Heading(el.normalName().charAt(1) - '0', children(el)));
            case "p" -> out.add(new Node.Paragraph(children(el)));
            case "b", "strong" -> out.add(new Node.Bold(children(el)));
            case "i", "em" -> out.add(new Node.Italic(children(el)));
            case "br" -> out.add(new Node.LineBreak());
            case "ul" -> out.add(listBlock(el, false));
            case "ol" -> out.add(listBlock(el, true));
            case "table" -> out.add(table(el));
            case "pre" -> out.add(new Node.CodeBlock(literalizeTokens(el.wholeText())));
            case "a" -> out.add(anchor(el));
            case "div" -> {
                int before = out.size();
                for (org.jsoup.nodes.Node c : el.childNodes()) {
                    convert(c, out);
                }
                if (out.size() > before) {
                    out.add(new Node.LineBreak());
                }
            }
            case "img" -> out.add(image(el));
            default -> {
                for (org.jsoup.nodes.Node c : el.childNodes()) {
                    convert(c, out);
                }
            }
        }
    }

    private List<Node> children(Element el) {
        List<Node> list = new ArrayList<>();
        for (org.jsoup.nodes.Node c : el.childNodes()) {
            convert(c, list);
        }
        return list;
    }

    private String literalizeTokens(String text) {
        Matcher tokenMatcher = TPL_TOKEN.matcher(text);
        StringBuilder sb = new StringBuilder();
        while (tokenMatcher.find()) {
            int index = Integer.parseInt(tokenMatcher.group(1));
            String replacement = index < capturedTemplates.size()
                    ? literalTemplate(capturedTemplates.get(index))
                    : "";
            tokenMatcher.appendReplacement(sb, Matcher.quoteReplacement(replacement));
        }
        tokenMatcher.appendTail(sb);
        return sb.toString();
    }

    private static String literalTemplate(Node.Template template) {
        StringBuilder sb = new StringBuilder("{{").append(template.name());
        for (Node.Template.Param param : template.params()) {
            sb.append("\n|");
            if (param.name() != null) {
                sb.append(param.name()).append('=');
            }
            sb.append(param.value());
        }
        if (!template.params().isEmpty()) {
            sb.append('\n');
        }
        return sb.append("}}").toString();
    }

    private void appendText(String text, List<Node> out) {
        Matcher tokenMatcher = TPL_TOKEN.matcher(text);
        int last = 0;
        while (tokenMatcher.find()) {
            emitText(text.substring(last, tokenMatcher.start()), out);
            int index = Integer.parseInt(tokenMatcher.group(1));
            if (index < capturedTemplates.size()) {
                out.add(capturedTemplates.get(index));
            }
            last = tokenMatcher.end();
        }
        emitText(text.substring(last), out);
    }

    private static void emitText(String segment, List<Node> out) {
        if (!segment.isBlank()) {
            out.add(new Node.Text(segment));
        }
    }

    private static boolean isPositional(String key) {
        if (key.isEmpty()) {
            return true;
        }
        for (int i = 0; i < key.length(); i++) {
            if (!Character.isDigit(key.charAt(i))) {
                return false;
            }
        }
        return true;
    }

    private Node listBlock(Element el, boolean ordered) {
        List<Node.ListBlock.Item> items = new ArrayList<>();
        for (Element li : el.children()) {
            if (li.normalName().equals("li")) {
                items.add(new Node.ListBlock.Item(children(li)));
            }
        }
        return new Node.ListBlock(ordered, items);
    }

    private Node table(Element el) {
        boolean fullWidth = el.attr("style").replace(" ", "").contains("width:100%")
                || el.attr("width").trim().equals("100%");
        List<Node.Table.Row> rows = new ArrayList<>();
        for (Element section : el.children()) {
            if (section.normalName().equals("tr")) {
                collectRow(section, rows);
            } else if (Set.of("tbody", "thead", "tfoot").contains(section.normalName())) {
                for (Element tr : section.children()) {
                    if (tr.normalName().equals("tr")) {
                        collectRow(tr, rows);
                    }
                }
            }
        }
        return new Node.Table(fullWidth, rows);
    }

    private void collectRow(Element tr, List<Node.Table.Row> rows) {
        List<Node.Table.Cell> cells = new ArrayList<>();
        for (Element cell : tr.children()) {
            String name = cell.normalName();
            if (name.equals("td") || name.equals("th")) {
                cells.add(new Node.Table.Cell(name.equals("th"), children(cell)));
            }
        }
        if (!cells.isEmpty()) {
            rows.add(new Node.Table.Row(cells));
        }
    }

    private Node anchor(Element el) {
        String href = el.attr("href");
        List<Node> label = children(el);
        if (href.startsWith(LINK_BASE)) {
            return new Node.WikiLink(literalizeTokens(decodeTitle(href.substring(LINK_BASE.length()))), label);
        }
        try {
            href = literalizeTokens(java.net.URLDecoder.decode(href, StandardCharsets.UTF_8));
        } catch (IllegalArgumentException e) {
            href = literalizeTokens(href);
        }
        return new Node.ExternalLink(href, label);
    }

    private Node image(Element el) {
        String src = el.attr("src");
        String filename = src.startsWith(IMG_BASE) ? src.substring(IMG_BASE.length()) : src;
        filename = decodeTitle(filename);
        List<String> options = new ArrayList<>();
        if (!el.attr("width").isBlank()) {
            options.add(el.attr("width") + "px");
        }
        if (!el.attr("alt").isBlank()) {
            options.add(el.attr("alt"));
        }
        return new Node.Image(filename, options);
    }

    private static String decodeTitle(String raw) {
        String decoded;
        try {
            decoded = URLDecoder.decode(raw, StandardCharsets.UTF_8);
        } catch (IllegalArgumentException e) {
            decoded = raw;
        }
        return decoded.replace('_', ' ').trim();
    }
}
