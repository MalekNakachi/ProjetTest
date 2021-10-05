package com.example.gestionAchat;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.autoconfigure.jdbc.DataSourceProperties;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.orm.jpa.EntityManagerFactoryBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import javax.persistence.EntityManagerFactory;
import javax.sql.DataSource;
import java.util.HashMap;
import java.util.Map;

//https://stackoverflow.com/questions/47094268/spring-data-multiple-data-source-configuration-run-but-insert-in-wrong-datasourc
@Configuration
@EnableTransactionManagement

@EnableJpaRepositories(basePackages = "org.flowable.engine.repository", entityManagerFactoryRef = "internDSEmFactory", transactionManagerRef = "internDSTransactionManager")
public class flowableDBConfig {
    @Primary
    @Bean
    @ConfigurationProperties("spring.datasourceintern")
    public DataSourceProperties internDSProperties() {
        return new DataSourceProperties();
    }

   @Primary
    @Bean
    public DataSource internDS(@Qualifier("internDSProperties") DataSourceProperties internDSProperties) {
        return internDSProperties.initializeDataSourceBuilder().build();
    }

  @Primary
    @Bean
    public LocalContainerEntityManagerFactoryBean internDSEmFactory(@Qualifier("internDS") DataSource internDS, EntityManagerFactoryBuilder builder) {
        Map<String, Object> properties = new HashMap<String, Object>();
        properties.put("hibernate.hbm2ddl.auto", "update");
        properties.put("hibernate.order_inserts", true);
        properties.put("hibernate.order_updates", true);
        return builder.dataSource(internDS).packages("org.flowable.engine").persistenceUnit("internDS").properties(properties).build();
    }

   @Primary
    @Bean(name = "internDSTransactionManager")
    public PlatformTransactionManager internDSTransactionManager(@Qualifier("internDSEmFactory") EntityManagerFactory internDSEmFactory) {
        return new JpaTransactionManager(internDSEmFactory);
    }

    @Primary
    @Bean(name = "jdbcDatasourceInternService")
    @Autowired
    public JdbcTemplate createJdbcTemplate_ProfileService(@Qualifier("internDS") DataSource internDS) {
        return new JdbcTemplate(internDS);
    }


}
