package com.zfgc.zfgbb.dao.users;

import org.springframework.stereotype.Repository;

import com.zfgc.zfgbb.dao.KeyedDao;
import com.zfgc.zfgbb.dbo.TeamMemberDbo;
import com.zfgc.zfgbb.dbo.TeamMemberDboExample;
import com.zfgc.zfgbb.mappers.TeamMemberDboMapper;

@Repository
public class TeamMemberDao extends KeyedDao<TeamMemberDbo, TeamMemberDboExample, Integer> {

	public TeamMemberDao(TeamMemberDboMapper mapper) {
		super(mapper);
	}
}
