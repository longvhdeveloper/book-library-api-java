package vn.vlong.booklibrary.api.configuration;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import vn.vlong.booklibrary.api.shared.bus.Bus;

@Configuration
public class EventBusConfig {

    @Bean
    public Bus bus() {
        return new Bus();
    }
}
