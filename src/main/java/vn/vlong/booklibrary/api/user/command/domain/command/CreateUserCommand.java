package vn.vlong.booklibrary.api.user.command.domain.command;

import lombok.Getter;
import vn.vlong.booklibrary.api.shared.domain.command.Command;

@Getter
public class CreateUserCommand extends Command {

  private String email;
  private String firstName;
  private String lastName;
  private String password;

  public CreateUserCommand(String email, String firstName, String lastName, String password) {
    this.email = email;
    this.firstName = firstName;
    this.lastName = lastName;
    this.password = password;
  }
}
