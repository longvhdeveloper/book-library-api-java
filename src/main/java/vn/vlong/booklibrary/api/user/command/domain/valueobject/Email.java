package vn.vlong.booklibrary.api.user.command.domain.valueobject;

import lombok.Getter;
import lombok.NoArgsConstructor;
import org.apache.commons.validator.routines.EmailValidator;
import vn.vlong.booklibrary.api.shared.domain.valueobject.IValueObject;

import javax.persistence.Embeddable;
import java.util.Objects;

@Embeddable
@NoArgsConstructor
public class Email implements IValueObject<Email> {

    @Getter
    private String email;

    public Email(final String email) {
        EmailValidator emailValidator = EmailValidator.getInstance();
        if (!emailValidator.isValid(email)) {
            throw new IllegalArgumentException(String.format("Email %s is not valid", email));
        }
        this.email = email;
    }

    @Override
    public boolean isSameValue(Email other) {
        if (Objects.isNull(other)) {
            return false;
        }
        return other.getEmail().equals(email);
    }
}
