package com.adderbot.commands;

import com.adderbot.commands.common.OptionType;
import com.adderbot.errors.ValidationException;
import com.adderbot.raid.Raid;
import com.adderbot.raid.RaidRepository;
import com.adderbot.raid.RaidService;
import com.adderbot.utils.ErrorUtils;
import discord4j.core.event.domain.interaction.ChatInputInteractionEvent;
import discord4j.core.object.entity.Message;
import discord4j.core.object.entity.Role;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.checkerframework.checker.units.qual.A;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;

import java.time.Duration;
import java.util.ArrayList;
import java.util.List;

@Component
@Getter
@RequiredArgsConstructor
public class CreateCommand implements SlashCommand {
    /**
     * Repository used for Raids
     */
    private final RaidRepository raidRepository;

    private final RaidService raidService;

    @Override
    public String getName() {
        return "create";
    }

    @Override
    public Mono<Void> handle(ChatInputInteractionEvent event) {
        try {
            Raid raid = raidRepository.findById(event.getInteraction().getChannelId().asString()).block();

            if (raid != null) {
                return event.reply().withEphemeral(true).withContent("Could not create raid, there is already a raid in the channel");
            }

            String[] allowedRoles = getOptionValue(event, "roles", OptionType.STRING).toString().split(",");

            List<String> roles = new ArrayList<>();

            for (String allowedRole : allowedRoles) {
                Role role = event.getInteraction().getGuild().block().getRoles().filter(roley -> {
                    return roley.getName().equals(allowedRole);
                }).blockFirst();

                if (role == null) {
                    return event.reply().withEphemeral(true).withContent(String.format("Error: role %s could not be found", allowedRole));
                }

                roles.add(role.getMention());
            }

            raid = getRaidService().createFromCommand(event.getInteraction().getUser(),
                    event.getInteraction().getGuildId().get(),
                    event.getInteraction().getChannelId().asString(),
                    getOptionValue(event, "difficulty", OptionType.STRING).toString(),
                    getOptionValue(event, "trial", OptionType.STRING).toString(),
                    getOptionValue(event, "date", OptionType.STRING).toString(),
                    getOptionValue(event, "time", OptionType.STRING).toString().toUpperCase(),
                    getOptionValue(event, "timezone", OptionType.STRING).toString(),
                    roles,
                    (Integer)getOptionValue(event, "melee_dps", OptionType.INTEGER),
                    (Integer)getOptionValue(event, "ranged_dps", OptionType.INTEGER),
                    (Integer)getOptionValue(event, "flex_dps", OptionType.INTEGER),
                    (Integer)getOptionValue(event, "cro_dps", OptionType.INTEGER));

            event.reply().withEmbeds(getRaidService().buildEmbed(raid)).block(Duration.ofSeconds(2));
            Message message = event.getReply().block(Duration.ofSeconds(2));

            if (message == null) {
                event.editReply("Error creating raid: message was null. " + ErrorUtils.devHelpText);
                return Mono.empty();
            }

            raid.setMessageId(message.getId().asString());

            raidRepository.insert(raid).block();
        } catch (ValidationException validationException) {
            return event.reply().withEphemeral(true).withContent(validationException.getMessage());
        }

        return Mono.empty();
    }
}
