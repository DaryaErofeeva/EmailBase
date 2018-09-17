package tr1nks.domain.repository;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import tr1nks.domain.entity.GroupEntity;
import tr1nks.domain.entity.SpecialityEntity;

public interface SpecialityRepository extends JpaRepository<SpecialityEntity, Long>, JpaSpecificationExecutor<GroupEntity> {
}
