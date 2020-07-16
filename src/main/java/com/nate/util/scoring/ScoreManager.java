package com.nate.util.scoring;

import com.nate.util.scoring.impl.DataType;
import com.nate.util.scoring.impl.TexasScoreManager;

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
