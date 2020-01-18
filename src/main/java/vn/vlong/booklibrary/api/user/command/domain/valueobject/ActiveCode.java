package vn.vlong.booklibrary.api.user.command.domain.valueobject;

import lombok.Getter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.util.StringUtils;
import vn.vlong.booklibrary.api.shared.domain.valueobject.IValueObject;

import java.util.Objects;

public class ActiveCode implements IValueObject<ActiveCode> {

    @Getter
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
