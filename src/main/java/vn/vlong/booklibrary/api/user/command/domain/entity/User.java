package vn.vlong.booklibrary.api.user.command.domain.entity;

import lombok.Getter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import vn.vlong.booklibrary.api.shared.domain.entity.BaseEntity;
import vn.vlong.booklibrary.api.shared.domain.event.Event;
import vn.vlong.booklibrary.api.user.command.domain.command.CreateUserCommand;
import vn.vlong.booklibrary.api.user.command.domain.event.UserCreatedEvent;
import vn.vlong.booklibrary.api.user.command.domain.valueobject.*;

import java.util.List;
import java.util.Objects;

public class User extends BaseEntity<User> {

    @Getter
    private Email email;

    @Getter
    private FullName fullName;

    @Getter
    private Password password;

    @Getter

    private boolean isActive;

    @Getter
    private ActiveCode activeCode;

    @Getter
    private UserRole userRole;

    @Getter
    private int version;

    private boolean isDeleted;

    public User(List<Event> events) {
        super();
        this.apply(events);
    }

    public User(CreateUserCommand command) {
        super();
        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
        UserCreatedEvent event = new UserCreatedEvent(
                command.getEmail(),
                User.getStream(),
                1,
                command.getFirstName(),
                command.getLastName(),
                encoder.encode(command.getPassword()),
                false,
                encoder.encode(command.getEmail()),
                Role.USER_ROLE.getValue()
        );
        this.apply(event);
        this.addUnCommittedEvent(event);
    }

    public static String getStream() {
        return "user";
    }

    // Apply
    private void apply(UserCreatedEvent event) {
        this.email = new Email(event.getAggregateId());
        this.fullName = new FullName(event.getFirstName(), event.getLastName());
        this.password = new Password(event.getPassword());
        this.isActive = event.isActive();
        this.activeCode = new ActiveCode(event.getActiveCode());
        this.userRole = new UserRole(Role.valueOf(event.getRole()));
        this.version = event.getVersion();
        this.isDeleted = false;
    }

    @Override
    public boolean isSameIdentity(User other) {
        if (Objects.isNull(other)) {
            return false;
        }
        return email.isSameValue(other.getEmail());
    }
}
