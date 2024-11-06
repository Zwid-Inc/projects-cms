package com.projectscms.server.tasks;

import com.projectscms.server.projects.Project;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Optional;

public interface TaskService
{
    Task addTask(Task task);
    Page<Task> getTasks(Pageable pageable);
    Page<Task> getAllProjectTasks(Long projectId, Pageable pageable);
    Optional<Task> getTaskById(Long id);
    Optional<Task> getTaskByName(String taskName);
    Optional<Task> updateTaskById(Long id, Task task);
    void deleteTaskById(Long id);
}
