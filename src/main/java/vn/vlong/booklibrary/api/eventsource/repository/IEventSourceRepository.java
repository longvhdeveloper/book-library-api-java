package vn.vlong.booklibrary.api.eventsource.repository;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;
import vn.vlong.booklibrary.api.eventsource.domain.EventSource;

@Repository
public interface IEventSourceRepository extends MongoRepository<EventSource, String> {
}
