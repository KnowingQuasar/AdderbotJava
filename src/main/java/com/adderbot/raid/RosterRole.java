package com.adderbot.raid;

import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
public class RosterRole {

    private Integer numberAvailable;

    private List<String> players;

    public RosterRole(Integer numberAvailable) {
        this.numberAvailable = numberAvailable;
        players = new ArrayList<>();
    }
}
