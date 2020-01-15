package vn.vlong.booklibrary.api.eventsource.domain;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.Generated;
import com.querydsl.core.types.Path;


/**
 * QEventSource is a Querydsl query type for EventSource
 */
@Generated("com.querydsl.codegen.EntitySerializer")
public class QEventSource extends EntityPathBase<EventSource> {

    private static final long serialVersionUID = 1626960742L;

    public static final QEventSource eventSource = new QEventSource("eventSource");

    public final StringPath aggregateId = createString("aggregateId");

    public final StringPath id = createString("id");

    public final DateTimePath<java.util.Date> occurred = createDateTime("occurred", java.util.Date.class);

    public final StringPath payload = createString("payload");

    public final StringPath type = createString("type");

    public final NumberPath<Integer> version = createNumber("version", Integer.class);

    public QEventSource(String variable) {
        super(EventSource.class, forVariable(variable));
    }

    public QEventSource(Path<? extends EventSource> path) {
        super(path.getType(), path.getMetadata());
    }

    public QEventSource(PathMetadata metadata) {
        super(EventSource.class, metadata);
    }

}

