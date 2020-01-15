package vn.vlong.booklibrary.api.user.query.controller.request;

import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Getter
public class GetUsersRequest {
    private String keyword = "";

    private Boolean isActive;

    private Integer role;

    private int limit = 10;

    private int offset = 1;
}
