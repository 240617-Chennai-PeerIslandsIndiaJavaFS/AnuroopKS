package org.example.service;

import org.example.dao.MessageDAO;
import org.example.models.Message;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class MessageService {
    private final MessageDAO messageDAO;

    public MessageService() {
        this.messageDAO = new MessageDAO();
    }

    public boolean addMessage(Message message) {
        try {
            return messageDAO.insertMessage(message);
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }


    public List<Message> getMessagesById(int userId) throws SQLException {
        List<Message> messages = messageDAO.getMessagesById(userId);
        return messages != null ? messages : new ArrayList<>();
    }
}