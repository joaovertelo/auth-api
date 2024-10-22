package com.devertelo.auth_api.domain.users;

import com.devertelo.auth_api.infrastructure.users.UserMapper;
import com.devertelo.auth_api.infrastructure.users.UserRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@AllArgsConstructor
@Service
public class UserServiceImpl implements UserService {

    private final UserRepository repository;
    private final UserFactory factory;
    private final UserMapper mapper;

    @Override
    public Optional<UserResponse> findById(Long id) {
        return this.repository.findById(id)
                .map(mapper::toUserResponse);
    }

    @Override
    public Optional<UserResponse> findByUsername(String username) {
        return this.repository.findByUsername(username)
                .map(mapper::toUserResponse);

    }

    @Override
    public void delete(Long id) {
        this.repository.deleteById(id);
    }

    @Override
    public UserResponse createWithUserRole(UserRequest user) {
        var withRoleUSER = factory.createEntityWithRoleUser(user.getUsername(), user.getEmail(), user.getPassword());
        var entity = repository.save(withRoleUSER);
        return mapper.toUserResponse(entity);
    }


}
