package com.example.mock2.Entity;


import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.*;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import javax.persistence.*;
import java.sql.Date;
import java.util.Collection;
import java.util.Set;
import java.util.stream.Collectors;


@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@ToString
@Table (name = "user")
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
public class User implements UserDetails {

    @Id
    @GeneratedValue (strategy = GenerationType.IDENTITY)
    @Column(name = "userId")
    private long userId;

    @Column(name = "username")
    private String username;
    @Column(name = "password")
    private String password;

    @Column(name = "userAddress")
    private String userAddress;

    @Column(name = "userFullname")
    private String userFullname;

    @Column(name = "userPhone")
    private String userPhone;

    @Column(name = "userEmail")
    private String userEmail;

    @Column(name = "userDob")
    private Date userDob;

    @Column(name = "userGender")
    private String userGender;

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {

        if(this.getRoles() == null ) return null;
        return this.getRoles().stream()
                .map(r -> new SimpleGrantedAuthority(r.getRoleName()))
                .collect(Collectors.toSet());
    }

    @Override
    public String getPassword() {
        return password;
    }

    @Override
    public String getUsername() {
        return username;
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

    @JsonBackReference
    @ManyToMany (fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinTable(name = "user_role",
            joinColumns = @JoinColumn(name = "userId"),
            inverseJoinColumns = @JoinColumn(name = "roleId"))
    private Set<Role> roles;


    @JsonBackReference
    @OneToMany (fetch = FetchType.LAZY, cascade = CascadeType.ALL,mappedBy = "user")
    private Set<Rating> ratings;

    @JsonBackReference
    @OneToMany (fetch = FetchType.LAZY, cascade = CascadeType.ALL,mappedBy = "user")
    private Set<Cart> carts;


    @OneToMany (fetch = FetchType.LAZY, cascade = CascadeType.ALL,mappedBy = "user")
    private Set<Bill> bills;


}

