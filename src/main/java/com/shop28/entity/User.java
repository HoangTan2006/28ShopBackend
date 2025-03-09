package com.shop28.entity;

import jakarta.persistence.*;
import lombok.*;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.*;

@Setter
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "users")
public class User extends AbstractEntity<Integer> implements CustomUserDetails {
    @Column(name = "first_name")
    private String firstName;

    @Column(name = "last_name")
    private String lastName;

    @Column(name = "username")
    private String username;

    @Column(name = "password")
    private String password;

    @Column(name = "email")
    private String email;

    @Column(name = "phone")
    private Integer phone;

    @Column(name = "is_lock")
    private Boolean isLock;

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    private Set<UserHasRole> roles = new HashSet<>();

    @OneToMany(mappedBy = "user")
    private Set<CartItem> carts = new HashSet<>();

    @OneToMany(mappedBy = "user")
    private Set<Order> orders = new HashSet<>();

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
         return roles.stream().map(r -> new SimpleGrantedAuthority("ROLE_" + r.getRole().getName())).toList();
    }

    @Override
    public boolean isAccountNonExpired() {
        return CustomUserDetails.super.isAccountNonExpired();
    }

    @Override
    public boolean isAccountNonLocked() {
        return !isLock;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return CustomUserDetails.super.isCredentialsNonExpired();
    }

    @Override
    public boolean isEnabled() {
        return CustomUserDetails.super.isEnabled();
    }
}
