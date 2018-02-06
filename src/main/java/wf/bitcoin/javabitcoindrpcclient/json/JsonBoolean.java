package wf.bitcoin.javabitcoindrpcclient.json;

public class JsonBoolean implements JsonValue {

    private final String deserialisedValue;

    public JsonBoolean(boolean bool) {
        this.deserialisedValue = String.valueOf(bool);
    }

    @Override
    public String deserialise() {
        return deserialisedValue;
    }

}
