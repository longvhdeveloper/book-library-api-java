package vn.vlong.booklibrary.api.user.query.controller.response;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class UserDTO {

  private String email;
  private String firstName;
  private String lastName;
  private boolean isActive;
  private String activeCode;
  private int role;
}
