package org.example.dao;

import org.example.connection.DBConnection;
import org.example.models.ProjectTeams;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ProjectTeamsDAO {
    private Connection connection;

    public ProjectTeamsDAO() {
        connection = DBConnection.getConnection();
    }

    public void addProjectTeam(ProjectTeams projectTeam) {
        try {
            String query = "INSERT INTO ProjectTeams (team_name) VALUES (?)";
            PreparedStatement pstmt = connection.prepareStatement(query);
            pstmt.setString(1, projectTeam.getTeamName());
            pstmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public ProjectTeams getProjectTeam(String teamName) {
        ProjectTeams projectTeam = null;
        try {
            String query = "SELECT * FROM ProjectTeams WHERE team_name = ?";
            PreparedStatement pstmt = connection.prepareStatement(query);
            pstmt.setString(1, teamName);
            ResultSet rs = pstmt.executeQuery();
            if (rs.next()) {
                projectTeam = new ProjectTeams();
                projectTeam.setTeamId(rs.getInt("team_id"));
                projectTeam.setTeamName(rs.getString("team_name"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return projectTeam;
    }

    public List<ProjectTeams> getAllProjectTeams() {
        List<ProjectTeams> projectTeams = new ArrayList<>();
        try {
            String query = "SELECT * FROM ProjectTeams";
            Statement stmt = connection.createStatement();
            ResultSet rs = stmt.executeQuery(query);
            while (rs.next()) {
                ProjectTeams projectTeam = new ProjectTeams();
                projectTeam.setTeamId(rs.getInt("team_id"));
                projectTeam.setTeamName(rs.getString("team_name"));
                projectTeams.add(projectTeam);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return projectTeams;
    }

    public void updateProjectTeam(String currentTeamName, ProjectTeams updatedProjectTeam) {
        try {
            String query = "UPDATE ProjectTeams SET team_name = ? WHERE team_name = ?";
            PreparedStatement pstmt = connection.prepareStatement(query);
            pstmt.setString(1, updatedProjectTeam.getTeamName());
            pstmt.setString(2, currentTeamName);
            pstmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }


    public void deleteProjectTeam(String teamName) {
        try {
            String query = "DELETE FROM ProjectTeams WHERE team_name = ?";
            PreparedStatement pstmt = connection.prepareStatement(query);
            pstmt.setString(1, teamName);
            pstmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
