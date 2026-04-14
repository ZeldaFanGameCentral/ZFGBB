package com.zfgc.zfgbb.config;

import org.mapstruct.MapperConfig;
import org.mapstruct.Mapping;
import org.mapstruct.ReportingPolicy;

import com.zfgc.zfgbb.dbo.AbstractDbo;
import com.zfgc.zfgbb.model.BaseModel;

@MapperConfig(
	    componentModel = "spring",
	    unmappedTargetPolicy = ReportingPolicy.ERROR,
	    unmappedSourcePolicy = ReportingPolicy.IGNORE
	    
	)
public interface BBMapperConfig {
}
