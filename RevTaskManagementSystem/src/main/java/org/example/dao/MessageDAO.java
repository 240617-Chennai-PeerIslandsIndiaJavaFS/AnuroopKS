package org.example.dao;

import org.example.connection.DBConnection;
import org.example.models.Message;


import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class MessageDAO {

    public boolean insertMessage(Message message) throws SQLException {
        String sql = "INSERT INTO Messages (content, timestamp, receiver_id, sender_id) VALUES (?, ?, ?, ?)";
        try (Connection connection = DBConnection.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {

            statement.setString(1, message.getContent());
            statement.setTimestamp(2, new Timestamp(message.getTimestamp().getTime()));
            statement.setInt(3, message.getReceiverId());
            statement.setInt(4, message.getSenderId());

            return statement.executeUpdate() > 0;
        }
    }

     public List<Message> getMessagesById(int userId) throws SQLException {
        List<Message> listMessage = new ArrayList<>();
        String sql = "SELECT * FROM Messages WHERE receiver_id = ? OR sender_id = ?";

        try (Connection connection = DBConnection.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {

            statement.setInt(1, userId);
            statement.setInt(2, userId);

            try (ResultSet resultSet = statement.executeQuery()) {
                while (resultSet.next()) {
                    int messageId = resultSet.getInt("message_id");
                    String content = resultSet.getString("content");
                    Timestamp timestamp = resultSet.getTimestamp("timestamp");
                    int receiverId = resultSet.getInt("receiver_id");
                    int senderId = resultSet.getInt("sender_id");

                    Message message = new Message(messageId, content, new Timestamp(timestamp.getTime()), receiverId, senderId);
                    listMessage.add(message);
                }
            }
        }
        return listMessage;
    }
}