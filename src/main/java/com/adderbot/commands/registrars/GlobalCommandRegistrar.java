package com.adderbot.commands.registrars;

import com.adderbot.errors.CommandRegistrarException;
import discord4j.common.JacksonResources;
import discord4j.discordjson.json.ApplicationCommandData;
import discord4j.discordjson.json.ApplicationCommandRequest;
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
public class GlobalCommandRegistrar implements ApplicationRunner, ICommandRegistrar {
    private final Logger LOGGER = LoggerFactory.getLogger(this.getClass());

    private final RestClient client;

    //Use the rest client provided by our Bean
    @Autowired
    public GlobalCommandRegistrar(RestClient client) {
        this.client = client;
    }

    //This method will run only once on each start up and is automatically called with Spring so blocking is okay.
    @Override
    public void run(ApplicationArguments args) throws IOException, CommandRegistrarException {
        //Create an ObjectMapper that supported Discord4J classes
        final JacksonResources d4jMapper = JacksonResources.create();

        // Convenience variables for the sake of easier to read code below.
        PathMatchingResourcePatternResolver matcher = new PathMatchingResourcePatternResolver();
        final ApplicationService applicationService = client.getApplicationService();
        final Long applicationId = client.getApplicationId().block();
        if (applicationId == null) {
            throw new CommandRegistrarException("Application Id was null. Could not register commands");
        }

        //These are commands already registered with discord from previous runs of the bot.
        Map<String, ApplicationCommandData> discordCommands = applicationService
                .getGlobalApplicationCommands(applicationId)
                .collectMap(ApplicationCommandData::name)
                .block();

        //Get our commands json from resources as command data
        Map<String, ApplicationCommandRequest> commands = new HashMap<>();
        for (Resource resource : matcher.getResources("commands/global/*.json")) {
            ApplicationCommandRequest request = d4jMapper.getObjectMapper()
                    .readValue(resource.getInputStream(), ApplicationCommandRequest.class);

            commands.put(request.name(), request);

            //Check if this is a new command that has not already been registered.
            assert discordCommands != null;
            if (!discordCommands.containsKey(request.name())) {
                //Not yet created with discord, lets do it now.
                applicationService.createGlobalApplicationCommand(applicationId, request).block();

                LOGGER.info("Created global command: " + request.name());
            }
        }

        //Check if any  commands have been deleted or changed.
        assert discordCommands != null;
        for (ApplicationCommandData discordCommand : discordCommands.values()) {
            long discordCommandId = Long.parseLong(discordCommand.id());

            ApplicationCommandRequest command = commands.get(discordCommand.name());

            if (command == null) {
                //Removed command.json, delete global command
                applicationService.deleteGlobalApplicationCommand(applicationId, discordCommandId).block();

                LOGGER.info("Deleted global command: " + discordCommand.name());
                continue; //Skip further processing on this command.
            }

            //Check if the command has been changed and needs to be updated.
            if (hasChanged(discordCommand, command)) {
                applicationService.modifyGlobalApplicationCommand(applicationId, discordCommandId, command).block();

                LOGGER.info("Updated global command: " + command.name());
            }
        }
    }
}