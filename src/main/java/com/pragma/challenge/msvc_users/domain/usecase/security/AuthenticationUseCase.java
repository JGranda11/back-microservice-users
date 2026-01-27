package com.pragma.challenge.msvc_users.domain.usecase.security;

import com.pragma.challenge.msvc_users.domain.api.security.AuthenticationServicePort;
import com.pragma.challenge.msvc_users.domain.exception.ExpiredTokenException;
import com.pragma.challenge.msvc_users.domain.exception.InvalidTokenException;
import com.pragma.challenge.msvc_users.domain.exception.UserNotRegisteredException;
import com.pragma.challenge.msvc_users.domain.model.User;
import com.pragma.challenge.msvc_users.domain.model.auth.AuthenticatedUser;
import com.pragma.challenge.msvc_users.domain.model.auth.AuthenticationInfo;
import com.pragma.challenge.msvc_users.domain.spi.IUserPersistencePort;
import com.pragma.challenge.msvc_users.domain.spi.security.AuthenticationSecurityPort;
import com.pragma.challenge.msvc_users.domain.spi.security.TokenSecurityPort;
import com.pragma.challenge.msvc_users.domain.util.enums.RoleName;

public class AuthenticationUseCase implements AuthenticationServicePort {
    private final TokenSecurityPort tokenSecurityPort;
    private final AuthenticationSecurityPort authenticationSecurityPort;
    private final IUserPersistencePort userPersistencePort;

    public AuthenticationUseCase(TokenSecurityPort tokenSecurityPort,
                                 AuthenticationSecurityPort authenticationSecurityPort,
                                 IUserPersistencePort userPersistencePort) {
        this.tokenSecurityPort = tokenSecurityPort;
        this.authenticationSecurityPort = authenticationSecurityPort;
        this.userPersistencePort = userPersistencePort;
    }

    @Override
    public AuthenticatedUser authenticate(String email, String password) {
        User user = userPersistencePort.findByEmail(email);
        if(user == null){
            throw new UserNotRegisteredException();
        }

        AuthenticationInfo authenticationInfo = AuthenticationInfo.builder()
                .id(user.getId())
                .password(password)
                .build();
        authenticationSecurityPort.authenticate(authenticationInfo);
        String token = tokenSecurityPort.createToken(user);

        return AuthenticatedUser.builder()
                .id(user.getId())
                .role(user.getRole().getName())
                .token(token)
                .build();
    }

    @Override
    public AuthenticatedUser validateToken(String token) {
        Long userId;
        boolean isValid;
        RoleName userRole;
        try{
            userId = tokenSecurityPort.getUsername(token);
            userRole = userPersistencePort.findById(userId).getRole().getName();
            isValid = tokenSecurityPort.validateToken(token, userId);
        } catch (Exception e){
            throw new InvalidTokenException();
        }
        if(!isValid) throw new ExpiredTokenException();

        return AuthenticatedUser.builder()
                .token(token)
                .role(userRole)
                .id(userId)
                .build();

    }
}
