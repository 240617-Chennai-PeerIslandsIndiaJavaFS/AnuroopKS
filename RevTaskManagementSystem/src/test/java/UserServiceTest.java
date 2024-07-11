import org.example.connection.DBConnection;
import org.example.dao.UserDAO;
import org.example.models.UserModels;

import org.example.service.UserService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.sql.Connection;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class UserServiceTest {

    @Mock
    private UserDAO userDao;

    @Mock
    private Connection connection;

    @InjectMocks
    private UserService userService;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
        mockStatic(DBConnection.class);
        when(DBConnection.getConnection()).thenReturn(connection);
    }

    @Test
    public void testGetAllUsers() throws Exception {
        List<UserModels.User> mockUsers = Arrays.asList(new UserModels.User(), new UserModels.User());
        when(userDao.fetchUsers()).thenReturn(mockUsers);

        List<UserModels.User> users = userService.getAllUsers();
        assertEquals(2, users.size());
        verify(userDao, times(1)).fetchUsers();
    }

}