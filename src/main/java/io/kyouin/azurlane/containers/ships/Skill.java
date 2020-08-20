package io.kyouin.azurlane.containers.ships;

import io.kyouin.azurlane.enums.SkillType;
import org.jsoup.nodes.Element;

public class Skill {

    private final String name;
    private final SkillType type;
    private final String iconUrl;
    private final String description;

    public static Skill fromElement(Element tr) {
        tr.select("span").remove();

        String name = tr.text().trim();
        SkillType type = SkillType.fromStyle(tr.selectFirst("th").attr("style"));

        String iconUrl = null;

        if (!tr.select("a").isEmpty()) iconUrl = tr.selectFirst("a").attr("href");

        Element nextTr = tr.nextElementSibling().nextElementSibling();
        nextTr.select("a:contains((gif)),b,sup,small,li.mw-empty-elt").remove();
        nextTr.select("li").prepend("• ");

        String description = nextTr.text().replaceAll("•", "\n•").trim();

        return new Skill(name, type, iconUrl, description);
    }

    public Skill(String name, SkillType type, String iconUrl, String description) {
        this.name = name;
        this.type = type;
        this.iconUrl = iconUrl;
        this.description = description;
    }

    public String getName() {
        return name;
    }

    public SkillType getType() {
        return type;
    }

    public String getIconUrl() {
        return iconUrl;
    }

    public String getDescription() {
        return description;
    }
}
