package tr1nks.domain.entity;

import javax.annotation.Generated;
import javax.persistence.metamodel.ListAttribute;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value = "org.hibernate.jpamodelgen.JPAMetaModelEntityProcessor")
@StaticMetamodel(GroupEntity.class)
public abstract class GroupEntity_ extends tr1nks.domain.entity.MyEntity_ {

	public static volatile SingularAttribute<GroupEntity, SpecializationEntity> specializationEntity;
	public static volatile SingularAttribute<GroupEntity, StudyLevelEntity> studyLevelEntity;
	public static volatile SingularAttribute<GroupEntity, FacultyEntity> facultyEntity;
	public static volatile SingularAttribute<GroupEntity, Integer> year;
	public static volatile SingularAttribute<GroupEntity, Integer> num;
	public static volatile ListAttribute<GroupEntity, StudentEntity> students;

}

