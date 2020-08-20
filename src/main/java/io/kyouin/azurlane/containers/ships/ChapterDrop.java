package io.kyouin.azurlane.entities;

import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.util.Arrays;
import java.util.List;

public class ChapterDrop {

    private final String chapter;
    private final List<String> stages;

    public static ChapterDrop fromElement(Element tr) {
        Elements td = tr.select("td");
        td.select("a[title*=Boss] > img").forEach(img -> img.prepend(img.attr("alt")));
        td.select("a[title*=-] > img").forEach(img -> img.prepend("✓"));

        String chapter = td.get(0).text().trim();
        List<String> stages = Arrays.asList(td.get(1).text().trim(), td.get(2).text().trim(), td.get(3).text().trim(), td.get(4).text().trim());

        return new ChapterDrop(chapter, stages);
    }

    public ChapterDrop(String chapter, List<String> stages) {
        this.chapter = chapter;
        this.stages = stages;
    }

    public String getChapter() {
        return chapter;
    }

    public List<String> getStages() {
        return stages;
    }
}
