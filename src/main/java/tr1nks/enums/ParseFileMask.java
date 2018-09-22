package tr1nks.enums;


public enum ParseFileMask {
    SURNAME("Фамилия"),
    NAME("Имя"),
    PATRONYMIC("Отчество"),
    CODE("Код"),
    GROUP_OR_CATHEDRA("Группа"),
    BUDGET_OR_RATE("Бюджет");

    private String value;

    ParseFileMask(String value) {
        this.value = value;
    }

    public static ParseFileMask[] valuesW() {
        return values();
    }

    public String getValue() {
        return value;
    }
}
