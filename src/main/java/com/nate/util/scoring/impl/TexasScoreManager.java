package com.nate.util.scoring.impl;

import com.nate.model.entities.stats.EquityData;
import com.nate.model.entities.stats.FlopData;
import com.nate.model.entities.stats.KeyedHand;
import com.nate.model.entities.stats.Statistic;
import com.nate.model.enums.Card;
import com.nate.structure.Pair;
import com.nate.util.concurrent.FlopStatsRunner;
import com.nate.util.concurrent.Runner;
import com.nate.util.scoring.ScoreManager;

import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;

import static com.nate.model.entities.SimResults.*;

public class TexasScoreManager extends ScoreManager {

    private final static int ITERATIONS = 10000;

    private static Map<String, Integer> results = new ConcurrentHashMap<>();
    private static Map<Pair<Card>, Integer> equityData = new ConcurrentHashMap<>();
    private static AtomicInteger TIE = new AtomicInteger(0);
    private static Map<Statistic, Integer> flopStats = new ConcurrentHashMap<>();
    public static volatile int counter = 0;

    public static TexasScoreManager create() {
        return new TexasScoreManager();
    }

    public static TexasScoreManager create(List<Pair<Card>> hands) {
        return new TexasScoreManager();
    }

    public static void addKey(Pair<Card> key) {
        equityData.put(key, 0);
    }

    public static EquityData twoHandEquity(Pair<Card> first, Pair<Card> second, Set<Card> board) {

        ExecutorService pool = Executors.newFixedThreadPool(Runtime.getRuntime().availableProcessors());

        for (int i = 0; i < ITERATIONS; i++) {
            try {
                Runner run = Runner.create(first, second, board);
                pool.submit(run);
            } catch (Exception e) {
                System.out.println("ERROR!!!! Probably Executor????");
            }
        }

        pool.shutdown();

        try {
            pool.awaitTermination(Long.MAX_VALUE, TimeUnit.NANOSECONDS);
        } catch (InterruptedException e) {
            System.out.println("getFlopStats() :: Interrupted Exception");
            e.printStackTrace();
        }

        return compileEquityData(equityData, TIE.intValue());
    }

    public static FlopData getFlopStats(Pair<Card> hand) {

        ExecutorService pool = Executors.newFixedThreadPool(Runtime.getRuntime().availableProcessors());

        initFlopStats();
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

        pool.shutdown();

        try {
            pool.awaitTermination(Long.MAX_VALUE, TimeUnit.NANOSECONDS);
        } catch (InterruptedException e) {
            System.out.println("getFlopStats() :: Interrupted Exception");
            e.printStackTrace();
        }
        System.out.println("hello");

        return calcResults(hand, flopStats);
    }

    private static void initFlopStats() {
        for (Statistic stat : Statistic.values()) {
            flopStats.put(stat, 0);
        }
    }

    public static void initEquityMap(Pair<Card> first, Pair<Card> second) {
        equityData.put(first, 0);
        equityData.put(second, 0);
    }

    public static synchronized void updateCounter() {
        counter = counter + 1;
    }

    private static synchronized void updateTie() {
        TIE.getAndIncrement();
    }

    public static synchronized void logResults(int result, Pair<Card> first, Pair<Card> second) {
        if (result < 0) {
            equityData.compute(first, (k,v) ->  (v == null) ? 1 : 1 + v);
        } else if (result > 0) {
            equityData.compute(second, (k,v) -> (v == null) ? 1 : 1 + v);
        } else {
            updateTie();
        }
    }

    public static void printFlopStats() {
        System.out.println("print results");
    }

    public static synchronized void addToStatContainer(Map<Statistic, Integer> stats) {
        stats.forEach((key, value) -> {
            if (value > 0) {
                flopStats.compute(key,  (k,v) -> v + 1);
            }
        });
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

