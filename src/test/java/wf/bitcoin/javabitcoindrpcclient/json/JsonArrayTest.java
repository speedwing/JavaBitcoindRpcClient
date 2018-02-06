package wf.bitcoin.javabitcoindrpcclient.json;

import org.junit.Test;

import static org.junit.Assert.assertEquals;

import static org.junit.Assert.assertEquals;

public class JsonArrayTest {

    @Test
    public void testBla() {

        JsonArray jsonValues = new JsonArray<>();
        jsonValues.add(new JsonNull());
        jsonValues.add(new JsonNumber(42));
        assertEquals("[ null , \"42\" ]", jsonValues.deserialise());

    }


}