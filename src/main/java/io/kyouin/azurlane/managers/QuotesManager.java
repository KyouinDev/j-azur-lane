package io.kyouin.azurlane.managers;

import io.kyouin.azurlane.containers.quotes.Quotes;
import io.kyouin.azurlane.core.AzurConstants;
import io.kyouin.azurlane.utils.FileUtils;
import io.kyouin.azurlane.utils.HtmlUtils;
import io.kyouin.azurlane.utils.WikiUtils;

import java.util.List;

public class QuotesManager implements IContainerManager<Quotes> {

    private final List<Quotes> quotesList = FileUtils.loadQuotes();

    @Override
    public Quotes get(String name) {
        return quotesList.stream()
                .filter(quotes -> quotes.getName().equalsIgnoreCase(name))
                .findFirst()
                .orElse(null);
    }

    @Override
    public List<Quotes> getAll() {
        return quotesList;
    }

    @Override
    public void update(String name) {
        Quotes quotes = null;

        try {
            quotes = Quotes.fromElement(HtmlUtils.getBody(AzurConstants.WIKI_SHIP_QUOTES.replace("{ship}", name.replaceAll(" ", "_"))));
        } catch (Exception e) {
            System.out.println("Failed: " + name);
            e.printStackTrace();
        }

        if (quotes == null) return;

        Quotes finalQuotes = quotes;

        quotesList.stream()
                .filter(s -> s.getName().equals(finalQuotes.getName()))
                .findFirst()
                .ifPresent(quotesList::remove);

        quotesList.add(quotes);
    }

    @Override
    public void updateAll() {
        WikiUtils.getShipNames().forEach(this::update);
        FileUtils.saveQuotes(quotesList);
    }
}
