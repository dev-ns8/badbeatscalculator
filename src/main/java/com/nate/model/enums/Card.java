package com.nate.model.enums;


import com.google.gson.annotations.SerializedName;

public enum Card {

    ACE_HEART(12, Suit.HEART),
    ACE_DIAMOND(12, Suit.DIAMOND),
    ACE_SPADE(12, Suit.SPADE),
    ACE_CLUB(12, Suit.CLUB),

    TWO_HEART(0, Suit.HEART),
    TWO_DIAMOND(0, Suit.DIAMOND),
    TWO_SPADE(0, Suit.SPADE),
    TWO_CLUB(0, Suit.CLUB),

    THREE_HEART(1, Suit.HEART),
    THREE_DIAMOND(1, Suit.DIAMOND),
    THREE_SPADE(1, Suit.SPADE),
    THREE_CLUB(1, Suit.CLUB),

    FOUR_HEART(2, Suit.HEART),
    FOUR_DIAMOND(2, Suit.DIAMOND),
    FOUR_SPADE(2, Suit.SPADE),
    FOUR_CLUB(2, Suit.CLUB),

    FIVE_HEART(3, Suit.HEART),
    FIVE_DIAMOND(3, Suit.DIAMOND),
    FIVE_SPADE(3, Suit.SPADE),
    FIVE_CLUB(3, Suit.CLUB),

    SIX_HEART(4, Suit.HEART),
    SIX_DIAMOND(4, Suit.DIAMOND),
    SIX_SPADE(4, Suit.SPADE),
    SIX_CLUB(4, Suit.CLUB),

    SEVEN_HEART(5, Suit.HEART),
    SEVEN_DIAMOND(5, Suit.DIAMOND),
    SEVEN_SPADE(5, Suit.SPADE),
    SEVEN_CLUB(5, Suit.CLUB),

    EIGHT_HEART(6, Suit.HEART),
    EIGHT_DIAMOND(6, Suit.DIAMOND),
    EIGHT_SPADE(6, Suit.SPADE),
    EIGHT_CLUB(6, Suit.CLUB),

    NINE_HEART(7, Suit.HEART),
    NINE_DIAMOND(7, Suit.DIAMOND),
    NINE_SPADE(7, Suit.SPADE),
    NINE_CLUB(7, Suit.CLUB),

    TEN_HEART(8, Suit.HEART),
    TEN_DIAMOND(8, Suit.DIAMOND),
    TEN_SPADE(8, Suit.SPADE),
    TEN_CLUB(8, Suit.CLUB),

    JACK_HEART(9, Suit.HEART),
    JACK_DIAMOND(9, Suit.DIAMOND),
    JACK_SPADE(9, Suit.SPADE),
    JACK_CLUB(9, Suit.CLUB),

    QUEEN_HEART(10, Suit.HEART),
    QUEEN_DIAMOND(10, Suit.DIAMOND),
    QUEEN_SPADE(10, Suit.SPADE),
    QUEEN_CLUB(10, Suit.CLUB),

    KING_HEART(11, Suit.HEART),
    KING_DIAMOND(11, Suit.DIAMOND),
    KING_SPADE(11, Suit.SPADE),
    KING_CLUB(11, Suit.CLUB);

    @SerializedName("value")
    public int value;
    @SerializedName("suit")
    public Suit suit;

    Card(int value, Suit suit) {
        this.value = value;
        this.suit = suit;
    }

    public static Card getCard(int value, String suit) {
        switch (suit) {
            case "heart":
                return getCard(value, 0);
            case "diamond":
                return getCard(value, 1);
            case "spade":
                return getCard(value, 2);
            case "club":
                return getCard(value, 3);
            default:
                return getCard(value, 0);
        }
    }

    public static Card getCard(int value, int suit) {
        switch (value) {
            case 12:
                switch (suit) {
                    case 0:
                        return ACE_HEART;
                    case 1:
                        return ACE_DIAMOND;
                    case 2:
                        return ACE_SPADE;
                    case 3:
                        return ACE_CLUB;
                }
            case 11:
                switch (suit) {
                    case 0:
                        return KING_HEART;
                    case 1:
                        return KING_DIAMOND;
                    case 2:
                        return KING_SPADE;
                    case 3:
                        return KING_CLUB;
                }
            case 10:
                switch (suit) {
                    case 0:
                        return QUEEN_HEART;
                    case 1:
                        return QUEEN_DIAMOND;
                    case 2:
                        return QUEEN_SPADE;
                    case 3:
                        return QUEEN_CLUB;
                }
            case 9:
                switch (suit) {
                    case 0:
                        return JACK_HEART;
                    case 1:
                        return JACK_DIAMOND;
                    case 2:
                        return JACK_SPADE;
                    case 3:
                        return JACK_CLUB;
                }
            case 8:
                switch (suit) {
                    case 0:
                        return TEN_HEART;
                    case 1:
                        return TEN_DIAMOND;
                    case 2:
                        return TEN_SPADE;
                    case 3:
                        return TEN_CLUB;
                }
            case 7:
                switch (suit) {
                    case 0:
                        return NINE_HEART;
                    case 1:
                        return NINE_DIAMOND;
                    case 2:
                        return NINE_SPADE;
                    case 3:
                        return NINE_CLUB;
                }
            case 6:
                switch (suit) {
                    case 0:
                        return EIGHT_HEART;
                    case 1:
                        return EIGHT_DIAMOND;
                    case 2:
                        return EIGHT_SPADE;
                    case 3:
                        return EIGHT_CLUB;
                }
            case 5:
                switch (suit) {
                    case 0:
                        return SEVEN_HEART;
                    case 1:
                        return SEVEN_DIAMOND;
                    case 2:
                        return SEVEN_SPADE;
                    case 3:
                        return SEVEN_CLUB;
                }
            case 4:
                switch (suit) {
                    case 0:
                        return SIX_HEART;
                    case 1:
                        return SIX_DIAMOND;
                    case 2:
                        return SIX_SPADE;
                    case 3:
                        return SIX_CLUB;
                }
            case 3:
                switch (suit) {
                    case 0:
                        return FIVE_HEART;
                    case 1:
                        return FIVE_DIAMOND;
                    case 2:
                        return FIVE_SPADE;
                    case 3:
                        return FIVE_CLUB;
                }
            case 2:
                switch (suit) {
                    case 0:
                        return FOUR_HEART;
                    case 1:
                        return FOUR_DIAMOND;
                    case 2:
                        return FOUR_SPADE;
                    case 3:
                        return FOUR_CLUB;
                }
            case 1:
                switch (suit) {
                    case 0:
                        return THREE_HEART;
                    case 1:
                        return THREE_DIAMOND;
                    case 2:
                        return THREE_SPADE;
                    case 3:
                        return THREE_CLUB;
                }
            case 0:
                switch (suit) {
                    case 0:
                        return TWO_HEART;
                    case 1:
                        return TWO_DIAMOND;
                    case 2:
                        return TWO_SPADE;
                    case 3:
                        return TWO_CLUB;
                }
            default:
                return TWO_HEART;
        }
    }

    @Override
    public String toString(){
        String name = this.name();
        String[] parts = name.split("_");
        StringBuilder builder = new StringBuilder(parts[0]);
        switch(parts[1]) {
            case "HEART":
                builder.append("H");
                break;
            case "DIAMOND":
                builder.append("D");
                break;
            case "SPADE":
                builder.append("S");
                break;
            case "CLUB":
                builder.append("C");
                break;
            default:
                break;
        }
        return builder.toString();
    }
}
