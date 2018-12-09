package tr1nks.domain.entity;

import javax.annotation.Generated;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value = "org.hibernate.jpamodelgen.JPAMetaModelEntityProcessor")
@StaticMetamodel(SpecializationEntity.class)
public abstract class SpecializationEntity_ {

    public static volatile SingularAttribute<SpecializationEntity, Long> id;
    public static volatile SingularAttribute<SpecializationEntity, String> name;
    public static volatile SingularAttribute<SpecializationEntity, SpecialityEntity> specialityEntity;
    public static volatile SingularAttribute<SpecializationEntity, Integer> specializationId;

}

