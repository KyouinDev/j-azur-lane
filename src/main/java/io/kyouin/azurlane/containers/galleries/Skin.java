package io.kyouin.azurlane.containers.ships;

import org.jsoup.nodes.Element;

public class Skin {

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
        String nameText = shipskin.selectFirst("div.shipskin-header").text().trim();
        String name = nameText.substring(nameText.indexOf(':') + 2);

        String src = shipskin.selectFirst("div.shipskin-image > a > img").attr("abs:src");
        String imageUrl = src.split("\\.png")[0].replace("thumb/", "") + ".png";

        String imageChibiUrl = null;

        if (shipskin.select("div.shipskin-chibi > a.new").isEmpty())
            imageChibiUrl = shipskin.selectFirst("div.shipskin-chibi > a > img").attr("abs:src");

        String obtainedFrom = shipskin.selectFirst("th:contains(Obtained From)").nextElementSibling().text().trim();
        boolean live2D = shipskin.selectFirst("th:contains(Live2D Model)").nextElementSibling().text().trim().equals("Yes");

        if (shipskin.select("th:contains(EN Client)").isEmpty()) return new Skin(name, imageUrl, imageChibiUrl, obtainedFrom, live2D);

        String availableEN = getSkinAvailable(shipskin, "EN");
        String availableCN = getSkinAvailable(shipskin, "CN");
        String availableJP = getSkinAvailable(shipskin, "JP");

        Integer cost = null;

        if (!shipskin.select("th:contains(Cost)").isEmpty()) {
            Element costElement = shipskin.selectFirst("th:contains(Cost)").nextElementSibling();

            if (costElement.selectFirst("img").attr("alt").equals("Gem")) cost = Integer.parseInt(costElement.text().trim());
        }

        return new Skin(name, imageUrl, imageChibiUrl, availableEN, availableCN, availableJP, obtainedFrom, live2D, cost);
    }

    private static String getSkinAvailable(Element shipskin, String client) {
        Element available = shipskin.selectFirst("th:contains(" + client + " Client)").nextElementSibling();
        String text = available.text().trim();

        if (text.equals("skin unavailable")) return text;

        return available.nextElementSibling().text().trim();
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
