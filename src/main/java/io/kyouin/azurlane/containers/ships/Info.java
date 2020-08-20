package io.kyouin.azurlane.containers.ships;

import org.jsoup.nodes.Element;

public class Info {

    private final String fullName;
    private final String bannerUrl;
    private final String iconUrl;
    private final String constructionTime;
    private final String rarity;
    private final String shipClass;
    private final String id;
    private final String nationality;
    private final String classification;
    private final String illustrator;
    private final String voiceActor;
    private final boolean secretaryQuest;
    private final boolean retrofit;

    public static Info fromElement(Element content) {
        if (!content.select("th.darklinks > a").isEmpty()) content.selectFirst("th.darklinks > a").remove();

        String fullName = content.selectFirst("th.darklinks").text().trim();
        String bannerUrl = content.selectFirst("table#Header > tbody > tr > th > a > img").attr("abs:src");
        String iconUrl = content.selectFirst("a.image > img").attr("abs:src");
        String constructionTime = content.selectFirst("th:contains(Construction Time)").nextElementSibling().text().trim();
        String rarity = content.selectFirst("th:contains(Rarity)").nextElementSibling().text().trim();
        String shipClass = content.selectFirst("th:contains(Class)").nextElementSibling().text().trim();
        String id = content.selectFirst("th:contains(ID)").nextElementSibling().text().trim();
        String nationality = content.selectFirst("th:contains(Nationality)").nextElementSibling().text().trim();
        String classification = content.selectFirst("th:contains(Classification)").nextElementSibling().text().trim();
        String illustrator = content.selectFirst("th:contains(Illustrator)").nextElementSibling().text().trim().replaceAll("[  ]", "");
        String voiceActor = content.selectFirst("th:contains(Voice actor)").nextElementSibling().text().trim().replaceAll("[  ]", "");
        boolean secretaryQuest = !content.select("a:contains(Questline)").isEmpty();
        boolean retrofit = !content.select("span#Retrofit").isEmpty();

        return new Info(fullName, bannerUrl, iconUrl, constructionTime, rarity, shipClass, id, nationality, classification, illustrator, voiceActor, secretaryQuest, retrofit);
    }

    public Info(String fullName, String bannerUrl, String iconUrl, String constructionTime, String rarity, String shipClass, String id, String nationality, String classification, String illustrator, String voiceActor, boolean secretaryQuest, boolean retrofit) {
        this.fullName = fullName;
        this.bannerUrl = bannerUrl;
        this.iconUrl = iconUrl;
        this.constructionTime = constructionTime;
        this.rarity = rarity;
        this.shipClass = shipClass;
        this.id = id;
        this.nationality = nationality;
        this.classification = classification;
        this.illustrator = illustrator;
        this.voiceActor = voiceActor;
        this.secretaryQuest = secretaryQuest;
        this.retrofit = retrofit;
    }

    public String getFullName() {
        return fullName;
    }

    public String getBannerUrl() {
        return bannerUrl;
    }

    public String getIconUrl() {
        return iconUrl;
    }

    public String getConstructionTime() {
        return constructionTime;
    }

    public String getRarity() {
        return rarity;
    }

    public String getShipClass() {
        return shipClass;
    }

    public String getId() {
        return id;
    }

    public String getNationality() {
        return nationality;
    }

    public String getClassification() {
        return classification;
    }

    public String getIllustrator() {
        return illustrator;
    }

    public String getVoiceActor() {
        return voiceActor;
    }

    public boolean hasSecretaryQuest() {
        return secretaryQuest;
    }

    public boolean hasRetrofit() {
        return retrofit;
    }
}
