package com.pragma.challenge.msvc_users.infrastructure.input.rest;

import com.pragma.challenge.msvc_users.application.dto.request.EmployeeRequest;
import com.pragma.challenge.msvc_users.application.dto.request.UserRequest;
import com.pragma.challenge.msvc_users.application.dto.response.IsOwnerResponse;
import com.pragma.challenge.msvc_users.application.dto.response.UserResponse;
import com.pragma.challenge.msvc_users.application.handler.UserHandler;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("v1/users")
@RequiredArgsConstructor
@Tag(name = "Usuarios", description = "Operaciones relacionadas con usuarios y roles")
public class UserController {
    private final UserHandler userHandler;

    @Operation(
            summary = "Crear propietario (OWNER)",
            description = "Crea un usuario con rol OWNER en el sistema"
    )
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "201",
                    description = "Propietario creado correctamente",
                    content = @Content(schema = @Schema(implementation = UserResponse.class))
            ),
            @ApiResponse(
                    responseCode = "400",
                    description = "Datos inválidos enviados en la solicitud"
            ),
            @ApiResponse(
                    responseCode = "409",
                    description = "El usuario ya existe"
            )
    })
    @PostMapping("/owners")
    @PreAuthorize("hasAnyRole('ADMIN')")
    public ResponseEntity<UserResponse> createOwner(
            @RequestBody @Valid UserRequest owner){
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(userHandler.createOwner(owner));
    }

    @PostMapping("/employees")
    public ResponseEntity<UserResponse> createEmployee(@RequestBody @Valid EmployeeRequest employeeRequest){
        return ResponseEntity.status(HttpStatus.CREATED).body(userHandler.createEmployee(employeeRequest));
    }

    @Operation(
            summary = "Validar si un usuario es OWNER",
            description = "Verifica si un usuario tiene el rol OWNER"
    )
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "Resultado de la validación",
                    content = @Content(schema = @Schema(implementation = IsOwnerResponse.class))
            ),
            @ApiResponse(
                    responseCode = "404",
                    description = "Usuario no encontrado"
            )
    })
    @GetMapping("/{id}/is-owner")
    public ResponseEntity<IsOwnerResponse> isOwner(@PathVariable Long id){
        return ResponseEntity.ok(
                userHandler.isOwner(id)
        );
    }

    @Operation(
            summary = "Crear cliente (CUSTOMER)",
            description = "Crea un usuario con rol CUSTOMER en el sistema"
    )
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "201",
                    description = "Cliente creado correctamente",
                    content = @Content(schema = @Schema(implementation = UserResponse.class))
            ),
            @ApiResponse(
                    responseCode = "400",
                    description = "Datos inválidos enviados en la solicitud"
            ),
            @ApiResponse(
                    responseCode = "409",
                    description = "El usuario ya existe"
            )
    })
    @PostMapping("/customers")
    public ResponseEntity<UserResponse> createCustomer(
            @RequestBody @Valid UserRequest client) {
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(userHandler.createCustomer(client));
    }
}
