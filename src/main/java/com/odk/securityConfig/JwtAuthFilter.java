package com.odk.securityConfig;

import com.nimbusds.jose.jwk.source.ImmutableSecret;
import com.odk.Entity.Utilisateur;
import com.odk.Service.Interface.Service.UtilisateurService;
import lombok.AllArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.oauth2.jose.jws.MacAlgorithm;
import org.springframework.security.oauth2.jwt.*;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerExceptionResolver;

import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;
import java.nio.charset.StandardCharsets;
import java.time.Instant;
import java.time.temporal.ChronoUnit;

@Component
@AllArgsConstructor
public class JwtAuthFilter {

    private final HandlerExceptionResolver handlerExceptionResolver;
    private UtilisateurService utilisateurService;
//    private JwtUtile jwtService;

    private final String JWT_SECRET = "N6pMChrUeAVcYLJJaQjfcB7fIcxJkQR7ClWraTEK8dPQFwKfb85KIQH6Fc4PbVVuy0Oi2GFCB9ETRJFjXQShDA";

    private final long JWT_EXPIRATION_MS = 86400000;  // Validité du token (1 jour)

    // Générer le token JWT en incluant les informations de l'utilisateur et de son organisation
    public String generateToken(Authentication authentication, Utilisateur utilisateur, JwtEncoder jwtEncoder) {

        UserDetails userDetails = (UserDetails) authentication.getPrincipal();

        Instant now = Instant.now();

        // Générer le JWT
        JwtClaimsSet jwtClaimsSet = JwtClaimsSet.builder()
                .subject(userDetails.getUsername())
                .claim("email", utilisateur.getEmail())
                .claim("id", utilisateur.getId())
                .claim("role", utilisateur.getRole())
                .issuedAt(now)
                .expiresAt(now.plus(30, ChronoUnit.DAYS))
                .build();

        JwtEncoderParameters jwtEncoderParameters = JwtEncoderParameters.from(JwsHeader.with(MacAlgorithm.HS512).type("jwt").build(), jwtClaimsSet);

        return jwtEncoder.encode(jwtEncoderParameters).getTokenValue();
    }

    @Bean
    JwtEncoder jwtEncoder() {
        return new NimbusJwtEncoder(new ImmutableSecret<>(JWT_SECRET.getBytes(StandardCharsets.UTF_8)));
    };

    @Bean
    JwtDecoder jwtDecoder() {
        SecretKey secretKey = new SecretKeySpec(JWT_SECRET.getBytes(StandardCharsets.UTF_8), "HmacSHA512");
        return NimbusJwtDecoder.withSecretKey(secretKey).macAlgorithm(MacAlgorithm.HS512).build();
    };

}
