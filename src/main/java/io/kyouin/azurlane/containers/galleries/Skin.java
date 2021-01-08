package io.kyouin.azurlane.containers.galleries;

import org.jsoup.nodes.Element;

public class Skin {

    private final static String SKIN_HEADER = "div.shipskin-header";
    private final static String SKIN_IMAGE = "div.shipskin-image > * img";
    private final static String SKIN_CHIBI = "div.shipskin-chibi > * img";
    private final static String OBTAINED_FROM = "th:contains(Obtained From)";
    private final static String AVAILABLE = "th:contains(EN Client)";
    private final static String LIVE2D = "th:contains(Live2D Model)";
    private final static String COST = "th:contains(Cost)";
    private final static String GEM = "img[alt=\"Gem\"]";

    private final String name;
    private final String imageUrl;
    private final String imageChibiUrl;
    private final Boolean unavailable;
    private final String obtainedFrom;
    private final boolean live2D;
    private final Integer cost;

    public static Skin fromElement(Element shipSkin) {
        if (shipSkin.select(SKIN_IMAGE).isEmpty()) return null;

        String nameText = shipSkin.selectFirst(SKIN_HEADER).text();
        String name = nameText.substring(nameText.indexOf(':') + 2);
        String imageUrl = shipSkin.selectFirst(SKIN_IMAGE).attr("abs:src").split("\\.png")[0].replace("thumb/", "") + ".png";
        String imageChibiUrl = null;

        if (!shipSkin.select(SKIN_CHIBI).isEmpty()) {
            imageChibiUrl = shipSkin.selectFirst(SKIN_CHIBI).attr("abs:src");
        }

        Boolean unavailable = null;

        if (!shipSkin.select(AVAILABLE).isEmpty() && shipSkin.selectFirst(AVAILABLE).nextElementSibling().text().equals("skin unavailable")) {
            unavailable = true;
        }

        String obtainedFrom = shipSkin.selectFirst(OBTAINED_FROM).nextElementSibling().text();
        boolean live2D = shipSkin.selectFirst(LIVE2D).nextElementSibling().text().equals("Yes");

        Integer cost = null;

        if (!shipSkin.select(COST).isEmpty() && !shipSkin.selectFirst(COST).nextElementSibling().select(GEM).isEmpty()) {
            cost = Integer.parseInt(shipSkin.selectFirst(COST).nextElementSibling().text());
        }

        return new Skin(name, imageUrl, imageChibiUrl, unavailable, obtainedFrom, live2D, cost);
    }

    public Skin(String name, String imageUrl, String imageChibiUrl, Boolean unavailable, String obtainedFrom, boolean live2D, Integer cost) {
        this.name = name;
        this.imageUrl = imageUrl;
        this.imageChibiUrl = imageChibiUrl;
        this.unavailable = unavailable;
        this.obtainedFrom = obtainedFrom;
        this.live2D = live2D;
        this.cost = cost;
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

    public boolean isAvailable() {
        return unavailable == null;
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
