package tr1nks.domain.repository;


import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import tr1nks.domain.entity.*;

import javax.persistence.criteria.Join;
import java.util.List;

public interface GroupRepository extends JpaRepository<GroupEntity, Long>, JpaSpecificationExecutor<GroupEntity> {

    GroupEntity getTopByFacultyEntityIdAndSpecializationEntityIdAndStudyLevelEntityIdAndNumAndYear(
            long facultyEntityId, long specializationEntityId, long studyLevelEntityId, int num, int year);

    GroupEntity getTopByFacultyEntityFacultyIdAndSpecializationEntitySpecializationIdAndStudyLevelEntityLevelIdAndNumAndYear(
            int facultyId, int specializationId, int levelId, int num, int year);

    class GroupSpecifications {                                //8.04.122.10.18.2
        public static Specification<GroupEntity> hasStudyLevelId(int studyLevelId) {
            return Specification.where((root, query, criteriaBuilder) -> {
                Join<GroupEntity, StudyLevelEntity> groupStudyLevelJoin = root.join(GroupEntity_.studyLevelEntity);
                return criteriaBuilder.equal(groupStudyLevelJoin.get(StudyLevelEntity_.levelId), studyLevelId);
            });
        }

        public static Specification<GroupEntity> hasFacultyId(int facultyId) {
            return Specification.where((root, query, criteriaBuilder) -> {
                Join<GroupEntity, FacultyEntity> groupStudyLevelJoin = root.join(GroupEntity_.facultyEntity);
                return criteriaBuilder.equal(groupStudyLevelJoin.get(FacultyEntity_.id), facultyId);
            });
        }

        public static Specification<GroupEntity> hasSpecializationId(int specialityId, int specializationId) {
            return Specification.where((root, query, criteriaBuilder) -> {
                Join<GroupEntity, SpecializationEntity> groupSpecializationJoin = root.join(GroupEntity_.specializationEntity);
                Join<SpecializationEntity, SpecialityEntity> groupSpecialityJoin = groupSpecializationJoin.join(SpecializationEntity_.specialityEntity);
                return criteriaBuilder.and(
                        criteriaBuilder.equal(groupSpecializationJoin.get(SpecializationEntity_.specializationId), specializationId),
                        criteriaBuilder.equal(groupSpecialityJoin.get(SpecialityEntity_.specialityId), specialityId));
            });
        }

        public static Specification<GroupEntity> hasYear(int year) {
            return Specification.where((root, query, criteriaBuilder) -> criteriaBuilder.equal(root.get(GroupEntity_.year), year));
        }

        public static Specification<GroupEntity> hasNum(int num) {
            return Specification.where((root, query, criteriaBuilder) -> criteriaBuilder.equal(root.get(GroupEntity_.num), num));
        }
    }

}
