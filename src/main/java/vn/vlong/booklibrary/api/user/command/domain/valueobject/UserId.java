package vn.vlong.booklibrary.api.user.command.domain.valueobject;

import lombok.Getter;
import lombok.NoArgsConstructor;
import vn.vlong.booklibrary.api.shared.domain.valueobject.IValueObject;

import javax.persistence.Embeddable;
import java.util.Objects;
import java.util.UUID;

@Embeddable
@NoArgsConstructor
public class UserId implements IValueObject<UserId> {
    private String id;

    @Getter
    private int version;

    public UserId(final UUID uuid, int version) {
        if (Objects.isNull(uuid)) {
            throw new IllegalArgumentException("Id is not valid");
        }

        if (version < 1) {
            version = 1;
        }

        this.id = uuid.toString();
        this.version = version;
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
