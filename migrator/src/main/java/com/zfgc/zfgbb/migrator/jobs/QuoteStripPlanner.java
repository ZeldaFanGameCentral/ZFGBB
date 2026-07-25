package com.zfgc.zfgbb.migrator.jobs;

import org.springframework.stereotype.Component;

import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class QuoteStripPlanner {

	public static final String KEEP_NESTED_EMBEDDED = "nested-embedded";
	public static final String KEEP_BLANK_EMBEDDED = "blank-embedded";
	public static final String KEEP_SOURCE_UNAVAILABLE = "source-unavailable";
	public static final String KEEP_SOURCE_NESTED = "nested-source";
	public static final String KEEP_MODIFIED = "modified";

	private final SourceReferenceOperations sourceReferences;

	public interface StripContext {
		String resolveFloorBody(Integer msgId);

		String normalize(String text);

		default void recordStrip(Integer msgId) {}

		default void recordKeep(Integer msgId, String reason) {}
	}

	public String stripFaithfulMsgQuotes(String input, StripContext context) {
		if (input == null)
			return null;
		return sourceReferences.rewriteSourceReferenceBodies(input,
				(msgId, body) -> decideStrip(msgId, body, context));
	}

	private String decideStrip(Integer msgId, String body, StripContext context) {
		if (body == null) {
			return body;
		}
		if (sourceReferences.containsSourceReference(body)) {
			context.recordKeep(msgId, KEEP_NESTED_EMBEDDED);
			return body;
		}
		String normalizedEmbedded = context.normalize(body);
		if (normalizedEmbedded.isBlank()) {
			context.recordKeep(msgId, KEEP_BLANK_EMBEDDED);
			return body;
		}
		String sourceBody = context.resolveFloorBody(msgId);
		if (sourceBody == null || sourceBody.isBlank()) {
			context.recordKeep(msgId, KEEP_SOURCE_UNAVAILABLE);
			return body;
		}
		if (sourceReferences.containsSourceReference(sourceBody)) {
			context.recordKeep(msgId, KEEP_SOURCE_NESTED);
			return body;
		}
		if (!normalizedEmbedded.equals(context.normalize(sourceBody))) {
			context.recordKeep(msgId, KEEP_MODIFIED);
			return body;
		}
		context.recordStrip(msgId);
		return "";
	}
}
