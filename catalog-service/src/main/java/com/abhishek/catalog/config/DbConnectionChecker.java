package com.abhishek.catalog.config;

import jakarta.annotation.PostConstruct;
import org.springframework.stereotype.Component;

import java.sql.Connection;
import java.sql.DriverManager;

@Component
public class DbConnectionChecker {

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
