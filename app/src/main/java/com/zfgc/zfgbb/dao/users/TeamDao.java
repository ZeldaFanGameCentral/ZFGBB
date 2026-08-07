package com.zfgc.zfgbb.dao.users;

import org.springframework.stereotype.Repository;

import com.zfgc.zfgbb.dao.KeyedDao;
import com.zfgc.zfgbb.dbo.TeamDbo;
import com.zfgc.zfgbb.dbo.TeamDboExample;
import com.zfgc.zfgbb.mappers.TeamDboMapper;

@Repository
public class TeamDao extends KeyedDao<TeamDbo, TeamDboExample, Integer> {

	public TeamDao(TeamDboMapper mapper) {
		super(mapper);
	}
}
