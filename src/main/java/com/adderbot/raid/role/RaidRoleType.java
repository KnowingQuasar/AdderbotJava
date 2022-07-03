package com.adderbot.raid.role;

import lombok.Data;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;
import org.springframework.data.mongodb.core.mapping.MongoId;

@Document(collection = "raid_role_types")
@Data
public class RaidRoleType {

    @MongoId
    private String id;

    @Field(value = "category")
    private String category;

    @Field(value = "name")
    private String name;

    @Field(value = "displayPriority")
    private Integer displayPriority;

    @Field(value = "displayName")
    private String displayName;

    @Field(value = "displayEmoji")
    private String displayEmoji;
}
