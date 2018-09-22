package tr1nks.model.person.filter;

public class FilterItem <I extends Number> {
    private I id;
    private boolean checked = false;
    private String name;
    private String abbr;

    public FilterItem(I id, String name, String abbr) {
        this.id = id;
        this.name = name;
        this.abbr = abbr;
    }

    public FilterItem(I id, String name) {
        this.id = id;
        this.name = name;
        this.abbr = name;
    }

    public FilterItem() {
    }

    public I getId() {
        return id;
    }

    public void setId(I id) {
        this.id = id;
    }

    public boolean isChecked() {
        return checked;
    }

    public boolean getChecked() {
        return checked;
    }

    public void setChecked(boolean checked) {
        this.checked = checked;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAbbr() {
        return abbr;
    }

    public void setAbbr(String abbr) {
        this.abbr = abbr;
    }
}
