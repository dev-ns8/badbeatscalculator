package com.nate.api;


import com.fasterxml.jackson.core.JsonProcessingException;
import com.google.gson.Gson;
import com.nate.model.entities.stats.KeyedHand;
import com.nate.model.enums.Card;
import com.nate.model.enums.Suit;
import com.nate.util.scoring.ScoreManager;
import com.nate.util.scoring.impl.TexasScoreManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;
import java.util.Random;

@RestController
public class CalcAggregatorProxy {

    @Autowired
    private ScoreManager scoreManager;

    @RequestMapping("/")
    public String index() {
        return "Greetings from spring-boot";
    }

    @GetMapping("/flop-test")
    public static ResponseEntity<?> flopTest() {
        KeyedHand hand = KeyedHand.of(Card.QUEEN_HEART, Card.FOUR_DIAMOND, null);

        return new ResponseEntity<>("hello world", HttpStatus.OK);

    }

//    @PostMapping("/hand")
//    public static void hand(@RequestBody String hand) throws JsonProcessingException {
//        Gson gson = new Gson();
//        final Pair<String> test = gson.fromJson(hand, Pair.class);
//        System.out.println("hello");
//    }

    @PostMapping("/hand-equity")
    public static ResponseEntity<?> getHandEquity(@RequestBody String hands) {
        Gson gson = new Gson();
        final HandWrapper test = gson.fromJson(hands, HandWrapper.class);
        System.out.println("hello");
        return new ResponseEntity<>("hello world", HttpStatus.OK);
    }

//    @PostMapping("/hand-equity")
//    public static ResponseEntity<?> twoCardEquityCalc(@RequestParam(value = "firstHand") @Valid Pair<Card> first, @RequestParam(value = "secondHand") @Valid Pair<Card> second, @RequestParam(value = "thirdHand") @Valid Pair<Card> third) {
//
//
//        return new ResponseEntity<>("hello world", HttpStatus.OK);
//    }
//
//    @PostMapping("/hand-equity")
//    public static ResponseEntity<?> twoCardEquityCalc(@RequestParam(value = "firstHand") @Valid Pair<Card> first, @RequestParam(value = "secondHand") @Valid Pair<Card> second, @RequestParam(value = "thirdHand") @Valid Pair<Card> third, @RequestParam(value = "fourthHand") @Valid Pair<Card> fourth) {
//
//
//        return new ResponseEntity<>("hello world", HttpStatus.OK);
//    }

//    @PostMapping("/flop-stats")
//    public static ResponseEntity<?> flopStats(@RequestParam(value = "firstVal") int firstVal, @RequestParam(value = "secondVal") int secondVal, @RequestParam(value = "suited") boolean suited) {
//        Random rand = new Random();
//        int index = rand.nextInt(Suit.values().length);
//        List<Integer> suits = new LinkedList<>(Arrays.asList(0,1,2,3));
//        int suit = suits.get(index);
//        Card first = Card.getCard(firstVal, suit);
//        Card second;
//        if (suited) {
//            second = Card.getCard(secondVal, suit);
//        } else {
//            suits.remove(index);
//            second = Card.getCard(secondVal, suits.get(rand.nextInt(3)));
//        }
//
//        Pair<Card> hand = new Pair(first, second);
//
//        TexasScoreManager.getFlopStats(hand);
//
//        return new ResponseEntity<>("Hello World", HttpStatus.OK);
//    }

    @GetMapping("/test-ak")
    public static ResponseEntity<?> testAkQq() {
        KeyedHand ak = KeyedHand.of(Card.ACE_CLUB, Card.KING_CLUB, null);
        KeyedHand qq = KeyedHand.of(Card.QUEEN_DIAMOND, Card.QUEEN_HEART, null);

        TexasScoreManager.simulateConcurrently(ak.toSet(), qq.toSet(), null);

        return new ResponseEntity<>("testAkQq() return", HttpStatus.OK);
    }


}
