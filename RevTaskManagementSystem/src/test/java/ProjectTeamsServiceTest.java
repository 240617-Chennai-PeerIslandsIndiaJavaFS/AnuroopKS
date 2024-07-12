import org.example.models.ProjectTeams;
import org.example.service.ProjectTeamsService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class ProjectTeamsServiceTest {

    private ProjectTeamsService projectTeamsService;

    @BeforeEach
    public void setUp() {
        projectTeamsService = new ProjectTeamsService();
    }

    @Test
    public void testAddProjectTeam() {
        ProjectTeams projectTeam = new ProjectTeams();
        projectTeam.setTeamName("Team A");

        projectTeamsService.addProjectTeam(projectTeam);

        ProjectTeams retrievedTeam = projectTeamsService.getProjectTeam("Team A");
        assertNotNull(retrievedTeam);
        assertEquals("Team A", retrievedTeam.getTeamName());
    }

    @Test
    public void testGetAllProjectTeams() {
        List<ProjectTeams> teams = projectTeamsService.getAllProjectTeams();
        assertNotNull(teams);
    }

    @Test
    public void testUpdateProjectTeam() {
        ProjectTeams projectTeam = new ProjectTeams();
        projectTeam.setTeamName("Team B");

        projectTeamsService.addProjectTeam(projectTeam);

        ProjectTeams updatedProjectTeam = new ProjectTeams();
        updatedProjectTeam.setTeamName("Team B Updated");

        projectTeamsService.updateProjectTeam("Team B", updatedProjectTeam);

        ProjectTeams retrievedTeam = projectTeamsService.getProjectTeam("Team B Updated");
        assertNotNull(retrievedTeam);
        assertEquals("Team B Updated", retrievedTeam.getTeamName());
    }

    @Test
    public void testDeleteProjectTeam() {
        ProjectTeams projectTeam = new ProjectTeams();
        projectTeam.setTeamName("Team C");

        projectTeamsService.addProjectTeam(projectTeam);

        projectTeamsService.deleteProjectTeam("Team C");

        ProjectTeams retrievedTeam = projectTeamsService.getProjectTeam("Team C");
        assertNull(retrievedTeam);
    }


    @Test
    public void testGetNonExistentProjectTeam() {
        ProjectTeams retrievedTeam = projectTeamsService.getProjectTeam("NonExistentTeam");
        assertNull(retrievedTeam);
    }

}