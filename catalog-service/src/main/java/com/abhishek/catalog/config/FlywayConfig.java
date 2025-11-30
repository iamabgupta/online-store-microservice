package com.abhishek.catalog.config;

import org.flywaydb.core.Flyway;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class FlywayConfig {

    @Bean(initMethod = "migrate")
    public Flyway flyway() {

        try {
            Class.forName("org.postgresql.Driver"); //  forces JDBC driver to load
        } catch (ClassNotFoundException e) {
            throw new RuntimeException("PostgreSQL driver not found!", e);
        }

        return Flyway.configure()
                .dataSource(
                        "jdbc:postgresql://localhost:5432/catalog_db",
                        "catalog_user",
                        "catalog_pass"
                )
                .locations("classpath:db/migration")
                .baselineOnMigrate(true)
                .load();
    }
}