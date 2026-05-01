package com.cupidlinks.model;

import java.time.LocalDateTime;

public class Match {

    private int matchId;
    private int user1Id;
    private int user2Id;
    private LocalDateTime matchedAt;

    public Match() {}

    public int getMatchId() { return matchId; }
    public void setMatchId(int matchId) { this.matchId = matchId; }

    public int getUser1Id() { return user1Id; }
    public void setUser1Id(int user1Id) { this.user1Id = user1Id; }

    public int getUser2Id() { return user2Id; }
    public void setUser2Id(int user2Id) { this.user2Id = user2Id; }

    public LocalDateTime getMatchedAt() { return matchedAt; }
    public void setMatchedAt(LocalDateTime matchedAt) { this.matchedAt = matchedAt; }
}