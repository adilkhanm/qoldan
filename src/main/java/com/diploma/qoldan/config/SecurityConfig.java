package com.diploma.qoldan.config;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import java.util.Arrays;
import java.util.List;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfig {

    private final JwtAuthenticationFilter jwtAuthFilter;
    private final AuthenticationProvider authenticationProvider;

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http.cors().and()
                .csrf()
                .disable()
                .authorizeHttpRequests()

                .requestMatchers("auth/register/privileged")
                .hasAuthority("ROLE_ADMIN")

                .requestMatchers("/auth/**", "/swagger-ui/**", "/v3/**")
                .permitAll()

                .requestMatchers("/donation-announcements/my")
                .hasAuthority("ROLE_ORGANIZATION")

                .requestMatchers(HttpMethod.GET, "/product/**", "/category", "/tag", "/product-type", "/image/**", "/donation-announcements/**")
                .permitAll()

                .requestMatchers("/category/**", "/tag/**", "/product-type/**")
                .hasAuthority("ROLE_ADMIN")

                .requestMatchers("/donation-announcements/**", "/donations/to-organization/**")
                .hasAuthority("ROLE_ORGANIZATION")

                .requestMatchers(HttpMethod.POST, "/organizations/**")
                .hasAuthority("ROLE_ADMIN")

                .requestMatchers(HttpMethod.PUT, "/organizations/**")
                .hasAnyAuthority("ROLE_ORGANIZATION", "ROLE_ADMIN")

                .requestMatchers("/**")
                .hasAnyAuthority("ROLE_USER", "ROLE_ADMIN", "ROLE_ORGANIZATION")

                .anyRequest()
                .authenticated()
                .and()
                .sessionManagement()
                .sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                .and()
                .authenticationProvider(authenticationProvider)
                .addFilterBefore(jwtAuthFilter, UsernamePasswordAuthenticationFilter.class);

        return http.build();
    }
}
