package wf.bitcoin.javabitcoindrpcclient;

import wf.bitcoin.krotjson.JSON;

import java.util.LinkedHashMap;
import java.util.logging.Logger;

public class WalletRequestBuilder {

    private static Logger LOG = Logger.getLogger(WalletRequestBuilder.class.getCanonicalName());

    public String prepareRequest(String method, Object... parameters) {
        String request = JSON.stringify(new LinkedHashMap() {
            {
                put("method", method);
                put("params", parameters);
                put("id", "1");
            }
        });
        return request;
    }


}
