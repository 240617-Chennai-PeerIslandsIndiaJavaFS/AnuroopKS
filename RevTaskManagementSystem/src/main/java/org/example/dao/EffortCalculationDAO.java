package org.example.dao;

import org.example.connection.DBConnection;
import org.example.models.EffortCalculation;

import java.sql.*;

public class EffortCalculationDAO {

    private DBConnection dbConnection;
    private Connection connection;

    public EffortCalculationDAO() throws SQLException {
        this.connection = dbConnection.getConnection();
    }


    public void updateEffortCalculation(EffortCalculation effortCalculation) throws SQLException {
        String updateQuery = "UPDATE EffortCalculation SET project_end_date = ?, actual_effort = ? WHERE project_id = ?";
        try (PreparedStatement preparedStatement = connection.prepareStatement(updateQuery)) {
            preparedStatement.setDate(1, effortCalculation.getProjectEndDate());
            preparedStatement.setInt(2, effortCalculation.getActualEffort());
            preparedStatement.setInt(3, effortCalculation.getProjectId());

            int rowsAffected = preparedStatement.executeUpdate();
            if (rowsAffected > 0) {
                System.out.println("Actual Effort updated successfully.");
            }
        } catch (SQLException e) {
            System.out.println("Failed to update effort calculation: " + e.getMessage());
            throw e;
        }
    }

    public void addEffortCalculation(EffortCalculation effortCalculation) throws SQLException {
        String insertQuery = "INSERT INTO EffortCalculation (project_end_date, actual_effort, project_id) VALUES (?, ?, ?)";
        try (PreparedStatement preparedStatement = connection.prepareStatement(insertQuery)) {
            preparedStatement.setDate(1, effortCalculation.getProjectEndDate());
            preparedStatement.setInt(2, effortCalculation.getActualEffort());
            preparedStatement.setInt(3, effortCalculation.getProjectId());

            int rowsAffected = preparedStatement.executeUpdate();
            if (rowsAffected > 0) {
                System.out.println("Effort calculation added successfully.");
            } else {
                System.out.println("Failed to add effort calculation.");
            }
        } catch (SQLException e) {
            System.out.println("Failed to add effort calculation: " + e.getMessage());
            throw e;
        }
    }

    public EffortCalculation getEffortCalculationByProjectId(int projectId) throws SQLException {
        String selectQuery = "SELECT * FROM EffortCalculation WHERE project_id = ?";
        try (PreparedStatement preparedStatement = connection.prepareStatement(selectQuery)) {
            preparedStatement.setInt(1, projectId);
            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                if (resultSet.next()) {
                    EffortCalculation effortCalculation = new EffortCalculation();
                    effortCalculation.setProjectId(resultSet.getInt("project_id"));
                    effortCalculation.setProjectEndDate(resultSet.getDate("project_end_date"));
                    effortCalculation.setActualEffort(resultSet.getInt("actual_effort"));
                    return effortCalculation;
                } else {
                    return null;
                }
            }
        } catch (SQLException e) {
            System.out.println("Failed to retrieve effort calculation: " + e.getMessage());
            throw e;
        }
    }

}