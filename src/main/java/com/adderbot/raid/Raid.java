package com.adderbot.raid;

import com.adderbot.raid.type.RaidType;
import discord4j.core.object.entity.User;
import lombok.AllArgsConstructor;
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
@AllArgsConstructor
@Builder
public class Raid {

    /**
     * Id of the channel that the raid is hosted in, doubles as raid id
     */
    @MongoId
    private String id;

    /**
     * Id of the message housing the raid
     */
    @Field(value = "messageId")
    private String messageId;

    /**
     * Id of the guild that the raid is hosted in
     */
    @Field(value = "guildId")
    private String guildId;

    /**
     * Id of the raid lead
     */
    @Field(value = "leadId")
    private String leadId;

    /**
     * Discord User Object of the lead
     */
    private User lead;

    /**
     * The difficulty of the raid (V, N, HM)
     */
    @Field(value = "difficulty")
    private String difficulty;

    /**
     * The raid type id of the raid
     */
    @Field(value = "raidTypeId")
    private String raidTypeId;

    /**
     * The raid type of the raid
     */
    private RaidType raidType;

    /**
     * The DateTime object for the raid
     */
    private ZonedDateTime zonedDateTime;

    @Field(name = "runDateTimestamp")
    private Long timestampInSeconds;

    /**
     * Roles that are still available in the raid
     */
    @Field(name = "availableRoles")
    private Map<String, Integer> availableRoles;

    /**
     * Map of players
     */
    @Field(name = "players")
    private Map<String, RosterRole> roster;

    @PersistenceConstructor
    public Raid(String id, String messageId, String guildId, String difficulty, String raidTypeId, Long timestampInSeconds,
                   Map<String, Integer> availableRoles, Map<String, RosterRole> roster) {
        super();
        this.id = id;
        this.messageId = messageId;
        this.guildId = guildId;
        this.difficulty = difficulty;
        this.raidTypeId = raidTypeId;
        this.timestampInSeconds = timestampInSeconds;
        this.availableRoles = availableRoles;
        this.roster = roster;
    }
}
