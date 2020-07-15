package com.nate.util.scoring;

import com.nate.model.enums.Card;

import java.util.List;
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
}
