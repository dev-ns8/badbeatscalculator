package com.nate.model.enums;

public enum MadeHand {

    ROYAL_FLUSH(22),
    STRAIGHT_FLUSH(21),
    FOUR_KIND(20),
    FULL_HOUSE(19),
    FLUSH(18),
    STRAIGHT(17),
    THREE_KIND(16),
    TWO_PAIR(15),
    PAIR(14),
    ACE_HIGH(12),
    KING_HIGH(11),
    QUEEN_HIGH(10),
    JACK_HIGH(9),
    TEN_HIGH(8),
    NINE_HIGH(7),
    EIGHT_HIGH(6),
    SEVEN_HIGH(5),
    SIX_HIGH(4),
    FIVE_HIGH(3),
    FOUR_HIGH(2),
    THREE_HIGH(1),
    TWO_HIGH(0);

    public final int value;

    MadeHand(int value) {
        this.value = value;
    }

    public static MadeHand getHand(int value) {
        switch (value) {
            case 22:
                return ROYAL_FLUSH;
            case 21:
                return STRAIGHT_FLUSH;
            case 20:
                return FOUR_KIND;
            case 19:
                return FULL_HOUSE;
            case 18:
                return FLUSH;
            case 17:
                return STRAIGHT;
            case 16:
                return THREE_KIND;
            case 15:
                return TWO_PAIR;
            case 14:
                return PAIR;
            case 12:
                return ACE_HIGH;
            case 11:
                return KING_HIGH;
            case 10:
                return QUEEN_HIGH;
            case 9:
                return JACK_HIGH;
            case 8:
                return TEN_HIGH;
            case 7:
                return NINE_HIGH;
            case 6:
                return EIGHT_HIGH;
            case 5:
                return SEVEN_HIGH;
            case 4:
                return SIX_HIGH;
            case 3:
                return FIVE_HIGH;
            case 2:
                return FOUR_HIGH;
            case 1:
                return THREE_HIGH;
            case 0:
                return TWO_HIGH;
            default:
                // TODO:: Should throw an error here
                return TWO_HIGH;
        }
    }


    @Override
    public String toString() {
        return this.name();
    }
}
