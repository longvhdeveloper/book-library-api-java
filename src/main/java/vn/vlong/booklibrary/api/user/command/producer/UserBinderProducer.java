package vn.vlong.booklibrary.api.user.command.producer;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.stream.annotation.EnableBinding;
import org.springframework.messaging.support.MessageBuilder;
import vn.vlong.booklibrary.api.shared.domain.entity.BaseEntity;
import vn.vlong.booklibrary.api.shared.domain.event.Event;
import vn.vlong.booklibrary.api.shared.producer.IProducer;
import vn.vlong.booklibrary.api.user.channel.UserBinderSource;

import java.util.List;

@EnableBinding(UserBinderSource.class)
@Slf4j
public class UserBinderProducer implements IProducer {
    private UserBinderSource userBinderSource;
    private ObjectMapper objectMapper;

    @Autowired
    public UserBinderProducer(UserBinderSource userBinderSource, ObjectMapper objectMapper) {
        this.userBinderSource = userBinderSource;
        this.objectMapper = objectMapper;
    }

    @Override
    public void send(Event event) {
        try {
            String payload = objectMapper.writeValueAsString(event);
            userBinderSource.output().send(MessageBuilder.withPayload(payload).setHeader("type",
                    event.getClass().getSimpleName()).build());
        } catch (JsonProcessingException e) {
            log.error(String.format("Can not send event:::%s", e.getMessage()));
        }
    }

    @Override
    public void send(List<Event> events) {
        events.forEach(this::send);
    }

    @Override
    public void send(BaseEntity entity) {
        send(entity.getUnCommittedEvents());
        entity.clearUnCommittedEvents();
    }
}
