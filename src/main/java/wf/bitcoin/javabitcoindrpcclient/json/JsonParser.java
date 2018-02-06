package wf.bitcoin.javabitcoindrpcclient.json;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.logging.Logger;

public class JsonParser {

    private Logger LOG = Logger.getLogger(JsonParser.class.getCanonicalName());

    private final String json;

    private int cursor = 0;

    private List<Character> characterList = Arrays.asList('\t', ' ', '\n');

    public JsonParser(String json) {
        this.json = json;
    }

    public JsonValue parse() {
        Optional<JsonObjectDetails> nextJsonObjectOpt = this.findNextJsonObject();
        return nextJsonObjectOpt
                .map(jsonObjectDetails -> {
                    final JsonValue jsonValue;
                    if (jsonObjectDetails.getJsonObjectType() == JsonObjectType.OBJECT) {
                        jsonValue = parseJsonObject(json.substring(jsonObjectDetails.getIndexBegin(), jsonObjectDetails.getIndexEnd() + 1));
                    } else {
                        jsonValue = parseJsonArray(json.substring(jsonObjectDetails.getIndexBegin(), jsonObjectDetails.getIndexEnd() + 1));
                    }
                    return jsonValue;
                })
                .orElse(new JsonNull());
    }

    public JsonValue parseJsonObject(String jsonObjectString) {
        LOG.info("parseJsonObject, jsonObject(" + jsonObjectString + ")");
        JsonObject jsonObject = new JsonObject();
        // I'm in an object, there

        return jsonObject;
    }

    public JsonValue parseJsonArray(String jsonObject) {
        LOG.info("parseJsonArray, jsonObject(" + jsonObject + ")");
        return null;
    }

    private int find(int startingIndex, JsonObjectType jsonObjectType) {
        final char open, close;
        if (jsonObjectType == JsonObjectType.OBJECT) {
            open = '{';
            close = '}';
        } else {
            open = '[';
            close = ']';
        }
        for (int j = startingIndex + 1, k = 1; j < json.length(); j++) {
            final Character currentChar = json.charAt(j);
            if (currentChar == open) {
                k++;
            } else if (currentChar == close) {
                k--;
            }
            if (k == 0) {
                return j;
            }
        }
        throw new RuntimeException("Malformed json");
    }

    public Optional<JsonObjectDetails> findNextJsonObject() {
        for (int i = cursor; i < json.length(); i++) {
            final Character currentChar = json.charAt(i);
            if (currentChar == '{') {
                int endIndex = find(i, JsonObjectType.OBJECT);
                return Optional.of(new JsonObjectDetails(i, endIndex, JsonObjectType.OBJECT));
            } else if (currentChar == '[') {
                int endIndex = find(i, JsonObjectType.ARRAY);
                return Optional.of(new JsonObjectDetails(i, endIndex, JsonObjectType.ARRAY));
            } else if (!characterList.contains(currentChar)) {
                throw new RuntimeException("Malformed Json. Unexpected characters.");
            }
        }
        return Optional.empty();
    }

    public enum JsonObjectType {
        OBJECT, ARRAY
    }

    public class JsonObjectDetails {

        private final int indexBegin;

        private final int indexEnd;

        private final JsonObjectType jsonObjectType;

        public JsonObjectDetails(int indexBegin, int indexEnd, JsonObjectType jsonObjectType) {
            this.indexBegin = indexBegin;
            this.indexEnd = indexEnd;
            this.jsonObjectType = jsonObjectType;
        }

        public int getIndexBegin() {
            return indexBegin;
        }

        public int getIndexEnd() {
            return indexEnd;
        }

        public JsonObjectType getJsonObjectType() {
            return jsonObjectType;
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;

            JsonObjectDetails that = (JsonObjectDetails) o;

            if (indexBegin != that.indexBegin) return false;
            if (indexEnd != that.indexEnd) return false;
            return jsonObjectType == that.jsonObjectType;
        }

        @Override
        public int hashCode() {
            int result = indexBegin;
            result = 31 * result + indexEnd;
            result = 31 * result + (jsonObjectType != null ? jsonObjectType.hashCode() : 0);
            return result;
        }

        @Override
        public String toString() {
            return "JsonObjectDetails{" +
                    "indexBegin=" + indexBegin +
                    ", indexEnd=" + indexEnd +
                    ", jsonObjectType=" + jsonObjectType +
                    '}';
        }
    }


//    public static JsonValue readArray(String jsonString) {
//        Optional
//                .of(jsonString)
//                .map(String::trim)
//                .map(trimmedJson -> {
//                    char first = trimmedJson.charAt(0);
//                    char last = trimmedJson.charAt(trimmedJson.length() - 1);
//                    if (first != '[' || last != ']') {
//                        throw new RuntimeException("Json(" + jsonString + ") is not an array");
//                    }
//                    String trimmedJsonList = trimmedJson.substring(1, trimmedJson.length() -1).trim();
//                    if (trimmedJsonList.length() == 0) {
//                        return JsonArray.Empty;
//                    } else {
//
//                    }
//                });
//
//    }

}
