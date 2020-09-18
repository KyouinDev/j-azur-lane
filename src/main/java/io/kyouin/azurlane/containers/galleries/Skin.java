package io.kyouin.azurlane.containers.galleries;

import org.jsoup.nodes.Element;

public class Skin {

    private final static String SKIN_HEADER = "div.shipskin-header";
    private final static String SKIN_IMAGE = "div.shipskin-image > * img";
    private final static String SKIN_CHIBI = "div.shipskin-chibi > * img";
    private final static String OBTAINED_FROM = "th:contains(Obtained From)";
    private final static String AVAILABLE_EN = "th:contains(EN Client)";
    private final static String AVAILABLE_CN = "th:contains(CN Client)";
    private final static String AVAILABLE_JP = "th:contains(JP Client)";
    private final static String LIVE2D = "th:contains(Live2D Model)";
    private final static String COST = "th:contains(Cost)";
    private final static String GEM = "img[alt=\"Gem\"]";

    private final String name;
    private final String imageUrl;
    private final String imageChibiUrl;
    private final String availableEN;
    private final String availableCN;
    private final String availableJP;
    private final String obtainedFrom;
    private final boolean live2D;
    private final Integer cost;

    public static Skin fromElement(Element shipskin) {
        if (shipskin.select(SKIN_IMAGE).isEmpty()) return null;

        String nameText = shipskin.selectFirst(SKIN_HEADER).text();
        String name = nameText.substring(nameText.indexOf(':') + 2);
        String imageUrl = shipskin.selectFirst(SKIN_IMAGE).attr("abs:src").split("\\.png")[0].replace("thumb/", "") + ".png";

        Element chibiElement = shipskin.selectFirst(SKIN_CHIBI);
        String imageChibiUrl = chibiElement == null ? null : chibiElement.attr("abs:src");

        String obtainedFrom = shipskin.selectFirst(OBTAINED_FROM).nextElementSibling().text();
        boolean live2D = shipskin.selectFirst(LIVE2D).nextElementSibling().text().equals("Yes");

        if (shipskin.select(AVAILABLE_EN).isEmpty()) return new Skin(name, imageUrl, imageChibiUrl, obtainedFrom, live2D);

        String availableEN = getSkinAvailable(shipskin, AVAILABLE_EN);
        String availableCN = getSkinAvailable(shipskin, AVAILABLE_CN);
        String availableJP = getSkinAvailable(shipskin, AVAILABLE_JP);

        Element costElement = shipskin.selectFirst(COST) == null ? null : shipskin.selectFirst(COST).nextElementSibling();
        Integer cost = costElement == null || costElement.selectFirst(GEM) == null ? null : Integer.parseInt(costElement.text());

        return new Skin(name, imageUrl, imageChibiUrl, availableEN, availableCN, availableJP, obtainedFrom, live2D, cost);
    }

    private static String getSkinAvailable(Element shipskin, String client) {
        Element available = shipskin.selectFirst(client).nextElementSibling();
        String text = available.text();

        if ("skin unavailable".equals(text)) return text;

        return available.nextElementSibling().text();
    }

    public Skin(String name, String imageUrl, String imageChibiUrl, String availableEN, String availableCN, String availableJP, String obtainedFrom, boolean live2D, Integer cost) {
        this.name = name;
        this.imageUrl = imageUrl;
        this.imageChibiUrl = imageChibiUrl;
        this.availableEN = availableEN;
        this.availableCN = availableCN;
        this.availableJP = availableJP;
        this.obtainedFrom = obtainedFrom;
        this.live2D = live2D;
        this.cost = cost;
    }

    public Skin(String name, String imageUrl, String imageChibiUrl, String obtainedFrom, boolean live2D) {
        this(name, imageUrl, imageChibiUrl, null, null, null, obtainedFrom, live2D, null);
    }

    public String getName() {
        return name;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public String getImageChibiUrl() {
        return imageChibiUrl;
    }

    public String getAvailableEN() {
        return availableEN;
    }

    public String getAvailableCN() {
        return availableCN;
    }

    public String getAvailableJP() {
        return availableJP;
    }

    public String getObtainedFrom() {
        return obtainedFrom;
    }

    public boolean isLive2D() {
        return live2D;
    }

    public Integer getCost() {
        return cost;
    }
}
