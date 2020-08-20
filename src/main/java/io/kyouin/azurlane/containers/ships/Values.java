package io.kyouin.azurlane.entities;

import org.jsoup.nodes.Element;

public class Values {

    private final String enhance;
    private final String scrap;

    public static Values fromElement(Element tbody) {
        tbody.select("td > img").forEach(img -> img.prepend(img.attr("alt")));

        String enhance = tbody.select("td").first().text().trim();
        String scrap = tbody.select("td").last().text().trim();

        return new Values(enhance, scrap);
    }

    public Values(String enhance, String scrap) {
        this.enhance = enhance;
        this.scrap = scrap;
    }

    public String getEnhance() {
        return enhance;
    }

    public String getScrap() {
        return scrap;
    }
}
