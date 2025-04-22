package com.reservation.reservationsalle.security.jwt;

import com.reservation.reservationsalle.model.User;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import org.springframework.stereotype.Service;
import java.security.Key;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtException;
import org.springframework.security.core.userdetails.UserDetails;


@Service
public class JwtService {

    //  Clé secrète utilisée pour signer le JWT
    private static final String SECRET_KEY = "MaSuperCleJwtUltraSecreteQuiDoitEtreTresLongue1234567890"; // Min 256 bits

    //  Retourne la clé en objet Key utilisable
    private Key getSignInKey() {
        return Keys.hmacShaKeyFor(SECRET_KEY.getBytes());
    }

    //  Génère un JWT pour l'utilisateur
    public String generateToken(User user) {
        Map<String, Object> claims = new HashMap<>();
        claims.put("role", user.getRole()); // optionnel, si tu veux stocker le rôle

        return Jwts.builder()
                .setClaims(claims)
                .setSubject(user.getEmail()) // l'identifiant principal
                .setIssuedAt(new Date(System.currentTimeMillis())) // date de création
                .setExpiration(new Date(System.currentTimeMillis() + 1000 * 60 * 60 * 24)) // 24h
                .signWith(getSignInKey(), SignatureAlgorithm.HS256)
                .compact();
    }

    public String extractUsername(String token) {
        return extractAllClaims(token).getSubject();
    }

    public boolean isTokenValid(String token, UserDetails userDetails) {
        final String username = extractUsername(token);
        return (username.equals(userDetails.getUsername())) && !isTokenExpired(token);
    }

    // 🔓 Extraire toutes les infos du token (claims)
    private Claims extractAllClaims(String token) {
        return Jwts
                .parserBuilder()
                .setSigningKey(getSignInKey())
                .build()
                .parseClaimsJws(token)
                .getBody();
    }

    // 🕓 Vérifie si le token est expiré
    private boolean isTokenExpired(String token) {
        return extractAllClaims(token).getExpiration().before(new Date());
    }
}
