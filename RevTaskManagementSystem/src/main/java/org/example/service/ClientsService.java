package org.example.service;

import org.example.dao.ClientsDAO;
import org.example.models.Clients;

import java.sql.SQLException;
import java.util.List;

public class ClientsService {
    private ClientsDAO clientsDAO;

    public ClientsService() {
        this.clientsDAO = new ClientsDAO();
    }

    public void addClient(Clients client) throws SQLException {
        clientsDAO.addClient(client);
    }

    public Clients getClientById(int clientId) throws SQLException {
        return clientsDAO.getClientById(clientId);
    }

    public Clients getClientByName(String clientName) throws SQLException {
        return clientsDAO.getClientByName(clientName);
    }

    public List<Clients> getAllClients() throws SQLException {
        return clientsDAO.getAllClients();
    }

    public void updateClient(Clients client) throws SQLException {
        clientsDAO.updateClient(client);
    }

    public void deleteClient(String clientName) throws SQLException {
        clientsDAO.deleteClient(clientName);
    }
}