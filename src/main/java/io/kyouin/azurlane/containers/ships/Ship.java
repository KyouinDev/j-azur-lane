package io.kyouin.azurlane.entities;

import io.kyouin.azurlane.enums.SkillType;
import io.kyouin.azurlane.utils.HtmlUtils;
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
    private final List<Skin> skins;

    public static Ship fromElement(Element content) {
        String name = content.selectFirst("div.azl_box_title").text().trim();
        Info info = Info.fromElement(content);

        List<Stats> statistics = content.selectFirst("table#Statistics").select("div.tabbertab").stream()
                .map(Stats::fromElement)
                .sorted(Comparator.comparing(Stats::getName))
                .collect(Collectors.toList());

        List<Equipment> equipment = content.select("table#Equipment > tbody > tr").stream()
                .skip(2)
                .map(Equipment::fromElement)
                .collect(Collectors.toList());

        LimitBreaks limitBreaks = null;
        DevelopmentLevels developmentLevels = null;

        if (!content.select("table#Limit_breaks").isEmpty()) {
            limitBreaks = LimitBreaks.fromElement(content.selectFirst("table#Limit_breaks > tbody"));
        } else if (!content.select("table#Development_levels").isEmpty()) {
            developmentLevels = DevelopmentLevels.fromElement(content.selectFirst("table#Development_levels > tbody"));
        }

        List<Skill> skills = content.select("table#Skills > tbody > tr").stream()
                .filter(skillRow -> !skillRow.select("th").isEmpty())
                .filter(skillRow -> SkillType.fromStyle(skillRow.selectFirst("th").attr("style")) != null)
                .map(Skill::fromElement)
                .collect(Collectors.toList());

        FleetTech fleetTech = FleetTech.fromElement(content.selectFirst("table#Fleet_technology"));

        List<ChapterDrop> chapterDrops = null;

        if (!content.select("table#Drop").isEmpty()) {
            chapterDrops = content.select("table#Drop > tbody > tr").stream()
                    .skip(2).limit(13)
                    .map(ChapterDrop::fromElement)
                    .collect(Collectors.toList());
        }

        Obtainment obtainment = null;

        if (!content.select("table#Obtainment").isEmpty()) {
            obtainment = Obtainment.fromElement(content.selectFirst("table#Obtainment").selectFirst("td"));
        }

        Values values = Values.fromElement(content.selectFirst("table#Values"));

        Element gallery = HtmlUtils.getBody(content.selectFirst("a:contains(Gallery)").attr("abs:href")).selectFirst("div.mw-parser-output");
        List<Skin> skins = gallery.select("div.shipskin").stream().map(Skin::fromElement).collect(Collectors.toList());

        return new Ship(name, info, statistics, equipment, limitBreaks, developmentLevels, skills, fleetTech, chapterDrops, obtainment, values, skins);
    }

    public Ship(String name, Info info, List<Stats> stats, List<Equipment> equipment, LimitBreaks limitBreaks, DevelopmentLevels developmentLevels, List<Skill> skills, FleetTech fleetTech, List<ChapterDrop> chapterDrops, Obtainment obtainment, Values values, List<Skin> skins) {
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
        this.skins = skins;
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

    public List<Skin> getSkins() {
        return skins;
    }
}
