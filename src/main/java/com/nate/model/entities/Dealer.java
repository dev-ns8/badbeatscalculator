package com.nate.model.entities;

import com.nate.model.enums.Card;
import com.nate.structure.Pair;
import net.jcip.annotations.NotThreadSafe;

import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@NotThreadSafe
public class Dealer {

    private Queue<Card> deck = new LinkedList<>();
    private int pot = 0;

    Dealer() {
        List<Card> cards = Arrays.asList(Card.values());
        Collections.shuffle(cards);
        deck.addAll(cards);
    }

    public static Dealer create() {
        return new Dealer();
    }

    public Set<Card> getCards(Set<Card> retreive) {
        if (retreive == null) {
            return null;
        }
        Set<Card> r = new HashSet<>(retreive);
        deck.removeAll(retreive);
//        for (Card card : deck)  {
//            if (retreive.contains(card)) {
//                r.add(card);
//                deck.remove(card);
//            }
//        }
        return r;
    }

    public void shuffle() {
        List<Card> deckList = deck.stream().collect(Collectors.toList());
        Collections.shuffle(deckList);
        deck = new LinkedList<>(deckList);
    }

    public void removeCards(List<Card> cards) {
        deck.removeAll(cards);
    }

    public void newDeck() {
        List<Card> cards = Arrays.asList(Card.values());
        Collections.shuffle(cards);
        this.deck = new LinkedList<>();
        this.deck.addAll(cards);
    }

    public Queue<Card> getDeck() {
        return deck;
    }

    public Queue<Card> burn() {
        deck.poll();
        return deck;
    }

    public static Set<Pair<Card>> getAllPairs() {
        Set<Card> cards = Stream.of(Card.values()).collect(Collectors.toSet());
        Set<Pair<Card>> hands = new HashSet<>();
        for (int i = 0; i < 52; i++) {
            for (int j = 1; j < 52; j++) {
                if (cards.toArray()[i].equals(cards.toArray()[j])) {
                    continue;
                } else {
                    Pair<Card> hand = new Pair<>((Card)cards.toArray()[i], (Card)cards.toArray()[j]);
                    if (hands.contains(hand)) {
                        System.out.println("Ever get here??");
                        continue;
                    }
                    hands.add(hand);
                }
            }
        }
        return hands;
    }

    public void loadDeck(List<Card> cards) {
        deck.removeAll(cards);
    }

    public List<Card> dealBoard() {
        List<Card> board = new ArrayList<>();
        Set<Card> flop = flop();
        board.addAll(new ArrayList<>(flop));
        Card turn = dealCard();
        board.add(turn);
        Card river = dealCard();
        board.add(river);
        return board;
    }

    public Set<Card> getCardsFromDeck(Set<Card> cards) {
        Set<Card> r = new HashSet<>();
        for (Card card : cards) {
            r.add(getCardFromDeck(card));
        }
        return r;
    }

    public Card getCardFromDeck(Card card) {
        deck.remove(card);
        return card;
    }

    public Set<Card> dealHoleCards() {
        Set<Card> hole = new HashSet<>();
        hole.add(dealHandFirstCard());
        hole.add(dealHandSecondCard());
        return hole;
    }

    public Card dealHandFirstCard() {
        return deck.poll();
    }

    public Card dealHandSecondCard() {
        return deck.poll();
    }

    public Card dealCardForHand() {
        return deck.poll();
    }

    public void dealTable(List<Player> players) {
        int rounds = 0;
        while (rounds < 2) {
            for (Player player : players) {
                player.collectCard(dealCardForHand());
            }
            rounds++;
        }
    }

    public Set<Card> flop() {
        Set<Card> r = new HashSet<>();
        r.add(deck.poll());
        r.add(deck.poll());
        r.add(deck.poll());
        return r;
    }

    public Card dealCard() {
        deck = burn();
        return deck.poll();
    }

    public boolean canDealFullHand(int players) {
        int cardsNeeded = (players * 2) + 4;
        if (deck.size() > cardsNeeded) {
            return true;
        }
        return false;
    }

    public void clearPot() {
        pot = 0;
    }

    public void bet(int amount) {
        pot = pot + amount;
    }
}
