package com.zfgc.zfgbb;

import java.util.Arrays;
import java.util.List;

import org.modelmapper.ModelMapper;
import org.modelmapper.config.Configuration.AccessLevel;
import org.modelmapper.convention.MatchingStrategies;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.FilterType;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;

@SpringBootApplication(scanBasePackages = "com.zfgc.zfgbb")
@ComponentScan(basePackages = "com.zfgc.zfgbb",
		excludeFilters = @ComponentScan.Filter(type = FilterType.REGEX, pattern = "com\\.zfgc\\.zfgbb\\.migrator\\..*"))
@MapperScan("com.zfgc.zfgbb.mappers")
@EnableMethodSecurity(prePostEnabled = true)
public class ZfgbbApplication {

	public static void main(String[] args) {
		SpringApplication.run(ZfgbbApplication.class, args);
	}

	@Bean(name = "org.dozer.Mapper")
	public ModelMapper modelMapper() {
		ModelMapper modelMapper = new ModelMapper();
		modelMapper.getConfiguration()
				.setFieldMatchingEnabled(true)
				.setFieldAccessLevel(AccessLevel.PUBLIC)
				.setMatchingStrategy(MatchingStrategies.STRICT);

		return modelMapper;
	}

}
