package io.kyouin.azurlane.enums;

public enum StatsType {

    HEALTH("HP"),                  /* unused */ ARMOR("Armor"),  RELOAD("RLD"),
    FIREPOWER("FP"),               TORPEDO("TRP"),  EVASION("EVA"),
    ANTI_AIR("AA"),                AVIATION("AVI"), OIL_CONSUMPTION("OIL"),
    LUCK("LCK"),                   ACCURACY("ACC"), SPEED("SPD"),
    ANTI_SUBMARINE_WARFARE("ASW"), OXYGEN("OXY"),   AMMUNITION("AMMO");

    private final String tag;

    StatsType(String tag) {
        this.tag = tag;
    }

    public String getTag() {
        return tag;
    }
}
