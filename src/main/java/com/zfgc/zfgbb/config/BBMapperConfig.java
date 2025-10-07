package com.zfgc.zfgbb.config;

import org.mapstruct.MapperConfig;
import org.mapstruct.ReportingPolicy;

@MapperConfig(
	    componentModel = "spring",
	    unmappedTargetPolicy = ReportingPolicy.ERROR,
	    unmappedSourcePolicy = ReportingPolicy.IGNORE
	)
public interface BBMapperConfig {
   
}
