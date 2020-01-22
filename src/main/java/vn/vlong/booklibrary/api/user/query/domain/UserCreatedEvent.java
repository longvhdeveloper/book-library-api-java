package vn.vlong.booklibrary.api.user.query.domain;

import lombok.Getter;
import lombok.NoArgsConstructor;
import vn.vlong.booklibrary.api.shared.domain.event.Event;

@NoArgsConstructor
@Getter
public class UserCreatedEvent extends Event {
    private String firstName;
    private String lastName;
    private String password;
    private boolean active;
    private String activeCode;
    private int role;

    public UserCreatedEvent(String aggregateId, String stream, int version, String firstName, String lastName,
                            String password, boolean active, String activeCode, int role) {
        this.aggregateId = aggregateId;
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
