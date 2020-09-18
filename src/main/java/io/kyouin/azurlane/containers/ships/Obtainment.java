package io.kyouin.azurlane.containers.ships;

import org.jsoup.nodes.Element;

public class Obtainment {

    private final static String BANNER_NOTES = "div:eq(0)";
    private final static String GENERAL_NOTES = "div:eq(1)";

    private final String bannerNotes;
    private final String generalNotes;

    public static Obtainment fromElement(Element td) {
        String bannerText = td.select(BANNER_NOTES).text().replaceAll(" ?(Light:|Heavy:|Special:|Limited:)", "\n$1").trim();
        String generalText = td.select(GENERAL_NOTES).text().replaceFirst("^Notes: ?", "");

        String bannerNotes = bannerText.isEmpty() ? null : bannerText;
        String generalNotes = generalText.isEmpty() ? null : generalText;

        return new Obtainment(bannerNotes, generalNotes);
    }

    public Obtainment(String bannerNotes, String generalNotes) {
        this.bannerNotes = bannerNotes;
        this.generalNotes = generalNotes;
    }

    public String getBannerNotes() {
        return bannerNotes;
    }

    public String getGeneralNotes() {
        return generalNotes;
    }
}
