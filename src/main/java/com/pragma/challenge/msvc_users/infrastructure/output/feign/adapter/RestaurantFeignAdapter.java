package com.pragma.challenge.msvc_users.infrastructure.output.feign.adapter;

import com.pragma.challenge.msvc_users.domain.model.User;
import com.pragma.challenge.msvc_users.domain.spi.RestaurantPersistencePort;
import com.pragma.challenge.msvc_users.infrastructure.output.feign.client.RestaurantFeignClient;
import com.pragma.challenge.msvc_users.infrastructure.output.feign.dto.request.RestaurantEmployeeRequest;
import com.pragma.challenge.msvc_users.infrastructure.output.feign.dto.response.RestaurantEmployeeResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Configuration;

@Configuration
@RequiredArgsConstructor
public class RestaurantFeignAdapter implements RestaurantPersistencePort {
    private final RestaurantFeignClient restaurantFeignClient;

    @Override
    public User registerEmployeeInRestaurant(User user, Long restaurantId) {
        RestaurantEmployeeRequest request = RestaurantEmployeeRequest.builder()
                .id(String.valueOf(user.getId()))
                .restaurantId(String.valueOf(restaurantId))
                .build();
        RestaurantEmployeeResponse response = restaurantFeignClient.registerEmployee(request);

        return User.builder()
                .id(Long.valueOf(response.getId()))
                .build();
    }
}
