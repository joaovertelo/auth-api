package com.devertelo.auth_api.application.config.security;

import com.devertelo.auth_api.domain.users.UserResponse;
import com.devertelo.auth_api.infrastructure.users.UserEntity;
import com.devertelo.auth_api.infrastructure.users.UserMapper;
import com.devertelo.auth_api.infrastructure.users.UserRepository;
import lombok.AllArgsConstructor;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import static java.lang.String.format;

@AllArgsConstructor
@Service
public class AppUserDetailsService implements UserDetailsService {

    private final UserRepository usuarioRepository;
    private final UserMapper userMapper;

    @Override
    public UserResponse loadUserByUsername(String username) throws UsernameNotFoundException {

        UserEntity userEntity = usuarioRepository.findByUsername(username)
                .orElseThrow(() -> {
                    String msgErro = format("Usuario %s não encontrado", username);
                    return new UsernameNotFoundException(msgErro);
                });

        return userMapper.toUserResponse(userEntity);
    }


}
