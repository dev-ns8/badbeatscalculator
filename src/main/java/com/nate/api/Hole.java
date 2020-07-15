package com.nate.api;

import com.nate.model.enums.Card;
import lombok.Getter;

import javax.validation.constraints.NotNull;

public class Hole {

    @Getter
    @NotNull
    private final Card first;
    @Getter
    @NotNull
    private final Card second;

    private Hole(Card first, Card second) {
        this.first = first;
        this.second = second;
    }

    public static Hole of(Card first, Card second) {
        return new Hole(first, second);
    }
}
