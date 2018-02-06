package wf.bitcoin.javabitcoindrpcclient.json;

import java.util.HashMap;

import static java.util.stream.Collectors.joining;

public class JsonObject extends HashMap<String, JsonValue> implements JsonValue {

    @Override
    public String deserialise() {
        String jsonObject = this
                .entrySet()
                .stream()
                .map(bla -> JsonValue.betweenQuotes(bla.getKey()) + " : " + bla.getValue().deserialise())
                .collect(joining(", "));
        return "{ " + jsonObject + " }";
    }

}
