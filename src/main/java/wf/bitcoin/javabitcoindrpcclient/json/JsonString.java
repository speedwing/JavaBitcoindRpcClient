package wf.bitcoin.javabitcoindrpcclient.json;

public class JsonString implements JsonValue {

    private final String deserialisedValue;

    public JsonString(String value) {
        this.deserialisedValue = value;
    }

    @Override
    public String deserialise() {
        return JsonValue.betweenQuotes(deserialisedValue);
    }

}
