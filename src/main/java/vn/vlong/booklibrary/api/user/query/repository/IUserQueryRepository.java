package vn.vlong.booklibrary.api.user.query.repository;

import org.springframework.data.domain.PageRequest;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.stereotype.Repository;
import vn.vlong.booklibrary.api.user.query.domain.entity.User;

import java.util.List;

@Repository
public interface IUserQueryRepository extends MongoRepository<User, String> {

    @Query("{$and : [{$or : [{first_name: {$regex: ?0}}, {last_name : {$regex: ?0}}, {email : {$regex: ?0}}]}, {is_active: ?1}, {role : ?2}]}")
    List<User> findUsers(String keyword, boolean isActive, int role, PageRequest pageRequest);
}
