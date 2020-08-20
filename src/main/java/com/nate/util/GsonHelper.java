package com.nate.util;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.nate.api.CardDeserializer;
import com.nate.model.enums.Card;

public class GsonHelper {

    private static final Gson GSON;

    public static Gson getInstance() {
        return GSON;
    }

    static {

        GsonBuilder gsonBuilder = new GsonBuilder()
                .registerTypeAdapter(Card.class, new CardDeserializer());

        GSON = gsonBuilder.create();
    }
}
