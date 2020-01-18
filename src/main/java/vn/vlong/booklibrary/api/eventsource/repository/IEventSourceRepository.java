package vn.vlong.booklibrary.api.eventsource.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import vn.vlong.booklibrary.api.eventsource.domain.entity.EventSource;

import java.util.List;

@Repository
public interface IEventSourceRepository extends JpaRepository<EventSource, Integer> {

    @Query("SELECT es FROM EventSource es WHERE es.aggregateId=:aggregateId AND es.stream=:stream")
    List<EventSource> findByAggregateId(@Param("aggregateId") String aggregateId, @Param("stream") String stream);
}
