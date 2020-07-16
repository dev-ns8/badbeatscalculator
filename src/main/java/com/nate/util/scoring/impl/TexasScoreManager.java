package com.nate.util.scoring.impl;

import com.nate.model.entities.stats.Statistic;
import com.nate.model.enums.Card;
import com.nate.structure.Pair;
import com.nate.util.concurrent.FlopStatsRunner;
import com.nate.util.concurrent.Runner;
import com.nate.util.scoring.ScoreManager;

import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import static com.nate.model.entities.SimResults.*;

public class TexasScoreManager extends ScoreManager {

    private final static int ITERATIONS = 1000;

    private static Map<String, Integer> results = new ConcurrentHashMap<>();
    private static Map<Statistic, Double> flopStats = new ConcurrentHashMap<>();
    public static volatile int counter = 0;

    static {
        results.put(HOLE_ONE, 0);
        results.put(HOLE_TWO, 0);
        results.put(TIE, 0);
    }

    public static TexasScoreManager create() {
        return new TexasScoreManager();
    }

    public static void simulateConcurrently(Set<Card> f, Set<Card> s, Set<Card> b) {

        ExecutorService pool = Executors.newFixedThreadPool(Runtime.getRuntime().availableProcessors());

        for (int i = 0; i < ITERATIONS; i++) {
            try {
                Runner run = Runner.create(f, s, b);
                pool.submit(run);
            } catch (Exception e) {
                System.out.println("ERROR!!!! Probably Executor????");
            }
        }

        handleResults(pool, DataType.EQUITY);
    }

    public static void twoHandEquity(Pair<Card> first, Pair<Card> second, Set<Card> board) {

        ExecutorService pool = Executors.newFixedThreadPool(Runtime.getRuntime().availableProcessors());

        for (int i = 0; i < ITERATIONS; i++) {
            try {
                Runner run = Runner.create(first.toSet(), second.toSet(), board);
                pool.submit(run);
            } catch (Exception e) {
                System.out.println("ERROR!!!! Probably Executor????");
            }
        }

        handleResults(pool, DataType.EQUITY);
    }

    public static void getFlopStats(Pair<Card> hand) {

        ExecutorService pool = Executors.newFixedThreadPool(Runtime.getRuntime().availableProcessors());

        for (int i = 0; i < ITERATIONS; i++) {
            try {
                FlopStatsRunner runner = FlopStatsRunner.create(hand);
                pool.submit(runner);
            } catch (Exception e) {
                System.out.println("ERROR:: FlopStatsRunner :: ");
                e.printStackTrace();
                throw new IllegalStateException("Error with FlopStatsRunner");
            }
        }

        handleResults(pool, DataType.FLOP);
    }

    public static synchronized void updateCounter() {
        counter = counter + 1;
    }

    public static synchronized int getCurr(String key) {
        return results.get(key);
    }

    public static void logResults(int result) {
        if (result < 0) {
            results.compute(HOLE_ONE, (k,v) -> results.get(k) + 1);
        } else if (result > 0) {
            results.compute(HOLE_TWO, (k,v) -> results.get(k) + 1);
        } else {
            results.compute(TIE, (k,v) -> results.get(k) + 1);
        }
    }

    public static void printFlopStats() {
        System.out.println("print results");
    }

    public static void addToStatContainer(Map<Statistic, Double> stats) {
        System.out.println("done");
        stats.forEach((key, value) -> {
            flopStats.compute(key, (k, val) -> {
                if (val == null) {
                    return value;
                }
                if (value.doubleValue() > 0.0) {
                    return val + 1.0;
                } else {
                    return val;
                }
            });
        });
        System.out.println("done");
    }

    private static synchronized int getCounter() {
        return counter;
    }

    public static Map<String, Integer> getResults() {
        return results;
    }

    public static synchronized void printResults(Map<String, Integer> winLosses) {
        int total = winLosses.get(HOLE_ONE) + winLosses.get(HOLE_TWO) + winLosses.get(TIE);
        System.out.println("Hands run: " + total);
        System.out.println("SecondHand wins: " + winLosses.get(HOLE_TWO));
        System.out.println("FirstHand wins: " + winLosses.get(HOLE_ONE));
        System.out.println("Ties: " + winLosses.get(TIE));

    }
}

