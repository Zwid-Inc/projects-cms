package com.projectscms.server.auth;

import com.projectscms.server.users.User;
import com.projectscms.server.users.UserRepository;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import lombok.AllArgsConstructor;
import org.hibernate.cfg.Environment;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import javax.crypto.SecretKey;
import java.io.Console;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.Date;
import java.util.Map;
import java.util.function.Function;

@Service
@AllArgsConstructor
public class JwtServiceImpl implements JwtService{

    private final UserRepository userRepository;
    private static final String SECRET_KEY = Environment.getProperties().getProperty("SECRET_KEY");
    // private static final String SECRET_KEY="11cddb9cffc86f87a24265433dfe099bf3473ee295c84f99cfdb620609213655";
    private SecretKey getSignKey(){
        return Keys.hmacShaKeyFor(Decoders.BASE64.decode(SECRET_KEY));
    }
    public String generateToken(User user){
        //User user = userRepository.findByEmail(email).orElseThrow();
        System.out.println("============================================================================");
        System.out.println(SECRET_KEY);
        System.out.println("============================================================================");
        return generateToken(Map.of("id", user.getUserId()), user.getEmail());
    }
    public String generateToken(Map<String, Object> extraClaims, String email){
        final Instant now = Instant.now();
        final Instant expiry = now.plus(24, ChronoUnit.HOURS);

        return Jwts
                .builder()
                .setClaims(extraClaims)
                .setSubject(email)
                .setIssuedAt(Date.from(now))
                .setExpiration(Date.from(expiry))
                .signWith(getSignKey(), SignatureAlgorithm.HS256)
                .compact();
    }
    private Claims extractAllClaims(String token){
        return Jwts.parser()
                .setSigningKey(getSignKey())
                .build()
                .parseClaimsJws(token)
                .getBody();
    }
    public <T> T extractClaim(String token, Function<Claims, T> claimsResolver){
        final Claims claims = extractAllClaims(token);
        return claimsResolver.apply(claims);
    }
    public String extractUserName(String token){
        return extractClaim(token, Claims::getSubject);
    }
    private Date extractExpiration(String token){
        return extractClaim(token, Claims::getExpiration);
    }
    public boolean isTokenExpired(String token){
        return extractExpiration(token).before(Date.from(Instant.now()));
    }
    public boolean isTokenValid(String token, UserDetails userDetails){
        final String userName = extractUserName(token);
        return userName.equals(userDetails.getUsername()) && !isTokenExpired(token);
    }
}
