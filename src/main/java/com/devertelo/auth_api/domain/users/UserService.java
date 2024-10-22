package com.devertelo.auth_api.domain.users;

import java.util.Optional;

public interface UserService {

    Optional<UserResponse> findById(Long id);

    Optional<UserResponse> findByUsername(String username);

    void delete(Long id);

    UserResponse createWithUserRole(UserRequest user);

}
