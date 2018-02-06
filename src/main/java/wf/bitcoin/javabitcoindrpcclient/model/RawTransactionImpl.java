package wf.bitcoin.javabitcoindrpcclient.model;

import wf.bitcoin.javabitcoindrpcclient.BitcoinJSONRPCClient;
import wf.bitcoin.javabitcoindrpcclient.BitcoinRpcRuntimeException;
import wf.bitcoin.javabitcoindrpcclient.MapWrapper;

import java.io.Serializable;
import java.util.AbstractList;
import java.util.Date;
import java.util.List;
import java.util.Map;

public class RawTransactionImpl extends MapWrapper implements RawTransaction, Serializable {

    private BitcoinJSONRPCClient bitcoinJSONRPCClient;

    public RawTransactionImpl(BitcoinJSONRPCClient bitcoinJSONRPCClient, Map<String, Object> tx) {
        super(tx);
        this.bitcoinJSONRPCClient = bitcoinJSONRPCClient;
    }

    @Override
    public String hex() {
        return mapStr("hex");
    }

    @Override
    public String txId() {
        return mapStr("txid");
    }

    @Override
    public int version() {
        return mapInt("version");
    }

    @Override
    public long lockTime() {
        return mapLong("locktime");
    }

    @Override
    public String hash() {
        return mapStr("hash");
    }

    @Override
    public long size() {
        return mapLong("size");
    }

    @Override
    public long vsize() {
        return mapLong("vsize");
    }

    private class InImpl extends MapWrapper implements In, Serializable {

        public InImpl(Map m) {
            super(m);
        }

        @Override
        public String txid() {
            return mapStr("txid");
        }

        @Override
        public int vout() {
            return mapInt("vout");
        }

        @Override
        public Map<String, Object> scriptSig() {
            return (Map) m.get("scriptSig");
        }

        @Override
        public long sequence() {
            return mapLong("sequence");
        }

        @Override
        public RawTransaction getTransaction() {
            try {
                return bitcoinJSONRPCClient.getRawTransaction(mapStr("txid"));
            } catch (BitcoinRpcRuntimeException ex) {
                throw new RuntimeException(ex);
            }
        }

        @Override
        public Out getTransactionOutput() {
            return getTransaction().vOut().get(mapInt("vout"));
        }

        @Override
        public String scriptPubKey() {
            return mapStr("scriptPubKey");
        }

    }

    @Override
    public List<In> vIn() {
        final List<Map<String, Object>> vIn = (List<Map<String, Object>>) m.get("vin");
        return new AbstractList<In>() {

            @Override
            public In get(int index) {
                return new InImpl(vIn.get(index));
            }

            @Override
            public int size() {
                return vIn.size();
            }
        };
    }

    private class OutImpl extends MapWrapper implements Out, Serializable {

        public OutImpl(Map m) {
            super(m);
        }

        @Override
        public double value() {
            return mapDouble("value");
        }

        @Override
        public int n() {
            return mapInt("n");
        }

        private class ScriptPubKeyImpl extends MapWrapper implements ScriptPubKey, Serializable {

            public ScriptPubKeyImpl(Map m) {
                super(m);
            }

            @Override
            public String asm() {
                return mapStr("asm");
            }

            @Override
            public String hex() {
                return mapStr("hex");
            }

            @Override
            public int reqSigs() {
                return mapInt("reqSigs");
            }

            @Override
            public String type() {
                return mapStr("type");
            }

            @Override
            public List<String> addresses() {
                return (List) m.get("addresses");
            }

        }

        @Override
        public ScriptPubKey scriptPubKey() {
            return new OutImpl.ScriptPubKeyImpl((Map) m.get("scriptPubKey"));
        }

        @Override
        public TxInput toInput() {
            return new BasicTxInput(transaction().txId(), n());
        }

        @Override
        public RawTransaction transaction() {
            return RawTransactionImpl.this;
        }

    }

    @Override
    public List<Out> vOut() {
        final List<Map<String, Object>> vOut = (List<Map<String, Object>>) m.get("vout");
        return new AbstractList<Out>() {

            @Override
            public Out get(int index) {
                return new OutImpl(vOut.get(index));
            }

            @Override
            public int size() {
                return vOut.size();
            }
        };
    }

    @Override
    public String blockHash() {
        return mapStr("blockhash");
    }

    @Override
    public int confirmations() {
        return mapInt("confirmations");
    }

    @Override
    public Date time() {
        return mapCTime("time");
    }

    @Override
    public Date blocktime() {
        return mapCTime("blocktime");
    }

}
