package com.adderbot.time;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class TimeService {
    /**
     * Repository used for Timezones
     */
    private final TimezoneRepository timezoneRepository;

    @Autowired
    public TimeService(TimezoneRepository timezoneRepository) {
        this.timezoneRepository = timezoneRepository;
    }

    /**
     * Gets a timezone by its id (like EDT, MST, etc)
     * @param timezoneId The id of the timezone
     * @return The timezone
     */
    public TimezoneDto getTimezoneById(String timezoneId) {
        return timezoneRepository.findById(timezoneId).block();
    }
}
