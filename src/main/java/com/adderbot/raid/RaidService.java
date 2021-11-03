package com.adderbot.raid;

import com.adderbot.errors.ValidationException;
import com.adderbot.time.TimeService;
import lombok.NonNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class RaidService {
    /**
     * Repository used for Raids
     */
    private final RaidRepository raidRepository;

    /**
     * TimeService used to parse timezones and times
     */
    private final TimeService timeService;

    /**
     * RaidValidator used to validate raids
     */
    private final RaidValidator raidValidator;

    /**
     * Autowired constructor wiring in the RaidRepository
     * @param raidRepository Autowired RaidRespository
     */
    @Autowired
    public RaidService(RaidRepository raidRepository, TimeService timeService, RaidValidator raidValidator) {
        this.raidRepository = raidRepository;
        this.timeService = timeService;
        this.raidValidator = raidValidator;
    }
}
