//package com.odk.Service.Interface.Service;
//
//import com.odk.Entity.Jwt;
//import com.odk.Entity.RefreshToken;
//import com.odk.Entity.Utilisateur;
//import com.odk.Repository.JwtRepository;
//import io.jsonwebtoken.Claims;
//import io.jsonwebtoken.Jwts;
//import io.jsonwebtoken.SignatureAlgorithm;
//import io.jsonwebtoken.io.Decoders;
//import io.jsonwebtoken.security.Keys;
//import lombok.AllArgsConstructor;
//import lombok.extern.slf4j.Slf4j;
//import org.springframework.scheduling.annotation.Scheduled;
//import org.springframework.security.core.context.SecurityContextHolder;
//import org.springframework.stereotype.Component;
//
//import java.security.Key;
//import java.time.Instant;
//import java.util.*;
//import java.util.function.Function;
//import java.util.stream.Collectors;
//
//@Slf4j
//@Component
//@AllArgsConstructor
//public class JwtUtile {
//    public static final String BEARER = "bearer";
//    public static final String REFRESH = "refresh";
//    public static final String TOKEN_INVALIDE = "Token invalide";
//    private final String ENCRIPTION_KEY = "26b084c8953fcfc9e23bb3342dae67e1ff9264180752bfbaefaa6601cd352939";
//    private UtilisateurService utilisateurService;
//    private JwtRepository jwtRepository;
//
//    public Map<String, String> generate(String username) {
//        Utilisateur utilisateur = (Utilisateur) this.utilisateurService.loadUserByUsername(username);
//        return this.generateJwt(utilisateur);
//    }
//
//    public String extractUsername(String token) {
//        return this.getClaim(token, Claims::getSubject);
//    }
//
//    public boolean isTokenExpired(String token) {
//        Date expirationDate = getExpirationDateFromToken(token);
//        return expirationDate.before(new Date());
//    }
//
//    private Date getExpirationDateFromToken(String token) {
//        return this.getClaim(token, Claims::getExpiration);
//    }
//
//    private <T> T getClaim(String token, Function<Claims, T> function) {
//        Claims claims = getAllClaims(token);
//        return function.apply(claims);
//    }
//
//    private Claims getAllClaims(String token) {
//        return Jwts.parserBuilder()
//                .setSigningKey(this.getKey())
//                .build()
//                .parseClaimsJws(token)
//                .getBody();
//    }
//
//    private Map<String, String> generateJwt(Utilisateur utilisateur) {
//        final long currentTime = System.currentTimeMillis();
//        final long expirationTime = currentTime + 30 * 60 * 1000;
//
//        final Map<String, Object> claims = Map.of(
//                "nom", utilisateur.getNom(),
//                "prenom", utilisateur.getPrenom(),
//                "phone", utilisateur.getPhone(),
//                "role", utilisateur.getRole(),
//                Claims.EXPIRATION, new Date(expirationTime),
//                Claims.SUBJECT, utilisateur.getEmail()
//        );
//
//        final String bearer = Jwts.builder()
//                .setIssuedAt(new Date(currentTime))
//                .setExpiration(new Date(expirationTime))
//                .setSubject(utilisateur.getEmail())
//                .setClaims(claims)
//                .signWith(getKey(), SignatureAlgorithm.HS256)
//                .compact();
//        return Map.of("bearer", bearer);
//    }
//
//    private Key getKey() {
//        final byte[] decoder = Decoders.BASE64.decode(ENCRIPTION_KEY);
//        return Keys.hmacShaKeyFor(decoder);
//    }
//
//
//
//}
