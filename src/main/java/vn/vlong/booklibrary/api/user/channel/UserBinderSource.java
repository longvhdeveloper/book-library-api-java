package vn.vlong.booklibrary.api.user.channel;

import org.springframework.cloud.stream.annotation.Output;
import org.springframework.messaging.MessageChannel;

public interface UserBinderSource {
    String OUTPUT = "user-topic-output";

    @Output(UserBinderSource.OUTPUT)
    MessageChannel output();
}
