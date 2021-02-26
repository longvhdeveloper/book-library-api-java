package com.vlong.booklibrary.api.user.domain;

import java.util.stream.Stream;

public enum Status {
    ACTIVE(1), PENDING(3), DEACTIVATE(5);

    private final int status;

    private Status(int status) {
        this.status = status;
    }

    public int getStatus() {
        return status;
    }

    public static Status of(int status) {
        return Stream.of(Status.values())
                .filter(p -> p.getStatus() == status)
                .findFirst()
                .orElseThrow(IllegalArgumentException::new);
    }
}
