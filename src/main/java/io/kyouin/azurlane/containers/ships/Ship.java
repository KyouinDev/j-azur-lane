package io.kyouin.azurlane.containers.ships;

import io.kyouin.azurlane.enums.SkillType;
import org.jsoup.nodes.Element;

import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

public class Ship {

    private final static String SHIP_NAME = "h1#firstHeading";
    private final static String CONTENT = "div.mw-parser-output";
    private final static String STATISTICS = "table#Statistics > * div.tabbertab";
    private final static String EQUIPMENT = "table#Gear > tbody > tr:gt(1)";
    private final static String LIMIT_BREAKS = "table#Limit_breaks > tbody";
    private final static String DEVELOPMENT_LEVELS = "table#Development_levels > tbody";
    private final static String SKILLS = "table#Skills > tbody > tr";
    private final static String SKILL_HEADER = "th";
    private final static String FLEET_TECH = "table#Fleet_technology > tbody";
    private final static String DROP_STAGES = "table#Drop > tbody";
    private final static String CHAPTERS = "tr:gt(1), tr:lt(14)";
    private final static String OBTAINMENT = "table#Obtainment > tbody";
    private final static String OBTAINMENT_ROW = "td";
    private final static String VALUES = "table#Values > tbody";

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
        String name = content.selectFirst(SHIP_NAME).text();
        Element divContent = content.selectFirst(CONTENT);
        Info info = Info.fromElement(divContent);

        List<Stats> statistics = divContent.select(STATISTICS).stream()
                .map(Stats::fromElement)
                .sorted(Comparator.comparing(Stats::getName))
                .collect(Collectors.toList());

        List<Equipment> equipment = divContent.select(EQUIPMENT).stream()
                .map(Equipment::fromElement)
                .collect(Collectors.toList());

        Element limitBreaksElement = divContent.selectFirst(LIMIT_BREAKS);
        LimitBreaks limitBreaks = limitBreaksElement == null ? null : LimitBreaks.fromElement(limitBreaksElement);

        Element developmentLevelsElement = divContent.selectFirst(DEVELOPMENT_LEVELS);
        DevelopmentLevels developmentLevels = developmentLevelsElement == null ? null : DevelopmentLevels.fromElement(developmentLevelsElement);

        List<Skill> skills = divContent.select(SKILLS).stream()
                .filter(skillRow -> !skillRow.select(SKILL_HEADER).isEmpty())
                .filter(skillRow -> SkillType.fromStyle(skillRow.selectFirst(SKILL_HEADER).attr("style")) != null)
                .map(Skill::fromElement)
                .collect(Collectors.toList());

        FleetTech fleetTech = FleetTech.fromElement(divContent.selectFirst(FLEET_TECH));

        Element chapterDropsElement = divContent.selectFirst(DROP_STAGES);
        List<ChapterDrop> chapterDrops = chapterDropsElement == null ? null : chapterDropsElement.select(CHAPTERS).stream()
                .map(ChapterDrop::fromElement)
                .collect(Collectors.toList());

        Element obtainmentElement = divContent.selectFirst(OBTAINMENT);
        Obtainment obtainment = obtainmentElement == null ? null : Obtainment.fromElement(obtainmentElement.selectFirst(OBTAINMENT_ROW));

        Element valuesElement = divContent.selectFirst(VALUES);
        Values values = valuesElement == null ? null : Values.fromElement(valuesElement);

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
