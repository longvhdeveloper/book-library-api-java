package vn.vlong.booklibrary.api.user.query.repository;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;
import org.springframework.stereotype.Repository;
import vn.vlong.booklibrary.api.user.query.domain.entity.User;

@Repository
public interface IUserQueryRepository extends MongoRepository<User, String>, QuerydslPredicateExecutor<User> {
}
