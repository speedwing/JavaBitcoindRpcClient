package wf.bitcoin.javabitcoindrpcclient.model;

import wf.bitcoin.javabitcoindrpcclient.MapWrapper;

import java.io.Serializable;
import java.util.Map;

public class BlockChainInfoMapWrapper extends MapWrapper implements BlockChainInfo, Serializable {

    public BlockChainInfoMapWrapper(Map m) {
        super(m);
    }

    @Override
    public String chain() {
        return mapStr("chain");
    }

    @Override
    public int blocks() {
        return mapInt("blocks");
    }

    @Override
    public String bestBlockHash() {
        return mapStr("bestblockhash");
    }

    @Override
    public double difficulty() {
        return mapDouble("difficulty");
    }

    @Override
    public double verificationProgress() {
        return mapDouble("verificationprogress");
    }

    @Override
    public String chainWork() {
        return mapStr("chainwork");
    }
}
