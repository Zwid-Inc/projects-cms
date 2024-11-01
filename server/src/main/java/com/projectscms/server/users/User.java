package com.projectscms.server.users;

import com.fasterxml.jackson.annotation.*;
import com.projectscms.server.projects.Project;
import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Set;

@Entity
@NoArgsConstructor
@Data
@JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, property = "userId")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long userId;

    @NotBlank
    private String name;

    @NotBlank
    private String lastName;

    private String indexNr;

    @Email(regexp = "^(.+)@(pbs\\.edu\\.pl)$")
    @NotBlank
    @Column(unique = true)
    private String email;

    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    private String password;

    @ElementCollection(fetch = FetchType.EAGER)
    @CollectionTable(name = "user_roles", joinColumns = @JoinColumn(name = "user_id"))
    @Enumerated(EnumType.STRING)
    private Set<Roles> roles;

    @OneToMany( mappedBy = "projectOwner", fetch = FetchType.EAGER)
    @JsonIgnoreProperties({"projectOwner", "projectOwnerId"})
    private Set<Project> ownedProjects;

    public void setPassword(String password) {
        this.password = password;
        //TODO after configuring
        //this.password = new BCryptPasswordEncoder().encode(password);
    }


}