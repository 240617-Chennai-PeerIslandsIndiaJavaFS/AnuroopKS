package org.example.dao;

import org.example.connection.DBConnection;
import org.example.models.Task;
import org.example.models.Timestamps;
import org.example.service.TimestampsService;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

import static org.example.dao.UserDAO.connection;

public class TaskDAO {

    public boolean addTask(Task task) {
        String query = "INSERT INTO tasks (task_name, task_description, task_start_date, task_end_date, project_id, assigned_to, task_status) VALUES (?, ?, ?, ?, ?, ?, ?)";
        try (Connection connection = DBConnection.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(query)) {

            preparedStatement.setString(1, task.getTaskName());
            preparedStatement.setString(2, task.getTaskDescription());
            preparedStatement.setDate(3, task.getTaskStartDate());
            preparedStatement.setDate(4, task.getTaskEndDate());
            preparedStatement.setInt(5, task.getProjectId());
            preparedStatement.setInt(6, task.getAssignedTo());
            preparedStatement.setString(7, task.getTaskStatus().name()); // Convert enum to string

            int rowsAffected = preparedStatement.executeUpdate();
            return rowsAffected > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }
    public List<Task> getTasksByProjectId(int projectId) {
        List<Task> tasks = new ArrayList<>();
        String query = "SELECT * FROM tasks WHERE project_id = ?";
        try (Connection connection = DBConnection.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(query)) {

            preparedStatement.setInt(1, projectId);
            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                while (resultSet.next()) {
                    Task task = mapResultSetToTask(resultSet);
                    tasks.add(task);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return tasks;
    }
    private Task mapResultSetToTask(ResultSet resultSet) throws SQLException {
        Task task = new Task();
        task.setTaskId(resultSet.getInt("task_id"));
        task.setTaskName(resultSet.getString("task_name"));
        task.setTaskDescription(resultSet.getString("task_description"));
        task.setTaskStartDate(resultSet.getDate("task_start_date"));
        task.setTaskEndDate(resultSet.getDate("task_end_date"));
        task.setProjectId(resultSet.getInt("project_id"));
        task.setAssignedTo(resultSet.getInt("assigned_to"));
        task.setTaskStatus(Task.TaskStatus.valueOf(resultSet.getString("task_status")));
        return task;
    }
    public Task getTaskById(int taskId) {
        String query = "SELECT * FROM tasks WHERE task_id = ?";
        try (Connection connection = DBConnection.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(query)) {

            preparedStatement.setInt(1, taskId);
            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                if (resultSet.next()) {
                    return mapResultSetToTask(resultSet);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public Task getTaskByName(String taskName) {
        String query = "SELECT * FROM tasks WHERE task_name = ?";
        try (Connection connection = DBConnection.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(query)) {

            preparedStatement.setString(1, taskName);
            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                if (resultSet.next()) {
                    return mapResultSetToTask(resultSet);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public boolean updateTask(Task task) {
        String query = "UPDATE tasks SET task_name = ?, task_description = ?, task_start_date = ?, task_end_date = ?, project_id = ?, assigned_to = ?, task_status = ? WHERE task_id = ?";
        try (Connection connection = DBConnection.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(query)) {

            preparedStatement.setString(1, task.getTaskName());
            preparedStatement.setString(2, task.getTaskDescription());
            preparedStatement.setDate(3, task.getTaskStartDate());
            preparedStatement.setDate(4, task.getTaskEndDate());
            preparedStatement.setInt(5, task.getProjectId());
            preparedStatement.setInt(6, task.getAssignedTo());
            preparedStatement.setString(7, task.getTaskStatus().name()); // Convert enum to string
            preparedStatement.setInt(8, task.getTaskId());

            int rowsAffected = preparedStatement.executeUpdate();
            return rowsAffected > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }


    public List<Task> getTasksByUserId(int userId) throws SQLException {
        List<Task> tasks = new ArrayList<>();
        String query = "SELECT * FROM tasks WHERE assigned_to = ?";
        try (PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setInt(1, userId);
            try (ResultSet resultSet = statement.executeQuery()) {
                while (resultSet.next()) {
                    Task task = new Task();
                    task.setTaskId(resultSet.getInt("task_id"));
                    task.setTaskName(resultSet.getString("task_name"));
                    task.setTaskDescription(resultSet.getString("task_description"));
                    task.setTaskStartDate(resultSet.getDate("task_start_date"));
                    task.setTaskEndDate(resultSet.getDate("task_end_date"));
                    task.setTaskStatus(Task.TaskStatus.valueOf(resultSet.getString("task_status")));
                    task.setProjectId(resultSet.getInt("project_id"));
                    task.setAssignedTo(resultSet.getInt("assigned_to"));
                    tasks.add(task);
                }
            }
        }
        return tasks;
    }
    public static void updateTaskStatus(int taskId, String status) throws SQLException {
        String query = "UPDATE tasks SET task_status = ? WHERE task_id = ?";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setString(1, status);
            stmt.setInt(2, taskId);
            stmt.executeUpdate();
        }
    }
    public List<Timestamps> getTaskHistory(int taskId) {
        return TimestampsService.getTimestampsByTaskId(taskId);
    }
}