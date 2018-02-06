package wf.bitcoin.javabitcoindrpcclient.model;

public class BasicTxInput implements TxInput {

    protected String txid;
    protected int vout;
    protected String scriptPubKey;

    public BasicTxInput(String txid, int vout) {
        this.txid = txid;
        this.vout = vout;
    }

    public BasicTxInput(String txid, int vout, String scriptPubKey) {
        this(txid, vout);
        this.scriptPubKey = scriptPubKey;
    }

    @Override
    public String txid() {
        return txid;
    }

    @Override
    public int vout() {
        return vout;
    }

    @Override
    public String scriptPubKey() {
        return scriptPubKey;
    }

}
