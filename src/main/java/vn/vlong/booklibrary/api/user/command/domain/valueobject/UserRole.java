package vn.vlong.booklibrary.api.user.command.domain.valueobject;

import vn.vlong.booklibrary.api.shared.domain.valueobject.IValueObject;

import java.util.Objects;

public class UserRole implements IValueObject<UserRole> {
    private int role;

    public UserRole(final Role role) {
        if (Objects.isNull(role)) {
            throw new IllegalArgumentException("Role is not valid");
        }
        this.role = role.getValue();
    }

    public Role getRole() {
        return Role.valueOf(role);
    }

    @Override
    public boolean isSameValue(UserRole other) {
        if (Objects.isNull(other)) {
            return false;
        }

        return role == other.getRole().getValue();
    }
}
