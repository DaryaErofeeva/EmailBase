package tr1nks.domain.repository;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import tr1nks.domain.entity.DomainsEntity;

public interface DomainsRepository extends JpaRepository<DomainsEntity, Long> {
    @Query(value = "SELECT * FROM domains LIMIT 1", nativeQuery = true)
    DomainsEntity getFirst();
}
