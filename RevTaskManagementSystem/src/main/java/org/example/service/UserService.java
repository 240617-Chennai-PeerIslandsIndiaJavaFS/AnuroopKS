package org.example.service;

import org.example.dao.UserDAO;
import org.example.models.UserModels;

import java.util.List;

public class UserService {
    private UserDAO userDao;

    public UserService(UserDAO userDao) {
        this.userDao = userDao;
    }

    public List<UserModels.User> getAllUsers() {
        return userDao.fetchUsers();
    }

    public static UserModels.User getUser(String user_name) {
        return UserDAO.getUser(user_name);
    }

    public boolean registerUser(UserModels.User user) {
        if (user == null) {
            throw new IllegalArgumentException("User cannot be null");
        }
        if (user.getUser_name() == null || user.getUser_name().trim().isEmpty()) {
            throw new IllegalArgumentException("Username cannot be null or empty");
        }
        if (user.getPassword() == null || user.getPassword().trim().isEmpty()) {
            throw new IllegalArgumentException("Password cannot be null or empty");
        }
        if (user.getEmail() == null || user.getEmail().trim().isEmpty()) {
            throw new IllegalArgumentException("Email cannot be null or empty");
        }
        if (user.getPhone() == null || user.getPhone().trim().isEmpty()) {
            throw new IllegalArgumentException("Phone cannot be null or empty");
        }

        return userDao.registerUser(user);
    }

    public boolean updateUser(UserModels.User user) {
        if (user == null) {
            throw new IllegalArgumentException("User cannot be null");
        }
        if (user.getUser_id() <= 0) {
            throw new IllegalArgumentException("User ID must be a positive number");
        }
        if (user.getUser_name() == null || user.getUser_name().trim().isEmpty()) {
            throw new IllegalArgumentException("Username cannot be null or empty");
        }
        if (user.getPassword() == null || user.getPassword().trim().isEmpty()) {
            throw new IllegalArgumentException("Password cannot be null or empty");
        }
        if (user.getEmail() == null || user.getEmail().trim().isEmpty()) {
            throw new IllegalArgumentException("Email cannot be null or empty");
        }
        if (user.getPhone() == null || user.getPhone().trim().isEmpty()) {
            throw new IllegalArgumentException("Phone cannot be null or empty");
        }

        return userDao.updateUser(user);
    }

    public boolean deleteUser(int user_id) {
        if (user_id <= 0) {
            throw new IllegalArgumentException("User ID must be a positive number");
        }

        return userDao.deleteUser(user_id);
    }

    public UserModels.User login(String username, String password) {
        if (username == null || password == null) {
            throw new IllegalArgumentException("Username and password cannot be null");
        }
        if (username.trim().isEmpty() || password.trim().isEmpty()) {
            throw new IllegalArgumentException("Username and password cannot be empty");
        }

        return userDao.login(username, password);
    }
    public UserModels.User getUserById(int userId) {
        return UserDAO.getUserById(userId);
    }

}