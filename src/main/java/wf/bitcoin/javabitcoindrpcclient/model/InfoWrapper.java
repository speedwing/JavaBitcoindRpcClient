package wf.bitcoin.javabitcoindrpcclient.model;

import wf.bitcoin.javabitcoindrpcclient.MapWrapper;

import java.io.Serializable;
import java.util.Map;

public class InfoWrapper extends MapWrapper implements Info, Serializable {

    public InfoWrapper(Map m) {
        super(m);
    }

    @Override
    public double balance() {
        return mapDouble("balance");
    }

    @Override
    public int blocks() {
        return mapInt("blocks");
    }

    @Override
    public int connections() {
        return mapInt("connections");
    }

    @Override
    public double difficulty() {
        return mapDouble("difficulty");
    }

    @Override
    public String errors() {
        return mapStr("errors");
    }

    @Override
    public long keyPoolOldest() {
        return mapLong("keypoololdest");
    }

    @Override
    public long keyPoolSize() {
        return mapLong("keypoolsize");
    }

    @Override
    public double payTxFee() {
        return mapDouble("paytxfee");
    }

    @Override
    public long protocolVersion() {
        return mapLong("protocolversion");
    }

    @Override
    public String proxy() {
        return mapStr("proxy");
    }

    @Override
    public double relayFee() {
        return mapDouble("relayfee");
    }

    @Override
    public boolean testnet() {
        return mapBool("testnet");
    }

    @Override
    public int timeOffset() {
        return mapInt("timeoffset");
    }

    @Override
    public long version() {
        return mapLong("version");
    }

    @Override
    public long walletVersion() {
        return mapLong("walletversion");
    }

}
