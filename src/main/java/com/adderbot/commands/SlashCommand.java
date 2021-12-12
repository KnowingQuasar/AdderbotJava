package com.adderbot.commands;

import com.adderbot.commands.common.OptionType;
import com.adderbot.errors.Error;
import discord4j.core.event.domain.interaction.ChatInputInteractionEvent;
import discord4j.core.object.command.ApplicationCommandInteractionOption;
import discord4j.core.object.command.ApplicationCommandInteractionOptionValue;
import lombok.NonNull;
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

    default Object getOptionValue(@NonNull ChatInputInteractionEvent event, @NonNull String optionName, @NonNull OptionType optionType) {
        Optional<ApplicationCommandInteractionOption> optionOptional = event.getOption(optionName);
        ApplicationCommandInteractionOption option = optionOptional.orElse(null);
        if (option == null) {
            return null;
        } else {
            ApplicationCommandInteractionOptionValue optionValue = option.getValue().orElse(null);
            if (optionValue == null) {
                return null;
            } else {
                switch (optionType) {
                    case STRING -> {
                        return optionValue.asString();
                    }
                    case INTEGER -> {
                        return optionValue.asLong();
                    }
                    case BOOLEAN -> {
                        return optionValue.asBoolean();
                    }
                    case USER -> {
                        return optionValue.asUser();
                    }
                    case CHANNEL -> {
                        return optionValue.asChannel();
                    }
                    case ROLE -> {
                        return optionValue.asRole();
                    }
                    case MENTIONABLE -> {
                        return optionValue.asSnowflake();
                    }
                    case NUMBER -> {
                        return optionValue.asDouble();
                    }
                    default -> {
                        throw new UnsupportedOperationException("Subcommand and subcommand groups are not supported for getOptionValue");
                    }
                }
            }
        }
    }
}
