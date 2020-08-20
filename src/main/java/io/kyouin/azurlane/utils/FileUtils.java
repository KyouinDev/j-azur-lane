package io.kyouin.azurlane.utils;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import io.kyouin.azurlane.entities.Ship;

import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

public final class FileUtils {

    private static final File parentFile = new File("azurlane");

    private static final File shipsFile = new File(parentFile, "ships.json");

    private static final Type shipsType = new TypeToken<List<Ship>>(){}.getType();

    public static void init() {
        if (parentFile.mkdirs()) {
            System.out.println("Created directory azurlane");
        }

        checkFile(shipsFile, "[]");
    }

    private static void checkFile(File file, String empty) {
        try {
            if (file.createNewFile()) {
                System.out.println("Created file " + file.getName());

                try (FileWriter fw = new FileWriter(file)) {
                    fw.write(empty);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static List<Ship> loadShips() {
        List<Ship> ships = new ArrayList<>();

        try (FileReader fr = new FileReader(shipsFile)) {
            ships = new Gson().fromJson(fr, shipsType);

            System.out.println("Loaded " + ships.size() + " ships from file");
        } catch (IOException e) {
            e.printStackTrace();
        }

        return ships;
    }

    public static void saveShips(List<Ship> ships) {
        Gson gson = new GsonBuilder().setPrettyPrinting().create();

        try (FileWriter fw = new FileWriter(shipsFile)) {
            fw.write(gson.toJson(ships, shipsType));

            System.out.println("Saved " + ships.size() + " ships to file");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
