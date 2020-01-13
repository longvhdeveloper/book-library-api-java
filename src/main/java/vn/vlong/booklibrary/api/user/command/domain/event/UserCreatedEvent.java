package vn.vlong.booklibrary.api.user.command.domain.event;

import lombok.Getter;
import vn.vlong.booklibrary.api.shared.domain.event.Event;
import vn.vlong.booklibrary.api.user.command.domain.entity.User;

@Getter
public class UserCreatedEvent extends Event {
    private User user;

    public UserCreatedEvent(Object source, User user) {
        super(source, user.getUserId().getId());
        this.user = user;
    }
}
