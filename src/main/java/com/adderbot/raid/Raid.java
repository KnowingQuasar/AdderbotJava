package com.adderbot.raid;

import com.adderbot.raid.type.RaidType;
import discord4j.core.object.entity.User;
import lombok.Builder;
import lombok.Data;
import org.springframework.data.annotation.PersistenceConstructor;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;
import org.springframework.data.mongodb.core.mapping.MongoId;

import java.time.ZonedDateTime;
import java.util.Map;

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

    @Field(name = "date_timestamp")
    private Long timestampInSeconds;

    /**
     * Roles that are still available in the raid
     */
    @Field(name = "available_roles")
    private Map<String, Integer> availableRoles;

    /**
     * Map of players
     */
    @Field(name = "players")
    private Map<String, String> players;

    @PersistenceConstructor
    public Raid(String id, String messageId, String difficulty, String raidTypeId, Long timestampInSeconds,
                   Map<String, Integer> availableRoles, Map<String, String> players) {
        super();
        this.id = id;
        this.messageId = messageId;
        this.difficulty = difficulty;
        this.raidTypeId = raidTypeId;
        this.timestampInSeconds = timestampInSeconds;
        this.availableRoles = availableRoles;
        this.players = players;
    }

    public Raid(User lead, String channelId, String difficulty, RaidType raidType, ZonedDateTime zonedDateTime) {
        this.lead = lead;
        this.id = channelId;
        this.difficulty = difficulty;
        this.raidType = raidType;
        this.zonedDateTime = zonedDateTime;
    }
}
