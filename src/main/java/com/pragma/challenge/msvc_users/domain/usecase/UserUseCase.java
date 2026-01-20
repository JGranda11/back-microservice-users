package com.pragma.challenge.msvc_users.domain.usecase;

import com.pragma.challenge.msvc_users.domain.api.IUserServicePort;
import com.pragma.challenge.msvc_users.domain.exception.EntityAlreadyExistsException;
import com.pragma.challenge.msvc_users.domain.exception.EntityNotFoundException;
import com.pragma.challenge.msvc_users.domain.exception.UnderAgedUserException;
import com.pragma.challenge.msvc_users.domain.model.Role;
import com.pragma.challenge.msvc_users.domain.model.User;
import com.pragma.challenge.msvc_users.domain.spi.IRolePersistencePort;
import com.pragma.challenge.msvc_users.domain.spi.IUserPersistencePort;
import com.pragma.challenge.msvc_users.domain.util.enums.RoleName;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;

public class UserUseCase implements IUserServicePort {

    private final IUserPersistencePort userPersistencePort;
    private final IRolePersistencePort rolePersistencePort;

    public UserUseCase(IUserPersistencePort userPersistencePort,
                       IRolePersistencePort rolePersistencePort) {
        this.userPersistencePort = userPersistencePort;
        this.rolePersistencePort = rolePersistencePort;
    }

    @Override
    public User createOwner(User user) {
        return saveUser(user, RoleName.OWNER);
    }

    @Override
    public User findById(Long id) {
        User user = userPersistencePort.findById(id);
        if(user == null) throw new EntityNotFoundException(User.class.getSimpleName(), String.valueOf(id));
        return user;
    }

    @Override
    public User createEmployee(User user, Long restaurantId) {
        User savedUser = saveUser(user, RoleName.EMPLOYEE);
        //restaurante puerto
        return null;
    }

    @Override
    public User createCustomer(User user) {
        return saveUser(user, RoleName.CUSTOMER);
    }

    @Override
    public boolean isOwner(Long userId) {
        return false;
    }

    private User saveUser(User user, RoleName rolename){
        Role role = rolePersistencePort.findByName(rolename);

        if (role == null) {
            throw new EntityNotFoundException(
                    "Role named '%s' hasn't been found",
            rolename.name());
        }
        user.setRole(role);
        validateUser(user);
        return userPersistencePort.saveUser(user);
    }

    private void validateUser(User user){
        //validar
        if(userPersistencePort.findByIdentityDocument(user.getIdentityDocument()) != null){
            throw new EntityAlreadyExistsException(
                    "An user with '%s' as identity document already exists",
                    user.getIdentityDocument());
        }
        if (userPersistencePort.findByEmail(user.getEmail()) != null) {
            throw new EntityAlreadyExistsException(
                    "An user with '%s' as email already exists",
                    user.getEmail());
        }
        //fecha(+18)
        if (user.getBirthdate().until(LocalDate.now(), ChronoUnit.YEARS) < 18L) {
            throw new UnderAgedUserException();
        }
    }
}
