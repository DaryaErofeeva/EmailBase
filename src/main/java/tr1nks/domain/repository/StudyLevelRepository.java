package tr1nks.domain.repository;


import org.springframework.data.jpa.repository.JpaRepository;
import tr1nks.domain.entity.StudyLevelEntity;

public interface StudyLevelRepository extends JpaRepository<StudyLevelEntity, Long> {

    StudyLevelEntity getTopByLevelId(int levelId);
}
