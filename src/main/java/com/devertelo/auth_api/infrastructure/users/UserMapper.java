package com.devertelo.auth_api.infrastructure.users;

import com.devertelo.auth_api.domain.UserResponse;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface UserMapper {

    UserResponse toUserResponse(UserEntity userEntity);
}
