package io.kyouin.azurlane.containers.ships;

import io.kyouin.azurlane.enums.StatsType;
import io.kyouin.azurlane.utils.ObjectUtils;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class Stats {

    private final static String TABLE_BODY = "table > tbody";

    private final String name;
    private final String armor;
    private final List<Integer> values;

    public static Stats fromElement(Element tabbertab) {
        Elements tds = tabbertab.selectFirst(TABLE_BODY).select("td");
        String name = tabbertab.attr("title").strip();
        String armor = tds.get(1).text();
        List<Integer> values = IntStream.range(0, tds.size())
                .filter(i -> i != 1)
                .mapToObj(i -> ObjectUtils.parseInt(tds.get(i).text(), 0))
                .collect(Collectors.toList());

        return new Stats(name, armor, values);
    }

    public Stats(String name, String armor, List<Integer> values) {
        this.name = name;
        this.armor = armor;
        this.values = values;
    }

    public String getName() {
        return name;
    }

    public String getArmor() {
        return armor;
    }

    public List<Integer> getValues() {
        return values;
    }

    public int getHealth() {
        return values.get(StatsType.HEALTH.ordinal());
    }

    public int getReload() {
        return values.get(StatsType.RELOAD.ordinal());
    }

    public int getFirepower() {
        return values.get(StatsType.FIREPOWER.ordinal());
    }

    public int getTorpedo() {
        return values.get(StatsType.TORPEDO.ordinal());
    }

    public int getEvasion() {
        return values.get(StatsType.EVASION.ordinal());
    }

    public int getAntiAir() {
        return values.get(StatsType.ANTI_AIR.ordinal());
    }

    public int getAviation() {
        return values.get(StatsType.AVIATION.ordinal());
    }

    public int getOilConsumption() {
        return values.get(StatsType.OIL_CONSUMPTION.ordinal());
    }

    public int getLuck() {
        return values.get(StatsType.LUCK.ordinal());
    }

    public int getAccuracy() {
        return values.get(StatsType.ACCURACY.ordinal());
    }

    public int getSpeed() {
        return values.get(StatsType.SPEED.ordinal());
    }

    public int getAntiSubmarineWarfare() {
        return values.get(StatsType.ANTI_SUBMARINE_WARFARE.ordinal());
    }

    public int getOxygen() {
        return values.get(StatsType.OXYGEN.ordinal());
    }

    public int getAmmunition() {
        return values.get(StatsType.AMMUNITION.ordinal());
    }
}
