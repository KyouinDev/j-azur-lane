package io.kyouin.azurlane.managers;

import io.kyouin.azurlane.containers.ships.Ship;
import io.kyouin.azurlane.core.AzurConstants;
import io.kyouin.azurlane.utils.FileUtils;
import io.kyouin.azurlane.utils.HtmlUtils;
import io.kyouin.azurlane.utils.WikiUtils;

import java.util.List;

public class ShipManager implements IContainerManager<Ship> {

    private final List<Ship> ships = FileUtils.loadShips();

    @Override
    public Ship get(String name) {
        return ships.stream()
                .filter(ship -> ship.getName().equalsIgnoreCase(name))
                .findFirst()
                .orElse(null);
    }

    @Override
    public List<Ship> getAll() {
        return ships;
    }

    @Override
    public void update(String name) {
        Ship ship = null;

        try {
            ship = Ship.fromElement(HtmlUtils.getBody(AzurConstants.WIKI_SHIP.replace("{ship}", name.replaceAll(" ", "_"))).selectFirst("div.mw-parser-output"));
        } catch (Exception e) {
            System.out.println("Failed: " + name);
            e.printStackTrace();
        }

        if (ship == null) return;

        Ship finalShip = ship;

        ships.stream().filter(s -> s.getName().equals(finalShip.getName())).findFirst().ifPresent(ships::remove);
        ships.add(ship);
    }

    @Override
    public void updateAll() {
        WikiUtils.getShipNames().forEach(this::update);
        FileUtils.saveShips(ships);
    }
}
