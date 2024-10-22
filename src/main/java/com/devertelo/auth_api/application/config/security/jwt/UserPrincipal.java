package com.devertelo.auth_api.application.config.security.jwt;

import lombok.*;

import java.io.Serializable;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UserPrincipal implements Serializable {

    private Long userId;
    private String username;
    private String email;
}
