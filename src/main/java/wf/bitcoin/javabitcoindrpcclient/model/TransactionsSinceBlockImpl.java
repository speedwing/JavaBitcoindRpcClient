package wf.bitcoin.javabitcoindrpcclient.model;

import wf.bitcoin.javabitcoindrpcclient.BitcoinJSONRPCClient;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

public class TransactionsSinceBlockImpl implements TransactionsSinceBlock, Serializable {

    private BitcoinJSONRPCClient bitcoinJSONRPCClient;
    public final List<Transaction> transactions;
    public final String lastBlock;

    public TransactionsSinceBlockImpl(BitcoinJSONRPCClient bitcoinJSONRPCClient, Map r) {
        this.bitcoinJSONRPCClient = bitcoinJSONRPCClient;
        this.transactions = new TransactionListMapWrapper(bitcoinJSONRPCClient, (List) r.get("transactions"));
        this.lastBlock = (String) r.get("lastblock");
    }

    @Override
    public List<Transaction> transactions() {
        return transactions;
    }

    @Override
    public String lastBlock() {
        return lastBlock;
    }

}
