package dev.shulika.adminpanel.security;

import dev.shulika.adminpanel.model.WebUser;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.List;

@RequiredArgsConstructor
public class WebUserDetails implements UserDetails {
    private final WebUser webUser;

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return List.of(new SimpleGrantedAuthority(this.webUser.getRole().toString()));
    }

    @Override
    public String getPassword() {
        return this.webUser.getPassword();
    }

    @Override
    public String getUsername() {
        return this.webUser.getUsername();
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
        return this.webUser.getIsEnabled();
    }
}
