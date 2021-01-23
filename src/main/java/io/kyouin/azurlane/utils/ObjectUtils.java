package io.kyouin.azurlane.utils;

public final class ObjectUtils {

    private ObjectUtils() {
        //nothing
    }

    public static int parseInt(String toParse, int defaultValue) {
        try {
            return Integer.parseInt(toParse);
        } catch (NumberFormatException e) {
            return defaultValue;
        }
    }

    public static String getNameWithoutAccents(String name) {
        return name.replaceAll("[âäàåã]", "a")
                .replaceAll("[êëéè]", "e")
                .replaceAll("[ïîì]", "i")
                .replaceAll("[ôöò]", "o")
                .replaceAll("[üù]", "u");
    }

    public static String getSimplifiedName(String name) {
        return name.replaceAll("[()\\-']", "")
                .replaceAll("µ", "muse");
    }
}
