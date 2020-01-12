package vn.me.vlong.booklibraryapi.modules.shared.gateway;

import vn.me.vlong.booklibraryapi.modules.shared.model.command.Command;

public interface ICommandGateway {
    void send(Command command);
}
