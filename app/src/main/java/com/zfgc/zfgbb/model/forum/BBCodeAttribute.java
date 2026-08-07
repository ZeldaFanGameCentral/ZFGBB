package com.zfgc.zfgbb.model.forum;

import static org.jsoup.nodes.Entities.escape;

import java.util.Optional;

import org.jsoup.nodes.Entities;

import com.zfgc.zfgbb.model.BaseModel;
import com.zfgc.zfgbb.content.renderer.LinkPolicy;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class BBCodeAttribute extends BaseModel {

	private Integer bbCodeAttributeId;
    private String attributeDataType;
    private String attributeIndex;
    private Integer bbCodeAttributeModeId;
    private String name;
    private String semanticRole;
    private AttributeDataType dataType;
    private AttributeValuePolicy valuePolicy = AttributeValuePolicy.rejectingEveryValue("");

    public Optional<AttributeSemanticRole> declaredSemanticRole() {
		return AttributeSemanticRole.forCode(semanticRole);
	}

    public String transformValue(String value){
		if (value == null) {
			return "";
		}
		String trimmed = value.trim();

		return switch (dataType) {
		case TEXT -> escape(trimmed);
		case URL -> LinkPolicy.theSafeHrefFor(trimmed).map(Entities::escape).orElse("");
		default -> valuePolicy.apply(trimmed);
		};
	}

	@Override
	public Integer getId() {
		return bbCodeAttributeId;
	}

	@Override
	public void setId(Integer id) {
		bbCodeAttributeId = id;
	}
}
