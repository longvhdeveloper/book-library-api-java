package vn.vlong.booklibrary.api.user.query.repository;

import com.fasterxml.jackson.core.JsonProcessingException;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.core.query.Query;
import vn.vlong.booklibrary.api.user.query.domain.entity.User;


public interface IUserQueryRepository {

  void save(User user);

  Page<User> findAll(Query query, Pageable pageable);

  Optional<User> findById(String id) throws JsonProcessingException;

  Optional<User> findByEmail(String email);
}
