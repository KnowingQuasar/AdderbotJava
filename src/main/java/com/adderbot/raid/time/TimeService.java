package com.adderbot.raid.time;

import org.jetbrains.annotations.NotNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;

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

    /**
     * Creates a timestamp in seconds using the given date, time, and timezone
     * @param date the date
     * @param time the time
     * @param timezone the timezone
     * @return the timestamp in seconds
     */
    public Long createTimestamp(@NotNull String date, @NotNull String time, @NotNull String timezone) {
        DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("M/d/yyyy h[:m]a Z");

        TimezoneDto timezoneDto = getTimezoneById(timezone);

        if (timezoneDto == null) {
            return null;
        }

        ZonedDateTime localDate = ZonedDateTime.parse(date.replace(" ", "") + " " + time.replace(" ", "") + " " + timezoneDto.getUtcOffset(),
                dateTimeFormatter);

        return localDate.toEpochSecond();
    }
}
