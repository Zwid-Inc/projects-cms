package com.projectscms.server.tasks;

import com.projectscms.server.projects.ProjectServiceImpl;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class TaskServiceImpl implements TaskService{

    private final TaskRepository taskRepository;
    private static final Logger LOG = LoggerFactory.getLogger(TaskServiceImpl.class);

    @Override
    public Task addTask(Task task) {
        return taskRepository.save(task);
    }

    @Override
    public Page<Task> getTasks(Pageable pageable) {
        return taskRepository.findAll(pageable);
    }

    @Override
    public Page<Task> getAllProjectTasks(Long projectId, Pageable pageable) {
        return taskRepository.findByProjectId(projectId,pageable);
    }

    @Override
    public Optional<Task> getTaskById(Long id) {
        return taskRepository.findById(id);
    }

    @Override
    public Optional<Task> getTaskByName(String taskName) {
        return taskRepository.findByTaskNameContainingIgnoreCase(taskName);
    }

    @Override
    public Optional<Task> updateTaskById(Long id, Task task) {
        Optional<Task> taskOptional = taskRepository.findById(id);
        if(taskOptional.isPresent()){
            Task taskToUpdate = taskOptional.get();
            taskToUpdate.setTaskName(task.getTaskName());
            taskToUpdate.setDescription(task.getDescription());
            Task updatedTask = taskRepository.save(taskToUpdate);
            return Optional.of(updatedTask);
        } else {
            return Optional.empty();
        }
    }

    @Override
    public void deleteTaskById(Long id) {
        taskRepository.deleteById(id);
    }
}
