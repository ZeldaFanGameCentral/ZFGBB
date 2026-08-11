package com.zfgc.zfgbb.content.renderer.bbcode;

import java.util.EnumMap;
import java.util.Map;

import org.springframework.stereotype.Component;

import com.zfgc.zfgbb.content.ContentScope;

@Component
public class BBCodeGrammarHolder {

	private record PublishedGrammars(BBCodeGrammar unfiltered, Map<ContentScope, BBCodeGrammar> bySurface) {}

	private volatile PublishedGrammars published = new PublishedGrammars(
			BBCodeGrammar.theGrammarThatDeclaresNothing(), Map.of());

	public BBCodeGrammar current() {
		return published.unfiltered();
	}

	public BBCodeGrammar current(ContentScope surface) {
		if (surface == null || !surface.isAConcreteSurface())
			return published.unfiltered();
		BBCodeGrammar honoured = published.bySurface().get(surface);
		return honoured == null ? published.unfiltered() : honoured;
	}

	public void publish(BBCodeGrammar unfiltered, Map<ContentScope, BBCodeGrammar> bySurface) {
		Map<ContentScope, BBCodeGrammar> copied = new EnumMap<>(ContentScope.class);
		copied.putAll(bySurface);
		published = new PublishedGrammars(unfiltered, copied);
	}

	public void publish(BBCodeGrammar everySurfaceReadsTheSameGrammar) {
		Map<ContentScope, BBCodeGrammar> bySurface = new EnumMap<>(ContentScope.class);
		for (ContentScope surface : ContentScope.values())
			if (surface.isAConcreteSurface())
				bySurface.put(surface, everySurfaceReadsTheSameGrammar);
		publish(everySurfaceReadsTheSameGrammar, bySurface);
	}
}
