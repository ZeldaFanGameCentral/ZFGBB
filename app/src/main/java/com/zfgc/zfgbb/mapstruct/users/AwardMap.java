package com.zfgc.zfgbb.mapstruct.users;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import com.zfgc.zfgbb.config.BBMapperConfig;
import java.time.OffsetDateTime;

import com.zfgc.zfgbb.dbo.AwardDbo;
import com.zfgc.zfgbb.dbo.UserAwardDbo;
import com.zfgc.zfgbb.model.users.Award;

@Mapper(config = BBMapperConfig.class)
public interface AwardMap {

	@Mapping(target = "reason", ignore = true)
	@Mapping(target = "grantedTs", ignore = true)
	@Mapping(target = "contentEntityId", ignore = true)
	Award toCatalogEntry(AwardDbo dbo);

	@Mapping(target = "awardId", source = "award.awardId")
	@Mapping(target = "reason", source = "grant.reason")
	@Mapping(target = "contentEntityId", source = "grant.contentEntityId")
	@Mapping(target = "grantedTs", source = "grant.grantedTs")
	Award toGrantedAward(AwardDbo award, UserAwardDbo grant);

	default String map(OffsetDateTime timestamp) {
		return timestamp == null ? null : timestamp.toString();
	}
}
