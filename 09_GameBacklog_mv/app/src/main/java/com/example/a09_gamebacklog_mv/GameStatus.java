package com.example.a09_gamebacklog_mv;

public enum GameStatus {
    WANT(0),
    PLAYING(1),
    STALLED(2),
    DROPPED(3);

    private final int value;
    GameStatus(int value) {
        this.value = value;
    }

    public int getValue() {
        return value;
    }
}
