package wf.bitcoin.javabitcoindrpcclient.model;

import wf.bitcoin.javabitcoindrpcclient.MapWrapper;

import java.io.Serializable;
import java.util.Map;

public class AddressWrapper extends MapWrapper implements Address, Serializable {

    public AddressWrapper(Map m) {
        super(m);
    }

    @Override
    public String address() {
        return mapStr("address");
    }

    @Override
    public String connected() {
        return mapStr("connected");
    }
}
