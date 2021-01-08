package io.kyouin.azurlane.containers.galleries;

import org.jsoup.nodes.Element;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

public class Gallery {

    private final static String SHIP_NAME = "h1#firstHeading";
    private final static String SKIN_DIV = "div.shipskin";

    private final String name;
    private final List<Skin> skins;

    public static Gallery fromElement(Element content) {
        String name = content.selectFirst(SHIP_NAME).text().split("/")[0];
        List<Skin> skins = content.select(SKIN_DIV).stream()
                .map(Skin::fromElement)
                .filter(Objects::nonNull)
                .collect(Collectors.toList());

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
