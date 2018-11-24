package tr1nks.constants;

public enum StudentField {
    SURNAME("surname"),
    NAME("name"),
    PATRONYMIC("patronymic"),
    CODE("code"),
    LOGIN("login"),
    INIT_PASSWORD("initPassword"),
    IMAGINE("imagine"),
    OFFICE("office"),
    GROUP("group"),
    BUDGET("budget");

    public final String field;

    StudentField(String field) {
        this.field = field;
    }
}
