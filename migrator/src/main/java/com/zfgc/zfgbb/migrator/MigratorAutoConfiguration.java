package com.zfgc.zfgbb.migrator;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import javax.sql.DataSource;

import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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
import org.springframework.core.io.Resource;
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
		@MapperScan(basePackages = "com.zfgc.zfgbb.migrator.ci.mappers", sqlSessionTemplateRef = "smfSqlSessionTemplate"),
		@MapperScan(basePackages = "com.zfgc.zfgbb.migrator.wiki.mappers", sqlSessionTemplateRef = "smfSqlSessionTemplate"),
		@MapperScan(basePackages = "com.zfgc.zfgbb.migrator.mappers")
})
public class MigratorAutoConfiguration {
	private static final Logger LOG = LoggerFactory.getLogger(MigratorAutoConfiguration.class);

	@Bean(destroyMethod = "shutdownNow")
	public ExecutorService migrationJobExecutor() {
		return Executors.newSingleThreadExecutor(runnable -> {
			Thread thread = new Thread(runnable, "migrator-job-runner");
			thread.setDaemon(true);
			return thread;
		});
	}

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
		var combined = new Resource[modelMappers.length + migratorMappers.length];
		System.arraycopy(modelMappers, 0, combined, 0, modelMappers.length);
		System.arraycopy(migratorMappers, 0, combined, modelMappers.length, migratorMappers.length);
		factory.setMapperLocations(combined);
		LOG.warn("The migrator SqlSessionFactory is primary while zfgbb.migrator.enabled=true, so "
				+ "zfgbb.mybatis.statement-timeout-seconds applies to no statement, including ordinary "
				+ "request-path queries. Migration jobs write through the same mappers and legitimately "
				+ "run longer than that timeout. Disable the migrator once migration is complete.");
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
		PathMatchingResourcePatternResolver resolver = new PathMatchingResourcePatternResolver();
		var smf = resolver.getResources("classpath:com/zfgc/zfgbb/migrator/smf/mappers/*.xml");
		var ci = resolver.getResources("classpath:com/zfgc/zfgbb/migrator/ci/mappers/*.xml");
		var wiki = resolver.getResources("classpath:com/zfgc/zfgbb/migrator/wiki/mappers/*.xml");
		var combined = new Resource[smf.length + ci.length + wiki.length];
		System.arraycopy(smf, 0, combined, 0, smf.length);
		System.arraycopy(ci, 0, combined, smf.length, ci.length);
		System.arraycopy(wiki, 0, combined, smf.length + ci.length, wiki.length);
		factory.setMapperLocations(combined);
		return factory.getObject();
	}

	@Bean
	public SqlSessionTemplate smfSqlSessionTemplate(@Qualifier("smfSqlSessionFactory") SqlSessionFactory smfSqlSessionFactory) {
		return new SqlSessionTemplate(smfSqlSessionFactory);
	}
}
