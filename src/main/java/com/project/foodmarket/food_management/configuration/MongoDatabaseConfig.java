package com.project.foodmarket.food_management.configuration;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.mongodb.MongoDatabaseFactory;
import org.springframework.data.mongodb.core.SimpleMongoClientDatabaseFactory;

import com.mongodb.client.MongoDatabase;

@Configuration
public class MongoDatabaseConfig {
    
    @Value("${spring.mongodb.uri}")
    private String connectionString;

    @Bean
    public MongoDatabaseFactory mongoDatabaseFactory(){        
        return new SimpleMongoClientDatabaseFactory(connectionString){
            @Override
            public MongoDatabase getMongoDatabase(){
                String dbName = MongoContextHolder.getDatabaseName();

                return getMongoDatabase(dbName != null ? dbName : "default");
            }
        };
    }
}
