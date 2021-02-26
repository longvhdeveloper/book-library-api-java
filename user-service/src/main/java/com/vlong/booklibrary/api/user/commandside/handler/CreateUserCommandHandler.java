package com.vlong.booklibrary.api.user.commandside.handler;

import com.vlong.booklibrary.api.user.commandside.command.CreateUserCommand;
import com.vlong.booklibrary.api.user.commandside.command.ICommand;
import com.vlong.booklibrary.api.user.commandside.repository.IUserCommandRepository;
import com.vlong.booklibrary.api.user.domain.User;
import com.vlong.booklibrary.api.user.exception.UserConflictException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
@Slf4j
public class CreateUserCommandHandler implements ICommandHandler {

    private final IUserCommandRepository userCommandRepository;
    private final PasswordEncoder passwordEncoder;

    @Autowired
    public CreateUserCommandHandler(IUserCommandRepository userCommandRepository, PasswordEncoder passwordEncoder) {
        this.userCommandRepository = userCommandRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public Optional<User> handle(ICommand command) throws UserConflictException {
        CreateUserCommand createUserCommand = (CreateUserCommand) command;

        Optional<User> optional = userCommandRepository.findByEmail(createUserCommand.getEmail());

        if (optional.isPresent())
            throw new UserConflictException("User with email " + createUserCommand.getEmail() + " is exist");

        User user = new User(createUserCommand.getEmail(),
                encryptPassword(createUserCommand.getPassword()));
        userCommandRepository.save(user);
        return Optional.of(user);
    }

    private String encryptPassword(String rawPassword) {
        return passwordEncoder.encode(rawPassword);
    }
}
