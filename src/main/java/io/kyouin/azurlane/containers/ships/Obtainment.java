package io.kyouin.azurlane.containers.ships;

import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

public class Obtainment {

    private final static String DROP_TABLE = "div#Construction > * tr";
    private final static String ADDITIONAL_NOTES = "th:contains(Additional Notes)";
    private final static String WAR_ARCHIVES_NOTES = "th:contains(Available in War Archives)";
    private final static String HISTORY_NOTES = "th:contains(Construction and Drop History)";

    private final boolean light;
    private final boolean heavy;
    private final boolean special;
    private final String additionalNotes;
    private final String warArchivesNotes;
    private final String historyNotes;

    public static Obtainment fromElement(Element content) {
        Elements tds = content.select(DROP_TABLE).get(3).select("td");

        boolean light = !tds.get(0).text().equals("-");
        boolean heavy = !tds.get(1).text().equals("-");
        boolean special = !tds.get(2).text().equals("-");
        String additionalNotes = null;
        String warArchivesNotes = null;
        String historyNotes = null;

        if (!content.select(ADDITIONAL_NOTES).isEmpty()) {
            content.selectFirst(ADDITIONAL_NOTES).nextElementSibling().select("img").forEach(img -> img.prepend(img.attr("alt")));
            additionalNotes = content.selectFirst(ADDITIONAL_NOTES).nextElementSibling().text();
        }

        if (!content.select(WAR_ARCHIVES_NOTES).isEmpty()) {
            content.selectFirst(WAR_ARCHIVES_NOTES).nextElementSibling().select("img").forEach(img -> img.prepend(img.attr("alt")));
            warArchivesNotes = content.selectFirst(WAR_ARCHIVES_NOTES).nextElementSibling().text();
        }

        if (!content.select(HISTORY_NOTES).isEmpty()) {
            content.selectFirst(HISTORY_NOTES).nextElementSibling().select("img").forEach(img -> img.prepend(img.attr("alt")));
            historyNotes = content.selectFirst(HISTORY_NOTES).nextElementSibling().text();
        }

        return new Obtainment(light, heavy, special, additionalNotes, warArchivesNotes, historyNotes);
    }

    public Obtainment(boolean light, boolean heavy, boolean special, String additionalNotes, String warArchivesNotes, String historyNotes) {
        this.light = light;
        this.heavy = heavy;
        this.special = special;
        this.additionalNotes = additionalNotes;
        this.warArchivesNotes = warArchivesNotes;
        this.historyNotes = historyNotes;
    }

    public boolean isLight() {
        return light;
    }

    public boolean isHeavy() {
        return heavy;
    }

    public boolean isSpecial() {
        return special;
    }

    public String getAdditionalNotes() {
        return additionalNotes;
    }

    public boolean hasAdditionalNotes() {
        return additionalNotes != null;
    }

    public String getWarArchivesNotes() {
        return warArchivesNotes;
    }

    public boolean hasWarArchivesNotes() {
        return warArchivesNotes != null;
    }

    public String getHistoryNotes() {
        return historyNotes;
    }

    public boolean hasHistoryNotes() {
        return historyNotes != null;
    }
}
