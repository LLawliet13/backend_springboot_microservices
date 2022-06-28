package com.example.mock2.Entity;


import com.example.mock2.DTO.UserDTO;
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

//    public User(long userId, String userAddress, String userFullName
//            , String userPhone, String userEmail, Date userDob, String userGender) {
//
//    }

    public User(long userId, String username, String userAddress, String userFullname, String userPhone, String userEmail, Date userDob, String userGender) {
        this.userId = userId;
        this.username = username;
        this.userAddress = userAddress;
        this.userFullname = userFullname;
        this.userPhone = userPhone;
        this.userEmail = userEmail;
        this.userDob = userDob;
        this.userGender = userGender;
    }

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





    public UserDTO convertToUserDTO() {
        return new UserDTO(userId,username,userAddress,userFullname,userPhone,
                userEmail,userDob,userGender);
    }
}

