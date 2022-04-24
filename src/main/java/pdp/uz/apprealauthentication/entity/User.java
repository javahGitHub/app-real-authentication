package pdp.uz.apprealauthentication.entity;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.Size;
import java.sql.Timestamp;
import java.util.Collection;
import java.util.List;
import java.util.Set;
import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity(name = "users")
public class User implements UserDetails {

    @Id
    @GeneratedValue
    private UUID id;//generate universal id

    @Column(nullable = false, length = 50)
    private String firstName;//first name length between 3-50 chs


    @Column(nullable = false, length = 50)
    private String lastName;//last name length between 3-50 chs

    @Column(unique = true, nullable = false)
    private String email;//only email

    @Column(unique = true, nullable = false)
    private String password;//unique password


    @Column(nullable = false, updatable = false)
    @CreationTimestamp
    private Timestamp createdAt;//user login date non updatable


    @UpdateTimestamp
    private Timestamp updatedAt;//user last updated time


    @ManyToMany
    private Set<Role> roles;


    // →-→-→-→-→-→-→-→-→-→-→-→-→-→-→-→ User fields for Override →-→-→-→-→-→-→-→-→-→-→-→-→-→-→-→❤

    private boolean accountNonExpired=true;

    private boolean accountNonLocked=true;

    private boolean credentialsNonExpired=true;

    private boolean enabled;

    // →-→-→-→-→-→-→-→-→-→-→-→-→-→-→-→ User details for Override →-→-→-→-→-→-→-→-→-→-→-→-→-→-→-→❤


    //Get User roles
    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return this.roles;
    }

    //Get User Email
    @Override
    public String getUsername() {
        return this.email;
    }

    //Check User account to expired
    @Override
    public boolean isAccountNonExpired() {
        return this.accountNonExpired;
    }

    //Check User account to locked
    @Override
    public boolean isAccountNonLocked() {
        return this.accountNonLocked;
    }

    //Check User credentials to expired
    @Override
    public boolean isCredentialsNonExpired() {
        return this.credentialsNonExpired;
    }

    //Check User is enabled to enter
    @Override
    public boolean isEnabled() {
        return this.enabled;
    }

    private String emailCode;

}
