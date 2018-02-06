package wf.bitcoin.javabitcoindrpcclient.json;

public class JsonNull implements JsonValue {

    @Override
    public String deserialise() {
        return "null";
    }

}
