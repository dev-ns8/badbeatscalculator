package com.nate.model.entities.stats;

import com.google.common.collect.ImmutableMap;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

public class MetricContainer implements Serializable {

    private static final long serialVersionUID = -858927340L;

    private final Map<KeyedHand, FlopData> map;

    private MetricContainer(Map<KeyedHand, FlopData> map) {
        this.map = ImmutableMap.copyOf(map);
    }

    public FlopData getContainer(KeyedHand hand) {
        return map.get(hand);
    }

    public static MetricContainer create(KeyedHand hand) {
        Map<KeyedHand, FlopData> map = new HashMap<>();
        return new MetricContainer(map);
    }


}
