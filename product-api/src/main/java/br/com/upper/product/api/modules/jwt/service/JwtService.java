package br.com.upper.product.api.modules.jwt.service;

import org.apache.logging.log4j.util.Strings;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;

import br.com.upper.product.api.config.AuthorizationException;
import br.com.upper.product.api.modules.jwt.dto.JwtResponse;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;

@Service
public class JwtService {

    @Value("${app-config.secrets.api-secret}")
    private String apiSecret;

    private static final String EMPTY_SPACE = " ";
    private static final Integer TOKEN_INDEX = 2;

    public void validateAuthorization(String token) {
        var accessToken = extractToken(token);
        try {
            var claims = Jwts.parserBuilder()
                    .setSigningKey(Keys.hmacShaKeyFor(apiSecret.getBytes()))
                    .build()
                    .parseClaimsJws(accessToken)
                    .getBody();
            var user = JwtResponse.getUser(claims);
            if (ObjectUtils.isEmpty(user) || ObjectUtils.isEmpty(user.getId())) {
                throw new AuthorizationException("The user is not valid.");
            }
        } catch (Exception ex) {
            ex.printStackTrace();
            throw new AuthorizationException("Error while trying to proccess the access token.");
        }
    }

    private String extractToken(String token) {
        if (ObjectUtils.isEmpty(token)) {
            throw new AuthorizationException("The access token was not informed");
        }
        if (token.toLowerCase().contains(EMPTY_SPACE)) {
            return token.split(EMPTY_SPACE)[TOKEN_INDEX];
        }
        return token;
    }
}
