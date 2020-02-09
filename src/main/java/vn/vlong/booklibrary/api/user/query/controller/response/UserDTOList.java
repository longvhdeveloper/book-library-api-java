package vn.vlong.booklibrary.api.user.query.controller.response;

import java.util.List;
import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
public class UserDTOList {

  private long total;
  private int totalPage;
  private List<UserDTO> users;
}
