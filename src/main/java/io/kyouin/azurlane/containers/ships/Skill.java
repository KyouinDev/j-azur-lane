package io.kyouin.azurlane.containers.ships;

import io.kyouin.azurlane.enums.SkillType;
import io.kyouin.azurlane.utils.HtmlUtils;
import org.jsoup.nodes.Element;

public class Skill {

    private final static String SKILL_HEADER = "th";
    private final static String SKILL_ICON = "a > img";
    private final static String BARRAGE_GIF_LINK = "a:contains((gif))";
    private final static String BARRAGE_GIF = "div#file > a";
    private final static String USELESS_TAGS = "a:contains((gif)),b,sup,small,li.mw-empty-elt";
    private final static String LIST_ELEMENTS = "li";

    private final String name;
    private final SkillType type;
    private final String iconUrl;
    private final String gifUrl;
    private final String description;

    public static Skill fromElement(Element tr) {
        String name = tr.text().replaceAll(" ?CN:.+", "");
        SkillType type = SkillType.fromStyle(tr.selectFirst(SKILL_HEADER).attr("style"));

        Element icon = tr.selectFirst(SKILL_ICON);
        String iconUrl = icon == null ? null : icon.attr("abs:src").split("\\.png")[0].replace("thumb/", "") + ".png";

        Element nextTr = tr.nextElementSibling().nextElementSibling();

        Element gifElement = nextTr.selectFirst(BARRAGE_GIF_LINK);
        String gifUrl = gifElement == null ? null : HtmlUtils.getBody(gifElement.attr("abs:href")).selectFirst(BARRAGE_GIF).attr("abs:href");

        nextTr.select(USELESS_TAGS).remove();
        nextTr.select(LIST_ELEMENTS).prepend("• ");

        String description = nextTr.text().replaceAll("•", "\n•");

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
