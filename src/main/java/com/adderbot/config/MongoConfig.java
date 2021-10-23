package com.adderbot.config;

import com.mongodb.ConnectionString;
import com.mongodb.MongoClientSettings;
import com.mongodb.lang.NonNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.mongodb.config.AbstractMongoClientConfiguration;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;
import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;

import java.util.Collection;
import java.util.Collections;

@Configuration
@EnableMongoRepositories(basePackages = "com.adderbot.repositories")
public class MongoConfig extends AbstractMongoClientConfiguration {
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

    @Override
    @NonNull
    public MongoClient mongoClient() {
        ConnectionString connectionString = new ConnectionString(mongoDetails.uri());
        MongoClientSettings mongoClientSettings = MongoClientSettings.builder()
                .applyConnectionString(connectionString)
                .build();

        return MongoClients.create(mongoClientSettings);
    }

    @Override
    @NonNull
    public Collection<String> getMappingBasePackages() {
        return Collections.singleton("com.adderbot");
    }
}
