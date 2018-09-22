package tr1nks.enums;

public enum TableStyleClass {
    DEFAULT,
    ACTIVE,
    SUCCESS,
    INFO,
    WARNING,
    DANGER;

    public String toStyleClass() {
        return this.name().toLowerCase();
    }
}
