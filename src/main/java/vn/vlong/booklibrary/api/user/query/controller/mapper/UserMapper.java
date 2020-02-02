package vn.vlong.booklibrary.api.user.query.controller.mapper;

import java.util.stream.Collectors;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Component;
import vn.vlong.booklibrary.api.user.query.controller.response.UserDTO;
import vn.vlong.booklibrary.api.user.query.controller.response.UserDTOList;
import vn.vlong.booklibrary.api.user.query.domain.entity.User;

@Component
public class UserMapper {

  public UserDTO toUserDTO(User user) {
    return UserDTO.builder()
        .firstName(user.getFirstName())
        .lastName(user.getLastName())
        .email(user.getEmail())
        .isActive(user.isActive())
        .activeCode(user.getActiveCode())
        .role(user.getRole())
        .build();
  }

  public UserDTOList toUserDTOList(Page<User> userPage) {
    return UserDTOList.builder()
        .total(userPage.getTotalElements())
        .totalPage(userPage.getTotalPages())
        .users(userPage.getContent().stream().map(this::toUserDTO).collect(Collectors.toList()))
        .build();
  }
}
