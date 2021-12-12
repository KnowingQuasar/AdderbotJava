package com.adderbot.raid.time;

import lombok.Getter;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

@Document(collection = "supported_timezones")
@Getter
public class TimezoneDto {
    @Id
    private String id;

    @Field(name = "name")
    private String name;
    @Field(name = "utc_offset")
    private String utcOffset;


}
