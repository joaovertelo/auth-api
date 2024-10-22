package com.devertelo.auth_api.application.config.security;

import com.devertelo.auth_api.application.config.security.jwt.JwtAuthenticationFilter;
import lombok.AllArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.CsrfConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;


@Configuration
@EnableWebSecurity
@EnableMethodSecurity
@AllArgsConstructor
public class RouterSecurityConfig {

    private final JwtAuthenticationFilter jwtAuthenticationFilter;
    private final AuthenticationProvider authenticationProvider;


    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity httpSecurity) throws Exception {
        httpSecurity
                .csrf(CsrfConfigurer::disable)
                .authorizeHttpRequests(authorize -> authorize
                                .anyRequest()
                                .permitAll()
//                                .requestMatchers("/v3/api-docs/**").permitAll()
//                                .requestMatchers("/swagger-ui/**").permitAll()
//                                .requestMatchers("/auth/**").permitAll()
//                                .requestMatchers(HttpMethod.POST, "/users").permitAll()
//                        .requestMatchers(HttpMethod.GET, "/users/admin").hasAnyAuthority(UserRole.ADMIN.name())
//                                .anyRequest().authenticated()
                )
                .sessionManagement(
                        sessionManagement -> sessionManagement.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .authenticationProvider(authenticationProvider)
                .addFilterBefore(jwtAuthenticationFilter, UsernamePasswordAuthenticationFilter.class);
//                .logout()
//                .logoutUrl("/api/v1/auth/logout")
//                .addLogoutHandler(logoutHandler)
//                .logoutSuccessHandler((request, response, authentication) -> SecurityContextHolder.clearContext());

        return httpSecurity.build();
    }

}
