package org.example.models;

import java.sql.Timestamp;

public class Message {
    private static int messageId;
    private String content;
    private Timestamp timestamp;
    private int receiverId;
    private int senderId;

    public Message(int messageId, String content, Timestamp timestamp, int receiverId, int senderId) {
        this.messageId = messageId;
        this.content = content;
        this.timestamp = timestamp;
        this.receiverId = receiverId;
        this.senderId = senderId;
    }

    public static int getMessageId() {
        return messageId;
    }

    public void setMessageId(int messageId) {
        this.messageId = messageId;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public Timestamp getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(Timestamp timestamp) {
        this.timestamp = timestamp;
    }

    public int getReceiverId() {
        return receiverId;
    }

    public void setReceiverId(int receiverId) {
        this.receiverId = receiverId;
    }

    public int getSenderId() {
        return senderId;
    }

    public void setSenderId(int senderId) {
        this.senderId = senderId;
    }
}