package com.projectscms.server.projects;

import com.projectscms.server.users.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/projects")
@RequiredArgsConstructor
// TODO @Secured({"ROLE_ADMIN", "ROLE_USER"})
public class ProjectController {

    private final ProjectService projectService;
    private final UserService userService;

    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping
    public ResponseEntity<?> addProject(@RequestParam Long ownerId, @RequestBody Project project) {
        return userService.getUserById(ownerId).map(owner -> {
            project.setProjectOwner(owner);
            projectService.addProject(project);
            return ResponseEntity.status(201).build();
        }).orElse(ResponseEntity.badRequest().build());
    }

    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping
    public ResponseEntity<?> getAllProjects() {
        return ResponseEntity.ok(projectService.getAllProjects());
    }

    @PreAuthorize("hasRole('ADMIN') or #username == principal.email")
    @GetMapping("/users")
    public ResponseEntity<?> getUserProjects(@RequestParam String username) {
        return ResponseEntity.ok(projectService.getAllUserProjects(username));
    }

    //TODO @Secured("ROLE_ADMIN")
    @PreAuthorize("hasRole('USER') or hasRole('ADMIN')")
    @GetMapping("/{id}")
    public ResponseEntity<?> getProjectById(@PathVariable Long id) {
        return projectService.getProjectById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PreAuthorize("hasRole('USER') or hasRole('ADMIN')")
    @GetMapping("/byName/{name}")
    public ResponseEntity<?> getProjectByName(@PathVariable String name) {
        return projectService.getProjectByProjectName(name)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PreAuthorize("hasRole('ADMIN')")
    @PatchMapping("/{id}")
    public ResponseEntity<?> updateProjectById(@PathVariable Long id, @RequestBody Project project) {
        Project updatedProject = projectService.updateProjectById(id, project);
        return updatedProject != null ?
                ResponseEntity.ok("Project has been updated: " + updatedProject) :
                ResponseEntity.notFound().build();
    }

    @PreAuthorize("hasRole('ADMIN')")
    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteProjectById(@PathVariable Long id) {
         projectService.deleteProjectById(id);
               return ResponseEntity.ok("Project deleted successfully");
    }
}