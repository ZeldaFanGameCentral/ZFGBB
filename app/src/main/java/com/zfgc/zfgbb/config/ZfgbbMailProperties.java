package com.zfgc.zfgbb.config;

import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties(prefix = "zfgbb.mail")
public record ZfgbbMailProperties(String from) {}
