package com.worldcup.exception;

public class MatchNotFoundException extends RuntimeException {
    public MatchNotFoundException() {
        super("Match not found or already finished");
    }
}
