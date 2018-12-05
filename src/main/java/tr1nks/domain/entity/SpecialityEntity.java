package tr1nks.domain.entity;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "speciality")
public class SpecialityEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id", nullable = false)
    private long id;

    @Basic
    @Column(name = "speciality_id", nullable = false, unique = true, length = 3)
    private int specialityId;

    @Basic
    @Column(name = "name", nullable = false, length = 90)
    private String name;

    @OneToMany(mappedBy = "specialityEntity")
    private List<SpecializationEntity> specializationEntities;

    public SpecialityEntity(long id, int specialityId, String name) {
        this.id = id;
        this.specialityId = specialityId;
        this.name = name;
    }

    public SpecialityEntity() {
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public int getSpecialityId() {
        return specialityId;
    }

    public void setSpecialityId(int specialityId) {
        this.specialityId = specialityId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<SpecializationEntity> getSpecializationEntities() {
        return specializationEntities;
    }

    public void setSpecializationEntities(List<SpecializationEntity> specializationEntities) {
        this.specializationEntities = specializationEntities;
    }
}
