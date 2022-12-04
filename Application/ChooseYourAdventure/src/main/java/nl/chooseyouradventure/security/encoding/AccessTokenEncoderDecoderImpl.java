package nl.chooseyouradventure.security.encoding;


import io.jsonwebtoken.*;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import nl.chooseyouradventure.model.dta.AccessToken;
import nl.chooseyouradventure.security.authentication.DatabaseUserDetailsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.security.Key;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Function;

@Service
public class AccessTokenEncoderDecoderImpl implements AccessTokenEncoder, AccessTokenDecoder {
    private final Key key;

    private  DatabaseUserDetailsService userDetailsService;

    public AccessTokenEncoderDecoderImpl(@Value("${jwt.secret}") String secretKey,@Autowired DatabaseUserDetailsService service) {
        byte[] keyBytes = Decoders.BASE64.decode(secretKey);
        this.key = Keys.hmacShaKeyFor(keyBytes);
        this.userDetailsService=service;
    }

    @Override
    public String encode(AccessToken accessToken) {
        Map<String, Object> claimsMap = new HashMap<>();
        if (!CollectionUtils.isEmpty(accessToken.getRoles())) {
            claimsMap.put("roles", accessToken.getRoles());
        }
        if (accessToken.getUserId() != null) {
            claimsMap.put("userid", accessToken.getUserId());
        }

        Instant now = Instant.now();
        return Jwts.builder()
                .setSubject(accessToken.getSubject())
                .setIssuedAt(Date.from(now))
                .setExpiration(Date.from(now.plus(60, ChronoUnit.MINUTES)))
                .addClaims(claimsMap)
                .signWith(key)
                .compact();
    }

    @Override
    public AccessToken decode(String accessTokenEncoded) {
        try {
//            String username=getUsernameFromToken(accessTokenEncoded);
//if(username==null)return null;
//UserDetails userDetails =  userDetailsService.loadUserByUsername(username);
//
//            if(validateToken(accessTokenEncoded,userDetails)) {
            if(validateToken(accessTokenEncoded)){
//                String sjwt =Jwts.builder() // Compliant
//                        .setSubject(userDetails.getUsername())
//                        .signWith(SignatureAlgorithm.HS256, key)
//                        .compact();
//// Verifying:
//                Jwts.parser().setSigningKey(key).parseClaimsJws(accessTokenEncoded).getBody(); // Compliant

                Jwt jwt = Jwts.parserBuilder().setSigningKey(key).build().parse(accessTokenEncoded);
                Claims claims = (Claims) jwt.getBody();

                List<String> roles = claims.get("roles", List.class);

                return AccessToken.builder()
                        .subject(claims.getSubject())
                        .roles(roles)
                        .userId(claims.get("userid", Integer.class))
                        .build();
            }
            return null;
        } catch (JwtException e) {
            throw new InvalidAccessTokenException(e.getMessage());
        }
    }

    private boolean validateToken(String token) {
//        final String username = getUsernameFromToken(token);
//        return (username.equals(details.getUsername()) && !isTokenExpired(token));
        return !isTokenExpired(token);
    }

    //retrieve expiration date from jwt token
    public Date getExpirationDateFromToken(String token) {
        return getClaimFromToken(token, Claims::getExpiration);
    }
    public <T> T getClaimFromToken(String token, Function<Claims, T> claimsResolver) {
        final Claims claims = getAllClaimsFromToken(token);
        return claimsResolver.apply(claims);
    }
    //for retrieveing any information from token we will need the secret key
    private Claims getAllClaimsFromToken(String token) {
        return Jwts.parser().setSigningKey(key).parseClaimsJws(token).getBody();
    }
    //check if the token has expired
    private Boolean isTokenExpired(String token) {
        final Date expiration = getExpirationDateFromToken(token);
        return expiration.before(new Date());
    }

    //retrieve username from jwt token
    public String getUsernameFromToken(String token) {
        return getClaimFromToken(token, Claims::getSubject);
    }




}
