package com.devertelo.auth_api.domain.users;

import com.devertelo.auth_api.domain.UserRole;
import com.devertelo.auth_api.infrastructure.users.UserEntity;
import lombok.AllArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

@Component
@AllArgsConstructor
public class UserFactory {

    private final PasswordEncoder passwordEncoder;

    public UserEntity createEntity(String username, String email, String password, UserRole userRole) {
        return UserEntity.builder()
                .username(username)
                .email(email)
                .password(passwordEncoder.encode(password))
                .role(userRole)
                .isAccountNonExpired(true)
                .isAccountNonLocked(true)
                .isCredentialsNonExpired(true)
                .isEnabled(true)
                .createdAt(LocalDateTime.now())
                .updatedAt(LocalDateTime.now())
                .build();
    }

    public UserEntity createEntityWithRoleUser(String username, String email, String password) {
        return createEntity(username, email, password, UserRole.USER);
    }
}
