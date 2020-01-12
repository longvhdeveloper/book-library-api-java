package vn.me.vlong.booklibraryapi.modules.users.command.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import vn.me.vlong.booklibraryapi.modules.users.command.controller.request.CreateUserRequest;
import vn.me.vlong.booklibraryapi.modules.users.command.controller.response.Success;
import vn.me.vlong.booklibraryapi.modules.users.command.service.UserCommandService;

@RestController
//@RequestMapping("/users")
public class UserCommandController {

    private UserCommandService userCommandService;

    @Autowired
    public UserCommandController(UserCommandService userCommandService) {
        this.userCommandService = userCommandService;
    }

    @PostMapping("/users/create")
    public Success createUser(@RequestBody CreateUserRequest createUserRequest) {
        userCommandService.createUser(createUserRequest);
        return Success.builder().status(HttpStatus.OK.value()).message("Create user success").build();
    }
}
