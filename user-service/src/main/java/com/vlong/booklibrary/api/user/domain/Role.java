package com.vlong.booklibrary.api.user.domain;

import java.util.stream.Stream;

public enum Role {

    ADMIN(1), STAFF(3), MEMBER(5);

    private final int role;

    private Role(int role) {
        this.role = role;
    }

    public int getRole() {
        return role;
    }

    public static Role of(int role) {
        return Stream.of(Role.values())
                .filter(p -> p.getRole() == role)
                .findFirst()
                .orElseThrow(IllegalArgumentException::new);
    }
}
