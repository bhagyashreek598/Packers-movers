package com.backend.security;

import java.io.IOException;
import java.util.List;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import com.backend.dtos.JwtDTO;

import io.jsonwebtoken.Claims;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Component
@RequiredArgsConstructor
public class CustomJwtVerificationFilter extends OncePerRequestFilter {
    
    private static final Logger log = LoggerFactory.getLogger(CustomJwtVerificationFilter.class);

    private final JwtUtils jwtUtils;
    private final TokenBlacklistService tokenBlacklistService;
    
    @Override
    protected void doFilterInternal(HttpServletRequest request,
                                    HttpServletResponse response,
                                    FilterChain filterChain)
            throws ServletException, IOException {

        String authHeader = request.getHeader("Authorization");

        if (authHeader != null && authHeader.startsWith("Bearer ")) {
            String jwt = authHeader.substring(7).trim();

            try {
                // 1. Check token is not blacklisted (user already logged out)
                if (tokenBlacklistService.isBlacklisted(jwt)) {
                    log.warn("Rejected blacklisted (logged-out) token");
                    response.sendError(HttpServletResponse.SC_UNAUTHORIZED, "Token has been invalidated. Please login again.");
                    return;
                }

                Claims claims = jwtUtils.validateToken(jwt);

                String email = claims.getSubject();
                Long userId = claims.get("user_id", Long.class);
                String role = claims.get("user_role", String.class);

                String formattedRole =
                        role.startsWith("ROLE_") ? role : "ROLE_" + role;

                // Use CustomerPrincipal as the concrete subclass here.
                // The JWT filter only needs userId/email/authorities from the token —
                // not staffRole details — so CustomerPrincipal is safe for both CUSTOMER & STAFF roles.
                // This is still polymorphism: we hold a UserPrincipal reference to a concrete subclass.
                UserPrincipal principal = new CustomerPrincipal(
                        userId,
                        email,
                        null,
                        List.of(new SimpleGrantedAuthority(formattedRole)),
                        role.replace("ROLE_", "")
                );

                Authentication authentication =
                        new UsernamePasswordAuthenticationToken(
                                principal,
                                null,
                                principal.getAuthorities()
                        );

                SecurityContextHolder.getContext().setAuthentication(authentication);

            } catch (Exception e) {
                log.error("JWT Verification failed: {}", e.getMessage());
            }
        }

        filterChain.doFilter(request, response);
    }


//    @Override
//    protected void doFilterInternal(HttpServletRequest request,
//                                    HttpServletResponse response,
//                                    FilterChain filterChain)
//            throws ServletException, IOException {
//
//        String path = request.getServletPath();
//
//        // 1. Skip Public Endpoints
//        if (path.equals("/users/signin") || path.startsWith("/users/signup")) {
//            filterChain.doFilter(request, response);
//            return;
//        }
//
//        String authHeader = request.getHeader("Authorization");
//
//        // 2. Token Extraction & Role Correction
//        if (authHeader != null && authHeader.startsWith("Bearer ")) {
//            // .trim() add kiya gaya hai whitespace error hatane ke liye
//            String jwt = authHeader.substring(7).trim(); 
//            
//            try {
//                Claims claims = jwtUtils.validateToken(jwt);
//
//                String email = claims.getSubject();
//                Long userId = claims.get("user_id", Long.class);
//                String role = claims.get("user_role", String.class); // Role can be "ADMIN" or "ROLE_ADMIN"
//
//                // 🔥 Role handling fix: 
//                // Agar role pehle se "ROLE_" se shuru hota hai toh waisa hi rehne do, 
//                // warna "ROLE_" prefix add karo.
//                String formattedRole = role.startsWith("ROLE_") ? role : "ROLE_" + role;
//
//                // Create full UserPrincipal for Spring Security
//                UserPrincipal principal = new UserPrincipal(
//                        userId,
//                        email,
//                        null,
//                        List.of(new SimpleGrantedAuthority(formattedRole)),
//                        role.replace("ROLE_", "")
//                );
//
//                Authentication authentication =
//                        new UsernamePasswordAuthenticationToken(
//                                principal,
//                                null,
//                                principal.getAuthorities()
//                        );
//
//                SecurityContextHolder.getContext().setAuthentication(authentication);
//                log.info("Authenticated user {} with role {}", email, formattedRole);
//                
//            } catch (Exception e) {
//                log.error("JWT Verification failed: {}", e.getMessage());
//                // Optional: Yahan error response bhi bhej sakte hain
//            }
//        }
//
//        filterChain.doFilter(request, response);
//    }
}