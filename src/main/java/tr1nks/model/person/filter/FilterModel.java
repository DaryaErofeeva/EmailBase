package tr1nks.model.person.filter;

import tr1nks.model.PageModel;

import java.util.ArrayList;
import java.util.List;

public class FilterModel<I extends Number> implements PageModel {
    private int id;
    private String title;
    private List<FilterItem<I>> itemsList;

    public FilterModel(String title, int id, List<FilterItem<I>> itemsList) {
        this.title = title;
        this.id = id;
        this.itemsList = itemsList;
    }

    public FilterModel() {
        itemsList = new ArrayList<>();
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public List<FilterItem<I>> getItemsList() {
        return itemsList;
    }

    public void setItemsList(List<FilterItem<I>> itemsList) {
        this.itemsList = itemsList;
    }
}
