package com.zfgc.zfgbb.content.renderer.bbcode;

import java.util.List;
import java.util.Optional;

import com.zfgc.zfgbb.model.forum.AttributeSemanticRole;
import com.zfgc.zfgbb.model.forum.BBCodeAttribute;
import com.zfgc.zfgbb.model.forum.BBCodeAttributeMode;
import com.zfgc.zfgbb.model.forum.BBCodeConfig;

public final class BBCodeTag implements BBCodeNode {

	private final BBCodeConfig config;

	private final ContentLevel contentLevel;

	private final List<BBCodeNode> children;

	private final String openMarkup;

	private final Optional<String> closeMarkup;

	private final boolean awaitingItsAuthorsCloser;

	private final boolean openedByAnImplicitMarker;

	private final AuthoredSource authoredSource;

	private final BBCodeConfig.ParsedAttributes parsedAttributes;

	private final Optional<BBCodeAttributeMode> attributeMode;

	BBCodeTag(BBCodeConfig config, String openMarkup, Optional<String> closeMarkup, ContentLevel contentLevel,
			AuthoredSource authoredSource, BBCodeConfig.ParsedAttributes parsedAttributes,
			Optional<BBCodeAttributeMode> attributeMode, boolean awaitingItsAuthorsCloser,
			boolean openedByAnImplicitMarker, List<BBCodeNode> children) {
		this.config = config;
		this.openMarkup = openMarkup;
		this.closeMarkup = closeMarkup;
		this.contentLevel = contentLevel;
		this.authoredSource = authoredSource;
		this.parsedAttributes = parsedAttributes;
		this.attributeMode = attributeMode;
		this.awaitingItsAuthorsCloser = awaitingItsAuthorsCloser;
		this.openedByAnImplicitMarker = openedByAnImplicitMarker;
		this.children = children;
	}

	public BBCodeTag itsMarkupAndBodyReplacedBy(String replacementOpenMarkup, List<BBCodeNode> replacementChildren) {
		return new BBCodeTag(config, replacementOpenMarkup, closeMarkup, contentLevel, authoredSource, parsedAttributes,
				attributeMode, awaitingItsAuthorsCloser, openedByAnImplicitMarker, replacementChildren);
	}

	public AuthoredSource authoredSource() {
		return authoredSource;
	}

	public BBCodeConfig.ParsedAttributes parsedAttributes() {
		return parsedAttributes;
	}

	public Optional<BBCodeAttributeMode> attributeMode() {
		return attributeMode;
	}

	public Optional<String> valueWithRole(AttributeSemanticRole role) {
		if (attributeMode.isEmpty())
			return Optional.empty();
		List<BBCodeAttribute> attributes = attributeMode.get().getAttributes();
		List<String> rawValues = parsedAttributes.rawValuesInTheOrder(attributeMode.get());
		for (int index = 0; index < attributes.size(); index++)
			if (attributes.get(index).declaredSemanticRole().filter(role::equals).isPresent()
					&& rawValues.get(index) != null && !rawValues.get(index).isBlank())
				return Optional.of(rawValues.get(index).trim());
		return Optional.empty();
	}

	public boolean itsBodyCarriesTheRole(AttributeSemanticRole role) {
		return attributeMode.flatMap(BBCodeAttributeMode::declaredContentSemanticRole)
				.filter(role::equals).isPresent();
	}

	public Optional<String> theValueOfTheDeclaredAttribute(String attributeName) {
		return attributeMode.flatMap(mode -> parsedAttributes.theValueOfTheAttributeDeclaredBy(mode,
				attributeName));
	}

	public BBCodeConfig config() {
		return config;
	}

	public ContentLevel contentLevel() {
		return contentLevel;
	}

	public String openMarkup() {
		return openMarkup;
	}

	public Optional<String> closeMarkup() {
		return closeMarkup;
	}

	public boolean awaitingItsAuthorsCloser() {
		return awaitingItsAuthorsCloser;
	}

	public boolean openedByAnImplicitMarker() {
		return openedByAnImplicitMarker;
	}

	@Override
	public List<BBCodeNode> children() {
		return children;
	}
}
