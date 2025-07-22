package com.worldcup.utils;

public class Validators {
    private Validators() {}

    public static boolean isNullOrBlank(String s) {
        return s == null || s.isBlank();
    }

    public static void validateTeamName(String teamName, String fieldName) {
        if (isNullOrBlank(teamName)) {
            throw new IllegalArgumentException(fieldName + " cannot be null or blank");
        }
    }

    public static void validateTeamsAreDifferent(String homeTeam, String awayTeam) {
        if (homeTeam != null && homeTeam.equals(awayTeam)) {
            throw new IllegalArgumentException("A team cannot play against itself");
        }
    }
}
