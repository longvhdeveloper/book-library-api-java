package vn.vlong.booklibrary.api.user.command.domain.valueobject;

import lombok.Getter;

public enum Role {
    USER_ROLE(1), ADMIN_ROLE(2);

    @Getter
    private int value;

    Role(final int value) {
        this.value = value;
    }

    public static Role valueOf(int value) {
        switch (value) {
            case 1:
                return USER_ROLE;
            case 2:
                return ADMIN_ROLE;
        }
        throw new IllegalArgumentException(String.format("Role value %d is not valid", value));
    }
}
