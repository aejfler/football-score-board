package com.worldcup.exception;

public class NegativeScoreException extends IllegalArgumentException {
    public NegativeScoreException(int homeScore, int awayScore) {
        super("Scores must be zero or positive. Received: home = " + homeScore + ", away = " + awayScore);
    }
}
