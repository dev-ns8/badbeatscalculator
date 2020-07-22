package com.nate.util.scoring;

import com.nate.model.entities.stats.FlopData;
import com.nate.model.entities.stats.KeyedHand;
import com.nate.model.entities.stats.Statistic;
import com.nate.model.enums.Card;
import com.nate.structure.Pair;
import com.nate.util.scoring.impl.DataType;
import com.nate.util.scoring.impl.TexasScoreManager;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.TimeUnit;

import static com.nate.util.scoring.impl.TexasScoreManager.getResults;

/**
 * Class will aggregate scores (Stats + equities?) of hands.  This means it will take either
 * requests that include a starting hand, a list of starting hands, or a
 * list of starting hands and a variably completed list of board cards.
 *
 */

public abstract class ScoreManager {

    public static KeyedHand calcResults(Pair<Card> hand, Map<Statistic, Integer> stats) {
        int totalRuns = stats.get(Statistic.NUMBER_OF_RUNS);
        Map<Statistic, Double> real = new HashMap<>();
        for (Map.Entry entry : stats.entrySet()) {
            double found = (int)entry.getValue();
            double percent;
            if (found > 0) {
                percent = (found * 100) / totalRuns;
            } else {
                percent = 0.0;
            }

            real.put((Statistic) entry.getKey(), percent);
        }
        KeyedHand keyedHand = KeyedHand.of(hand.getFirst(), hand.getSecond(), FlopData.of(real));
        return keyedHand;
    }

    public static void handleResults(ExecutorService pool, DataType type) {

        pool.shutdown();

        try {

            pool.awaitTermination(Long.MAX_VALUE, TimeUnit.NANOSECONDS);

            if (type.equals(DataType.FLOP)) {
                TexasScoreManager.printFlopStats();
            } else if (type.equals(DataType.EQUITY)) {
                TexasScoreManager.printResults(getResults());
            }
        } catch (InterruptedException e) {
            System.out.println("InterruptedException :: ScoreManager :: RequestType: " + type);
            e.printStackTrace();
        }

    }
}
