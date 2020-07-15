package com.nate.model.entities.exceptions;

import com.nate.model.enums.Card;

import java.util.Set;

public class CardsNotFoundException extends Exception {

    public CardsNotFoundException(String errorMessage) {
        super(errorMessage);
    }

    public static CardsNotFoundException create(Set<Card> hand) {
        StringBuilder base = new StringBuilder("HandType not found in hand: ");
//        StringBuilder base = new StringBuilder(MadeHand.getHand(handType) + " Not found in hand: ");
        for (Card card : hand) {
            base.append(card);
            base.append(", ");
        }
        return new CardsNotFoundException(base.toString());
    }
}
