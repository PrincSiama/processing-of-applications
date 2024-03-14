package dev.sosnovsky.applications.Jwt;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class JwtRequest {
    private String userName;
    private String password;
}
