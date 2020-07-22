package com.nate.api;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.google.gson.annotations.SerializedName;
import com.nate.model.enums.Card;
import com.nate.structure.Pair;
import lombok.Getter;

import java.util.ArrayList;


public class HandWrapper {

    @Getter
    @SerializedName("hands")
    private final ArrayList<Pair<Card>> hands;

    @JsonCreator
    public HandWrapper(ArrayList<Pair<Card>> hands) {
        this.hands = hands;
    }

    private static HandWrapper of (ArrayList<Pair<Card>> hands) {
        return new HandWrapper(hands);
    }
}
