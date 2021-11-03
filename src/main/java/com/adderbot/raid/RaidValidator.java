package com.adderbot.raid;

import com.adderbot.errors.Error;
import com.adderbot.raid.type.RaidTypeService;
import com.adderbot.time.TimeService;
import com.adderbot.utils.ValidationUtils;
import lombok.NonNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Set;

@Component
public class RaidValidator {
    private RaidService raidService;
    private RaidTypeService raidTypeService;
    private TimeService timeService;

    @Autowired
    public RaidValidator(RaidService raidService, RaidTypeService raidTypeService, TimeService timeService) {
        this.raidService = raidService;
        this.raidTypeService = raidTypeService;
        this.timeService = timeService;
    }

    public void validate(@NonNull Raid raid, Set<Error> errors) {
        validateDifficulty(raid, errors);
        validateRaidType(raid, errors);
    }

    public void validateDifficulty(@NonNull Raid raid, Set<Error> errors) {
        if (ValidationUtils.isNullOrEmpty(raid.getDifficulty())) {
            errors.add(new Error("Difficulty cannot be empty for created raid."));
        }
    }

    public void validateRaidType(@NonNull Raid raid, Set<Error> errors) {
        if (ValidationUtils.isNullOrEmpty(raid.getRaidTypeId())) {
            errors.add(new Error("Raid Type cannot be empty for created raid."));
        } else if(raidTypeService.getRaidTypeById(raid.getRaidTypeId()) == null) {
            errors.add(new Error("Raid Type was not found in the database. Contact the bot author for help."));
        }
    }

    public void validateDate(@NonNull Raid raid, Set<Error> errors) {
        if (ValidationUtils.isNullOrEmpty(raid.getDate())) {
            errors.add(new Error("Date cannot be empty for created raid."));
        } else {

        }
    }
}
