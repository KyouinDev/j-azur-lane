package io.kyouin.azurlane.containers.ships;

import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

public class Obtainment {

    private final static String DROP_TABLE = "div#Construction > * tr";
    private final static String ADDITIONAL_NOTES = "th:contains(Additional Notes)";
    private final static String WAR_ARCHIVES = "th:contains(Available in War Archives)";
    private final static String HISTORY = "th:contains(Construction and Drop History)";

    private final boolean light;
    private final boolean heavy;
    private final boolean special;
    private final String additionalNotes;
    private final String warArchives;
    private final String history;

    public static Obtainment fromElement(Element content) {
        Elements tds = content.select(DROP_TABLE).get(3).select("td");

        boolean light = tds.get(0).text().equals("✓");
        boolean heavy = tds.get(1).text().equals("✓");
        boolean special = tds.get(2).text().equals("✓");
        String additionalNotes = null;
        String warArchives = null;
        String history = null;

        if (!content.select(ADDITIONAL_NOTES).isEmpty()) {
            content.selectFirst(ADDITIONAL_NOTES).select("img").forEach(img -> img.prepend(img.attr("alt")));
            additionalNotes = content.selectFirst(ADDITIONAL_NOTES).nextElementSibling().text();
        }

        if (!content.select(WAR_ARCHIVES).isEmpty()) {
            content.selectFirst(WAR_ARCHIVES).select("img").forEach(img -> img.prepend(img.attr("alt")));
            warArchives = content.selectFirst(WAR_ARCHIVES).nextElementSibling().text();
        }

        if (!content.select(HISTORY).isEmpty()) {
            content.selectFirst(HISTORY).select("img").forEach(img -> img.prepend(img.attr("alt")));
            history = content.selectFirst(HISTORY).nextElementSibling().text();
        }

        return new Obtainment(light, heavy, special, additionalNotes, warArchives, history);
    }

    public Obtainment(boolean light, boolean heavy, boolean special, String additionalNotes, String warArchives, String history) {
        this.light = light;
        this.heavy = heavy;
        this.special = special;
        this.additionalNotes = additionalNotes;
        this.warArchives = warArchives;
        this.history = history;
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

    public String getWarArchives() {
        return warArchives;
    }

    public String getHistory() {
        return history;
    }
}
