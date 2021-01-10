package io.kyouin.azurlane.managers;

import com.google.gson.reflect.TypeToken;
import io.kyouin.azurlane.containers.quotes.Quotes;
import io.kyouin.azurlane.core.AzurConstants;
import io.kyouin.azurlane.utils.FileUtils;
import io.kyouin.azurlane.utils.HtmlUtils;
import io.kyouin.azurlane.utils.WikiUtils;

import java.io.File;
import java.lang.reflect.Type;
import java.util.List;

public class QuotesManager implements IContainerManager<Quotes> {

    private static final File QUOTES_FILE = new File(FileUtils.PARENT_FILE, "quotes.json");
    private static final Type QUOTES_TYPE = new TypeToken<List<Quotes>>(){}.getType();

    private final List<Quotes> quotesList;

    public QuotesManager() {
        FileUtils.initFile(QUOTES_FILE);
        quotesList = load();
    }

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
    public boolean update(String name) {
        Quotes quotes = null;

        try {
            quotes = Quotes.fromElement(HtmlUtils.getBody(AzurConstants.WIKI_SHIP_QUOTES.replace("{ship}",
                    name.replaceAll(" ", "_"))));
        } catch (Exception e) {
            System.out.println("Failed to update: " + name);
            e.printStackTrace(); //todo logger
        }

        if (quotes == null) return false;

        Quotes finalQuotes = quotes;

        quotesList.stream()
                .filter(s -> s.getName().equals(finalQuotes.getName()))
                .findFirst()
                .ifPresent(quotesList::remove);
        quotesList.add(quotes);

        return true;
    }

    @Override
    public void updateAll() {
        WikiUtils.getShipNames().forEach(this::update);
        save();
    }

    @Override
    public List<Quotes> load() {
        return FileUtils.load(QUOTES_FILE, QUOTES_TYPE);
    }

    @Override
    public void save() {
        FileUtils.save(QUOTES_FILE, QUOTES_TYPE, quotesList);
    }
}
