package wf.bitcoin.javabitcoindrpcclient.json;

import java.util.LinkedList;

import static java.util.stream.Collectors.joining;

public class JsonArray<T extends JsonValue> extends LinkedList<T> implements JsonValue {

    @Override
    public String deserialise() {
        String jsonArray = this.stream().map(JsonValue::deserialise).collect(joining(" , "));
        return "[ " + jsonArray + " ]";
    }

}
