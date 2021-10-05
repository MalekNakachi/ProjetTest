package com.example.gestionAchat;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.autoconfigure.jdbc.DataSourceProperties;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.orm.jpa.EntityManagerFactoryBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
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



@Configuration
@EnableTransactionManagement
@EnableJpaRepositories(basePackages = {"com.example.gestionAchat.repository", "com.example.gestionAchat.repository.pm"}, entityManagerFactoryRef = "externDSEmFactory", transactionManagerRef = "externDSTransactionManager")
public class GestionAchatDBConfig {

//@Primary
    @Bean
    @ConfigurationProperties("spring.datasourceextern")
    public DataSourceProperties externDSProperties() {
        return new DataSourceProperties();
    }

//    @Primary
    @Bean
    public DataSource externDS(@Qualifier("externDSProperties") DataSourceProperties externDSProperties) {
        return externDSProperties.initializeDataSourceBuilder().build();
    }
 //   @Primary
    @Bean
    public LocalContainerEntityManagerFactoryBean externDSEmFactory(@Qualifier("externDS") DataSource externDS, EntityManagerFactoryBuilder builder) {
        Map<String, Object> properties = new HashMap<String, Object>();
        properties.put("hibernate.hbm2ddl.auto", "update");
        properties.put("hibernate.order_inserts", true);
        properties.put("hibernate.order_updates", true);
        return builder.dataSource(externDS).packages("com.example.gestionAchat.domain", "com.example.gestionAchat.entities").persistenceUnit("externDS").properties(properties).build();
    }
  //  @Primary
    @Bean(name = "externDSTransactionManager")
    public PlatformTransactionManager externDSTransactionManager(@Qualifier("externDSEmFactory") EntityManagerFactory externDSEmFactory) {
        return new JpaTransactionManager(externDSEmFactory);
    }
//    @Primary
    @Bean(name = "jdbcDatasourceExternService")
    @Autowired
    public JdbcTemplate createJdbcTemplate_ProfileService(@Qualifier("externDS") DataSource externDS) {
        return new JdbcTemplate(externDS);
    }
}
