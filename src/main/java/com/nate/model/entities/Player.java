package com.nate.model.entities;

import com.nate.model.enums.Card;
import com.nate.model.enums.Position;

import java.util.HashSet;
import java.util.Set;

public class Player {

    private Set<Card> hand;
    private Position position;
    private int chipStack;

    private Player(Position position, int chipStack) {
        this.position = position;
        this.chipStack = chipStack;
    }

    public static Player create(Position position) {
        return new Player(position, 1000);
    }

    public void collectCard(Card card) {
        if (hand == null) {
            hand = new HashSet<>();
        }
        hand.add(card);
    }

    public Set<Card> getHand() {
        return hand;
    }

    public Player collectHand(Set<Card> hand) {
        this.hand = hand;
        return this;
    }

    public Player fold() {
        hand = new HashSet<>();
        position = null;
        return this;
    }

    public Player call(int bet) {
        chipStack = chipStack - bet;
        return this;
    }

    public Player raise(int callPlusRaise) {
        return call(callPlusRaise);
    }
}
