package tr1nks.domain.entity;

import javax.persistence.*;

@Entity
@Inheritance(strategy = InheritanceType.TABLE_PER_CLASS)
public abstract class PersonEntity extends MyEntity {
    @Basic
    @Column(name = "surname", nullable = false, length = 50)
    private String surname;
    @Basic
    @Column(name = "name", nullable = false, length = 50)
    private String name;
    @Basic
    @Column(name = "patronymic", length = 50)
    private String patronymic;
    @Basic
    @Column(name = "code", unique = true, nullable = false, length = 100)
    private String code;
    @Basic
    @Column(name = "login", unique = true, length = 100)
    private String login;
    @Basic
    @Column(name = "init_password", length = 15)
    private String initPassword;
    @Basic
    @Column(name = "imagine", nullable = false, columnDefinition = "bit(1) default false")
    private boolean imagine;
    @Basic
    @Column(name = "office", nullable = false, columnDefinition = "bit(1) default false")
    private boolean office;

    public PersonEntity(String surname, String name, String patronymic, String code) {
        this.surname = surname;
        this.name = name;
        this.patronymic = patronymic;
        this.code = code;
    }

    protected PersonEntity() {

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
}
