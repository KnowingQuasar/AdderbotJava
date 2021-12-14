package com.adderbot.raid;

import com.adderbot.raid.type.RaidType;
import discord4j.core.object.entity.User;
import lombok.Data;

import java.time.ZonedDateTime;

@Data
public class Raid {
    /**
     * Id of the raid lead
     */
    private User lead;
    /**
     * Id of the channel that the raid is housed in
     */
    private String channelId;
    /**
     * Id of the message that the raid is contained in
     */
    private String messageId;
    /**
     * The difficulty of the raid (V, N, HM)
     */
    private String difficulty;
    /**
     * The raid type of the raid
     */
    private RaidType raidType;
    /**
     * The DateTime object for the raid
     */
    private ZonedDateTime zonedDateTime;

    public Raid(User lead, String channelId, String difficulty, RaidType raidType, ZonedDateTime zonedDateTime) {
        this.lead = lead;
        this.channelId = channelId;
        this.difficulty = difficulty;
        this.raidType = raidType;
        this.zonedDateTime = zonedDateTime;
    }
}
