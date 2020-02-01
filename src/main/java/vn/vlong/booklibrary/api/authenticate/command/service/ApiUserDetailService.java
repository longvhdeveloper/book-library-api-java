package vn.vlong.booklibrary.api.authenticate.command.service;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import vn.vlong.booklibrary.api.authenticate.command.domain.entity.UserPrincipal;
import vn.vlong.booklibrary.api.eventsource.service.EventSourceService;
import vn.vlong.booklibrary.api.shared.domain.event.Event;
import vn.vlong.booklibrary.api.shared.logger.LogExecutionTime;
import vn.vlong.booklibrary.api.user.command.domain.entity.User;

@Service
public class ApiUserDetailService implements UserDetailsService {

  private EventSourceService eventSourceService;

  @Autowired
  public ApiUserDetailService(
      EventSourceService eventSourceService) {
    this.eventSourceService = eventSourceService;
  }

  @LogExecutionTime
  @Override
  public UserDetails loadUserByUsername(String usernameOrEmail) throws UsernameNotFoundException {
    List<Event> events = eventSourceService.loadEvents(usernameOrEmail, User.getStream());

    if (events.isEmpty()) {
      throw new UsernameNotFoundException(
          String.format("User with email %s is not exist", usernameOrEmail));
    }

    User user = new User(events);

    if (!user.isActive()) {
      throw new UsernameNotFoundException(
          String.format("User with email %s is not active", usernameOrEmail));
    }
    return new UserPrincipal(user);
  }
}
