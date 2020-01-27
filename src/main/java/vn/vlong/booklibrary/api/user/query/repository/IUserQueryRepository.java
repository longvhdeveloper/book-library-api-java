package vn.vlong.booklibrary.api.user.query.repository;

import java.util.Optional;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;
import org.springframework.stereotype.Repository;
import vn.vlong.booklibrary.api.user.query.domain.entity.User;

@Repository
public interface IUserQueryRepository extends MongoRepository<User, String>,
    QuerydslPredicateExecutor<User> {

  @Query("{'email': ?0}")
  Optional<User> findByEmail(String email);
}
