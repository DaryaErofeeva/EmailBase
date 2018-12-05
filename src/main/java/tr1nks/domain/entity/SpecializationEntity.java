package tr1nks.domain.entity;


import javax.persistence.*;

@Entity
@Table(name = "specialization")
public class SpecializationEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id", nullable = false)
    private long id;

    @Basic
    @Column(name = "specialization_id", nullable = false, length = 3)
    private int specializationId;

    @Basic
    @Column(name = "name", nullable = false, length = 90)
    private String name;

    @Basic
    @ManyToOne
    @JoinColumn(name = "speciality_id", referencedColumnName = "id", nullable = false)
    private SpecialityEntity specialityEntity;

    public SpecializationEntity(long id, int specializationId, String name, SpecialityEntity specialityEntity) {
        this.id = id;
        this.specializationId = specializationId;
        this.name = name;
        this.specialityEntity = specialityEntity;
    }

    public SpecializationEntity() {
    }

    public int getSpecializationId() {
        return specializationId;
    }

    public void setSpecializationId(int specializationId) {
        this.specializationId = specializationId;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public SpecialityEntity getSpecialityEntity() {
        return specialityEntity;
    }

    public void setSpecialityEntity(SpecialityEntity specialityEntity) {
        this.specialityEntity = specialityEntity;
    }
}
