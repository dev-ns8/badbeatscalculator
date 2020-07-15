package com.nate.model.entities.stats;

import com.nate.model.enums.Card;
import lombok.Getter;
import org.springframework.lang.Nullable;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

public class KeyedHand implements Serializable {

    private static final long serialVersionUID = 86348200742L;

    @Getter private final Card first;
    @Getter private final Card second;
    @Getter private final FlopData flopData;


    private KeyedHand(Card first, Card second, FlopData flopData) {
        this.first = first;
        this.second = second;
        this.flopData = flopData;
    }

    public static KeyedHand of(Card first, Card second, @Nullable FlopData flopData) {
        if (first == null || second == null) {
            throw new IllegalStateException("KeyedHand.of() Need 2 cards");
        }
        return new KeyedHand(first, second, flopData);
    }

    public Set<Card> toSet() {
        Set<Card> r = new HashSet<>();
        r.add(first);
        r.add(second);
        return r;
    }
}
