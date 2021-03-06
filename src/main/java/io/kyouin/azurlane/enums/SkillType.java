package io.kyouin.azurlane.enums;

import java.util.Arrays;

public enum SkillType {

    OFFENSIVE("Pink"), DEFENSIVE("DeepSkyBlue"), SUPPORT("Gold");

    private final String color;

    SkillType(String color) {
        this.color = color;
    }

    public static SkillType fromStyle(String style) {
        return Arrays.stream(SkillType.values())
                .filter(type -> style.contains(type.color))
                .findFirst()
                .orElse(null);
    }
}
