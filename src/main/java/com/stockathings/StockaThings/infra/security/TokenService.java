package com.stockathings.StockaThings.infra.security;


import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTCreationException;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.stockathings.StockaThings.domain.user.User;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.util.Date;
import java.util.UUID;

@Service
public class TokenService {

    @Value("${api.security.token.secret}")
    private String salzinho;

    private static final String ISSUER = "auth-api";

    public String getToken(User user){
        try {
            Algorithm algorithm = Algorithm.HMAC256(salzinho);
            String token = JWT.create()
                    .withIssuer(ISSUER)
                    .withSubject(user.getLogin())
                    .withExpiresAt(genExpirationDate()) //Tempo de expiração do token
                    .sign(algorithm); //assinatura e geração final
            return token;
        } catch (JWTCreationException exception) {
            throw new RuntimeException("Erro ao gerar token", exception);
        }
    }

    public String validateToken(String token){
        try{
            Algorithm algorithm = Algorithm.HMAC256(salzinho);
            return  JWT.require(algorithm)
                    .withIssuer("auth-api")
                    .build()
                    .verify(token)
                    .getSubject();
        } catch (JWTVerificationException exception) {
            return null;
        }
    }


    /*public String getJti(String token) {
        try {
            Algorithm algorithm = Algorithm.HMAC256(salzinho);
            DecodedJWT jwt = JWT.require(algorithm).withIssuer(ISSUER).build().verify(token);
            return jwt.getId();
        } catch (Exception e) {
            return null;
        }
    }
    public Instant getExpiration(String token) {
        try{
            Algorithm algorithm = Algorithm.HMAC256(salzinho);
            DecodedJWT jwt = JWT.require(algorithm).withIssuer(ISSUER).build().verify(token);
            Date d = jwt.getExpiresAt();
            return d != null ? d.toInstant() : null;
        } catch (Exception e) {
            return null;
        }
    }*/
    private Instant genExpirationDate(){
        return LocalDateTime.now().plusHours(2).toInstant(ZoneOffset.of("-03:00")); //Time zone de brasilia esse token durará 2 horas
    }
}
