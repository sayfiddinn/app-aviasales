package uz.pdp.appaviauz.entity;


import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import uz.pdp.appaviauz.entity.base.AbsEntity;

import java.util.Collection;
import java.util.stream.Collectors;

@EqualsAndHashCode(callSuper = true)
@Entity
@Table(name = "users")
@Data
public class User extends AbsEntity implements UserDetails {

    @Column(unique = true, nullable = false)
    private String email;
    private String code;
    @Column(nullable = false)
    private String passportId;
    @Column(nullable = false)
    private String password;
    @Column(nullable = false)
    private String phoneNumber;
    @JsonIgnore
    @Column(columnDefinition = "boolean default false ")
    private boolean isDeleted;
    private boolean accountNonExpired;
    private boolean accountNonLocked;
    private boolean credentialsNonExpired;
    private boolean enabled;
    @JsonIgnore
    @ManyToOne
    private Role role;

    {
        accountNonExpired = true;
        accountNonLocked = true;
        credentialsNonExpired = true;
        enabled = false;
    }


    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return role.getAuthorities()
                .stream()
                .map(authority -> new SimpleGrantedAuthority(authority.name()))
                .collect(Collectors.toSet());
    }


    @Override
    public String getUsername() {
        return email;
    }

    @Override
    public boolean isAccountNonExpired() {
        return accountNonExpired;
    }

    @Override
    public boolean isAccountNonLocked() {
        return accountNonLocked;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return credentialsNonExpired;
    }

    @Override
    public boolean isEnabled() {
        return enabled;
    }
}
