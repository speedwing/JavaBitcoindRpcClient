package wf.bitcoin.javabitcoindrpcclient.model;

import wf.bitcoin.javabitcoindrpcclient.BitcoinJSONRPCClient;
import wf.bitcoin.javabitcoindrpcclient.BitcoinRpcRuntimeException;
import wf.bitcoin.javabitcoindrpcclient.MapWrapper;

import java.io.Serializable;
import java.util.Date;
import java.util.List;
import java.util.Map;

public class BlockMapWrapper extends MapWrapper implements Block, Serializable {

    private BitcoinJSONRPCClient bitcoinJSONRPCClient;

    public BlockMapWrapper(BitcoinJSONRPCClient bitcoinJSONRPCClient, Map m) {
        super(m);
        this.bitcoinJSONRPCClient = bitcoinJSONRPCClient;
    }

    @Override
    public String hash() {
        return mapStr("hash");
    }

    @Override
    public int confirmations() {
        return mapInt("confirmations");
    }

    @Override
    public int size() {
        return mapInt("size");
    }

    @Override
    public int height() {
        return mapInt("height");
    }

    @Override
    public int version() {
        return mapInt("version");
    }

    @Override
    public String merkleRoot() {
        return mapStr("merkleroot");
    }

    @Override
    public String chainwork() {
        return mapStr("chainwork");
    }

    @Override
    public List<String> tx() {
        return (List<String>) m.get("tx");
    }

    @Override
    public Date time() {
        return mapCTime("time");
    }

    @Override
    public long nonce() {
        return mapLong("nonce");
    }

    @Override
    public String bits() {
        return mapStr("bits");
    }

    @Override
    public double difficulty() {
        return mapDouble("difficulty");
    }

    @Override
    public String previousHash() {
        return mapStr("previousblockhash");
    }

    @Override
    public String nextHash() {
        return mapStr("nextblockhash");
    }

    @Override
    public Block previous() throws BitcoinRpcRuntimeException {
        if (!m.containsKey("previousblockhash"))
            return null;
        return bitcoinJSONRPCClient.getBlock(previousHash());
    }

    @Override
    public Block next() throws BitcoinRpcRuntimeException {
        if (!m.containsKey("nextblockhash"))
            return null;
        return bitcoinJSONRPCClient.getBlock(nextHash());
    }

}
