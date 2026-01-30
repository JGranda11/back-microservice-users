package com.pragma.challenge.msvc_users.domain.usecase;

import com.pragma.challenge.msvc_users.domain.api.IUserServicePort;
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

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;

public class UserUseCase implements IUserServicePort {

    private final IUserPersistencePort userPersistencePort;
    private final IRolePersistencePort rolePersistencePort;
    private final IPasswordEncoderPort passwordEncoderPort;
    private final RestaurantPersistencePort restaurantPersistencePort;

    public UserUseCase(IUserPersistencePort userPersistencePort, IRolePersistencePort rolePersistencePort, IPasswordEncoderPort passwordEncoderPort, RestaurantPersistencePort restaurantPersistencePort) {
        this.userPersistencePort = userPersistencePort;
        this.rolePersistencePort = rolePersistencePort;
        this.passwordEncoderPort = passwordEncoderPort;
        this.restaurantPersistencePort = restaurantPersistencePort;
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
        registerInRestaurant(savedUser, restaurantId);

        return savedUser;
    }

    private void registerInRestaurant(User user, Long restaurantId){
        try{
            restaurantPersistencePort.registerEmployeeInRestaurant(user, restaurantId);
        } catch (Exception e){
            e.printStackTrace();
            userPersistencePort.deletedById(user.getId());
            throw new ErrorRegisteringEmployeeException();
        }
    }

    @Override
    public User createCustomer(User user) {
        return saveUser(user, RoleName.CUSTOMER);
    }

    @Override
    public boolean isOwner(Long userId) {
        User user = findById(userId);
        return user.getRole().getName().equals(RoleName.OWNER);
    }

    private User saveUser(User user, RoleName rolename){
        Role role = rolePersistencePort.findByName(rolename);

        if (role == null) {
            throw new EntityNotFoundException(
                    "Role named '%s' hasn't been found",
            rolename.name());
        }
        user.setRole(role);
        encryptPassword(user);
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

    private User encryptPassword(User user){
        String passwordEncode = passwordEncoderPort.encode(user.getPassword());
        user.setPassword(passwordEncode);
        return user;
    }
}
