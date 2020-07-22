package com.nate.model.enums;

import com.google.gson.annotations.SerializedName;

public enum Suit {

    HEART("heart"),
    DIAMOND("diamond"),
    SPADE("spade"),
    CLUB("club");

    @SerializedName("name")
    public String name;

    Suit(String name) {
        this.name = name;
    }
}
