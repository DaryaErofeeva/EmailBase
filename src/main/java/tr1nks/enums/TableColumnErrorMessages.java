package tr1nks.enums;

public enum TableColumnErrorMessages {
    SURNAME("Wrong surname string"),
    NAME("Wrong name string"),
    PATRONYMIC("Wrong patronymic string"),
    GROUP_OR_CATHEDRA("Can not find such group or cathedra"),
    CODE("Such code already exists in DB"),
    LOGIN("Such login already exists in DB"),
    INIT_PASSW("Wrong password string"),
    BUDGET_OR_RATE("Wrong value for budget or rate"),
    IMAGINE("Wrong value for imagine"),
    OFFICE("Wrong value for office");

    public final String message;

    TableColumnErrorMessages(String message) {
        this.message = message;
    }
}
