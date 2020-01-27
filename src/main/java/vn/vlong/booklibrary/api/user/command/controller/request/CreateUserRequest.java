package vn.vlong.booklibrary.api.user.command.controller.request;

import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
public class CreateUserRequest {

  private String email;
  private String firstName;
  private String lastName;
  private String password;
}
