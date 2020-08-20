package io.kyouin.azurlane.containers.quotes;

import org.jsoup.nodes.Element;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class Quotes {

    private final String name;
    private final List<SkinQuotes> skinQuotes;

    public static Quotes fromElement(Element content) {
        String name = content.selectFirst("h1#firstHeading").text().trim().replace("/Quotes", "");
        Element englishTab = content.selectFirst("div[title*=English]");

        if (englishTab == null) return new Quotes(name, new ArrayList<>());

        List<SkinQuotes> skinQuotes = englishTab.select("h3").stream()
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
