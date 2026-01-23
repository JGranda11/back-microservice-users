package com.pragma.challenge.msvc_users.application.dto.response;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
@Schema(description = "Respuesta con la información del cliente")
public class CustomerResponse {

    @Schema(description = "Nombre del cliente", example = "Juan")
    private String name;

    @Schema(description = "Nombre del cliente", example = "Juan")
    private String lastname;

    @Schema(description = "Número de teléfono del cliente", example = "+573001234567")
    private String phone;
}
