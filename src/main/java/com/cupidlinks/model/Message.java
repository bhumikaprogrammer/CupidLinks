package com.cupidlinks.model;

import java.time.LocalDateTime;

/**
 * Model class representing a message sent inside a match conversation.
 */
public class Message {

    private int messageId;
    private int matchId;
    private int senderId;
    private String content;
    private LocalDateTime sentAt;

    /**
     * Creates an empty Message object.
     */
    public Message() {}

    /**
     * @return unique message ID
     */
    public int getMessageId() { return messageId; }

    /**
     * @param messageId unique message ID
     */
    public void setMessageId(int messageId) { this.messageId = messageId; }

    /**
     * @return ID of the match conversation
     */
    public int getMatchId() { return matchId; }

    /**
     * @param matchId ID of the match conversation
     */
    public void setMatchId(int matchId) { this.matchId = matchId; }

    /**
     * @return ID of the user who sent the message
     */
    public int getSenderId() { return senderId; }

    /**
     * @param senderId ID of the user who sent the message
     */
    public void setSenderId(int senderId) { this.senderId = senderId; }

    /**
     * @return message body text
     */
    public String getContent() { return content; }

    /**
     * @param content message body text
     */
    public void setContent(String content) { this.content = content; }

    /**
     * @return date and time when the message was sent
     */
    public LocalDateTime getSentAt() { return sentAt; }

    /**
     * @param sentAt date and time when the message was sent
     */
    public void setSentAt(LocalDateTime sentAt) { this.sentAt = sentAt; }
}
