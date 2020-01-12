package vn.me.vlong.booklibraryapi.modules.shared.handler;

public interface ICommandHandler<Command> {
    void handle(Command command) throws Exception;
}
