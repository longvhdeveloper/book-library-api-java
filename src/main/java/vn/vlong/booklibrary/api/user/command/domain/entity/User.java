package vn.vlong.booklibrary.api.user.command.domain.entity;

import lombok.Getter;
import org.apache.commons.lang3.RandomStringUtils;
import vn.vlong.booklibrary.api.shared.domain.entity.BaseEntity;
import vn.vlong.booklibrary.api.shared.domain.event.Event;
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

    public User(FullName fullName, Email email, Password password, boolean isActive,
                ActiveCode activeCode, UserRole userRole, int version) {
        checkArgument(fullName, email, password, activeCode, userRole);

        this.fullName = fullName;
        this.email = email;
        this.password = password;
        this.isActive = isActive;
        this.activeCode = activeCode;
        this.userRole = userRole;
        this.version = version;
    }

    public User(List<Event> events) {
        this.apply(events);
    }

    public static User create(FullName fullName, Email email, Password password) {
        return new User(fullName, email, password, false,
                new ActiveCode(RandomStringUtils.random(36)), new UserRole(Role.USER_ROLE), 1);
    }

    public String getStream() {
        return "user";
    }

    private void checkArgument(FullName fullName, Email email, Password password,
                               ActiveCode activeCode, UserRole userRole) {

        if (Objects.isNull(fullName)) {
            throw new IllegalArgumentException("FullName is not valid");
        }

        if (Objects.isNull(email)) {
            throw new IllegalArgumentException("Email is not valid");
        }

        if (Objects.isNull(password)) {
            throw new IllegalArgumentException("Password is not valid");
        }

        if (Objects.isNull(activeCode)) {
            throw new IllegalArgumentException("ActiveCode is not valid");
        }

        if (Objects.isNull(userRole)) {
            throw new IllegalArgumentException("UserRole is not valid");
        }
    }

    @Override
    public boolean isSameIdentity(User other) {
        if (Objects.isNull(other)) {
            return false;
        }
        return email.isSameValue(other.getEmail());
    }
}
