package com.zfgc.zfgbb.services.search;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;

import com.zfgc.zfgbb.mappers.custom.ContentEntitySearchQueryMapper;

@Configuration
public class ContentEntitySearchRealmConfig {

	@Bean
	@Order(30)
	public SearchRealmProvider projectSearchRealm(
			ContentEntitySearchQueryMapper contentEntitySearchQueryMapper, SearchSnippets searchSnippets) {
		return new ContentEntitySearchRealm("project", "CMS Projects", "PROJECT", "/content/projects/",
				contentEntitySearchQueryMapper, searchSnippets);
	}

	@Bean
	@Order(40)
	public SearchRealmProvider resourceSearchRealm(
			ContentEntitySearchQueryMapper contentEntitySearchQueryMapper, SearchSnippets searchSnippets) {
		return new ContentEntitySearchRealm("resource", "CMS Resources", "RESOURCE", "/content/resources/",
				contentEntitySearchQueryMapper, searchSnippets);
	}
}
