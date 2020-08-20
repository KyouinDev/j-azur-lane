package io.kyouin.azurlane.containers.ships;

import io.kyouin.azurlane.enums.SkillType;
import org.jsoup.nodes.Element;

import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

public class Ship {

    private final String name;
    private final Info info;
    private final List<Stats> stats;
    private final List<Equipment> equipment;
    private final LimitBreaks limitBreaks;
    private final DevelopmentLevels developmentLevels;
    private final List<Skill> skills;
    private final FleetTech fleetTech;
    private final List<ChapterDrop> chapterDrops;
    private final Obtainment obtainment;
    private final Values values;

    public static Ship fromElement(Element content) {
        String name = content.selectFirst("h1#firstHeading").text().trim();
        Element divContent = content.selectFirst("div.mw-parser-output");
        Info info = Info.fromElement(divContent);

        List<Stats> statistics = divContent.selectFirst("table#Statistics").select("div.tabbertab").stream()
                .map(Stats::fromElement)
                .sorted(Comparator.comparing(Stats::getName))
                .collect(Collectors.toList());

        List<Equipment> equipment = divContent.select("table#Equipment > tbody > tr").stream()
                .skip(2)
                .map(Equipment::fromElement)
                .collect(Collectors.toList());

        LimitBreaks limitBreaks = null;
        DevelopmentLevels developmentLevels = null;

        if (!divContent.select("table#Limit_breaks").isEmpty()) {
            limitBreaks = LimitBreaks.fromElement(divContent.selectFirst("table#Limit_breaks > tbody"));
        }

        if (!divContent.select("table#Development_levels").isEmpty()) {
            developmentLevels = DevelopmentLevels.fromElement(divContent.selectFirst("table#Development_levels > tbody"));
        }

        List<Skill> skills = divContent.select("table#Skills > tbody > tr").stream()
                .filter(skillRow -> !skillRow.select("th").isEmpty())
                .filter(skillRow -> SkillType.fromStyle(skillRow.selectFirst("th").attr("style")) != null)
                .map(Skill::fromElement)
                .collect(Collectors.toList());

        FleetTech fleetTech = FleetTech.fromElement(divContent.selectFirst("table#Fleet_technology"));

        List<ChapterDrop> chapterDrops = null;

        if (!divContent.select("table#Drop").isEmpty()) {
            chapterDrops = divContent.select("table#Drop > tbody > tr").stream()
                    .skip(2).limit(13)
                    .map(ChapterDrop::fromElement)
                    .collect(Collectors.toList());
        }

        Obtainment obtainment = null;

        if (!divContent.select("table#Obtainment").isEmpty()) {
            obtainment = Obtainment.fromElement(divContent.selectFirst("table#Obtainment").selectFirst("td"));
        }

        Values values = Values.fromElement(divContent.selectFirst("table#Values"));

        return new Ship(name, info, statistics, equipment, limitBreaks, developmentLevels, skills, fleetTech, chapterDrops, obtainment, values);
    }

    public Ship(String name, Info info, List<Stats> stats, List<Equipment> equipment, LimitBreaks limitBreaks, DevelopmentLevels developmentLevels, List<Skill> skills, FleetTech fleetTech, List<ChapterDrop> chapterDrops, Obtainment obtainment, Values values) {
        this.name = name;
        this.info = info;
        this.stats = stats;
        this.equipment = equipment;
        this.limitBreaks = limitBreaks;
        this.developmentLevels = developmentLevels;
        this.skills = skills;
        this.fleetTech = fleetTech;
        this.chapterDrops = chapterDrops;
        this.obtainment = obtainment;
        this.values = values;
    }

    public String getName() {
        return name;
    }

    public Info getInfo() {
        return info;
    }

    public List<Stats> getStats() {
        return stats;
    }

    public List<Equipment> getEquipment() {
        return equipment;
    }

    public LimitBreaks getLimitBreaks() {
        return limitBreaks;
    }

    public DevelopmentLevels getDevelopmentLevels() {
        return developmentLevels;
    }

    public List<Skill> getSkills() {
        return skills;
    }

    public FleetTech getFleetTech() {
        return fleetTech;
    }

    public List<ChapterDrop> getChapterDrops() {
        return chapterDrops;
    }

    public Obtainment getObtainment() {
        return obtainment;
    }

    public Values getValues() {
        return values;
    }
}
