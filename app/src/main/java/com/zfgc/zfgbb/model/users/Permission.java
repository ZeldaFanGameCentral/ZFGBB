package com.zfgc.zfgbb.model.users;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.zfgc.zfgbb.model.BaseModel;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

@Getter
@Setter
@SuperBuilder(toBuilder=true)
@NoArgsConstructor
@AllArgsConstructor
public class Permission extends BaseModel {

	@JsonIgnore
	private Integer permissionId;
	private String permissionCode;
	private String permissionName;
	
	public Integer getPermissionId() {
		return permissionId;
	}

	public void setPermissionId(Integer permissionId) {
		this.permissionId = permissionId;
	}

	public String getPermissionCode() {
		return permissionCode;
	}

	public void setPermissionCode(String permissionCode) {
		this.permissionCode = permissionCode;
	}

	public String getPermissionName() {
		return permissionName;
	}

	public void setPermissionName(String permissionName) {
		this.permissionName = permissionName;
	}

	@Override
	public Integer getId() {
		return permissionId;
	}

	@Override
	public void setId(Integer id) {
		permissionId = id;
	}
	
}