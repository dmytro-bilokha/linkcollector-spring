package bilokhado.linkcollector.appconfig;

import javax.sql.DataSource;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.dao.annotation.PersistenceExceptionTranslationPostProcessor;

import java.util.Properties;

import javax.persistence.EntityManagerFactory;

import org.springframework.jdbc.datasource.DriverManagerDataSource;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.JpaVendorAdapter;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;

/*
 * A class for JPA configuration.
 */
@Configuration
@EnableTransactionManagement
public class PersistenceJPAConfig {

	/**
	 * Creates and configures entity manager factory.
	 * 
	 * @return entity manager factory bean
	 */
	@Bean
	public LocalContainerEntityManagerFactoryBean entityManagerFactory() {
		LocalContainerEntityManagerFactoryBean em = new LocalContainerEntityManagerFactoryBean();
		em.setDataSource(dataSource());
		em.setPackagesToScan(new String[] { "bilokhado.linkcollector.entity" });
		JpaVendorAdapter vendorAdapter = new HibernateJpaVendorAdapter();
		em.setJpaVendorAdapter(vendorAdapter);
		em.setJpaProperties(additionalProperties());
		return em;
	}

	/**
	 * Configures data source from mysql database.
	 * 
	 * @return data source object
	 */
	@Bean
	public DataSource dataSource() {
		DriverManagerDataSource dataSource = new DriverManagerDataSource();
		dataSource.setDriverClassName("com.mysql.jdbc.Driver");
		dataSource.setUrl("jdbc:mysql://localhost:3306/www");
		dataSource.setUsername("www");
		dataSource.setPassword("world-wide-wlak");
		return dataSource;
	}

	/**
	 * Creates transaction manager.
	 * 
	 * @param emf
	 *            Entity manager factory
	 * @return transaction manager object
	 */
	@Bean
	public PlatformTransactionManager transactionManager(EntityManagerFactory emf) {
		JpaTransactionManager transactionManager = new JpaTransactionManager();
		transactionManager.setEntityManagerFactory(emf);
		return transactionManager;
	}

	/**
	 * Creates exception translation for JPA.
	 * 
	 * @return exception translation post-processor
	 */
	@Bean
	public PersistenceExceptionTranslationPostProcessor exceptionTranslation() {
		return new PersistenceExceptionTranslationPostProcessor();
	}

	/**
	 * Creates properties object and sets JPA properties.
	 * 
	 * @return properties object
	 */
	Properties additionalProperties() {
		Properties properties = new Properties();
		properties.setProperty("javax.persistence.schema-generation.database.action", "drop-and-create");
		properties.setProperty("javax.persistence.schema-generation.create-source", "script");
		properties.setProperty("javax.persistence.schema-generation.drop-source", "script");
		properties.setProperty("javax.persistence.schema-generation.create-script-source", "META-INF/create.sql");
		properties.setProperty("javax.persistence.schema-generation.drop-script-source", "META-INF/drop.sql");
		properties.setProperty("javax.persistence.sql-load-script-source", "META-INF/load.sql");
		properties.setProperty("hibernate.hbm2ddl.auto", "create-drop");
		properties.setProperty("hibernate.dialect", "org.hibernate.dialect.MySQL5Dialect");
		return properties;
	}
}