package com.pragma.challenge.msvc_users.infrastructure.output.feign.dto.response;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class RestaurantEmployeeResponse {
    private String id;
    private String restaurantId;
}
