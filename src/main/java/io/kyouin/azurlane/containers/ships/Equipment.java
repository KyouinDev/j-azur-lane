package io.kyouin.azurlane.containers.ships;

import org.jsoup.nodes.Element;

public class Equipment {

    private final String equippable;
    private final String efficiency;

    public static Equipment fromElement(Element tr) {
        String equippable = tr.select("a").text().trim();
        tr.select("a").remove();

        String efficiency = tr.text().trim();

        return new Equipment(equippable, efficiency);
    }

    public Equipment(String equippable, String efficiency) {
        this.equippable = equippable;
        this.efficiency = efficiency;
    }

    public String getEquippable() {
        return equippable;
    }

    public String getEfficiency() {
        return efficiency;
    }
}
