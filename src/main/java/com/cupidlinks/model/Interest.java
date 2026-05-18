package com.cupidlinks.model;

import java.time.LocalDateTime;

/**
 * Model class representing an interest action between two users.
 */
public class Interest {

    private int likeId;
    private int senderId;
    private int receiverId;
    private String status;
    private LocalDateTime sentAt;

    /**
     * Creates an empty Interest object.
     */
    public Interest() {}

    /**
     * @return unique interest record ID
     */
    public int getLikeId() { return likeId; }

    /**
     * @param likeId unique interest record ID
     */
    public void setLikeId(int likeId) { this.likeId = likeId; }

    /**
     * @return ID of the user who sent the interest
     */
    public int getSenderId() { return senderId; }

    /**
     * @param senderId ID of the user who sent the interest
     */
    public void setSenderId(int senderId) { this.senderId = senderId; }

    /**
     * @return ID of the user who received the interest
     */
    public int getReceiverId() { return receiverId; }

    /**
     * @param receiverId ID of the user who received the interest
     */
    public void setReceiverId(int receiverId) { this.receiverId = receiverId; }

    /**
     * @return current interest status
     */
    public String getStatus() { return status; }

    /**
     * @param status current interest status
     */
    public void setStatus(String status) { this.status = status; }

    /**
     * @return date and time when the interest was sent
     */
    public LocalDateTime getSentAt() { return sentAt; }

    /**
     * @param sentAt date and time when the interest was sent
     */
    public void setSentAt(LocalDateTime sentAt) { this.sentAt = sentAt; }
}
