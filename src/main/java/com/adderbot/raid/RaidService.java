package com.adderbot.raid;

import com.adderbot.errors.ValidationException;
import com.adderbot.raid.role.RaidRoleType;
import com.adderbot.raid.role.RaidRoleTypeService;
import com.adderbot.raid.type.RaidType;
import com.adderbot.raid.type.RaidTypeRepository;
import com.adderbot.raid.type.RaidTypeService;
import com.adderbot.time.TimeService;
import com.adderbot.utils.ErrorUtils;
import com.adderbot.utils.ValidationUtils;
import discord4j.common.util.Snowflake;
import discord4j.core.object.entity.User;
import discord4j.core.spec.EmbedCreateSpec;
import discord4j.rest.util.Color;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.data.util.Pair;
import org.springframework.stereotype.Service;

import java.time.ZonedDateTime;
import java.util.*;

@Service
@RequiredArgsConstructor
public class RaidService {

    /**
     * TimeService used to parse timezones and times
     */
    private final TimeService timeService;

    /**
     * RaidTypeService used to get raid type information
     */
    private final RaidTypeService raidTypeService;

    /**
     * RaidRoleTypeService to get the metadata about raid roles
     */
    private final RaidRoleTypeService raidRoleTypeService;

    public String generateRaidName(Raid raid, RaidType raidType) {
        return raid.getDifficulty().toLowerCase(Locale.ROOT) + raidType.getId()
                + (raidType.getId().equals("HM") ? " HM" : "");
    }

    public Raid createFromCommand(User user, Snowflake guildId, String channelId, String difficulty, String trial, String date,
                                  String time, String timezone, List<String> roles, Integer numberMelee, Integer numberRanged,
                                  Integer numberFlex, Integer numberCros) throws ValidationException {

        ZonedDateTime timestamp = timeService.createTimestamp(date, time, timezone);

        if (guildId == null) {
            throw new ValidationException(ErrorUtils.createError + " This command is only available within a Discord server.");
        }

        if (timestamp == null) {
            throw new ValidationException(ErrorUtils.createError + " Please make sure that the date is in MM/dd/YYYY and time is in HH:SSam/pm formats.");
        }
        if (ValidationUtils.isNullOrEmpty(channelId)) {
            throw new ValidationException(ErrorUtils.createError + " Id for raid was null. " + ErrorUtils.devHelpText);
        }
        if (ValidationUtils.isNullOrEmpty(difficulty)) {
            throw new ValidationException(ErrorUtils.createError + " Difficulty for raid was null. " + ErrorUtils.devHelpText);
        }

        RaidType raidType = raidTypeService.getRaidTypeById(trial);

        if (ValidationUtils.isNullOrEmpty(raidType.getId())) {
            throw new ValidationException(ErrorUtils.createError + " Raid Type for raid was null. " + ErrorUtils.devHelpText);
        }

        HashMap<String, RosterRole> roster = new HashMap<>();

        try {
            if (difficulty.equalsIgnoreCase("HM")) {
                raidType.getHm().getRaid_roles().forEach((String role, Integer numAvailable) -> roster.put(role, new RosterRole(numAvailable)));
            } else {
                raidType.getNon_hm().getRaid_roles().forEach((String role, Integer numAvailable) -> roster.put(role, new RosterRole(numAvailable)));
            }
        } catch (NullPointerException npe) {
            throw new ValidationException(ErrorUtils.createError + " Invalid combination of raid type and difficulty. Please check what trial and difficulty you have selected.");
        }

        return Raid.builder()
                .leadId(user.getId().asString())
                .guildId(guildId.asString())
                .difficulty(difficulty)
                .availableRoles(new HashMap<>())
                .timestampInSeconds(timestamp.toEpochSecond())
                .id(channelId)
                .roster(roster)
                .raidTypeId(raidType.getId())
                .build();
    }

    public EmbedCreateSpec buildEmbed(Raid raid) {

        RaidType raidType = raidTypeService.getRaidTypeById(raid.getRaidTypeId());

        StringBuilder raidStringBuilder = new StringBuilder();
        raidStringBuilder.append("**Trial:** ")
                .append(generateRaidName(raid, raidType))
                .append("\n**When (adjusted to your timezone):** <t:")
                .append(raid.getTimestampInSeconds())
                .append(":F>\n**Leader:** ")
                .append(String.format("<@!%s>", raid.getLeadId()))
                .append("\n")
                .append("**-----------------------------------------------**\n");

        PriorityQueue<Pair<String, Integer>> roleStringQueue = new PriorityQueue<>(Comparator.comparing(Pair::getSecond));

        Map<String, RaidRoleType> raidRoleTypes = raidRoleTypeService.getAllRaidRolesMapById();

        raid.getRoster().forEach((role, rosterRole) -> {
            RaidRoleType raidRoleType = raidRoleTypes.get(role);
            Integer displayPriority = raidRoleType.getDisplayPriority();

            for (String player : rosterRole.getPlayers()) {
                roleStringQueue.add(Pair.of(String.format("%s **%s:** <@!%s>", raidRoleType.getDisplayEmoji(), raidRoleType.getDisplayName(), player), displayPriority));
            }

            for (int i = 0; i < rosterRole.getNumberAvailable(); i++) {
                roleStringQueue.add(Pair.of(String.format("%s **%s:**", raidRoleType.getDisplayEmoji(), raidRoleType.getDisplayName()), displayPriority));
            }
        });

        while (!roleStringQueue.isEmpty()) {
            raidStringBuilder.append(roleStringQueue.poll().getFirst()).append("\n");
        }

        return EmbedCreateSpec.builder()
                .title(raidType.getName())
                .color(Color.of(Integer.decode(raidType.getEmbedColor())))
                .description(raidStringBuilder.toString())
                .build();
    }
}
