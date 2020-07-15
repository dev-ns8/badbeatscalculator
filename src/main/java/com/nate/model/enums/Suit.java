package com.nate.model.enums;

public enum Suit {

    HEART("heart"),
    DIAMOND("diamond"),
    SPADE("spade"),
    CLUB("club");

    public String name;

    Suit(String name) {
        this.name = name;
    }
}
