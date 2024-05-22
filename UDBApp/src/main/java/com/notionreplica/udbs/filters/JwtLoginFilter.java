package com.notionreplica.udbs.filters;

import jakarta.annotation.PostConstruct;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Service;
import org.springframework.web.filter.OncePerRequestFilter;
import redis.clients.jedis.Jedis;

import java.io.IOException;
import java.util.List;

@Service
@RequiredArgsConstructor
@PropertySource("classpath:application.yml")
public class JwtLoginFilter extends OncePerRequestFilter {


    private Jedis jedis;

    @Value("${spring.redis.url}")
    private String redisURL;

    @PostConstruct
    public void init() {
        jedis = new Jedis(redisURL);
    }

    @Override
    protected void doFilterInternal(@NonNull HttpServletRequest request,
                                    @NonNull HttpServletResponse response,
                                    @NonNull FilterChain filterChain)
            throws ServletException, IOException {
        final String authHeader = request.getHeader("Authorization");
        final String jwt;
        final String username;

        if (authHeader == null || !authHeader.startsWith("Bearer ")) {
            response.sendError(HttpServletResponse.SC_UNAUTHORIZED, "Authorization header missing or invalid");
            return;
        }

        jwt = authHeader.substring(7);
        String requestURI = request.getRequestURI();
        username = extractUsernameFromPath(requestURI);

        if (username == null || jedis.get(username) == null) {
            response.sendError(HttpServletResponse.SC_UNAUTHORIZED, "User not authorized");
            return;
        }

        String storedJwt = jedis.get(username);
        if (!jwt.equals(storedJwt)) {
            response.sendError(HttpServletResponse.SC_UNAUTHORIZED, "Invalid token");
            return;
        }

        if (SecurityContextHolder.getContext().getAuthentication() == null) {
            UsernamePasswordAuthenticationToken authToken = new UsernamePasswordAuthenticationToken(
                    username, null, List.of(new SimpleGrantedAuthority("USER")));
            authToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
            SecurityContextHolder.getContext().setAuthentication(authToken);
        }
        filterChain.doFilter(request, response);
    }

    private String extractUsernameFromPath(String requestURI) {
        String[] parts = requestURI.split("/");
        if (parts.length > 2) {
            return parts[2];
        }
        return null;
    }
}