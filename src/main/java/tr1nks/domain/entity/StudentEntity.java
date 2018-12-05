package tr1nks.domain.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;

@Entity
@Table(name = "person")
public class StudentEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id", nullable = false)
    private long id;

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

    @Basic
    @ManyToOne
    @JsonIgnore//todo remove
    @JoinColumn(name = "group_id", referencedColumnName = "id", nullable = false)
    private GroupEntity groupEntity;

    @Basic
    @Column(name = "budget", nullable = false, columnDefinition = "bit(1) default false")
    private boolean budget;

    public StudentEntity(long id, String surname, String name, String patronymic, String code, GroupEntity groupEntity,
                         String login, String initPassword, boolean budget) {
        this.id = id;
        this.surname = surname;
        this.name = name;
        this.patronymic = patronymic;
        this.code = code;
        this.login = login;
        this.initPassword = initPassword;
        this.groupEntity = groupEntity;
        this.budget = budget;
    }

    public StudentEntity(long id, String surname, String name, String patronymic, String code, GroupEntity groupEntity,
                         String login, String initPassword, boolean imagine, boolean office, boolean budget) {
        this.id = id;
        this.surname = surname;
        this.name = name;
        this.patronymic = patronymic;
        this.code = code;
        this.imagine = imagine;
        this.office = office;
        this.login = login;
        this.initPassword = initPassword;
        this.groupEntity = groupEntity;
        this.budget = budget;
    }

    public StudentEntity(String surname, String name, String patronymic, String code, GroupEntity groupEntity) {
        this.surname = surname;
        this.name = name;
        this.patronymic = patronymic;
        this.code = code;
        this.groupEntity = groupEntity;
    }

    public StudentEntity() {
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
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

    public GroupEntity getGroupEntity() {
        return groupEntity;
    }

    public void setGroupEntity(GroupEntity groupEntity) {
        this.groupEntity = groupEntity;
    }

    public boolean isBudget() {
        return budget;
    }

    public void setBudget(boolean budget) {
        this.budget = budget;
    }
}
