package com.nate.model.entities.stats;

import com.nate.model.enums.Card;
import com.nate.structure.Pair;
import lombok.Getter;

import java.util.Map;

public class EquityData {

    @Getter private final Map<Pair<Card>, Double> equityData;

    private EquityData(Map<Pair<Card>, Double> equityData) {
        this.equityData = equityData;
    }

    public static EquityData of(Map<Pair<Card>, Double> data) {
        return new EquityData(data);
    }
}
