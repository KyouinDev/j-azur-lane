package io.kyouin.azurlane.core;

import io.kyouin.azurlane.entities.Ship;
import io.kyouin.azurlane.utils.FileUtils;
import io.kyouin.azurlane.utils.HtmlUtils;
import org.jsoup.select.Elements;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

public class AzurLane {

    private static AzurLane instance;

    private List<Ship> ships;

    private AzurLane() {
        FileUtils.init();

        ships = new ArrayList<>();
    }

    public static AzurLane getInstance() {
        if (instance == null) instance = new AzurLane();

        return instance;
    }

    public List<Ship> getShips() {
        return ships;
    }

    public void loadShips() {
        List<Ship> tempShips = FileUtils.loadShips();

        if (tempShips.size() == 0) {
            updateShips();
        } else {
            ships = tempShips;
        }
    }

    private Ship loadShip(String url) {
        Ship ship = null;

        try {
            ship = Ship.fromElement(HtmlUtils.getBody(url).selectFirst("div.mw-parser-output"));
        } catch (Exception e) {
            System.out.println("Ship failed to load: " + url.replace(AzurConstants.WIKI_URL + "/", ""));
            e.printStackTrace();
        }

        return ship;
    }

    public void updateShips() {
        Elements tables = HtmlUtils.getBody(AzurConstants.WIKI_LIST_URL).select("table.wikitable");
        List<String> urls = new ArrayList<>();

        tables.stream().limit(3).forEachOrdered(table -> {
            Elements rows = table.select("tbody > tr");

            urls.addAll(rows.stream().skip(1)
                    .filter(row -> !row.select("td").get(2).text().equals("Unreleased"))
                    .map(row -> row.select("td > a").get(1).attr("abs:href"))
                    .collect(Collectors.toList()));
        });

        List<Ship> tempShips = urls.stream()
                .map(this::loadShip)
                .collect(Collectors.toList());

        saveShips(tempShips);
    }

    public void updateShip(String name) {
        Ship ship = loadShip(AzurConstants.WIKI_URL + "/" + name);

        if (ship == null) return;

        ships.stream().filter(s -> s.getName().equals(name)).findFirst().ifPresent(check -> ships.remove(check));
        ships.add(ship);

        saveShips(ships);
    }

    private void saveShips(List<Ship> tempShips) {
        tempShips = tempShips.stream()
                .filter(Objects::nonNull)
                .sorted(Comparator.comparing(ship -> ship.getInfo().getId()))
                .collect(Collectors.toList());

        FileUtils.saveShips(tempShips);

        ships = tempShips;
    }
}
