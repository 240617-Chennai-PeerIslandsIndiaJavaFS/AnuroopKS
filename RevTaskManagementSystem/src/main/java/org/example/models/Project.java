package org.example.models;

import java.sql.Date;

public class Project {
    private int projectId;
    private String projectName;
    private int clientId;
    private int teamId;
    private Date startDate;
    private Date deadline;
    private String projectDescription;
    private ProjectStatus projectStatus;

    public enum ProjectStatus {
        Assigned,
        In_Progress,
        Completed;

        public static ProjectStatus fromString(String status) {
            try {
                return ProjectStatus.valueOf(status.replace(" ", "_"));
            } catch (IllegalArgumentException e) {
                throw new IllegalArgumentException("No enum constant for status: " + status);
            }
        }
    }
    // Getters and Setters
    public int getProjectId() {
        return projectId;
    }

    public void setProjectId(int projectId) {
        this.projectId = projectId;
    }

    public String getProjectName() {
        return projectName;
    }

    public void setProjectName(String projectName) {
        this.projectName = projectName;
    }

    public int getClientId() {
        return clientId;
    }

    public void setClientId(int clientId) {
        this.clientId = clientId;
    }

    public int getTeamId() {
        return teamId;
    }

    public void setTeamId(int teamId) {
        this.teamId = teamId;
    }

    public Date getStartDate() {
        return startDate;
    }

    public void setStartDate(Date startDate) {
        this.startDate = startDate;
    }

    public Date getDeadline() {
        return deadline;
    }

    public void setDeadline(Date deadline) {
        this.deadline = deadline;
    }

    public String getProjectDescription() {
        return projectDescription;
    }

    public void setProjectDescription(String projectDescription) {
        this.projectDescription = projectDescription;
    }

    public ProjectStatus getProjectStatus() {
        return projectStatus;
    }

    public void setProjectStatus(ProjectStatus projectStatus) {
        this.projectStatus = projectStatus;
    }

}