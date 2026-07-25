package com.zfgc.zfgbb.model.forum;

import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.zfgc.zfgbb.model.BaseModel;
import com.zfgc.zfgbb.model.users.Permission;
import com.zfgc.zfgbb.security.Securable;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Forum extends BaseModel implements Securable{

	private List<Category> categories = new ArrayList<>();
	private String boardName;

	@JsonIgnore
	private Integer boardId;
	@JsonIgnore
	private List<Permission> boardPermissions = new ArrayList<>();

	@Override
	@JsonIgnore
	public List<Permission> getPermissions() {
		return this.boardPermissions;
	}

	@Override
	public Integer getId() {
		return boardId;
	}

	@Override
	public void setId(Integer id) {
		boardId = id;
	}
}
