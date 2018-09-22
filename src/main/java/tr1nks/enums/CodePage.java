package tr1nks.enums;

public enum CodePage {
    UTF8("UTF-8"),
    cp1251("Cp1251"),
    cp866("Cp866");
    private String value;

    public static CodePage[] valuesW() {
        return values();
    }

    CodePage(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }
}
