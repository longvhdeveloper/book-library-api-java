package vn.vlong.booklibrary.api.shared.domain.command;

import org.springframework.context.ApplicationEvent;

public abstract class Command extends ApplicationEvent implements ICommand {
    public Command(Object source) {
        super(source);
    }
}
