package org.example.service;

import org.example.dao.TimestampsDAO;
import org.example.models.Timestamps;

import java.sql.SQLException;
import java.util.List;

public class TimestampsService {
    private static final TimestampsDAO timestampsDAO = new TimestampsDAO();

    public static void addTimestamp(Timestamps timestamp) {
        try {
            timestampsDAO.addTimestamp(timestamp);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public Timestamps getTimestamp(int id) {
        try {
            return timestampsDAO.getTimestamp(id);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public List<Timestamps> getAllTimestamps() {
        try {
            return timestampsDAO.getAllTimestamps();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public void updateTimestamp(Timestamps timestamp) {
        try {
            timestampsDAO.updateTimestamp(timestamp);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void deleteTimestamp(int id) {
        try {
            timestampsDAO.deleteTimestamp(id);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    public static List<Timestamps> getTimestampsByTaskId(int taskId) {
        try {
            return timestampsDAO.getTimestampsByTaskId(taskId);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }
}