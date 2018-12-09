package tr1nks.domain.entity;

import javax.annotation.Generated;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value = "org.hibernate.jpamodelgen.JPAMetaModelEntityProcessor")
@StaticMetamodel(StudentEntity.class)
public abstract class StudentEntity_ {

    public static volatile SingularAttribute<StudentEntity, Long> id;
    public static volatile SingularAttribute<StudentEntity, String> patronymic;
    public static volatile SingularAttribute<StudentEntity, String> code;
    public static volatile SingularAttribute<StudentEntity, Boolean> imagine;
    public static volatile SingularAttribute<StudentEntity, String> surname;
    public static volatile SingularAttribute<StudentEntity, String> name;
    public static volatile SingularAttribute<StudentEntity, Boolean> office;
    public static volatile SingularAttribute<StudentEntity, String> login;
    public static volatile SingularAttribute<StudentEntity, String> initPassword;
    public static volatile SingularAttribute<StudentEntity, GroupEntity> groupEntity;
    public static volatile SingularAttribute<StudentEntity, Boolean> budget;

}

