package vn.me.vlong.booklibraryapi.modules.users.command.model.command;

import lombok.Getter;
import vn.me.vlong.booklibraryapi.modules.shared.model.command.Command;

@Getter
public class CreateUserCommand extends Command {
    private String email;
    private String firstName;
    private String lastName;
    private String password;

    public CreateUserCommand(Object source, String email, String firstName, String lastName, String password) {
        super(source);
        this.email = email;
        this.firstName = firstName;
        this.lastName = lastName;
        this.password = password;
    }
}
