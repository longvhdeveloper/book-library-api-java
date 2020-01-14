package vn.vlong.booklibrary.api.user.query.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import vn.vlong.booklibrary.api.user.query.controller.request.GetUsersRequest;
import vn.vlong.booklibrary.api.user.query.domain.entity.User;
import vn.vlong.booklibrary.api.user.query.repository.IUserQueryRepository;

import java.util.List;

@Service
public class UserQueryService {
    private IUserQueryRepository userQueryRepository;

    @Autowired
    public UserQueryService(IUserQueryRepository userQueryRepository) {
        this.userQueryRepository = userQueryRepository;
    }

    public List<User> getUsers(GetUsersRequest request) {
        return userQueryRepository.findUsers(request.getKeyword(), request.isActive(), request.getRole(),
                PageRequest.of(request.getOffset() - 1, request.getLimit()));
    }
}
