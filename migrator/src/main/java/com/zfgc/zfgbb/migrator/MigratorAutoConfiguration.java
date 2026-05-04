package com.zfgc.zfgbb.migrator;

import javax.sql.DataSource;

import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.mybatis.spring.SqlSessionTemplate;
import org.mybatis.spring.annotation.MapperScan;
import org.mybatis.spring.annotation.MapperScans;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
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
@MapperScans({
		@MapperScan(basePackages = "com.zfgc.zfgbb.migrator.smf.mappers", sqlSessionTemplateRef = "smfSqlSessionTemplate"),
		@MapperScan(basePackages = "com.zfgc.zfgbb.migrator.smf.queries", sqlSessionTemplateRef = "smfSqlSessionTemplate"),
		@MapperScan(basePackages = "com.zfgc.zfgbb.migrator.mappers")
})
public class MigratorAutoConfiguration {

	@Bean
	@Primary
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
		PathMatchingResourcePatternResolver resolver = new PathMatchingResourcePatternResolver();
		var modelMappers = resolver.getResources("classpath:com/zfgc/zfgbb/mappers/*.xml");
		var migratorMappers = resolver.getResources("classpath:com/zfgc/zfgbb/migrator/mappers/*.xml");
		var combined = new org.springframework.core.io.Resource[modelMappers.length + migratorMappers.length];
		System.arraycopy(modelMappers, 0, combined, 0, modelMappers.length);
		System.arraycopy(migratorMappers, 0, combined, modelMappers.length, migratorMappers.length);
		factory.setMapperLocations(combined);
		return factory.getObject();
	}

	@Bean
	@Primary
	public SqlSessionTemplate sqlSessionTemplate(@Qualifier("sqlSessionFactory") SqlSessionFactory sqlSessionFactory) {
		return new SqlSessionTemplate(sqlSessionFactory);
	}

	@Bean
	public DataSource smfDataSource() {
		return new SmfRoutingDataSource();
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
