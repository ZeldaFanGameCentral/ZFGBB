package com.zfgc.zfgbb.migrator;

import javax.sql.DataSource;

import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.mybatis.spring.SqlSessionTemplate;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.boot.jdbc.autoconfigure.DataSourceProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;

@Configuration
@ConditionalOnProperty(prefix = "zfgbb.migrator", name = "enabled", havingValue = "true")
@ComponentScan(basePackages = {
		"com.zfgc.zfgbb.migrator.converters",
		"com.zfgc.zfgbb.migrator.jobs",
		"com.zfgc.zfgbb.migrator.web"})
@MapperScan(basePackages = "com.zfgc.zfgbb.migrator.smf.mappers", sqlSessionTemplateRef = "smfSqlSessionTemplate")
public class MigratorAutoConfiguration {

	@Bean
	@Primary
	@ConfigurationProperties(prefix = "spring.datasource")
	public DataSourceProperties dataSourceProperties() {
		return new DataSourceProperties();
	}

	@Bean
	@Primary
	public DataSource dataSource(DataSourceProperties props) {
		return props.initializeDataSourceBuilder().build();
	}

	@Bean
	@Primary
	public SqlSessionFactory sqlSessionFactory(@Qualifier("dataSource") DataSource dataSource) throws Exception {
		SqlSessionFactoryBean factory = new SqlSessionFactoryBean();
		factory.setDataSource(dataSource);
		factory.setMapperLocations(new PathMatchingResourcePatternResolver()
				.getResources("classpath:com/zfgc/zfgbb/mappers/*.xml"));
		return factory.getObject();
	}

	@Bean
	@Primary
	public SqlSessionTemplate sqlSessionTemplate(@Qualifier("sqlSessionFactory") SqlSessionFactory sqlSessionFactory) {
		return new SqlSessionTemplate(sqlSessionFactory);
	}

	@Bean
	@ConfigurationProperties(prefix = "zfgbb.migrator.smf.datasource")
	public DataSource smfDataSource() {
		return DataSourceBuilder.create().build();
	}

	@Bean
	public SqlSessionFactory smfSqlSessionFactory(@Qualifier("smfDataSource") DataSource smfDataSource) throws Exception {
		SqlSessionFactoryBean factory = new SqlSessionFactoryBean();
		factory.setDataSource(smfDataSource);
		factory.setMapperLocations(new PathMatchingResourcePatternResolver()
				.getResources("classpath:com/zfgc/zfgbb/migrator/smf/mappers/*.xml"));
		return factory.getObject();
	}

	@Bean
	public SqlSessionTemplate smfSqlSessionTemplate(@Qualifier("smfSqlSessionFactory") SqlSessionFactory smfSqlSessionFactory) {
		return new SqlSessionTemplate(smfSqlSessionFactory);
	}
}
