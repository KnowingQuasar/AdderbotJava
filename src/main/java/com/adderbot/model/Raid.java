package com.adderbot.model;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.Map;

@Document("raid")
public class Raid {
    @Id
    private String id;

    private String messageId;
    private String difficulty;
    private String raidTypeId;
    private String dateTimestampInMs;
    private Map<String, Integer> availableRoles;
    private Map<String, String> players;

    public Raid(String id, String messageId, String difficulty, String raidTypeId, String dateTimestampInMs,
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
