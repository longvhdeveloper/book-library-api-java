package vn.vlong.booklibrary.api.user.query.repository;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.repository.support.PageableExecutionUtils;
import org.springframework.stereotype.Repository;
import vn.vlong.booklibrary.api.user.query.domain.entity.User;

@Repository
public class UserQueryRepositoryImpl implements IUserQueryRepository {

  private MongoTemplate mongoTemplate;
  private ObjectMapper objectMapper;
  private StringRedisTemplate stringRedisTemplate;

  public UserQueryRepositoryImpl(MongoTemplate mongoTemplate,
      ObjectMapper objectMapper,
      StringRedisTemplate stringRedisTemplate) {
    this.mongoTemplate = mongoTemplate;
    this.objectMapper = objectMapper;
    this.stringRedisTemplate = stringRedisTemplate;
  }

  @Override
  public void save(User user) {
    try {
      Objects.requireNonNull(user);
      mongoTemplate.save(user);
      storeCache(user);
    } catch (Exception e) {
      throw new RuntimeException(
          String.format("Can not store user with id %s. Reason: %s", user.getId(), e.getMessage()));
    }
  }

  @Override
  public Page<User> findAll(Query query, Pageable pageable) {
    query.with(pageable);
    query.fields().include("_id");
    List<User> userIds = mongoTemplate.find(query, User.class);
    List<User> users = userIds.stream().map(user -> findById(user.getId()))
        .filter(Optional::isPresent).map(Optional::get)
        .collect(Collectors.toList());
    return PageableExecutionUtils
        .getPage(users, pageable, () -> mongoTemplate.count(query, User.class));
  }

  @Override
  public Optional<User> findByEmail(String email) {
    Query query = new Query();
    query.addCriteria(Criteria.where("email").is(email));
    query.fields().include("_id");
    User userId = mongoTemplate.findOne(query, User.class);
    if (Objects.isNull(userId)) {
      return Optional.empty();
    }
    return findById(userId.getId());
  }

  @Override
  public Optional<User> findById(String id) {
    try {
      if (Objects.requireNonNull(stringRedisTemplate.hasKey(id)).equals(true)) {
        System.out.println("HIT-CACHE::::::");
        User user = objectMapper
            .readValue(Objects.requireNonNull(stringRedisTemplate.opsForValue().get(id))
                , User.class);
        return Optional.of(user);
      }
      Query query = new Query();
      query.addCriteria(Criteria.where("id").is(id));
      User user = mongoTemplate.findOne(query, User.class);
      storeCache(Objects.requireNonNull(user));
      return Optional.of(user);
    } catch (Exception e) {
      throw new RuntimeException(
          String.format("Can not get user with id %s. Reason: %s", id, e.getMessage()));
    }
  }

  private void storeCache(User user) throws JsonProcessingException {
    stringRedisTemplate.opsForValue().set(user.getId(), objectMapper.writeValueAsString(user));
  }
}
