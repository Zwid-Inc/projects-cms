package com.projectscms.server.projects;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import com.projectscms.server.tasks.Task;
import com.projectscms.server.users.User;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDateTime;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

@Getter
@Setter
@Entity
@NoArgsConstructor
@JsonPropertyOrder({
        "id",
        "projectName",
        "projectDescription",
        "projectOwnerId",
        "MaintainersIds",
        "creationTime",
        "releaseDate",
        "taskList"
})
public class Project {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, length = 50)
    private String projectName;

    @Column(nullable = false, length = 1000)
    private String projectDescription;

    @ManyToOne(optional = true)
    @JsonIgnoreProperties({"ownedProjects"})
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    private User projectOwner;

    @ManyToMany(fetch = FetchType.EAGER, cascade = CascadeType.REFRESH)
    @JsonIgnoreProperties({"ownedProjects"})
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    private Set<User> projectMaintainers;

    @CreationTimestamp
    @Column(nullable = false, updatable = false)
    private LocalDateTime creationTime;

    private LocalDateTime releaseDate;

    @OneToMany(mappedBy = "project", cascade = CascadeType.ALL, fetch = FetchType.EAGER, orphanRemoval = true)
    @JsonManagedReference
    private List<Task> taskList;

    public Project(String projectName, String projectDescription) {
        this.projectName = projectName;
        this.projectDescription = projectDescription;
    }


    @JsonProperty("projectOwnerId")
    public long getProjectOwnerId(){
        return projectOwner.getUserId();
    }

    @JsonProperty(value = "MaintainersIds", access = JsonProperty.Access.READ_ONLY)
    public List<Map<String, Long>> getProjectMaintainersIds() {
        return projectMaintainers.stream()
                .map(user -> Map.of("userId", user.getUserId()))
                .toList();
    }

    public void removeMaintainerById(Long userId) {
        Iterator<User> iterator = projectMaintainers.iterator();
        while (iterator.hasNext()) {
            User maintainer = iterator.next();
            if (maintainer.getUserId().equals(userId)) {
                iterator.remove();
                break;
            }
        }
    }

}