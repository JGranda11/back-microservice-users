package com.pragma.challenge.msvc_users.application.dto.response;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
@Schema(description = "Respuesta con la información básica del usuario")
public class UserResponse {

    @Schema(description = "Nombre del usuario", example = "Juan")
    private String name;

    @Schema(description = "Apellido del usuario", example = "Pérez")
    private String lastname;

    @Schema(description = "Correo electrónico del usuario", example = "juan@mail.com")
    private String email;
}
