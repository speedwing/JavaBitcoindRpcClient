package wf.bitcoin.javabitcoindrpcclient.json;

public class JsonNumber implements JsonValue {

    private final String deserialisedValue;

    public JsonNumber(int value) {
        this.deserialisedValue = String.valueOf(value);
    }

    public JsonNumber(float value) {
        this.deserialisedValue = String.valueOf(value);
    }

    public JsonNumber(double value) {
        this.deserialisedValue = String.valueOf(value);
    }

    @Override
    public String deserialise() {
        return JsonValue.betweenQuotes(deserialisedValue);
    }

}
