package com.pragma.challenge.msvc_users.application.dto.response;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
@Schema(description = "Respuesta que indica si el usuario tiene rol OWNER")
public class IsOwnerResponse {
    @Schema(description = "Indica si el usuario es propietario", example = "true")
    boolean isOwner;
}
