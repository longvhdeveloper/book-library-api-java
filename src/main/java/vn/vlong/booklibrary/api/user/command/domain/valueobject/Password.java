package vn.vlong.booklibrary.api.user.command.domain.valueobject;

import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.util.StringUtils;
import vn.vlong.booklibrary.api.shared.domain.valueobject.IValueObject;

import javax.persistence.Embeddable;
import java.util.Objects;

@Embeddable
@NoArgsConstructor
public class Password implements IValueObject<Password> {

    @Getter
    private String password;

    public Password(String password) {
        if (StringUtils.isEmpty(password)) {
            throw new IllegalArgumentException("Password is not valid");
        }

        this.password = new BCryptPasswordEncoder().encode(password);
    }

    @Override
    public boolean isSameValue(Password other) {
        if (Objects.isNull(other)) {
            return false;
        }
        return other.getPassword().equals(password);
    }
}
