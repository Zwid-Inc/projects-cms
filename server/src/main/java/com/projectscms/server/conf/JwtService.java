package com.projectscms.server.auth;

import com.projectscms.server.users.User;
import com.projectscms.server.users.UserRepository;
import org.hibernate.cfg.Environment;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import javax.crypto.SecretKey;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.Date;
import java.util.Map;
import java.util.function.Function;

public interface JwtService {

        String generateToken(User user);
        String generateToken(Map<String, Object> extraClaims, String email);
        <T> T extractClaim(String token, Function<Claims, T> claimsResolver);
        String extractUserName(String token);
}