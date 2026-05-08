package com.testing.bookspringapp.config;

import com.testing.bookspringapp.auth.AuthenticationService;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
@EnableMethodSecurity
@RequiredArgsConstructor
public class SecurityConfiguration {

    private final JwtAuthenticationFilter jwtAuthFilter;
    private final AuthenticationProvider authenticationProvider;

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http, AuthenticationService authenticationService) throws Exception {
        http
                // 1. Disable CSRF for stateless APIs
                .csrf(csrf -> csrf.disable())

                // 2. Configure endpoint permissions

                .authorizeHttpRequests(auth -> auth
                        .requestMatchers("/api/v1/auth/**").permitAll()
                        .requestMatchers(
                                "/swagger-ui/**",
                                "/swagger-ui.html",
                                "/v3/api-docs/**",
                                "/v3/api-docs",
                                "/webjars/**",
                                "/favicon.ico"
                        ).permitAll()
                        // user requests
                        .requestMatchers(HttpMethod.GET, "/api/v1/users").hasAuthority("ADMIN")
                        .requestMatchers(HttpMethod.GET, "/api/v1/users/**").hasAuthority("ADMIN")
                        .requestMatchers("/api/v1/users/**").authenticated()


                        // rating requests
                        .requestMatchers(HttpMethod.POST, "/api/v1/rates").authenticated()
                        .requestMatchers(HttpMethod.GET, "/api/v1/rates/book/**").permitAll()
                        .requestMatchers(HttpMethod.PUT, "/api/v1/rates/**").authenticated()
                        .requestMatchers(HttpMethod.DELETE, "/api/v1/rates/**").authenticated()
                        .requestMatchers("/api/v1/rates/**").authenticated()

                        // borrowing requests
                        .requestMatchers(HttpMethod.POST, "/api/v1/borrows").authenticated()
                        .requestMatchers(HttpMethod.GET, "/api/v1/borrows/me").authenticated()
                        .requestMatchers(HttpMethod.DELETE, "/api/v1/borrows/**").authenticated()
                        .requestMatchers("/api/v1/borrows/**").authenticated()

                        // books requests
                        .requestMatchers(HttpMethod.GET, "/api/v1/books").permitAll()
                        .requestMatchers(HttpMethod.GET, "/api/v1/books/**").permitAll()
                        .requestMatchers(HttpMethod.POST, "/api/v1/books").hasAuthority("ADMIN")
                        .requestMatchers(HttpMethod.PUT, "/api/v1/books/**").hasAuthority("ADMIN")
                        .requestMatchers(HttpMethod.DELETE, "/api/v1/books/**").hasAuthority("ADMIN")
                        .requestMatchers("/api/v1/books/**").authenticated()

                        .anyRequest().authenticated()
                )
                // 3. Set session to STATELESS (don't store user state in memory)
                .sessionManagement(session -> session
                        .sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                )

                // 4. Set the authentication provider and add the JWT filter
                .authenticationProvider(authenticationProvider)
                .logout(logout -> logout
                        .logoutUrl("/api/v1/auth/logout") // The endpoint to call
                        .addLogoutHandler(authenticationService) // Runs your custom logic
                        .logoutSuccessHandler((request, response, authentication) ->
                                response.setStatus(HttpServletResponse.SC_OK)) // Returns 200 OK
                )
                .addFilterBefore(jwtAuthFilter, UsernamePasswordAuthenticationFilter.class)
                .exceptionHandling(ex -> ex
                        .authenticationEntryPoint((request, response, authException) -> {
                            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
                            response.getWriter().write("Invalid or expired token");
                        })
                );

        return http.build();
    }
}