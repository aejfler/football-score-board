package com.worldcup.util;

import com.worldcup.exception.InvalidTeamNameException;
import com.worldcup.exception.NegativeScoreException;
import com.worldcup.exception.SelfMatchException;

public class Validators {
    private Validators() {}

    public static boolean isNullOrBlank(String s) {
        return s == null || s.isBlank();
    }

    public static void validateTeamName(String teamName) {
        if (isNullOrBlank(teamName)) {
            throw new InvalidTeamNameException();
        }
    }

    public static void validateTeamsAreDifferent(String homeTeam, String awayTeam) {
        if (homeTeam != null && homeTeam.equals(awayTeam)) {
            throw new SelfMatchException();
        }
    }

    public static void validateTeamScores(int homeScore, int awayScore) {
        if (homeScore < 0 || awayScore < 0) {
            throw new NegativeScoreException(homeScore, awayScore);
        }
    }
}
