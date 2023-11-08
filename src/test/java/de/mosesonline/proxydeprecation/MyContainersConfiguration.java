package de.mosesonline.proxydeprecation;

import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.boot.testcontainers.service.connection.ServiceConnection;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Primary;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.testcontainers.containers.PostgreSQLContainer;

import javax.sql.DataSource;

@TestConfiguration(proxyBeanMethods = false)
public class MyContainersConfiguration {
    @Bean
    @ServiceConnection
    public PostgreSQLContainer<?> postgresContainer() {
        final var container = new PostgreSQLContainer<>("postgres:16.0-alpine3.18")
                .withInitScript("init_schema.sql")
                .withDatabaseName("mosesonlineinit_schema.sql");
        container.start();
        return container;
    }

    @Bean
    @Primary
    public DataSource dataSource(PostgreSQLContainer<?> postgreSQLContainer) {
        DataSourceBuilder<?> dataSourceBuilder = DataSourceBuilder.create();
        dataSourceBuilder.driverClassName(postgreSQLContainer.getDriverClassName());
        dataSourceBuilder.url(postgreSQLContainer.getJdbcUrl());
        dataSourceBuilder.username(postgreSQLContainer.getUsername());
        dataSourceBuilder.password(postgreSQLContainer.getPassword());
        return dataSourceBuilder.build();
    }

    @DynamicPropertySource
    static void redisProperties(DynamicPropertyRegistry registry) {
        registry.add("spring.liquibase.change-log", () -> "classpath:/db/changelog/db.changelog-master.xml");
        registry.add("spring.liquibase.enabled", () -> "true");
        registry.add("spring.liquibase.default-schema", () -> "mosesonline");
        registry.add("spring.jpa.properties.hibernate.default_schema", () -> "mosesonline");
    }

}
