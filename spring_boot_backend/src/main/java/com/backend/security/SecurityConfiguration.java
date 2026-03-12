package com.backend.security;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import lombok.RequiredArgsConstructor;
import lombok.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import java.util.List;


@Configuration // To declare a java config class (equivalent to bean config xml file)
@EnableWebSecurity // to enable spring security
@EnableMethodSecurity // optional to add method level authorization rules
@RequiredArgsConstructor
public class SecurityConfiguration {
	
	private static final Logger log = LoggerFactory.getLogger(SecurityConfiguration.class);
	// ctor based D.I
	private final PasswordEncoder passwordEncoder;
	private final CustomJwtVerificationFilter jwtFilter;

	@Bean
	public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
	    log.info("******** Configuring Packers and Movers Security with Driver, Helper, Manager roles *******");

	    http
	    .cors(Customizer.withDefaults())   // ⭐⭐⭐ THIS LINE FIXES BROWSER ISSUE
	    .csrf(csrf -> csrf.disable())
	    .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS));
	    
	    http.authorizeHttpRequests(request ->
	        request
	            // 1. PUBLIC ENDPOINTS: Sabke liye khule hain
	            .requestMatchers(
	                "/v3/api-docs/**", 
	                "/swagger-ui/**", 
	                "/users/signin",           // Login API
	                "/users/logout",           // Logout API (token blacklisted server-side)
	                "/users/signup/**",        // Customer ya common signup
	                "/users/locations/**"      // Location APIs for state/city dropdown
	            ).permitAll()
	            .requestMatchers(HttpMethod.OPTIONS, "/**").permitAll()
	            // 2. MANAGER ONLY: Manager poore operation ko dekh sakta hai
	            .requestMatchers("/api/admin/**").hasRole("ADMIN")
	            .requestMatchers("/api/bookings/all").hasRole("STAFF")
	            .requestMatchers("/api/staff/manage").hasRole("STAFF")

	            // 3. DRIVER ONLY: Sirf driver apne tasks dekh sakta hai
	            .requestMatchers("/api/driver/my-trips").hasRole("DRIVER")
	            .requestMatchers("/api/driver/update-status").hasRole("DRIVER")

	            // 4. HELPER ONLY: Agar Helper ke liye alag APIs hain
	            .requestMatchers("/api/helper/attendance").hasRole("HELPER")
	            
	            // 5. CUSTOMER ONLY: Customer booking operations
	            .requestMatchers("/users/bookings/**").hasRole("CUSTOMER")

	            // 6. SHARED ACCESS: Manager aur Driver dono use kar sakein
	            .requestMatchers("/api/bookings/details/{id}").hasAnyRole("MANAGER", "DRIVER")

	            // 7. Baki sabhi requests ke liye Login zaroori hai
	            .anyRequest().authenticated()
	    )
	    // Custom JWT filter ko auth filter se pehle lagana
	    .addFilterBefore(jwtFilter, UsernamePasswordAuthenticationFilter.class);

	    return http.build();
	}

	// Configure AuthManager as spring bean
	@Bean
	AuthenticationManager authenticationManager(AuthenticationConfiguration config) throws Exception {
		return config.getAuthenticationManager();
	}
	

	
	@Bean
	public CorsConfigurationSource corsConfigurationSource() {
	    CorsConfiguration config = new CorsConfiguration();

	    config.setAllowedOriginPatterns(List.of(
	            "http://localhost:*",
	            "https://kd4-vaibhavi-92455.github.io"
	        ));

	    
	    config.setAllowedMethods(List.of("GET","POST","PUT","DELETE","OPTIONS"));
	    config.setAllowedHeaders(List.of("*"));
	    config.setAllowCredentials(true);

	    UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
	    source.registerCorsConfiguration("/**", config);
	    return source;
	}

}

//package com.backend.security;
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//import org.springframework.http.HttpMethod;
//import org.springframework.security.authentication.AuthenticationManager;
//import org.springframework.security.config.Customizer;
//import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
//import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
//import org.springframework.security.config.annotation.web.builders.HttpSecurity;
//import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
//import org.springframework.security.config.http.SessionCreationPolicy;
//import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
//import org.springframework.security.crypto.password.PasswordEncoder;
//import org.springframework.security.web.SecurityFilterChain;
//import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
//
//import lombok.RequiredArgsConstructor;
//import lombok.extern.slf4j.Slf4j;
//import lombok.*;
//
//import org.springframework.web.cors.CorsConfiguration;
//import org.springframework.web.cors.CorsConfigurationSource;
//import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
//import java.util.List;
//
//
//@Configuration // To declare a java config class (equivalent to bean config xml file)
//@EnableWebSecurity // to enable spring security
//@EnableMethodSecurity // optional to add method level authorization rules
//@RequiredArgsConstructor
//@Slf4j
//public class SecurityConfiguration {
//	// ctor based D.I
//	private final PasswordEncoder passwordEncoder;
//	private final CustomJwtVerificationFilter jwtFilter;
//
//	/*
//	 * Configure Spring sec filter chain as a spring bean (@Bean) , to override the
//	 * spring sec defaults - Disable CSRF protection - Disable HttpSession - Disable
//	 * login / logout page generation (i.e disable form login) - retain Basic
//	 * Authentication scheme. - Add authorization rules - swagger , sign in , sign
//	 * up , listing doctors.. - public end points - any other request - authenticate
//	 * Add HttpSecurity as the dependency - to build sec filter chain
//	 */
//	
//	@Bean
//	public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
//	    log.info("******** Configuring Packers and Movers Security with Driver, Helper, Manager roles *******");
//
//	    // CSRF aur Session management ko stateless (JWT) set karna
////	    http.csrf(csrf -> csrf.disable())
////	        .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS));
//
//	    http
//	    .cors(Customizer.withDefaults())   // ⭐⭐⭐ THIS LINE FIXES BROWSER ISSUE
//	    .csrf(csrf -> csrf.disable())
//	    .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS));
//	    
//	    http.authorizeHttpRequests(request ->
//	        request
//	            // 1. PUBLIC ENDPOINTS: Sabke liye khule hain
//	            .requestMatchers(
//	                "/v3/api-docs/**", 
//	                "/swagger-ui/**", 
//	                "/users/signin",           // Login API
//	                "/users/signup/**",        // Customer ya common signup
//	                "/users/locations/**"      // Location APIs for state/city dropdown
//	               // "/staff/register-driver" // Driver registration link
//	            ).permitAll()
//	            .requestMatchers(HttpMethod.OPTIONS, "/**").permitAll()
//	            // 2. MANAGER ONLY: Manager poore operation ko dekh sakta hai
//	            .requestMatchers("/api/admin/**").hasRole("ADMIN")
//	            .requestMatchers("/api/bookings/all").hasRole("STAFF")
//	            .requestMatchers("/api/staff/manage").hasRole("STAFF")
//
//	            // 3. DRIVER ONLY: Sirf driver apne tasks dekh sakta hai
//	            .requestMatchers("/api/driver/my-trips").hasRole("DRIVER")
//	            .requestMatchers("/api/driver/update-status").hasRole("DRIVER")
//
//	            // 4. HELPER ONLY: Agar Helper ke liye alag APIs hain
//	            .requestMatchers("/api/helper/attendance").hasRole("HELPER")
//	            
//	            // 5. CUSTOMER ONLY: Customer booking operations
//	            .requestMatchers("/users/bookings/**").hasRole("CUSTOMER")
//
//	            // 6. SHARED ACCESS: Manager aur Driver dono use kar sakein
//	            .requestMatchers("/api/bookings/details/{id}").hasAnyRole("MANAGER", "DRIVER")
//
//	            // 7. Baki sabhi requests ke liye Login zaroori hai
//	            .anyRequest().authenticated()
//	    )
//	    // Custom JWT filter ko auth filter se pehle lagana
//	    .addFilterBefore(jwtFilter, UsernamePasswordAuthenticationFilter.class);
//
//	    return http.build();
//	}
//
//	// Configure AuthManager as spring bean
//	@Bean
//	AuthenticationManager authenticationManager(AuthenticationConfiguration config) throws Exception {
//		return config.getAuthenticationManager();
//	}
//	
////	@Bean
////	public CorsConfigurationSource corsConfigurationSource() {
////	    CorsConfiguration config = new CorsConfiguration();
////	    config.setAllowedOrigins(List.of(
////	            "http://localhost:5173",
////	            "https://kd4-vaibhavi-92455.github.io/project/",
////	            "https://kd4-vaibhavi-92455.github.io/"
////	        ));
////	    
////	    config.setAllowedMethods(List.of("GET", "POST", "PUT", "DELETE", "OPTIONS"));
////	    config.setAllowedHeaders(List.of("*"));
////	    config.setAllowCredentials(true);
////
////	    UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
////	    source.registerCorsConfiguration("/**", config);
////	    return source;
////	}
//	
//	@Bean
//	public CorsConfigurationSource corsConfigurationSource() {
//	    CorsConfiguration config = new CorsConfiguration();
//	    config.setAllowedOrigins(List.of(
//	        "http://localhost:5173",
//	        "https://kd4-vaibhavi-92455.github.io",
//	        "https://kd4-vaibhavi-92455.github.io/project"
//	    ));
//	    config.setAllowedMethods(List.of("GET","POST","PUT","DELETE","OPTIONS"));
//	    config.setAllowedHeaders(List.of("*"));
//	    config.setAllowCredentials(true);
//
//	    UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
//	    source.registerCorsConfiguration("/**", config);
//	    return source;
//	}
//
//}
