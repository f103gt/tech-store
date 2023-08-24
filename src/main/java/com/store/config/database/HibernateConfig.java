package com.store.config.database;

import com.store.config.security.model.Role;
import com.store.config.security.model.User;
import org.apache.tomcat.dbcp.dbcp2.BasicDataSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.*;
import org.springframework.core.env.Environment;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.orm.hibernate5.LocalSessionFactoryBean;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import javax.sql.DataSource;
import java.util.Properties;

@Configuration
@EnableTransactionManagement
@ComponentScan(basePackages="com.store.dao")
@PropertySource("classpath:application.properties")
public class HibernateConfig {
    private final Environment environment;

    @Autowired
    public HibernateConfig(Environment environment) {
        this.environment = environment;
    }

    @Bean
    public LocalSessionFactoryBean sessionFactory() {
        LocalSessionFactoryBean sessionFactoryBean = new LocalSessionFactoryBean();
        sessionFactoryBean.setDataSource(dataSource());
        sessionFactoryBean.setPackagesToScan("com.store.dao");
        sessionFactoryBean.setAnnotatedClasses(User.class, Role.class);
        sessionFactoryBean.setHibernateProperties(hibernateProperties());
        return sessionFactoryBean;
    }

    @Bean
    public DataSource dataSource() {
        BasicDataSource dataSource = new BasicDataSource();
        dataSource.setDriverClassName(environment.getProperty("spring.datasource.driver-class-name"));
        dataSource.setUrl(environment.getProperty("spring.datasource.url"));
        dataSource.setUsername(environment.getProperty("spring.datasource.username"));
        dataSource.setPassword(environment.getProperty("spring.datasource.password"));
        return dataSource;
    }

    @Bean
    public JdbcTemplate jdbcTemplate(){
        return new JdbcTemplate(dataSource());
    }

    private Properties hibernateProperties() {
        Properties properties = new Properties();
        properties.put("logging.level.org.hibernate.SQL", environment.getRequiredProperty("logging.level.org.hibernate.SQL"));
        properties.put("spring.jpa.hibernate.dialect", environment.getRequiredProperty("spring.jpa.hibernate.dialect"));
        properties.put("spring.jpa.hibernate.hbm2ddl.auto",environment.getRequiredProperty("spring.jpa.hibernate.hbm2ddl.auto"));
        properties.put("spring.jpa.hibernate.show-sql", environment.getRequiredProperty("spring.jpa.hibernate.show-sql"));
        properties.put("spring.jpa.hibernate.format-sql", environment.getRequiredProperty("spring.jpa.hibernate.format-sql"));
        properties.put("spring.jpa.properties.hibernate.jdbc.batch_size",environment.getRequiredProperty("spring.jpa.properties.hibernate.jdbc.batch_size"));
        properties.put("spring.jpa.properties.hibernate.order_updates",environment.getRequiredProperty("spring.jpa.properties.hibernate.order_updates"));
        properties.put("spring.jpa.properties.hibernate.order_inserts",environment.getRequiredProperty("spring.jpa.properties.hibernate.order_inserts"));
        return properties;
    }
}
