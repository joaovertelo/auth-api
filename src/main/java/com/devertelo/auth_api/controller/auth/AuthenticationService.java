package com.devertelo.auth_api.controller.auth;

import com.devertelo.auth_api.application.config.security.AppUserDetailsService;
import com.devertelo.auth_api.application.config.security.jwt.JwtService;
import com.devertelo.auth_api.application.exception.BadRequestException;
import com.devertelo.auth_api.domain.users.UserFactory;
import com.devertelo.auth_api.domain.users.UserResponse;
import com.devertelo.auth_api.domain.users.UserService;
import com.devertelo.auth_api.infrastructure.users.UserMapper;
import com.devertelo.auth_api.infrastructure.users.UserRepository;
import jakarta.validation.constraints.NotNull;
import lombok.RequiredArgsConstructor;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthenticationService {
    private final UserRepository userRepository;
    private final UserService userService;

    private final JwtService jwtService;
    private final AuthenticationManager authenticationManager;
    private final AppUserDetailsService appUserDetailsService;
    private final UserFactory userFactory;
    private final UserMapper userMapper;

    public AuthenticationResponse register(RegisterRequest request) {

        var user =
                userFactory.createEntityWithRoleUser(request.getUsername(), request.getEmail(), request.getPassword());

        try {
            var savedUser = userRepository.save(user);
            var userResponse = userMapper.toUserResponse(savedUser);
            var jwtToken = jwtService.generateToken(userResponse);
            var refreshToken = jwtService.generateRefreshToken(userResponse);
            return AuthenticationResponse.builder()
                    .accessToken(jwtToken)
                    .refreshToken(refreshToken)
                    .build();
        } catch (DataIntegrityViolationException dataIntegrityViolationException) {
            throw new BadRequestException(dataIntegrityViolationException.getMessage(), "erro");
        }
    }

    public AuthenticationResponse authenticate(AuthenticationRequest request) {
        var authenticateResponse = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(request.getLogin(), request.getPassword()));
        UserResponse userResponse = (UserResponse) authenticateResponse.getPrincipal();
        var jwtToken = jwtService.generateToken(userResponse);
        var refreshToken = jwtService.generateRefreshToken(userResponse);

        return AuthenticationResponse.builder()
                .accessToken(jwtToken)
                .refreshToken(refreshToken)
                .build();
    }

    public AuthenticationResponse refreshToken(@NotNull String refreshToken) {

        var username = jwtService.extractUsername(refreshToken);

        if (username == null) {
            throw new BadRequestException("Refresh token is invalid");
        }
        var user = this.userService.findByUsername(username)
                .orElseThrow(() -> new BadRequestException("User not found"));

        if (jwtService.isInvalidToken(refreshToken, user)) {
            throw new BadRequestException("Refresh token is invalid");
        }
        var accessToken = jwtService.generateToken(user);
        var newRefreshToken = jwtService.generateRefreshToken(user);

        return AuthenticationResponse.builder()
                .accessToken(accessToken)
                .refreshToken(newRefreshToken)
                .build();
    }
}
