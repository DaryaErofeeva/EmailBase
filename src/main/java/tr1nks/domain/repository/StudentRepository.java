package tr1nks.domain.repository;


import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import tr1nks.domain.entity.*;

import javax.persistence.criteria.*;
import java.util.List;
import java.util.Objects;

public interface StudentRepository extends JpaRepository<StudentEntity, Long>, JpaSpecificationExecutor<StudentEntity> {

    StudentEntity getFirstByLogin(String login);

    StudentEntity getFirstByCode(String code);

    List<StudentEntity> findAllByGroupEntityFacultyEntityNameAndGroupEntityYearAndGroupEntity(String facultyName, int year, GroupEntity groupEntity);

    List<StudentEntity> findAllByGroupEntityFacultyEntityNameAndGroupEntityYear(String facultyName, int year);

    List<StudentEntity> findAllByGroupEntityFacultyEntityNameAndGroupEntity(String facultyName, GroupEntity groupEntity);

    List<StudentEntity> findAllByGroupEntityYearAndGroupEntity(int year, GroupEntity groupEntity);

    List<StudentEntity> findAllByGroupEntityFacultyEntityName(String facultyName);

    List<StudentEntity> findAllByGroupEntityYear(int year);

    List<StudentEntity> findAllByGroupEntity(GroupEntity groupEntity);

    StudentEntity findFirstByNameAndSurnameAndGroupEntity(String name, String surname, GroupEntity groupEntity);

    class StudentSpecifications {
        public static Specification<StudentEntity> hasIdIn(List<Long> ids) {
            return Specification.where((root, query, criteriaBuilder) -> root.get(StudentEntity_.id).in(ids));
        }

        public static Specification<StudentEntity> hasFacultyIdIn(List<Integer> ids) {
            return Specification.where((root, query, criteriaBuilder) -> {
                Join<StudentEntity, GroupEntity> studentGroupJoin = root.join(StudentEntity_.groupEntity);
                Join<GroupEntity, FacultyEntity> groupFacultyJoin = studentGroupJoin.join(GroupEntity_.facultyEntity);
                final Path<Integer> facultyId = groupFacultyJoin.get(FacultyEntity_.facultyId);
                return facultyId.in(ids);
            });
        }

        public static Specification<StudentEntity> hasGroupIdIn(List<Long> ids) {
            return Specification.where((root, query, criteriaBuilder) -> {
                Join<StudentEntity, GroupEntity> studentGroupJoin = root.join(StudentEntity_.groupEntity);
                final Path<Long> groupId = studentGroupJoin.get(GroupEntity_.id);
                return groupId.in(ids);
            });
        }

        public static Specification<StudentEntity> hasYearIn(List<Integer> years) {
            return Specification.where((root, query, criteriaBuilder) -> {
                Join<StudentEntity, GroupEntity> studentGroupJoin = root.join(StudentEntity_.groupEntity);
                final Path<Integer> year = studentGroupJoin.get(GroupEntity_.year);
                return year.in(years);
            });
        }

        public static Specification<StudentEntity> getByFacultyName(String facultyName) {
            return Specification.where((Specification<StudentEntity>) (root, criteriaQuery, criteriaBuilder) -> {
                Join<StudentEntity, GroupEntity> studentGroupJoin = root.join(StudentEntity_.groupEntity);
                Join<GroupEntity, FacultyEntity> groupFacultyJoin = studentGroupJoin.join(GroupEntity_.facultyEntity);
                return root.in(criteriaQuery.where(criteriaBuilder.equal(groupFacultyJoin.get(FacultyEntity_.name), facultyName)));
            });
        }

        public static Specification<StudentEntity> getByGroupEntity(GroupEntity groupEntity) {
            return Specification.where((Specification<StudentEntity>) (root, criteriaQuery, criteriaBuilder) ->
                    root.in(criteriaQuery.where(criteriaBuilder.equal(root.get(StudentEntity_.groupEntity), groupEntity))));
        }

        public static Specification<StudentEntity> appendSpecification(Specification<StudentEntity> base, Specification<StudentEntity> appended) {
            if (Objects.isNull(base)) {
                return appended;
            } else {
                base.and(appended);
                return base;
            }
        }
    }
}
