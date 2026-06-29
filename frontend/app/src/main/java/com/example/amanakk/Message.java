package com.example.amanakk;

public class Message {
    private Long id;
    private String receiverNationalId; // Receiver (cyber specialist) uses unique ID
    private String senderName;
    private String senderId; // Sender (victim) uses national ID
    private String messagePreview;
    private String timestamp;
    private boolean solved;

    // Getters and Setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getReceiverNationalId() {
        return receiverNationalId;
    }

    public void setReceiverNationalId(String receiverNationalId) {
        this.receiverNationalId = receiverNationalId;
    }

    public String getSenderName() {
        return senderName;
    }

    public void setSenderName(String senderName) {
        this.senderName = senderName;
    }

    public String getSenderId() {
        return senderId;
    }

    public void setSenderId(String senderId) {
        this.senderId = senderId;
    }

    public String getMessagePreview() {
        return messagePreview;
    }

    public void setMessagePreview(String messagePreview) {
        this.messagePreview = messagePreview;
    }

    public String getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(String timestamp) {
        this.timestamp = timestamp;
    }

    public boolean isSolved() {
        return solved;
    }

    public void setSolved(boolean solved) {
        this.solved = solved;
    }
}
