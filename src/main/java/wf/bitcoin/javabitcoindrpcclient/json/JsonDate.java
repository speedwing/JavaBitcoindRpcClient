package wf.bitcoin.javabitcoindrpcclient.json;

import java.util.Date;

public class JsonDate implements JsonValue {

    private final String deserialisedValue;

    public JsonDate(Date date) {
        this.deserialisedValue = "new Date(" + date.getTime() + ")";
    }

    @Override
    public String deserialise() {
        return JsonValue.betweenQuotes(deserialisedValue);
    }

}
