package org.example.dao;

import org.example.connection.DBConnection;
import org.example.models.Milestone;


import java.sql.*;


public class MilestoneDAO {
    private final Connection connection;

    public MilestoneDAO(Connection connection) {
        this.connection = connection;
    }
    public void addMilestone(Milestone milestone) throws SQLException {
        String sql = "INSERT INTO milestones (milestone_id, milestone_name, milestone_description, project_id) VALUES (?, ?, ?, ?)";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setInt(1, milestone.getMilestoneId());
            stmt.setString(2, milestone.getMilestoneName().name());
            stmt.setString(3, milestone.getMilestoneDescription());
            stmt.setInt(4, milestone.getProjectId());
            stmt.executeUpdate();
        }
    }

    public int getProjectIdByName(String projectName) throws SQLException {
        String query = "SELECT project_id FROM Projects WHERE project_name = ?";
        try (Connection connection = DBConnection.getConnection();
             PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setString(1, projectName);
            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()) {
                return resultSet.getInt("project_id");
            } else {
                throw new SQLException("Project not found with name: " + projectName);
            }
        }
    }

}