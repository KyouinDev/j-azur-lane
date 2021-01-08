package io.kyouin.azurlane.containers.ships;

import org.jsoup.nodes.Element;

public class LimitBreaks {

    private final static String FIRST_LB = "th:contains(1st)";
    private final static String SECOND_LB = "th:contains(2nd)";
    private final static String THIRD_LB = "th:contains(3rd)";

    private final String first;
    private final String second;
    private final String third;

    public static LimitBreaks fromElement(Element tbody) {
        if (tbody.select("tr").size() == 2) return null;

        tbody.select("li").prepend("• ");

        String first = tbody.selectFirst(FIRST_LB).nextElementSibling().text().replaceAll(" ?•", "\n•").trim();
        String second = tbody.selectFirst(SECOND_LB).nextElementSibling().text().replaceAll(" ?•", "\n•").trim();
        String third = tbody.selectFirst(THIRD_LB).nextElementSibling().text().replaceAll(" ?•", "\n•").trim();

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
