package vn.vlong.booklibrary.api.shared.handler;

import vn.vlong.booklibrary.api.shared.domain.command.Command;

public interface ICommandHandler<T extends Command> {
    void handle(T command) throws Exception;
}
