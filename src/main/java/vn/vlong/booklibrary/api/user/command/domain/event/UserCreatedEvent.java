package vn.vlong.booklibrary.api.user.command.domain.event;

import lombok.Getter;
import lombok.NoArgsConstructor;
import vn.vlong.booklibrary.api.shared.domain.event.Event;

@Getter
@NoArgsConstructor
public class UserCreatedEvent extends Event {
    private String firstName;
    private String lastName;
    private String password;
    private boolean active;
    private String activeCode;
    private int role;

    public UserCreatedEvent(String stream, int version, String firstName, String lastName,
                            String email, String password, boolean active, String activeCode, int role) {
        this.aggregateId = email;
        this.version = version;
        this.stream = stream;
        this.firstName = firstName;
        this.lastName = lastName;
        this.password = password;
        this.active = active;
        this.activeCode = activeCode;
        this.role = role;
    }
}
