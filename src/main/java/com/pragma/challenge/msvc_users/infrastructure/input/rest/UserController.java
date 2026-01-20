package com.pragma.challenge.msvc_users.infrastructure.input.rest;

import com.pragma.challenge.msvc_users.application.handler.UserHandler;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("v1/users")
@RequiredArgsConstructor
public class UserController {
    private final UserHandler userHandler;

}
