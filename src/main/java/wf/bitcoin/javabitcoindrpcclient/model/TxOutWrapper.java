package wf.bitcoin.javabitcoindrpcclient.model;

import wf.bitcoin.javabitcoindrpcclient.MapWrapper;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

public class TxOutWrapper extends MapWrapper implements TxOut, Serializable {

    public TxOutWrapper(Map m) {
        super(m);
    }

    @Override
    public String bestBlock() {
        return mapStr("bestblock");
    }

    @Override
    public long confirmations() {
        return mapLong("confirmations");
    }

    @Override
    public BigDecimal value() {
        return mapBigDecimal("value");
    }

    @Override
    public String asm() {
        return mapStr("asm");
    }

    @Override
    public String hex() {
        return mapStr("hex");
    }

    @Override
    public long reqSigs() {
        return mapLong("reqSigs");
    }

    @Override
    public String type() {
        return mapStr("type");
    }

    @Override
    public List<String> addresses() {
        return (List<String>) m.get("addresses");
    }

    @Override
    public long version() {
        return mapLong("version");
    }

    @Override
    public boolean coinBase() {
        return mapBool("coinbase");
    }
}
