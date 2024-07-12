package org.example.models;

import java.sql.Timestamp;

public class Timestamps {
    private int timestamp_id;
    private int task_id;
    private Timestamp time;

    // getters and setters
    public int getTimestampId() {
        return timestamp_id;
    }

    public void setTimestampId(int timestampId) {
        this.timestamp_id = timestampId;
    }

    public int getTaskId() {
        return task_id;
    }

    public void setTaskId(int taskId) {
        this.task_id = taskId;
    }

    public Timestamp getTime() {
        return time;
    }

    public void setTime(Timestamp time) {
        this.time = time;
    }
}