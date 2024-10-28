package com.projectscms.server.users;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Set;

@Entity
@NoArgsConstructor
@Data
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank
    private String name;

    @NotBlank
    private String lastName;

    private String indexNr;

    @Email(regexp = "^(.+)@(pbs\\.edu\\.pl)$")
    @NotBlank
    @Column(unique = true)
    private String email;

    // TODO after adding Project
   /* @OneToMany(cascade = CascadeType.ALL, mappedBy = "projectOwner")
    @JsonManagedReference
    private Set<Project> ownedProjects; */

    @ElementCollection(fetch = FetchType.EAGER)
    @CollectionTable(name = "user_roles", joinColumns = @JoinColumn(name = "user_id"))
    @Enumerated(EnumType.STRING)
    private Set<Roles> roles;

    @JsonProperty("password")
    private String password;

    public void setPassword(String password) {
        this.password = password;
        //TODO after configuring
        //this.password = new BCryptPasswordEncoder().encode(password);
    }
}