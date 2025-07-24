package com.worldcup.exception;

public class TeamAlreadyPlayingException extends RuntimeException {
    public TeamAlreadyPlayingException() {
        super("One or both teams are already playing");
    }
}
