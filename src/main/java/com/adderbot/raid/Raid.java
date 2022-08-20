package com.adderbot.raid;

import lombok.Builder;
import lombok.Data;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;
import org.springframework.data.mongodb.core.mapping.MongoId;

import java.util.Map;

@Document(collection = "raids")
@Data
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
     * Epoch second timestamp for the raid
     */
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
}
