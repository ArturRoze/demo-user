package com.example.demouser.config.postgres;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;
import org.testcontainers.containers.PostgreSQLContainer;

import javax.sql.DataSource;

@TestConfiguration
public class PostgresEmbeddedConfig {

    @Value("${spring.datasource.name}")
    public String name;

    @Value("${spring.datasource.username}")
    public String username;

    @Value("${spring.datasource.password}")
    public String password;

    @Bean
    public PostgreSQLContainer<?> postgreSQLContainer() {
        PostgreSQLContainer<?>  postgreSQLContainer = new PostgreSQLContainer<>("postgres:11.1")
                .withDatabaseName(name)
                .withUsername(username)
                .withPassword(password);

        postgreSQLContainer.start();

        return postgreSQLContainer;
    }

    @Bean
    public DataSource dataSource(PostgreSQLContainer<?> postgreSQLContainer) {
        return DataSourceBuilder.create()
                .password(password)
                .username(username)
                .url(postgreSQLContainer.getJdbcUrl())
                .build();
    }

}
