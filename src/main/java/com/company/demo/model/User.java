package com.company.demo.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

@Entity
@Table(name = "auth_user")
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "user_status_id")
    private UserStatus userStatus;

    @Column(unique = true, name = "username")
    private String username;

    private String email;
    private String name;
    private Long externalId;

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(name = "auth_user_role",
            joinColumns = {@JoinColumn(name = "user_id", referencedColumnName = "id")},
            inverseJoinColumns = {@JoinColumn(name = "role_id", referencedColumnName = "id")})
    private List<Role> roles;

    public Collection<? extends GrantedAuthority> authorities() {

        List<GrantedAuthority> grantedAuthorities = new ArrayList<>();

        getRoles().forEach(role -> {
            grantedAuthorities.add(new SimpleGrantedAuthority(role.getRoleName()));
            role.getPermissions().forEach(permission -> grantedAuthorities.add(new SimpleGrantedAuthority(permission.getPermissionName())));
        });
        return grantedAuthorities;
    }
}

