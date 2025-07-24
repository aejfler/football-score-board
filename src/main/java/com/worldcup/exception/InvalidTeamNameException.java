package com.worldcup.exception;

public class InvalidTeamNameException extends IllegalArgumentException {
    public InvalidTeamNameException() {
        super("Team name cannot be empty or null");
    }
}
