package wf.bitcoin.javabitcoindrpcclient.json;


import org.testng.Assert;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import java.util.Optional;

@Test
public class JsonParserTest {

    @DataProvider(name = "json-tests-1")
    private Object[][] jsonTests() {
        return new Object[][]{
                {"{}", 0, 1},
                {" { } ", 1, 3},
                {" \n { } ", 3, 5},
                {" \t { } ", 3, 5},
                {" { { } } ", 1, 7}
        };
    }

    @Test(dataProvider = "json-tests-1")
    public void findNextJsonObject(String json, int indexBegin, int indexEnd) throws Exception {
        JsonParser jsonParser = new JsonParser(json);
        Optional<JsonParser.JsonObjectDetails> nextJsonObject = jsonParser.findNextJsonObject();
        Assert.assertEquals(nextJsonObject.get(), jsonParser.new JsonObjectDetails(indexBegin, indexEnd, JsonParser.JsonObjectType.OBJECT));
    }

    @Test(expectedExceptions = RuntimeException.class)
    public void findNextJsonObjectException() {
        String simpleJson = " { { } ";
        JsonParser jsonParser = new JsonParser(simpleJson);
        jsonParser.findNextJsonObject();
    }


    @Test(expectedExceptions = RuntimeException.class)
    public void findNextJsonObjectExceptionUnexpectedCharacter() {
        String simpleJson = "a { { } ";
        JsonParser jsonParser = new JsonParser(simpleJson);
        jsonParser.findNextJsonObject();
    }

    public void bla() {
        String json = "{ \"bla\" : true } ";
        JsonParser jsonParser = new JsonParser(json);
        JsonValue parse = jsonParser.parse();
        System.out.println("Parsed: " + parse.deserialise());
    }

    public void bla1() {
        String json = "[ {\"bla\" : true } ]";
        JsonParser jsonParser = new JsonParser(json);
        JsonValue parse = jsonParser.parse();
        System.out.println("Parsed: " + parse.deserialise());
    }


}