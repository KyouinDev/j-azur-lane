package io.kyouin.azurlane.entities;

import org.jsoup.nodes.Element;

public class Obtainment {

    private final String bannerNotes;
    private final String generalNotes;

    public static Obtainment fromElement(Element td) {
        String bannerText = td.select("div").first().text();
        String generalText = td.select("div").last().text();

        bannerText = bannerText.replaceAll(" ?(Light:|Heavy:|Special:|Limited:)", "\n$1");
        generalText = generalText.replaceFirst("^Notes:", "");

        String bannerNotes = bannerText.isEmpty() ? null : bannerText.trim();
        String generalNotes = generalText.isEmpty() ? null : generalText.trim();

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
