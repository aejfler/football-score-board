package com.worldcup.exception;

public class SelfMatchException extends IllegalArgumentException {
    public SelfMatchException() {
        super("A team cannot play against itself");
    }
}
