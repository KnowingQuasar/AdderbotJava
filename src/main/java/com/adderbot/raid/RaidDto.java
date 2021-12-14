package com.adderbot.raid;

import com.adderbot.errors.ValidationException;
import com.adderbot.utils.ErrorUtils;
import com.adderbot.utils.ValidationUtils;
import lombok.Builder;
import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.PersistenceConstructor;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

import java.util.Map;

@Document("raid")
@Data
@Builder
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
    private Long timestampInSeconds;
    @Field(name = "available_roles")
    private Map<String, Integer> availableRoles;
    @Field(name = "players")
    private Map<String, String> players;

    @PersistenceConstructor
    public RaidDto(String id, String messageId, String difficulty, String raidTypeId, Long timestampInSeconds,
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

//    public void validate() throws ValidationException {
//        if (ValidationUtils.isNullOrEmpty(id)) {
//            throw new ValidationException(ErrorUtils.createError + " Id for raid was null. " + ErrorUtils.devHelpText);
//        }
//        if (ValidationUtils.isNullOrEmpty(messageId)) {
//            throw new ValidationException(ErrorUtils.createError + " Message Id for raid was null. " + ErrorUtils.devHelpText);
//        }
//        if (ValidationUtils.isNullOrEmpty(difficulty)) {
//            throw new ValidationException(ErrorUtils.createError + " Difficulty for raid was null. " + ErrorUtils.devHelpText);
//        }
//        if (ValidationUtils.isNullOrEmpty(raidTypeId)) {
//            throw new ValidationException(ErrorUtils.createError + " Raid Type for raid was null. " + ErrorUtils.devHelpText);
//        }
//        if (timestampInSeconds == null) {
//            throw new ValidationException(ErrorUtils.createError + " Please make sure that the date is in MM/dd/YYYY and time is in HH:SSam/pm formats.");
//        }
//        if (ValidationUtils.isNullOrEmpty(availableRoles)) {
//            throw new ValidationException(ErrorUtils.createError + " Please make sure that there are the right amount of DPS for this raid.");
//        }
//        if (ValidationUtils.isNullOrEmpty(players)) {
//            throw new ValidationException()
//        }
//    }
}
