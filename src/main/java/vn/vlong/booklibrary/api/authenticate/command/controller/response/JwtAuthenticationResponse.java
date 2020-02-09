package vn.vlong.booklibrary.api.authenticate.command.controller.response;

import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
public class JwtAuthenticationResponse {

  private String accessToken;
  private String tokenType;

}
