package vn.vlong.booklibrary.api.user.command.domain.command;

import lombok.Getter;

@Getter
public class ActiveUserCommand {

  private String email;
  private String activeCode;

  public ActiveUserCommand(String email, String activeCode) {
    this.email = email;
    this.activeCode = activeCode;
  }
}
