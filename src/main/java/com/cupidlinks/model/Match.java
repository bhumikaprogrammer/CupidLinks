package com.cupidlinks.model;

import java.time.LocalDateTime;

/**
 * Model class representing a match between two users.
 */
public class Match {

    private int matchId;
    private int user1Id;
    private int user2Id;
    private LocalDateTime matchedAt;

    /**
     * Creates an empty Match object.
     */
    public Match() {}

    /**
     * @return unique match ID
     */
    public int getMatchId() { return matchId; }

    /**
     * @param matchId unique match ID
     */
    public void setMatchId(int matchId) { this.matchId = matchId; }

    /**
     * @return ID of the first matched user
     */
    public int getUser1Id() { return user1Id; }

    /**
     * @param user1Id ID of the first matched user
     */
    public void setUser1Id(int user1Id) { this.user1Id = user1Id; }

    /**
     * @return ID of the second matched user
     */
    public int getUser2Id() { return user2Id; }

    /**
     * @param user2Id ID of the second matched user
     */
    public void setUser2Id(int user2Id) { this.user2Id = user2Id; }

    /**
     * @return date and time when the match was created
     */
    public LocalDateTime getMatchedAt() { return matchedAt; }

    /**
     * @param matchedAt date and time when the match was created
     */
    public void setMatchedAt(LocalDateTime matchedAt) { this.matchedAt = matchedAt; }
}
