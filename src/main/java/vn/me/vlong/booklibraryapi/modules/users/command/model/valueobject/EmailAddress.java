package vn.me.vlong.booklibraryapi.modules.users.command.model.valueobject;

import lombok.Getter;
import lombok.NoArgsConstructor;
import org.apache.commons.validator.routines.EmailValidator;
import vn.me.vlong.booklibraryapi.modules.shared.model.valueobject.IValueObject;

import javax.persistence.Embeddable;
import java.util.Objects;

@Embeddable
@Getter
@NoArgsConstructor
public final class EmailAddress implements IValueObject<EmailAddress> {
    private String email;

    public EmailAddress(final String email) {
        EmailValidator emailValidator = EmailValidator.getInstance();
        if (!emailValidator.isValid(email)) {
            throw new IllegalArgumentException(String.format("Email %s is not valid", email));
        }
        this.email = email;
    }

    @Override
    public boolean sameValueAs(EmailAddress other) {
        if (Objects.isNull(other)) {
            return false;
        }
        return other.getEmail().equals(email);
    }
}
