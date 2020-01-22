package vn.vlong.booklibrary.api.user.channel;

import org.springframework.cloud.stream.annotation.Input;
import org.springframework.messaging.SubscribableChannel;

public interface UserBinderSink {
    String INPUT = "user-topic-input";

    @Input(UserBinderSink.INPUT)
    SubscribableChannel input();
}
