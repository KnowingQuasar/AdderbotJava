package com.adderbot.commands.registrars;

import com.adderbot.errors.CommandRegistrarException;
import discord4j.discordjson.json.ApplicationCommandData;
import discord4j.discordjson.json.ApplicationCommandRequest;
import org.springframework.boot.ApplicationArguments;

import java.io.IOException;

public interface ICommandRegistrar {

    void run(ApplicationArguments args) throws IOException, CommandRegistrarException;

    default boolean hasChanged(ApplicationCommandData discordCommand, ApplicationCommandRequest command) throws CommandRegistrarException {
        if(discordCommand == null || command == null) {
            throw new CommandRegistrarException("discordCommand or command was null. Could not register commands.");
        }

        // Compare types
        if (!discordCommand.type().toOptional().orElse(1).equals(command.type().toOptional().orElse(1))) return true;

        //Check if description has changed.
        if (!discordCommand.description().equals(command.description().toOptional().orElse(""))) return true;

        //Check if default permissions have changed
        boolean discordCommandDefaultPermission = discordCommand.defaultPermission().toOptional().orElse(true);
        boolean commandDefaultPermission = command.defaultPermission().toOptional().orElse(true);

        if (discordCommandDefaultPermission != commandDefaultPermission) return true;

        //Check and return if options have changed.
        return !discordCommand.options().equals(command.options());
    }
}
