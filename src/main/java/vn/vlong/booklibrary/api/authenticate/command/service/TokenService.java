package vn.vlong.booklibrary.api.authenticate.command.service;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import java.util.Date;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;
import vn.vlong.booklibrary.api.authenticate.command.domain.entity.UserPrincipal;
import vn.vlong.booklibrary.api.configuration.JwtConfig;

@Service
@Slf4j
public class TokenService {

  private JwtConfig jwtConfig;

  @Autowired
  public TokenService(JwtConfig jwtConfig) {
    this.jwtConfig = jwtConfig;
  }

  public String generateToken(Authentication authentication) {
    UserPrincipal userPrincipal = (UserPrincipal) authentication.getPrincipal();
    Date now = new Date();
    Date expiration = new Date(now.getTime() + jwtConfig.getExpiration());

    return Jwts.builder()
        .setSubject(userPrincipal.getUser().getEmail().getEmail())
        .setIssuedAt(new Date())
        .setExpiration(expiration)
        .signWith(SignatureAlgorithm.HS512, jwtConfig.getSecret())
        .compact();
  }

  public String getEmailFromToken(String token) {
    Claims claims = Jwts.parser().setSigningKey(jwtConfig.getSecret()).parseClaimsJws(token)
        .getBody();

    return claims.getSubject();
  }

  public boolean validateToken(String token) {
    try {
      Jwts.parser().setSigningKey(jwtConfig.getSecret()).parseClaimsJws(token);
      return true;
    } catch (Exception e) {
      log.error(String.format("Validate token failed. Reason:::%s", e.getMessage()));
    }
    return false;
  }
}
