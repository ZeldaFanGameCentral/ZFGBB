package com.zfgc.zfgbb.content.renderer;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.EnumMap;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.springframework.core.io.Resource;
import org.springframework.core.io.ResourceLoader;
import org.springframework.stereotype.Component;

import tools.jackson.core.JacksonException;
import tools.jackson.core.type.TypeReference;
import tools.jackson.databind.ObjectMapper;

import com.zfgc.zfgbb.content.ContentFormat;
import com.zfgc.zfgbb.content.renderer.bbcode.BBCodeTag;

@Component
public class ConversionNotes {

	static final String MESSAGE_FILE = "classpath:content/renderer/conversion-notes.json";

	enum Note {

		INLINE_CODE_SPAN_CARRYING_BB_CODE,
		CODE_BLOCK_CARRYING_THE_BB_CODE_CODE_CLOSER,
		ORDERED_LIST_NOT_STARTING_AT_ONE,
		NO_ENABLED_BB_CODE_CARRIES_THIS_CONSTRUCT,
		LINK_TITLE_TEXT,
		THE_CODES_THE_FLIP_COULD_NOT_CARRY("codes", "target"),
		A_CODE_THE_OTHER_FORMAT_DOES_NOT_CARRY("code", "target"),
		CONTENT_THE_OTHER_FORMAT_DOES_NOT_CARRY("target");

		private final Set<String> placeholders;

		Note(String... placeholders) {
			this.placeholders = Set.of(placeholders);
		}

		private void messageMustCarryExactlyItsPlaceholders(String message) {
			Set<String> carried = placeholdersIn(message);
			if (!carried.equals(placeholders))
				throw new IllegalStateException(
						name() + " takes " + placeholders + " but its message carries " + carried);
		}
	}

	private final Map<Note, String> messages;

	public ConversionNotes(ResourceLoader resources, ObjectMapper objectMapper) {
		this.messages = messagesDeclaredIn(resources.getResource(MESSAGE_FILE), objectMapper);
	}

	static Map<Note, String> messagesDeclaredIn(Resource messageFile, ObjectMapper objectMapper) {
		if (!messageFile.exists())
			throw new IllegalStateException("conversion note message file missing: " + messageFile.getDescription());
		Map<String, Object> declared = read(messageFile, objectMapper);
		List<String> missing = new ArrayList<>();
		for (Note note : Note.values())
			if (!declared.containsKey(note.name()))
				missing.add(note.name());
		if (!missing.isEmpty())
			throw new IllegalStateException("conversion note message file declares no message for " + missing);
		Set<String> unknown = new LinkedHashSet<>(declared.keySet());
		for (Note note : Note.values())
			unknown.remove(note.name());
		if (!unknown.isEmpty())
			throw new IllegalStateException("conversion note message file declares unknown keys " + unknown);
		Map<Note, String> messages = new EnumMap<>(Note.class);
		for (Note note : Note.values()) {
			Object message = declared.get(note.name());
			if (!(message instanceof String text))
				throw new IllegalStateException(note.name() + " message is not a string: " + message);
			note.messageMustCarryExactlyItsPlaceholders(text);
			messages.put(note, text);
		}
		return messages;
	}

	private static Map<String, Object> read(Resource messageFile, ObjectMapper objectMapper) {
		try (InputStream declared = messageFile.getInputStream()) {
			return objectMapper.readValue(declared, new TypeReference<Map<String, Object>>() {});
		}
		catch (IOException | JacksonException unreadable) {
			throw new IllegalStateException(
					"conversion note message file unreadable: " + messageFile.getDescription(), unreadable);
		}
	}

	private static Set<String> placeholdersIn(String message) {
		Set<String> placeholders = new LinkedHashSet<>();
		int cursor = 0;
		while (cursor < message.length()) {
			int open = message.indexOf('{', cursor);
			if (open < 0)
				return placeholders;
			int close = message.indexOf('}', open);
			if (close < 0)
				throw new IllegalStateException("conversion note message has an unclosed placeholder: " + message);
			placeholders.add(message.substring(open + 1, close));
			cursor = close + 1;
		}
		return placeholders;
	}

	private String message(Note note, Map<String, String> values) {
		String message = messages.get(note);
		StringBuilder written = new StringBuilder(message.length());
		int cursor = 0;
		while (cursor < message.length()) {
			int open = message.indexOf('{', cursor);
			if (open < 0)
				break;
			int close = message.indexOf('}', open);
			String placeholder = message.substring(open + 1, close);
			String value = values.get(placeholder);
			if (value == null)
				throw new IllegalStateException(note.name() + " was written with no value for " + placeholder);
			written.append(message, cursor, open).append(value);
			cursor = close + 1;
		}
		return written.append(message, cursor, message.length()).toString();
	}

	String inlineCodeSpanCarryingBBCode() {
		return message(Note.INLINE_CODE_SPAN_CARRYING_BB_CODE, Map.of());
	}

	String codeBlockCarryingTheBBCodeCodeCloser() {
		return message(Note.CODE_BLOCK_CARRYING_THE_BB_CODE_CODE_CLOSER, Map.of());
	}

	String orderedListNotStartingAtOne() {
		return message(Note.ORDERED_LIST_NOT_STARTING_AT_ONE, Map.of());
	}

	String noEnabledBBCodeCarriesThisConstruct() {
		return message(Note.NO_ENABLED_BB_CODE_CARRIES_THIS_CONSTRUCT, Map.of());
	}

	String linkTitleText() {
		return message(Note.LINK_TITLE_TEXT, Map.of());
	}

	String codesTheFlipCouldNotCarry(Set<BBCodeTag> keptAsBBCode, ContentFormat target) {
		Set<String> codes = new LinkedHashSet<>();
		for (BBCodeTag tag : keptAsBBCode)
			codes.add("[" + tag.config().getCode() + "]");
		return message(Note.THE_CODES_THE_FLIP_COULD_NOT_CARRY,
				Map.of("codes", String.join(", ", codes), "target", nameOf(target)));
	}

	String aCodeTheOtherFormatDoesNotCarry(String code, ContentFormat target) {
		return message(Note.A_CODE_THE_OTHER_FORMAT_DOES_NOT_CARRY,
				Map.of("code", code, "target", nameOf(target)));
	}

	String contentTheOtherFormatDoesNotCarry(ContentFormat target) {
		return message(Note.CONTENT_THE_OTHER_FORMAT_DOES_NOT_CARRY, Map.of("target", nameOf(target)));
	}

	private static String nameOf(ContentFormat format) {
		return format == ContentFormat.MARKDOWN ? "Markdown" : "BBCode";
	}
}
