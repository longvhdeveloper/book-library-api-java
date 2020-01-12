package vn.me.vlong.booklibraryapi.modules.shared.model.command;

import org.springframework.context.ApplicationEvent;

public abstract class Command extends ApplicationEvent implements ICommand {
    public Command(Object source) {
        super(source);
    }
}
