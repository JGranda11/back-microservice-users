package com.pragma.challenge.msvc_users.infrastructure.configuration.security.filter;

import com.pragma.challenge.msvc_users.domain.api.security.TokenServicePort;
import com.pragma.challenge.msvc_users.domain.util.TokenHolder;
import com.pragma.challenge.msvc_users.infrastructure.util.ConfigurationConstants;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Component
@RequiredArgsConstructor
public class JwtAuthorizationFilter extends OncePerRequestFilter {
    private final TokenServicePort tokenServicePort;
    private final UserDetailsService userDetailsService;

    @Override
    protected void doFilterInternal(HttpServletRequest request,
                                    @NonNull HttpServletResponse response,
                                    @NonNull FilterChain filterChain)
            throws ServletException, IOException {
        final String authorizationHeader = request.getHeader(ConfigurationConstants.AUTHORIZATION_HEADER);

        if (authorizationHeader == null || !authorizationHeader.startsWith(ConfigurationConstants.TOKEN_PREFIX)) {
            filterChain.doFilter(request, response);
            return;
        }
        String token = authorizationHeader.substring(ConfigurationConstants.TOKEN_PREFIX.length());
        Long username = tokenServicePort.getUsername(token);

        setContextAuthentication(request, token, String.valueOf(username));
        TokenHolder.setToken(authorizationHeader);
        filterChain.doFilter(request, response);

    }

    private void setContextAuthentication(HttpServletRequest request, String token, String username) {
        if (username != null && SecurityContextHolder.getContext().getAuthentication() == null) {
            UserDetails userDetails = userDetailsService.loadUserByUsername(username);

            if (tokenServicePort.validateToken(token, Long.valueOf(username))) {
                UsernamePasswordAuthenticationToken authentication =
                        new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
                authentication.setDetails(
                        new WebAuthenticationDetailsSource().buildDetails(request)
                );
                SecurityContextHolder.getContext().setAuthentication(authentication);
            }

        }
    }
}