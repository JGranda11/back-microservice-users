package com.pragma.challenge.msvc_users.infrastructure.output.jpa.adapter;

import com.pragma.challenge.msvc_users.domain.model.User;
import com.pragma.challenge.msvc_users.domain.spi.IUserPersistencePort;
import com.pragma.challenge.msvc_users.infrastructure.output.jpa.entity.UserEntity;
import com.pragma.challenge.msvc_users.infrastructure.output.jpa.mapper.UserEntityMapper;
import com.pragma.challenge.msvc_users.infrastructure.output.jpa.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@RequiredArgsConstructor
public class UserJpaAdapter implements IUserPersistencePort {
    private final UserRepository userRepository;
    private final UserEntityMapper userEntityMapper;

    @Override
    public User saveUser(User user) {
        UserEntity entity = userEntityMapper.toEntity(user);
        log.debug("user: {}", entity);
        return userEntityMapper.toDomain(
                userRepository.save(entity)
        );
    }

    @Override
    public User findByIdentityDocument(String identityDocument) {
        return userEntityMapper.toDomain(
                userRepository.findByIdentityDocument(identityDocument).orElse(null)
        );
    }

    @Override
    public User findByEmail(String email) {
        return userEntityMapper.toDomain(
                userRepository.findByEmail(email).orElse(null)
        );
    }


    @Override
    public User findById(Long id) {
        return userEntityMapper.toDomain(
                userRepository.findById(id).orElse(null)
        );
    }

    @Override
    public void deletedById(Long id) {
        userRepository.deleteById(id);
    }
}
