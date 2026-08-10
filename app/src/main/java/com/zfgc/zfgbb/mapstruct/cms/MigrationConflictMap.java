package com.zfgc.zfgbb.mapstruct.cms;

import java.util.List;

import org.mapstruct.Builder;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import com.zfgc.zfgbb.config.BBMapperConfig;
import com.zfgc.zfgbb.dbo.MigrationConflictDbo;
import com.zfgc.zfgbb.model.cms.ConflictCandidate;
import com.zfgc.zfgbb.model.cms.ConflictView;

@Mapper(config = BBMapperConfig.class, builder = @Builder(disableBuilder = true))
public interface MigrationConflictMap {

	@Mapping(target = "id", source = "row.migrationConflictId")
	@Mapping(target = "detectedTs", source = "row.createdTs")
	@Mapping(target = "entityLabel", source = "entityLabel")
	@Mapping(target = "candidates", source = "candidates")
	ConflictView toView(MigrationConflictDbo row, String entityLabel, List<ConflictCandidate> candidates);
}
