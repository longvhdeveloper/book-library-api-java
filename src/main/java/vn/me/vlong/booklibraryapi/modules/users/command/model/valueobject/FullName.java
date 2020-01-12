package vn.me.vlong.booklibraryapi.modules.users.command.model.valueobject;

import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.util.StringUtils;
import vn.me.vlong.booklibraryapi.modules.shared.model.valueobject.IValueObject;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import java.util.Objects;

@Getter
@Embeddable
@NoArgsConstructor
public class FullName implements IValueObject<FullName> {

    @Column(name = "first_name")
    private String firstName;

    @Column(name = "last_name")
    private String lastName;

    public FullName(String firstName, String lastName) {

        if (StringUtils.isEmpty(firstName)) {
            throw new IllegalArgumentException("First name is not valid");
        }

        if (StringUtils.isEmpty(lastName)) {
            throw new IllegalArgumentException("First name is not valid");
        }

        this.firstName = firstName;
        this.lastName = lastName;
    }

    public String getFullName() {
        return String.format("%s %s", firstName, lastName);
    }

    @Override
    public boolean sameValueAs(FullName other) {
        if (Objects.isNull(other)) {
            return false;
        }
        return other.getFirstName().equals(firstName) && other.getLastName().equals(lastName);
    }
}
