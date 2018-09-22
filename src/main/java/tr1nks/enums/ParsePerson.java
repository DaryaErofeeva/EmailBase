package tr1nks.enums;

public enum ParsePerson {
    STUDENT("Студент");
    private String value;

    public static ParsePerson[] valuesW() {
        return values();
    }

    ParsePerson(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }
}
