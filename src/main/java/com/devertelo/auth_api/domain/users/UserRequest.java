package com.devertelo.auth_api.domain.users;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import java.io.Serial;
import java.io.Serializable;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class UserRequest implements Serializable {

    @Serial
    private static final long serialVersionUID = -5099767849656768036L;

    private String email;
    private String username;
    private String password;

}
