package com.zfgc.zfgbb.migrator.markup;

import java.util.List;

public sealed interface Node {

    record Document(List<Node> children) implements Node {}

    record Text(String value) implements Node {}

    record Bold(List<Node> children) implements Node {}

    record Italic(List<Node> children) implements Node {}

    record Heading(int level, List<Node> children) implements Node {}

    record Paragraph(List<Node> children) implements Node {}

    record LineBreak() implements Node {}

    record WikiLink(String target, List<Node> label) implements Node {}

    record ExternalLink(String url, List<Node> label) implements Node {}

    record Image(String filename, List<String> options) implements Node {}

    record CodeBlock(String text) implements Node {}

    record ListBlock(boolean ordered, List<Item> items) implements Node {
        public record Item(List<Node> children) {}
    }

    record Table(boolean fullWidth, List<Row> rows) implements Node {
        public record Row(List<Cell> cells) {}
        public record Cell(boolean header, List<Node> children) {}
    }

    record Template(String name, List<Param> params) implements Node {
        public record Param(String name, String value) {}
    }

    record Category(String name) implements Node {}

    record TocDirective(boolean show) implements Node {}
}
