package vn.vlong.booklibrary.api.authenticate.command.domain.entity;

import java.util.Collection;
import java.util.Collections;
import lombok.Getter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import vn.vlong.booklibrary.api.user.command.domain.entity.User;

@Getter
public class UserPrincipal implements UserDetails {

  private User user;
  private Collection<? extends GrantedAuthority> authorities;

  public UserPrincipal(User user) {
    this.user = user;
    authorities = Collections
        .singleton(new SimpleGrantedAuthority("ROLE_" + user.getUserRole().getRole().getName()));
  }

  @Override
  public Collection<? extends GrantedAuthority> getAuthorities() {
    return authorities;
  }

  @Override
  public String getPassword() {
    return user.getPassword().getPassword();
  }

  @Override
  public String getUsername() {
    return user.getEmail().getEmail();
  }

  @Override
  public boolean isAccountNonExpired() {
    return true;
  }

  @Override
  public boolean isAccountNonLocked() {
    return true;
  }

  @Override
  public boolean isCredentialsNonExpired() {
    return true;
  }

  @Override
  public boolean isEnabled() {
    return user.isActive();
  }
}
