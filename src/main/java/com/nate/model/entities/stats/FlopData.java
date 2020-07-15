package com.nate.model.entities.stats;

import com.google.common.collect.ImmutableMap;
import lombok.Getter;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

public class FlopData implements Serializable {

    private static final long serialVersionUID = 6253534059769L;

    @Getter
    private final Map<Statistic, Double> stats;

    private FlopData(Map<Statistic, Double> stats) {
        this.stats = ImmutableMap.copyOf(stats);
    }

    public static FlopData of(double quads, double fullHouse, double flush, double straight,
                              double set, double twoPair, double overPair, double middlePair,
                              double lowPair, double aceHigh, double noHand, double flushDraw,
                              double nfd, double oesd, double numRuns) {
        Map<Statistic, Double> stats = new HashMap<>();
        stats.put(Statistic.QUADS, quads);
        stats.put(Statistic.FULL_HOUSE, fullHouse);
        stats.put(Statistic.FLUSH, flush);
        stats.put(Statistic.STRAIGHT, straight);
        stats.put(Statistic.SET, set);
        stats.put(Statistic.TWO_PAIR, twoPair);
        stats.put(Statistic.OVER_PAIR, overPair);
        stats.put(Statistic.MIDDLE_PAIR, middlePair);
        stats.put(Statistic.LOW_PAIR, lowPair);
        stats.put(Statistic.ACE_HIGH, aceHigh);
        stats.put(Statistic.NO_HAND, noHand);
        stats.put(Statistic.FLUSH_DRAW, flushDraw);
        stats.put(Statistic.NUT_FLUSH_DRAW, nfd);
        stats.put(Statistic.OPEN_END_DRAW, oesd);
        stats.put(Statistic.NUMBER_OF_RUNS, numRuns);
        return new FlopData(stats);
    }


}
