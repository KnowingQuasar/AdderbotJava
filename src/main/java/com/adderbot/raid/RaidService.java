package com.adderbot.raid;

import com.adderbot.errors.ValidationException;
import com.adderbot.raid.type.RaidType;
import com.adderbot.raid.type.RaidTypeService;
import com.adderbot.time.TimeService;
import com.adderbot.utils.ErrorUtils;
import com.adderbot.utils.ValidationUtils;
import discord4j.core.object.entity.User;
import discord4j.core.spec.EmbedCreateSpec;
import lombok.Getter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.ZonedDateTime;
import java.util.Locale;

@Service
@Getter
public class RaidService {
    /**
     * Repository used for Raids
     */
    private final RaidRepository raidRepository;

    /**
     * TimeService used to parse timezones and times
     */
    private final TimeService timeService;

    /**
     * RaidTypeService used to get raid type information
     */
    private final RaidTypeService raidTypeService;

    /**
     * Autowired constructor wiring in the RaidRepository
     * @param raidRepository Autowired RaidRespository
     */
    @Autowired
    public RaidService(RaidRepository raidRepository, TimeService timeService, RaidTypeService raidTypeService) {
        this.raidRepository = raidRepository;
        this.timeService = timeService;
        this.raidTypeService = raidTypeService;
    }

    public String generateRaidName(Raid raid) {
        return raid.getDifficulty().toLowerCase(Locale.ROOT) + raid.getRaidType().getId()
                + (raid.getRaidType().getId().equals("HM") ? " HM" : "");
    }

    public Raid createFromCommand(User user, String channelId, String difficulty, String trial, String date, String time, String timezone, String roles,
                                  Integer numberMelee, Integer numberRanged, Integer numberFlex, Integer numberCros) throws ValidationException {
        ZonedDateTime timestamp = getTimeService().createTimestamp(date, time, timezone);

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

        return new Raid(user, channelId, difficulty, raidType, timestamp);
    }

    public EmbedCreateSpec buildEmbed(Raid raid) {
        return EmbedCreateSpec.builder()
                .title(raid.getRaidType().getName())
                .description(String.format("**Trial:** %s\n", generateRaidName(raid))
                        .concat(String.format("%s\n", getTimeService().buildDateString(raid.getZonedDateTime())))
                        .concat(String.format("%s\n", getTimeService().buildTimeString(raid.getZonedDateTime())))
                        .concat(String.format("%s\n", raid.getLead().getMention()))
                        .concat("**-----------------------------------------------**\n")
                        .concat("**MT:**\n")
                        .concat("**OT:**\n")
                        .concat("**H1:**\n")
                        .concat("**H2:**\n")
                        .concat("**DPS:**\n")
                        .concat("**DPS:**\n")
                        .concat("**DPS:**\n")
                        .concat("**DPS:**\n")
                        .concat("**DPS:**\n")
                        .concat("**DPS:**\n")
                        .concat("**DPS:**\n")
                        .concat("**DPS:**\n"))
                .build();
    }
}
