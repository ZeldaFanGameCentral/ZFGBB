package com.zfgc.zfgbb.migrator.markup;

import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public final class MarkupConverter {

    private static final Pattern PARAM_WIKILINK = Pattern.compile(
            "\\[\\[(?!(?:File|Image):)([^\\]|]+)(?:\\|([^\\]]*))?\\]\\]", Pattern.CASE_INSENSITIVE);

    private MarkupConverter() {}

    public static Node.Document parse(String wikitext) {
        return MediaWikiParser.parse(wikitext);
    }

    public static String toBbCode(String wikitext) {
        return toBbCode(parse(wikitext));
    }

    public static String toBbCode(Node.Document document) {
        StringBuilder sb = new StringBuilder();
        for (Node n : document.children()) {
            bb(n, sb);
        }
        return tidy(sb.toString());
    }

    public static List<String> categories(Node.Document document) {
        return document.children().stream()
                .filter(n -> n instanceof Node.Category)
                .map(n -> ((Node.Category) n).name())
                .map(name -> name.replace('_', ' ').trim())
                .filter(name -> !name.isEmpty())
                .distinct()
                .toList();
    }

    private static void bb(Node node, StringBuilder sb) {
        switch (node) {
            case Node.Document d -> d.children().forEach(c -> bb(c, sb));
            case Node.Text t -> sb.append(inline(t.value()));
            case Node.Bold b -> wrap(sb, "[b]", b.children(), "[/b]", MarkupConverter::bb);
            case Node.Italic i -> wrap(sb, "[i]", i.children(), "[/i]", MarkupConverter::bb);
            case Node.LineBreak ignored -> sb.append('\n');
            case Node.Heading h ->
                    block(sb, "[h" + clampLevel(h.level()) + "]", h.children(), "[/h" + clampLevel(h.level()) + "]", MarkupConverter::bb);
            case Node.Paragraph p -> {
                renderChildren(p.children(), sb, MarkupConverter::bb);
                sb.append("\n\n");
            }
            case Node.WikiLink link ->
                    sb.append("[wiki=").append(slug(link.target())).append(']')
                            .append(labelOr(link.label(), link.target())).append("[/wiki]");
            case Node.ExternalLink link ->
                    sb.append("[url=").append(link.url()).append(']')
                            .append(labelOr(link.label(), link.url())).append("[/url]");
            case Node.Image img -> sb.append("[img]wiki-file:").append(img.filename()).append("[/img]");
            case Node.CodeBlock c -> sb.append("[pre]").append(c.text()).append("[/pre]\n\n");
            case Node.ListBlock list -> {
                sb.append(list.ordered() ? "[list=1]\n" : "[list]\n");
                for (Node.ListBlock.Item it : list.items()) {
                    sb.append("[li]");
                    renderChildren(it.children(), sb, MarkupConverter::bb);
                    sb.append("[/li]\n");
                }
                sb.append("[/list]\n\n");
            }
            case Node.Table table -> {
                sb.append(table.fullWidth() ? "[table=full]" : "[table]");
                for (Node.Table.Row row : table.rows()) {
                    sb.append("[tr]");
                    for (Node.Table.Cell cell : row.cells()) {
                        String tag = cell.header() ? "th" : "td";
                        sb.append('[').append(tag).append(']')
                                .append(render(cell.children()))
                                .append("[/").append(tag).append(']');
                    }
                    sb.append("[/tr]");
                }
                sb.append("[/table]\n\n");
            }
            case Node.Template tpl -> {
                if (tpl.params().isEmpty()) {
                    sb.append("[template=").append(tpl.name()).append("][/template]");
                } else {
                    sb.append("[template=").append(tpl.name()).append("]\n");
                    int positional = 0;
                    for (Node.Template.Param p : tpl.params()) {
                        String key = p.name() != null ? p.name() : "_" + (++positional);
                        sb.append(key).append('=').append(oneLine(convertParamWikilinks(p.value()))).append('\n');
                    }
                    sb.append("[/template]");
                }
            }
            case Node.Category ignored -> { }
            case Node.TocDirective toc -> sb.append(toc.show() ? "[toc]" : "[notoc]").append('\n');
        }
    }

    private interface Emitter {
        void emit(Node n, StringBuilder sb);
    }

    private static void renderChildren(List<Node> nodes, StringBuilder sb, Emitter emitter) {
        nodes.forEach(c -> emitter.emit(c, sb));
    }

    private static void wrap(StringBuilder sb, String open, List<Node> children, String close, Emitter emitter) {
        sb.append(open);
        renderChildren(children, sb, emitter);
        sb.append(close);
    }

    private static void block(StringBuilder sb, String open, List<Node> children, String close, Emitter emitter) {
        sb.append(open);
        renderChildren(children, sb, emitter);
        sb.append(close).append("\n\n");
    }

    private static String render(List<Node> nodes) {
        StringBuilder sb = new StringBuilder();
        renderChildren(nodes, sb, MarkupConverter::bb);
        return sb.toString().trim();
    }

    private static String labelOr(List<Node> label, String fallback) {
        String rendered = render(label);
        return rendered.isBlank() ? fallback : rendered;
    }

    private static int clampLevel(int level) {
        return Math.max(1, Math.min(6, level));
    }

    private static String slug(String title) {
        return title.trim().replace(' ', '_');
    }

    private static String inline(String text) {
        return text.replaceAll("\\s+", " ");
    }

    private static String oneLine(String value) {
        return value.replaceAll("\\s+", " ").trim();
    }

    private static String convertParamWikilinks(String value) {
        if (value == null || value.indexOf("[[") < 0) {
            return value == null ? "" : value;
        }
        Matcher matcher = PARAM_WIKILINK.matcher(value);
        StringBuilder sb = new StringBuilder();
        while (matcher.find()) {
            String target = matcher.group(1).trim();
            String label = matcher.group(2) != null && !matcher.group(2).isBlank()
                    ? matcher.group(2).trim() : target;
            matcher.appendReplacement(sb, Matcher.quoteReplacement(
                    "[wiki=" + slug(target) + "]" + label + "[/wiki]"));
        }
        matcher.appendTail(sb);
        return sb.toString();
    }

    private static String tidy(String s) {
        return s.replaceAll("[ \\t]+\n", "\n").replaceAll("\n{3,}", "\n\n").strip();
    }
}
