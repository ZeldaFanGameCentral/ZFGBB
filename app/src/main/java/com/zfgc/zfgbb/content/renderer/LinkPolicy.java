package com.zfgc.zfgbb.content.renderer;

import java.util.Optional;
import java.util.regex.Pattern;

public class LinkPolicy {

	private static final Pattern SCHEMELESS_DOMAIN = Pattern.compile(
			"^[a-z0-9](?:[a-z0-9.-]*[a-z0-9])?\\.[a-z]{2,}(?:[:/?#].*)?$", Pattern.CASE_INSENSITIVE);

	private static final Pattern LINKABLE_URL_SCHEME = Pattern.compile("^(?:https?|ftp|mailto):",
			Pattern.CASE_INSENSITIVE);

	private static final int THE_LAST_C0_CONTROL = 0x1F;

	private static final int THE_DELETE_CHARACTER = 0x7F;

	public static final String SCHEME_A_SCHEMELESS_DOMAIN_IS_PROMOTED_TO = "https://";

	public static Optional<String> theSafeHrefFor(String value) {
		if (value == null)
			return Optional.empty();
		String resolvable = theValueABrowserWouldResolve(value);
		if (resolvable.isEmpty() || carriesAControlCharacter(resolvable))
			return Optional.empty();
		if (resolvable.startsWith("#") || isSafeRelativeUrl(resolvable)
				|| LINKABLE_URL_SCHEME.matcher(resolvable).find())
			return Optional.of(resolvable);
		if (isSchemelessDomain(resolvable))
			return Optional.of(SCHEME_A_SCHEMELESS_DOMAIN_IS_PROMOTED_TO + resolvable);
		return Optional.empty();
	}

	private static String theValueABrowserWouldResolve(String value) {
		return value.replace("\t", "").replace("\n", "").replace("\r", "").trim();
	}

	public static boolean isSafeRelativeUrl(String value) {
		return value != null && !carriesAControlCharacter(value) && value.startsWith("/")
				&& !value.startsWith("//") && value.indexOf('\\') < 0;
	}

	public static boolean isSchemelessDomain(String value) {
		return value != null && !carriesAControlCharacter(value) && SCHEMELESS_DOMAIN.matcher(value).matches();
	}

	private static boolean carriesAControlCharacter(String value) {
		return value.chars()
				.anyMatch(character -> character <= THE_LAST_C0_CONTROL || character == THE_DELETE_CHARACTER);
	}
}
