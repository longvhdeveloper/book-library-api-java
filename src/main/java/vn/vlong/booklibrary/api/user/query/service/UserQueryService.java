package vn.vlong.booklibrary.api.user.query.service;

import com.querydsl.core.types.dsl.BooleanExpression;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import vn.vlong.booklibrary.api.user.query.controller.request.GetUsersRequest;
import vn.vlong.booklibrary.api.user.query.domain.entity.QUser;
import vn.vlong.booklibrary.api.user.query.domain.entity.User;
import vn.vlong.booklibrary.api.user.query.repository.IUserQueryRepository;

import java.util.List;
import java.util.Objects;

@Service
public class UserQueryService {
    private IUserQueryRepository userQueryRepository;

    @Autowired
    public UserQueryService(IUserQueryRepository userQueryRepository) {
        this.userQueryRepository = userQueryRepository;
    }

    public List<User> getUsers(GetUsersRequest request) {
        QUser qUser = QUser.user;

        BooleanExpression conditions = qUser.email.like("%" + request.getKeyword() + "%")
                .or(qUser.firstName.like("%" + request.getKeyword() + "%"))
                .or(qUser.lastName.like("%" + request.getKeyword() + "%"));

        if (!Objects.isNull(request.getIsActive())) {
            conditions = conditions.and(qUser.isActive.eq(request.getIsActive()));
        }

        if (!Objects.isNull(request.getRole())) {
            conditions = conditions.and(qUser.role.eq(request.getRole()));
        }

        return userQueryRepository.findAll(conditions, PageRequest.of(request.getOffset() - 1, request.getLimit()))
                .getContent();
    }
}
