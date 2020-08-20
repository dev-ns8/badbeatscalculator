package com.nate.api;

import com.google.gson.*;
import com.nate.model.enums.Card;

import java.lang.reflect.Type;

public class CardDeserializer implements JsonDeserializer<Card> {

    @Override
    public Card deserialize(JsonElement jsonElement, Type type, JsonDeserializationContext jsonDeserializationContext) throws JsonParseException {
        JsonObject obj = jsonElement.getAsJsonObject();
        JsonObject jObj = obj.getAsJsonObject("suit");
        JsonElement value = obj.get("value");
        String suit = jObj.get("name").getAsString();
        return Card.getCard(value.getAsInt(), suit);
    }
}
