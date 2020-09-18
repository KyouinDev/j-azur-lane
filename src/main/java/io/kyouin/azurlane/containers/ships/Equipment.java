package io.kyouin.azurlane.containers.ships;

import org.jsoup.nodes.Element;

public class Equipment {

    private final static String BRS = "br";

    private final String equippable;
    private final String efficiency;

    public static Equipment fromElement(Element tr) {
        tr.select(BRS).prepend("|");

        String text = tr.text();

        String equippable = text.split("\\|")[0];
        String efficiency = text.split("\\|")[1];

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
