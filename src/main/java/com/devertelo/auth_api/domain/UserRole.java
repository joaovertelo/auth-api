package com.devertelo.auth_api.domain;

import com.google.common.collect.Sets;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import java.util.Set;
import java.util.stream.Collectors;

import static com.devertelo.auth_api.domain.UserPermission.USER_READ;
import static com.devertelo.auth_api.domain.UserPermission.USER_WRITE;

public enum UserRole {

    ADMIN(Sets.newHashSet(USER_READ, USER_WRITE)),
    USER(Sets.newHashSet(USER_READ, USER_WRITE)),
    READ(Sets.newHashSet(USER_READ));

    private final Set<UserPermission> permissions;

    UserRole(Set<UserPermission> permissions) {
        this.permissions = permissions;
    }

    public Set<UserPermission> getPermissions() {
        return permissions;
    }

    public Set<GrantedAuthority> getGrantedAuthorities() {
        Set<GrantedAuthority> grantedAuthorities = getPermissions().stream()
                .map(userPermission -> new SimpleGrantedAuthority(userPermission.getPermission()))
                .collect(Collectors.toSet());
        grantedAuthorities.add(new SimpleGrantedAuthority("ROLE_" + this.name()));
        return grantedAuthorities;
    }
}
