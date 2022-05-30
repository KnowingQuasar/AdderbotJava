package com.adderbot.raid.type;

import lombok.Data;
import org.springframework.data.mongodb.core.mapping.Field;

import java.util.Map;

@Data
public class DifficultyRepresentation {
    @Field(name = "expected_number_dps")
    private int expected_number_dps;

    @Field(name = "raid_roles")
    private Map<String, Integer> raid_roles;
}
