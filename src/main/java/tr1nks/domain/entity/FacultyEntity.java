package tr1nks.domain.entity;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "faculty")
public class FacultyEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id", nullable = false)
    private long id;

    @Basic
    @Column(name = "faculty_id", unique = true, nullable = false, length = 3)
    private int facultyId;

    @Basic
    @Column(name = "name", unique = true, nullable = false, length = 50)
    private String name;

    @Basic
    @Column(name = "abbr", unique = true, nullable = false, length = 5)
    private String abbr;

    @OneToMany(mappedBy = "facultyEntity")
    private List<GroupEntity> groupEntities;

    public FacultyEntity(long id, int facultyId, String name, String abbr) {
        this.id = id;
        this.facultyId = facultyId;
        this.name = name;
        this.abbr = abbr;
    }

    public FacultyEntity() {
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public int getFacultyId() {
        return facultyId;
    }

    public void setFacultyId(int facultyId) {
        this.facultyId = facultyId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAbbr() {
        return abbr;
    }

    public void setAbbr(String abbr) {
        this.abbr = abbr;
    }

    public List<GroupEntity> getGroupEntities() {
        return groupEntities;
    }

    public void setGroupEntities(List<GroupEntity> groupEntities) {
        this.groupEntities = groupEntities;
    }
}
