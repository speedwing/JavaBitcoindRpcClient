package wf.bitcoin.javabitcoindrpcclient.model;

import java.io.Serializable;

public interface Info extends Serializable {

    long version();

    long protocolVersion();

    long walletVersion();

    double balance();

    int blocks();

    int timeOffset();

    int connections();

    String proxy();

    double difficulty();

    boolean testnet();

    long keyPoolOldest();

    long keyPoolSize();

    double payTxFee();

    double relayFee();

    String errors();
}
