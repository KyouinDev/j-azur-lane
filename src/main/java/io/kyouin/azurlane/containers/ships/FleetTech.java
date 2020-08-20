package io.kyouin.azurlane.containers.ships;

import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

public class FleetTech {

    private final String collectionBonus;
    private final String level120Bonus;
    private final Integer collectionPoints;
    private final Integer maxLimitBreakPoints;
    private final Integer level120Points;
    private final Integer totalPoints;

    public static FleetTech fromElement(Element tbody) {
        Elements td = tbody.select("td");

        if (td.size() == 2 || td.get(3).text().trim().equals("0")) return null;

        td.select("a[title*=Tech point]").remove();
        td.select("img").forEach(img -> img.prepend(img.attr("alt")));

        String collectionBonus = td.get(0).text().trim().replaceAll(" ", " ");
        String level120Bonus = td.get(1).text().trim().replaceAll(" ", " ");

        Integer collectionPoints = null;
        Integer maxLimitBreakPoints = null;
        Integer level120Points = null;
        Integer totalPoints = null;

        if (!td.get(2).text().isEmpty()) collectionPoints = Integer.parseInt(td.get(2).text().trim());
        if (!td.get(4).text().isEmpty()) maxLimitBreakPoints = Integer.parseInt(td.get(4).text().trim());
        if (!td.get(5).text().isEmpty()) level120Points = Integer.parseInt(td.get(5).text().trim());
        if (!td.get(3).text().isEmpty()) totalPoints = Integer.parseInt(td.get(3).text().trim());

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
