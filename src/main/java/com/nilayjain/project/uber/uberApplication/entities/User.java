<<<<<<< HEAD
package com.nilayjain.project.uber.uberApplication.entities;

import com.nilayjain.project.uber.uberApplication.entities.enums.Role;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Entity
@Getter
@Setter
@Table(name="app_user",indexes = {
        @Index(name = "idx_user_email", columnList = "email")
})

public class User implements UserDetails {

    @Id // primary key
    @GeneratedValue(strategy = GenerationType.IDENTITY) //SEQUENCE can be used in postgrace sql
    private Long id;

    private String name;

    @Column(unique = true)
    private String email;

    private String password;

    @ElementCollection(fetch = FetchType.EAGER) //use to follow database rul like 1Nf, 2NF, 3NF etc
    @Enumerated(EnumType.STRING)//use to tell hibernate that we are storing roles as it is in String format
    private Set<Role> roles;

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return roles.stream()
                .map(role -> new SimpleGrantedAuthority("ROLE_"+role.name()))
                .collect(Collectors.toSet());
    }

    @Override
    public String getUsername() {
        return email;
    }
}
=======
package com.nilayjain.project.uber.uberApplication.entities;

import com.nilayjain.project.uber.uberApplication.entities.enums.Role;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Entity
@Getter
@Setter
@Table(name="app_user",indexes = {
        @Index(name = "idx_user_email", columnList = "email")
})

public class User implements UserDetails {

    @Id // primary key
    @GeneratedValue(strategy = GenerationType.IDENTITY) //SEQUENCE can be used in postgrace sql
    private Long id;

    private String name;

    @Column(unique = true)
    private String email;

    private String password;

    @ElementCollection(fetch = FetchType.EAGER) //use to follow database rul like 1Nf, 2NF, 3NF etc
    @Enumerated(EnumType.STRING)//use to tell hibernate that we are storing roles as it is in String format
    private Set<Role> roles;

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return roles.stream()
                .map(role -> new SimpleGrantedAuthority("ROLE_"+role.name()))
                .collect(Collectors.toSet());
    }

    @Override
    public String getUsername() {
        return email;
    }
}
>>>>>>> master
