package vn.vlong.booklibrary.api.user.command.controller.request;

import lombok.Getter;
import lombok.Value;

@Value
@Getter
public class ActiveUserRequest {

  private String email;
  private String activeCode;
}
