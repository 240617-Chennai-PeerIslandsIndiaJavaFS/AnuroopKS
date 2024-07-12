package org.example.dao;

import org.example.connection.DBConnection;
import org.example.models.Timestamps;


import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class TimestampsDAO {

    public void addTimestamp(Timestamps timestamp) throws SQLException {
        String query = "INSERT INTO timestamps (timestamp_id, task_id, time) VALUES (?, ?, ?)";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setInt(1, timestamp.getTimestampId());
            stmt.setInt(2, timestamp.getTaskId());
            stmt.setTimestamp(3, timestamp.getTime());
            stmt.executeUpdate();
        }
    }

    public Timestamps getTimestamp(int id) throws SQLException {
        String query = "SELECT * FROM timestamps WHERE timestamp_id = ?";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setInt(1, id);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                Timestamps timestamp = new Timestamps();
                timestamp.setTimestampId(rs.getInt("timestamp_id"));
                timestamp.setTaskId(rs.getInt("task_id"));
                timestamp.setTime(rs.getTimestamp("time"));
                return timestamp;
            }
        }
        return null;
    }

    public List<Timestamps> getAllTimestamps() throws SQLException {
        String query = "SELECT * FROM timestamps";
        List<Timestamps> timestampsList = new ArrayList<>();
        try (Connection conn = DBConnection.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(query)) {
            while (rs.next()) {
                Timestamps timestamp = new Timestamps();
                timestamp.setTimestampId(rs.getInt("timestamp_id"));
                timestamp.setTaskId(rs.getInt("task_id"));
                timestamp.setTime(rs.getTimestamp("time"));
                timestampsList.add(timestamp);
            }
        }
        return timestampsList;
    }

    public void updateTimestamp(Timestamps timestamp) throws SQLException {
        String query = "UPDATE timestamps SET task_id = ?, time = ? WHERE timestamp_id = ?";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setInt(1, timestamp.getTaskId());
            stmt.setTimestamp(2, timestamp.getTime());
            stmt.setInt(3, timestamp.getTimestampId());
            stmt.executeUpdate();
        }
    }

    public void deleteTimestamp(int id) throws SQLException {
        String query = "DELETE FROM timestamps WHERE timestamp_id = ?";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setInt(1, id);
            stmt.executeUpdate();
        }
    }
    public List<Timestamps> getTimestampsByTaskId(int taskId) throws SQLException {
        String query = "SELECT * FROM timestamps WHERE task_id = ?";
        List<Timestamps> timestampsList = new ArrayList<>();
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setInt(1, taskId);
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                Timestamps timestamp = new Timestamps();
                timestamp.setTimestampId(rs.getInt("timestamp_id"));
                timestamp.setTaskId(rs.getInt("task_id"));
                timestamp.setTime(rs.getTimestamp("time"));
                timestampsList.add(timestamp);
            }
        }
        return timestampsList;
    }
}