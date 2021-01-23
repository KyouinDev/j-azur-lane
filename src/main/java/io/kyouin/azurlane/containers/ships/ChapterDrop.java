package io.kyouin.azurlane.containers.ships;

import org.jsoup.nodes.Element;

import java.util.Arrays;
import java.util.List;

public class ChapterDrop {

    private final static String BOSS_NODES = "td > a[title*=Boss] > img";
    private final static String DROP_STAGES = "td > a[title*=-] > img[alt*=Check]";
    private final static String CHAPTER_NAME = "td:eq(0)";
    private final static String FIRST_STAGE = "td:eq(1)";
    private final static String SECOND_STAGE = "td:eq(2)";
    private final static String THIRD_STAGE = "td:eq(3)";
    private final static String FOURTH_STAGE = "td:eq(4)";

    private final String chapter;
    private final List<String> stages;

    public static ChapterDrop fromElement(Element tr) {
        tr.select(BOSS_NODES).forEach(img -> img.prepend(img.attr("alt")));
        tr.select(DROP_STAGES).forEach(img -> img.prepend("✓"));

        String chapter = tr.select(CHAPTER_NAME).text();

        if (!chapter.startsWith("Chapter")) return null;

        List<String> stages = Arrays.asList(tr.select(FIRST_STAGE).text(), tr.select(SECOND_STAGE).text(), tr.select(THIRD_STAGE).text(), tr.select(FOURTH_STAGE).text());

        if (stages.stream().allMatch(""::equals)) return null;

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

    public boolean allStagesDrop() {
        return stages.stream().allMatch("✓"::equals);
    }
}
