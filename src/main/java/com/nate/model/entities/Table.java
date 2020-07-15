package com.nate.model.entities;

import java.util.LinkedList;

public class Table {

    private LinkedList<Player> players = new LinkedList<>();
    private final Dealer dealer;

    private Table(Dealer dealer) {
        this.dealer = dealer;
    }

    public static Table create() {
        return new Table(new Dealer());
    }

//    public Player getNextPlayer() {
//        Iterator it = players.iterator();
//        if (it.hasNext()) {
//            return it.next();
//        }
//        // and if players is not null
//        // return the first player in the list
//    }

    public Table seatPlayer(Player player) {
        players.add(player);
        return this;
    }

    public Dealer getDealer() {
        return dealer;
    }

    public LinkedList<Player> getPlayers() {
        return players;
    }

//    public Table dealHands() {
//        for (Player player : players) {
//            dealer.dealCard()
//
//        }
//        dealer.
//
//    }
}
