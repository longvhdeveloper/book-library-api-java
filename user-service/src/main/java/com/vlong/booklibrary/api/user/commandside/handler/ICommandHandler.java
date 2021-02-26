package com.vlong.booklibrary.api.user.commandside.handler;

import com.vlong.booklibrary.api.user.commandside.command.ICommand;
import com.vlong.booklibrary.api.user.domain.User;

import java.util.Optional;

public interface ICommandHandler {
    Optional<User> handle(ICommand command) throws Exception;
}
