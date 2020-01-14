package vn.vlong.booklibrary.api.user.command.domain.event;

import lombok.Getter;
import vn.vlong.booklibrary.api.shared.domain.event.Event;

import java.util.UUID;

@Getter
public class UserCreatedEvent extends Event {
    private String firstName;
    private String lastName;
    private String email;
    private String password;
    private boolean isActive;
    private String activeCode;
    private int role;

    public UserCreatedEvent(Object source, UUID aggregateId, int version, String firstName, String lastName,
                            String email, String password, boolean isActive, String activeCode, int role) {
        super(source, aggregateId, version);
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.password = password;
        this.isActive = isActive;
        this.activeCode = activeCode;
        this.role = role;
    }
}
