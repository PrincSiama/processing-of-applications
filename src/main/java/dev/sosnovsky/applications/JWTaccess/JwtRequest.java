package dev.sosnovsky.applications.JWTaccess;

import lombok.Data;

@Data
public class JwtRequest {
    private String userName;
    private String password;
}
