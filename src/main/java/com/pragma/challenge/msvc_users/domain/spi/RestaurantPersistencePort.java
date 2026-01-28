package com.pragma.challenge.msvc_users.domain.spi;

import com.pragma.challenge.msvc_users.domain.model.User;

public interface RestaurantPersistencePort {
    User registerEmployeeInRestaurant(User user, Long restaurantId);
}
