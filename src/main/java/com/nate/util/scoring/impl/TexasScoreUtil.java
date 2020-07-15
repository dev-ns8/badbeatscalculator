package com.nate.util.scoring.impl;

import com.nate.model.entities.CardComparator;
import com.nate.model.entities.exceptions.CardsNotFoundException;
import com.nate.model.entities.exceptions.SplitHandLengthException;
import com.nate.model.entities.stats.Statistic;
import com.nate.model.enums.Card;
import com.nate.model.enums.MadeHand;
import com.nate.model.enums.Suit;
import com.nate.util.scoring.Hand;

import java.util.*;
import java.util.stream.Collectors;

public final class TexasScoreUtil {

    private TexasScoreUtil() {
    }

    /**
     * Compares two hands returning int which denotes the better hand or 0
     * in case of tie
     *
     *
     * @param first {@link Set} first hand
     * @param second {@link Set} second hand
     * @param board {@link Set} board
     *
     * @return -1 if firstHand > secondHand
     * 0 if firstHand == secondHand
     * 1 if firstHand < secondHand
     */
    public static int compareHands(Set<Card> first, Set<Card> second, Set<Card> board) throws Exception {
        Set<Card> firstSort = new HashSet<>();
        Set<Card> secondSort = new HashSet<>();
        firstSort.addAll(first);
        firstSort.addAll(board);
        secondSort.addAll(second);
        secondSort.addAll(board);

        Set<Card> firstFive = sevenToBestFive(firstSort);
        Set<Card> secondFive = sevenToBestFive(secondSort);

        return compareHands(firstFive, secondFive);
    }

    public static int score(Set<Card> cards) {
        return scoreHand(new ArrayList<>(cards));
    }

    /**
     * Want a method that captures the winning hand and the history about the hand
     *      - How did it flop?
     *      - How did hand two flop?
     *      - Who won the hand?
     *      - What was the winning 5 cards?
     *      - What is the name of winning 5?
     *
     *
     * @param {@link} Hand 5 Card Hand
     * @return
     */
    public static Map<Statistic, Boolean> flopStats(Hand flop) {
        Map<Statistic, Boolean> results = new TreeMap<>(Collections.reverseOrder());
        int score = scoreHand(flop.toList());

        /**
         * Checks to perform:
         *
         * 1 quads
         * 2 fullHouse
         * 3 flush
         * 4 straight
         * 5 set
         * 6 two pair
         *
         * TODO::
         * 7 overPair
         * 8 middlePair
         * 9 lowPair
         * 10 aceHigh
         * 11 noHand
         * 12 flushDraw
         * 13 nfd
         * 14 oesd
         * 15 numberOfRuns
         */
        results.put(Statistic.QUADS, isFourOfAKind(flop.toSet()));
        results.put(Statistic.FULL_HOUSE, isFullHouse(flop.toSet()));
        results.put(Statistic.FLUSH, isFlush(flop.toList()));
        results.put(Statistic.STRAIGHT, isStraight(flop.toList(), flop.getHandSize()-1));
        results.put(Statistic.SET, isThreeOfAkind(flop.toSet()));
        results.put(Statistic.TWO_PAIR, isTwoPair(flop.toSet()));
        if (isTwoPair(flop.toSet())) {
            results.put(Statistic.TWO_PAIR, true);
            List<Card> sorted = splitHand(flop.toSet(), MadeHand.TWO_PAIR.value); // Should return 3 cards first two being pairs
            Card nonPair = sorted.get(sorted.size() -1);
            if (sorted.get(0).value > nonPair.value) {
                results.put(Statistic.OVER_PAIR, true);
                if (sorted.get(1).value > nonPair.value) {
                    results.put(Statistic.MIDDLE_PAIR, true);
                } else {
                    results.put(Statistic.LOW_PAIR, true);
                }
            } else {
                results.put(Statistic.LOW_PAIR, true);
            }
        } else if (isPair(flop.toSet())) {
            List<Card> sorted = splitHand(flop.toSet(), MadeHand.PAIR.value); // first 2 elements paired, next 3 elements un-paired
            if (sorted.get(0).value >= sorted.get(1).value) {
                results.put(Statistic.OVER_PAIR, true);
            } else if (sorted.get(0).value > sorted.get(sorted.size()-1).value) {
                results.put(Statistic.MIDDLE_PAIR, true);
            } else {
                results.put(Statistic.LOW_PAIR, true);
            }
        } else {
            // No pair
            List<Card> sorted = splitHand(flop.toSet(), MadeHand.ACE_HIGH.value);
            if (sorted.get(0).value == Card.ACE_CLUB.value) {
                results.put(Statistic.ACE_HIGH, true);
            }
            results.put(Statistic.FLUSH_DRAW, hasFlushDraw(flop.toSet()));
            results.put(Statistic.NUT_FLUSH_DRAW, hasNutFlushDraw(flop.toSet()));
            results.put(Statistic.OPEN_END_DRAW, hasOESD(flop.toSet()));
            /** calculate if every check from above returned false and if so, add a "true" to
             *  {@Code results} for "no made hand"
             */


        }
        return results;
    }

    private static boolean hasOESD(Set<Card> cards) {
        if (isStraight(cards.stream().collect(Collectors.toList()), 4)) {
            //TODO:: Possible to have an OESD and a Gutshot Straight draw
            return true;
        }
        return false;
    }

    private static boolean hasFlushDraw(Set<Card> cards) {
        HashMap<Suit, Integer> suits = new HashMap<>();
        boolean hasDraw = false;
        for (Card card : cards) {
            if (suits.containsKey(card.suit)) {
                int numSeen = suits.get(card.suit);
                if (numSeen == 3) {
                    hasDraw = true;
                    break;
                }
                suits.put(card.suit, numSeen + 1);
            } else {
                suits.put(card.suit, 1);
            }
        }

        return hasDraw;
    }

    private static boolean hasNutFlushDraw(Set<Card> cards) {
        if (hasFlushDraw(cards)) {
            return hasAce(cards);
        }
        return false;
    }

    private static boolean hasAce(Set<Card> cards) {
        for (Card card : cards) {
            if (card.value == Card.ACE_CLUB.value) {
                return true;
            }
        }
        return false;
    }

    private static int compareHands(Set<Card> firstFive, Set<Card> secondFive) throws Exception {
        if (firstFive.size() != 5 || secondFive.size() != 5) {
            return 100;
        }
        int firstScore = scoreHand(new ArrayList<>(firstFive));
        int secondScore = scoreHand(new ArrayList<>(secondFive));
        if (firstScore == secondScore) {
            Set<Card> sortedFirst = new TreeSet<>(new CardComparator());
            Set<Card> sortedSecond = new TreeSet<>(new CardComparator());
            sortedFirst.addAll(firstFive);
            sortedSecond.addAll(secondFive);
            switch (firstScore) {
                case 22:
                    return 0;
                case 21:
                    return handleHigherStraight(sortedFirst, sortedSecond);
                case 20:
                    return handleHigherFour(sortedFirst, sortedSecond);
                case 19:
                    return handleHigherBoat(sortedFirst, sortedSecond);
                case 18:
                    return handleHigherFlush(sortedFirst, sortedSecond);
                case 17:
                    return handleHigherStraight(sortedFirst, sortedSecond);
                case 16:
                    return handleHigherSet(sortedFirst, sortedSecond);
                case 15:
                    return handleHigherTwoPair(sortedFirst, sortedSecond);
                case 14:
                    return handleHigherPair(sortedFirst, sortedSecond);
                default:
                    return handleHighCard(sortedFirst, sortedSecond);
            }
        } else if (firstScore > secondScore) {
            return -1;
        } else {
            return 1;
        }
    }

    private static Set<Card> makeFiveFromFourKind(Set<Card> workSeven) {
        Set<Card> seven = new TreeSet<>(workSeven);
        Set<Card> five = new HashSet<>();
        try {
            Set<Card> four = getFourKind(seven);
            five.addAll(four);
            seven.removeAll(four);
            Card max = findMax(seven);
            five.add(max);
        } catch (CardsNotFoundException e) {
            System.out.println("ERROR:: makeFiveFromFourKind()");
        }
        return five;
    }

    private static Set<Card> makeFiveFromThreeKind(Set<Card> workSeven) {
        List<Card> remaining = new ArrayList<>(workSeven);
        List<Card> five = new ArrayList<>();
        try {
            Set<Card> three = getSet(new HashSet<>(remaining));
            remaining.removeAll(three);
            five.addAll(three);
            Card max = findMax(new HashSet<>(remaining));
            remaining.remove(max);
            Card min = findMax(new HashSet<>(remaining));
            five.add(max);
            five.add(min);
        } catch (CardsNotFoundException e) {
            System.out.println("ERROR:: makeFiveFromThreeKind()");
        }
        return new HashSet<>(five);
    }

    // 9 7 4 Diamonds----- ACE club ACE HEART 7 8 Diamond / Heart

    private static Set<Card> getBoat(Set<Card> workSeven) {
        Set<Card> seven = new TreeSet<>(workSeven);
        Set<Card> five = new HashSet<>();
        try {
            Set<Card> three = getSet(seven);
            seven.removeAll(three);
            Set<Card> two = getPair(seven);
            five.addAll(three);
            five.addAll(two);
        } catch (CardsNotFoundException e) {
            System.out.println("ERROR:: getBoat()");
        }
        return five;
    }

    private static Set<Card> getFlush(Set<Card> seven) {
        EnumMap<Suit, Set<Card>> flush = new EnumMap<>(Suit.class);
        for (Card card : seven) {
            if (flush.containsKey(card.suit)) {
                Set<Card> cards = flush.get(card.suit);
                cards.add(card);
                flush.put(card.suit, cards);
            } else {
                Set<Card> cards = new HashSet<>();
                cards.add(card);
                flush.put(card.suit, cards);
            }
        }

        for (Map.Entry entry : flush.entrySet()) {
            if (((Set<Card>)entry.getValue()).size() == 5) {
                return (Set<Card>)entry.getValue();
            } else if (((Set<Card>)entry.getValue()).size() > 5) {
                Set<Card> highFlush = new TreeSet<>(new CardComparator());
                highFlush.addAll((Set<Card>)entry.getValue());
                Set<Card> returnSet = new TreeSet<>(new CardComparator());
                int index = 0;
                for (Card card : highFlush) {
                    if (index == 5) {
                        return returnSet;
                    }
                    returnSet.add(card);
                    index++;
                }
            }
        }
        return null;
    }

    private static Set<Card> makeFiveFromTwoPair(Set<Card> workSeven) {
        Set<Card> seven = new TreeSet<>(workSeven);
        Set<Card> five = new TreeSet<>(new CardComparator());
        try {
            Set<Card> firstPair = getPair(seven);
            seven.removeAll(firstPair);
            five.addAll(firstPair);
            Set<Card> secondPair = getPair(seven);
            five.addAll(secondPair);
            seven.removeAll(secondPair);
            Card kick = findMax(seven);
            five.add(kick);
        } catch (CardsNotFoundException e) {
            System.out.println("ERROR:: makeFiveFromTwoPair()");
        }
        return five;
    }

    private static Set<Card> makeFiveFromPair(Set<Card> workSeven) {
        Set<Card> seven = new TreeSet<>(workSeven);
        Set<Card> five = new TreeSet<>(new CardComparator());
        try {
            Set<Card> pair = getPair(seven);
            seven.removeAll(pair);
            five.addAll(pair);
            Card max = findMax(seven);
            seven.remove(max);
            five.add(max);
            max = findMax(seven);
            seven.remove(max);
            five.add(max);
            max = findMax(seven);
            five.add(max);
        } catch (CardsNotFoundException e) {
            System.out.println("ERROR:: makeFiveFromPair()");
        }
        return five;
    }

    private static Set<Card> highestFive(Set<Card> seven) {
        Set<Card> five = new TreeSet<>(new CardComparator());
        int index = 0;
        for (Card card : seven) {
            if (index == 5)  {
                break;
            }
            five.add(card);
            index++;
        }
        return five;
    }

    private static Set<Card> sevenToBestFive(Set<Card> seven) {
        Set<Card> sevenHand = new TreeSet<>(new CardComparator());
        sevenHand.addAll(seven);
        int score = scoreHand(new ArrayList<>(sevenHand));
        Set<Card> five;
        switch (score) {
            case 22:
                five = getStraight(sevenHand);
                break;
            case 21:
                five = getStraight(sevenHand);
                break;
            case 20:
                five = makeFiveFromFourKind(sevenHand);
                break;
            case 19:
                five = getBoat(sevenHand);
                break;
            case 18:
                five = getFlush(sevenHand);
                break;
            case 17:
                five = getStraight(sevenHand);
                break;
            case 16:
                five = makeFiveFromThreeKind(sevenHand);
                break;
            case 15:
                five = makeFiveFromTwoPair(sevenHand);
                break;
            case 14:
                five = makeFiveFromPair(sevenHand);
                break;
            default:
                five = highestFive(sevenHand);
        }
        if (five == null) {
            System.out.println("Seven: " + Arrays.toString(sevenHand.toArray()));
        }
        return five;
    }

    private static boolean suitsMatch(List<Card> list) {
        Suit suit = list.get(0).suit;
        for (int i = 1; i < list.size()-1; i++) {
            if (!list.get(i).suit.equals(suit)) {
                return false;
            }
        }
        return true;
    }

    private static int handleHigherStraight(Set<Card> first, Set<Card> second) {
        Card firstHigh = (Card)first.toArray()[0];
        Card secondHigh = (Card)second.toArray()[0];
        return Integer.compare(secondHigh.value, firstHigh.value);
    }

    private static int handleHigherFour(Set<Card> first, Set<Card> second) throws SplitHandLengthException {
        Card firstFour;
        Card secondFour;
        Card firstFifth;
        Card secondFifth;

        List<Card> firstArr = splitHand(first, 20);
        List<Card> secondArr = splitHand(second, 20);

        checkSplitHand(firstArr, MadeHand.FOUR_KIND);
        checkSplitHand(secondArr, MadeHand.FOUR_KIND);

        firstFour = firstArr.get(0);
        firstFifth = firstArr.get(1);
        secondFifth = secondArr.get(1);
        secondFour = secondArr.get(0);

        return handleReturnValue(firstFour.value, firstFifth.value, secondFour.value, secondFifth.value);
    }

    private static int handleReturnValue(int firstValZero,int firstValOne, int secondValZero, int secondValOne) {
        if (firstValZero == secondValZero) {
            return Integer.compare(secondValOne, firstValOne);
        } else if (firstValZero > secondValZero) {
            return -1;
        } else {
            return 1;
        }
    }

    private static int handleHigherSet(Set<Card> first, Set<Card> second) throws Exception {
        if (first.size() != 5 || second.size() != 5) {
            System.out.println("ddd");
        }
        List<Card> firstArr = splitHand(first, 16);
        List<Card> secondArr = splitHand(second, 16);
        checkSplitHand(firstArr, MadeHand.THREE_KIND);
        checkSplitHand(secondArr, MadeHand.THREE_KIND);
        return iterateForReturn(firstArr, secondArr);
    }

    private static void checkSplitHand(List<Card> hand, MadeHand type) throws SplitHandLengthException {
        if (hand == null) {
            throw SplitHandLengthException.error(null, type);
        }
        switch (type.value) {
            case 22:
            case 21:
            case 18:
            case 17:
                if (hand.size() != 5) {
                    throw SplitHandLengthException.error(hand, type);
                }
                break;
            case 14:
                if (hand.size() != 4) {
                    throw SplitHandLengthException.error(hand, type);
                }
                break;
            case 15:
            case 16:
                if (hand.size() != 3) {
                    throw SplitHandLengthException.error(hand, type);
                }
                break;
            case 20:
            case 19:
                if (hand.size() != 2) {
                    throw SplitHandLengthException.error(hand, type);
                }
                break;
            default:
                if (hand.size() != 5) {
                    throw SplitHandLengthException.error(hand, type);
                }
        }
    }

    private static int iterateForReturn(List<Card> first, List<Card> second) {
        if (first.size()!= second.size()) {
            return 100;
        }
        for (int i = 0; i < first.size(); i++) {
            if (first.get(i).value > second.get(i).value) {
                return -1;
            } else if (first.get(i).value < second.get(i).value) {
                return 1;
            } else if (i == first.size()) {
                return 0;
            }
        }
        return 0;
    }

    private static int handleHigherBoat(Set<Card> first, Set<Card> second) throws SplitHandLengthException {
        Card firstThree;
        Card firstTwo;
        Card secondThree;
        Card secondTwo;

        List<Card> firstArr = splitHand(first, 19);
        List<Card> secondArr = splitHand(second, 19);

        checkSplitHand(firstArr, MadeHand.FULL_HOUSE);
        checkSplitHand(secondArr, MadeHand.FULL_HOUSE);

        firstThree = firstArr.get(0);
        firstTwo = firstArr.get(1);
        secondThree = secondArr.get(0);
        secondTwo = secondArr.get(1);

        return handleReturnValue(firstThree.value, firstTwo.value, secondThree.value, secondTwo.value);
    }

    private static int handleHigherFlush(Set<Card> first, Set<Card> second) {
        List<Card> firstList = new ArrayList<>(first);
        List<Card> secondList = new ArrayList<>(second);
        for (int i = 0; i < 5; i++) {
            if (firstList.get(i).value > secondList.get(i).value) {
                return -1;
            } else if (firstList.get(i).value < secondList.get(i).value) {
                return 1;
            }
        }
        return 0;
    }

    private static int handleHighCard(Set<Card> first, Set<Card> second) throws SplitHandLengthException {
        List<Card> firstArr = splitHand(first, 12);
        List<Card> secondArr = splitHand(second, 12);

        checkSplitHand(firstArr, MadeHand.ACE_HIGH);
        checkSplitHand(secondArr, MadeHand.ACE_HIGH);

        return iterateForReturn(firstArr, secondArr);

    }

   // king spade qc    As 3s
    //MadeHand 8c kingc villain Ah 10d  Board:   7c 9d  9s

    private static int handleHigherPair(Set<Card> first, Set<Card> second) throws SplitHandLengthException {
        List<Card> firstArr = splitHand(first, 14);
        List<Card> secondArr = splitHand(second, 14);

        checkSplitHand(firstArr, MadeHand.PAIR);
        checkSplitHand(secondArr, MadeHand.PAIR);

        return iterateForReturn(firstArr, secondArr);
    }

    private static int handleHigherTwoPair(Set<Card> first, Set<Card> second) throws Exception {
        List<Card> firstArr = splitHand(first, 15);
        List<Card> secondArr = splitHand(second, 15);

        checkSplitHand(firstArr, MadeHand.TWO_PAIR);
        checkSplitHand(secondArr, MadeHand.TWO_PAIR);

        return iterateForReturn(firstArr, secondArr);
    }

    public static List<Card> testSplitHand(Set<Card> unsorted, int handType) {
        return splitHand(unsorted, handType);
    }

    /**
     * Method returns a List that acts like a decimal number. Ex:
     * 1.15 > 1.015.  The beginning of the return array has the most
     * significant cards in the first slots.
     *
     * Array with the most significant cards first.  Ex:
     * 4 of a kind: Ad, Ah, 5h, As, Ac -> returns [Ad, 5h]
     *
     * @return list {@link List}
     */
    private static List<Card> splitHand(Set<Card> unsorted, int handType) {
        List<Card> returnArr = new ArrayList<>();
        Set<Card> hand = new TreeSet<>(new CardComparator());
        hand.addAll(unsorted);
        Object[] arr = hand.toArray();
        if (MadeHand.getHand(handType).equals(MadeHand.FOUR_KIND)) {
            if (((Card)arr[0]).value == ((Card)arr[1]).value) {
                // highest cards are 4 of a kind
                returnArr.add(0, (Card)arr[0]);
                returnArr.add(1, (Card)arr[4]);
            } else {
                // bottom cards are 4 of a kind
                returnArr.add(0, (Card)arr[4]);
                returnArr.add(1, (Card)arr[0]);
            }
        } else if (MadeHand.getHand(handType).equals(MadeHand.FULL_HOUSE)) {
            if (((Card) arr[0]).value == ((Card) arr[1]).value) {
                // Top cards are the 3
                returnArr.add(0, (Card) arr[0]);
                returnArr.add(1, (Card) arr[3]);
            } else {
                // bottom cards are the 3
                returnArr.add(0, (Card) arr[3]);
                returnArr.add(1, (Card) arr[0]);
            }
        } else if (MadeHand.getHand(handType).equals(MadeHand.THREE_KIND)) {
            try {
                List<Card> remaining = new ArrayList<>(hand);
                Set<Card> three = getSet(hand);
                remaining.removeAll(three);
                returnArr.add(0, three.iterator().next());
                TreeSet<Card> sorted = new TreeSet<>(new CardComparator());
                sorted.addAll(remaining);
                Iterator<Card> sort = sorted.iterator();
                int index = 1;
                while (sort.hasNext()) {
                    if (index < 3) {
                        returnArr.add(index, sort.next());
                    } else {
                        break;
                    }
                    index++;
                }
            } catch (CardsNotFoundException e) {
                System.out.println("Error in splitHand() +566:: " + e.getMessage());
                return null;
            }
        } else if (MadeHand.getHand(handType).equals(MadeHand.TWO_PAIR)) {
            try {
                List<Card> remaining = new ArrayList<>(hand);
                Set<Card> firstPair = getPair(hand);
                remaining.removeAll(firstPair);
                Set<Card> secondPair = getPair(new HashSet<>(remaining));
                remaining.removeAll(secondPair);
                Card firstCard = firstPair.iterator().next();
                Card secondCard = secondPair.iterator().next();
                if (firstCard.value > secondCard.value) {
                    returnArr.add(0, firstCard);
                    returnArr.add(1, secondCard);
                } else {
                    returnArr.add(0, secondCard);
                    returnArr.add(1, firstCard);
                }
                Card max = findMax(new HashSet<>(remaining));
                returnArr.add(2, max);
            } catch (CardsNotFoundException e) {
                System.out.println("Error in splitHand() +588 :: " + e.getMessage());

            }
        } else if (MadeHand.getHand(handType).equals(MadeHand.PAIR)) {
            try {
                List<Card> remaining = new ArrayList<>(hand);
                Set<Card> pair = getPair(hand);
                remaining.removeAll(pair);
                returnArr.add(0, pair.iterator().next());
                Set<Card> ordered = new TreeSet<>(new CardComparator());
                ordered.addAll(remaining);
                int index = 1 ;
                Iterator it = ordered.iterator();
                while (it.hasNext()) {
                    if (index < 4) {
                        returnArr.add(index, (Card)it.next());
                    } else {
                        break;
                    }
                    index++;
                }
            } catch (CardsNotFoundException e) {
                System.out.println("Error in splitHand() +610 :: " + e.getMessage());
            }
        } else {
            int index = 0;
            Iterator it = hand.iterator();
            while (it.hasNext() && index < 5) {
                returnArr.add(index, (Card)it.next());
                index++;
            }
        }
        return returnArr;
    }
    // Ac Ks Ad Jd
    private static Card findMax(Set<Card> hand) {
        Iterator it = hand.iterator();
        Card max = (Card)it.next();
        while (it.hasNext()) {
            Card card = ((Card)it.next());
            if (card.value > max.value) {
                max = card;
            }
        }
        return max;
    }

    private static int scoreHand(List<Card> hand) {
        // if score is == 20 || score <= 16 we have to do some extra calc'ing / scoring
        Set<Card> handSet = new TreeSet<>(new CardComparator());
        handSet.addAll(hand);
        if (isRoyalFlush(hand)) {
            return 22;
        } else if (isStraightFlush(hand)) {
            return 21;
        } else if (isFourOfAKind(handSet)) {
            return 20;
        } else if (isFullHouse(handSet)) {
            return 19;
        } else if (isFlush(hand)) {
            return 18;
        } else if (isStraight(hand, 5)) {
            return 17;
        } else if (isThreeOfAkind(handSet)) {
            return 16 ;
        } else if (isTwoPair(handSet)) {
            return 15;
        } else if (isPair(handSet)) {
            return 14;
        } else {
            hand.sort((o1, o2) -> o2.value - o1.value);
            return hand.get(0).value;
        }
    }

    private static boolean isFlush(List<Card> hand) {
        EnumMap<Suit, Integer> suites = new EnumMap<>(Suit.class);
        for (Card card : hand) {
            if (suites.containsKey(card.suit)) {
                suites.put(card.suit, suites.get(card.suit) + 1);
            } else {
                suites.put(card.suit, 1);
            }
        }

        for (Integer num : suites.values()) {
            if (num == 5) {
                return true;
            }
        }
        return false;
    }

    private static boolean isWheelCard(Card card) {
        return card.value == 12 || card.value == 3 || card.value == 2 || card.value == 1 || card.value == 0;
    }

    private static boolean checkWheelStraight(List<Card> hand) {
        Map<Integer, Boolean> wheelStraight = new HashMap<>();
        for (Card card : hand) {
            if (isWheelCard(card)) {
                if (!wheelStraight.containsKey(card.value)) {
                    wheelStraight.put(card.value, true);
                }
            }
        }
        if (wheelStraight.values().size() != 5) {
            return false;
        }
        if (!wheelStraight.containsKey(0)) {
            return false;
        }
        if (!wheelStraight.containsKey(1)) {
            return false;
        }
        if (!wheelStraight.containsKey(2)) {
            return false;
        }
        if (!wheelStraight.containsKey(3)) {
            return false;
        }
        if (!wheelStraight.containsKey(12)) {
            return false;
        }
        return true;
    }

    private static boolean doesNotContainCardValue(Set<Card> set, Card card) {
        for (Card c : set) {
            if (c.value == card.value) {
                return false;
            }
        }
        return true;
    }

    private static Set<Card> getStraight(Set<Card> sevenHand) {
        Set<Card> straight = new TreeSet<>(new CardComparator());
        Iterator<Card> it = sevenHand.iterator();
        Card last = it.next();
        Card curr;
        int numOfConsecs = 0;
        // Ace present != wheel straight necessarily
        if (last.value == 12) {
            boolean wheel = checkWheelStraight(new ArrayList<>(sevenHand));
            if (wheel) {
                straight.add(last);
                while (it.hasNext()) {
                    curr = it.next();
                    if (curr.value <= 3 && doesNotContainCardValue(straight, curr)) {
                        straight.add(curr);
                    }
                }
                if (straight.size() == 5) {
                    return straight;
                }
            }
        }

        while (it.hasNext()) {
            curr = it.next();
            if (curr.value == last.value) {
                last = curr;
                continue;
            }
            if (curr.value + 1 == last.value) {
                numOfConsecs++;
                if (!straight.contains(last)) {
                    straight.add(last);
                }
                if (numOfConsecs == 4 && (curr.value + 1) == last.value) {
                    straight.add(curr);
                    return straight;
                }
            } else {
                numOfConsecs = 0;
                straight = new TreeSet<>(new CardComparator());
            }
            last = curr;
        }
        if (straight.size() == 5) {
            return straight;
        }
        return null;
    }

    private static boolean isStraight(List<Card> hand, int straightNum) {
        hand.sort((o1, o2) -> o2.value - o1.value);
        if (hand.get(0).value == 12) {
            if (checkWheelStraight(hand)) {
                return true;
            }
        }

        int numOfConsecs = 0;
        Card last = hand.get(0);
        for (Card card : hand) {
            if (card.value == last.value) {
                continue;
            }
            if (card.value + 1 == last.value) {
                numOfConsecs++;
                last = card;
            } else {
                numOfConsecs = 0;
                last = card;
            }

            if (numOfConsecs == straightNum - 1) {
                return true;
            }
        }
        return false;
    }

    private static boolean isTopStraight(List<Card> hand) {
        hand.sort((o1, o2) -> o2.value - o1.value);
        if (hand.get(0).value == 12 && hand.get(1).value == 11) {
            return isStraight(hand, 5);
        }
        return false;
    }

    private static boolean isRoyalFlush(List<Card> hand) {
        boolean suits = TexasScoreUtil.suitsMatch(hand);
        if (!suits) {
            return false;
        }
        return isTopStraight(hand);
    }

    private static boolean isStraightFlush(List<Card> hand) {
        if (suitsMatch(hand)) {
            return isStraight(hand, 5);
        }
        return false;
    }

    private static boolean isPair(Set<Card> hand) {
        Map<Integer, Integer> tableMap = new HashMap<>();
        for (Card card : hand) {
            if (tableMap.containsKey(card.value)) {
                tableMap.put(card.value, tableMap.get(card.value) + 1);
            } else {
                tableMap.put(card.value, 1);
            }
        }

        for (int value : tableMap.values()) {
            if (value == 2) {
                return true;
            }
        }
        return false;
    }

    private static boolean isThreeOfAkind(Set<Card> hand) {
        Map<Integer, Integer> tableMap = new HashMap<>();
        for (Card card : hand) {
            if (tableMap.containsKey(card.value)) {
                tableMap.put(card.value, tableMap.get(card.value) + 1);
            } else {
                tableMap.put(card.value, 1);
            }
        }

        for (int value : tableMap.values()) {
            if (value == 3) {
                return true;
            }
        }
        return false;
    }

    private static boolean isFullHouse(Set<Card> workHand) {
        Set<Card> hand = new TreeSet<>(workHand);
        if (isThreeOfAkind(hand)) {
            Set<Card> set = new HashSet<>();
            try {
                set = getSet(hand);
            } catch (Exception e) {
                System.out.println("Hmmmmm didn't find three of a kind");
            }
            hand.removeAll(set);
            return isPair(hand);
        }
        return false;
    }

    private static Map<Integer, Set<Card>> createMap(Set<Card> hand) {
        Map<Integer, Set<Card>> cards = new TreeMap<>(Collections.reverseOrder());
        for (Card card : hand) {
            if (cards.containsKey(card.value)) {
                cards.get(card.value).add(card);
            } else {
                Set<Card> list = new HashSet<>();
                list.add(card);
                cards.put(card.value, list);
            }
        }
        return cards;
    }

    private static Set<Card> getSet(Set<Card> hand) throws CardsNotFoundException {
        Map<Integer, Set<Card>> cards = createMap(hand) ;

        for (Map.Entry entry : cards.entrySet()) {
            if (((Set)entry.getValue()).size() == 3) {
                return ((Set)entry.getValue());
            }
        }
        throw formatError(3, hand);
    }

    private static Set<Card> getFourKind(Set<Card> hand) throws CardsNotFoundException {
        Map<Integer, Set<Card>> cards = createMap(hand);

        for (Map.Entry entry : cards.entrySet()) {
            if (((Set)entry.getValue()).size() == 4) {
                return ((Set)entry.getValue());
            }
        }
        throw formatError(4, hand);
    }

    private static CardsNotFoundException formatError(int numOfCards, Set<Card> hand) {
        StringBuilder error = new StringBuilder(numOfCards + " of a kind not found in hand: ");
        for (Card card : hand) {
            error.append(card);
        }
        return new CardsNotFoundException(error.toString());
    }

    private static Set<Card> getPair(Set<Card> hand) throws CardsNotFoundException {
        Map<Integer, Set<Card>> cards = createMap(hand);

        for (Map.Entry entry : cards.entrySet()) {
            if (((Set)entry.getValue()).size() == 2) {
                return ((Set)entry.getValue());
            }
        }
        throw formatError(2, hand);
    }

    private static boolean isTwoPair(Set<Card> hand) {
        if (isPair(hand)) {
            try {
                Set<Card> pair = getPair(hand);
                Set<Card> removed = new HashSet<>();
                for (Card card : hand) {
                    if (!pair.contains(card)) {
                        removed.add(card);
                    }
                }
                if (isPair(removed)) {
                    return true;
                }
            } catch (Exception e) {
                System.out.println("ISSUEEE in isTwoPair()");
                return false;
            }
        }
        return false;
    }

    /**
     *
      * @param hand
     * @return Boolean
     */
    private static boolean isFourOfAKind(Set<Card> hand) {
        Map<Integer, Integer> tableMap = new HashMap<>();
        for (Card card : hand) {
            if (tableMap.containsKey(card.value)) {
                tableMap.put(card.value, tableMap.get(card.value) + 1);
            } else {
                tableMap.put(card.value, 1);
            }
        }

        for (int value: tableMap.values()) {
            if (value == 4) {
                return true;
            }
        }
        return false;
    }
}
