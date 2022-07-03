package com.adderbot.raid.type;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;
import org.springframework.data.mongodb.core.mapping.MongoId;

@Document(collection = "raid_types")
@Data
public class RaidType {
    @MongoId
    private String id;

    @Field(name = "name")
    private String name;

    @Field(name = "embed_color")
    private String embedColor;

    @Field(name = "hm")
    private DifficultyRepresentation hm;

    @Field(name = "non_hm")
    private DifficultyRepresentation non_hm;
}
