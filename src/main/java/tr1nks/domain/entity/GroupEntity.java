package tr1nks.domain.entity;

import javax.persistence.*;
import java.util.List;

/**
 * 8.04.122.10.18.2 <br>
 * (Study level).(faculty id).(speciality id).(specialization id).(year).(num)
 */
@Entity
@Table(name = "academ_group")
public class GroupEntity extends MyEntity {
    @Basic
    @ManyToOne
    @JoinColumn(name = "level_id", referencedColumnName = "id", nullable = false)
    private StudyLevelEntity studyLevelEntity;
    @Basic
    @ManyToOne
    @JoinColumn(name = "faculty_id", referencedColumnName = "id", nullable = false)
    private FacultyEntity facultyEntity;
    @Basic
    @ManyToOne
    @JoinColumn(name = "specialization_id", referencedColumnName = "id", nullable = false)
    private SpecializationEntity specializationEntity;
    @Basic
    @Column(name = "year", nullable = false, length = 3)
    private int year;
    @Basic
    @Column(name = "num", nullable = false, length = 3)
    private int num;
    @OneToMany(mappedBy = "groupEntity")
    private List<StudentEntity> students;

    public GroupEntity(long id, StudyLevelEntity studyLevelEntity, FacultyEntity facultyEntity, SpecializationEntity specializationEntity, Integer year, Integer num) {
        setId(id);
        this.studyLevelEntity = studyLevelEntity;
        this.facultyEntity = facultyEntity;
        this.specializationEntity = specializationEntity;
        this.year = year;
        this.num = num;
    }

    public GroupEntity() {
    }

    public StudyLevelEntity getStudyLevelEntity() {
        return studyLevelEntity;
    }

    public void setStudyLevelEntity(StudyLevelEntity studyLevelEntity) {
        this.studyLevelEntity = studyLevelEntity;
    }

    public FacultyEntity getFacultyEntity() {
        return facultyEntity;
    }

    public void setFacultyEntity(FacultyEntity facultyEntity) {
        this.facultyEntity = facultyEntity;
    }

    public SpecializationEntity getSpecializationEntity() {
        return specializationEntity;
    }

    public void setSpecializationEntity(SpecializationEntity specializationEntity) {
        this.specializationEntity = specializationEntity;
    }

    public int getYear() {
        return year;
    }

    public void setYear(int year) {
        this.year = year;
    }

    public int getNum() {
        return num;
    }

    public void setNum(int num) {
        this.num = num;
    }

    public List<StudentEntity> getStudents() {
        return students;
    }

    public void setStudents(List<StudentEntity> students) {
        this.students = students;
    }

    public String getCipher() {
        return this.studyLevelEntity.getLevelId() + "." +
                facultyEntity.getFacultyId() + "." +
                specializationEntity.getSpecialityEntity().getSpecialityId() + "." +
                specializationEntity.getSpecializationId() + "." +
                year + "." +
                num;
    }
}
