package com.devertelo.auth_api.controller.auth;

import com.devertelo.auth_api.swagger.model.AuthApiRequest;
import com.devertelo.auth_api.swagger.model.AuthApiResponse;
import com.devertelo.auth_api.swagger.model.AuthRegisterApiRequest;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface AuthenticationMapper {

    RegisterRequest toRegisterRequest(AuthRegisterApiRequest authRegisterApiRequest);

    AuthApiResponse toAuthResponse(AuthenticationResponse authenticationResponse);

    AuthenticationRequest toAuthenticationRequest(AuthApiRequest authApiRequest);
}
