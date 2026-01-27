package com.pragma.challenge.msvc_users.infrastructure.configuration;

import com.pragma.challenge.msvc_users.domain.api.IUserServicePort;
import com.pragma.challenge.msvc_users.domain.api.security.AuthenticationServicePort;
import com.pragma.challenge.msvc_users.domain.api.security.TokenServicePort;
import com.pragma.challenge.msvc_users.domain.model.User;
import com.pragma.challenge.msvc_users.domain.spi.IRolePersistencePort;
import com.pragma.challenge.msvc_users.domain.spi.IUserPersistencePort;
import com.pragma.challenge.msvc_users.domain.spi.security.AuthenticationSecurityPort;
import com.pragma.challenge.msvc_users.domain.spi.security.IPasswordEncoderPort;
import com.pragma.challenge.msvc_users.domain.spi.security.TokenSecurityPort;
import com.pragma.challenge.msvc_users.domain.usecase.UserUseCase;
import com.pragma.challenge.msvc_users.domain.usecase.security.AuthenticationUseCase;
import com.pragma.challenge.msvc_users.domain.usecase.security.TokenUseCase;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.List;

import static com.pragma.challenge.msvc_users.infrastructure.util.ConfigurationConstants.ROLE_PREFIX;

@Configuration
public class BeanConfiguration {

    @Bean
     public IUserServicePort userServicePort(IUserPersistencePort userPersistencePort,
                                             IRolePersistencePort rolePersistencePort,
                                             IPasswordEncoderPort passwordEncoderPort){
        return new UserUseCase(userPersistencePort,
                rolePersistencePort,
                passwordEncoderPort);
    }

    @Bean
    public PasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder();
    }

    @Bean
    public AuthenticationServicePort authenticationServicePort(
            TokenSecurityPort tokenSecurityPort,
            AuthenticationSecurityPort authenticationSecurityPort,
            IUserPersistencePort userPersistencePort){
        return new AuthenticationUseCase(
                tokenSecurityPort,
                authenticationSecurityPort,
                userPersistencePort
        );
    }

    @Bean
    public TokenServicePort tokenServicePort(TokenSecurityPort tokenSecurityPort){
        return new TokenUseCase(tokenSecurityPort);
    }

    @Bean
    UserDetailsService userDetailsService(IUserServicePort userServicePort) {

        return username -> {
            User domainUser = userServicePort.findById(Long.valueOf(username));
            return new org.springframework.security.core.userdetails.User(
                    String.valueOf(domainUser.getId()),
                    domainUser.getPassword(),
                    List.of(new SimpleGrantedAuthority(ROLE_PREFIX + domainUser.getRole().getName().name()))
            );
        };
    }

    @Bean
    AuthenticationProvider authenticationProvider(
            PasswordEncoder passwordEncoder,
            UserDetailsService userDetailsService
    ) {
        DaoAuthenticationProvider daoAuthenticationProvider = new DaoAuthenticationProvider();
        daoAuthenticationProvider.setPasswordEncoder(passwordEncoder);
        daoAuthenticationProvider.setUserDetailsService(userDetailsService);
        return daoAuthenticationProvider;
    }

    @Bean
    AuthenticationManager authenticationManager(AuthenticationConfiguration configuration) throws Exception {
        return configuration.getAuthenticationManager();
    }
}
