package com.nate.util.concurrent;

import com.nate.model.entities.Dealer;
import com.nate.model.entities.stats.Statistic;
import com.nate.model.enums.Card;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

public class Util {

    static Set<Card> handleBoard(Set<Card> curr, Dealer dealer) {
        if (curr == null) {
            Set<Card> r = new HashSet<>();
            r.addAll(dealer.flop());
            r.add(dealer.dealCard());
            r.add(dealer.dealCard());
            return r;
        } else {
            Set<Card> r = new HashSet<>(curr);
            if (r.size() < 5) {
                while (r.size() < 5) {
                    Card card = dealer.dealCard();
                    r.add(card);
                }
            } else {
                dealer.getCards(r);
            }
            return r;
        }
    }

    static Set<Card> randomFlop(Dealer dealer) {
        Set<Card> r = new HashSet<>();
        r.addAll(dealer.flop());
        if (r.size() != 3) {
            throw new IllegalStateException("randomFlop() :: Did not find 3 cards");
        }
        return r;
    }

    static Map<Statistic, Double> combine (Map<Statistic, Double> first, Map<Statistic, Boolean> second) {
        Map<Statistic, Double> r = new HashMap<>();
        for (Map.Entry entry : second.entrySet()) {
            Statistic key = (Statistic) entry.getKey();
            Boolean val = (boolean)entry.getValue();
            if (r.containsKey(key)) {
                if (val) {
                    r.put(key, first.get(key) + 1);
                }
            } else {
                r.put(key, 0.0);
            }
        }

        return r;
    }

    static Set<Card> handleHole(Set<Card> curr, Dealer dealer) {
        if (curr == null) {
            Set<Card> r = new HashSet<>();
            r.add(dealer.dealCard());
            r.add(dealer.dealCard());
            return r;
        } else {
            Set<Card> copy = new HashSet<>(curr);
            if (copy.size() < 2) {
                while (copy.size() < 2) {
                    dealer.getCards(copy);
                    copy.add(dealer.dealCard());
                }
            } else {
                dealer.getCards(copy);
            }
            return copy;
        }
    }
}
