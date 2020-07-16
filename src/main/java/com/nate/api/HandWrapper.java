package com.nate.api;

import lombok.Getter;

import java.util.List;


public class HandWrapper {

    @Getter private final List<Pair> hands;

    public HandWrapper(List<Pair> hands) {
        this.hands = hands;
    }

    private static HandWrapper of (List<Pair> hands) {
        return new HandWrapper(hands);
    }
}
