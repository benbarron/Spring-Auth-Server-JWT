package io.github.benbarron.springauthorizationserver.models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import java.util.Set;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Document(collection = "users")
public class User implements UserDetails {

    @Field
    @Id
    private String id;

    @Field
    @JsonIgnore
    private String password;

    @Field
    @Indexed(unique = true)
    private String username;

    @Field
    private boolean accountNonExpired;

    @Field
    private boolean accountNonLocked;

    @Field
    private boolean credentialsNonExpired;

    @Field
    private boolean enabled;

    @Field
    private Set<GrantedAuthority> authorities;

    public User(String username, String password, boolean accountNonExpired, boolean accountNonLocked,
                boolean credentialsNonExpired, boolean enabled, Set<GrantedAuthority> authorities) {
        this.username = username;
        this.password = password;
        this.accountNonExpired = accountNonExpired;
        this.accountNonLocked = accountNonLocked;
        this.credentialsNonExpired = credentialsNonExpired;
        this.enabled = enabled;
        this.authorities = authorities;
    }

    public User(String username, String password) {
        this.username = username;
        this.password = password;
        this.accountNonExpired = true;
        this.accountNonLocked = true;
        this.credentialsNonExpired = true;
        this.enabled = true;
    }
}
