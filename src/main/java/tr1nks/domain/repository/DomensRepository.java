package tr1nks.domain.repository;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import tr1nks.domain.entity.DomensEntity;

public interface DomensRepository extends JpaRepository<DomensEntity, Long> {
    @Query(value = "SELECT * FROM domens LIMIT 1", nativeQuery = true)
    DomensEntity getFirst();

    DomensEntity getFirstById(Long id);

    DomensEntity getFirstByEmailDomenAndImagineDomenAndOfficeDomen(String emailDomen, String imagineDomen, String officeDomen);
}
