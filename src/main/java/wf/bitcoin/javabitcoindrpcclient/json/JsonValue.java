package wf.bitcoin.javabitcoindrpcclient.json;

public interface JsonValue {

    String deserialise();

    static String betweenQuotes(String value) {
        return "\"" + value + "\"";
    }

}
