package dev.sosnovsky.applications.JWTaccess;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import jakarta.validation.constraints.NotNull;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import java.nio.charset.StandardCharsets;
import java.time.Duration;
import java.util.*;

@Component
public class JwtTokenUtils {
    private final String accessSecret = "applicationsapplicationsmoreapplications";
    private final String refreshSecret = "itisarefrashSecretTokenForMyapplication";

    @Value("${jwt.access.lifetime}")
    private int jwtAccessLifeTime;
    @Value("${jwt.refresh.lifetime}")
    private int jwtRefreshLifeTime;


    private final SecretKey accessKey = Keys.hmacShaKeyFor(accessSecret.getBytes(StandardCharsets.UTF_8));
    private final SecretKey refreshKey = Keys.hmacShaKeyFor(refreshSecret.getBytes(StandardCharsets.UTF_8));

    public String generateAccessToken(UserDetails userDetails) {
        Map<String, Object> claims = new HashMap<>();
        List<String> rolesList = userDetails
                .getAuthorities().stream().map(GrantedAuthority::getAuthority).toList();
        claims.put("roles", rolesList);

        Date issuedDate = new Date();
        Date expiredDate = new Date(issuedDate.getTime() + Duration.ofMinutes(jwtAccessLifeTime).toMillis());

        return Jwts.builder()
                .claims(claims)
                .subject(userDetails.getUsername())
                .issuedAt(issuedDate)
                .expiration(expiredDate)
                .signWith(accessKey)
                .compact();
    }

    public String generateRefreshToken(UserDetails userDetails) {
        Date issuedDate = new Date();
        Date expiredDate = new Date(issuedDate.getTime() + Duration.ofMinutes(jwtRefreshLifeTime).toMillis());

        return Jwts.builder()
                .subject(userDetails.getUsername())
                .issuedAt(issuedDate)
                .expiration(expiredDate)
                .signWith(refreshKey)
                .compact();
    }

    public String getUserNameFromAccessToken(@NotNull String token) {
        return getClaimsFromToken(token, accessKey).getSubject();
    }

    public String getUserNameFromRefreshToken(@NotNull String token) {
        return getClaimsFromToken(token, refreshKey).getSubject();
    }

    public List<String> getRolesFromAccessToken(@NotNull String token) {
        return getClaimsFromToken(token, accessKey).get("roles", List.class);
                //.stream().map(role -> role.);
    /*private static Set<Role> getRoles(Claims claims) {
        final List<String> roles = claims.get("roles", List.class);
        return roles.stream()
                .map(Role::valueOf)
                .collect(Collectors.toSet());*/
    }

    public Claims getClaimsFromToken(@NotNull String token, SecretKey key) {
        return Jwts.parser()
                .verifyWith(key)
                .build()
                .parseSignedClaims(token)
                .getPayload();
    }
}
