package com.adderbot.commands;

import com.adderbot.raid.Raid;
import com.adderbot.raid.RaidService;
import com.adderbot.raid.RaidValidator;
import discord4j.core.event.domain.interaction.ChatInputInteractionEvent;
import discord4j.core.object.entity.Message;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;

@Component
public class CreateCommand implements SlashCommand {
    private final RaidService raidService;
    private final RaidValidator raidValidator;

    @Autowired
    public CreateCommand(RaidService raidService, RaidValidator raidValidator) {
        this.raidService = raidService;
        this.raidValidator = raidValidator;
    }

    @Override
    public String getName() {
        return "create";
    }

    @Override
    public Mono<Void> handle(ChatInputInteractionEvent event) {
        event.reply().withContent("Creating raid...").block();
        Message message = event.getReply().block();
        if (message == null) {
            event.editReply("Error creating raid. Contact the developer for help.").block();
            return Mono.empty();
        }

        Raid raid = Raid.builder()
                .channelId(event.getInteraction().getChannelId().asString())
                .messageId(message.getId().asString())
                .raidTypeId(getOptionValue(event, "trial").toString())
                .date(getOptionValue(event, "date").toString())
                .time(getOptionValue(event, "time").toString())
                .timezoneId(getOptionValue(event, "timezone").toString())
                .build();

        raidValidator.validate(raid, errors);

        return event.reply()
                .withContent(raid.toString());
    }
}
