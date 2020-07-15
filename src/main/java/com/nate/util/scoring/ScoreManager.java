package com.nate.util.scoring;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * Class will aggregate scores (Stats + equities?) of hands.  This means it will take either
 * requests that include a starting hand, a list of starting hands, or a
 * list of starting hands and a variably completed list of board cards.
 *
 */

public abstract class ScoreManager {


    abstract void handleResult();

    private void submitJob(Runnable runable, int iterations) {

        ExecutorService pool = Executors.newFixedThreadPool(Runtime.getRuntime().availableProcessors());

        for (int i = 0; i < iterations; i++) {
//            try {
//                Runnable thread = runable.create();
//
//            } catch () {
//
//            }

        }

    }



}
