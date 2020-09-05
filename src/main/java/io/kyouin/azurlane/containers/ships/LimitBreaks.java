package io.kyouin.azurlane.containers.ships;

import org.jsoup.nodes.Element;

public class LimitBreaks {

    private final String first;
    private final String second;
    private final String third;

    public static LimitBreaks fromElement(Element tbody) {
        if (tbody.select("td").size() == 1) return null;

        if (!tbody.select("li").isEmpty()) tbody.select("li").prepend("• ");

        String first = tbody.select("td").get(0).text().replaceAll(" ?•", "\n•").trim();
        String second = tbody.select("td").get(1).text().replaceAll(" ?•", "\n•").trim();
        String third = tbody.select("td").get(2).text().replaceAll(" ?•", "\n•").trim();

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
