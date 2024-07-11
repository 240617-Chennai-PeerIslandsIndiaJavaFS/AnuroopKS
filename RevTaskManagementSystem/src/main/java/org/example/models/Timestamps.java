package org.example.models;

import java.time.LocalDateTime;

public class Timestamps {
    private int timestampId;
    private int taskId;
    private int milestoneId;
    private LocalDateTime timestamp;

    public Timestamps() {}

    public Timestamps(int timestampId, int taskId, int milestoneId, LocalDateTime timestamp) {
        this.timestampId = timestampId;
        this.taskId = taskId;
        this.milestoneId = milestoneId;
        this.timestamp = timestamp;
    }

    public int getTimestampId() {
        return timestampId;
    }

    public void setTimestampId(int timestampId) {
        this.timestampId = timestampId;
    }

    public int getTaskId() {
        return taskId;
    }

    public void setTaskId(int taskId) {
        this.taskId = taskId;
    }

    public int getMilestoneId() {
        return milestoneId;
    }

    public void setMilestoneId(int milestoneId) {
        this.milestoneId = milestoneId;
    }

    public LocalDateTime getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(LocalDateTime timestamp) {
        this.timestamp = timestamp;
    }

}