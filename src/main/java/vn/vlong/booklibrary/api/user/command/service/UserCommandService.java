package vn.vlong.booklibrary.api.user.command.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import vn.vlong.booklibrary.api.shared.domain.command.Command;
import vn.vlong.booklibrary.api.shared.gateway.CommandGateway;
import vn.vlong.booklibrary.api.user.command.controller.request.CreateUserRequest;
import vn.vlong.booklibrary.api.user.command.domain.command.CreateUserCommand;

@Service
public class UserCommandService {
    private CommandGateway<Command> commandGateway;

    @Autowired
    public UserCommandService(final CommandGateway<Command> commandGateway) {
        this.commandGateway = commandGateway;
    }

    public void createUser(CreateUserRequest request) {
        CreateUserCommand createUserCommand = new CreateUserCommand(this, request.getEmail(),
                request.getFirstName(), request.getLastName(), request.getPassword());
        commandGateway.send(createUserCommand);
    }
}
