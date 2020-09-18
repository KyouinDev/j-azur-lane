package io.kyouin.azurlane.containers.ships;

import org.jsoup.nodes.Element;

public class LimitBreaks {

    private final static String FIRST = "th:contains(1st)";
    private final static String SECOND = "th:contains(2nd)";
    private final static String THIRD = "th:contains(3rd)";
    private final static String ROWS = "tr";
    private final static String LIST_ELEMENTS = "li";

    private final String first;
    private final String second;
    private final String third;

    public static LimitBreaks fromElement(Element tbody) {
        if (tbody.select(ROWS).size() == 2) return null;

        tbody.select(LIST_ELEMENTS).prepend("• ");

        String first = tbody.selectFirst(FIRST).nextElementSibling().text().replaceAll(" ?•", "\n•").trim();
        String second = tbody.selectFirst(SECOND).nextElementSibling().text().replaceAll(" ?•", "\n•").trim();
        String third = tbody.selectFirst(THIRD).nextElementSibling().text().replaceAll(" ?•", "\n•").trim();

        return new LimitBreaks(first, second, third);
    }

    public LimitBreaks(String first, String second, String third) {
        this.first = first;
        this.second = second;
        this.third = third;
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
}
