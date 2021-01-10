package io.kyouin.azurlane.managers;

import com.google.gson.reflect.TypeToken;
import io.kyouin.azurlane.containers.galleries.Gallery;
import io.kyouin.azurlane.core.AzurConstants;
import io.kyouin.azurlane.utils.FileUtils;
import io.kyouin.azurlane.utils.HtmlUtils;
import io.kyouin.azurlane.utils.WikiUtils;

import java.io.File;
import java.lang.reflect.Type;
import java.util.List;

public class GalleryManager implements IContainerManager<Gallery> {

    private static final File GALLERIES_FILE = new File(FileUtils.PARENT_FILE, "galleries.json");
    private static final Type GALLERIES_TYPE = new TypeToken<List<Gallery>>(){}.getType();

    private final List<Gallery> galleryList;

    public GalleryManager() {
        FileUtils.initFile(GALLERIES_FILE);
        galleryList = load();
    }

    @Override
    public Gallery get(String name) {
        return galleryList.stream()
                .filter(gallery -> gallery.getName().equalsIgnoreCase(name))
                .findFirst()
                .orElse(null);
    }

    @Override
    public List<Gallery> getAll() {
        return galleryList;
    }

    @Override
    public boolean update(String name) {
        Gallery gallery = null;

        try {
            gallery = Gallery.fromElement(HtmlUtils.getBody(AzurConstants.WIKI_SHIP_GALLERY.replace("{ship}",
                    name.replaceAll(" ", "_"))));
        } catch (Exception e) {
            System.out.println("Failed to update: " + name);
            e.printStackTrace(); //todo logger
        }

        if (gallery == null) return false;

        Gallery finalGallery = gallery;

        galleryList.stream()
                .filter(s -> s.getName().equals(finalGallery.getName()))
                .findFirst()
                .ifPresent(galleryList::remove);
        galleryList.add(finalGallery);

        return true;
    }

    @Override
    public void updateAll() {
        WikiUtils.getShipNames().forEach(this::update);
        save();
    }

    @Override
    public List<Gallery> load() {
        return FileUtils.load(GALLERIES_FILE, GALLERIES_TYPE);
    }

    @Override
    public void save() {
        FileUtils.save(GALLERIES_FILE, GALLERIES_TYPE, galleryList);
    }
}
