package vn.vlong.booklibrary.api.user.query.service;

import java.util.Objects;
import java.util.concurrent.CompletableFuture;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import vn.vlong.booklibrary.api.shared.logger.LogExecutionTime;
import vn.vlong.booklibrary.api.user.query.controller.request.GetUsersRequest;
import vn.vlong.booklibrary.api.user.query.domain.entity.User;
import vn.vlong.booklibrary.api.user.query.repository.IUserQueryRepository;

@Service
public class UserQueryService {

  private IUserQueryRepository userQueryRepository;

  @Autowired
  public UserQueryService(IUserQueryRepository userQueryRepository) {
    this.userQueryRepository = userQueryRepository;
  }

  @Async("asyncExecutor")
  @LogExecutionTime
  public CompletableFuture<Page<User>> getUsers(GetUsersRequest request) {
    Query query = new Query();
    Criteria criteria = new Criteria();
    if (StringUtils.hasText(request.getKeyword())) {
      criteria.andOperator(Criteria.where("email").regex(request.getKeyword()).orOperator(
          Criteria.where("first_name").regex(request.getKeyword()).orOperator(
              Criteria.where("last_name").regex(request.getKeyword())
          )
      ));
    }

    if (!Objects.isNull(request.getIsActive())) {
      criteria.andOperator(Criteria.where("is_active").is(request.getIsActive()));
    }

    if (!Objects.isNull(request.getRole())) {
      criteria.andOperator(Criteria.where("role").is(request.getRole()));
    }
    return CompletableFuture
        .completedFuture(userQueryRepository.findAll(query, PageRequest.of(request.getOffset() - 1,
            request.getLimit())));
  }
}
