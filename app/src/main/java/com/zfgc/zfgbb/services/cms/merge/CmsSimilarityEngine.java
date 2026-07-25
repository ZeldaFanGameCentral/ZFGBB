package com.zfgc.zfgbb.services.cms.merge;

import java.util.HashSet;
import java.util.Set;

import org.springframework.stereotype.Component;

import com.zfgc.zfgbb.migrator.converters.cms.CmsSupport;

@Component
public class CmsSimilarityEngine {

	public Set<String> tokenize(String title) {
		Set<String> tokens = new HashSet<>();
		if (title == null) {
			return tokens;
		}
		for (String token : CmsSupport.normalizeTitle(title).split("[^a-z0-9]+")) {
			if (token.length() >= 2) {
				tokens.add(token);
			}
		}
		return tokens;
	}

	public double calculateJaccardSimilarity(String titleA, String titleB) {
		String normalizedA = CmsSupport.normalizeTitle(titleA);
		String normalizedB = CmsSupport.normalizeTitle(titleB);
		if (normalizedA.isEmpty() || normalizedB.isEmpty()) {
			return 0.0;
		}
		if (normalizedA.equals(normalizedB)) {
			return 1.0;
		}
		Set<String> tokensA = tokenize(titleA);
		Set<String> tokensB = tokenize(titleB);
		if (tokensA.isEmpty() || tokensB.isEmpty()) {
			return 0.0;
		}
		Set<String> union = new HashSet<>(tokensA);
		union.addAll(tokensB);
		long sharedTokensCount = tokensA.stream().filter(tokensB::contains).count();
		return (double) sharedTokensCount / union.size();
	}

	public double calculateContainmentScore(Set<String> tokensA, Set<String> tokensB, String normalizedA, String normalizedB) {
		if (normalizedA.isEmpty() || normalizedB.isEmpty()) {
			return 0.0;
		}
		if (normalizedA.equals(normalizedB)) {
			return 1.0;
		}
		Set<String> shorterTokens = tokensA.size() <= tokensB.size() ? tokensA : tokensB;
		Set<String> longerTokens = shorterTokens == tokensA ? tokensB : tokensA;
		if (shorterTokens.size() < 2) {
			return 0.0;
		}
		long sharedTokensCount = shorterTokens.stream().filter(longerTokens::contains).count();
		return (double) sharedTokensCount / shorterTokens.size();
	}

	public double calculateTitleScore(String titleA, String titleB) {
		return calculateContainmentScore(tokenize(titleA), tokenize(titleB), CmsSupport.normalizeTitle(titleA), CmsSupport.normalizeTitle(titleB));
	}
}
