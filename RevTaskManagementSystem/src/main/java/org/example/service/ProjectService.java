package org.example.service;

import org.example.dao.ProjectDAO;
import org.example.models.Project;

import java.sql.SQLException;
import java.util.List;

import static org.example.dao.UserDAO.connection;

public class ProjectService {
    private ProjectDAO projectDAO;

    public ProjectService() {
        this.projectDAO = new ProjectDAO(connection);
    }

    public void addProject(Project project) throws SQLException {
        projectDAO.addProject(project);
    }

    public Project getProjectById(int projectId) throws SQLException {
        return projectDAO.getProject(projectId);
    }

    public List<Project> getAllProjects() throws SQLException {
        return projectDAO.getAllProjects();
    }

    public void updateProject(Project project) throws SQLException {
        projectDAO.updateProject(project);
    }

    public Project getProjectByName(String projectName) throws SQLException {
        return projectDAO.getProjectByName(projectName);
    }

    public List<Project> getProjectsByTeamMember(int memberId) throws SQLException {
        return projectDAO.getProjectsByTeamMember(memberId);
    }

    public int getProjectIdByName(String projectName) throws SQLException {
        return projectDAO.getProjectIdByName(projectName);
    }
}