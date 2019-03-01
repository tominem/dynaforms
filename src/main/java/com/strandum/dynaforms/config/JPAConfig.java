package com.strandum.dynaforms.config;

import java.util.Properties;

import javax.persistence.EntityManagerFactory;
import javax.sql.DataSource;

import org.hibernate.jpa.HibernatePersistenceProvider;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseBuilder;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseType;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;

import com.strandum.dynaforms.repository.FormElementRepository;

@Configuration
@EnableJpaRepositories(basePackageClasses={FormElementRepository.class})
public class JPAConfig {

	@Bean
	public DataSource dataSource() {
		return new EmbeddedDatabaseBuilder()
	            .generateUniqueName(false)
	            .setName("dynaformdb")
	            .setType(EmbeddedDatabaseType.H2)
	            .setScriptEncoding("UTF-8")
	            .build();
	}
	
	@Bean
	public LocalContainerEntityManagerFactoryBean entityManagerFactory(DataSource dataSource) {
		LocalContainerEntityManagerFactoryBean entityManagerFactory = new LocalContainerEntityManagerFactoryBean();
		entityManagerFactory.setDataSource(dataSource);
		Properties jpaProperties = new Properties();
		jpaProperties.put("hibernate.hbm2ddl.auto", "update");
		jpaProperties.put("hibernate.show_sql", "true");
		jpaProperties.put("hibernate.format_sql", "true");
		entityManagerFactory.setJpaProperties(jpaProperties);
		entityManagerFactory.setPackagesToScan("com.strandum.dynaforms.model");
		entityManagerFactory
				.setPersistenceProvider(new HibernatePersistenceProvider());
		return entityManagerFactory;
	}
	
	@Bean
	public JpaTransactionManager transactionManager(DataSource dataSource,
			EntityManagerFactory entityManagerFactory) {
		JpaTransactionManager transactionManager = new JpaTransactionManager(
				entityManagerFactory);
		transactionManager.setDataSource(dataSource);
		return transactionManager;
	}
	
}
