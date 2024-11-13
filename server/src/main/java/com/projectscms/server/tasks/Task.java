package com.projectscms.server.tasks;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.projectscms.server.projects.Project;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@Entity
public class Task {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @Column(length = 50, nullable = false)
    private String taskName;

    private Long sequenceNr;

    @Column(length = 1000)
    private String description;

    @CreationTimestamp
    @NotNull
    private LocalDateTime creationDateTime;

    @ManyToOne
    @JsonBackReference
    private Project project;
}
