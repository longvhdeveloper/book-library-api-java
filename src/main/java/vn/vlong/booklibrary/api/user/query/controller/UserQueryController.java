package vn.vlong.booklibrary.api.user.query.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Flux;
import vn.vlong.booklibrary.api.user.query.controller.mapper.UserMapper;
import vn.vlong.booklibrary.api.user.query.controller.request.GetUsersRequest;
import vn.vlong.booklibrary.api.user.query.controller.response.UserDTO;
import vn.vlong.booklibrary.api.user.query.domain.entity.User;
import vn.vlong.booklibrary.api.user.query.service.UserQueryService;

import java.util.concurrent.ExecutionException;

@RestController
@RequestMapping("/users")
public class UserQueryController {

    private UserQueryService userQueryService;
    private UserMapper userMapper;

    @Autowired
    public UserQueryController(UserQueryService userQueryService, UserMapper userMapper) {
        this.userQueryService = userQueryService;
        this.userMapper = userMapper;
    }

    @GetMapping("/list")
    public Flux<UserDTO> getUsers(GetUsersRequest request) throws ExecutionException, InterruptedException {
        Flux<User> users = userQueryService.getUsers(request).get();
        return userMapper.toUserDTOFlux(users);
    }
}
