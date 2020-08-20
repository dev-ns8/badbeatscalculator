package com.nate.api;

import com.google.gson.*;
import com.google.gson.reflect.TypeToken;
import com.nate.model.enums.Card;
import com.nate.structure.Pair;
import com.nate.util.GsonHelper;

import java.lang.reflect.Type;

public class HandListDeserializer implements JsonDeserializer<HandWrapper> {
    @Override
    public HandWrapper deserialize(JsonElement jsonElement, Type type, JsonDeserializationContext jsonDeserializationContext) throws JsonParseException {
        JsonObject obj = jsonElement.getAsJsonObject();
        JsonElement el = obj.get("hands");
        JsonElement firstCard = ((JsonArray) el).get(0);
        JsonElement secondCard = ((JsonArray) el).get(1);
        Card one = GsonHelper.getInstance().fromJson(firstCard, Card.class);
        Card two = GsonHelper.getInstance().fromJson(secondCard, Card.class);
        Pair<Card> first = new Pair<>(one, two);
        System.out.println("stop");
        System.out.println("stop");
        return null;
    }
}
