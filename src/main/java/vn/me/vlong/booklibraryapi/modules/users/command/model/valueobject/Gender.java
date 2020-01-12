package vn.me.vlong.booklibraryapi.modules.users.command.model.valueobject;

import vn.me.vlong.booklibraryapi.modules.shared.model.valueobject.IValueObject;

import java.util.Objects;

public enum Gender implements IValueObject<Gender> {

    MALE(1), FEMALE(2), OTHER(3), UNKNOWN(4);

    private int value;

    Gender(int value) {
        this.value = value;
    }

    public int getValue() {
        return value;
    }

    @Override
    public boolean sameValueAs(Gender other) {
        if (Objects.isNull(other)) {
            return false;
        }
        return this.equals(other);
    }
}
