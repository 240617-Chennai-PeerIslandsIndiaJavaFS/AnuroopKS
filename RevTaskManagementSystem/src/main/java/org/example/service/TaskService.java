package org.example.service;

import org.example.dao.TaskDAO;
import org.example.models.Task;
import org.example.models.Timestamps;

import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.Date;
import java.util.List;

public class TaskService {
    private TaskDAO taskDAO;

    public TaskService() {
        this.taskDAO = new TaskDAO();
    }

    public boolean addTask(Task task) throws SQLException {
        return taskDAO.addTask(task);
    }

    public Task getTaskByName(String taskName) throws SQLException {
        return taskDAO.getTaskByName(taskName);
    }

    public List<Task> getTasksByProjectId(int projectId) {
        return taskDAO.getTasksByProjectId(projectId);
    }

    public List<Task> getTasksByUserId(int userId) throws SQLException {
        return taskDAO.getTasksByUserId(userId);
    }
    public void updateTaskStatus(int taskId, String status) {
        try {
            TaskDAO.updateTaskStatus(taskId, status);

            Timestamps timestamp = new Timestamps();
            timestamp.setTaskId(taskId);
            timestamp.setTime(new Timestamp(new Date().getTime()));
            TimestampsService.addTimestamp(timestamp);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}