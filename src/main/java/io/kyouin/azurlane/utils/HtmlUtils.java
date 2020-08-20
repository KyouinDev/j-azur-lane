package io.kyouin.azurlane.utils;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;

import java.io.IOException;

public final class HtmlUtils {

    private HtmlUtils() {
        //nothing
    }

    private static long lastRequestMs = System.currentTimeMillis();

    public static Element getBody(String url) {
        long ms = System.currentTimeMillis() - lastRequestMs;

        try {
            Thread.sleep(Math.max(1000 - ms, 0));
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        Document doc = null;

        try {
            doc = Jsoup.connect(url)
                    .userAgent("Mozilla/5.0 (Windows NT 6.1) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/41.0.2228.0 Safari/537.36")
                    .ignoreContentType(true).ignoreHttpErrors(true).followRedirects(false)
                    .timeout(30 * 1000).get();

            lastRequestMs = System.currentTimeMillis();
        } catch (IOException ignored) {}

        if (doc == null) throw new IllegalArgumentException("Document could not be get: " + url);

        return doc.body();
    }
}
