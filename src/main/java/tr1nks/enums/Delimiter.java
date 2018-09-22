package tr1nks.enums;


public enum Delimiter {
    SEMICOLON(";"),
    COMMA(","),
    DOT("."),
    DASH("-");
    private String value;

    public static Delimiter[] valuesW() {
        return values();
    }

    Delimiter(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }
}
