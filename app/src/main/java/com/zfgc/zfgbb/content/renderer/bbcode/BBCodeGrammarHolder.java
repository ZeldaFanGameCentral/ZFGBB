package com.zfgc.zfgbb.content.renderer.bbcode;

import org.springframework.stereotype.Component;

@Component
public class BBCodeGrammarHolder {

	private volatile BBCodeGrammar current = BBCodeGrammar.theGrammarThatDeclaresNothing();

	public BBCodeGrammar current() {
		return current;
	}

	public void publish(BBCodeGrammar grammar) {
		current = grammar;
	}
}
