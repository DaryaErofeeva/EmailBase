package tr1nks.domain.entity;

import javax.annotation.Generated;
import javax.persistence.metamodel.ListAttribute;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value = "org.hibernate.jpamodelgen.JPAMetaModelEntityProcessor")
@StaticMetamodel(SpecialityEntity.class)
public abstract class SpecialityEntity_ extends tr1nks.domain.entity.MyEntity_ {

	public static volatile ListAttribute<SpecialityEntity, SpecializationEntity> specializationEntities;
	public static volatile SingularAttribute<SpecialityEntity, String> name;
	public static volatile SingularAttribute<SpecialityEntity, Integer> specialityId;

}

