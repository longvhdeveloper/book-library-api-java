package vn.me.vlong.booklibraryapi.modules.users.command.model.entity;

import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.Type;
import vn.me.vlong.booklibraryapi.modules.shared.model.entity.BaseEntity;
import vn.me.vlong.booklibraryapi.modules.users.command.model.valueobject.*;

import javax.persistence.Column;
import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.Id;
import java.util.Date;
import java.util.UUID;

@Entity
@NoArgsConstructor
public class User extends BaseEntity<User> {

    @Id
    private String id;

    @Embedded
    @Getter
    private EmailAddress emailAddress;

    @Embedded
    @Getter
    private Password password;

    @Embedded
    @Getter
    private FullName fullName;

    @Column(name = "is_active", nullable = false)
    @Type(type = "org.hibernate.type.NumericBooleanType")
    @Getter
    private boolean isActive;

    @Embedded
    @Getter
    private ActiveCode activeCode;

    private String phone;

    @Column(name = "birthday")
    private Date birthDay;

    private int gender;

    private int role;

    public User(UUID id, EmailAddress emailAddress, FullName fullName, Password password, String phone,
                Date birthday, Gender gender, boolean isActive, ActiveCode activeCode, UserRole role) {
        this.id = id.toString();
        this.emailAddress = emailAddress;
        this.password = password;
        this.fullName = fullName;
        this.isActive = isActive;
        this.activeCode = activeCode;
        this.phone = phone;
        this.birthDay = birthday;
        this.gender = gender.getValue();
        this.role = role.getValue();
    }

    public static User create(EmailAddress emailAddress, FullName fullName, Password password) {
        return new User(UUID.randomUUID(), emailAddress, fullName, password, "", null, Gender.UNKNOWN, false,
                new ActiveCode(emailAddress.getEmail()), UserRole.USER_ROLE);
    }

    public UUID getId() {
        return UUID.fromString(id);
    }
}
