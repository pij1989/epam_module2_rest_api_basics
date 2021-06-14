package com.epam.esm.configuration;

import com.epam.esm.model.dao.GiftCertificateDao;
import com.epam.esm.model.dao.TagDao;
import liquibase.integration.spring.SpringLiquibase;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.*;
import org.springframework.core.env.Environment;
import org.springframework.jdbc.datasource.DriverManagerDataSource;

import javax.sql.DataSource;
import java.util.Objects;

@Configuration
@ComponentScan("com.epam.esm.model")
@PropertySource("classpath:database_test.properties")
public class TestConfiguration {
    private static final String DB_URL = "db.url";
    private static final String DB_USERNAME = "db.username";
    private static final String DB_PASSWORD = "db.password";
    private static final String DB_DRIVER = "db.driver";

    @Autowired
    private Environment env;

    @Bean
    @Profile("unit")
    public DataSource mockDataSource() {
        return Mockito.mock(DataSource.class);
    }

    @Bean
    @Profile("integration")
    public DataSource dataSource() {
        DriverManagerDataSource driverManagerDataSource = new DriverManagerDataSource();
        driverManagerDataSource.setDriverClassName(Objects.requireNonNull(env.getProperty(DB_DRIVER)));
        driverManagerDataSource.setUrl(env.getProperty(DB_URL));
        driverManagerDataSource.setUsername(env.getProperty(DB_USERNAME));
        driverManagerDataSource.setPassword(env.getProperty(DB_PASSWORD));
        return driverManagerDataSource;
    }

    @Bean
    @Profile("integration")
    public SpringLiquibase liquibase() {
        SpringLiquibase springLiquibase = new SpringLiquibase();
        springLiquibase.setChangeLog("classpath:testChangeLog.xml");
        springLiquibase.setDataSource(dataSource());
        return springLiquibase;
    }

    @Bean
    @Profile("unit")
    public TagDao tagDao() {
        return Mockito.mock(TagDao.class);
    }

    @Bean
    @Profile("unit")
    public GiftCertificateDao giftCertificateDao(){
        return Mockito.mock(GiftCertificateDao.class);
    }
}
