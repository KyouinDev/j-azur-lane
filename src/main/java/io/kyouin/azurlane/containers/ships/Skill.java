package io.kyouin.azurlane.containers.ships;

import io.kyouin.azurlane.enums.SkillType;
import io.kyouin.azurlane.utils.HtmlUtils;
import org.jsoup.nodes.Element;

public class Skill {

    private final static String SKILL_ICON = "a > img";
    private final static String BARRAGE_GIF_LINK = "a:contains((gif))";
    private final static String BARRAGE_GIF = "div#file > a";
    private final static String USELESS_TAGS = "a:contains((gif)),b,sup,small,li.mw-empty-elt";

    private final String name;
    private final SkillType type;
    private final String iconUrl;
    private final String gifUrl;
    private final String description;

    public static Skill fromElement(Element tr) {
        String name = tr.text().replaceAll(" ?(CN|JP):.+", "");
        SkillType type = SkillType.fromStyle(tr.selectFirst("th").attr("style"));
        String iconUrl = null;

        if (!tr.select(SKILL_ICON).isEmpty()) {
            iconUrl = tr.selectFirst(SKILL_ICON).attr("abs:src").split("\\.png")[0].replace("thumb/", "") + ".png";
        }

        Element nextTr = tr.nextElementSibling().nextElementSibling();
        String gifUrl = null;

        if (!nextTr.select(BARRAGE_GIF_LINK).isEmpty()) {
            gifUrl = HtmlUtils.getBody(nextTr.selectFirst(BARRAGE_GIF_LINK).attr("abs:href")).selectFirst(BARRAGE_GIF).attr("abs:href");
        }

        nextTr.select(USELESS_TAGS).remove();
        nextTr.select("li").prepend("• ");

        String description = nextTr.text().replaceAll("•", "\n•").replace(". ,", "").strip();

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

    public boolean hasIcon() {
        return iconUrl != null;
    }

    public String getGifUrl() {
        return gifUrl;
    }

    public boolean hasGif() {
        return gifUrl != null;
    }

    public String getDescription() {
        return description;
    }
}
