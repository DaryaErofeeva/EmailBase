package tr1nks.domain.repository;


import org.springframework.data.jpa.repository.JpaRepository;
import tr1nks.domain.entity.FacultyEntity;

public interface FacultyRepository extends JpaRepository<FacultyEntity, Long> {
}
