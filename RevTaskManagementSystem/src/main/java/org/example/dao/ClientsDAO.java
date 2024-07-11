package org.example.dao;

import org.example.connection.DBConnection;
import org.example.models.Clients;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class ClientsDAO {
    private Connection connection;


    public ClientsDAO() {
        this.connection = DBConnection.getConnection();
    }

    public void addClient(Clients client) throws SQLException {
        String query = "INSERT INTO Clients (client_name, client_phone, client_email) VALUES (?, ?, ?)";
        try (PreparedStatement stmt = connection.prepareStatement(query)) {
            stmt.setString(1, client.getClient_name());
            stmt.setString(2, client.getClient_phone());
            stmt.setString(3, client.getClient_email());
            stmt.executeUpdate();
        }
    }

    public Clients getClientByName(String clientName) throws SQLException {
        String query = "SELECT * FROM Clients WHERE client_name = ?";
        try (PreparedStatement stmt = connection.prepareStatement(query)) {
            stmt.setString(1, clientName);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                Clients client = new Clients();
                client.setClient_id(rs.getInt("client_id"));
                client.setClient_name(rs.getString("client_name"));
                client.setClient_phone(rs.getString("client_phone"));
                client.setClient_email(rs.getString("client_email"));
                return client;
            }
        }
        return null;
    }

    public List<Clients> getAllClients() throws SQLException {
        List<Clients> clientsList = new ArrayList<>();
        String query = "SELECT * FROM Clients";
        try (PreparedStatement stmt = connection.prepareStatement(query)) {
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                Clients client = new Clients();
                client.setClient_id(rs.getInt("client_id"));
                client.setClient_name(rs.getString("client_name"));
                client.setClient_phone(rs.getString("client_phone"));
                client.setClient_email(rs.getString("client_email"));
                clientsList.add(client);
            }
        }
        return clientsList;
    }

    public void updateClient(Clients client) throws SQLException {
        String query = "UPDATE Clients SET client_name = ?, client_phone = ?, client_email = ? WHERE client_id = ?";
        try (PreparedStatement stmt = connection.prepareStatement(query)) {
            stmt.setString(1, client.getClient_name());
            stmt.setString(2, client.getClient_phone());
            stmt.setString(3, client.getClient_email());
            stmt.setInt(4, client.getClient_id());
            stmt.executeUpdate();
        }
    }

    public void deleteClient(String clientName) throws SQLException {
        String query = "DELETE FROM Clients WHERE client_name = ?";
        try (PreparedStatement stmt = connection.prepareStatement(query)) {
            stmt.setString(1, clientName);
            stmt.executeUpdate();
        }
    }

    public Clients getClientById(int clientId) throws SQLException {
        String query = "SELECT * FROM Clients WHERE client_id = ?";
        try (PreparedStatement stmt = connection.prepareStatement(query)) {
            stmt.setInt(1, clientId);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                Clients client = new Clients();
                client.setClient_id(rs.getInt("client_id"));
                client.setClient_name(rs.getString("client_name"));
                client.setClient_phone(rs.getString("client_phone"));
                client.setClient_email(rs.getString("client_email"));
                return client;
            }
        }
        return null;
    }
}