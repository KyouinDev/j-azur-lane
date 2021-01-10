package io.kyouin.azurlane.utils;

import com.google.gson.GsonBuilder;

import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

public final class FileUtils {

    public static final File PARENT_FILE = new File("azurlane");

    private FileUtils() {
        //nothing
    }

    public static void init() {
        if (PARENT_FILE.mkdirs()) {
            System.out.println("Created directory azurlane."); //todo logger
        }
    }

    public static void initFile(File file) {
        try {
            if (file.createNewFile()) {
                try (FileWriter fw = new FileWriter(file)) {
                    fw.write("[]");
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static <T> List<T> load(File file, Type type) {
        List<T> list = new ArrayList<>();

        try (FileReader fr = new FileReader(file)) {
            list = new GsonBuilder().setPrettyPrinting().create().fromJson(fr, type);
        } catch (IOException e) {
            e.printStackTrace();
        }

        return list;
    }

    public static <T> void save(File file, Type type, List<T> list) {
        try (FileWriter fw = new FileWriter(file)) {
            fw.write(new GsonBuilder().setPrettyPrinting().create().toJson(list, type));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
