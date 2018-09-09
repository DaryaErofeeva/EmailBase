package tr1nks.domain.entity;

import javax.annotation.Generated;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value = "org.hibernate.jpamodelgen.JPAMetaModelEntityProcessor")
@StaticMetamodel(PersonEntity.class)
public abstract class PersonEntity_ extends tr1nks.domain.entity.MyEntity_ {

	public static volatile SingularAttribute<PersonEntity, String> patronymic;
	public static volatile SingularAttribute<PersonEntity, String> code;
	public static volatile SingularAttribute<PersonEntity, Boolean> imagine;
	public static volatile SingularAttribute<PersonEntity, String> surname;
	public static volatile SingularAttribute<PersonEntity, String> name;
	public static volatile SingularAttribute<PersonEntity, Boolean> office;
	public static volatile SingularAttribute<PersonEntity, String> login;
	public static volatile SingularAttribute<PersonEntity, String> initPassword;

}

