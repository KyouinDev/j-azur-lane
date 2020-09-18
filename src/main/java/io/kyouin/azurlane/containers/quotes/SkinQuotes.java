package io.kyouin.azurlane.containers.quotes;

import org.jsoup.nodes.Element;

import java.util.List;
import java.util.stream.Collectors;

public class SkinQuotes {

    private final static String TABLE_ROWS = "table > tbody > tr:gt(0)";

    private final String skinName;
    private final List<QuoteLine> quoteLines;

    public static SkinQuotes fromElement(Element h3) {
        String skinName = h3.text();

        List<QuoteLine> quoteLines = h3.nextElementSibling().select(TABLE_ROWS).stream()
                .map(QuoteLine::fromElement)
                .collect(Collectors.toList());

        return new SkinQuotes(skinName, quoteLines);
    }

    public SkinQuotes(String skinName, List<QuoteLine> quoteLines) {
        this.skinName = skinName;
        this.quoteLines = quoteLines;
    }

    public String getSkinName() {
        return skinName;
    }

    public List<QuoteLine> getQuotes() {
        return quoteLines;
    }
}
