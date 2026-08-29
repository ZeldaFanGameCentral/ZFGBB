package com.zfgc.zfgbb.model.forum;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.zfgc.zfgbb.model.BaseModel;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class BBCodeAttributeMode extends BaseModel {
	@JsonIgnore
	private Integer bbCodeAttributeModeId;
    private Integer bbCodeConfigId;
    private Boolean contentIsAttributeFlag = false;
    private String openTag;
    private String closeTag;
    private Boolean outputContentFlag = true;
    private String contentSemanticRole;
    private List<BBCodeAttribute> attributes = new ArrayList<>();

    public Optional<AttributeSemanticRole> declaredContentSemanticRole() {
		return AttributeSemanticRole.forCode(contentSemanticRole);
	}

	@Override
	public Integer getId() {
		return bbCodeAttributeModeId;
	}

	@Override
	public void setId(Integer id) {
		bbCodeAttributeModeId = id;
	}
}
