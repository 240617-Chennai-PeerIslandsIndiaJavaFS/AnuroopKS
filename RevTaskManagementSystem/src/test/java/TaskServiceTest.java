import org.example.dao.TaskDAO;
import org.example.models.Task;
import org.example.service.TaskService;
import org.example.service.TimestampsService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.sql.SQLException;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class TaskServiceTest {

    @Mock
    private TaskDAO taskDAO;

    @Mock
    private TimestampsService timestampsService;

    @InjectMocks
    private TaskService taskService;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testAddTask_Positive() throws SQLException {
        Task task = new Task();
        when(taskDAO.addTask(task)).thenReturn(true);

        boolean result = taskService.addTask(task);

        assertTrue(result);
        verify(taskDAO, times(1)).addTask(task);
    }

    @Test
    public void testAddTask_Negative() throws SQLException {
        Task task = new Task();
        when(taskDAO.addTask(task)).thenReturn(false);

        boolean result = taskService.addTask(task);

        assertFalse(result);
        verify(taskDAO, times(1)).addTask(task);
    }


    @Test
    public void testGetTaskByName_Positive() throws SQLException {
        Task task = new Task();
        when(taskDAO.getTaskByName("Test Task")).thenReturn(task);

        Task result = taskService.getTaskByName("Test Task");

        assertEquals(task, result);
        verify(taskDAO, times(1)).getTaskByName("Test Task");
    }

    @Test
    public void testGetTaskByName_Negative() throws SQLException {
        when(taskDAO.getTaskByName("Nonexistent Task")).thenReturn(null);

        Task result = taskService.getTaskByName("Nonexistent Task");

        assertNull(result);
        verify(taskDAO, times(1)).getTaskByName("Nonexistent Task");
    }


    @Test
    public void testGetTasksByProjectId_Positive() {
        List<Task> tasks = Arrays.asList(new Task(), new Task());
        when(taskDAO.getTasksByProjectId(1)).thenReturn(tasks);

        List<Task> result = taskService.getTasksByProjectId(1);

        assertEquals(tasks, result);
        verify(taskDAO, times(1)).getTasksByProjectId(1);
    }

    @Test
    public void testGetTasksByProjectId_Negative() {
        when(taskDAO.getTasksByProjectId(1)).thenReturn(null);

        List<Task> result = taskService.getTasksByProjectId(1);

        assertNull(result);
        verify(taskDAO, times(1)).getTasksByProjectId(1);
    }

    @Test
    public void testGetTasksByUserId_Positive() throws SQLException {
        List<Task> tasks = Arrays.asList(new Task(), new Task());
        when(taskDAO.getTasksByUserId(1)).thenReturn(tasks);

        List<Task> result = taskService.getTasksByUserId(1);

        assertEquals(tasks, result);
        verify(taskDAO, times(1)).getTasksByUserId(1);
    }

    @Test
    public void testGetTasksByUserId_Negative() throws SQLException {
        when(taskDAO.getTasksByUserId(1)).thenReturn(null);

        List<Task> result = taskService.getTasksByUserId(1);

        assertNull(result);
        verify(taskDAO, times(1)).getTasksByUserId(1);
    }

    @Test
    public void testGetTasksByUserId_Exception() throws SQLException {
        when(taskDAO.getTasksByUserId(1)).thenThrow(new SQLException("Database error"));

        SQLException exception = assertThrows(SQLException.class, () -> {
            taskService.getTasksByUserId(1);
        });

        assertEquals("Database error", exception.getMessage());
        verify(taskDAO, times(1)).getTasksByUserId(1);
    }
}