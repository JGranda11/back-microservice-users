package com.pragma.challenge.msvc_users.domain.usecase.security;

import com.pragma.challenge.msvc_users.domain.exception.ExpiredTokenException;
import com.pragma.challenge.msvc_users.domain.exception.InvalidTokenException;
import com.pragma.challenge.msvc_users.domain.exception.UserNotRegisteredException;
import com.pragma.challenge.msvc_users.domain.model.Role;
import com.pragma.challenge.msvc_users.domain.model.User;
import com.pragma.challenge.msvc_users.domain.model.auth.AuthenticatedUser;
import com.pragma.challenge.msvc_users.domain.spi.IUserPersistencePort;
import com.pragma.challenge.msvc_users.domain.spi.security.AuthenticationSecurityPort;
import com.pragma.challenge.msvc_users.domain.spi.security.TokenSecurityPort;
import com.pragma.challenge.msvc_users.domain.util.enums.RoleName;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

public class AuthenticationUseCaseTest {

    @Mock
    private TokenSecurityPort tokenSecurityPort;

    @Mock
    private AuthenticationSecurityPort authenticationSecurityPort;

    @Mock
    private IUserPersistencePort userPersistencePort;

    @InjectMocks
    private AuthenticationUseCase authenticationUseCase;

    private static final String MOCK_TOKEN = "token";
    public static final Long USER_ID = 4L;
    public static final String USER_NAME = "User";
    public static final String USER_LASTNAME = "Dummy";
    public static final String USER_IDENTITY_DOCUMENT= "1223334444";
    public static final String USER_PHONE = "+573225545645";
    public static final String USER_EMAIL = "email@dummy.com";
    public static final String USER_PASSWORD = "password";
    public static final LocalDate USER_BIRTHDATE = LocalDate.now().minusYears(20);
    public static final Long ROLE_ID = 3L;
    public static final RoleName ROLE_NAME = RoleName.OWNER;
    public static final String ROLE_DESCRIPTION = "Restaurant owner";

    private final Role ownerRole = Role.builder()
            .id(ROLE_ID)
            .name(ROLE_NAME)
            .description(ROLE_DESCRIPTION)
            .build();

    private final User user = User.builder()
            .id(USER_ID)
            .name(USER_NAME)
            .lastname(USER_LASTNAME)
            .identityDocument(USER_IDENTITY_DOCUMENT)
            .phone(USER_PHONE)
            .birthdate(USER_BIRTHDATE)
            .email(USER_EMAIL)
            .password(USER_PASSWORD)
            .role(ownerRole)
            .build();

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void authenticate(){
        when(userPersistencePort.findByEmail(USER_EMAIL)).thenReturn(user);
        doNothing().when(authenticationSecurityPort).authenticate(any());
        when(tokenSecurityPort.createToken(any())).thenReturn(MOCK_TOKEN);

        AuthenticatedUser authenticatedUser = authenticationUseCase.authenticate(USER_EMAIL, USER_PASSWORD);

        verify(authenticationSecurityPort).authenticate(any());
        assertEquals(USER_ID, authenticatedUser.getId());
        assertEquals(ROLE_NAME, authenticatedUser.getRole());
        assertEquals(MOCK_TOKEN, authenticatedUser.getToken());
    }

    @Test
    void authenticateUserNotRegistered(){
        when(userPersistencePort.findByEmail(USER_EMAIL)).thenReturn(null);

        assertThrows(UserNotRegisteredException.class,
                () -> authenticationUseCase.authenticate(USER_EMAIL, USER_PASSWORD));

        verify(authenticationSecurityPort, times(0)).authenticate(any());
    }

    @Test
    void validateToken(){
        when(tokenSecurityPort.getUsername(MOCK_TOKEN)).thenReturn(USER_ID);
        when(userPersistencePort.findById(USER_ID)).thenReturn(user);
        when(tokenSecurityPort.validateToken(MOCK_TOKEN, USER_ID)).thenReturn(true);

        AuthenticatedUser authUser = authenticationUseCase.validateToken(MOCK_TOKEN);

        assertEquals(USER_ID, authUser.getId());
        assertEquals(ROLE_NAME, authUser.getRole());
        assertEquals(MOCK_TOKEN, authUser.getToken());
    }

    @Test
    void validateToken_expiredToken(){
        when(tokenSecurityPort.getUsername(MOCK_TOKEN)).thenReturn(USER_ID);
        when(userPersistencePort.findById(USER_ID)).thenReturn(user);
        when(tokenSecurityPort.validateToken(MOCK_TOKEN, USER_ID)).thenReturn(false);

        assertThrows(ExpiredTokenException.class,
                () -> authenticationUseCase.validateToken(MOCK_TOKEN));
    }

    @Test
    void validateToken_notUserFound(){
        when(tokenSecurityPort.getUsername(MOCK_TOKEN)).thenReturn(USER_ID);
        when(userPersistencePort.findById(USER_ID)).thenReturn(null);
        when(tokenSecurityPort.validateToken(MOCK_TOKEN, USER_ID)).thenReturn(true);

        assertThrows(InvalidTokenException.class,
                () -> authenticationUseCase.validateToken(MOCK_TOKEN));

        verify(tokenSecurityPort, times(0)).validateToken(any(), any());
    }
}
