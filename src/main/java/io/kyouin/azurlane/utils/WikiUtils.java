package io.kyouin.azurlane.utils;

import io.kyouin.azurlane.core.AzurConstants;

import java.util.List;
import java.util.stream.Collectors;

public final class WikiUtils {

    private final static String TABLE_ROWS = "table.wikitable > * tr:gt(0)";
    private final static String FIRST_ROW_CELL = "td:eq(1)";
    private final static String SECOND_ROW_CELL = "td:eq(2)";

    private WikiUtils() {
        //nothing
    }

    public static List<String> getShipNames() {
        return HtmlUtils.getBody(AzurConstants.WIKI_SHIP_LIST).select(TABLE_ROWS).stream()
                .filter(row -> !"Unreleased".equals(row.select(SECOND_ROW_CELL).text()))
                .map(row -> row.select(FIRST_ROW_CELL).text())
                .distinct()
                .collect(Collectors.toList());
    }
}
