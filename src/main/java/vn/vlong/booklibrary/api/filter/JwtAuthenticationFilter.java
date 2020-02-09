package vn.vlong.booklibrary.api.filter;

import java.io.IOException;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;
import vn.vlong.booklibrary.api.authenticate.command.service.ApiUserDetailService;
import vn.vlong.booklibrary.api.authenticate.command.service.TokenService;
import vn.vlong.booklibrary.api.configuration.JwtConfig;

@Slf4j
@Component
public class JwtAuthenticationFilter extends OncePerRequestFilter {

  private TokenService tokenService;

  private ApiUserDetailService apiUserDetailService;

  private JwtConfig jwtConfig;

  @Autowired
  public JwtAuthenticationFilter(
      TokenService tokenService,
      ApiUserDetailService apiUserDetailService,
      JwtConfig jwtConfig) {
    this.tokenService = tokenService;
    this.apiUserDetailService = apiUserDetailService;
    this.jwtConfig = jwtConfig;
  }

  @Override
  protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response,
      FilterChain filterChain) throws ServletException, IOException {
    String jwt = getJwtFromHeader(request);

    if (StringUtils.hasText(jwt) && tokenService.validateToken(jwt)) {
      String email = tokenService.getEmailFromToken(jwt);

      UserDetails userDetails = apiUserDetailService.loadUserByUsername(email);
      UsernamePasswordAuthenticationToken authentication
          = new UsernamePasswordAuthenticationToken(userDetails, null,
          userDetails.getAuthorities());

      authentication.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));

      SecurityContextHolder.getContext().setAuthentication(authentication);
    }

    filterChain.doFilter(request, response);
  }

  private String getJwtFromHeader(HttpServletRequest request) {
    String bearerToken = request.getHeader(jwtConfig.getHeader());
    log.info("TOKEN::: " + bearerToken);
    if (StringUtils.hasText(bearerToken) && bearerToken.startsWith(jwtConfig.getPrefix())) {
      return bearerToken.substring(7);
    }
    return null;
  }
}
