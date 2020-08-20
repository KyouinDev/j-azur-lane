package io.kyouin.azurlane.entities;

import io.kyouin.azurlane.enums.StatsType;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.util.LinkedHashMap;
import java.util.Map;
import java.util.stream.IntStream;

public class Stats {

    private final String name;
    private final Map<StatsType, String> values;

    public static Stats fromElement(Element tabbertab) {
        String name = tabbertab.attr("title").trim().replaceAll(" ", "");
        Map<StatsType, String> values = new LinkedHashMap<>();

        Elements td = tabbertab.selectFirst("table").select("td");
        IntStream.range(0, td.size()).forEach(i -> values.put(StatsType.values()[i], td.get(i).text().trim()));

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
