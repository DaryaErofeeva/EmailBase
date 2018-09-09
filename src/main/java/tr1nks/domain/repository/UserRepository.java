package tr1nks.domain.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import tr1nks.domain.entity.UserEntity;

public interface UserRepository extends JpaRepository<UserEntity, Long> {
    UserEntity getByEmail(String email);
}
