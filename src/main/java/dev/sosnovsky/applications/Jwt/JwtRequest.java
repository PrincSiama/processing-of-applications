package dev.sosnovsky.applications.Jwt;

import lombok.Data;

@Data
public class JwtRequest {
    private String login;
    private String password;
}
