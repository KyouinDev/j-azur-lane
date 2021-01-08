package io.kyouin.azurlane.containers.ships;

import io.kyouin.azurlane.enums.StatsType;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.util.LinkedHashMap;
import java.util.Map;
import java.util.stream.IntStream;

public class Stats {

    private final static String TABLE_BODY = "table > tbody";

    private final String name;
    private final String armor;
    private final Map<StatsType, Integer> values;

    public static Stats fromElement(Element tabbertab) {
        String name = tabbertab.attr("title").replaceAll(" ", "");

        Elements tds = tabbertab.selectFirst(TABLE_BODY).select("td");
        String armor = tds.get(1).text();
        Map<StatsType, Integer> values = new LinkedHashMap<>();

        IntStream.range(0, tds.size()).forEach(i -> {
            if (i == 1) return;

            int stat;

            try {
                stat = Integer.parseInt(tds.get(i).text());
            } catch (NumberFormatException e) {
                stat = 0;
            }

            values.put(StatsType.values()[i], stat);
        });

        return new Stats(name, armor, values);
    }

    public Stats(String name, String armor, Map<StatsType, Integer> values) {
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

    public Map<StatsType, Integer> getValues() {
        return values;
    }
}
