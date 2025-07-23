package com.worldcup.model;

import java.util.Objects;

public class Match {
    private final String homeTeam;
    private final String awayTeam;
    private int homeScore;
    private int awayScore;
    private long lastUpdated;

    public Match(String homeTeam, String awayTeam) {
        this.homeTeam = homeTeam;
        this.awayTeam = awayTeam;
        this.homeScore = 0;
        this.awayScore = 0;
        this.lastUpdated = System.currentTimeMillis();
    }

    public String getHomeTeam() { return homeTeam; }
    public String getAwayTeam() { return awayTeam; }
    public int getHomeScore() { return homeScore; }
    public int getAwayScore() { return awayScore; }
    public long getLastUpdated() { return lastUpdated; }
    public int getTotalScore() { return homeScore + awayScore; }

    public void updateScore(int homeScore, int awayScore) {
        this.homeScore = homeScore;
        this.awayScore = awayScore;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Match match)) return false;
        return homeTeam.equals(match.homeTeam) && awayTeam.equals(match.awayTeam);
    }

    @Override
    public int hashCode() {
        return Objects.hash(homeTeam, awayTeam);
    }

    @Override
    public String toString() {
        return homeTeam + " " + homeScore + " - " + awayTeam + " " + awayScore;
    }
}
