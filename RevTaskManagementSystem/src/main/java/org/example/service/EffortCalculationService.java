package org.example.service;

import org.example.dao.EffortCalculationDAO;
import org.example.models.EffortCalculation;

import java.sql.SQLException;


public class EffortCalculationService {
    private EffortCalculationDAO effortCalculationDAO;

    public EffortCalculationService() throws SQLException {
        this.effortCalculationDAO = new EffortCalculationDAO();
    }


    public void updateEffortCalculation(EffortCalculation effortCalculation) throws SQLException {
        effortCalculationDAO.updateEffortCalculation(effortCalculation);
    }

    public void addEffortCalculation(EffortCalculation effortCalculation) throws SQLException {
        effortCalculationDAO.addEffortCalculation(effortCalculation);
    }

    public EffortCalculation getEffortCalculationByProjectId(int projectId) throws SQLException {
        return effortCalculationDAO.getEffortCalculationByProjectId(projectId);
    }

}