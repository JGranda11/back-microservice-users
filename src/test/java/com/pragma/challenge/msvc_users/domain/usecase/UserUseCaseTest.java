package com.pragma.challenge.msvc_users.domain.usecase;

import com.pragma.challenge.msvc_users.domain.exception.EntityAlreadyExistsException;
import com.pragma.challenge.msvc_users.domain.exception.EntityNotFoundException;
import com.pragma.challenge.msvc_users.domain.exception.UnderAgedUserException;
import com.pragma.challenge.msvc_users.domain.model.Role;
import com.pragma.challenge.msvc_users.domain.model.User;
import com.pragma.challenge.msvc_users.domain.spi.IRolePersistencePort;
import com.pragma.challenge.msvc_users.domain.spi.IUserPersistencePort;
import com.pragma.challenge.msvc_users.domain.util.enums.RoleName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class UserUseCaseTest {

    @Mock
    private IUserPersistencePort userPersistencePort;

    @Mock
    private IRolePersistencePort rolePersistencePort;

    @InjectMocks
    private UserUseCase userUseCase;

    // Dummies

    public static final Long USER_ID = 1L;
    public static final String USER_NAME = "Juan";
    public static final String USER_LASTNAME = "Perez";
    public static final String USER_IDENTITY_DOCUMENT = "1023456789";
    public static final String USER_PHONE = "+573005698325";
    public static final String USER_EMAIL = "juan@example.com";
    public static final String USER_PASSWORD = "password";
    public static final LocalDate USER_BIRTHDATE = LocalDate.now().minusYears(25);
    public static final Long ROLE_ID = 3L;
    public static final RoleName ROLE_NAME = RoleName.OWNER;
    public static final String ROLE_DESCRIPTION = "Restaurant owner";


    private final Role ownerRole = Role.builder()
            .id(2L)
            .name(RoleName.OWNER)
            .description(ROLE_DESCRIPTION)
            .build();

    private final User user = User.builder()
            .name(USER_NAME)
            .lastname(USER_LASTNAME)
            .identityDocument(USER_IDENTITY_DOCUMENT)
            .phone(USER_PHONE)
            .birthdate(USER_BIRTHDATE)
            .email(USER_EMAIL)
            .password(USER_PASSWORD)
            .build();

    private final User savedUser = User.builder()
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


    @Test
    void createOwner() {
        // Arrange
        when(userPersistencePort.findByEmail(USER_EMAIL)).thenReturn(null);
        when(userPersistencePort.findByIdentityDocument(USER_IDENTITY_DOCUMENT)).thenReturn(null);
        when(userPersistencePort.saveUser(any(User.class))).thenReturn(savedUser);
        when(rolePersistencePort.findByName(any())).thenReturn(ownerRole);

        // Act
        User savedUser = userUseCase.createOwner(user);

        verify(userPersistencePort).findByEmail(USER_EMAIL);
        verify(userPersistencePort).findByIdentityDocument(USER_IDENTITY_DOCUMENT);
        verify(userPersistencePort).saveUser(any(User.class));
        assertEquals(ROLE_NAME, savedUser.getRole().getName());
        assertEquals(USER_EMAIL, savedUser.getEmail());
    }

    @Test
    void createOwner_roleNotFound() {
        when(rolePersistencePort.findByName(RoleName.OWNER)).thenReturn(null);

        assertThrows(EntityNotFoundException.class,
                () -> userUseCase.createOwner(user));
    }

    @Test
    void createUser_EmailAlreadyExists(){
        when(rolePersistencePort.findByName(any())).thenReturn(ownerRole);
        when(userPersistencePort.findByEmail(USER_EMAIL)).thenReturn(savedUser);

        assertThrows(EntityAlreadyExistsException.class, () -> userUseCase.createOwner(user));
    }

    @Test
    void createOwner_identityDocumentAlreadyExists() {
        when(rolePersistencePort.findByName(RoleName.OWNER)).thenReturn(ownerRole);
        when(userPersistencePort.findByIdentityDocument(USER_IDENTITY_DOCUMENT))
                .thenReturn(savedUser);

        assertThrows(EntityAlreadyExistsException.class,
                () -> userUseCase.createOwner(user));
    }

    @Test
    void isOwner(){
        when(userPersistencePort.findById(USER_ID)).thenReturn(savedUser);

        boolean isOwner = userUseCase.isOwner(USER_ID);

        verify(userPersistencePort).findById(USER_ID);
        assertTrue(isOwner);
    }

    @Test
    void createOwner_underAgeUser() {
        User underAgeUser = User.builder()
                .name(USER_NAME)
                .lastname(USER_LASTNAME)
                .identityDocument(USER_IDENTITY_DOCUMENT)
                .phone(USER_PHONE)
                .birthdate(LocalDate.now().minusYears(16))
                .email(USER_EMAIL)
                .password(USER_PASSWORD)
                .build();

        when(rolePersistencePort.findByName(RoleName.OWNER)).thenReturn(ownerRole);
        when(userPersistencePort.findByEmail(USER_EMAIL)).thenReturn(null);
        when(userPersistencePort.findByIdentityDocument(USER_IDENTITY_DOCUMENT)).thenReturn(null);

        assertThrows(UnderAgedUserException.class,
                () -> userUseCase.createOwner(underAgeUser));
    }

}
