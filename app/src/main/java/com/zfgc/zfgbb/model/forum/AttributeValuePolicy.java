package com.zfgc.zfgbb.model.forum;

import java.util.Locale;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import java.util.regex.Pattern;

public record AttributeValuePolicy(Optional<Pattern> validationPattern, String fallbackValue,
		boolean valueAdmitsWhitespace, boolean lowercasesValue, Optional<String> bareIntegerUnit,
		Set<String> allowedValues, Map<String, String> valueMappings) {

	public static final int LONGEST_VALUE_A_VALIDATION_PATTERN_IS_APPLIED_TO = 4096;

	public static AttributeValuePolicy rejectingEveryValue(String fallbackValue) {
		return new AttributeValuePolicy(Optional.empty(), fallbackValue, false, false, Optional.empty(), Set.of(),
				Map.of());
	}

	public boolean admits(String value) {
		if (value.length() > LONGEST_VALUE_A_VALIDATION_PATTERN_IS_APPLIED_TO)
			return false;
		String considered = lowercasesValue ? value.toLowerCase(Locale.ROOT) : value;
		if (!allowedValues.isEmpty())
			return allowedValues.contains(considered);
		return validationPattern.filter(pattern -> pattern.matcher(considered).matches()).isPresent();
	}

	public String apply(String value) {
		if (value.length() > LONGEST_VALUE_A_VALIDATION_PATTERN_IS_APPLIED_TO)
			return fallbackValue;
		String considered = lowercasesValue ? value.toLowerCase(Locale.ROOT) : value;
		String mapped = valueMappings.get(keyThisValueMapsUnder(considered));
		if (mapped != null)
			return mapped;
		return withBareIntegerUnitSuffixed(valueThisPolicyAccepts(considered));
	}

	private static String keyThisValueMapsUnder(String considered) {
		if (!isABareInteger(considered))
			return considered;
		int firstSignificantDigit = 0;
		while (firstSignificantDigit < considered.length() - 1 && considered.charAt(firstSignificantDigit) == '0')
			firstSignificantDigit++;
		return considered.substring(firstSignificantDigit);
	}

	private String valueThisPolicyAccepts(String considered) {
		if (!allowedValues.isEmpty())
			return allowedValues.contains(considered) ? considered : fallbackValue;
		if (validationPattern.isEmpty())
			return fallbackValue;
		return validationPattern.get().matcher(considered).matches() ? considered : fallbackValue;
	}

	private String withBareIntegerUnitSuffixed(String accepted) {
		return bareIntegerUnit.isPresent() && isABareInteger(accepted)
				? accepted + bareIntegerUnit.get()
				: accepted;
	}

	private static boolean isABareInteger(String value) {
		if (value.isEmpty())
			return false;
		for (int index = 0; index < value.length(); index++)
			if (value.charAt(index) < '0' || value.charAt(index) > '9')
				return false;
		return true;
	}
}
