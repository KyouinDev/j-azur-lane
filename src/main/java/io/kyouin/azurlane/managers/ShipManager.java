package io.kyouin.azurlane.managers;

import com.google.gson.reflect.TypeToken;
import io.kyouin.azurlane.containers.ships.Ship;
import io.kyouin.azurlane.core.AzurConstants;
import io.kyouin.azurlane.utils.FileUtils;
import io.kyouin.azurlane.utils.HtmlUtils;
import io.kyouin.azurlane.utils.WikiUtils;

import java.io.File;
import java.lang.reflect.Type;
import java.util.List;

public class ShipManager implements IContainerManager<Ship> {

    private static final File SHIPS_FILE = new File(FileUtils.PARENT_FILE, "ships.json");
    private static final Type SHIPS_TYPE = new TypeToken<List<Ship>>(){}.getType();

    private final List<Ship> shipList;

    public ShipManager() {
        FileUtils.initFile(SHIPS_FILE);
        shipList = load();
    }

    @Override
    public Ship get(String name) {
        return shipList.stream()
                .filter(ship -> ship.getName().equalsIgnoreCase(name))
                .findFirst()
                .orElse(null);
    }

    @Override
    public List<Ship> getAll() {
        return shipList;
    }

    @Override
    public boolean update(String name) {
        Ship ship = null;

        try {
            ship = Ship.fromElement(HtmlUtils.getBody(AzurConstants.WIKI_SHIP.replace("{ship}",
                    name.replaceAll(" ", "_"))));
        } catch (Exception e) {
            System.out.println("Failed to update: " + name);
            e.printStackTrace(); //todo logger
        }

        if (ship == null) return false;

        Ship finalShip = ship;

        shipList.stream()
                .filter(s -> s.getName().equals(finalShip.getName()))
                .findFirst()
                .ifPresent(shipList::remove);
        shipList.add(ship);

        return true;
    }

    @Override
    public void updateAll() {
        WikiUtils.getShipNames().forEach(this::update);
        save();
    }

    @Override
    public List<Ship> load() {
        return FileUtils.load(SHIPS_FILE, SHIPS_TYPE);
    }

    @Override
    public void save() {
        FileUtils.save(SHIPS_FILE, SHIPS_TYPE, shipList);
    }
}
