package io.kyouin.azurlane.containers.quotes;

import org.jsoup.nodes.Element;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class Quotes {

    private final static String SHIP_NAME = "h1#firstHeading";
    private final static String EN_QUOTES = "div[title*=English]";
    private final static String HEADERS = "h3";

    private final String name;
    private final List<SkinQuotes> skinQuotes;

    public static Quotes fromElement(Element content) {
        String name = content.selectFirst(SHIP_NAME).text().split("/")[0];

        Element englishTab = content.selectFirst(EN_QUOTES);

        if (englishTab == null) return new Quotes(name, new ArrayList<>());

        List<SkinQuotes> skinQuotes = englishTab.select(HEADERS).stream()
                .map(SkinQuotes::fromElement)
                .collect(Collectors.toList());

        return new Quotes(name, skinQuotes);
    }

    public Quotes(String name, List<SkinQuotes> skinQuotes) {
        this.name = name;
        this.skinQuotes = skinQuotes;
    }

    public String getName() {
        return name;
    }

    public List<SkinQuotes> getSkinQuotes() {
        return skinQuotes;
    }
}
