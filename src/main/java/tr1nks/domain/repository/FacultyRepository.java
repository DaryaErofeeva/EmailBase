package tr1nks.domain.repository;


import org.springframework.data.jpa.repository.JpaRepository;
import tr1nks.domain.entity.FacultyEntity;

import java.util.List;

public interface FacultyRepository extends JpaRepository<FacultyEntity, Long> {
    FacultyEntity getByFacultyId(Integer facultyId);

    List<FacultyEntity> getAllByIdIn(List<Long> Id);
}
