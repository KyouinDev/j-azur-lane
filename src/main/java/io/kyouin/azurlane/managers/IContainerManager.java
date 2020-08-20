package io.kyouin.azurlane.managers;

import java.util.List;

public interface IContainerManager<T> {

    T get(String name);

    List<T> getAll();

    void update(String url);

    void updateAll();
}
