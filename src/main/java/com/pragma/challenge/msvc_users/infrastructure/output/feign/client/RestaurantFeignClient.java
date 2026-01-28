package com.pragma.challenge.msvc_users.infrastructure.output.feign.client;

import com.pragma.challenge.msvc_users.infrastructure.configuration.feign.FeignClientConfiguration;
import com.pragma.challenge.msvc_users.infrastructure.output.feign.dto.request.RestaurantEmployeeRequest;
import com.pragma.challenge.msvc_users.infrastructure.output.feign.dto.response.RestaurantEmployeeResponse;
import com.pragma.challenge.msvc_users.infrastructure.output.feign.util.FeignConstants;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@FeignClient(
        name = FeignConstants.RESTAURANT_CLIENT_NAME,
        url = "http://localhost:8082/v1/restaurants",
        configuration = FeignClientConfiguration.class
)
public interface RestaurantFeignClient {
    @PostMapping(value = "/employees", consumes = MediaType.APPLICATION_JSON_VALUE)
    RestaurantEmployeeResponse registerEmployee(@RequestBody RestaurantEmployeeRequest restaurantEmployeeRequest);
}
