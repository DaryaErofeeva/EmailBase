package tr1nks.domain.entity;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(name = "study_level")
public class StudyLevelEntity extends MyEntity {
    @Basic
    @Column(name = "level_id", nullable = false, length = 3)
    private int levelId;
    @Basic
    @Column(name = "name", nullable = false, length = 30)
    private String name;

    public StudyLevelEntity(int id, String name) {
        this.levelId = id;
        this.name = name;
    }

    public StudyLevelEntity() {
    }

    public int getLevelId() {
        return levelId;
    }

    public void setLevelId(int levelId) {
        this.levelId = levelId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
