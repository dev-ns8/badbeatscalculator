package com.nate.util.concurrent;

import com.nate.model.entities.Dealer;
import com.nate.model.entities.stats.Statistic;
import com.nate.model.enums.Card;
import com.nate.structure.Pair;
import com.nate.util.scoring.impl.TexasScoreUtil;
import com.nate.util.scoring.impl.TexasScoreManager;
import com.nate.util.scoring.impl.FiveCardHand;
import com.nate.util.scoring.Hand;

import java.util.HashMap;
import java.util.Map;

public class FlopStatsRunner implements Runnable {

    private final Pair<Card> hand;

    private FlopStatsRunner(Pair<Card> hand) {
        this.hand = hand;
    }

    public static FlopStatsRunner create(Pair<Card> hand) {
        return new FlopStatsRunner(hand);
    }

    @Override
    public void run() {
        getStats();
    }

    private void getStats() {
        Dealer dealer = Dealer.create();
        dealer.shuffle();
        Util.handleHole(hand.toSet(), dealer);
        Hand holeFlop = FiveCardHand.of(hand.toSet(), dealer.flop(), false);
        // ---------------------------------
        Map<Statistic, Boolean> stats = TexasScoreUtil.flopStats(holeFlop);
        /** TOOD::
         * This map has length either 7,8 or 10
         *
         *  Method will put a 'true' in for ACE_HIGH Even when a set is present.
         *  Don't think this should happen or should it???
         */

        Map<Statistic, Double> stat = new HashMap<>();
        for (Map.Entry entry : stats.entrySet()) {
            if ( (Boolean)entry.getValue()) {
                stat.put(((Statistic)entry.getKey()), 1.0);
            } else {
                stat.put(((Statistic)entry.getKey()), 0.0);
            }
        }

        stat.put(Statistic.NUMBER_OF_RUNS, 1.0);

        TexasScoreManager.addToStatContainer(stat);
    }
}
