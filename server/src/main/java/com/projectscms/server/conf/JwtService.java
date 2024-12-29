package com.projectscms.server.conf;

import com.projectscms.server.users.User;
import io.jsonwebtoken.Claims;

import java.util.Map;
import java.util.function.Function;

public interface JwtService {

        String generateToken(User user);
        String generateToken(Map<String, Object> extraClaims, String email);
        <T> T extractClaim(String token, Function<Claims, T> claimsResolver);
        String extractUserName(String token);
}