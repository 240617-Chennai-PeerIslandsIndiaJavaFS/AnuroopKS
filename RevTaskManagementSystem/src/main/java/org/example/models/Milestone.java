package org.example.models;


public class Milestone {
    private int milestoneId;
    private MilestoneName milestoneName;
    private String milestoneDescription;
    private int projectId;

    public enum MilestoneName {
        Project_Initiation,
        Design_Phase,
        Development_Phase,
        Integration_Phase,
        Testing_Phase,
        Deployment_Phase,
        Maintenance_Phase,
        Documentation_and_Training,
        Project_Closure;


    }


    public Milestone(int milestoneId, MilestoneName milestoneName, String milestoneDescription, int projectId) {
        this.milestoneId = milestoneId;
        this.milestoneName = milestoneName;
        this.milestoneDescription = milestoneDescription;
        this.projectId = projectId;
    }

    public int getMilestoneId() {
        return milestoneId;
    }

    public void setMilestoneId(int milestoneId) {
        this.milestoneId = milestoneId;
    }

    public MilestoneName getMilestoneName() {
        return milestoneName;
    }

    public void setMilestoneName(MilestoneName milestoneName) {
        this.milestoneName = milestoneName;
    }

    public String getMilestoneDescription() {
        return milestoneDescription;
    }

    public void setMilestoneDescription(String milestoneDescription) {
        this.milestoneDescription = milestoneDescription;
    }

    public int getProjectId() {
        return projectId;
    }

    public void setProjectId(int projectId) {
        this.projectId = projectId;
    }
}