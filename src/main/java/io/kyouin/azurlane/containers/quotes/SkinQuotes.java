package io.kyouin.azurlane.containers.quotes;

import org.jsoup.nodes.Element;

import java.util.List;
import java.util.stream.Collectors;

public class SkinQuotes {

    private final String skinName;
    private final List<Quote> quotes;

    public static SkinQuotes fromElement(Element h3) {
        String skinName = h3.text().trim();
        Element tbody = h3.nextElementSibling().selectFirst("table > tbody");

        List<Quote> quotes = tbody.select("tr").stream()
                .skip(1)
                .map(Quote::fromElement)
                .collect(Collectors.toList());

        return new SkinQuotes(skinName, quotes);
    }

    public SkinQuotes(String skinName, List<Quote> quotes) {
        this.skinName = skinName;
        this.quotes = quotes;
    }

    public String getSkinName() {
        return skinName;
    }

    public List<Quote> getQuotes() {
        return quotes;
    }
}
