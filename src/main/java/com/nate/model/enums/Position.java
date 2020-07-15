package com.nate.model.enums;

public enum Position {

    LITTLE_BLIND(0),
    BIG_BLIND(1),
    UTG(3),
    UTG_ONE(4),
    UTG_TWO(5),
    HIGH_JACK(6),
    CUTOFF(7);

    public int value;

    Position(int value) {
        this.value = value;
    }

    public static Position firstToGo(Street street) {
        if (street.equals(Street.PRE_FLOP)) {
            return UTG;
        }
        return LITTLE_BLIND;
    }
}
