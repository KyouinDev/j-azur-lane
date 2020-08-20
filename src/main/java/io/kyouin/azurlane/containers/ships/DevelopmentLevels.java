package io.kyouin.azurlane.entities;

import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

public class DevelopmentLevels {

    private final String first;
    private final String second;
    private final String third;
    private final String fourth;
    private final String fifth;
    private final String sixth;

    public static DevelopmentLevels fromElement(Element tbody) {
        Elements td = tbody.select("td");

        if (td.size() < 6) return null;

        td.select("img").forEach(img -> img.prepend(img.attr("alt")));
        td.select("li.mw-empty-elt").remove();
        td.select("li").prepend("• ");

        String first = td.get(0).text().replaceAll(" ?•", "\n•").trim();
        String second = td.get(1).text().replaceAll(" ?•", "\n•").trim();
        String third = td.get(2).text().replaceAll(" ?•", "\n•").trim();
        String fourth = td.get(3).text().replaceAll(" ?•", "\n•").trim();
        String fifth = td.get(4).text().replaceAll(" ?•", "\n•").trim();
        String sixth = td.get(5).text().replaceAll(" ?•", "\n•").trim();

        return new DevelopmentLevels(first, second, third, fourth, fifth, sixth);
    }

    public DevelopmentLevels(String first, String second, String third, String fourth, String fifth, String sixth) {
        this.first = first;
        this.second = second;
        this.third = third;
        this.fourth = fourth;
        this.fifth = fifth;
        this.sixth = sixth;
    }

    public String getFirst() {
        return first;
    }

    public String getSecond() {
        return second;
    }

    public String getThird() {
        return third;
    }

    public String getFourth() {
        return fourth;
    }

    public String getFifth() {
        return fifth;
    }

    public String getSixth() {
        return sixth;
    }
}
