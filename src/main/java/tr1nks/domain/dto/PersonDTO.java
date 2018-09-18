package tr1nks.domain.dto;

import tr1nks.domain.entity.PersonEntity;

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

    public PersonDTO(PersonEntity entity) {
        super(entity);
        this.surname = entity.getSurname();
        this.name = entity.getName();
        this.patronymic = entity.getPatronymic();
        this.code = entity.getCode();
        this.login = entity.getLogin();
        this.initPassword = entity.getInitPassword();
        this.imagine = entity.isImagine();
        this.office = entity.isOffice();
    }

    public PersonDTO() {
    }

    @Override
    public abstract PersonEntity toEntity();

    void fillEntity(PersonEntity entity) {
        super.fillEntity(entity);
        entity.setSurname(this.surname);
        entity.setName(this.name);
        entity.setPatronymic(this.patronymic);
        entity.setCode(this.code);
        entity.setLogin(this.login);
        entity.setInitPassword(this.initPassword);
        entity.setImagine(this.imagine);
        entity.setOffice(this.office);
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
