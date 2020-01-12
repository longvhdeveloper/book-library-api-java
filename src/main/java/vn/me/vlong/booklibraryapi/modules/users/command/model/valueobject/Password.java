package vn.me.vlong.booklibraryapi.modules.users.command.model.valueobject;

import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.util.StringUtils;
import vn.me.vlong.booklibraryapi.modules.shared.model.valueobject.IValueObject;

import javax.persistence.Embeddable;
import java.util.Objects;

@Embeddable
@Getter
@NoArgsConstructor
public class Password implements IValueObject<Password> {
    private String password;

    public Password(String password) {
        if (StringUtils.isEmpty(password)) {
            throw new IllegalArgumentException("Password is not valid");
        }

        this.password = new BCryptPasswordEncoder().encode(password);
    }

    @Override
    public boolean sameValueAs(Password other) {
        if (Objects.isNull(other)) {
            return false;
        }
        return other.getPassword().equals(password);
    }
}
