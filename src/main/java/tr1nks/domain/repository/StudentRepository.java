package tr1nks.domain.repository;


import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import tr1nks.domain.entity.*;

import javax.persistence.criteria.Join;
import javax.persistence.criteria.Path;
import java.util.List;

public interface StudentRepository extends JpaRepository<StudentEntity, Long>, JpaSpecificationExecutor<StudentEntity> {

    StudentEntity getFirstByLogin(String login);

    StudentEntity getFirstByCode(String code);

    List<StudentEntity> findAllByGroupEntityFacultyEntityNameOrGroupEntityYear(String facultyName, Integer year);

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
    }
}
