package org.example.dao;

import org.example.connection.DBConnection;
import org.example.models.UserModels;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class UserDAO {
    public static Connection connection;


    public UserDAO() {
        connection = DBConnection.getConnection();
    }

    public List<UserModels.User> fetchUsers() {
        List<UserModels.User> users = new ArrayList<>();
        String query = "SELECT * FROM user";
        try (Statement stmt = connection.createStatement()) {
            ResultSet resultSet = stmt.executeQuery(query);
            while (resultSet.next()) {
                UserModels.User user = new UserModels.User();
                user.setUser_id(resultSet.getInt("user_id"));
                user.setUser_name(resultSet.getString("user_name"));
                user.setEmail(resultSet.getString("email"));
                user.setPhone(resultSet.getString("phone"));
                user.setPassword(resultSet.getString("password"));
                user.setUser_role(UserModels.UserRole.valueOf(resultSet.getString("user_role")));
                user.setStatus(UserModels.Status.valueOf(resultSet.getString("status")));
                users.add(user);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return users;
    }

    public static UserModels.User getUser(String user_name) {
        UserModels.User user = null;
        String query = "SELECT * FROM user WHERE user_name =?";
        try (PreparedStatement pstmt = connection.prepareStatement(query)) {
            pstmt.setString(1, user_name);
            ResultSet resultSet = pstmt.executeQuery();
            if (resultSet.next()) {
                user = new UserModels.User();
                user.setUser_id(resultSet.getInt("user_id"));
                user.setUser_name(resultSet.getString("user_name"));
                user.setEmail(resultSet.getString("email"));
                user.setPhone(resultSet.getString("phone"));
                user.setPassword(resultSet.getString("password"));
                user.setUser_role(UserModels.UserRole.valueOf(resultSet.getString("user_role")));
                user.setStatus(UserModels.Status.valueOf(resultSet.getString("status")));
            }
        } catch (SQLException e) {
        }
        return user;
    }

    public boolean registerUser(UserModels.User user) {
        String query = "INSERT INTO user (user_name, email, phone, password, user_role, status) VALUES (?,?,?,?,?,?)";
        try (PreparedStatement pstmt = connection.prepareStatement(query)) {
            pstmt.setString(1, user.getUser_name());
            pstmt.setString(2, user.getEmail());
            pstmt.setString(3, user.getPhone());
            pstmt.setString(4, user.getPassword());
            pstmt.setString(5, user.getUser_role().name());
            pstmt.setString(6, user.getStatus().name());
            int rowsAffected = pstmt.executeUpdate();
            return rowsAffected > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public boolean updateUser(UserModels.User user) {
        String query = "UPDATE user SET user_name =?, email =?, phone =?, password =?, user_role =?, status =? WHERE user_id =?";
        try (PreparedStatement pstmt = connection.prepareStatement(query)) {
            pstmt.setString(1, user.getUser_name());
            pstmt.setString(2, user.getEmail());
            pstmt.setString(3, user.getPhone());
            pstmt.setString(4, user.getPassword());
            pstmt.setString(5, user.getUser_role().name());
            pstmt.setString(6, user.getStatus().name());
            pstmt.setInt(7, user.getUser_id());
            int rowsAffected = pstmt.executeUpdate();
            return rowsAffected > 0;
        } catch (SQLException e) {
            return false;
        }
    }

    public boolean deleteUser(int user_id) {
        String query = "DELETE FROM user WHERE user_id =?";
        try (PreparedStatement pstmt = connection.prepareStatement(query)) {
            pstmt.setInt(1, user_id);
            int rowsAffected = pstmt.executeUpdate();
            return rowsAffected > 0;
        } catch (SQLException e) {
            return false;
        }
    }

    public UserModels.User login(String user_name, String password) {
        UserModels.User user = null;
        String query = "SELECT * FROM user WHERE user_name =? AND password =?";
        try (PreparedStatement pstmt = connection.prepareStatement(query)) {
            pstmt.setString(1, user_name);
            pstmt.setString(2, password);
            ResultSet resultSet = pstmt.executeQuery();
            if (resultSet.next()) {
                user = new UserModels.User();
                user.setUser_id(resultSet.getInt("user_id"));
                user.setUser_name(resultSet.getString("user_name"));
                user.setEmail(resultSet.getString("email"));
                user.setPhone(resultSet.getString("phone"));
                user.setPassword(resultSet.getString("password"));
                user.setUser_role(UserModels.UserRole.valueOf(resultSet.getString("user_role")));
                user.setStatus(UserModels.Status.valueOf(resultSet.getString("status")));
            }
        } catch (SQLException ex) {
        }
        return user;

    }
    public static UserModels.User getUserById(int user_id) {
        UserModels.User user = null;
        String query = "SELECT * FROM user WHERE user_id = ?";
        try (PreparedStatement pstmt = connection.prepareStatement(query)) {
            pstmt.setInt(1, user_id);
            ResultSet resultSet = pstmt.executeQuery();
            if (resultSet.next()) {
                user = new UserModels.User();
                user.setUser_id(resultSet.getInt("user_id"));
                user.setUser_name(resultSet.getString("user_name"));
                user.setEmail(resultSet.getString("email"));
                user.setPhone(resultSet.getString("phone"));
                user.setPassword(resultSet.getString("password"));
                user.setUser_role(UserModels.UserRole.valueOf(resultSet.getString("user_role")));
                user.setStatus(UserModels.Status.valueOf(resultSet.getString("status")));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return user;
    }

}