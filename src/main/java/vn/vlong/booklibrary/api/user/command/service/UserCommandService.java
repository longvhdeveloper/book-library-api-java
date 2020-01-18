package vn.vlong.booklibrary.api.user.command.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import vn.vlong.booklibrary.api.user.command.controller.request.CreateUserRequest;
import vn.vlong.booklibrary.api.user.command.domain.command.CreateUserCommand;
import vn.vlong.booklibrary.api.user.command.handler.CreateUserCommandHandler;

@Service
public class UserCommandService {

    @Autowired
    private CreateUserCommandHandler createUserCommandHandler;

    public void createUser(CreateUserRequest request) throws Exception {
        CreateUserCommand createUserCommand = new CreateUserCommand(request.getEmail(),
                request.getFirstName(), request.getLastName(), request.getPassword());
        createUserCommandHandler.handle(createUserCommand);
    }
}
