package com.projectscms.server.tasks;

import com.projectscms.server.projects.Project;
import com.projectscms.server.projects.ProjectRepository;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.test.context.TestPropertySource;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@SpringBootTest
@TestPropertySource(locations = "classpath:application-test.properties")
public class TaskServiceTest {

    @MockBean
    private TaskRepository taskRepository;

    @MockBean
    private ProjectRepository projectRepository;

    @Autowired
    private TaskServiceImpl taskService;

    private Task sampleTask;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        sampleTask = new Task();
        sampleTask.setId(1L);
        sampleTask.setTaskName("Example task");
        sampleTask.setDescription("Create example task");
        sampleTask.setProject(new Project());
    }

    @Test
    void testCreateTask() {
        // Given
        when(taskRepository.save(sampleTask)).thenReturn(sampleTask);

        // When
        Task savedTask = taskService.addTask(sampleTask);

        // Then
        assertEquals(sampleTask, savedTask);
        verify(taskRepository, times(1)).save(sampleTask);
    }

    @Test
    void testGetAllTasks() {
        // Given
        List<Task> taskList = List.of(sampleTask);
        Page<Task> taskPage = new PageImpl<>(taskList);
        Pageable pageable = PageRequest.of(0, 10);

        // When
        when(taskRepository.findAll(pageable)).thenReturn(taskPage);
        List<Task> tasks = taskService.getTasks(pageable).getContent();

        // Then
        assertNotNull(tasks);
        assertEquals(1, tasks.size());
        verify(taskRepository, times(1)).findAll(pageable);
    }

    @Test
    void testGetTaskById() {
        // Given
        when(taskRepository.findById(1L)).thenReturn(Optional.of(sampleTask));

        // When
        Optional<Task> foundTask = taskService.getTaskById(1L);

        // Then
        assertTrue(foundTask.isPresent());
        assertEquals("Example task", foundTask.get().getTaskName());
        verify(taskRepository, times(1)).findById(1L);
    }

    @Test
    void testGetTaskByName() {
        // Given
        String name = "Example task";
        when(taskRepository.findByTaskNameContainingIgnoreCase(name)).thenReturn(Optional.of(sampleTask));

        // When
        Optional<Task> foundTask = taskService.getTaskByName(name);

        // Then
        assertTrue(foundTask.isPresent());
        assertEquals(name, foundTask.get().getTaskName());
        verify(taskRepository, times(1)).findByTaskNameContainingIgnoreCase(name);
    }

    @Test
    void testUpdateTask_TaskExists() {
        // Given
        Task taskToUpdate = new Task();
        taskToUpdate.setTaskName("Another example task");
        taskToUpdate.setDescription("Create another example task");

        when(taskRepository.findById(1L)).thenReturn(Optional.of(sampleTask));
        when(taskRepository.save(any(Task.class))).thenReturn(sampleTask);

        // When
        Optional<Task> updatedTask = taskService.updateTaskById(1L, taskToUpdate);

        // Then
        assertTrue(updatedTask.isPresent());
        verify(taskRepository, times(1)).findById(1L);
        verify(taskRepository, times(1)).save(any(Task.class));
    }

    @Test
    void testUpdateTask_TaskNotExists() {
        // Given
        when(taskRepository.findById(999L)).thenReturn(Optional.empty());

        // When
        taskService.updateTaskById(999L, sampleTask);

        // Then
        assertFalse(taskService.updateTaskById(999L, sampleTask).isPresent());
        verify(taskRepository, times(2)).findById(999L);
        verify(taskRepository, never()).save(any(Task.class));
    }

    @Test
    void testDeleteTaskById_TaskExists() {
        // Given
        when(taskRepository.findById(1L)).thenReturn(Optional.of(sampleTask));

        // When
        boolean result = taskService.deleteTaskById(1L);

        // Then
        assertTrue(result);
        verify(taskRepository, times(1)).findById(1L);
        verify(taskRepository, times(1)).deleteById(1L);
    }

    @Test
    void testDeleteTaskById_TaskNotExists() {
        // Given
        when(taskRepository.findById(999L)).thenReturn(Optional.empty());

        // When
        boolean result = taskService.deleteTaskById(999L);

        // Then
        assertFalse(result);
        verify(taskRepository, times(1)).findById(999L);
        verify(taskRepository, never()).delete(any(Task.class));
    }
}
