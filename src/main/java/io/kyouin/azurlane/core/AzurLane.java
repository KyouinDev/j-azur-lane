package io.kyouin.azurlane.core;

import io.kyouin.azurlane.managers.GalleryManager;
import io.kyouin.azurlane.managers.ShipManager;
import io.kyouin.azurlane.utils.FileUtils;

public final class AzurLane {

    private final static AzurLane instance = new AzurLane();

    private final ShipManager shipManager;

    private final GalleryManager galleryManager;

    private AzurLane() {
        FileUtils.init();

        shipManager = new ShipManager();
        galleryManager = new GalleryManager();
    }

    public static AzurLane getInstance() {
        return instance;
    }

    public ShipManager getShipManager() {
        return shipManager;
    }

    public GalleryManager getGalleryManager() {
        return galleryManager;
    }

    public void fullUpdateShip(String name) {
        shipManager.update(name);
        galleryManager.update(name);
    }

    public void saveToFiles() {
        FileUtils.saveShips(shipManager.getAll());
        FileUtils.saveGalleries(galleryManager.getAll());
    }
}
