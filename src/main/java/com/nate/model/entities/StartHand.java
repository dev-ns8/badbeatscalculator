package com.nate.model.entities;

import com.nate.model.enums.Card;
import lombok.Getter;

public class StartHand {

    @Getter private final int id;
    @Getter private final Card first;
    @Getter private final Card second;

    public StartHand(int id, Card first, Card second) {
        this.id = id;
        this.first = first;
        this.second = second;
    }
}
