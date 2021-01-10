package io.kyouin.azurlane.containers.ships;

import io.kyouin.azurlane.utils.ObjectUtils;
import org.jsoup.nodes.Element;

public class FleetTech {

    private final static String TECH_POINTS_LINKS = "a[title*=Tech point]";
    private final static String COLLECTION = "th:contains(Collection)";
    private final static String LVL120 = "th:contains(Level 120)";
    private final static String MLB = "th:contains(Max LB)";

    private final String collectionBonus;
    private final String level120Bonus;
    private final int collectionPoints;
    private final int maxLimitBreakPoints;
    private final int level120Points;
    private final int totalPoints;

    public static FleetTech fromElement(Element tbody) {
        if (tbody.select("td").size() == 1) return null;

        tbody.select(TECH_POINTS_LINKS).remove();
        String totalPointsText = tbody.select(COLLECTION).last().nextElementSibling().nextElementSibling().text();

        if ("0".equals(totalPointsText)) return null;

        tbody.select("img").forEach(img -> img.prepend(img.attr("alt")));

        String collectionBonus = tbody.select(COLLECTION).first().nextElementSibling().text()
                .replaceAll("\\)", "),")
                .replaceAll(" ", " ");
        String level120Bonus = tbody.select(LVL120).first().nextElementSibling().text()
                .replaceAll("\\)", "),")
                .replaceAll(" ", " ");
        int collectionPoints = ObjectUtils.parseInt(tbody.select(COLLECTION).last().nextElementSibling().text(), 0);
        int maxLimitBreakPoints = ObjectUtils.parseInt(tbody.select(MLB).first().nextElementSibling().text(), 0);
        int level120Points = ObjectUtils.parseInt(tbody.select(LVL120).last().nextElementSibling().text(), 0);
        int totalPoints = ObjectUtils.parseInt(totalPointsText, 0);

        return new FleetTech(collectionBonus, level120Bonus, collectionPoints, maxLimitBreakPoints, level120Points, totalPoints);
    }

    public FleetTech(String collectionBonus, String level120Bonus, Integer collectionPoints, Integer maxLimitBreakPoints, Integer level120Points, Integer totalPoints) {
        this.collectionBonus = collectionBonus;
        this.level120Bonus = level120Bonus;
        this.collectionPoints = collectionPoints;
        this.maxLimitBreakPoints = maxLimitBreakPoints;
        this.level120Points = level120Points;
        this.totalPoints = totalPoints;
    }

    public String getCollectionBonus() {
        return collectionBonus;
    }

    public String getLevel120Bonus() {
        return level120Bonus;
    }

    public int getCollectionPoints() {
        return collectionPoints;
    }

    public int getMaxLimitBreakPoints() {
        return maxLimitBreakPoints;
    }

    public int getLevel120Points() {
        return level120Points;
    }

    public int getTotalPoints() {
        return totalPoints;
    }
}
