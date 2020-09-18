package io.kyouin.azurlane.containers.ships;

import org.jsoup.nodes.Element;

public class FleetTech {

    private final static String TECH_POINTS_LINKS = "a[title*=Tech point]";
    private final static String COLLECTION = "th:contains(Collection)";
    private final static String LVL120 = "th:contains(Level 120)";
    private final static String MLB = "th:contains(Max LB)";
    private final static String TDS = "td";
    private final static String IMGS = "img";

    private final String collectionBonus;
    private final String level120Bonus;
    private final Integer collectionPoints;
    private final Integer maxLimitBreakPoints;
    private final Integer level120Points;
    private final Integer totalPoints;

    public static FleetTech fromElement(Element tbody) {
        if (tbody.select(TDS).size() == 1) return null;

        tbody.select(TECH_POINTS_LINKS).remove();
        tbody.select(IMGS).forEach(img -> img.prepend(img.attr("alt")));

        String collectionBonus = tbody.select(COLLECTION).first().nextElementSibling().text().replaceAll(" ", " ");
        String level120Bonus = tbody.select(LVL120).first().nextElementSibling().text().replaceAll(" ", " ");

        Element collectionPointsElement = tbody.select(COLLECTION).last().nextElementSibling();
        Element maxLimitBreakPointsElement = tbody.select(MLB).first().nextElementSibling();
        Element level120PointsElement = tbody.select(LVL120).last().nextElementSibling();
        Element totalPointsElement = tbody.select(COLLECTION).last().nextElementSibling().nextElementSibling();

        Integer collectionPoints = collectionPointsElement == null || collectionPointsElement.text().isEmpty() ? null : Integer.parseInt(collectionPointsElement.text());
        Integer maxLimitBreakPoints = collectionPointsElement == null || maxLimitBreakPointsElement.text().isEmpty() ? null : Integer.parseInt(maxLimitBreakPointsElement.text());
        Integer level120Points = collectionPointsElement == null || level120PointsElement.text().isEmpty() ? null : Integer.parseInt(level120PointsElement.text());
        Integer totalPoints = collectionPointsElement == null || totalPointsElement.text().isEmpty() ? null : Integer.parseInt(totalPointsElement.text());

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

    public Integer getCollectionPoints() {
        return collectionPoints;
    }

    public Integer getMaxLimitBreakPoints() {
        return maxLimitBreakPoints;
    }

    public Integer getLevel120Points() {
        return level120Points;
    }

    public Integer getTotalPoints() {
        return totalPoints;
    }
}
