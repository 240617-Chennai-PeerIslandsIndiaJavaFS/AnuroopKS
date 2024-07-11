

import org.example.dao.ProjectDAO;
import org.example.models.Project;
import org.example.service.ProjectService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.sql.SQLException;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class ProjectServiceTest {

    @Mock
    private ProjectDAO projectDAO;

    @InjectMocks
    private ProjectService projectService;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testAddProject() throws SQLException {
        Project project = new Project();
        projectService.addProject(project);
        verify(projectDAO, times(1)).addProject(project);
    }

    @Test
    public void testGetProjectById() throws SQLException {
        Project mockProject = new Project();
        when(projectDAO.getProject(1)).thenReturn(mockProject);

        Project project = projectService.getProjectById(1);
        assertNotNull(project);
        verify(projectDAO, times(1)).getProject(1);
    }

    @Test
    public void testGetAllProjects() throws SQLException {
        List<Project> mockProjects = Arrays.asList(new Project(), new Project());
        when(projectDAO.getAllProjects()).thenReturn(mockProjects);

        List<Project> projects = projectService.getAllProjects();
        assertEquals(2, projects.size());
        verify(projectDAO, times(1)).getAllProjects();
    }

    @Test
    public void testUpdateProject() throws SQLException {
        Project project = new Project();
        projectService.updateProject(project);
        verify(projectDAO, times(1)).updateProject(project);
    }

    @Test
    public void testGetProjectByName() throws SQLException {
        Project mockProject = new Project();
        when(projectDAO.getProjectByName("testProject")).thenReturn(mockProject);

        Project project = projectService.getProjectByName("testProject");
        assertNotNull(project);
        verify(projectDAO, times(1)).getProjectByName("testProject");
    }

    @Test
    public void testGetProjectsByTeamMember() throws SQLException {
        List<Project> mockProjects = Arrays.asList(new Project(), new Project());
        when(projectDAO.getProjectsByTeamMember(1)).thenReturn(mockProjects);

        List<Project> projects = projectService.getProjectsByTeamMember(1);
        assertEquals(2, projects.size());
        verify(projectDAO, times(1)).getProjectsByTeamMember(1);
    }
}