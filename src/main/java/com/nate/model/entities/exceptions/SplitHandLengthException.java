package com.nate.model.entities.exceptions;

import com.nate.model.enums.Card;
import com.nate.model.enums.MadeHand;
import com.sun.istack.internal.Nullable;

import java.util.Arrays;
import java.util.List;

public class SplitHandLengthException extends Exception {

    private SplitHandLengthException(String err) {
        super(err);
    }

    public static SplitHandLengthException error(@Nullable List<Card> first, MadeHand hand) {
        String error = "Error with hand: " + Arrays.toString(first.toArray()) + " Should have found HandType of: " + hand;
        return new SplitHandLengthException(error);
    }
}
