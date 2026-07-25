package com.zfgc.zfgbb.model.forum;

import static org.jsoup.nodes.Entities.escape;

import java.time.Instant;
import java.time.OffsetDateTime;
import java.time.ZoneOffset;
import java.time.format.DateTimeFormatter;
import java.util.Locale;
import java.util.Optional;

public record BBCodeDateElement(String isoTimestamp) {

	public static final String LONG_FORM_CLASS = "bb-date-long";

	private static final DateTimeFormatter SERVER_RENDERED_FALLBACK_FORMAT =
			DateTimeFormatter.ofPattern("MMMM d, yyyy, h:mm:ss a", Locale.ENGLISH);

	public static BBCodeDateElement of(OffsetDateTime timestamp) {
		return new BBCodeDateElement(timestamp.toString());
	}

	public static String theEpochSecondsAsADateElement(String value) {
		String epochSeconds = value == null ? "" : value.trim();
		if (epochSeconds.isEmpty() || !epochSeconds.chars().allMatch(digit -> digit >= '0' && digit <= '9'))
			return "";
		try {
			long epochMilliseconds = Long.parseLong(epochSeconds + "000");
			return of(OffsetDateTime.ofInstant(Instant.ofEpochMilli(epochMilliseconds), ZoneOffset.UTC)).toHtml();
		} catch (NumberFormatException outOfRange) {
			return "";
		}
	}

	public String toHtml() {
		if (isoTimestamp == null || isoTimestamp.isBlank())
			return "";
		return itsLongFormText()
				.map(text -> "<time class=\"" + LONG_FORM_CLASS + "\" datetime=\"" + escape(isoTimestamp) + "\">"
						+ text + "</time>")
				.orElseGet(() -> escape(isoTimestamp));
	}

	public Optional<String> itsLongFormText() {
		try {
			return Optional.of(SERVER_RENDERED_FALLBACK_FORMAT.format(OffsetDateTime.parse(isoTimestamp)));
		} catch (RuntimeException unparseableTimestamp) {
			return Optional.empty();
		}
	}
}
