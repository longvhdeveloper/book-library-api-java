package vn.vlong.booklibrary.api.authenticate.command.controller;

import javax.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import vn.vlong.booklibrary.api.authenticate.command.controller.request.SignInRequest;
import vn.vlong.booklibrary.api.authenticate.command.controller.response.JwtAuthenticationResponse;
import vn.vlong.booklibrary.api.authenticate.command.service.TokenService;
import vn.vlong.booklibrary.api.configuration.JwtConfig;

@RestController
@RequestMapping("/auth")
public class AuthenticateController {

  private AuthenticationManager authenticationManager;
  private PasswordEncoder passwordEncoder;
  private TokenService tokenService;
  private JwtConfig jwtConfig;

  @Autowired
  public AuthenticateController(
      AuthenticationManager authenticationManager,
      PasswordEncoder passwordEncoder,
      TokenService tokenService, JwtConfig jwtConfig) {
    this.authenticationManager = authenticationManager;
    this.passwordEncoder = passwordEncoder;
    this.tokenService = tokenService;
    this.jwtConfig = jwtConfig;
  }

  @PostMapping("/signin")
  public ResponseEntity<?> authenticateUser(@Valid @RequestBody SignInRequest request) {

    System.out.println("here");
    Authentication authentication = authenticationManager.authenticate(
        new UsernamePasswordAuthenticationToken(request.getEmail(), request.getPassword()));

    SecurityContextHolder.getContext().setAuthentication(authentication);
    String token = tokenService.generateToken(authentication);
    return ResponseEntity
        .ok(JwtAuthenticationResponse.builder().accessToken(token).tokenType(jwtConfig.getPrefix())
            .build());
  }

}
