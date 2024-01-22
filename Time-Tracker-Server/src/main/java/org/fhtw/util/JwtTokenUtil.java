package org.fhtw.util;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTDecodeException;
import com.auth0.jwt.exceptions.JWTVerificationException;
import org.fhtw.exception.AuthenticationException;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;

/**
 * The {@link JwtTokenUtil} class provides utility methods for creating and validating JSON Web Tokens (JWTs).
 * It uses the Auth0 JWT library to perform the underlying operations.
 * <p>
 * The class contains a method {@link JwtTokenUtil#getUsernameFromToken(String)} which takes a token as a parameter, decodes it and returns the subject of the token.
 * <p>
 * The class contains a method {@link JwtTokenUtil#getExpirationDateFromToken(String)} which takes a token as a parameter, decodes it and returns the expiration date of the token.
 * <p>
 * The class contains a method {@link JwtTokenUtil#validateToken(String, String)} which takes a token and a username as parameters, validates them and returns a boolean indicating if the token is valid or not.
 * <p>
 * The class contains a method {@link JwtTokenUtil#generateToken(String)} which takes a username as a parameter and generates a new token.
 */
public class JwtTokenUtil {

    private final String jwtSecret = "kavna/()6asd,g.mqljaeswf90/()";

    public String getUsernameFromToken(String token) throws AuthenticationException {
        try {
            return JWT.decode(token).getSubject();
        } catch (JWTDecodeException e) {
            throw new AuthenticationException("Invalid token: " + e.getMessage());
        }
    }

    public LocalDateTime getExpirationDateFromToken(String token) throws AuthenticationException {
        try {
            Date expiration = JWT.decode(token).getExpiresAt();
            return LocalDateTime.ofInstant(expiration.toInstant(), ZoneId.systemDefault());
        } catch (JWTDecodeException e) {
            throw new AuthenticationException("Invalid token: " + e.getMessage());
        }
    }

    public boolean validateToken(String token, String username) throws AuthenticationException {
        try {
            Algorithm algorithm = Algorithm.HMAC512(jwtSecret);
            JWT.require(algorithm)
                    .withSubject(username)
                    .build()
                    .verify(token);
            return !isTokenExpired(token);
        } catch (JWTVerificationException e) {
            throw new AuthenticationException("Invalid token: " + e.getMessage());
        }
    }

    private boolean isTokenExpired(String token) throws AuthenticationException {
        LocalDateTime expirationDate = getExpirationDateFromToken(token);
        return expirationDate.isBefore(LocalDateTime.now());
    }

    public String generateToken(String username) {
        int jwtExpiresIn = 60;
        Instant expiresAt = LocalDateTime.now().plusMinutes(jwtExpiresIn).atZone(ZoneId.systemDefault()).toInstant();

        Algorithm algorithm = Algorithm.HMAC512(jwtSecret);
        return JWT.create()
                .withSubject(username)
                .withExpiresAt(Date.from(expiresAt))
                .sign(algorithm);
    }

}