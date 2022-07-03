package com.adderbot.commands;

import com.adderbot.commands.common.OptionType;
import com.adderbot.errors.ValidationException;
import com.adderbot.raid.Raid;
import com.adderbot.raid.RaidService;
import com.adderbot.time.TimeService;
import com.adderbot.utils.ErrorUtils;
import discord4j.core.event.domain.interaction.ChatInputInteractionEvent;
import discord4j.core.object.entity.Message;
import lombok.Getter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;

import java.time.Duration;

@Component
@Getter
public class CreateCommand implements SlashCommand {
    private final RaidService raidService;

    @Autowired
    public CreateCommand(RaidService raidService) {
        this.raidService = raidService;
    }

    @Override
    public String getName() {
        return "create";
    }

    @Override
    public Mono<Void> handle(ChatInputInteractionEvent event) {
        try {
            Raid raid = getRaidService().createFromCommand(event.getInteraction().getUser(),
                    event.getInteraction().getGuildId().get(),
                    event.getInteraction().getChannelId().asString(),
                    getOptionValue(event, "difficulty", OptionType.STRING).toString(),
                    getOptionValue(event, "trial", OptionType.STRING).toString(),
                    getOptionValue(event, "date", OptionType.STRING).toString(),
                    getOptionValue(event, "time", OptionType.STRING).toString().toUpperCase(),
                    getOptionValue(event, "timezone", OptionType.STRING).toString(),
                    getOptionValue(event, "roles", OptionType.STRING).toString(),
                    (Integer)getOptionValue(event, "melee_dps", OptionType.INTEGER),
                    (Integer)getOptionValue(event, "ranged_dps", OptionType.INTEGER),
                    (Integer)getOptionValue(event, "flex_dps", OptionType.INTEGER),
                    (Integer)getOptionValue(event, "cro_dps", OptionType.INTEGER));

            event.reply().withEmbeds(getRaidService().buildEmbed(raid)).block(Duration.ofSeconds(2));
            Message message = event.getReply().block(Duration.ofSeconds(2));
            raid.setMessageId(message.getId().asString());

        } catch (ValidationException validationException) {
            return event.reply().withContent(validationException.getMessage());
        }

        Message message = event.getReply().block(Duration.ofSeconds(2));

        if (message == null) {
            event.editReply("Error creating raid: message was null. " + ErrorUtils.devHelpText);
            return Mono.empty();
        }
        return Mono.empty();
    }
}
