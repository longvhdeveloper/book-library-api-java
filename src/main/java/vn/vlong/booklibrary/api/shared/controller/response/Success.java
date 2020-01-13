package vn.vlong.booklibrary.api.shared.controller.response;

import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
public class Success {
    private int code;
    private String message;
}
