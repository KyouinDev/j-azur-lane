package io.kyouin.azurlane.containers.quotes;

import org.jsoup.nodes.Element;

public class Line {

    private final static String EVENT = "td:eq(0)";
    private final static String AUDIO = "td:eq(1) > a";
    private final static String TRANSCRIPTION = "td:eq(2)";

    private final String event;
    private final String transcription;
    private final String audioUrl;

    public static Line fromElement(Element tr) {
        String event = tr.selectFirst(EVENT).text();
        String transcription = tr.selectFirst(TRANSCRIPTION).text();
        String audioUrl = null;

        if (!tr.select(AUDIO).isEmpty()) {
            audioUrl = tr.selectFirst(AUDIO).attr("href");
        }

        return new Line(event, transcription, audioUrl);
    }

    public Line(String event, String transcription, String audioUrl) {
        this.event = event;
        this.transcription = transcription;
        this.audioUrl = audioUrl;
    }

    public String getEvent() {
        return event;
    }

    public String getTranscription() {
        return transcription;
    }

    public String getAudioUrl() {
        return audioUrl;
    }

    public boolean hasAudio() {
        return audioUrl != null;
    }
}
