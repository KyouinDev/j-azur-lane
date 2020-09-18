package io.kyouin.azurlane.containers.ships;

import io.kyouin.azurlane.enums.StatsType;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.util.LinkedHashMap;
import java.util.Map;
import java.util.stream.IntStream;

public class Stats {

    private final static String TABLE_CELLS = "table > * td";

    private final String name;
    private final Map<StatsType, String> values;

    public static Stats fromElement(Element tabbertab) {
        String name = tabbertab.attr("title").replaceAll(" ", "");
        Map<StatsType, String> values = new LinkedHashMap<>();

        Elements tds = tabbertab.select(TABLE_CELLS);
        IntStream.range(0, tds.size()).forEach(i -> values.put(StatsType.values()[i], tds.get(i).text()));

        return new Stats(name, values);
    }

    public Stats(String name, Map<StatsType, String> values) {
        this.name = name;
        this.values = values;
    }

    public String getName() {
        return name;
    }

    public Map<StatsType, String> getValues() {
        return values;
    }
}
