package io.kyouin.azurlane.containers.ships;

import org.jsoup.nodes.Element;

public class Values {

    private final static String ENHANCE = "td:eq(0)";
    private final static String SCRAP = "td:eq(1)";
    private final static String IMGS = "img";

    private final String enhance;
    private final String scrap;

    public static Values fromElement(Element tbody) {
        tbody.select(IMGS).forEach(img -> img.prepend(img.attr("alt")));

        String enhance = tbody.selectFirst(ENHANCE).text();
        String scrap = tbody.selectFirst(SCRAP).text();

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
