package com.pragma.challenge.msvc_users.domain.usecase;

import com.pragma.challenge.msvc_users.domain.exception.EntityAlreadyExistsException;
import com.pragma.challenge.msvc_users.domain.exception.EntityNotFoundException;
import com.pragma.challenge.msvc_users.domain.exception.ErrorRegisteringEmployeeException;
import com.pragma.challenge.msvc_users.domain.exception.UnderAgedUserException;
import com.pragma.challenge.msvc_users.domain.model.Role;
import com.pragma.challenge.msvc_users.domain.model.User;
import com.pragma.challenge.msvc_users.domain.spi.IRolePersistencePort;
import com.pragma.challenge.msvc_users.domain.spi.IUserPersistencePort;
import com.pragma.challenge.msvc_users.domain.spi.RestaurantPersistencePort;
import com.pragma.challenge.msvc_users.domain.spi.security.IPasswordEncoderPort;
import com.pragma.challenge.msvc_users.domain.util.enums.RoleName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class UserUseCaseTest {

    @Mock
    private IUserPersistencePort userPersistencePort;

    @Mock
    private IRolePersistencePort rolePersistencePort;

    @Mock
    private IPasswordEncoderPort passwordEncoderPort;

    @Mock
    private RestaurantPersistencePort restaurantPersistencePort;

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
    public static final Long ROLE_ID = 2L;
    public static final RoleName ROLE_NAME = RoleName.OWNER;
    public static final String ROLE_DESCRIPTION = "Restaurant owner";
    public static final Long RESTAURANT_ID = 5L;

    private final Role role = Role.builder()
            .id(ROLE_ID)
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

    private final User expectedUser = User.builder()
            .id(USER_ID)
            .name(USER_NAME)
            .lastname(USER_LASTNAME)
            .identityDocument(USER_IDENTITY_DOCUMENT)
            .phone(USER_PHONE)
            .birthdate(USER_BIRTHDATE)
            .email(USER_EMAIL)
            .password(USER_PASSWORD)
            .role(role)
            .build();



    @Test
    void createOwner() {
        // Arrange
        when(userPersistencePort.findByEmail(USER_EMAIL)).thenReturn(null);
        when(userPersistencePort.findByIdentityDocument(USER_IDENTITY_DOCUMENT)).thenReturn(null);
        when(userPersistencePort.saveUser(any(User.class))).thenReturn(expectedUser);
        when(rolePersistencePort.findByName(any())).thenReturn(role);
        when(passwordEncoderPort.encode(any(String.class)))
                .thenReturn("encrypted-password");

        // Act
        User savedUser = userUseCase.createOwner(user);

        verify(passwordEncoderPort).encode(USER_PASSWORD);
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
        when(rolePersistencePort.findByName(any())).thenReturn(role);
        when(userPersistencePort.findByEmail(USER_EMAIL)).thenReturn(expectedUser);

        assertThrows(EntityAlreadyExistsException.class, () -> userUseCase.createOwner(user));
    }

    @Test
    void createOwner_identityDocumentAlreadyExists() {
        when(rolePersistencePort.findByName(RoleName.OWNER)).thenReturn(role);
        when(userPersistencePort.findByIdentityDocument(USER_IDENTITY_DOCUMENT))
                .thenReturn(expectedUser);

        assertThrows(EntityAlreadyExistsException.class,
                () -> userUseCase.createOwner(user));
    }

    @Test
    void isOwner(){
        when(userPersistencePort.findById(USER_ID)).thenReturn(expectedUser);

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

        when(rolePersistencePort.findByName(RoleName.OWNER)).thenReturn(role);
        when(userPersistencePort.findByEmail(USER_EMAIL)).thenReturn(null);
        when(userPersistencePort.findByIdentityDocument(USER_IDENTITY_DOCUMENT)).thenReturn(null);

        assertThrows(UnderAgedUserException.class,
                () -> userUseCase.createOwner(underAgeUser));
    }

    @Test
    void createEmployee_Success() {
        Role employeeRole = Role.builder()
                .id(2L)
                .name(RoleName.EMPLOYEE)
                .build();

        when(rolePersistencePort.findByName(RoleName.EMPLOYEE))
                .thenReturn(employeeRole);

        when(userPersistencePort.saveUser(any(User.class)))
                .thenAnswer(invocation -> invocation.getArgument(0));

        User result = userUseCase.createEmployee(user, RESTAURANT_ID);

        verify(rolePersistencePort).findByName(RoleName.EMPLOYEE);
        verify(userPersistencePort).saveUser(any(User.class));
        verify(restaurantPersistencePort)
                .registerEmployeeInRestaurant(any(User.class), eq(RESTAURANT_ID));

        assertEquals(RoleName.EMPLOYEE, result.getRole().getName());
    }

    @Test
    void createEmployee_RoleNotFound() {
        // Arrange
        when(rolePersistencePort.findByName(RoleName.EMPLOYEE)).thenReturn(null);

        // Act & Assert
        assertThrows(EntityNotFoundException.class, () -> userUseCase.createEmployee(user,
                3L));
    }

    @Test
    void createEmployee_ErrorRegisteringInRestaurant() {
        when(rolePersistencePort.findByName(RoleName.EMPLOYEE))
                .thenReturn(Role.builder().id(2L).name(RoleName.EMPLOYEE).build());

        when(userPersistencePort.saveUser(any(User.class)))
                .thenAnswer(invocation -> {
                    User u = invocation.getArgument(0);
                    u.setId(USER_ID);
                    return u;
                });

        doThrow(new RuntimeException())
                .when(restaurantPersistencePort)
                .registerEmployeeInRestaurant(any(User.class), anyLong());

        assertThrows(ErrorRegisteringEmployeeException.class,
                () -> userUseCase.createEmployee(user, RESTAURANT_ID));

        verify(userPersistencePort).deletedById(USER_ID);
    }

    @Test
    void createCustomer() {
        Role customerRole = Role.builder().id(2L).name(RoleName.CUSTOMER).build();
        expectedUser.setRole(customerRole);

        when(userPersistencePort.findByEmail(USER_EMAIL)).thenReturn(null);
        when(userPersistencePort.findByIdentityDocument(USER_IDENTITY_DOCUMENT)).thenReturn(null);
        when(userPersistencePort.saveUser(any(User.class))).thenReturn(expectedUser);
        when(rolePersistencePort.findByName(any())).thenReturn(role);

        // Act
        User savedUser = userUseCase.createCustomer(user);

        verify(userPersistencePort).findByEmail(USER_EMAIL);
        verify(userPersistencePort).findByIdentityDocument(USER_IDENTITY_DOCUMENT);
        verify(userPersistencePort).saveUser(any(User.class));
        assertEquals(customerRole.getName(), savedUser.getRole().getName());
        assertEquals(USER_EMAIL, savedUser.getEmail());
    }
}
