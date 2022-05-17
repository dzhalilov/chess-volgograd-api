package com.example.chessvolgograd.model;

public enum PlayerOrder {
    ID("id"),
    CLASSIC("classicRating"),
    RAPID("rapidRating"),
    BLITZ("blitzRating");

    public final String label;

    PlayerOrder(String label) {
        this.label = label;
    }
}
