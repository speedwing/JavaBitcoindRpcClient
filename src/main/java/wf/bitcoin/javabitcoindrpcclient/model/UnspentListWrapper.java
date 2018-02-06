package wf.bitcoin.javabitcoindrpcclient.model;

import wf.bitcoin.javabitcoindrpcclient.ListMapWrapper;
import wf.bitcoin.javabitcoindrpcclient.MapWrapper;

import java.util.List;
import java.util.Map;

import static wf.bitcoin.javabitcoindrpcclient.MapWrapper.mapInt;
import static wf.bitcoin.javabitcoindrpcclient.MapWrapper.mapStr;

public class UnspentListWrapper extends ListMapWrapper<Unspent> {

    public UnspentListWrapper(List<Map> list) {
        super(list);
    }

    @Override
    protected Unspent wrap(final Map m) {
        return new Unspent() {

            @Override
            public String txid() {
                return mapStr(m, "txid");
            }

            @Override
            public int vout() {
                return mapInt(m, "vout");
            }

            @Override
            public String address() {
                return mapStr(m, "address");
            }

            @Override
            public String scriptPubKey() {
                return mapStr(m, "scriptPubKey");
            }

            @Override
            public String account() {
                return mapStr(m, "account");
            }

            @Override
            public double amount() {
                return MapWrapper.mapDouble(m, "amount");
            }

            @Override
            public int confirmations() {
                return mapInt(m, "confirmations");
            }

        };
    }
}
