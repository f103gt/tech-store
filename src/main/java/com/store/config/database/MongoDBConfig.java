package com.store.config.database;

import com.mongodb.ConnectionString;
import com.mongodb.MongoClientSettings;
import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import com.mongodb.client.MongoDatabase;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;
import org.bson.Document;
import com.mongodb.client.MongoCollection;

@Configuration
public class MongoDBConfig {
    private final Environment environment;

    @Autowired
    public MongoDBConfig(Environment environment) {
        this.environment = environment;
    }

    @Bean
    public MongoClient mongoClient() {
        ConnectionString connectionString = new ConnectionString("mongodb://" +
                environment.getProperty("mongodb.host") + ":" +
                environment.getProperty("mongodb.port"));
        MongoClientSettings settings = MongoClientSettings.builder()
                .applyConnectionString(connectionString)
                .build();
        return MongoClients.create(settings);
    }

    @Bean
    public MongoDatabase mongoDatabase() {
        return mongoClient().getDatabase(environment.getProperty("mongodb.database"));
    }
}
