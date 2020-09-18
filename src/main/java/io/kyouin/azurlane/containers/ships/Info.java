package io.kyouin.azurlane.containers.ships;

import org.jsoup.nodes.Element;

public class Info {

    private final static String FULL_NAME = "th.darklinks";
    private final static String USELESS_LINK = "th.darklinks > a";
    private final static String BANNER_IMAGE = "table#Header > * img";
    private final static String ICON_IMAGE = "a.image > img";
    private final static String CONSTRUCTION_TIME = "th:contains(Construction Time)";
    private final static String RARITY = "th:contains(Rarity)";
    private final static String CLASS = "th:contains(Class)";
    private final static String ID = "th:contains(ID)";
    private final static String FACTION = "th:contains(Faction)";
    private final static String CLASSIFICATION = "th:contains(Classification)";
    private final static String ILLUSTRATOR = "th:contains(Illustrator)";
    private final static String VOICE_ACTOR = "th:contains(Voice actor)";
    private final static String QUESTLINE = "th:contains(Questline)";
    private final static String RETROFIT = "span#Retrofit";

    private final String fullName;
    private final String bannerUrl;
    private final String iconUrl;
    private final String constructionTime;
    private final String rarity;
    private final String shipClass;
    private final String id;
    private final String faction;
    private final String classification;
    private final String illustrator;
    private final String voiceActor;
    private final boolean secretaryQuest;
    private final boolean retrofit;

    public static Info fromElement(Element content) {
        content.selectFirst(USELESS_LINK).remove();

        String fullName = content.selectFirst(FULL_NAME).text();
        String bannerUrl = content.selectFirst(BANNER_IMAGE).attr("abs:src");
        String iconUrl = content.selectFirst(ICON_IMAGE).attr("abs:src");
        String constructionTime = content.selectFirst(CONSTRUCTION_TIME).nextElementSibling().text();
        String rarity = content.selectFirst(RARITY).nextElementSibling().text();
        String shipClass = content.selectFirst(CLASS).nextElementSibling().text();
        String id = content.selectFirst(ID).nextElementSibling().text();
        String faction = content.selectFirst(FACTION).nextElementSibling().text();
        String classification = content.selectFirst(CLASSIFICATION).nextElementSibling().text();
        String illustrator = content.selectFirst(ILLUSTRATOR).nextElementSibling().text().replaceAll("[  ]", "");
        String voiceActor = content.selectFirst(VOICE_ACTOR).nextElementSibling().text().replaceAll("[  ]", "");
        boolean secretaryQuest = !content.select(QUESTLINE).isEmpty();
        boolean retrofit = !content.select(RETROFIT).isEmpty();

        return new Info(fullName, bannerUrl, iconUrl, constructionTime, rarity, shipClass, id, faction, classification, illustrator, voiceActor, secretaryQuest, retrofit);
    }

    public Info(String fullName, String bannerUrl, String iconUrl, String constructionTime, String rarity, String shipClass, String id, String faction, String classification, String illustrator, String voiceActor, boolean secretaryQuest, boolean retrofit) {
        this.fullName = fullName;
        this.bannerUrl = bannerUrl;
        this.iconUrl = iconUrl;
        this.constructionTime = constructionTime;
        this.rarity = rarity;
        this.shipClass = shipClass;
        this.id = id;
        this.faction = faction;
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

    public String getFaction() {
        return faction;
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
