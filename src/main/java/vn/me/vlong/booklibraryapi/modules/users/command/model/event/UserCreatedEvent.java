package vn.me.vlong.booklibraryapi.modules.users.command.model.event;

import lombok.Getter;
import vn.me.vlong.booklibraryapi.modules.shared.model.event.Event;

import java.util.UUID;

@Getter
public class UserCreatedEvent extends Event {
    private String email;
    private String firstName;
    private String lastName;
    private String password;
    private boolean isActive;
    private String activeCode;

    public UserCreatedEvent(Object source, UUID aggregateId,String email, String firstName, String lastName,
                            String password,
                            boolean isActive, String activeCode) {
        super(source, aggregateId);
        this.email = email;
        this.firstName = firstName;
        this.lastName = lastName;
        this.password = password;
        this.isActive = isActive;
        this.activeCode = activeCode;
    }
}
