package com.pragma.challenge.msvc_users.infrastructure.output.feign.dto.request;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class RestaurantEmployeeRequest {
    private String id;
    private String restaurantId;
}
