package dev.moudni.securityservice.web;

import org.springframework.boot.autoconfigure.neo4j.Neo4jProperties;
import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.jwt.JwtClaimsSet;
import org.springframework.security.oauth2.jwt.JwtEncoder;
import org.springframework.security.oauth2.jwt.JwtEncoderParameters;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;

@RestController
public class AuthController {
    private JwtEncoder jwtEncoder;

    public AuthController(JwtEncoder jwtEncoder) {
        this.jwtEncoder = jwtEncoder;
    }

    @PostMapping("/token")
    public Map<String, String> jwtToken(Authentication authentication){
        Map<String, String> idToken = new HashMap<>();
        Instant instant = Instant.now();
        String scope = authentication.getAuthorities()
                .stream().map(auth -> auth.getAuthority())
                .collect(Collectors.joining(" "));

        JwtClaimsSet jwtClaimsSet = JwtClaimsSet.builder()
                .subject(authentication.getName())
                .issuedAt(instant)
                .issuer("security-service")
                .expiresAt(instant.plus(5, ChronoUnit.MINUTES))
                .claim("scope", scope)
                .build();

        String jwtAccessToken = jwtEncoder.encode((JwtEncoderParameters.from(jwtClaimsSet))).getTokenValue();
        idToken.put("accessToken", jwtAccessToken);
        return idToken;
    }

}
