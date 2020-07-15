package com.nate.util.scoring.impl;

import com.nate.model.enums.Card;
import com.nate.model.enums.MadeHand;
import com.nate.util.scoring.Hand;

import java.util.*;
import java.util.stream.Collectors;

public class FiveCardHand implements Hand, Iterable {

    private static final int SIZE = 5;

    private final List<Card> hole;
    private final List<Card> flop;
    private final boolean isReducedHand;

    private FiveCardHand(List<Card> hole, List<Card> flop, boolean isReducedHand) {
        this.hole = hole;
        this.flop = flop;
        this.isReducedHand = isReducedHand;
    }


    public static FiveCardHand of(Set<Card> hole, Set<Card> flop, boolean isReducedHand) {
        if (hole == null) {
            hole = new HashSet<Card>();
        } else if (flop == null) {
            flop = new HashSet<Card>();
        }

        if (!isReducedHand) {
            if (hole.size() != 2 || flop.size() != 3) {
                throw new IllegalStateException("Wrong number of cards in either flop or hole");
            }
        } else {
            int i;
            if ((i = hole.size() + flop.size()) != SIZE) {
                throw new IllegalStateException("Wrong number of Cards.  Expected 5 found: " + i);
            }
        }

        return new FiveCardHand(hole.stream().collect(Collectors.toList()), flop.stream().collect(Collectors.toList()), isReducedHand);
    }

    public boolean hasTopPair() {
        Set<Card> cards = hole.stream().collect(Collectors.toSet());
        cards.addAll(flop);
        if (TexasScoreUtil.score(cards) == MadeHand.PAIR.value) {
//            List<Card> sorted = TexasScoreUtil.splitHand(cards, MadeHand.PAIR.value);
        }
        return false;
    }

    @Override
    public List<Card> naturalSort() {
        List<Card> both = new ArrayList<>(hole);
        both.addAll(flop);
        both.sort((o1, o2) -> o2.value - o1.value);
        return both;
    }

    @Override
    public List<Card> madeHandSort() {
        return null;
    }

    @Override
    public List<Card> getHole() {
        return hole;
    }

    @Override
    public List<Card> getFlop() {
        return flop;
    }

    @Override
    public int getHandSize() {
        return SIZE;
    }

    @Override
    public Card getTurn() {
        throw new IndexOutOfBoundsException("No turn card in 5 card hand");
    }

    @Override
    public Card getRiver() {
        throw new IndexOutOfBoundsException("No River card in 5 card hand");
    }

    @Override
    public Set<Card> toSet() {
        Set<Card> r = new HashSet<>();
        r.addAll(hole);
        r.addAll(flop);
        if (r.size() != SIZE) {
            throw new IllegalStateException("toSet() expected to return 5 cards instead returning: " + r.size());
        }
        return r;
    }

    @Override
    public List<Card> toList() {
        List<Card> r = new ArrayList<>();
        r.addAll(hole);
        r.addAll(flop);
        if (r.size() != SIZE) {
            throw new IllegalStateException("toList() expected to return 5 cards instead returning: " + r.size());
        }
        return r;
    }

    @Override
    public Iterator iterator() {
        List<Card> n = new ArrayList<>(hole);
        n.addAll(flop);
        return new HandIterator(n);
    }

    class HandIterator implements Iterator {

        List<Card> cards;
        int index = 0;

        public HandIterator(List<Card> cards) {
            this.cards = cards;
        }

        @Override
        public boolean hasNext() {
            return index < SIZE;
        }

        @Override
        public Object next() {
            Card card = cards.get(index);
            index++;
            return card;
        }
    }
}
