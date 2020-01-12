package vn.me.vlong.booklibraryapi.modules.eventsource.repository;

import org.springframework.data.mongodb.repository.MongoRepository;
import vn.me.vlong.booklibraryapi.modules.eventsource.model.EventSource;

public interface IEventSourceRepository extends MongoRepository<EventSource, String> {
}
