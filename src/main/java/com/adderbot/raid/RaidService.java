package com.adderbot.raid;

import com.adderbot.raid.type.RaidTypeService;
import com.adderbot.raid.time.TimeService;
import discord4j.core.spec.EmbedCreateSpec;
import discord4j.rest.util.Color;
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
     * RaidTypeService used to get raid type information
     */
    private final RaidTypeService raidTypeService;

    /**
     * RaidValidator used to validate raids
     */
    private final RaidValidator raidValidator;

    /**
     * Autowired constructor wiring in the RaidRepository
     * @param raidRepository Autowired RaidRespository
     */
    @Autowired
    public RaidService(RaidRepository raidRepository, TimeService timeService, RaidTypeService raidTypeService, RaidValidator raidValidator) {
        this.raidRepository = raidRepository;
        this.timeService = timeService;
        this.raidTypeService = raidTypeService;
        this.raidValidator = raidValidator;
    }

    public void buildEmbed() {
        Integer.decode("0x0000s");
        EmbedCreateSpec embed = EmbedCreateSpec.builder()
                .color(Color.BLUE)
                .title("Title")
                .author("Some Name", "https://discord4j.com", "https://i.imgur.com/F9BhEoz.png")
                .description("a description")
                .thumbnail("https://i.imgur.com/F9BhEoz.png")
                .addField("field title", "value", false)
                .addField("\u200B", "\u200B", false)
                .addField("inline field", "value", true)
                .addField("inline field", "value", true)
                .addField("inline field", "value", true)
                .image("https://i.imgur.com/F9BhEoz.png")
                .footer("footer", "https://i.imgur.com/F9BhEoz.png")
                .build();
    }
}
