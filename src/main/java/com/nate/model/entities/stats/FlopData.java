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
        this.stats = stats;
    }

    public static FlopData of(double quads, double fullHouse, double flush, double straight,
                              double set, double twoPair, double overPair, double middlePair,
                              double lowPair, double aceHigh, double noHand, double flushDraw,
                              double nfd, double oesd, double numRuns) {
        Map<Statistic, Double> s = new HashMap<>();
        s.put(Statistic.QUADS, quads);
        s.put(Statistic.FULL_HOUSE, fullHouse);
        s.put(Statistic.FLUSH, flush);
        s.put(Statistic.STRAIGHT, straight);
        s.put(Statistic.SET, set);
        s.put(Statistic.TWO_PAIR, twoPair);
        s.put(Statistic.OVER_PAIR, overPair);
        s.put(Statistic.MIDDLE_PAIR, middlePair);
        s.put(Statistic.LOW_PAIR, lowPair);
        s.put(Statistic.ACE_HIGH, aceHigh);
        s.put(Statistic.NO_HAND, noHand);
        s.put(Statistic.FLUSH_DRAW, flushDraw);
        s.put(Statistic.NUT_FLUSH_DRAW, nfd);
        s.put(Statistic.OPEN_END_DRAW, oesd);
        s.put(Statistic.NUMBER_OF_RUNS, numRuns);
        return new FlopData(s);
    }

    public static FlopData of(Map<Statistic, Double> s) {
        Map<Statistic, Double> map = new HashMap<>();
        map.put(Statistic.QUADS, s.get(Statistic.QUADS));
        map.put(Statistic.FULL_HOUSE, s.get(Statistic.FULL_HOUSE));
        map.put(Statistic.FLUSH, s.get(Statistic.FLUSH));
        map.put(Statistic.STRAIGHT, s.get(Statistic.STRAIGHT));
        map.put(Statistic.SET, s.get(Statistic.SET));
        map.put(Statistic.TWO_PAIR, s.get(Statistic.TWO_PAIR));
        map.put(Statistic.OVER_PAIR, s.get(Statistic.OVER_PAIR));
        map.put(Statistic.MIDDLE_PAIR, s.get(Statistic.MIDDLE_PAIR));
        map.put(Statistic.LOW_PAIR, s.get(Statistic.LOW_PAIR));
        map.put(Statistic.ACE_HIGH, s.get(Statistic.ACE_HIGH));
        map.put(Statistic.NO_HAND, s.get(Statistic.NO_HAND));
        map.put(Statistic.FLUSH_DRAW, s.get(Statistic.FLUSH_DRAW));
        map.put(Statistic.NUT_FLUSH_DRAW, s.get(Statistic.NUT_FLUSH_DRAW));
        map.put(Statistic.OPEN_END_DRAW, s.get(Statistic.OPEN_END_DRAW));
        map.put(Statistic.NUMBER_OF_RUNS, s.get(Statistic.NUMBER_OF_RUNS));
        FlopData data = new FlopData(map);
        return data;
    }


}
