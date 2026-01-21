package com.pragma.challenge.msvc_users.infrastructure.configuration;

import com.pragma.challenge.msvc_users.domain.api.IUserServicePort;
import com.pragma.challenge.msvc_users.domain.spi.IRolePersistencePort;
import com.pragma.challenge.msvc_users.domain.spi.IUserPersistencePort;
import com.pragma.challenge.msvc_users.domain.usecase.UserUseCase;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

@Configuration
public class BeanConfiguration {

    @Bean
     public IUserServicePort userServicePort(IUserPersistencePort userPersistencePort,
                                             IRolePersistencePort rolePersistencePort){
        return new UserUseCase(userPersistencePort,
                rolePersistencePort);
    }

    @Bean
    public PasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder();
    }


}
