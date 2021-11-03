package com.adderbot.commands;

import com.adderbot.errors.Error;
import discord4j.core.event.domain.interaction.ChatInputInteractionEvent;
import discord4j.core.object.command.ApplicationCommandInteractionOption;
import reactor.core.publisher.Mono;

import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

/**
 * A simple interface defining our slash command class contract.
 *  a getName() method to provide the case-sensitive name of the command.
 *  and a handle() method which will house all the logic for processing each command.
 */
public interface SlashCommand {
    Set<Error> errors = new HashSet<>();

    String getName();

    Mono<Void> handle(ChatInputInteractionEvent event);

    default Object getOptionValue(ChatInputInteractionEvent event, String optionName) {
        Optional<ApplicationCommandInteractionOption> optionOptional = event.getOption("trial");
        return optionOptional.orElse(null);
    }
}
