package vn.vlong.booklibrary.api.user.command.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;
import vn.vlong.booklibrary.api.shared.controller.response.Success;
import vn.vlong.booklibrary.api.user.command.controller.request.CreateUserRequest;
import vn.vlong.booklibrary.api.user.command.domain.entity.User;
import vn.vlong.booklibrary.api.user.command.service.UserCommandService;

import java.util.concurrent.CompletableFuture;

@RestController
@RequestMapping("/users")
public class UserCommandController {

    private UserCommandService userCommandService;

    @Autowired
    public UserCommandController(final UserCommandService userCommandService) {
        this.userCommandService = userCommandService;
    }

    @PostMapping("/create")
    public Success createUser(@RequestBody CreateUserRequest request) throws Exception {
        CompletableFuture<Mono<User>> completableFutureUser = userCommandService.createUser(request);
        completableFutureUser.get();
        return Success.builder().code(HttpStatus.OK.value()).message("Create user success").build();
    }
}
