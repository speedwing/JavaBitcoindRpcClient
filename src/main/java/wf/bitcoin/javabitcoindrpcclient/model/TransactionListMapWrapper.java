package wf.bitcoin.javabitcoindrpcclient.model;

import wf.bitcoin.javabitcoindrpcclient.BitcoinJSONRPCClient;
import wf.bitcoin.javabitcoindrpcclient.BitcoinRpcRuntimeException;
import wf.bitcoin.javabitcoindrpcclient.ListMapWrapper;

import java.util.Date;
import java.util.List;
import java.util.Map;

import static wf.bitcoin.javabitcoindrpcclient.MapWrapper.*;

public class TransactionListMapWrapper extends ListMapWrapper<Transaction> {

    private BitcoinJSONRPCClient bitcoinJSONRPCClient;

    public TransactionListMapWrapper(BitcoinJSONRPCClient bitcoinJSONRPCClient, List<Map> list) {
        super(list);
        this.bitcoinJSONRPCClient = bitcoinJSONRPCClient;
    }

    @Override
    protected Transaction wrap(final Map m) {
        return new Transaction() {

            @Override
            public String account() {
                return mapStr(m, "account");
            }

            @Override
            public String address() {
                return mapStr(m, "address");
            }

            @Override
            public String category() {
                return mapStr(m, "category");
            }

            @Override
            public double amount() {
                return mapDouble(m, "amount");
            }

            @Override
            public double fee() {
                return mapDouble(m, "fee");
            }

            @Override
            public int confirmations() {
                return mapInt(m, "confirmations");
            }

            @Override
            public String blockHash() {
                return mapStr(m, "blockhash");
            }

            @Override
            public int blockIndex() {
                return mapInt(m, "blockindex");
            }

            @Override
            public Date blockTime() {
                return mapCTime(m, "blocktime");
            }

            @Override
            public String txId() {
                return mapStr(m, "txid");
            }

            @Override
            public Date time() {
                return mapCTime(m, "time");
            }

            @Override
            public Date timeReceived() {
                return mapCTime(m, "timereceived");
            }

            @Override
            public String comment() {
                return mapStr(m, "comment");
            }

            @Override
            public String commentTo() {
                return mapStr(m, "to");
            }

            private RawTransaction raw = null;

            @Override
            public RawTransaction raw() {
                if (raw == null)
                    try {
                        raw = bitcoinJSONRPCClient.getRawTransaction(txId());
                    } catch (BitcoinRpcRuntimeException ex) {
                        throw new RuntimeException(ex);
                    }
                return raw;
            }

            @Override
            public String toString() {
                return m.toString();
            }

        };
    }

}
