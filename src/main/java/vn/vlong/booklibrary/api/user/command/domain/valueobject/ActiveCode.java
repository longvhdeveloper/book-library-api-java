package vn.vlong.booklibrary.api.user.command.domain.valueobject;

import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.util.StringUtils;
import vn.vlong.booklibrary.api.shared.domain.valueobject.IValueObject;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import java.util.Objects;

@Embeddable
@NoArgsConstructor
public class ActiveCode implements IValueObject<ActiveCode> {

    @Getter
    @Column(name = "active_code")
    private String activeCode;

    public ActiveCode(final String data) {
        if (StringUtils.isEmpty(data)) {
            throw new IllegalArgumentException("Data of active code is not valid");
        }
        activeCode = new BCryptPasswordEncoder().encode(data);
    }

    @Override
    public boolean isSameValue(ActiveCode other) {
        if (Objects.isNull(other)) {
            return false;
        }
        return other.getActiveCode().equals(activeCode);
    }
}
