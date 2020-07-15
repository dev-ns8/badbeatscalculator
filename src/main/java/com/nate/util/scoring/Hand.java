package com.nate.util.scoring;

import com.nate.model.enums.Card;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

public interface Hand {

    List<Card> naturalSort();

    List<Card> madeHandSort();

    List<Card> getHole();

    List<Card> getFlop();

    Card getTurn();

    Card getRiver();

    Set<Card> toSet();

    List<Card> toList();

    int getHandSize();

    default boolean isStraight(List<Card> hand, int straightNum) {
        hand.sort((o1, o2) -> o2.value - o1.value);
        if (hand.get(0).value == 12) {
            if (checkWheelStraight(hand, 5)) {
                return true;
            }
        }

        int numOfConsecs = 0;
        Card last = hand.get(0);
        for (Card card : hand) {
            if (card.value == last.value) {
                continue;
            }
            if (card.value + 1 == last.value) {
                numOfConsecs++;
                last = card;
            } else {
                numOfConsecs = 0;
                last = card;
            }

            if (numOfConsecs == straightNum - 1) {
                return true;
            }
        }
        return false;
    }

    default boolean checkWheelStraight(List<Card> hand, int straightNum) {
        Map<Integer, Boolean> wheelStraight = new HashMap<>();
        for (Card card : hand) {
            if (isWheelCard(card)) {
                if (!wheelStraight.containsKey(card.value)) {
                    wheelStraight.put(card.value, true);
                }
            }
        }
        if (wheelStraight.values().size() != straightNum) {
            return false;
        }

        if (hand.size() == straightNum) {
            return true;
        }

        if (!wheelStraight.containsKey(0)) {
            return false;
        }
        if (!wheelStraight.containsKey(1)) {
            return false;
        }
        if (!wheelStraight.containsKey(2)) {
            return false;
        }
        if (!wheelStraight.containsKey(3)) {
            return false;
        }
        if (!wheelStraight.containsKey(12)) {
            return false;
        }
        return true;
    }

    default boolean isWheelCard(Card card) {
        return card.value == 12 || card.value == 3 || card.value == 2 || card.value == 1 || card.value == 0;
    }
}
