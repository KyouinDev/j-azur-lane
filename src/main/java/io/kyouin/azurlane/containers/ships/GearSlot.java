package io.kyouin.azurlane.containers.ships;

import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

public class GearSlot {

    private final String efficiency;
    private final String equippable;
    private final String quantity;

    public static GearSlot fromElement(Element tr) {
        Elements tds = tr.select("td");

        String efficiency = tds.get(1).text();
        String equippable = tds.get(2).text();
        String quantity = tds.get(3).text();

        return new GearSlot(efficiency, equippable, quantity);
    }

    public GearSlot(String efficiency, String equippable, String quantity) {
        this.efficiency = efficiency;
        this.equippable = equippable;
        this.quantity = quantity;
    }

    public String getEfficiency() {
        return efficiency;
    }

    public String getEquippable() {
        return equippable;
    }

    public String getQuantity() {
        return quantity;
    }
}
