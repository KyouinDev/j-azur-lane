package io.kyouin.azurlane.containers.galleries;

import org.jsoup.nodes.Element;

import java.util.List;
import java.util.stream.Collectors;

public class Gallery {

    private final String name;
    private final List<Skin> skins;

    public static Gallery fromElement(Element content) {
        String name = content.selectFirst("h1#firstHeading").text().trim().replace("/Gallery", "");
        List<Skin> skins = content.select("div.shipskin").stream().map(Skin::fromElement).collect(Collectors.toList());

        return new Gallery(name, skins);
    }

    public Gallery(String name, List<Skin> skins) {
        this.name = name;
        this.skins = skins;
    }

    public String getName() {
        return name;
    }

    public List<Skin> getSkins() {
        return skins;
    }
}
