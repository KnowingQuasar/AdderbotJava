package com.adderbot.raid;

import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.PersistenceConstructor;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

import java.util.Map;

@Document("raid")
public class RaidDto {
    @Id
    private String id;

    @Field(name = "message_id")
    private String messageId;
    @Field(name = "difficulty")
    private String difficulty;
    @Field(name = "raid_type_id")
    private String raidTypeId;
    @Field(name = "date_timestamp")
    private String dateTimestampInMs;
    @Field(name = "available_roles")
    private Map<String, Integer> availableRoles;
    @Field(name = "players")
    private Map<String, String> players;

    @PersistenceConstructor
    public RaidDto(String id, String messageId, String difficulty, String raidTypeId, String dateTimestampInMs,
                   Map<String, Integer> availableRoles, Map<String, String> players) {
        super();
        this.id = id;
        this.messageId = messageId;
        this.difficulty = difficulty;
        this.raidTypeId = raidTypeId;
        this.dateTimestampInMs = dateTimestampInMs;
        this.availableRoles = availableRoles;
        this.players = players;
    }
}
