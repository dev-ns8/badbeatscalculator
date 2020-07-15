package com.nate.model.entities;

import com.nate.model.enums.Card;

import java.util.Comparator;

public class CardComparator implements Comparator<Card> {

    @Override
    public int compare(Card o1, Card o2) {
        if (o2.value - o1.value == 0) {
            return -1;
        }

        int result = o2.value - o1.value;

        if (result < 0) {
            return -1;
        } else {
            return 1;
        }
    }
}
