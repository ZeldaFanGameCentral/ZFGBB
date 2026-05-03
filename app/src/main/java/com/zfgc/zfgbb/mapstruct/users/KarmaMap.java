package com.zfgc.zfgbb.mapstruct.users;

import java.util.List;

import org.mapstruct.Mapper;

import com.zfgc.zfgbb.config.BBMapperConfig;
import com.zfgc.zfgbb.dbo.UserKarmaViewDbo;
import com.zfgc.zfgbb.model.users.UserKarmaView;
import org.mapstruct.Mapping;

@Mapper(config=BBMapperConfig.class)
public interface KarmaMap {

	@Mapping(target = "createdTs", ignore = true)
	@Mapping(target = "updatedTs", ignore = true)
	UserKarmaView toViewModel(UserKarmaViewDbo dbo);
	
	List<UserKarmaView> toViewModelList(List<UserKarmaViewDbo> dbo);
	
}
