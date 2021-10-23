package com.adderbot.commands.registrars;

import com.adderbot.errors.CommandRegistrarException;
import discord4j.common.JacksonResources;
import discord4j.discordjson.Id;
import discord4j.discordjson.json.ApplicationCommandData;
import discord4j.discordjson.json.ApplicationCommandRequest;
import discord4j.discordjson.json.UserGuildData;
import discord4j.rest.RestClient;
import discord4j.rest.service.ApplicationService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.core.io.Resource;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

@Component
public class GuildCommandRegistrar implements ApplicationRunner, ICommandRegistrar {
    private final Logger LOGGER = LoggerFactory.getLogger(this.getClass());

    private final RestClient client;

    //Use the rest client provided by our Bean
    @Autowired
    public GuildCommandRegistrar(RestClient client) {
        this.client = client;
    }

    private void validateGuildCommands(Long applicationId, ApplicationService applicationService, UserGuildData guildData)
            throws IOException, CommandRegistrarException {
        PathMatchingResourcePatternResolver matcher = new PathMatchingResourcePatternResolver();
        //Create an ObjectMapper that supported Discord4J classes
        final JacksonResources d4jMapper = JacksonResources.create();

        //These are commands already registered with discord from previous runs of the bot.
        Map<String, ApplicationCommandData> discordCommands = applicationService
                .getGuildApplicationCommands(applicationId, guildData.id().asLong())
                .collectMap(ApplicationCommandData::name)
                .block();

        if (discordCommands == null) {
            throw new CommandRegistrarException(String.format("discordCommands null for guild %s", guildData.name()));
        }

        //Get our commands json from resources as command data
        Map<String, ApplicationCommandRequest> commands = new HashMap<>();
        for (Resource resource : matcher.getResources("commands/guild/*.json")) {
            ApplicationCommandRequest request = d4jMapper.getObjectMapper()
                    .readValue(resource.getInputStream(), ApplicationCommandRequest.class);

            commands.put(request.name(), request);

            //Check if this is a new command that has not already been registered.
            if (!discordCommands.containsKey(request.name())) {
                //Not yet created with discord, lets do it now.
                applicationService.createGuildApplicationCommand(applicationId, guildData.id().asLong(), request).block();

                LOGGER.info(String.format("Created guild command in %s: %s", guildData.name(), request.name()));
            }
        }

        //Check if any  commands have been deleted or changed.
        for (ApplicationCommandData discordCommand : discordCommands.values()) {
            long discordCommandId = Long.parseLong(discordCommand.id());

            ApplicationCommandRequest command = commands.get(discordCommand.name());

            if (command == null) {
                //Removed command.json, delete global command
                applicationService.deleteGuildApplicationCommand(applicationId, guildData.id().asLong(), discordCommandId).block();

                LOGGER.info(String.format("Deleted guild command from %s: %s", guildData.name(), discordCommand.name()));
                continue; //Skip further processing on this command.
            }

            //Check if the command has been changed and needs to be updated.
            if (hasChanged(discordCommand, command)) {
                applicationService.modifyGuildApplicationCommand(applicationId, guildData.id().asLong(), discordCommandId, command).block();

                LOGGER.info(String.format("Updated global command in %s: %s", guildData.name(), discordCommand.name()));
            }
        }
    }

    @Override
    public void run(ApplicationArguments args) throws IOException, CommandRegistrarException {
        // Convenience variables for the sake of easier to read code below.
        final ApplicationService applicationService = client.getApplicationService();
        final Long applicationId = client.getApplicationId().block();
        if (applicationId == null) {
            throw new CommandRegistrarException("Application Id was null. Could not register commands");
        }

        var discordGuilds = client.getGuilds().collectMap(UserGuildData::id).block();

        if (discordGuilds == null || discordGuilds.size() < 1) {
            throw new CommandRegistrarException("Bot is not connected to guilds. Could not register commands");
        }

        for (Map.Entry<Id, UserGuildData> guildMap : discordGuilds.entrySet()) {
            validateGuildCommands(applicationId, applicationService, guildMap.getValue());
        }
    }
}
