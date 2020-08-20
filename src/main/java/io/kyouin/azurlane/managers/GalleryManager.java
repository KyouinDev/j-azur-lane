package io.kyouin.azurlane.managers;

import io.kyouin.azurlane.containers.galleries.Gallery;
import io.kyouin.azurlane.core.AzurConstants;
import io.kyouin.azurlane.utils.FileUtils;
import io.kyouin.azurlane.utils.HtmlUtils;
import io.kyouin.azurlane.utils.WikiUtils;

import java.util.List;

public class GalleryManager implements IContainerManager<Gallery> {

    private final List<Gallery> galleries = FileUtils.loadGalleries();

    @Override
    public Gallery get(String name) {
        return galleries.stream()
                .filter(gallery -> gallery.getName().equalsIgnoreCase(name))
                .findFirst()
                .orElse(null);
    }

    @Override
    public List<Gallery> getAll() {
        return galleries;
    }

    @Override
    public void update(String name) {
        Gallery gallery = null;

        try {
            gallery = Gallery.fromElement(HtmlUtils.getBody(AzurConstants.WIKI_SHIP_GALLERY.replace("{ship}", name.replaceAll(" ", "_"))));
        } catch (Exception e) {
            System.out.println("Failed: " + name);
            e.printStackTrace();
        }

        if (gallery == null) return;

        Gallery finalGallery = gallery;

        galleries.stream().filter(s -> s.getName().equals(finalGallery.getName())).findFirst().ifPresent(galleries::remove);
        galleries.add(finalGallery);
    }

    @Override
    public void updateAll() {
        WikiUtils.getShipNames().forEach(this::update);
        FileUtils.saveGalleries(galleries);
    }
}
