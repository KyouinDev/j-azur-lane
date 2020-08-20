package io.kyouin.azurlane.containers.quotes;

import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

public class Quote {

    private final String event;
    private final String description;
    private final String audioUrl;

    public static Quote fromElement(Element tr) {
        Elements td = tr.select("td");

        String event = td.get(0).text().trim();
        String description = td.get(2).text().trim();

        Element audioElement = td.get(1).selectFirst("a");
        String audioUrl = null;

        if (audioElement != null) audioUrl = audioElement.attr("href");

        return new Quote(event, description, audioUrl);
    }

    public Quote(String event, String description, String audioUrl) {
        this.event = event;
        this.description = description;
        this.audioUrl = audioUrl;
    }

    public String getEvent() {
        return event;
    }

    public String getDescription() {
        return description;
    }

    public String getAudioUrl() {
        return audioUrl;
    }
}
