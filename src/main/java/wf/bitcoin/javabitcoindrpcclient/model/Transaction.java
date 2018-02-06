package wf.bitcoin.javabitcoindrpcclient.model;

import java.io.Serializable;
import java.util.Date;

public interface Transaction extends Serializable {

    String account();

    String address();

    String category();

    double amount();

    double fee();

    int confirmations();

    String blockHash();

    int blockIndex();

    Date blockTime();

    String txId();

    Date time();

    Date timeReceived();

    String comment();

    String commentTo();

    RawTransaction raw();
}
