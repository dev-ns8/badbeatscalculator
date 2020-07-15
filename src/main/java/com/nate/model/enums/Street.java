package com.nate.model.enums;

public enum Street {

    PRE_FLOP(0),
    FLOP(1),
    TURN(2),
    RIVER(3);

    public int value;

    Street(int value) {
        this.value = value;
    }
}
