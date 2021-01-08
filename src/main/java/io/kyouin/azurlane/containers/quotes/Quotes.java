package io.kyouin.azurlane.containers.quotes;

import org.jsoup.nodes.Element;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class Quotes {

    private final static String SHIP_NAME = "h1#firstHeading";
    private final static String EN_LINES = "div[title*=English]";

    private final String name;
    private final List<SkinLines> skinLines;

    public static Quotes fromElement(Element content) {
        String name = content.selectFirst(SHIP_NAME).text().split("/")[0];
        Element englishTab = content.selectFirst(EN_LINES);

        if (englishTab == null) return new Quotes(name, new ArrayList<>());

        List<SkinLines> skinLines = englishTab.select("h3").stream()
                .map(SkinLines::fromElement)
                .collect(Collectors.toList());

        return new Quotes(name, skinLines);
    }

    public Quotes(String name, List<SkinLines> skinLines) {
        this.name = name;
        this.skinLines = skinLines;
    }

    public String getName() {
        return name;
    }

    public List<SkinLines> getSkinLines() {
        return skinLines;
    }
}
