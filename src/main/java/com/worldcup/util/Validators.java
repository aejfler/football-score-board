package com.worldcup.util;

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
}
