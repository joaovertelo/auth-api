package com.devertelo.auth_api.controller.auth;


import com.devertelo.auth_api.swagger.api.AuthApi;
import com.devertelo.auth_api.swagger.model.AuthApiRequest;
import com.devertelo.auth_api.swagger.model.AuthApiResponse;
import com.devertelo.auth_api.swagger.model.AuthRegisterApiRequest;
import com.devertelo.auth_api.swagger.model.RefreshTokenApiRequest;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class AuthenticationController implements AuthApi {

    private final AuthenticationService authenticationService;
    private final AuthenticationMapper authenticationMapper;
    private final HttpServletRequest httpServletRequest;
    private final HttpServletResponse httpServletResponse;

    @Override
    public ResponseEntity<AuthApiResponse> registerUser(AuthRegisterApiRequest authRegisterApiRequest) {
        var registerRequest = authenticationMapper.toRegisterRequest(authRegisterApiRequest);
        var authenticationResponse = authenticationService.register(registerRequest);
        var response = authenticationMapper.toAuthResponse(authenticationResponse);
        return ResponseEntity.ok(response);
    }

    @Override
    public ResponseEntity<AuthApiResponse> authenticate(AuthApiRequest authRequest) {
        var authenticationRequest = authenticationMapper.toAuthenticationRequest(authRequest);
        var authenticationResponse = authenticationService.authenticate(authenticationRequest);
        var response = authenticationMapper.toAuthResponse(authenticationResponse);
        return ResponseEntity.ok(response);
    }

    @Override
    public ResponseEntity<AuthApiResponse> refreshToken(RefreshTokenApiRequest refreshTokenApiRequest) {

        var authenticationResponse = authenticationService.refreshToken(refreshTokenApiRequest.getRefreshToken());

        var authApiResponse = new AuthApiResponse().accessToken(authenticationResponse.getAccessToken())
                .refreshToken(authenticationResponse.getRefreshToken());

        return ResponseEntity.ok(authApiResponse);
    }

}
