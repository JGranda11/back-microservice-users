package com.pragma.challenge.msvc_users.infrastructure.input.rest;

import com.pragma.challenge.msvc_users.application.dto.request.UserRequest;
import com.pragma.challenge.msvc_users.application.dto.response.IsOwnerResponse;
import com.pragma.challenge.msvc_users.application.dto.response.UserResponse;
import com.pragma.challenge.msvc_users.application.handler.UserHandler;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("v1/users")
@RequiredArgsConstructor
public class UserController {
    private final UserHandler userHandler;

    @PostMapping("/owners")
    public ResponseEntity<UserResponse> createOwner(
            @RequestBody @Valid UserRequest owner){
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(userHandler.createOwner(owner));
    }

    @GetMapping("/{id}/is-owner")
    public ResponseEntity<IsOwnerResponse> isOwner(@PathVariable Long id){
        return ResponseEntity.ok(
                userHandler.isOwner(id)
        );
    }

    @PostMapping("/customers")
    public ResponseEntity<UserResponse> createCustomer(
            @RequestBody @Valid UserRequest client) {
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(userHandler.createCustomer(client));
    }
}
