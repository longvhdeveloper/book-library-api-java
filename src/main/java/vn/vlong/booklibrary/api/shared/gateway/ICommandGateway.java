package vn.vlong.booklibrary.api.shared.gateway;

import vn.vlong.booklibrary.api.shared.domain.command.Command;

public interface ICommandGateway<T extends Command> {
    void send(T command);
}
