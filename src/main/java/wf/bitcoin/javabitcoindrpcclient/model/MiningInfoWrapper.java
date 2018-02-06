package wf.bitcoin.javabitcoindrpcclient.model;

import wf.bitcoin.javabitcoindrpcclient.MapWrapper;

import java.io.Serializable;
import java.util.Map;

public class MiningInfoWrapper extends MapWrapper implements MiningInfo, Serializable {

    public MiningInfoWrapper(Map m) {
        super(m);
    }

    @Override
    public int blocks() {
        return mapInt("blocks");
    }

    @Override
    public int currentBlockSize() {
        return mapInt("currentblocksize");
    }

    @Override
    public int currentBlockWeight() {
        return mapInt("currentblockweight");
    }

    @Override
    public int currentBlockTx() {
        return mapInt("currentblocktx");
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
    public double networkHashps() {
        return Double.valueOf(mapStr("networkhashps"));
    }

    @Override
    public int pooledTx() {
        return mapInt("pooledtx");
    }

    @Override
    public boolean testNet() {
        return mapBool("testnet");
    }

    @Override
    public String chain() {
        return mapStr("chain");
    }
}
