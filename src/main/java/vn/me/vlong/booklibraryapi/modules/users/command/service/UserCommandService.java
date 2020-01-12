package vn.me.vlong.booklibraryapi.modules.users.command.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import vn.me.vlong.booklibraryapi.modules.shared.gateway.CommandGateway;
import vn.me.vlong.booklibraryapi.modules.users.command.controller.request.CreateUserRequest;
import vn.me.vlong.booklibraryapi.modules.users.command.model.command.CreateUserCommand;

@Service
public class UserCommandService {

    private CommandGateway commandGateway;

    @Autowired
    public UserCommandService(CommandGateway commandGateway) {
        this.commandGateway = commandGateway;
    }

    public void createUser(CreateUserRequest createUserRequest) {
        CreateUserCommand createUserCommand = new CreateUserCommand(this, createUserRequest.getEmail(),
                createUserRequest.getFirstName(), createUserRequest.getLastName(), createUserRequest.getPassword());
        commandGateway.send(createUserCommand);
    }
}
