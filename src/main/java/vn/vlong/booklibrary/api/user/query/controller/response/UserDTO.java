package vn.vlong.booklibrary.api.user.query.controller.response;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class UserDTO {
    private String id;
    private String firstName;
    private String lastName;
    private String email;
    private boolean isActive;
    private String activeCode;
    private int role;
}
