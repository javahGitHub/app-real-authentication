package pdp.uz.apprealauthentication.security;


import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jwt;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.stereotype.Component;
import pdp.uz.apprealauthentication.entity.Role;
import pdp.uz.apprealauthentication.entity.enums.SecretKey;

import java.util.Date;
import java.util.Set;

@Component
public class JwtProvider {

    public static final long expireMill=1000*60*60*24;
    SecretKey secretKey= SecretKey.KEY;
    public String generateToken(String username, Set<Role> roleSet){
        return Jwts.
                builder()
                .setSubject(username)
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis() + expireMill))
                .claim("roles", roleSet)
                .signWith(SignatureAlgorithm.HS512, secretKey.key )
                .compact();

    }


    public String getUsername(String token){

        try{
            return Jwts
                    .parser()
                    .setSigningKey(secretKey.key)
                    .parseClaimsJws(token)
                    .getBody()
                    .getSubject();

        }
        catch (Exception e){
            return null;
        }


    }
}
