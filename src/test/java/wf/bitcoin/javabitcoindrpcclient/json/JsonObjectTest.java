package wf.bitcoin.javabitcoindrpcclient.json;

import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class JsonObjectTest {

    @Test
    public void deserialise() throws Exception {

        JsonObject jsonObject = new JsonObject();

        jsonObject.put("a", new JsonNull());

        assertEquals("{ \"a\" : null }", jsonObject.deserialise());

    }

    @Test
    public void deserialise2() throws Exception {

        JsonObject jsonObject = new JsonObject();

        jsonObject.put("a", new JsonNull());
        jsonObject.put("b", new JsonString("ciao"));

        assertEquals("{ \"a\" : null, \"b\" : \"ciao\" }", jsonObject.deserialise());

    }

}