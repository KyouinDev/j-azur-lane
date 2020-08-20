package io.kyouin.azurlane.utils;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import io.kyouin.azurlane.containers.galleries.Gallery;
import io.kyouin.azurlane.containers.ships.Ship;

import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

public final class FileUtils {

    private static final Gson gson = new GsonBuilder().setPrettyPrinting().create();

    private static final File parentFile = new File("azurlane");

    private static final File shipsFile = new File(parentFile, "ships.json");
    private static final Type shipsType = new TypeToken<List<Ship>>(){}.getType();

    private static final File galleriesFile = new File(parentFile, "galleries.json");
    private static final Type galleriesType = new TypeToken<List<Gallery>>(){}.getType();

    private FileUtils() {
        //nothing
    }

    public static void init() {
        if (parentFile.mkdirs()) {
            System.out.println("Created directory azurlane");
        }

        checkFile(shipsFile, "[]");
        checkFile(galleriesFile, "[]");
    }

    private static void checkFile(File file, String empty) {
        try {
            if (file.createNewFile()) {
                try (FileWriter fw = new FileWriter(file)) {
                    fw.write(empty);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static <T> List<T> load(File file, Type type) {
        List<T> list = new ArrayList<>();

        try (FileReader fr = new FileReader(file)) {
            list = gson.fromJson(fr, type);
        } catch (IOException e) {
            e.printStackTrace();
        }

        return list;
    }

    private static <T> void save(File file, Type type, List<T> list) {
        try (FileWriter fw = new FileWriter(file)) {
            fw.write(gson.toJson(list, type));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static List<Ship> loadShips() {
        return load(shipsFile, shipsType);
    }

    public static List<Gallery> loadGalleries() {
        return load(galleriesFile, galleriesType);
    }

    public static void saveShips(List<Ship> ships) {
        save(shipsFile, shipsType, ships);
    }

    public static void saveGalleries(List<Gallery> galleries) {
        save(galleriesFile, galleriesType, galleries);
    }
}
