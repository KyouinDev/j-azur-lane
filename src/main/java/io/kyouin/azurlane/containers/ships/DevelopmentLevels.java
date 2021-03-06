package io.kyouin.azurlane.containers.ships;

import org.jsoup.nodes.Element;

public class DevelopmentLevels {

    private final static String FIRST_DL = "th:contains(5)";
    private final static String SECOND_DL = "th:contains(10)";
    private final static String THIRD_DL = "th:contains(15)";
    private final static String FOURTH_DL = "th:contains(20)";
    private final static String FIFTH_DL = "th:contains(25)";
    private final static String SIXTH_DL = "th:contains(30)";
    private final static String USELESS_TAGS = "li.mw-empty-elt";

    private final String first;
    private final String second;
    private final String third;
    private final String fourth;
    private final String fifth;
    private final String sixth;

    public static DevelopmentLevels fromElement(Element tbody) {
        if (tbody.select("td").size() < 6) return null;

        tbody.select(USELESS_TAGS).remove();
        tbody.select("img").forEach(img -> img.prepend(img.attr("alt")));
        tbody.select("li").prepend("• ");

        String first = tbody.selectFirst(FIRST_DL).nextElementSibling().text().replaceAll(" ?•", "\n•").strip();
        String second = tbody.selectFirst(SECOND_DL).nextElementSibling().text().replaceAll(" ?•", "\n•").strip();
        String third = tbody.selectFirst(THIRD_DL).nextElementSibling().text().replaceAll(" ?•", "\n•").strip();
        String fourth = tbody.selectFirst(FOURTH_DL).nextElementSibling().text().replaceAll(" ?•", "\n•").strip();
        String fifth = tbody.selectFirst(FIFTH_DL).nextElementSibling().text().replaceAll(" ?•", "\n•").strip();
        String sixth = tbody.selectFirst(SIXTH_DL).nextElementSibling().text().replaceAll(" ?•", "\n•").strip();

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
