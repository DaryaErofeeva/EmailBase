package tr1nks.domain.dto;

import tr1nks.domain.entity.GroupEntity;
import tr1nks.domain.entity.StudentEntity;

public class StudentDTO extends PersonDTO {
    private GroupEntity groupEntity;
    private boolean budget;

    public StudentDTO(StudentEntity entity) {
        super(entity);
        this.groupEntity = entity.getGroupEntity();
        this.budget = entity.isBudget();
    }

    public StudentDTO() {
    }

    @Override
    public StudentEntity toEntity() {
        StudentEntity entity = new StudentEntity();
        fillEntity(entity);
        return entity;
    }


    void fillEntity(StudentEntity entity) {
        super.fillEntity(entity);
        entity.setGroupEntity(this.groupEntity);
        entity.setBudget(this.budget);
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
