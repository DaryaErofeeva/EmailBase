package tr1nks.service.domain;

import tr1nks.domain.entity.UserEntity;


public interface UserService {
    UserEntity save(UserEntity userEntity);

    UserEntity getByEmail(String email);
}
