package io.kyouin.azurlane.utils;

import io.kyouin.azurlane.core.AzurConstants;
import org.jsoup.select.Elements;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public final class WikiUtils {

    private WikiUtils() {
        //nothing
    }

    public static List<String> getShipNames() {
        Elements tables = HtmlUtils.getBody(AzurConstants.WIKI_SHIP_LIST).select("table.wikitable");
        List<String> names = new ArrayList<>();

        tables.stream().limit(3).forEachOrdered(table -> {
            Elements rows = table.select("tbody > tr");

            names.addAll(rows.stream().skip(1)
                    .filter(row -> !row.select("td").get(2).text().equals("Unreleased"))
                    .map(row -> row.select("td > a").get(1).text().trim())
                    .collect(Collectors.toList()));
        });

        return names;
    }
}
