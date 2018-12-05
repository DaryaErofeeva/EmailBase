package tr1nks.domain.entity;

import javax.persistence.*;

@Entity
@Table(name = "study_level")
public class StudyLevelEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id", nullable = false)
    private long id;

    @Basic
    @Column(name = "level_id", nullable = false, length = 3)
    private int levelId;

    @Basic
    @Column(name = "name", nullable = false, length = 30)
    private String name;

    public StudyLevelEntity(long id, int levelId, String name) {
        this.id = id;
        this.levelId = levelId;
        this.name = name;
    }

    public StudyLevelEntity() {
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
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
