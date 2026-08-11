package com.zfgc.zfgbb.content.renderer.bbcode;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Deque;
import java.util.List;

public sealed interface BBCodeNode permits BBCodeDocument, BBCodeTag, BBCodeText {

	List<BBCodeNode> children();

	default List<BBCodeNode> selfAndEveryDescendant() {
		List<BBCodeNode> nodes = new ArrayList<>();
		Deque<BBCodeNode> pending = new ArrayDeque<>();
		pending.push(this);
		while (!pending.isEmpty()) {
			BBCodeNode node = pending.pop();
			nodes.add(node);
			List<BBCodeNode> children = node.children();
			for (int child = children.size() - 1; child >= 0; child--)
				pending.push(children.get(child));
		}
		return nodes;
	}
}
