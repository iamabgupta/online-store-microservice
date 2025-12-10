package com.abhishek.catalog.config;

import jakarta.annotation.PostConstruct;
import org.springframework.stereotype.Component;

import java.sql.Connection;
import java.sql.DriverManager;

/**
 * When configuring postgres + flyway + JPA, following things needs to be configured.
 * 1. Database URL is correct
 * 2. Username/password is correct
 * 3. PostgresSQL server is running
 * 4. Network port (5432) is reachable
 *
 * This class
 * 1. Runs automatically during application setup
 * 2. Tries to connect to PostgresSQL using JDBC
 * 3. Prints whenever connection is successful or failed.
 * 4. If failed, prints actual reason (Wrong password, DB not found, server not running etc.)
 *
 * This class is only for local troubleshooting
 */

//@Component : Spring automatically detects and create a bean
@Component
public class DbConnectionChecker {

    //Due to @PostConstruct methods runs immediately after bean creation
    @PostConstruct
    public void DBConnectionChecker(){
       try (Connection connection = DriverManager.getConnection(
               "jdbc:postgresql://localhost:5432/catalog_db",
               "catalog_user",
               "catalog_pass"
       )){

           System.out.println("Database connection established successfully");
       }catch (Exception e){
            System.err.println("Database connection failed");
            e.printStackTrace();
       }

    }
}
