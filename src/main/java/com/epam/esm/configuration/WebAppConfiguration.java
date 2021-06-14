package com.epam.esm.configuration;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateTimeSerializer;
import liquibase.integration.spring.SpringLiquibase;
import org.apache.commons.dbcp2.BasicDataSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.json.Jackson2ObjectMapperBuilder;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import javax.sql.DataSource;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Objects;

@Configuration
@ComponentScan("com.epam.esm")
@PropertySource("${path.configure:classpath:database.properties}")
@EnableTransactionManagement
@EnableWebMvc
public class WebAppConfiguration implements WebMvcConfigurer {
    private static final String DB_URL = "db.url";
    private static final String DB_USERNAME = "db.username";
    private static final String DB_PASSWORD = "db.password";
    private static final String DB_DRIVER = "db.driver";
    private static final String POOL_INITIAL_SIZE = "pool.initialSize";
    private static final String POOL_MAX_TOTAL = "pool.maxTotal";

    @Value("${path.changelog:classpath:changeLog.xml}")
    private String changeLog;

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

    @Bean
    public SpringLiquibase liquibase() {
        SpringLiquibase springLiquibase = new SpringLiquibase();
        springLiquibase.setChangeLog(changeLog);
        springLiquibase.setDataSource(dataSource());
        return springLiquibase;
    }

    @Override
    public void configureMessageConverters(List<HttpMessageConverter<?>> converters) {
        Jackson2ObjectMapperBuilder builder = new Jackson2ObjectMapperBuilder()
                .serializers(new LocalDateTimeSerializer(DateTimeFormatter.ISO_DATE_TIME))
                .serializationInclusion(JsonInclude.Include.NON_NULL)
                .serializationInclusion(JsonInclude.Include.NON_EMPTY);
        converters.add(new MappingJackson2HttpMessageConverter(builder.build()));
    }
}
