package tr1nks.domain.entity;


import javax.persistence.*;

@Entity
@Table(name = "specialization")
@SequenceGenerator(name = "specialization_seq", sequenceName = "specialization_id_seq", allocationSize = 1)
public class SpecializationEntity extends MyEntity {
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

    public SpecializationEntity(int specializationId, String name, SpecialityEntity specialityEntity) {
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
