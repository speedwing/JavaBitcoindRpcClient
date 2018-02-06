package wf.bitcoin.javabitcoindrpcclient.model;

import wf.bitcoin.javabitcoindrpcclient.MapWrapper;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Map;

public class TxOutSetInfoWrapper extends MapWrapper implements TxOutSetInfo, Serializable {

    public TxOutSetInfoWrapper(Map m) {
        super(m);
    }

    @Override
    public long height() {
        return mapInt("height");
    }

    @Override
    public String bestBlock() {
        return mapStr("bestBlock");
    }

    @Override
    public long transactions() {
        return mapInt("transactions");
    }

    @Override
    public long txouts() {
        return mapInt("txouts");
    }

    @Override
    public long bytesSerialized() {
        return mapInt("bytes_serialized");
    }

    @Override
    public String hashSerialized() {
        return mapStr("hash_serialized");
    }

    @Override
    public BigDecimal totalAmount() {
        return mapBigDecimal("total_amount");
    }
}
