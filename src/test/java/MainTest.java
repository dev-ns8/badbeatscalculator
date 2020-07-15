import com.nate.model.entities.CardComparator;
import com.nate.model.enums.Card;
import com.nate.util.concurrent.Runner;
import com.nate.util.scoring.impl.TexasScoreManager;
import com.nate.util.scoring.impl.TexasScoreUtil;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class MainTest {

//    public boolean testRoyalFlush() {
//        List<Card> royal = new ArrayList<>();
//        royal.add(Card.ACE_CLUB);
//        royal.add(Card.KING_CLUB);
//        royal.add(Card.QUEEN_CLUB);
//        royal.add(Card.JACK_CLUB);
//        royal.add(Card.TEN_CLUB);
//        System
//        boolean result = isRoyalFlush.test(royal);
//        return result;
//    }


    public static void atomocityTest() {
        Map<String, Integer> test = new ConcurrentHashMap<>();
        test.put("Nate", 25);
        test.put("liz", 31);
        test.compute("liz", (k, v) -> {
            System.out.println(k);
            System.out.println(v);
            if (v < 30) {
                return 0;
            }
            return v;
        });
    }

    public static void testTreeMap() {
        List<Integer> list = new ArrayList<>(Stream.of(10, 22, 14, 10).collect(Collectors.toList()));
        Map<Integer, Integer> map = new TreeMap<>();
        for (int i : list) {
            if (map.containsKey(i)) {
                map.put(i, 2);
            } else {
                map.put(i, 1);
            }
        }

        for (Map.Entry entry : map.entrySet()) {
            System.out.println("Key: " + entry.getKey() + " and value: " + entry.getValue());
        }
    }

//    public static void testCounter() {
//        ExecutorService service = Executors.newFixedThreadPool(10);
//        Set<Card> queens = Stream.of(Card.QUEEN_DIAMOND, Card.QUEEN_HEART).collect(Collectors.toSet());
//        Set<Card> ak = Stream.of(Card.ACE_SPADE, Card.KING_CLUB).collect(Collectors.toSet());
//
//        for (int i = 0; i < 1000; i++) {
//            Runner run = Runner.create(queens, ak, null, 1000);
//            service.submit(run);
//        }
//        service.shutdown();
//
//        ThreadPool.printResults(ThreadPool.getResults());
//
//        System.out.println("Counter: " + ThreadPool.counter);
//    }
//
//    public static void runRests() throws Exception {
////        bugHandWheelStraight();
//        ThreadPool sim = ThreadPool.create();
////        sim.pocketTens();
//        Set<Card> queens = Stream.of(Card.QUEEN_DIAMOND, Card.QUEEN_HEART).collect(Collectors.toSet());
//        Set<Card> ak = Stream.of(Card.ACE_SPADE, Card.KING_CLUB).collect(Collectors.toSet());
//        Set<Card> tens = Stream.of(Card.TEN_CLUB, Card.TEN_DIAMOND).collect(Collectors.toSet());
//        sim.simulateConcurrently(queens, ak, null, 1000000);
////        Set<Card> board = Stream.of(Card.JACK_HEART, Card.JACK_DIAMOND, Card.SEVEN_SPADE).collect(Collectors.toSet());
////        sim.genericSim(queens, ak, null, 1000000);
//
////        sim.pocketTens();
//
//
//        // -=------------------------------------------------
////        bugHand();
////        timeTrials();
////        secondBugHand();
////        testHandCompare();
////        testGetStraight();
////        testSorting();
//
////        try {
////            testSevenHand();
////        } catch (CardsNotFoundException e) {
////            System.out.println(e.getMessage());
////        }
////        testStraight();
////        testTwoPair();
//    }


//    public static void testHandEquals() {
//        Pair<Card> first = new Pair<>(Card.FOUR_HEART, Card.ACE_CLUB);
//        Pair<Card> second = new Pair<>(Card.ACE_CLUB, Card.FOUR_HEART);
//
//        Pair<Card> first2 = new Pair<>(Card.ACE_CLUB, Card.KING_DIAMOND);
//        Pair<Card> second2 = new Pair<>(Card.KING_DIAMOND, Card.ACE_CLUB);
//
//        Pair<Card> first3 = new Pair<>(Card.TWO_CLUB, Card.ACE_CLUB);
//        Pair<Card> second3 = new Pair<>(Card.ACE_CLUB, Card.FOUR_HEART);
//        System.out.println(first.equals(second));
//        System.out.println(first2.equals(second2));
//        System.out.println(first3.equals(second3));
//    }
//
//    public static void testHandCompare() {
//        List<Pair<Card>> handList = new ArrayList<>();
//        Pair<Card> first = new Pair<>(Card.FOUR_HEART, Card.ACE_CLUB);
//        Pair<Card> second = new Pair<>(Card.ACE_CLUB, Card.FOUR_HEART);
//
//        Pair<Card> first2 = new Pair<>(Card.ACE_CLUB, Card.KING_DIAMOND);
//        Pair<Card> second2 = new Pair<>(Card.KING_DIAMOND, Card.ACE_CLUB);
//
//        Pair<Card> first3 = new Pair<>(Card.TWO_CLUB, Card.ACE_CLUB);
//        Pair<Card> second3 = new Pair<>(Card.ACE_CLUB, Card.FOUR_HEART);
//
//        Pair<Card> first4= new Pair<>(Card.TEN_CLUB, Card.TEN_HEART);
//        Pair<Card> second4 = new Pair<>(Card.ACE_CLUB, Card.FOUR_HEART);
//
//
//        System.out.println(Pair.compare(first, second));
//        System.out.println(Pair.compare(first2, second2));
//        System.out.println(Pair.compare(first3, second3));
//        System.out.println(Pair.compare(first4, second4));
////        System.out.println(Pair.compare(first3, second3));
//    }
//
//    public static void testHandScorer() {
//        List<Card> hand = Arrays.asList(Card.ACE_CLUB, Card.TWO_DIAMOND, Card.THREE_SPADE, Card.FOUR_HEART, Card.TEN_CLUB);
//        List<Card> hand2 = Arrays.asList(Card.ACE_CLUB, Card.KING_DIAMOND, Card.QUEEN_CLUB, Card.ACE_DIAMOND, Card.KING_HEART);
//        List<Card> hand3 = Arrays.asList(Card.ACE_CLUB, Card.TWO_DIAMOND, Card.THREE_SPADE, Card.FOUR_HEART, Card.FIVE_CLUB);
//        System.out.println("" + ScoreUtil.scoreHand(hand2));
//    }
//
//    public static void testStraight() {
//        List<Card> straight = Arrays.asList(Card.TEN_CLUB, Card.NINE_CLUB, Card.SEVEN_DIAMOND, Card.EIGHT_HEART, Card.SIX_SPADE, Card.ACE_DIAMOND, Card.TWO_SPADE);
//        List<Card> pair = Arrays.asList(Card.TWO_CLUB, Card.NINE_CLUB, Card.SEVEN_DIAMOND, Card.EIGHT_HEART, Card.SIX_SPADE, Card.ACE_DIAMOND, Card.TWO_SPADE);
//        List<Card> aceHigh = Arrays.asList(Card.THREE_SPADE, Card.NINE_CLUB, Card.SEVEN_DIAMOND, Card.EIGHT_HEART, Card.SIX_SPADE, Card.ACE_DIAMOND, Card.TWO_SPADE);
//
//        assert ScoreUtil.isStraight(straight) == true;
//        assert ScoreUtil.isStraight(pair) == false;
//        assert ScoreUtil.isStraight(aceHigh) == false;
//
////        assert ScoreUtil.isPair(pair) == true;
//        assert ScoreUtil.scoreHand(aceHigh) == 12;
//        assert ScoreUtil.scoreHand(straight) == 17;
//        assert ScoreUtil.scoreHand(pair) == 14;
//    }
//
//    public static void testTwoPair() {
//        List<Card> twoPair = Arrays.asList(Card.NINE_DIAMOND, Card.NINE_CLUB, Card.SEVEN_DIAMOND, Card.EIGHT_HEART, Card.TWO_HEART, Card.ACE_DIAMOND, Card.TWO_SPADE);
//        List<Card> pair = Arrays.asList(Card.TWO_CLUB, Card.NINE_CLUB, Card.SEVEN_DIAMOND, Card.EIGHT_HEART, Card.SIX_SPADE, Card.ACE_DIAMOND, Card.TWO_SPADE);
//        List<Card> eightHigh= Arrays.asList(Card.THREE_SPADE, Card.FIVE_CLUB, Card.SEVEN_DIAMOND, Card.EIGHT_HEART, Card.SIX_SPADE, Card.TWO_SPADE);
//
////        assert ScoreUtil.isStraight(eightHigh) == false;
////        assert ScoreUtil.isTwoPair(twoPair) == true;
////        assert ScoreUtil.isThreeOfAkind(twoPair) == false;
////        assert ScoreUtil.isTwoPair(pair) == false;
//
//        assert ScoreUtil.scoreHand(twoPair) == 15;
//        assert ScoreUtil.scoreHand(pair) == 14;
//        assert ScoreUtil.scoreHand(eightHigh) == 6;
//    }
//
//    public static void testSevenHand() throws CardsNotFoundException {
//        Set<Card> twoPair = Stream.of(Card.NINE_DIAMOND, Card.NINE_CLUB, Card.SEVEN_DIAMOND, Card.EIGHT_HEART, Card.TWO_HEART, Card.ACE_DIAMOND, Card.TWO_SPADE).collect(Collectors.toSet());
//        Set<Card> pair = Stream.of(Card.TWO_CLUB, Card.NINE_CLUB, Card.SEVEN_DIAMOND, Card.EIGHT_HEART, Card.SIX_SPADE, Card.ACE_DIAMOND, Card.TWO_SPADE).collect(Collectors.toSet());
//        Set<Card> eightHigh= Stream.of(Card.THREE_SPADE, Card.FIVE_CLUB, Card.SEVEN_DIAMOND, Card.EIGHT_HEART, Card.SIX_SPADE, Card.TWO_SPADE).collect(Collectors.toSet());
//        Set<Card> straight = Stream.of(Card.TEN_CLUB, Card.NINE_CLUB, Card.SEVEN_DIAMOND, Card.EIGHT_HEART, Card.SIX_SPADE, Card.ACE_DIAMOND, Card.TWO_SPADE).collect(Collectors.toSet());
//        Set<Card> royal = Stream.of(Card.ACE_CLUB, Card.KING_CLUB, Card.QUEEN_CLUB, Card.FOUR_CLUB, Card.FIVE_DIAMOND, Card.JACK_CLUB, Card.TEN_CLUB).collect(Collectors.toSet());
//        Set<Card> wheelStraightFlush = Stream.of(Card.KING_DIAMOND, Card.NINE_CLUB, Card.ACE_DIAMOND, Card.TWO_DIAMOND, Card.THREE_DIAMOND, Card.FOUR_DIAMOND, Card.FIVE_DIAMOND).collect(Collectors.toSet());
//
//        assert ScoreUtil.scoreSevenCardHand(twoPair) == 15;
//        assert ScoreUtil.scoreSevenCardHand(pair) == 14;
//        assert ScoreUtil.scoreSevenCardHand(eightHigh) == 6;
//        assert ScoreUtil.scoreSevenCardHand(straight) == 17;
//        assert ScoreUtil.scoreSevenCardHand(royal) == 22;
//        assert ScoreUtil.scoreSevenCardHand(wheelStraightFlush) == 21;
//    }

//    public static void testHandCompare() {
//        Set<Card> bottom = Stream.of(Card.ACE_CLUB, Card.SEVEN_CLUB).collect(Collectors.toSet());
//        Set<Card> top = Stream.of(Card.QUEEN_HEART, Card.TEN_HEART).collect(Collectors.toSet());
//
//        Set<Card> board = Stream.of(Card.TEN_CLUB, Card.NINE_HEART, Card.EIGHT_SPADE, Card.JACK_DIAMOND, Card.TWO_DIAMOND).collect(Collectors.toSet());
//
//
//        Set<Card> equalOne = Stream.of(Card.ACE_DIAMOND, Card.JACK_DIAMOND).collect(Collectors.toSet());
//        Set<Card> equalTwo = Stream.of(Card.QUEEN_DIAMOND, Card.KING_DIAMOND).collect(Collectors.toSet());
//
//        Set<Card> boardEqual = Stream.of(Card.ACE_CLUB, Card.TEN_CLUB, Card.KING_CLUB, Card.QUEEN_CLUB, Card.JACK_CLUB).collect(Collectors.toSet());
//
//        Set<Card> highFlush = Stream.of(Card.TEN_SPADE, Card.JACK_SPADE).collect(Collectors.toSet());
//        Set<Card> lowFlush = Stream.of(Card.NINE_SPADE, Card.TWO_SPADE).collect(Collectors.toSet());
//        Set<Card> flushBoard = Stream.of(Card.ACE_SPADE, Card.TWO_DIAMOND, Card.QUEEN_SPADE, Card.THREE_HEART, Card.FIVE_SPADE).collect(Collectors.toSet());
////        Set<Card> equalFlushBoard = Stream.of(Card.ACE_SPADE, Card.KING_SPADE, Card.QUEEN_SPADE, Card.JACK_SPADE, Card.TEN_SPADE).collect(Collectors.toSet());
//
//        int result = ScoreUtil.compareHands(top, bottom, board);
//        int resultTwo = ScoreUtil.compareHands(bottom, top, board);
//        int zero = ScoreUtil.compareHands(equalOne, equalTwo, boardEqual);
//        int flushResult = ScoreUtil.compareHands(highFlush, lowFlush, flushBoard);
//        assert result == -1;
//        assert resultTwo == 1;
//        assert zero == 0;
//        assert flushResult == -1;
//        System.out.println("sss");
//    }

//    public static void timeTrials() {
//        Dealer dealer = Dealer.create();
//        ThreadPool sim = ThreadPool.create(dealer);
//        Map<Integer, Long> times = new HashMap<>();
//
//
//        long startFirst = System.nanoTime();
//        for (int i = 0; i < 50000000; i++) {
//            sim.runFullRandomSimulation(1);
////            System.out.println("Took " + duration + " ms");
//        }
//        long stopFirst = System.nanoTime();
//        long duration = (stopFirst - startFirst);
//        duration = duration / 1000000;
//        System.out.println("TOOK: " + duration + " Milliseconds");
//
////        for (Map.Entry entry : times.entrySet()) {
////            System.out.println("Iteration: " + entry.getKey() + " Took: " + entry.getValue() + " Milliseconds");
////        }
//    }

//    public static void bugHandWheelStraight() {
//        Set<Card> hole = Stream.of(Card.ACE_HEART, Card.KING_SPADE).collect(Collectors.toSet());
//        Set<Card> holeTwo = Stream.of(Card.QUEEN_DIAMOND, Card.QUEEN_HEART).collect(Collectors.toSet());
//        Set<Card> board = Stream.of(Card.FIVE_SPADE, Card.FOUR_SPADE, Card.THREE_HEART, Card.TWO_SPADE, Card.TWO_HEART).collect(Collectors.toSet());
//        Dealer dealer = Dealer.create();
//        ThreadPool sim = ThreadPool.create(dealer);
//        sim.runBugHand(hole, holeTwo, board);
//    }

//    public static void timeTrial() throws Exception {
//        for (int i = 0; i < 500000; i++) {
//            Dealer dealer = Dealer.create();
//
//            Set<Card> first = new TreeSet<>(new CardComparator());
//            Set<Card> second = new TreeSet<>(new CardComparator());
//
//            first.addAll(Stream.of(Card.KING_SPADE, Card.QUEEN_CLUB).collect(Collectors.toSet()));
//            second.addAll(Stream.of(Card.ACE_SPADE, Card.THREE_SPADE).collect(Collectors.toSet()));
//
//            dealer.removeCards(first.stream().collect(Collectors.toList()));
//            dealer.removeCards(second.stream().collect(Collectors.toList()));
//            dealer.shuffle();
//
//            List<Card> board = dealer.dealBoard();
//
//
//            int result = ScoreUtil.compareHands(first, second, board.stream().collect(Collectors.toSet()));
//
//            System.out.print("Hole1: " + Arrays.toString(first.toArray()));
//            System.out.print("           ");
//            System.out.println("Hole2 " + Arrays.toString(second.toArray()));
//            System.out.println("Board: " + Arrays.toString(board.toArray()));
//            System.out.println("Result: " + result);
//            System.out.println("--------------------------------------------------------------------");
//            System.out.println("--------------------------------------------------------------------");
//            System.out.println("--------------------------------------------------------------------");
//        }
//
//    }

    public static void potentialBugHands() {
        Set<Card> bug = new TreeSet<>(Stream.of(Card.ACE_SPADE, Card.ACE_HEART, Card.SEVEN_SPADE, Card.FOUR_SPADE, Card.THREE_SPADE, Card.THREE_HEART, Card.THREE_SPADE).collect(Collectors.toSet()));

    }

    public static void secondBugHand() {
        Set<Card> ak = new TreeSet<>(new CardComparator());
        Set<Card> qq = new TreeSet<>(new CardComparator());
        Set<Card> board = new TreeSet<>(new CardComparator());

        ak.add(Card.ACE_HEART);
        ak.add(Card.KING_CLUB);
        qq.add(Card.QUEEN_DIAMOND);
        qq.add(Card.QUEEN_SPADE);

        board.addAll(Stream.of(Card.TWO_SPADE, Card.THREE_DIAMOND, Card.ACE_SPADE, Card.TWO_CLUB, Card.THREE_CLUB).collect(Collectors.toSet()));
        try {
            int result = TexasScoreUtil.compareHands(ak, qq, board);
            System.out.println("RESULT::: " + result);
        } catch (Exception e) {
            System.out.println("ERRRRR::::::::;;;;;;; ;;;;;; ");
            System.out.println(e.getMessage());
            return;
        }

    }

//    public static void bugHand() {
//        Set<Card> ak = Stream.of(Card.ACE_HEART, Card.KING_SPADE).collect(Collectors.toSet());
//        Set<Card> qq = Stream.of(Card.QUEEN_DIAMOND, Card.QUEEN_CLUB).collect(Collectors.toSet());
//        Set<Card> board = Stream.of(Card.ACE_SPADE, Card.SEVEN_SPADE, Card.NINE_SPADE, Card.TWO_DIAMOND, Card.FOUR_HEART).collect(Collectors.toSet());
///
//        int result = ScoreUtil.compareHands(ak, qq, board);
//        System.out.println("ddd");
//    }
//
//    public static void testGetStraight() {
//        Set<Card> straight = Stream.of(Card.TEN_CLUB, Card.KING_CLUB, Card.NINE_DIAMOND, Card.TWO_SPADE, Card.EIGHT_HEART, Card.SEVEN_DIAMOND, Card.SIX_CLUB).sorted(((o1, o2) -> o2.value - o1.value)).collect(Collectors.toSet());
//        Set<Card> straightTwo = Stream.of(Card.EIGHT_HEART, Card.NINE_DIAMOND, Card.ACE_SPADE, Card.KING_DIAMOND, Card.QUEEN_HEART, Card.JACK_CLUB, Card.TEN_HEART).sorted(((o1, o2) -> o2.value - o1.value)).collect(Collectors.toSet());
//        Set<Card> notStraight = Stream.of(Card.KING_HEART, Card.TWO_CLUB, Card.EIGHT_HEART, Card.NINE_DIAMOND, Card.THREE_CLUB, Card.FOUR_HEART, Card.FIVE_CLUB).sorted(((o1, o2) -> o2.value - o1.value)).collect(Collectors.toSet());
//
//        Set<Card> sortedStraight= new TreeSet<>(new CardComparator());
//        Set<Card> sortedNo = new TreeSet<>(new CardComparator());
//        Set<Card> sortedTwo = new TreeSet<>(new CardComparator());
//
//        sortedStraight.addAll(straight);
//        sortedTwo.addAll(straightTwo);
//        sortedNo.addAll(notStraight);
//
//        Set<Card> returnStraight = ScoreUtil.getStraight(sortedStraight);
//        Set<Card> returnStrTwo = ScoreUtil.getStraight(sortedTwo);
//        Set<Card> returnNoStr = ScoreUtil.getStraight(sortedNo);
//
//        System.out.println("'d");
//    }
//
//    public static void testSorting() {
//        Set<Card> two= Stream.of(Card.NINE_DIAMOND, Card.NINE_CLUB, Card.SEVEN_DIAMOND, Card.EIGHT_HEART, Card.TWO_HEART, Card.ACE_DIAMOND, Card.TWO_SPADE).collect(Collectors.toSet());
//        Set<Card> pair = Stream.of(Card.TWO_CLUB, Card.NINE_CLUB, Card.SEVEN_DIAMOND, Card.EIGHT_HEART, Card.SIX_SPADE, Card.ACE_DIAMOND, Card.TWO_SPADE).collect(Collectors.toSet());
//
//        Set<Card> sortedPair = new TreeSet<>(new CardComparator());
//        Set<Card> sortedTwo = new TreeSet<>(new CardComparator());
//        boolean result = sortedPair.addAll(pair);
//        boolean resultTwo = sortedTwo.addAll(two);
//
//        Object[] sorted = sortedPair.toArray();
//        Object[] sortedOne = sortedTwo.toArray();
//
////        twoList.sort((o1, o2) -> o2.value - o1.value);
////        pairList.sort((o1, o2) -> o2.value - o1.value);
////
////        two = twoList.stream().collect(Collectors.toSet());
////        pair = pairList.stream().collect(Collectors.toSet());
//
//        System.out.println("check");
//    }
}
