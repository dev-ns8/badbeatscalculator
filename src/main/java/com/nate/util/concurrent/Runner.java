package com.nate.util.concurrent;

import com.nate.model.entities.Dealer;
import com.nate.model.enums.Card;
import com.nate.structure.Pair;
import com.nate.util.scoring.impl.TexasScoreUtil;
import com.nate.util.scoring.impl.TexasScoreManager;

import java.util.Set;

import static com.nate.util.concurrent.Util.handleBoard;
import static com.nate.util.concurrent.Util.handleHole;

public class Runner implements Runnable {

    private static boolean isNull = false;
    private static boolean secondNull = false;

    private Pair<Card> firstGlobal;
    private Pair<Card> secondGlobal;
    private final Set<Card> boardGlobal;

    private Runner(Pair<Card> first, Pair<Card> second, Set<Card> board) {
        firstGlobal = first;
        secondGlobal = second;
        boardGlobal = board;
    }

    public static Runner create(Pair<Card> f, Pair<Card> s, Set<Card> b) {
        if (f == null) {
            isNull = true;
        }
        if (s == null) {
            secondNull = true;
        }
        return new Runner(f, s, b);
    }

    @Override
    public void run() {
        runSim();
    }

    private void runSim() {
        Dealer dealer = Dealer.create();
        dealer.shuffle();
        Set<Card> first = handleHole(firstGlobal, dealer);
        firstGlobal = new Pair<>((Card)first.stream().toArray()[0], (Card)first.stream().toArray()[1]);
        Set<Card> second = handleHole(secondGlobal, dealer);
        secondGlobal = new Pair<>((Card)second.stream().toArray()[0], (Card)second.stream().toArray()[1]);
        Set<Card> board = handleBoard(boardGlobal, dealer);

        int result;

        try {
            result = TexasScoreUtil.compareHands(first, second, board);
        } catch (Exception e) {
            System.out.println("Generic simulation ERROR");
            System.out.println(e.getMessage());
            e.printStackTrace();
            return;
        }

        TexasScoreManager.logResults(result, firstGlobal, secondGlobal);
    }
}
