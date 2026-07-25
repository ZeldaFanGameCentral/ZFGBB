package com.zfgc.zfgbb.services.cms.merge;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.Set;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class CmsSimilarityEngineTest {

	private CmsSimilarityEngine similarityEngine;

	@BeforeEach
	void setUp() {
		similarityEngine = new CmsSimilarityEngine();
	}

	@Test
	void tokenizeSplitsAndFiltersShortTokens() {
		Set<String> tokens = similarityEngine.tokenize("The Quick Brown Fox 123 a");
		assertTrue(tokens.contains("the"));
		assertTrue(tokens.contains("quick"));
		assertTrue(tokens.contains("brown"));
		assertTrue(tokens.contains("fox"));
		assertTrue(tokens.contains("123"));
		assertEquals(5, tokens.size());
	}

	@Test
	void calculateJaccardSimilarityIdenticalTitles() {
		double score = similarityEngine.calculateJaccardSimilarity("Zelda Engine", "Zelda Engine");
		assertEquals(1.0, score, 0.001);
	}

	@Test
	void calculateJaccardSimilarityDifferentTitles() {
		double score = similarityEngine.calculateJaccardSimilarity("Zelda Engine", "Mario Engine");
		assertEquals(0.333, score, 0.01);
	}

	@Test
	void calculateContainmentScoreMatchesShorterInLonger() {
		Set<String> tokensA = similarityEngine.tokenize("Zelda Quest");
		Set<String> tokensB = similarityEngine.tokenize("The Legend of Zelda Quest Deluxe");
		double score = similarityEngine.calculateContainmentScore(tokensA, tokensB, "zelda quest", "the legend of zelda quest deluxe");
		assertEquals(1.0, score, 0.001);
	}

	@Test
	void calculateTitleScore() {
		double score = similarityEngine.calculateTitleScore("Super Mario", "Super Mario");
		assertEquals(1.0, score, 0.001);
	}
}
