package io.kyouin.azurlane.containers.quotes;

import org.jsoup.nodes.Element;

import java.util.List;
import java.util.stream.Collectors;

public class SkinLines {

    private final static String TABLE_ROWS = "table > tbody > tr:gt(0)";

    private final String skin;
    private final List<Line> lines;

    public static SkinLines fromElement(Element h3) {
        String skin = h3.text();
        List<Line> lines = h3.nextElementSibling().select(TABLE_ROWS).stream()
                .map(Line::fromElement)
                .collect(Collectors.toList());

        return new SkinLines(skin, lines);
    }

    public SkinLines(String skin, List<Line> lines) {
        this.skin = skin;
        this.lines = lines;
    }

    public String getSkin() {
        return skin;
    }

    public List<Line> getLines() {
        return lines;
    }
}
