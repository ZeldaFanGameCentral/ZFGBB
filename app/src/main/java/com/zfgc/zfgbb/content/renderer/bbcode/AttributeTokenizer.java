package com.zfgc.zfgbb.content.renderer.bbcode;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.TreeMap;

import com.zfgc.zfgbb.model.forum.AttributeValuePolicy;
import com.zfgc.zfgbb.model.forum.BBCodeConfig;
import com.zfgc.zfgbb.model.forum.BBCodeConfig.ParsedAttributes;

public final class AttributeTokenizer {

	private record AttributeSite(String name, int nameStart, int valueStart) {}

	private final BBCodeConfig bbCode;

	private final String attributeText;

	private final List<String> declaredNames;

	private final List<AttributeSite> sites;

	public AttributeTokenizer(BBCodeConfig bbCode, String attributeText) {
		this.bbCode = bbCode;
		this.attributeText = attributeText;
		this.declaredNames = declaredNames();
		this.sites = sitesWhereADeclaredNameOpensAValue();
	}

	public ParsedAttributes parseAttributeValues() {
		Map<String, String> attributeValues = new TreeMap<>();
		for (int index = 0; index < sites.size(); index++) {
			AttributeSite site = sites.get(index);
			int nextSiteStart = index + 1 < sites.size()
					? sites.get(index + 1).nameStart()
					: attributeText.length();
			// find the value
			attributeValues.put(site.name(), attributeText
					.substring(site.valueStart(), endOfValue(site, nextSiteStart))
					.trim());
		}

		StringBuilder attFormat = new StringBuilder();
		for (String declaredName : declaredNames)
			if (attributeValues.containsKey(declaredName))
				attFormat.append(declaredName);
		return new ParsedAttributes(attFormat.toString(), attributeValues,
				commaSeparatedValuesOf(attributeValues.get(BBCodeConfig.NAMELESS_ATTRIBUTE_NAME)));
	}

	private List<String> declaredNames() {
		List<String> declared = new ArrayList<>();
		for (String declaredName : Optional.ofNullable(bbCode.getAllAttributeNamesAsString()).orElse("").split(","))
			if (!declaredName.isEmpty())
				declared.add(declaredName);
		return declared;
	}

	private List<AttributeSite> sitesWhereADeclaredNameOpensAValue() {
		Map<String, AttributeSite> lastSiteOfEachName = new LinkedHashMap<>();
		if (declaredNames.contains(BBCodeConfig.NAMELESS_ATTRIBUTE_NAME)
				&& attributeText.startsWith(BBCodeConfig.NAMELESS_ATTRIBUTE_NAME))
			lastSiteOfEachName.put(BBCodeConfig.NAMELESS_ATTRIBUTE_NAME,
					new AttributeSite(BBCodeConfig.NAMELESS_ATTRIBUTE_NAME, 0,
							BBCodeConfig.NAMELESS_ATTRIBUTE_NAME.length()));
		for (int index = 0; index < attributeText.length(); index++) {
			if (index > 0 && !Character.isWhitespace(attributeText.charAt(index - 1)))
				continue;
			Optional<String> opened = declaredNameWrittenAt(index);
			// if the attribute isn't found, then fuck it, abort!
			if (opened.isEmpty())
				continue;
			String name = opened.get();
			lastSiteOfEachName.put(name, new AttributeSite(name, index, index + name.length()));
			index += name.length() - 1;
		}
		List<AttributeSite> found = new ArrayList<>(lastSiteOfEachName.values());
		found.sort(Comparator.comparingInt(AttributeSite::nameStart));
		return found;
	}

	private Optional<String> declaredNameWrittenAt(int index) {
		for (String declaredName : declaredNames)
			if (!declaredName.equals(BBCodeConfig.NAMELESS_ATTRIBUTE_NAME)
					&& attributeText.startsWith(declaredName, index))
				return Optional.of(declaredName);
		return Optional.empty();
	}

	private int endOfValue(AttributeSite site, int nextSiteStart) {
		if (valueOfMayContainWhitespace(site.name()))
			return nextSiteStart;
		for (int index = site.valueStart(); index < nextSiteStart; index++)
			if (Character.isWhitespace(attributeText.charAt(index)))
				return index;
		return nextSiteStart;
	}

	private boolean valueOfMayContainWhitespace(String attributeName) {
		return BBCodeConfig.NAMELESS_ATTRIBUTE_NAME.equals(attributeName)
				|| bbCode.valuePolicyOfTheAttributeNamed(attributeName)
						.filter(AttributeValuePolicy::valueAdmitsWhitespace)
						.isPresent();
	}

	private List<String> commaSeparatedValuesOf(String value) {
		if (value == null)
			return List.of();
		List<String> values = new ArrayList<>();
		int depth = 0;
		int partStart = 0;
		for (int index = 0; index < value.length(); index++) {
			char written = value.charAt(index);
			if (written == '(')
				depth++;
			else if (written == ')' && depth > 0)
				depth--;
			else if (written == ',' && depth == 0) {
				values.add(value.substring(partStart, index).trim());
				partStart = index + 1;
			}
		}
		values.add(value.substring(partStart).trim());
		return List.copyOf(values);
	}
}
