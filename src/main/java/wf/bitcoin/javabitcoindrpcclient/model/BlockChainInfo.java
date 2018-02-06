package wf.bitcoin.javabitcoindrpcclient.model;

import java.io.Serializable;

public interface BlockChainInfo extends Serializable {

    String chain();

    int blocks();

    String bestBlockHash();

    double difficulty();

    double verificationProgress();

    String chainWork();
}
