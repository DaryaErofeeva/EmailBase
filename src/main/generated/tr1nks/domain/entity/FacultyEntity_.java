package tr1nks.domain.entity;

import javax.annotation.Generated;
import javax.persistence.metamodel.ListAttribute;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value = "org.hibernate.jpamodelgen.JPAMetaModelEntityProcessor")
@StaticMetamodel(FacultyEntity.class)
public abstract class FacultyEntity_ {

	public static volatile SingularAttribute<FacultyEntity, Long> id;
	public static volatile SingularAttribute<FacultyEntity, Integer> facultyId;
	public static volatile SingularAttribute<FacultyEntity, String> name;
	public static volatile SingularAttribute<FacultyEntity, String> abbr;
	public static volatile ListAttribute<FacultyEntity, GroupEntity> groupEntities;

}

