package tr1nks.domain.entity;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@SequenceGenerator(name = "id_seq", sequenceName = "id_seq", allocationSize = 1)
@Inheritance(strategy = InheritanceType.TABLE_PER_CLASS)
public abstract class MyEntity implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "id_seq")
    @Column(name = "id", nullable = false)
    private long id;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }
}