package vn.me.vlong.booklibraryapi.modules.users.command.model.valueobject;

import vn.me.vlong.booklibraryapi.modules.shared.model.valueobject.IValueObject;

import java.util.Objects;

public enum UserRole implements IValueObject<UserRole> {
    USER_ROLE(1), ADMIN_ROLE(2);
    private int value;

    UserRole(int value) {
        this.value = value;
    }

    public int getValue() {
        return value;
    }


    @Override
    public boolean sameValueAs(UserRole other) {
        if (Objects.isNull(other)) {
            return false;
        }
        return other.equals(this);
    }
}
