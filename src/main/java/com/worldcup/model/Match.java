package com.worldcup.model;

import com.worldcup.util.Validators;
import lombok.Getter;
import lombok.Setter;

import java.util.Objects;
import java.util.concurrent.atomic.AtomicLong;

@Getter
@Setter
public class Match {
    private static final AtomicLong sequenceCounter = new AtomicLong(0);
    private long lastUpdated;
    private final String homeTeam;
    private final String awayTeam;
    private int homeScore;
    private int awayScore;

    public Match(String homeTeam, String awayTeam) {
        this.homeTeam = homeTeam;
        this.awayTeam = awayTeam;
        this.homeScore = 0;
        this.awayScore = 0;
        validate();
    }

    public int getTotalScore() {
        return homeScore + awayScore;
    }

    public void updateScore(int homeScore, int awayScore) {
        Validators.validateTeamScores(homeScore, awayScore);
        this.homeScore = homeScore;
        this.awayScore = awayScore;
        this.lastUpdated = sequenceCounter.incrementAndGet();
    }

    public void validate() {
        Validators.validateTeamScores(this.homeScore, this.awayScore);
        Validators.validateTeamName(this.homeTeam);
        Validators.validateTeamName(this.awayTeam);
        Validators.validateTeamsAreDifferent(this.homeTeam, this.awayTeam);
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
        return String.format("%s %d - %s %d", homeTeam, homeScore, awayTeam, awayScore);
    }
}
