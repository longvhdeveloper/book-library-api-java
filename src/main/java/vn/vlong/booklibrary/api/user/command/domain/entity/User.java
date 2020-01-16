package vn.vlong.booklibrary.api.user.command.domain.entity;

import lombok.Getter;
import lombok.NoArgsConstructor;
import org.apache.commons.lang3.RandomStringUtils;
import org.hibernate.annotations.Type;
import vn.vlong.booklibrary.api.user.command.domain.valueobject.*;

import javax.persistence.*;
import java.util.Objects;
import java.util.UUID;

@Entity
@Table(name = "users", indexes = {
        @Index(name = "idx_email", columnList = "email"),
        @Index(name = "idx_is_active", columnList = "is_active"),
        @Index(name = "idx_role", columnList = "role")
})
@NoArgsConstructor
public class User {

    @EmbeddedId
    @Getter
    private UserId userId;

    @Embedded
    @Getter
    private FullName fullName;

    @Embedded
    @Getter
    private Email email;

    @Embedded
    @Getter
    private Password password;

    @Getter
    @Type(type = "org.hibernate.type.NumericBooleanType")
    @Column(name = "is_active")
    private boolean isActive;

    @Embedded
    @Getter
    private ActiveCode activeCode;

    @Embedded
    @Getter
    private UserRole userRole;

    public User(UserId userId, FullName fullName, Email email, Password password, boolean isActive,
                ActiveCode activeCode, UserRole userRole) {

        checkArgument(userId, fullName, email, password, activeCode, userRole);

        this.userId = userId;
        this.fullName = fullName;
        this.email = email;
        this.password = password;
        this.isActive = isActive;
        this.activeCode = activeCode;
        this.userRole = userRole;
    }

    public static User create(FullName fullName, Email email, Password password) {
        return new User(new UserId(UUID.randomUUID(), 1), fullName, email, password, false,
                new ActiveCode(RandomStringUtils.random(36)), new UserRole(Role.USER_ROLE));
    }

    private void checkArgument(UserId userId, FullName fullName, Email email, Password password,
                               ActiveCode activeCode, UserRole userRole) {
        if (Objects.isNull(userId)) {
            throw new IllegalArgumentException("UserId is not valid");
        }

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
}
