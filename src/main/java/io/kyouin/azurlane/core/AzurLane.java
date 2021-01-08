package io.kyouin.azurlane.core;

import io.kyouin.azurlane.managers.GalleryManager;
import io.kyouin.azurlane.managers.QuotesManager;
import io.kyouin.azurlane.managers.ShipManager;
import io.kyouin.azurlane.utils.FileUtils;

public final class AzurLane {

    private final static AzurLane instance = new AzurLane();

    private final GalleryManager galleryManager;

    private final QuotesManager quotesManager;

    private final ShipManager shipManager;

    private AzurLane() {
        FileUtils.init();

        galleryManager = new GalleryManager();
        quotesManager = new QuotesManager();
        shipManager = new ShipManager();
    }

    public static AzurLane getInstance() {
        return instance;
    }

    public GalleryManager getGalleryManager() {
        return galleryManager;
    }

    public QuotesManager getQuotesManager() {
        return quotesManager;
    }

    public ShipManager getShipManager() {
        return shipManager;
    }

    public boolean fullUpdateShip(String name) {
        boolean gSuccess = galleryManager.update(name);
        boolean qSuccess = quotesManager.update(name);
        boolean sSuccess = shipManager.update(name);

        return gSuccess && qSuccess && sSuccess;
    }

    public void saveToFiles() {
        FileUtils.saveGalleries(galleryManager.getAll());
        FileUtils.saveQuotes(quotesManager.getAll());
        FileUtils.saveShips(shipManager.getAll());
    }
}
