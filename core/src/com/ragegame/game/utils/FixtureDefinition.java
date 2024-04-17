package com.ragegame.game.utils;

import java.util.UUID;

public class FixtureDefinition {
    public UUID id;
    public String fixtureType;

    public FixtureDefinition(UUID id, String fixtureType) {
        this.id = id;
        this.fixtureType = fixtureType;
    }
}
