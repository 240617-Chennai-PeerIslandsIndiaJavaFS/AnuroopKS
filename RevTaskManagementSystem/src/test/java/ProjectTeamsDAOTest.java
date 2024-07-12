import org.example.connection.DBConnection;
import org.example.dao.ProjectTeamsDAO;
import org.example.models.ProjectTeams;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.sql.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class ProjectTeamsDAOTest {

    @Mock
    private Connection connection;

    @Mock
    private PreparedStatement preparedStatement;

    @Mock
    private Statement statement;

    @Mock
    private ResultSet resultSet;

    @InjectMocks
    private ProjectTeamsDAO projectTeamsDAO;

    @BeforeEach
    public void setUp() throws SQLException {
        when(connection.prepareStatement(anyString())).thenReturn(preparedStatement);
    }

    // Positive Test Cases

    @Test
    public void testAddProjectTeam() throws SQLException {
        ProjectTeams projectTeam = new ProjectTeams();
        projectTeam.setTeamName("Team A");

        projectTeamsDAO.addProjectTeam(projectTeam);

        verify(preparedStatement, times(1)).setString(1, "Team A");
        verify(preparedStatement, times(1)).executeUpdate();
    }

    @Test
    public void testGetProjectTeam() throws SQLException {
        when(preparedStatement.executeQuery()).thenReturn(resultSet);
        when(resultSet.next()).thenReturn(true);
        when(resultSet.getInt("team_id")).thenReturn(1);
        when(resultSet.getString("team_name")).thenReturn("Team A");

        ProjectTeams projectTeam = projectTeamsDAO.getProjectTeam("Team A");

        assertNotNull(projectTeam);
        assertEquals(1, projectTeam.getTeamId());
        assertEquals("Team A", projectTeam.getTeamName());
    }

    @Test
    public void testUpdateProjectTeam() throws SQLException {
        ProjectTeams updatedProjectTeam = new ProjectTeams();
        updatedProjectTeam.setTeamName("Team B");

        projectTeamsDAO.updateProjectTeam("Team A", updatedProjectTeam);

        verify(preparedStatement, times(1)).setString(1, "Team B");
        verify(preparedStatement, times(1)).setString(2, "Team A");
        verify(preparedStatement, times(1)).executeUpdate();
    }

    @Test
    public void testDeleteProjectTeam() throws SQLException {
        projectTeamsDAO.deleteProjectTeam("Team A");

        verify(preparedStatement, times(1)).setString(1, "Team A");
        verify(preparedStatement, times(1)).executeUpdate();
    }


    @Test
    public void testGetProjectTeam_NonExistentTeam() throws SQLException {
        when(preparedStatement.executeQuery()).thenReturn(resultSet);
        when(resultSet.next()).thenReturn(false);

        ProjectTeams projectTeam = projectTeamsDAO.getProjectTeam("NonExistentTeam");

        assertNull(projectTeam);
    }



}