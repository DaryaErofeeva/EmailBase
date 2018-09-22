package tr1nks.model.person.filter;

import java.util.ArrayList;
import java.util.List;

public class FilterSetModel {
    private List<FilterModel> filterModelList = new ArrayList<>();

    public FilterSetModel(List<FilterModel> filterLists) {
        this.filterModelList = filterLists;
    }

    public FilterSetModel() {
    }

    public List<FilterModel> getFilterModelList() {
        return filterModelList;
    }

    public void setFilterModelList(List<FilterModel> filterModelList) {
        this.filterModelList = filterModelList;
    }

    public void addFilterModel(FilterModel filterListFilterPair) {
        this.filterModelList.add(filterListFilterPair);
    }
}
