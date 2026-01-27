package com.pragma.challenge.msvc_users.application.dto.request;

import com.pragma.challenge.msvc_users.application.util.ApplicationConstants;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class AuthenticationRequest {

    @NotNull(message = "'email' field must not be null")
    @Pattern(regexp = ApplicationConstants.EMAIL_ADDRESS_REGEX, message = "Given email does not match with expected pattern")
    private String email;

    @NotNull(message = "'password' field must not be null")
    private String password;
}
