package com.nate;

import com.nate.model.enums.Card;
import com.nate.structure.Pair;
import com.nate.util.scoring.impl.TexasScoreManager;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ImportResource;
import org.springframework.scheduling.annotation.EnableAsync;

@SpringBootApplication
@EnableAsync
@ImportResource("/bbc.env.xml")
public class Application {

    public static void main(String[] args) {
        System.out.println("Args coming::");
        System.out.println(args[0]);
        if (args[0].equals("-fs-ns")) {
            Pair<Card> first = new Pair(Card.ACE_CLUB, Card.ACE_DIAMOND);

            TexasScoreManager.getFlopStats(first);

        } else {
            SpringApplication.run(Application.class, args);
        }
    }

}
