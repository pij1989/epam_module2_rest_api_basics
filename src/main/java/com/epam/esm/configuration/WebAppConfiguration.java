package com.epam.esm.configuration;

import org.apache.commons.dbcp2.BasicDataSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

import javax.sql.DataSource;
import java.util.Objects;

@Configuration
@ComponentScan("com.epam.esm")
@PropertySource("classpath:database.properties")
@EnableWebMvc
public class WebAppConfiguration {
    private static final String DB_URL = "db.url";
    private static final String DB_USERNAME = "db.username";
    private static final String DB_PASSWORD = "db.password";
    private static final String DB_DRIVER = "db.driver";
    private static final String POOL_INITIAL_SIZE = "pool.initialSize";
    private static final String POOL_MAX_TOTAL = "pool.maxTotal";

    private final Environment env;

    @Autowired
    public WebAppConfiguration(Environment env) {
        this.env = env;
    }

    @Bean
    public BasicDataSource dataSource() {
        BasicDataSource basicDataSource = new BasicDataSource();
        basicDataSource.setUrl(env.getProperty(DB_URL));
        basicDataSource.setUsername(env.getProperty(DB_USERNAME));
        basicDataSource.setPassword(env.getProperty(DB_PASSWORD));
        basicDataSource.setDriverClassName(env.getProperty(DB_DRIVER));
        basicDataSource.setInitialSize(Integer.parseInt(Objects.requireNonNull(env.getProperty(POOL_INITIAL_SIZE))));
        basicDataSource.setMaxTotal(Integer.parseInt(Objects.requireNonNull(env.getProperty(POOL_MAX_TOTAL))));
        return basicDataSource;
    }

    @Bean
    public PlatformTransactionManager txManager(@Autowired DataSource dataSource) {
        return new DataSourceTransactionManager(dataSource);
    }
}
