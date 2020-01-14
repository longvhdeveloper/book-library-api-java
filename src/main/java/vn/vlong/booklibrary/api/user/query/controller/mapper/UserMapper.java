package vn.vlong.booklibrary.api.user.query.controller.mapper;

import org.springframework.stereotype.Component;
import vn.vlong.booklibrary.api.user.query.controller.response.UserDTO;
import vn.vlong.booklibrary.api.user.query.domain.entity.User;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class UserMapper {
    public UserDTO toUserDTO(User user) {
        return UserDTO.builder()
                .id(user.getId())
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
}
