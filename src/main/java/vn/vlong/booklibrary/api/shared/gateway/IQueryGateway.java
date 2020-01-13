package vn.vlong.booklibrary.api.shared.gateway;

import vn.vlong.booklibrary.api.shared.domain.query.Query;

public interface IQueryGateway<T extends Query> {
    void send(T query);
}
