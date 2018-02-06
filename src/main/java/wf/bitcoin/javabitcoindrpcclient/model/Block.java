package wf.bitcoin.javabitcoindrpcclient.model;

import wf.bitcoin.javabitcoindrpcclient.BitcoinRpcRuntimeException;
import wf.bitcoin.javabitcoindrpcclient.BitcoindRpcClient;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

public interface Block extends Serializable {

    String hash();

    int confirmations();

    int size();

    int height();

    int version();

    String merkleRoot();

    List<String> tx();

    Date time();

    long nonce();

    String bits();

    double difficulty();

    String previousHash();

    String nextHash();

    String chainwork();

    Block previous() throws BitcoinRpcRuntimeException;

    Block next() throws BitcoinRpcRuntimeException;
}
