package vn.vlong.booklibrary.api.eventsource.repository;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import vn.vlong.booklibrary.api.eventsource.domain.entity.EventSource;

@Repository
public interface IEventSourceRepository extends JpaRepository<EventSource, Integer> {

  @Query("SELECT es FROM EventSource es WHERE es.aggregateId=:aggregateId AND es.stream=:stream ORDER BY version ASC")
  List<EventSource> findByAggregateId(@Param("aggregateId") String aggregateId,
      @Param("stream") String stream);

  @Query(value = "SELECT DISTINCT(es.aggregate_id), es.id, es.stream, es.type_name, es.payload, es.version, es.occurred_on FROM event_source es", nativeQuery = true)
  List<EventSource> findAllDistinct();
}
