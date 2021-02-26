package com.vlong.booklibrary.api.user.commandside.command;

import lombok.Data;

@Data
public class CreateUserCommand implements ICommand {

    private final String email;
    private final String password;
    private final String firstName;
    private final String lastName;

    public CreateUserCommand(final String email, final String password, final String firstName, final String lastName) {
        this.email = email;
        this.password = password;
        this.firstName = firstName;
        this.lastName = lastName;
    }
}
