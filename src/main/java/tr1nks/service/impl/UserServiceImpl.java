package tr1nks.service.impl;

import org.springframework.stereotype.Service;
import tr1nks.domain.entity.UserEntity;
import tr1nks.domain.repository.UserRepository;
import tr1nks.service.UserService;

import javax.annotation.Resource;

@Service
public class UserServiceImpl implements UserService {
    @Resource
    private UserRepository userRepository;

    @Override
    public UserEntity save(UserEntity userEntity) {
        return userRepository.save(userEntity);
    }

    @Override
    public UserEntity getByEmail(String email) {
        return userRepository.getByEmail(email);
    }
}
