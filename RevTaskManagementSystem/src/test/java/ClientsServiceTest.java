
import org.example.dao.ClientsDAO;
import org.example.models.Clients;
import org.example.service.ClientsService;
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

public class ClientsServiceTest {

    @Mock
    private ClientsDAO clientsDAO;

    @InjectMocks
    private ClientsService clientsService;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testAddClient() throws SQLException {
        Clients client = new Clients();
        clientsService.addClient(client);
        verify(clientsDAO, times(1)).addClient(client);
    }

    @Test
    public void testGetClientById() throws SQLException {
        Clients mockClient = new Clients();
        when(clientsDAO.getClientById(1)).thenReturn(mockClient);

        Clients client = clientsService.getClientById(1);
        assertNotNull(client);
        verify(clientsDAO, times(1)).getClientById(1);
    }

    @Test
    public void testGetClientByName() throws SQLException {
        Clients mockClient = new Clients();
        when(clientsDAO.getClientByName("testClient")).thenReturn(mockClient);

        Clients client = clientsService.getClientByName("testClient");
        assertNotNull(client);
        verify(clientsDAO, times(1)).getClientByName("testClient");
    }

    @Test
    public void testGetAllClients() throws SQLException {
        List<Clients> mockClients = Arrays.asList(new Clients(), new Clients());
        when(clientsDAO.getAllClients()).thenReturn(mockClients);

        List<Clients> clients = clientsService.getAllClients();
        assertEquals(2, clients.size());
        verify(clientsDAO, times(1)).getAllClients();
    }

    @Test
    public void testUpdateClient() throws SQLException {
        Clients client = new Clients();
        clientsService.updateClient(client);
        verify(clientsDAO, times(1)).updateClient(client);
    }

    @Test
    public void testDeleteClient() throws SQLException {
        clientsService.deleteClient("testClient");
        verify(clientsDAO, times(1)).deleteClient("testClient");
    }
}