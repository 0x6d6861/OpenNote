package co.heri.dev.opennote.Model;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by mariusmathondo on 17/06/2017.
 */

public class Task {
    private String name;
    private List<Item> itemList = new ArrayList<>();


    public Task(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }


    public List<Item> getItems() {
        return itemList;
    }
}
