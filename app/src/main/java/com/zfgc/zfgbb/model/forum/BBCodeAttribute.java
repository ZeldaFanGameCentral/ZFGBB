package com.zfgc.zfgbb.model.forum;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.util.Set;
import java.util.regex.Pattern;

import com.zfgc.zfgbb.model.BaseModel;

public class BBCodeAttribute extends BaseModel {

	private Integer bbCodeAttributeId;
    private Integer attributeDataType;
    private String attributeIndex;
    private Integer bbCodeAttributeModeId;
    private String name;
    private AttributeDataType dataType;

    private static final Pattern HEX_COLOR = Pattern.compile("^#(?:[0-9a-fA-F]{3,4}|[0-9a-fA-F]{6}|[0-9a-fA-F]{8})$");
    private static final Pattern RGB_COLOR = Pattern.compile("^rgba?\\(\\s*\\d{1,3}\\s*,\\s*\\d{1,3}\\s*,\\s*\\d{1,3}\\s*(?:,\\s*(?:0|1|0?\\.\\d+)\\s*)?\\)$");
    private static final Pattern NAMED_COLOR = Pattern.compile("^[a-zA-Z]+$");
    private static final Pattern INTEGER = Pattern.compile("^\\d+$");
    private static final Pattern DIMENSION = Pattern.compile("^\\d+(?:\\.\\d+)?(?:px|pt|em|rem|%)?$");
    private static final Pattern IDENTIFIER = Pattern.compile("^[A-Za-z0-9_-]+$");
    private static final Pattern FONT_NAME = Pattern.compile("^[A-Za-z0-9 ,'\"_-]+$");
    private static final Pattern URL_SCHEME = Pattern.compile("^(?:https?|ftp|mailto):", Pattern.CASE_INSENSITIVE);
    private static final Set<String> LIST_TYPES = Set.of(
        "decimal","lower-roman","upper-roman","lower-alpha","upper-alpha","disc","circle","square","none"
    );

    public String transformValue(String value){
		if (value == null) {
			return "";
		}
		String trimmed = value.trim();

		switch(dataType){
		case TIMESTAMP:
			return createDate(trimmed);
		case COLOR:
			return sanitizeColor(trimmed);
		case INTEGER:
			return INTEGER.matcher(trimmed).matches() ? trimmed : "";
		case DIMENSION:
			return sanitizeDimension(trimmed);
		case URL:
			return sanitizeUrl(trimmed);
		case IDENTIFIER:
			return IDENTIFIER.matcher(trimmed).matches() ? trimmed : "";
		case FONT_NAME:
			return FONT_NAME.matcher(trimmed).matches() ? trimmed : "";
		case LIST_TYPE:
			return LIST_TYPES.contains(trimmed.toLowerCase()) ? trimmed.toLowerCase() : "decimal";
		case TEXT:
		default:
			return escapeHtml(trimmed);
		}
	}

    private static String sanitizeColor(String value) {
		String lower = value.toLowerCase();
		if (HEX_COLOR.matcher(lower).matches()) return lower;
		if (RGB_COLOR.matcher(lower).matches()) return lower;
		if (NAMED_COLOR.matcher(lower).matches()) return lower;
		return "";
	}

    private static String sanitizeDimension(String value) {
		if (!DIMENSION.matcher(value).matches()) return "";
		if (INTEGER.matcher(value).matches()) return value + "px";
		return value;
	}

    private static String sanitizeUrl(String value) {
		if (value.isEmpty()) return "";
		if (value.startsWith("/") && !value.startsWith("//")) return escapeHtml(value);
		if (URL_SCHEME.matcher(value).find()) return escapeHtml(value);
		return "";
	}

    private static String escapeHtml(String value) {
		StringBuilder out = new StringBuilder(value.length());
		for (int i = 0; i < value.length(); i++) {
			char c = value.charAt(i);
			switch (c) {
			case '<': out.append("&lt;"); break;
			case '>': out.append("&gt;"); break;
			case '"': out.append("&quot;"); break;
			case '\'': out.append("&#39;"); break;
			case '&': out.append("&amp;"); break;
			default: out.append(c);
			}
		}
		return out.toString();
	}

    public String createDate(String value){
    	Long ts = Long.parseLong(value + "000");
		return LocalDateTime.ofInstant(Instant.ofEpochMilli(ts), ZoneOffset.UTC).toString();
	}

	public Integer getBbCodeAttributeId() {
		return bbCodeAttributeId;
	}

	public void setBbCodeAttributeId(Integer bbCodeAttributeId) {
		this.bbCodeAttributeId = bbCodeAttributeId;
	}

	public Integer getAttributeDataType() {
		return attributeDataType;
	}

	public void setAttributeDataType(Integer attributeDataType) {
		this.attributeDataType = attributeDataType;
	}

	public Integer getBbCodeAttributeModeId() {
		return bbCodeAttributeModeId;
	}

	public void setBbCodeAttributeModeId(Integer bbCodeAttributeModeId) {
		this.bbCodeAttributeModeId = bbCodeAttributeModeId;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	@Override
	public Integer getId() {
		return bbCodeAttributeId;
	}

	@Override
	public void setId(Integer id) {
		bbCodeAttributeId = id;
	}

	public String getAttributeIndex() {
		return attributeIndex;
	}

	public void setAttributeIndex(String attributeIndex) {
		this.attributeIndex = attributeIndex;
	}

	public void setDataType(AttributeDataType dataType) {
		this.dataType = dataType;
	}

}
