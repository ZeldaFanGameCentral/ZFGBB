package com.zfgc.zfgbb.mapstruct.users;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import com.zfgc.zfgbb.config.BBMapperConfig;
import com.zfgc.zfgbb.dbo.AwardDbo;
import com.zfgc.zfgbb.model.users.Award;

@Mapper(config = BBMapperConfig.class)
public interface AwardMap {

	@Mapping(target = "reason", ignore = true)
	@Mapping(target = "grantedTs", ignore = true)
	@Mapping(target = "contentEntityId", ignore = true)
	Award toCatalogEntry(AwardDbo dbo);
}
