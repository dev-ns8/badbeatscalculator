package com.nate.util.concurrent;

import com.nate.model.entities.Dealer;
import com.nate.model.enums.Card;
import com.nate.util.scoring.impl.TexasScoreUtil;
import com.nate.util.scoring.impl.TexasScoreManager;

import java.util.Set;

import static com.nate.util.concurrent.Util.handleBoard;
import static com.nate.util.concurrent.Util.handleHole;

public class Runner implements Runnable {

    private final Set<Card> firstGlobal;
    private final Set<Card> secondGlobal;
    private final Set<Card> boardGlobal;

    private Runner(Set<Card> first, Set<Card> second, Set<Card> board) {
        firstGlobal = first;
        secondGlobal = second;
        boardGlobal = board;
    }

    public static Runner create(Set<Card> f, Set<Card> s, Set<Card> b) {
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
        Set<Card> second = handleHole(secondGlobal, dealer);
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

        TexasScoreManager.logResults(result);
        TexasScoreManager.updateCounter();
    }
}
