package dev.sosnovsky.applications.JWTaccess;

import dev.sosnovsky.applications.model.Role;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import org.springframework.lang.NonNull;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.encrypt.AesBytesEncryptor;
import org.springframework.stereotype.Component;

import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.SecretKeySpec;
import java.nio.charset.StandardCharsets;
import java.security.NoSuchAlgorithmException;
import java.security.Signature;
import java.security.interfaces.DSAKey;
import java.security.spec.EncodedKeySpec;
import java.security.spec.InvalidKeySpecException;
import java.time.Duration;
import java.util.*;

import static io.jsonwebtoken.Jwts.SIG.HS256;

@Component
@AllArgsConstructor
public class JwtTokenUtils {
//    @Value("${jwt.jwt.secret.access}")
    private final String secret = "applicationsapplicationsmoreapplications";

//    @Value("${jwt.access.lifetime}")
    private final Duration jwtLifeTime = Duration.ofMinutes(60);

    private final SecretKey key = Keys.hmacShaKeyFor(secret.getBytes(StandardCharsets.UTF_8));
    // "AES" or "RSA"
//    private final SecretKeySpec secretKey = new SecretKeySpec(secret.getBytes(StandardCharsets.UTF_8), "AES");

   /* SecretKey key = generateKey(256);

    public static SecretKey generateKey(int n) throws NoSuchAlgorithmException {
        KeyGenerator keyGenerator = KeyGenerator.getInstance("AES");
        keyGenerator.init(n);
        return keyGenerator.generateKey();
    }*/

    public String generateToken(UserDetails userDetails) {
        Map<String, Object> claims = new HashMap<>();
        List<String> rolesList = userDetails
                .getAuthorities().stream().map(GrantedAuthority::getAuthority).toList();
        claims.put("roles", rolesList);

        Date issuedDate = new Date();
        Date expiredDate = new Date(issuedDate.getTime() + jwtLifeTime.toMillis());

        return Jwts.builder()
                .claims(claims)
                .subject(userDetails.getUsername())
                .issuedAt(issuedDate)
                .expiration(expiredDate)
                .signWith(key)
                .compact();
    }

    public String getUserName(@NotNull String token) {
        return getClaimsFromToken(token).getSubject();
    }

    public List<String> getRoles(@NotNull String token) {
        return getClaimsFromToken(token).get("roles", List.class);
                //.stream().map(role -> role.);
    /*private static Set<Role> getRoles(Claims claims) {
        final List<String> roles = claims.get("roles", List.class);
        return roles.stream()
                .map(Role::valueOf)
                .collect(Collectors.toSet());*/
    }

    public Claims getClaimsFromToken(@NotNull String token) {
        return Jwts.parser()
                .verifyWith(key)
                .build()
                .parseSignedClaims(token)
                .getPayload();
    }
}
