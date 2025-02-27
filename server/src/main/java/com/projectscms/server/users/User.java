package com.projectscms.server.users;

import com.fasterxml.jackson.annotation.*;
import com.projectscms.server.projects.Project;
import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import java.time.LocalDateTime;
import java.util.Collection;
import java.util.List;
import java.util.Set;

@Entity
@NoArgsConstructor
@Data
@JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, property = "id")
//@Table(name = "users") // uncomment when performing integration tests
public class User implements UserDetails {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank
    private String name;

    @NotBlank
    private String lastName;

    private String indexNr;

    @Email
    @NotBlank
    @Column(unique = true)
    private String email;

    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    private String password;

    @ElementCollection(fetch = FetchType.EAGER)
    @CollectionTable(name = "user_roles", joinColumns = @JoinColumn(name = "user_id"))
    @Enumerated(EnumType.STRING)
    private Set<Role> roles;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "projectOwner", fetch = FetchType.EAGER)
    @JsonIgnoreProperties({"projectOwner", "projectOwnerId"})
    private Set<Project> ownedProjects;

    @Override
    @JsonIgnore
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return roles.stream()
                .map(role -> new SimpleGrantedAuthority("ROLE_"+role.name()))
                .toList();
    }

    @Override
    public String getUsername() {
        return this.email;
    }

    //Reset Password Token
    private String token;
    private LocalDateTime tokenExpiryDate;

    public void generateToken(String token) {
        this.token = token;
        this.tokenExpiryDate = LocalDateTime.now().plusMinutes(15);
    }

    public void clearToken() {
        this.token = null;
        this.tokenExpiryDate = null;
    }

    public LocalDateTime getTokenExpirationTime() {
        return tokenExpiryDate;
    }

    public void setPassword(String password) {
        this.password = password;
    }

}