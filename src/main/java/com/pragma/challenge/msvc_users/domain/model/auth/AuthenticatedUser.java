package com.pragma.challenge.msvc_users.domain.model.auth;

import com.pragma.challenge.msvc_users.domain.util.enums.RoleName;

public class AuthenticatedUser {
    private String token;
    private RoleName role;
    private Long id;

    private AuthenticatedUser(AuthenticatedUserBuilder builder) {
        this.token = builder.token;
        this.role = builder.role;
        this.id = builder.id;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public RoleName getRole() {
        return role;
    }

    public void setRole(RoleName role) {
        this.role = role;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public static AuthenticatedUserBuilder builder() {
        return new AuthenticatedUserBuilder();
    }

    public static class AuthenticatedUserBuilder {
        private String token;
        private RoleName role;
        private Long id;

        public AuthenticatedUserBuilder token(String token) {
            this.token = token;
            return this;
        }

        public AuthenticatedUserBuilder role(RoleName role) {
            this.role = role;
            return this;
        }

        public AuthenticatedUserBuilder id(Long id) {
            this.id = id;
            return this;
        }

        public AuthenticatedUser build() {
            return new AuthenticatedUser(this);
        }
    }
}
