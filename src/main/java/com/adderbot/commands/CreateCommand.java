package com.adderbot.commands;

import com.adderbot.commands.common.OptionType;
import com.adderbot.raid.RaidDto;
import com.adderbot.raid.RaidService;
import com.adderbot.raid.time.TimeService;
import com.adderbot.utils.ErrorUtils;
import discord4j.core.event.domain.interaction.ChatInputInteractionEvent;
import discord4j.core.object.entity.Message;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;

import java.time.Duration;

@Component
public class CreateCommand implements SlashCommand {
    private final RaidService raidService;
    private final TimeService timeService;

    @Autowired
    public CreateCommand(RaidService raidService, TimeService timeService) {
        this.raidService = raidService;
        this.timeService = timeService;
    }

    @Override
    public String getName() {
        return "create";
    }

    @Override
    public Mono<Void> handle(ChatInputInteractionEvent event) {
        Long timestamp = timeService.createTimestamp(getOptionValue(event, "date", OptionType.STRING).toString(),
                getOptionValue(event, "time", OptionType.STRING).toString().toUpperCase(),
                getOptionValue(event, "timezone", OptionType.STRING).toString());

        RaidDto raid = RaidDto.builder()
                .id(event.getInteraction().getChannelId().asString())
                .difficulty(getOptionValue(event, "difficulty", OptionType.STRING).toString())
                .raidTypeId(getOptionValue(event, "trial", OptionType.STRING).toString())
                .timestampInSeconds(timestamp)
                .build();

        if (errors.isEmpty()) {
            event.reply().withContent("Success").block(Duration.ofSeconds(2));
            Message message = event.getReply().block(Duration.ofSeconds(2));

            if (message == null) {
                event.editReply("Error creating raid: message was null. " + ErrorUtils.devHelpText);
                return Mono.empty();
            }

            raid.setMessageId(message.getId().asString());

            if (errors.isEmpty()) {
                raidService.toString();
            } else {
                event.editReply(ErrorUtils.generateErrorMessages(errors));
            }
        } else {
            return event.reply().withContent("Fail");
        }
        return Mono.empty();
    }
}
