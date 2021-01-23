package io.kyouin.azurlane.containers.ships;

import io.kyouin.azurlane.enums.SkillType;
import org.jsoup.nodes.Element;

import java.util.Comparator;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

public class Ship {

    private final static String SHIP_NAME = "h1#firstHeading";
    private final static String CONTENT = "div.mw-parser-output";
    private final static String STATISTICS = "table#Statistics > * div.tabbertab";
    private final static String GEAR = "tbody:has(* > th:contains(Gear))";
    private final static String LIMIT_BREAKS = "table#Limit_breaks > tbody";
    private final static String DEVELOPMENT_LEVELS = "table#Development_levels > tbody";
    private final static String SKILLS = "table#Skills > tbody > tr";
    private final static String FLEET_TECH = "table#Fleet_technology > tbody";
    private final static String DROP_TABLE = "table#Drop > tbody";
    private final static String DROP_STAGES = "tr:gt(2)";
    private final static String CONSTRUCTION = "div#Construction:has(table)";
    private final static String VALUES = "table#Values > tbody";

    private final String name;
    private final Info info;
    private final List<Stats> stats;
    private final List<GearSlot> gearSlots;
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

        List<GearSlot> gearSlots = divContent.selectFirst(GEAR).select("tr").stream()
                .skip(2)
                .map(GearSlot::fromElement)
                .collect(Collectors.toList());

        LimitBreaks limitBreaks = null;

        if (!divContent.select(LIMIT_BREAKS).isEmpty()) {
            limitBreaks = LimitBreaks.fromElement(divContent.selectFirst(LIMIT_BREAKS));
        }

        DevelopmentLevels developmentLevels = null;

        if (!divContent.select(DEVELOPMENT_LEVELS).isEmpty()) {
            developmentLevels = DevelopmentLevels.fromElement(divContent.selectFirst(DEVELOPMENT_LEVELS));
        }

        List<Skill> skills = divContent.select(SKILLS).stream()
                .filter(skillRow -> !skillRow.select("th").isEmpty())
                .filter(skillRow -> SkillType.fromStyle(skillRow.selectFirst("th").attr("style")) != null)
                .map(Skill::fromElement)
                .collect(Collectors.toList());

        FleetTech fleetTech = FleetTech.fromElement(divContent.selectFirst(FLEET_TECH));
        List<ChapterDrop> chapterDrops = null;

        if (!divContent.select(DROP_TABLE).isEmpty()) {
            chapterDrops = divContent.selectFirst(DROP_TABLE).select(DROP_STAGES).stream()
                    .map(ChapterDrop::fromElement)
                    .filter(Objects::nonNull)
                    .collect(Collectors.toList());

            if (chapterDrops.isEmpty()) {
                chapterDrops = null;
            }
        }

        Obtainment obtainment = null;

        if (!divContent.select(CONSTRUCTION).isEmpty()) {
            obtainment = Obtainment.fromElement(divContent);
        }

        Values values = null;

        if (!divContent.select(VALUES).isEmpty()) {
            values = Values.fromElement(divContent.selectFirst(VALUES));
        }

        return new Ship(name, info, statistics, gearSlots, limitBreaks, developmentLevels, skills, fleetTech, chapterDrops, obtainment, values);
    }

    public Ship(String name, Info info, List<Stats> stats, List<GearSlot> gearSlots, LimitBreaks limitBreaks, DevelopmentLevels developmentLevels, List<Skill> skills, FleetTech fleetTech, List<ChapterDrop> chapterDrops, Obtainment obtainment, Values values) {
        this.name = name;
        this.info = info;
        this.stats = stats;
        this.gearSlots = gearSlots;
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

    public List<GearSlot> getGearSlots() {
        return gearSlots;
    }

    public LimitBreaks getLimitBreaks() {
        return limitBreaks;
    }

    public boolean hasLimitBreaks() {
        return limitBreaks != null;
    }

    public DevelopmentLevels getDevelopmentLevels() {
        return developmentLevels;
    }

    public boolean hasDevelopmentLevels() {
        return developmentLevels != null;
    }

    public List<Skill> getSkills() {
        return skills;
    }

    public FleetTech getFleetTech() {
        return fleetTech;
    }

    public boolean hasFleetTech() {
        return fleetTech != null;
    }

    public List<ChapterDrop> getChapterDrops() {
        return chapterDrops;
    }

    public boolean isChapterDrop() {
        return chapterDrops != null;
    }

    public Obtainment getObtainment() {
        return obtainment;
    }

    public boolean hasObtainment() {
        return obtainment != null;
    }

    public Values getValues() {
        return values;
    }

    public boolean hasValues() {
        return values != null;
    }
}
