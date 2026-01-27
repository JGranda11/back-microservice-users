package com.pragma.challenge.msvc_users.domain.usecase.security;

import com.pragma.challenge.msvc_users.domain.spi.security.TokenSecurityPort;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.when;

 class TokenUseCaseTest {
    @Mock
    private TokenSecurityPort tokenSecurityPort;

    @InjectMocks
    private TokenUseCase tokenUseCase;

    private static final String MOCK_TOKEN = "token";
    private static final Long MOCK_USERNAME = 3L;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void validateToken() {
        when(tokenSecurityPort.validateToken(MOCK_TOKEN, MOCK_USERNAME)).thenReturn(true);

        boolean isValid = tokenUseCase.validateToken(MOCK_TOKEN, MOCK_USERNAME);

        assertTrue(isValid);
    }

    @Test
    void getUsername() {
        when(tokenSecurityPort.getUsername(MOCK_TOKEN)).thenReturn(MOCK_USERNAME);

        Long username = tokenUseCase.getUsername(MOCK_TOKEN);

        assertEquals(MOCK_USERNAME,username);
    }
}
