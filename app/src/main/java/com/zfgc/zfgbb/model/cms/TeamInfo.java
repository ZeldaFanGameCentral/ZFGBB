package com.zfgc.zfgbb.model.cms;

import java.util.ArrayList;
import java.util.List;

import lombok.Data;

@Data
public class TeamInfo {
	private Integer teamId;
	private String name;
	private String description;
	private List<TeamMember> members = new ArrayList<>();
}
