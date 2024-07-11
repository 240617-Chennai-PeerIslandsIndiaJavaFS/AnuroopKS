package org.example.models;

import java.sql.Date;

public class EffortCalculation {
    private Date projectEndDate;
    private int actualEffort;
    private int projectId;


    public Date getProjectEndDate() {
        return projectEndDate;
    }

    public void setProjectEndDate(Date projectEndDate) {
        this.projectEndDate = projectEndDate;
    }

    public int getActualEffort() {
        return actualEffort;
    }

    public void setActualEffort(int actualEffort) {
        this.actualEffort = actualEffort;
    }

    public int getProjectId() {
        return projectId;
    }

    public void setProjectId(int projectId) {
        this.projectId = projectId;
    }
}