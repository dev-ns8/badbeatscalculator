package com.nate.model.entities.exceptions;

public class HandCardsErrorException extends Exception {

    private HandCardsErrorException(String errMsg) {
        super(errMsg);
    }

    public static HandCardsErrorException error(int supposeHand, int cardsPresent) {
        return new HandCardsErrorException("PLACEHOLDER");
    }
}
