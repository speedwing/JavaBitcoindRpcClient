package wf.bitcoin.javabitcoindrpcclient.json;

import org.testng.annotations.Test;

import static org.testng.Assert.assertEquals;

@Test
public class JsonArrayTest {

    public void testBla() {

        JsonArray jsonValues = new JsonArray<>();
        jsonValues.add(new JsonNull());
        jsonValues.add(new JsonNumber(42));
        assertEquals("[ null , \"42\" ]", jsonValues.deserialise());

    }


}