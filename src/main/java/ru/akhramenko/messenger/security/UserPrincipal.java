package ru.akhramenko.messenger.security;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import ru.akhramenko.messenger.model.MessengerUser;

import java.security.Principal;
import java.util.Collection;
import java.util.Collections;

@Getter
@Setter
@Builder
public class UserPrincipal implements UserDetails, Principal {

    private MessengerUser messengerUser;

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return Collections.singletonList(new SimpleGrantedAuthority("User"));
    }

    @Override
    public String getName() {
        return  this.messengerUser.getId().toString();
    }

    @Override
    public String getPassword() {
        return this.messengerUser.getPassword();
    }

    @Override
    public String getUsername() {
        return this.messengerUser.getUserName();
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
        return true;
    }
}
