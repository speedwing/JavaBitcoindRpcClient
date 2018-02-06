package wf.bitcoin.javabitcoindrpcclient.model;

import java.io.Serializable;

public interface Unspent extends TxInput, TxOutput, Serializable {

    @Override
    String txid();

    @Override
    int vout();

    @Override
    String address();

    String account();

    String scriptPubKey();

    @Override
    double amount();

    int confirmations();
}
