package vn.vlong.booklibrary.api.user.query.consumer;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.stream.annotation.EnableBinding;
import org.springframework.cloud.stream.annotation.StreamListener;
import org.springframework.messaging.handler.annotation.Header;
import org.springframework.messaging.handler.annotation.Payload;
import vn.vlong.booklibrary.api.user.channel.UserBinderSink;
import vn.vlong.booklibrary.api.user.query.domain.UserCreatedEvent;
import vn.vlong.booklibrary.api.user.query.domain.entity.User;
import vn.vlong.booklibrary.api.user.query.repository.IUserQueryRepository;

@EnableBinding(UserBinderSink.class)
public class UserBinderConsumer {
    private static ObjectMapper objectMapper = new ObjectMapper();
    private IUserQueryRepository userQueryRepository;

    @Autowired
    public UserBinderConsumer(IUserQueryRepository userQueryRepository) {
        this.userQueryRepository = userQueryRepository;
    }

    @StreamListener(UserBinderSink.INPUT)
    public void receive(@Payload String payload, @Header(name = "type") String type) {
        try {
            System.out.println("TYPE:::" + type);
            System.out.println("Payload:::" + payload);
            switch (type) {
                case "UserCreatedEvent":
                    UserCreatedEvent userCreatedEvent = objectMapper.readValue(payload, UserCreatedEvent.class);
                    addNewUser(userCreatedEvent);
                    break;

            }
        } catch (JsonProcessingException e) {
            throw new RuntimeException(String.format("Can not process payload %s. Reason::::%s", payload,
                    e.getMessage()));
        }
    }

    private void addNewUser(UserCreatedEvent userCreatedEvent) {
        User user = new User(
                userCreatedEvent.getAggregateId(),
                userCreatedEvent.getFirstName(),
                userCreatedEvent.getLastName(),
                userCreatedEvent.getPassword(),
                userCreatedEvent.isActive(),
                userCreatedEvent.getActiveCode(),
                userCreatedEvent.getRole()
        );

        userQueryRepository.save(user);
    }
}
