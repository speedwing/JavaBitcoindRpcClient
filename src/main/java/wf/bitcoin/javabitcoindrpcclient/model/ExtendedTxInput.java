package wf.bitcoin.javabitcoindrpcclient.model;

import java.math.BigDecimal;

public class ExtendedTxInput extends BasicTxInput {

    String redeemScript;
    BigDecimal amount;

    public ExtendedTxInput(String txid, int vout) {
        super(txid, vout);
    }

    public ExtendedTxInput(String txid, int vout, String scriptPubKey) {
        super(txid, vout, scriptPubKey);
    }

    public ExtendedTxInput(String txid, int vout, String scriptPubKey, String redeemScript, BigDecimal amount) {
        super(txid, vout, scriptPubKey);
        this.redeemScript = redeemScript;
        this.amount = amount;
    }

    public String redeemScript() {
        return redeemScript;
    }

    public BigDecimal amount() {
        return amount;
    }

}
