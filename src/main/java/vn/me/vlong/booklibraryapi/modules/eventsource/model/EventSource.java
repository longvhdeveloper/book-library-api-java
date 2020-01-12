package vn.me.vlong.booklibraryapi.modules.eventsource.model;

import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.data.mongodb.core.mapping.Document;
import vn.me.vlong.booklibraryapi.modules.shared.model.entity.BaseEntity;

import javax.persistence.Id;
import java.util.Date;
import java.util.UUID;


@Document
@Getter
@NoArgsConstructor
public class EventSource extends BaseEntity<EventSource> {
    @Id
    private String id;
    private String type;
    private String aggregateId;
    private String payload;
    private Date occurred;

    public EventSource(UUID id, String type, UUID aggregateId, String payload, Date occurred) {
        this.id = id.toString();
        this.type = type;
        this.aggregateId = aggregateId.toString();
        this.payload = payload;
        this.occurred = occurred;
    }
}
