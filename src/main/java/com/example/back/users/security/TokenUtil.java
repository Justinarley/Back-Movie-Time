package com.example.back.users.security;

import java.util.Date;
import java.security.Key;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.security.Keys;
import io.jsonwebtoken.SignatureAlgorithm;

public class TokenUtil {
    
    // Define una clave secreta para la firma de los tokens
    private static final Key SECRET_KEY = Keys.secretKeyFor(SignatureAlgorithm.HS256); // Genera una clave secreta

    // Generar un token con el correo del usuario
    public static String generateToken(String correo) {
        return Jwts.builder()
                .setSubject(correo)  // Establece el correo como el sujeto del token
                .setIssuedAt(new Date())  // Fecha de emisión
                .setExpiration(new Date(System.currentTimeMillis() + 86400000))  // Expiración del token (24 horas)
                .signWith(SECRET_KEY)  // Firma del token con la clave secreta
                .compact();  // Devuelve el token compacto como String
    }

    // Decodificar el token para obtener el correo
    public static String decodeToken(String token) {
        Claims claims = Jwts.parserBuilder()
                .setSigningKey(SECRET_KEY)  // Establece la clave secreta para la validación
                .build()
                .parseClaimsJws(token)  // Decodifica el token
                .getBody();  // Obtiene los cuerpos de las reclamaciones (claims)
        return claims.getSubject();  // Retorna el correo como el sujeto
    }
}
