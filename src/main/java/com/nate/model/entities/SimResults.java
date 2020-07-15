package com.nate.model.entities;

import java.util.Map;

public class SimResults {

    public static final String HOLE_ONE = "holeone";
    public static final String HOLE_TWO = "holetwo";
    public static final String HOLE_THREE = "holethree";
    public static final String HOLE_FOUR = "holefour";
    public static final String HOLE_FIVE = "holefive";
    public static final String HOLE_SIX = "holesix";
    public static final String HOLE_SEVEN = "holeseven";
    public static final String HOLE_EIGHT = "holeeight";
    public static final String HOLE_NINE = "holenine";
    public static final String TIE = "tie";

    private final Map<String, Integer> results;

    private SimResults(Map<String, Integer> results) {
        this.results = results;
    }

    public static SimResults create(Map<String, Integer> results) {
        return new SimResults(results);
    }
}
