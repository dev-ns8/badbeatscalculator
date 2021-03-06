package com.nate.api;


import com.fasterxml.jackson.core.JsonProcessingException;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.nate.model.entities.stats.KeyedHand;
import com.nate.model.enums.Card;
import com.nate.model.enums.Suit;
import com.nate.structure.Pair;
import com.nate.util.scoring.ScoreManager;
import com.nate.util.scoring.impl.TexasScoreManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.lang.reflect.Type;
import java.util.*;

@RestController
public class CalcAggregatorProxy {

    @Autowired
    private ScoreManager scoreManager;

    @RequestMapping("/")
    public String index() {
        return "Greetings from spring-boot";
    }

    @PostMapping("/flop-stats")
    public static ResponseEntity<?> flopStats(@RequestParam(value = "firstVal") int firstVal, @RequestParam(value = "secondVal") int secondVal, @RequestParam(value = "suited") boolean suited) {
        Random rand = new Random();
        int index = rand.nextInt(Suit.values().length);
        List<Integer> suits = new LinkedList<>(Arrays.asList(0,1,2,3));
        int suit = suits.get(index);
        Card first = Card.getCard(firstVal, suit);
        Card second;
        if (suited) {
            second = Card.getCard(secondVal, suit);
        } else {
            suits.remove(index);
            second = Card.getCard(secondVal, suits.get(rand.nextInt(3)));
        }

        Pair<Card> hand = new Pair(first, second);

        KeyedHand data = TexasScoreManager.getFlopStats(hand);

        System.out.println("test");

        return new ResponseEntity<>(data, HttpStatus.OK);
    }

    @PostMapping("/hand-equity")
    public static ResponseEntity<?> getHandEquity(String hands) {
        Gson gson = new Gson();
        Type listType = new TypeToken<ArrayList<Pair>>(){}.getType();
        List<Pair<Card>> handList = gson.fromJson(hands, listType);
        TexasScoreManager.twoHandEquity(handList.get(0), handList.get(1), null);
        System.out.println("hello");
        return new ResponseEntity<>("hello world", HttpStatus.OK);
    }






    @GetMapping("/flop-test")
    public static ResponseEntity<?> flopTest() {

        return new ResponseEntity<>("hello world", HttpStatus.OK);

    }

    @PostMapping("/hand")
    public static void hand(String hand) throws JsonProcessingException {
        Gson gson = new Gson();
        Type handType = new TypeToken<Pair>(){}.getType();
        Pair<Card> test = gson.fromJson(hand.toString(), handType);
        System.out.println("hello");
    }


    @PostMapping("/hand-equity-board")
    public static ResponseEntity<?> getHandEquityWithBoard(String hands, @RequestBody ArrayList<Card> board) {
        Gson gson = new Gson();
//        Type listPairs = new TypeToken<ArrayList<Pair>>(){}.getType();
        Type boardType = new TypeToken<ArrayList<Card>>(){}.getType();
//        List<Pair<Card>> handList = gson.fromJson(hands, listPairs);
//        List<Card> boardList = gson.fromJson(board, boardType);
        System.out.println("test");
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

}
