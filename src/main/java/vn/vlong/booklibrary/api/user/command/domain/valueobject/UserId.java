package vn.vlong.booklibrary.api.user.command.domain.valueobject;

import lombok.NoArgsConstructor;
import vn.vlong.booklibrary.api.shared.domain.valueobject.IValueObject;

import javax.persistence.Embeddable;
import java.io.Serializable;
import java.util.Objects;
import java.util.UUID;

@Embeddable
@NoArgsConstructor
public class UserId implements IValueObject<UserId>, Serializable {
    private String id;

    public UserId(final UUID uuid) {
        if (Objects.isNull(uuid)) {
            throw new IllegalArgumentException("Id is not valid");
        }
        this.id = uuid.toString();
    }

    public UUID getId() {
        return UUID.fromString(id);
    }

    @Override
    public boolean isSameValue(UserId other) {
        if (Objects.isNull(other)) {
            return false;
        }

        return id.equals(other.getId().toString());
    }
}
