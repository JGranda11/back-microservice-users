package com.pragma.challenge.msvc_users.infrastructure.input.rest;

import com.pragma.challenge.msvc_users.application.dto.request.AuthenticationRequest;
import com.pragma.challenge.msvc_users.application.dto.response.AuthenticationResponse;
import com.pragma.challenge.msvc_users.application.handler.AuthenticationHandler;
import com.pragma.challenge.msvc_users.infrastructure.configuration.advisor.response.ExceptionResponse;
import com.pragma.challenge.msvc_users.infrastructure.configuration.advisor.response.ValidationExceptionResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import jakarta.annotation.security.PermitAll;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/v1/auth")
public class AuthenticationController {

    private final AuthenticationHandler authenticationHandler;

    @Operation(summary = "Creates a token if credentials are right" )
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "202",
                    description ="Credentials were right and it retrieves some basic info from the user",
                    content =  @Content(schema = @Schema(implementation = AuthenticationResponse.class))
            ),
            @ApiResponse(
                    responseCode = "409",
                    description = "Email or password are wrong",
                    content =  @Content(schema = @Schema(implementation = ExceptionResponse.class))
            ),
            @ApiResponse(
                    responseCode = "400",
                    description = "Validations don't pass",
                    content =  @Content(schema = @Schema(implementation = ValidationExceptionResponse.class))
            ),
    })
    @PermitAll
    @GetMapping("/login")
    public final ResponseEntity<AuthenticationResponse> login(@RequestBody AuthenticationRequest request){
        return ResponseEntity.accepted().body(
                authenticationHandler.login(request)
        );
    }

    @Operation(summary = "Create an owner using the given valid info" )
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "202",
                    description ="Token is authorized, response some user data",
                    content =  @Content(schema = @Schema(implementation = AuthenticationResponse.class))
            ),
            @ApiResponse(
                    responseCode = "409",
                    description = "Given token is not authorized",
                    content =  @Content(schema = @Schema(implementation = ExceptionResponse.class))
            ),
            @ApiResponse(
                    responseCode = "400",
                    description = "Validations don't pass",
                    content =  @Content(schema = @Schema(implementation = ValidationExceptionResponse.class))
            ),
    })
    @PermitAll
    @GetMapping("/authorize")
    public final ResponseEntity<AuthenticationResponse> validateToken(String token){
        return ResponseEntity.ok(
                authenticationHandler.validateToken(token)
        );
    }
}
