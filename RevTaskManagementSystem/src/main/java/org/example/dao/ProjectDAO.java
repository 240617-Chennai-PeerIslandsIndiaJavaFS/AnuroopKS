package org.example.dao;

import org.example.models.Project;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ProjectDAO {
    private Connection connection;

    public ProjectDAO(Connection connection) {
        this.connection = connection;
    }

    public void addProject(Project project) throws SQLException {
        String sql = "INSERT INTO Projects (project_name, client_id, team_id, start_date, deadline, project_description, project_status) VALUES (?, ?, ?, ?, ?, ?, ?)";
        try (PreparedStatement statement = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            statement.setString(1, project.getProjectName());
            statement.setInt(2, project.getClientId());
            statement.setInt(3, project.getTeamId());
            statement.setDate(4, project.getStartDate());
            statement.setDate(5, project.getDeadline());
            statement.setString(6, project.getProjectDescription());
            statement.setString(7, project.getProjectStatus().toString());

            int affectedRows = statement.executeUpdate();
            if (affectedRows > 0) {
                try (ResultSet generatedKeys = statement.getGeneratedKeys()) {
                    if (generatedKeys.next()) {
                        project.setProjectId(generatedKeys.getInt(1));
                    }
                }
            }
        }
    }

    public Project getProject(int projectId) throws SQLException {
        String sql = "SELECT * FROM Projects WHERE project_id = ?";
        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setInt(1, projectId);
            try (ResultSet resultSet = statement.executeQuery()) {
                if (resultSet.next()) {
                    return mapRowToProject(resultSet);
                }
            }
        }
        return null;
    }

    public List<Project> getAllProjects() throws SQLException {
        List<Project> projects = new ArrayList<>();
        String sql = "SELECT * FROM Projects";
        try (Statement statement = connection.createStatement();
             ResultSet resultSet = statement.executeQuery(sql)) {
            while (resultSet.next()) {
                projects.add(mapRowToProject(resultSet));
            }
        }
        return projects;
    }

    public void updateProject(Project project) throws SQLException {
        String sql = "UPDATE Projects SET project_name = ?, client_id = ?, team_id = ?, start_date = ?, deadline = ?, project_description = ?, project_status = ? WHERE project_id = ?";
        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setString(1, project.getProjectName());
            statement.setInt(2, project.getClientId());
            statement.setInt(3, project.getTeamId());
            statement.setDate(4, project.getStartDate());
            statement.setDate(5, project.getDeadline());
            statement.setString(6, project.getProjectDescription());
            statement.setString(7, project.getProjectStatus().toString());
            statement.setInt(8, project.getProjectId());

            statement.executeUpdate();
        }
    }

    private Project mapRowToProject(ResultSet resultSet) throws SQLException {
        Project project = new Project();
        project.setProjectId(resultSet.getInt("project_id"));
        project.setProjectName(resultSet.getString("project_name"));
        project.setClientId(resultSet.getInt("client_id"));
        project.setTeamId(resultSet.getInt("team_id"));
        project.setStartDate(resultSet.getDate("start_date"));
        project.setDeadline(resultSet.getDate("deadline"));
        project.setProjectDescription(resultSet.getString("project_description"));
        project.setProjectStatus(Project.ProjectStatus.fromString(resultSet.getString("project_status")));
        return project;
    }
    public Project getProjectByName(String projectName) {
        String query = "SELECT * FROM projects WHERE project_name = ?";
        try (PreparedStatement stmt = connection.prepareStatement(query)) {
            stmt.setString(1, projectName);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                Project project = new Project();
                project.setProjectName(rs.getString("project_name"));
                project.setProjectDescription(rs.getString("project_description"));
                project.setStartDate(rs.getDate("start_date"));
                project.setDeadline(rs.getDate("deadline"));
                project.setClientId(rs.getInt("client_id"));
                project.setTeamId(rs.getInt("team_id"));
                project.setProjectStatus(Project.ProjectStatus.valueOf(rs.getString("project_status")));
                return project;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null; // Return null if no project is found
    }
    public List<Project> getProjectsByTeamMember(int memberId) throws SQLException {
        List<Project> projects = new ArrayList<>();
        String query = "SELECT p.* FROM Projects p " +
                "JOIN ProjectTeamMembers ptm ON p.team_id = ptm.team_id " +
                "WHERE ptm.member_id = ?";
        try (PreparedStatement stmt = connection.prepareStatement(query)) {

            stmt.setInt(1, memberId);
            ResultSet resultSet = stmt.executeQuery();
            while (resultSet.next()) {
                Project project = new Project();
                project.setProjectId(resultSet.getInt("project_id"));
                project.setProjectName(resultSet.getString("project_name"));
                project.setClientId(resultSet.getInt("client_id"));
                project.setTeamId(resultSet.getInt("team_id"));
                project.setStartDate(resultSet.getDate("start_date"));
                project.setDeadline(resultSet.getDate("deadline"));
                project.setProjectDescription(resultSet.getString("project_description"));
                project.setProjectStatus(Project.ProjectStatus.valueOf(resultSet.getString("project_status")));
                projects.add(project);
            }
        }
        return projects;
    }
    public int getProjectIdByName(String projectName) throws SQLException {
        String query = "SELECT project_id FROM projects WHERE project_name = ?";
        try (PreparedStatement stmt = connection.prepareStatement(query)) {
            stmt.setString(1, projectName);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    return rs.getInt("project_id");
                }
            }
        }
        return -1;
    }
}