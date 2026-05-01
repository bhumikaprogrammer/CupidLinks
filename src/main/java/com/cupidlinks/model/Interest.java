package com.cupidlinks.model;

import java.time.LocalDateTime;

public class Interest {

    private int likeId;
    private int senderId;
    private int receiverId;
    private String status;
    private LocalDateTime sentAt;

    public Interest() {}

    public int getLikeId() { return likeId; }
    public void setLikeId(int likeId) { this.likeId = likeId; }

    public int getSenderId() { return senderId; }
    public void setSenderId(int senderId) { this.senderId = senderId; }

    public int getReceiverId() { return receiverId; }
    public void setReceiverId(int receiverId) { this.receiverId = receiverId; }

    public String getStatus() { return status; }
    public void setStatus(String status) { this.status = status; }

    public LocalDateTime getSentAt() { return sentAt; }
    public void setSentAt(LocalDateTime sentAt) { this.sentAt = sentAt; }
}