package vn.me.vlong.booklibraryapi.modules.users.command.controller.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Builder
@NoArgsConstructor
@AllArgsConstructor
@Getter
public class Success {
    private int status;
    private String message;
}
