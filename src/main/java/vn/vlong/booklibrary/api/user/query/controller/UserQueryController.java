package vn.vlong.booklibrary.api.user.query.controller;

import java.util.concurrent.ExecutionException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import vn.vlong.booklibrary.api.user.query.controller.mapper.UserMapper;
import vn.vlong.booklibrary.api.user.query.controller.request.GetUsersRequest;
import vn.vlong.booklibrary.api.user.query.controller.response.UserDTOList;
import vn.vlong.booklibrary.api.user.query.domain.entity.User;
import vn.vlong.booklibrary.api.user.query.service.UserQueryService;

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

  @PreAuthorize("hasRole('ADMIN')")
  @GetMapping("/list")
  public UserDTOList getUsers(GetUsersRequest request)
      throws ExecutionException, InterruptedException {
    Page<User> userPage = userQueryService.getUsers(request).get();
    return userMapper.toUserDTOList(userPage);
  }
}
