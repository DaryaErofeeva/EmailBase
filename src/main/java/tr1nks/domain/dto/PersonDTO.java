package tr1nks.domain.dto;

public abstract class PersonDTO extends MyDTO {
    private String surname;
    private String name;
    private String patronymic;
    private String code;
    private String login;
    private String initPassword;
    private boolean imagine;
    private boolean office;
    //non entity fields
    private boolean checked;
    private int errorFieldIndex = -1;
    private String errorMessage;


    public PersonDTO(long id, String surname, String name, String patronymic, String code, String login,
                     String initPassword, boolean imagine, boolean office, boolean checked, int errorFieldIndex,
                     String errorMessage) {
        super(id);
        this.surname = surname;
        this.name = name;
        this.patronymic = patronymic;
        this.code = code;
        this.login = login;
        this.initPassword = initPassword;
        this.imagine = imagine;
        this.office = office;
        this.checked = checked;
        this.errorFieldIndex = errorFieldIndex;
        this.errorMessage = errorMessage;
    }

    public PersonDTO(String surname, String name, String patronymic, String code, String login,
                     String initPassword, boolean imagine, boolean office, boolean checked, int errorFieldIndex,
                     String errorMessage) {
        this.surname = surname;
        this.name = name;
        this.patronymic = patronymic;
        this.code = code;
        this.login = login;
        this.initPassword = initPassword;
        this.imagine = imagine;
        this.office = office;
        this.checked = checked;
        this.errorFieldIndex = errorFieldIndex;
        this.errorMessage = errorMessage;
    }
    public PersonDTO(String surname, String name, String patronymic, String code, String login,
                     String initPassword, boolean imagine, boolean office) {
        this.surname = surname;
        this.name = name;
        this.patronymic = patronymic;
        this.code = code;
        this.login = login;
        this.initPassword = initPassword;
        this.imagine = imagine;
        this.office = office;
    }
    public PersonDTO(long id,String surname, String name, String patronymic, String code, String login,
                     String initPassword, boolean imagine, boolean office) {
        super(id);
        this.surname = surname;
        this.name = name;
        this.patronymic = patronymic;
        this.code = code;
        this.login = login;
        this.initPassword = initPassword;
        this.imagine = imagine;
        this.office = office;
    }

    public PersonDTO() {
    }

    public String getSurname() {
        return surname;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPatronymic() {
        return patronymic;
    }

    public void setPatronymic(String patronymic) {
        this.patronymic = patronymic;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public String getInitPassword() {
        return initPassword;
    }

    public void setInitPassword(String initPassword) {
        this.initPassword = initPassword;
    }

    public boolean isImagine() {
        return imagine;
    }

    public void setImagine(boolean imagine) {
        this.imagine = imagine;
    }

    public boolean isOffice() {
        return office;
    }

    public void setOffice(boolean office) {
        this.office = office;
    }

    public boolean isChecked() {
        return checked;
    }

    public void setChecked(boolean checked) {
        this.checked = checked;
    }

    public int getErrorFieldIndex() {
        return errorFieldIndex;
    }

    public void setErrorFieldIndex(int errorFieldIndex) {
        this.errorFieldIndex = errorFieldIndex;
    }

    public String getErrorMessage() {
        return errorMessage;
    }

    public void setErrorMessage(String errorMessage) {
        this.errorMessage = errorMessage;
    }
}
