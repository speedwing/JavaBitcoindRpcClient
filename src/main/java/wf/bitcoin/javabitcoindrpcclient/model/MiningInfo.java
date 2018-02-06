package wf.bitcoin.javabitcoindrpcclient.model;

import java.io.Serializable;

public interface MiningInfo extends Serializable {

    int blocks();

    int currentBlockSize();

    int currentBlockWeight();

    int currentBlockTx();

    double difficulty();

    String errors();

    double networkHashps();

    int pooledTx();

    boolean testNet();

    String chain();
}
