package com.adderbot.config;

import com.mongodb.ConnectionString;
import com.mongodb.MongoClientSettings;
import com.mongodb.client.MongoClient;
import com.mongodb.lang.NonNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.mongodb.config.AbstractReactiveMongoConfiguration;
import com.mongodb.client.MongoClients;
import org.springframework.data.mongodb.repository.config.EnableReactiveMongoRepositories;

import java.util.Collection;
import java.util.Collections;

@Configuration
@EnableReactiveMongoRepositories
public class MongoConfig extends AbstractReactiveMongoConfiguration {
    private final MongoDetails mongoDetails;

    @Autowired
    public MongoConfig(MongoDetails mongoDetails) {
        this.mongoDetails = mongoDetails;
    }

    @Override
    @NonNull
    protected String getDatabaseName() {
        return mongoDetails.database();
    }

    @Bean
    public MongoClient mongoClient() {
        ConnectionString connectionString = new ConnectionString(mongoDetails.uri());
        MongoClientSettings mongoClientSettings = MongoClientSettings.builder()
                .applyConnectionString(connectionString)
                .build();

        return MongoClients.create(mongoClientSettings);
    }
}
