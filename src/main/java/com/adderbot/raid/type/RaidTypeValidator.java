package com.adderbot.raid.type;

import com.adderbot.errors.Error;
import com.adderbot.utils.ErrorUtils;
import com.adderbot.utils.ValidationUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Set;

@Component
public class RaidTypeValidator {
    private final RaidTypeService raidTypeService;

    @Autowired
    public RaidTypeValidator(RaidTypeService raidTypeService) {
        this.raidTypeService = raidTypeService;
    }

    public void validateRaidTypeById(String raidTypeId, Set<Error> errors) {
        if (ValidationUtils.isNullOrEmpty(raidTypeId) || raidTypeService.getRaidTypeById(raidTypeId) == null) {
            errors.add(new Error("Raid Type was not found in the database. " + ErrorUtils.devHelpText));
        }
    }
}
