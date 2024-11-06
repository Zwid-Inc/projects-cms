package com.projectscms.server.tasks;

import jakarta.annotation.security.RolesAllowed;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;

@RestController
@RequiredArgsConstructor
@RequestMapping("/tasks")
public class TaskController {

    private final TaskService taskService;
    private static final Logger LOG = LoggerFactory.getLogger(TaskController.class);

    @GetMapping
 //   @RolesAllowed("ADMIN")
    Page<Task> getTasks(@RequestParam(name = "page", defaultValue = "0") int page,
                        @RequestParam(name = "size", defaultValue = "20") int size){
        Pageable pageable = PageRequest.of(page, size);
        return taskService.getTasks(pageable);
    }

    @GetMapping("/{id}")
  //  @RolesAllowed("ADMIN")
    public ResponseEntity<?> getTaskById(@PathVariable Long id){
         return taskService.getTaskById(id).isPresent() ?
                ResponseEntity.ok(taskService.getTaskById(id).get()) :
                ResponseEntity.notFound().build();
    }

    @PostMapping
    public ResponseEntity<?> addTask(@RequestBody Task task) {
        try{
            Task createdTask = taskService.addTask(task);
            URI location = ServletUriComponentsBuilder.fromCurrentRequest()
                    .path("/{categoryId}").buildAndExpand(createdTask.getId()).toUri();
            return ResponseEntity.created(location).build();
        } catch (Exception e){
            LOG.error(">>>addTask<<< ERROR: ", e);
            return ResponseEntity.badRequest().build();
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> updateTask(@PathVariable Long id, @RequestBody Task task) {
        return taskService.updateTaskById(id, task).isPresent() ?
                ResponseEntity.ok(taskService.updateTaskById(id, task).get()) :
                ResponseEntity.badRequest().build();
    }


    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteTaskById(@PathVariable Long id) {
        return taskService.getTaskById(id).map(task -> {
            taskService.deleteTaskById(id);
            return new ResponseEntity<Void>(HttpStatus.OK);
        }).orElseGet(() -> ResponseEntity.notFound().build());
    }
}
