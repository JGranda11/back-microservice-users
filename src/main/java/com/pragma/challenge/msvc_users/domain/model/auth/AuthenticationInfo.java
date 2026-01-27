package com.pragma.challenge.msvc_users.domain.model.auth;

public class AuthenticationInfo {
    private Long id;
    private String password;

    private AuthenticationInfo(AuthenticationInfoBuilder builder) {
        this.id = builder.id;
        this.password = builder.password;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public static AuthenticationInfoBuilder builder() {
        return new AuthenticationInfoBuilder();
    }

    public static class AuthenticationInfoBuilder {
        private Long id;
        private String password;

        public AuthenticationInfoBuilder id(Long id) {
            this.id = id;
            return this;
        }

        public AuthenticationInfoBuilder password(String password) {
            this.password = password;
            return this;
        }

        public AuthenticationInfo build() {
            return new AuthenticationInfo(this);
        }
    }
}
