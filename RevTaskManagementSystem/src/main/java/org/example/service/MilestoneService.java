package org.example.service;

import org.example.dao.MilestoneDAO;
import org.example.models.Milestone;

import java.sql.SQLException;

import static org.example.dao.UserDAO.connection;

public class MilestoneService {
    private final MilestoneDAO milestoneDAO;

    public MilestoneService() {
        this.milestoneDAO = new MilestoneDAO(connection);
    }

    public void addMilestone(Milestone milestone) {
        try {
            milestoneDAO.addMilestone(milestone);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public int getProjectIdByName(String projectName) {
        try {
            return milestoneDAO.getProjectIdByName(projectName);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return -1;
    }
}