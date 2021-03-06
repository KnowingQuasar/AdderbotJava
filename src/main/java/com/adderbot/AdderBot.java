package com.adderbot;

import com.adderbot.listeners.SlashCommandListener;
import com.mongodb.reactivestreams.client.MongoClient;
import discord4j.core.DiscordClientBuilder;
import discord4j.core.event.domain.interaction.ChatInputInteractionEvent;
import discord4j.rest.RestClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.context.properties.ConfigurationPropertiesScan;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import reactor.core.publisher.Mono;

@SpringBootApplication
@ConfigurationPropertiesScan
public class AdderBot {
    final MongoClient mongoClient;

    @Autowired
    public AdderBot(MongoClient mongoClient) {
        this.mongoClient = mongoClient;
    }

    public static void main(String[] args) {
        // Start Spring
        ApplicationContext springContext = new SpringApplicationBuilder(AdderBot.class)
                .build()
                .run(args);


        // Login to Discord
        DiscordClientBuilder.create(System.getenv("AdderbotTestToken")).build()
                .withGateway(gatewayClient -> {
                    SlashCommandListener slashCommandListener = new SlashCommandListener(springContext);

                    Mono<Void> onSlashCommandMono = gatewayClient
                            .on(ChatInputInteractionEvent.class, slashCommandListener::handle)
                            .then();

                    return Mono.when(onSlashCommandMono);
                }).block();
    }

    @Bean
    public RestClient discordRestClient() {
        return RestClient.create(System.getenv("AdderbotTestToken"));
    }
}
