package com.spring.api.model;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.userdetails.UserDetails;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Table(name="users")
@JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, property = "id")
@JsonIgnoreProperties("items")
public class ApplicationUser implements UserDetails {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Getter
    @Setter
    private Long id;

    @NotNull
    @Column(unique=true)
    @Getter
    @Setter
    private String username;

    @NotNull
    @Getter
    @Setter
    private String password;

    @NotNull
    @Getter
    @Setter
    private Boolean admin;

    @NotNull
    @Getter
    @Setter
    private Boolean locked;

    @NotNull
    @Getter
    @Setter
    private Boolean expired;

    @Getter
    @Setter
    @ManyToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JoinTable(name = "users_items",
            joinColumns = {@JoinColumn(name = "user_id")},
            inverseJoinColumns = {@JoinColumn(name = "item_id")})
    private List<Item> items = new ArrayList<>();

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        if(this.admin != null && this.admin) {
            return AuthorityUtils.createAuthorityList("ROLE_ADMIN");
        }

        return AuthorityUtils.createAuthorityList("ROLE_USER");
    }

    @Override
    public boolean isAccountNonExpired() {
        return !this.expired;
    }

    @Override
    public boolean isAccountNonLocked() {
        return !this.locked;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return !this.expired;
    }

    @Override
    public boolean isEnabled() {
        return !this.expired;
    }
}