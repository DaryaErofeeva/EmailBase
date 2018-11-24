package tr1nks.domain.repository;


import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import tr1nks.domain.entity.SpecialityEntity;
import tr1nks.domain.entity.SpecialityEntity_;
import tr1nks.domain.entity.SpecializationEntity;
import tr1nks.domain.entity.SpecializationEntity_;

import javax.persistence.criteria.Join;

public interface SpecializationRepository extends JpaRepository<SpecializationEntity, Long>, JpaSpecificationExecutor<SpecializationEntity> {

    SpecializationEntity getTopBySpecializationIdAndSpecialityEntityId(int specializationId, long specialityId);

    class SpecializationEntitySpecifications {

        public static Specification<SpecializationEntity> hasSpecializationId(int specializationId) {
            return Specification.where((root, query, criteriaBuilder) -> criteriaBuilder.equal(root.get(SpecializationEntity_.specializationId), specializationId));
        }

        public static Specification<SpecializationEntity> hasSpecialityId(int specialityId) {
            return Specification.where((root, query, criteriaBuilder) -> {
                Join<SpecializationEntity, SpecialityEntity> specializationSpecialityJoin = root.join(SpecializationEntity_.specialityEntity);
                return criteriaBuilder.equal(specializationSpecialityJoin.get(SpecialityEntity_.specialityId), specialityId);
            });
        }
    }
}
