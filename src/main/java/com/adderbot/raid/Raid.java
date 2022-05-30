package com.adderbot.raid;

import com.adderbot.raid.type.RaidType;
import discord4j.core.object.entity.User;
import lombok.Builder;
import lombok.Data;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;
import org.springframework.data.mongodb.core.mapping.MongoId;

import java.time.ZonedDateTime;

@Document("raid")
@Data
@Builder
public class Raid {

    /**
     * Id of the channel that the raid is housed in, doubles as raid id
     */
    @MongoId
    private String id;

    /**
     * Id of the raid lead
     */
    @Field(value = "lead_id")
    private User lead;

    /**
     * Id of the message that the raid is contained in
     */
    @Field(name = "message_id")
    private String messageId;

    /**
     * The difficulty of the raid (V, N, HM)
     */
    @Field(value = "difficulty")
    private String difficulty;

    /**
     * The raid type id of the raid
     */
    @Field(value = "raid_type")
    private String raidTypeId;

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
        this.id = channelId;
        this.difficulty = difficulty;
        this.raidType = raidType;
        this.zonedDateTime = zonedDateTime;
    }
}
