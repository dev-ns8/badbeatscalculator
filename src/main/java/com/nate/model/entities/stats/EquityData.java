package com.nate.model.entities.stats;

import com.nate.model.enums.Card;
import com.nate.structure.Pair;
import lombok.Getter;

import java.util.Map;

/**
 * Class maps a hand to the number of times it took first place at the table.
 * Dividing a hands number of first place finishes' by the total number of runs
 * gives you the equity it has at a multi hand game
 */
public class EquityData {

    @Getter private final Map<Pair<Card>, Integer> equityData;
    @Getter private final int ties;
    @Getter private final int iterations;

    private EquityData(Map<Pair<Card>, Integer> equityData, int ties, int iterations) {
        this.equityData = equityData;
        this.ties = ties;
        this.iterations = iterations;
    }

    public static EquityData of(Map<Pair<Card>, Integer> data, int ties, int iterations) {
        return new EquityData(data, ties, iterations);
    }

    public Double getHandsEquity(Pair<Card> key) {
        return 0.0;

    }
}
