package vn.vlong.booklibrary.api.configuration;

import lombok.Getter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

@Configuration
@Getter
public class JwtConfig {

  @Value("${security.jwt.header:Authorization}")
  private String header;

  @Value("${security.jwt.prefix:Bearer }")
  private String prefix;

  @Value("${security.jwt.expiration:#{24*60*60}}")
  private int expiration;

  @Value("${security.jwt.secret:JwtSecretKey}")
  private String secret;
}
