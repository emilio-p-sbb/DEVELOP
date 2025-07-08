package com.portofolio.auth.security;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;

import com.portofolio.auth.jwt.JwtAuthEntryPoint;
import com.portofolio.auth.jwt.JwtAuthFilter;

import lombok.RequiredArgsConstructor;

@Configuration
@EnableWebSecurity
@EnableMethodSecurity
@RequiredArgsConstructor
public class SecurityConfiguration {
    public static final String SWAGGER_UI_URL = "/swagger-ui/**";
    public static final String API_DOCS_URL = "/v3/api-docs/**";
    public static final String[] ALLOWED_URLS = {
            SWAGGER_UI_URL, API_DOCS_URL
    };
    private final JwtAuthFilter jwtAuthFilter;
    private final JwtAuthEntryPoint jwtAuthEntryPoint;
    
    private static final String[] WHITELIST = {
            "/api/users/signup", 
            "/api/messages/save", 
            "/api/skills/public", 
            "/api/experiences/public", 
            "/api/educations/public", 
            "/api/projects/public", 
            "/api/users/1", 
            "/api/auth/**",
            "/api/manager/token",
            "/api/manager/user/**",
            "/swagger-ui/**",
            "/swagger-ui.html",
            "/v3/api-docs/**",
            "/swagger-resources/**",
            "/webjars/**"
    };

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration configuration) throws Exception {
        return configuration.getAuthenticationManager();
    }
    
    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http.csrf(AbstractHttpConfigurer::disable);

        http.cors(cors -> cors.configurationSource(request -> {
            CorsConfiguration configuration = new CorsConfiguration();
            configuration.setAllowedOrigins(Collections.singletonList("http://localhost:3000"));
//            configuration.setAllowedMethods(Collections.singletonList("*"));
            configuration.setAllowedMethods(Arrays.asList("GET", "POST", "PUT", "DELETE", "OPTIONS"));
            configuration.setAllowedHeaders(Collections.singletonList("*"));
            configuration.setAllowCredentials(true); // ✅ INI PENTING
            configuration.setAllowedHeaders(List.of("Authorization","Content-Type"));
            configuration.setMaxAge(3600L);
            return configuration;
        }));

        http.authorizeHttpRequests(authorize -> authorize
                .requestMatchers(WHITELIST).permitAll()

                // Beri akses role-based hanya ke endpoint non-public
//                .requestMatchers(HttpMethod.GET, "/api/users/**").hasAuthority(Permissions.USER_READ.getName())
//                .requestMatchers(HttpMethod.POST, "/api/users/**").hasAuthority(Permissions.USER_CREATE.getName())
//                .requestMatchers(HttpMethod.PUT, "/api/users/**").hasAuthority(Permissions.USER_UPDATE.getName())
//                .requestMatchers(HttpMethod.DELETE, "/api/users/**").hasAuthority(Permissions.USER_DELETE.getName())

                .anyRequest().authenticated()
        );

        http.sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS));

        http.exceptionHandling(exception -> exception.authenticationEntryPoint(jwtAuthEntryPoint));

        http.addFilterBefore(jwtAuthFilter, UsernamePasswordAuthenticationFilter.class);

        return http.build();
    }
    
//    @Bean
//    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
//        http
//                .csrf(AbstractHttpConfigurer::disable);
//        http
//                .cors(cors -> cors.configurationSource(request -> {
//                    CorsConfiguration configuration = new CorsConfiguration();
//                    configuration.setAllowedOrigins(Collections.singletonList("http://localhost:3000"));
//                    configuration.setAllowedMethods(Collections.singletonList("*"));
//                    configuration.setAllowCredentials(true);
//                    configuration.setAllowedHeaders(Collections.singletonList("*"));
//                    configuration.setMaxAge(3600L);
//                    return configuration;
//                }));
//        http
//                .authorizeHttpRequests(authorize -> {
//                    authorize.requestMatchers(ALLOWED_URLS).permitAll();
//                    authorize.requestMatchers(HttpMethod.POST,"/api/users/signup").permitAll();
////                    authorize.requestMatchers("/api/auth/login").permitAll();
////                    authorize.requestMatchers("/api/auth/refresh").permitAll();
//                    authorize.requestMatchers(
//                            "/api/auth/**",
//                            "/api/manager/token",
//                            "/api/manager/user/**",
//                            "/swagger-ui/**",
//                            "/swagger-ui.html",
//                            "/v3/api-docs/**",
//                            "/swagger-resources/**",
//                            "/webjars/**"
//                        ).permitAll();
////                    authorize.requestMatchers(HttpMethod.GET, "/api/users/**").hasAuthority(Permissions.USER_READ.getName());
////                    authorize.requestMatchers(HttpMethod.POST, "/api/users/**").hasAuthority(Permissions.USER_CREATE.getName());
////                    authorize.requestMatchers(HttpMethod.PUT, "/api/users/**").hasAuthority(Permissions.USER_UPDATE.getName());
////                    authorize.requestMatchers(HttpMethod.DELETE, "/api/users/**").hasAuthority(Permissions.USER_DELETE.getName());
////                    authorize.anyRequest().permitAll();
//                    authorize.anyRequest().authenticated()
//                    ;
//                });
//        http
//                .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS));
//        http
//                .exceptionHandling(exception -> exception.authenticationEntryPoint(jwtAuthEntryPoint));
//        http
////                .formLogin(login -> login.loginProcessingUrl("/api/auth/login"))
//                .addFilterBefore(jwtAuthFilter, UsernamePasswordAuthenticationFilter.class);
//        return http.build();
//    }
    @Bean
    public static PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}
