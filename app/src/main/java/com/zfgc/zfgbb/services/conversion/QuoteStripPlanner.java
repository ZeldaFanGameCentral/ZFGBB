package com.zfgc.zfgbb.services.conversion;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.zfgc.zfgbb.content.renderer.BBCodeService;
import com.zfgc.zfgbb.content.renderer.BBCodeService.TagToken;

@Component
public class QuoteStripPlanner {

	public static final String KEEP_NESTED_EMBEDDED = "nested-embedded";
	public static final String KEEP_BLANK_EMBEDDED = "blank-embedded";
	public static final String KEEP_SOURCE_UNAVAILABLE = "source-unavailable";
	public static final String KEEP_SOURCE_NESTED = "nested-source";
	public static final String KEEP_MODIFIED = "modified";

	@Autowired
	private BBCodeService bbCodeService;

	public interface StripContext {
		String resolveFloorBody(Integer msgId);

		String normalize(String text);

		default void recordStrip(Integer msgId) {}

		default void recordKeep(Integer msgId, String reason) {}
	}

	public String stripFaithfulMsgQuotes(String input, StripContext context) {
		if (input == null) {
			return null;
		}
		StringBuilder out = new StringBuilder(input.length());
		int cursor = 0;
		int length = input.length();
		while (cursor < length) {
			if (input.charAt(cursor) != '[') {
				out.append(input.charAt(cursor));
				cursor++;
				continue;
			}
			TagToken tag = bbCodeService.readTag(input, cursor);
			if (tag == null) {
				out.append(input.charAt(cursor));
				cursor++;
				continue;
			}
			if (!tag.closing() && bbCodeService.isLiteralCode(tag.code())) {
				int close = bbCodeService.findLiteralClose(input, tag.endIndex(), tag.code());
				if (close < 0) {
					out.append(input, cursor, length);
					return out.toString();
				}
				out.append(input, cursor, close);
				cursor = close;
				continue;
			}
			if (!tag.closing() && "QUOTE".equals(tag.code())) {
				Integer msgId = bbCodeService.extractQuoteMsgId(tag.attributeText());
				if (msgId != null) {
					int bodyStart = tag.endIndex();
					int closerStart = bbCodeService.findQuoteCloser(input, bodyStart);
					if (closerStart < 0) {
						out.append(input, cursor, tag.endIndex());
						cursor = tag.endIndex();
						continue;
					}
					TagToken closer = bbCodeService.readTag(input, closerStart);
					int closerEnd = closer != null ? closer.endIndex() : closerStart + "[/quote]".length();
					String body = input.substring(bodyStart, closerStart);
					out.append(input, cursor, bodyStart);
					out.append(decideStrip(msgId, body, context));
					out.append(input, closerStart, closerEnd);
					cursor = closerEnd;
					continue;
				}
			}
			out.append(input, cursor, tag.endIndex());
			cursor = tag.endIndex();
		}
		return out.toString();
	}

	private String decideStrip(Integer msgId, String body, StripContext context) {
		if (body == null) {
			return body;
		}
		if (containsNestedQuote(body)) {
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
		if (containsNestedQuote(sourceBody)) {
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

	private boolean containsNestedQuote(String input) {
		if (input == null) {
			return false;
		}
		int cursor = 0;
		int length = input.length();
		while (cursor < length) {
			if (input.charAt(cursor) != '[') {
				cursor++;
				continue;
			}
			TagToken tag = bbCodeService.readTag(input, cursor);
			if (tag == null) {
				cursor++;
				continue;
			}
			if (!tag.closing() && bbCodeService.isLiteralCode(tag.code())) {
				int close = bbCodeService.findLiteralClose(input, tag.endIndex(), tag.code());
				if (close < 0) {
					return false;
				}
				cursor = close;
				continue;
			}
			if (!tag.closing() && "QUOTE".equals(tag.code())) {
				return true;
			}
			cursor = tag.endIndex();
		}
		return false;
	}
}
