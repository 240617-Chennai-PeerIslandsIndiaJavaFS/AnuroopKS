package org.example.service;

import org.example.dao.TaskDAO;
import org.example.models.Task;

import java.sql.SQLException;
import java.util.List;

public class TaskService {
    private TaskDAO taskDAO;

    public TaskService() {
        this.taskDAO = new TaskDAO();
    }

    public boolean addTask(Task task) throws SQLException {
        return taskDAO.addTask(task);
    }

    public Task getTaskById(int taskId) throws SQLException {
        return taskDAO.getTaskById(taskId);
    }

    public Task getTaskByName(String taskName) throws SQLException {
        return taskDAO.getTaskByName(taskName);
    }

    public boolean updateTask(Task task) throws SQLException {
        return taskDAO.updateTask(task);
    }

    public List<Task> getTasksByProjectId(int projectId) throws SQLException {
        return taskDAO.getTasksByProjectId(projectId);
    }

    public List<Task> getTasksByUserId(int userId) throws SQLException {
        return taskDAO.getTasksByUserId(userId);
    }
}