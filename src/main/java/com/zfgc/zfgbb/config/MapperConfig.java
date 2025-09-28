package com.zfgc.zfgbb.config;

import org.mapstruct.ReportingPolicy;

@MapperConfig(unmappedTargetPolicy = ReportingPolicy.ERROR, unmappedSourcePolicy = ReportingPolicy.ERROR)
public interface MapperConfig {
    // Shared configuration
}
