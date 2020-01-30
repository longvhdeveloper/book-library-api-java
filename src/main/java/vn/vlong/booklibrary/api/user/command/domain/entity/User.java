package vn.vlong.booklibrary.api.user.command.domain.entity;

import java.util.List;
import java.util.Objects;
import lombok.Getter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import vn.vlong.booklibrary.api.shared.domain.entity.BaseEntity;
import vn.vlong.booklibrary.api.shared.domain.event.Event;
import vn.vlong.booklibrary.api.user.command.domain.command.ActiveUserCommand;
import vn.vlong.booklibrary.api.user.command.domain.command.CreateUserCommand;
import vn.vlong.booklibrary.api.user.command.domain.event.UserActivatedEvent;
import vn.vlong.booklibrary.api.user.command.domain.event.UserCreatedEvent;
import vn.vlong.booklibrary.api.user.command.domain.valueobject.ActiveCode;
import vn.vlong.booklibrary.api.user.command.domain.valueobject.Email;
import vn.vlong.booklibrary.api.user.command.domain.valueobject.FullName;
import vn.vlong.booklibrary.api.user.command.domain.valueobject.Password;
import vn.vlong.booklibrary.api.user.command.domain.valueobject.Role;
import vn.vlong.booklibrary.api.user.command.domain.valueobject.UserRole;
import vn.vlong.booklibrary.api.user.exception.ActiveCodeIsNotMatchException;
import vn.vlong.booklibrary.api.user.exception.UserAlreadyActiveException;

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
        command.getRole()
    );
    this.apply(event);
    this.addUnCommittedEvent(event);
  }

  public static String getStream() {
    return "user";
  }

  public void handle(ActiveUserCommand command)
      throws UserAlreadyActiveException, ActiveCodeIsNotMatchException {
    if (isActive()) {
      throw new UserAlreadyActiveException(
          String.format("User with email %s is already active", email.getEmail()));
    }

    if (!checkActiveCode(command.getActiveCode())) {
      throw new ActiveCodeIsNotMatchException("Active code is not match.");
    }

    UserActivatedEvent event = new UserActivatedEvent(command.getEmail(), User.getStream(),
        version + 1, true);
    this.apply(event);
    this.addUnCommittedEvent(event);
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

  private void apply(UserActivatedEvent event) {
    this.isActive = event.isActive();
  }

  private boolean checkActiveCode(String activeCode) {
    return this.activeCode.getActiveCode().equals(activeCode);
  }

  @Override
  public boolean isSameIdentity(User other) {
    if (Objects.isNull(other)) {
      return false;
    }
    return email.isSameValue(other.getEmail());
  }
}
