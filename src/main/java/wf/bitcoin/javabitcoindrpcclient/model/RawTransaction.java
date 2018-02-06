package wf.bitcoin.javabitcoindrpcclient.model;

import wf.bitcoin.javabitcoindrpcclient.BitcoindRpcClient;

import java.io.Serializable;
import java.util.Date;
import java.util.List;
import java.util.Map;

public interface RawTransaction extends Serializable {

    String hex();

    String txId();

    int version();

    long lockTime();

    long size();

    long vsize();

    String hash();

    interface In extends TxInput, Serializable {

        Map<String, Object> scriptSig();

        long sequence();

        RawTransaction getTransaction();

        Out getTransactionOutput();
    }

    List<In> vIn(); // TODO : Create special interface instead of this

    interface Out extends Serializable {

        double value();

        int n();

        interface ScriptPubKey extends Serializable {

            String asm();

            String hex();

            int reqSigs();

            String type();

            List<String> addresses();
        }

        Out.ScriptPubKey scriptPubKey();

        TxInput toInput();

        RawTransaction transaction();
    }

    List<Out> vOut(); // TODO : Create special interface instead of this

    String blockHash();

    int confirmations();

    Date time();

    Date blocktime();
}
