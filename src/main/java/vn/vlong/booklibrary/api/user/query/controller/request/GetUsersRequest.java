package vn.vlong.booklibrary.api.user.query.controller.request;

import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Getter
public class GetUsersRequest {
    private String keyword = "";

    private boolean isActive = false;

    private int role = 1;

    private int limit = 10;

    private int offset = 1;
}
