package com.example.mock2.Entity;


import lombok.*;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import javax.persistence.*;
import java.sql.Date;
import java.util.Collection;
import java.util.Set;


@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@ToString
@Table (name = "user")
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

        return null;
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

    @ManyToMany (fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinTable(name = "user_role",
            joinColumns = @JoinColumn(name = "userId"),
            inverseJoinColumns = @JoinColumn(name = "roleId"))
    private Set<Role> roles;


    @OneToMany (fetch = FetchType.LAZY, cascade = CascadeType.ALL,mappedBy = "user")
    private Set<Rating> ratingSet;


    @OneToMany (fetch = FetchType.LAZY, cascade = CascadeType.ALL,mappedBy = "user")

    private Set<Cart> carts;


    @OneToMany (mappedBy = "user", fetch = FetchType.EAGER,
            cascade = {CascadeType.DETACH, CascadeType.MERGE,
                    CascadeType.PERSIST, CascadeType.REFRESH} )
    private Set<Bill> bills;


}

