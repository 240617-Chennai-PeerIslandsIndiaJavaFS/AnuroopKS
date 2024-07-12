

import org.example.dao.ProjectDAO;
import org.example.models.Project;
import org.example.models.Project.ProjectStatus;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.sql.*;


import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class ProjectDAOTest {

    private Connection connection;
    private ProjectDAO projectDAO;

    @BeforeEach
    void setUp() throws SQLException {
        connection = mock(Connection.class);
        projectDAO = new ProjectDAO(connection);
    }

    @Test
    void testAddProject() throws SQLException {
        Project project = new Project();
        project.setProjectName("Test Project");
        project.setClientId(1);
        project.setTeamId(1);
        project.setStartDate(Date.valueOf("2023-01-01"));
        project.setDeadline(Date.valueOf("2023-12-31"));
        project.setProjectDescription("Test Description");
        project.setProjectStatus(ProjectStatus.In_Progress);

        PreparedStatement statement = mock(PreparedStatement.class);
        when(connection.prepareStatement(anyString(), anyInt())).thenReturn(statement);
        when(statement.executeUpdate()).thenReturn(1);
        ResultSet resultSet = mock(ResultSet.class);
        when(statement.getGeneratedKeys()).thenReturn(resultSet);
        when(resultSet.next()).thenReturn(true);
        when(resultSet.getInt(1)).thenReturn(1);

        projectDAO.addProject(project);

        verify(statement, times(1)).setString(1, "Test Project");
        verify(statement, times(1)).setInt(2, 1);
        verify(statement, times(1)).setInt(3, 1);
        verify(statement, times(1)).setDate(4, Date.valueOf("2023-01-01"));
        verify(statement, times(1)).setDate(5, Date.valueOf("2023-12-31"));
        verify(statement, times(1)).setString(6, "Test Description");
        verify(statement, times(1)).setString(7, "In_Progress");
        verify(statement, times(1)).executeUpdate();
        verify(resultSet, times(1)).next();
        verify(resultSet, times(1)).getInt(1);

        assertEquals(1, project.getProjectId());
    }


    @Test
    void testUpdateProject() throws SQLException {
        Project project = new Project();
        project.setProjectId(1);
        project.setProjectName("Updated Project");
        project.setClientId(2);
        project.setTeamId(2);
        project.setStartDate(Date.valueOf("2023-02-01"));
        project.setDeadline(Date.valueOf("2023-11-30"));
        project.setProjectDescription("Updated Description");
        project.setProjectStatus(ProjectStatus.Assigned);

        String sql = "UPDATE Projects SET project_name = ?, client_id = ?, team_id = ?, start_date = ?, deadline = ?, project_description = ?, project_status = ? WHERE project_id = ?";
        PreparedStatement statement = mock(PreparedStatement.class);
        when(connection.prepareStatement(sql)).thenReturn(statement);

        projectDAO.updateProject(project);

        verify(statement, times(1)).setString(1, "Updated Project");
        verify(statement, times(1)).setInt(2, 2);
        verify(statement, times(1)).setInt(3, 2);
        verify(statement, times(1)).setDate(4, Date.valueOf("2023-02-01"));
        verify(statement, times(1)).setDate(5, Date.valueOf("2023-11-30"));
        verify(statement, times(1)).setString(6, "Updated Description");
        verify(statement, times(1)).setString(7, "Assigned");
        verify(statement, times(1)).setInt(8, 1);
        verify(statement, times(1)).executeUpdate();
    }
}