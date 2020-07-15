package com.nate.util.scoring;

import com.nate.model.entities.stats.Statistic;
import com.nate.model.enums.Card;

import java.util.Map;
import java.util.Set;

public interface ScoreUtil {

    int compareHands(Set<Card> first, Set<Card> second, Set<Card> board);

    int score(Set<Card> cards);

    Map<Statistic, Boolean> flopStats(Hand hand);

}
