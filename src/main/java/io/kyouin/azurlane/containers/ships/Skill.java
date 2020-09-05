package io.kyouin.azurlane.containers.ships;

import io.kyouin.azurlane.enums.SkillType;
import io.kyouin.azurlane.utils.HtmlUtils;
import org.jsoup.nodes.Element;

public class Skill {

    private final String name;
    private final SkillType type;
    private final String iconUrl;
    private final String gifUrl;
    private final String description;

    public static Skill fromElement(Element tr) {
        tr.select("span").remove();

        String name = tr.selectFirst("div > div").html().split("<br>")[0].trim();
        SkillType type = SkillType.fromStyle(tr.selectFirst("th").attr("style"));

        String iconUrl = null;

        if (!tr.select("a > img").isEmpty())
            iconUrl = tr.selectFirst("a > img").attr("abs:src").split("\\.png")[0].replace("thumb/", "") + ".png";

        Element nextTr = tr.nextElementSibling().nextElementSibling();

        String gifUrl = null;

        if (!nextTr.select("a:contains((gif))").isEmpty()) {
            Element gifBody = HtmlUtils.getBody(nextTr.selectFirst("a:contains((gif))").attr("abs:href"));
            gifUrl = gifBody.selectFirst("div#file > a").attr("abs:href");
        }

        nextTr.select("a:contains((gif)),b,sup,small,li.mw-empty-elt").remove();
        nextTr.select("li").prepend("• ");

        String description = nextTr.text().replaceAll("•", "\n•").trim();

        return new Skill(name, type, iconUrl, gifUrl, description);
    }

    public Skill(String name, SkillType type, String iconUrl, String gifUrl, String description) {
        this.name = name;
        this.type = type;
        this.iconUrl = iconUrl;
        this.gifUrl = gifUrl;
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

    public String getGifUrl() {
        return gifUrl;
    }

    public String getDescription() {
        return description;
    }
}
