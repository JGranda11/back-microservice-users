package com.pragma.challenge.msvc_users.application.dto.request;

import com.pragma.challenge.msvc_users.application.util.ApplicationConstants;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import lombok.Builder;
import lombok.Data;

import java.time.LocalDate;

@Data
@Builder
public class UserRequest {

    @NotNull(message = "'identity document' field must not be null")
    @Pattern(regexp = ApplicationConstants.IDENTITY_DOCUMENT_REGEX, message = "A document must be a number and be between 6 and 16")
    private String identityDocument;

    @NotNull(message = "'name' field must not be null")
    private String name;

    @NotNull(message = "'name' field must not be null")
    private String lastname;

    @NotNull(message = "'birthdate' field must not be null")
    private LocalDate birthdate;

    @NotNull(message = "'phone' field must not be null")
    @Pattern(regexp = ApplicationConstants.PHONE_NUMBER_REGEX, message = "Given phone number does not match with expected pattern")
    private String phone;

    @NotNull(message = "'email' field must not be null")
    @Pattern(regexp = ApplicationConstants.EMAIL_ADDRESS_REGEX, message = "Given email does not match with expected pattern")
    private String email;

    @NotNull(message = "'password' field must not be null")
    private String password;
}
