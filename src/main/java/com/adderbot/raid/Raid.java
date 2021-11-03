package com.adderbot.raid;

import lombok.Builder;
import lombok.Data;

@Builder
@Data
public class Raid {
    /**
     * Id of the channel that the raid is housed in
     */
    private String channelId;
    /**
     * Id of the message that the raid is contained in
     */
    private String messageId;
    /**
     * The difficulty of the raid (V, N, HM)
     */
    private String difficulty;
    /**
     * The id of the type of the raid (AA, HRC, etc)
     */
    private String raidTypeId;
    /**
     * The date of the raid
     */
    private String date;
    /**
     * The time of the raid
     */
    private String time;
    /**
     * The timezoneId of the raid (EDT, MST, etc)
     */
    private String timezoneId;
}
