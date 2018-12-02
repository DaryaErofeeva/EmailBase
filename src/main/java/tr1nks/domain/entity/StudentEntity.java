package tr1nks.domain.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;

@Entity
@Table(name = "person")
public class StudentEntity extends PersonEntity {
    @Basic
    @ManyToOne
    @JsonIgnore//todo remove
    @JoinColumn(name = "group_id", referencedColumnName = "id", nullable = false)
    private GroupEntity groupEntity;
    @Basic
    @Column(name = "budget", nullable = false, columnDefinition = "bit(1) default false")
    private boolean budget;

    public StudentEntity(long id, String surname, String name, String patronymic, String code, GroupEntity groupEntity, String login, String initPassword, boolean budget) {
        this(surname, name, patronymic, code, groupEntity);
        setId(id);
        setLogin(login);
        setInitPassword(initPassword);
        this.budget = budget;
    }

    public StudentEntity(long id, String surname, String name, String patronymic, String code, GroupEntity groupEntity, String login, String initPassword, boolean imagine, boolean office, boolean budget) {
        super(surname, name, patronymic, code, imagine, office);
        this.groupEntity = groupEntity;
        setId(id);
        setLogin(login);
        setInitPassword(initPassword);
        this.budget = budget;
    }

    public StudentEntity(String surname, String name, String patronymic, String code, GroupEntity groupEntity) {
        super(surname, name, patronymic, code);
        this.groupEntity = groupEntity;
    }

    public StudentEntity() {
        super();
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
