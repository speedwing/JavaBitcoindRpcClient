package wf.bitcoin.javabitcoindrpcclient.json;

import org.testng.annotations.Test;

import static org.testng.Assert.assertEquals;

@Test
public class JsonObjectTest {

    public void deserialise() throws Exception {

        JsonObject jsonObject = new JsonObject();

        jsonObject.put("a", new JsonNull());

        assertEquals("{ \"a\" : null }", jsonObject.deserialise());

    }

    public void deserialise2() throws Exception {

        JsonObject jsonObject = new JsonObject();

        jsonObject.put("a", new JsonNull());
        jsonObject.put("b", new JsonString("ciao"));

        assertEquals("{ \"a\" : null, \"b\" : \"ciao\" }", jsonObject.deserialise());

    }

}