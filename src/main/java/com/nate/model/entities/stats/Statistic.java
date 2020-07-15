package com.nate.model.entities.stats;

public enum Statistic {

    QUADS("Quads"),
    FULL_HOUSE("Full house"),
    FLUSH("Flush"),
    STRAIGHT("Straight"),
    SET("Set"),
    TWO_PAIR("Two pair"),
    OVER_PAIR("Over pair"),
    MIDDLE_PAIR("Middle pair"),
    LOW_PAIR("Low pair"),
    ACE_HIGH("Ace high"),
    NO_HAND("No hand"),
    FLUSH_DRAW("Flush draw"),
    NUT_FLUSH_DRAW("Nut flush draw"),
    OPEN_END_DRAW("Open ended straight draw"),
    NUMBER_OF_RUNS("Number of simulations");

    String name;

    Statistic(String name) {
        this.name = name;
    }
}
