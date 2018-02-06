package wf.bitcoin.javabitcoindrpcclient.model;

import wf.bitcoin.javabitcoindrpcclient.MapWrapper;

import java.io.Serializable;
import java.util.Map;

public class MultiSigWrapper extends MapWrapper implements MultiSig, Serializable {

    public MultiSigWrapper(Map m) {
        super(m);
    }


    @Override
    public String address() {
        return mapStr("address");
    }

    @Override
    public String redeemScript() {
        return mapStr("redeemScript");
    }
}
