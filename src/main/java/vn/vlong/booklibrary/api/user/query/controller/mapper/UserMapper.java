package vn.vlong.booklibrary.api.user.query.controller.mapper;

import java.util.List;
import java.util.stream.Collectors;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Flux;
import vn.vlong.booklibrary.api.user.query.controller.response.UserDTO;
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

  public List<UserDTO> toUserDTOs(List<User> users) {
    return users.stream().map(this::toUserDTO).collect(Collectors.toList());
  }

  public Flux<UserDTO> toUserDTOFlux(Flux<User> users) {
    return users.map(this::toUserDTO);
  }
}
