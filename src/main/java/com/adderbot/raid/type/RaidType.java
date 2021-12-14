package com.adderbot.raid.type;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

@Document(collection = "raid_types")
@Data
public class RaidType {
    @Id
    private String id;
    @Field(name = "name")
    private String name;
    @Field(name = "embed_color")
    private  String embedColor;
}
