package com.pragma.challenge.msvc_users.domain.spi.security;

import com.pragma.challenge.msvc_users.domain.model.User;

public interface TokenSecurityPort {
    /**
     * Creates a new token for the given user using their Long, and Role
     * @param user given user
     * @return returns a new token
     */
    String createToken(User user);

    /**
     * @param token a token already generated
     * @param username id of the user
     * @return true if is a valid token false in the other case
     */
    boolean validateToken(String token, Long username);

    /**
     * Get id of the user from Token
     * @param token a valid token
     * @return The UUID of the user
     */
    Long getUsername(String token);
}
