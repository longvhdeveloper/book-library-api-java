package vn.me.vlong.booklibraryapi.modules.users.command.model.valueobject;

import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.util.StringUtils;
import vn.me.vlong.booklibraryapi.modules.shared.model.valueobject.IValueObject;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import java.util.Objects;

@Embeddable
@Getter
@NoArgsConstructor
public class ActiveCode implements IValueObject<ActiveCode> {

    @Column(name = "active_code")
    private String activeCode;

    public ActiveCode(String data) {
        if (StringUtils.isEmpty(data)) {
            throw new IllegalArgumentException("Data of active code is not valid");
        }
        activeCode = new BCryptPasswordEncoder().encode(data);
    }

    @Override
    public boolean sameValueAs(ActiveCode other) {
        if (Objects.isNull(other)) {
            return false;
        }
        return other.getActiveCode().equals(activeCode);
    }
}
